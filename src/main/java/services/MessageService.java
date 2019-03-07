
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Message;
import repositories.MessageRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class MessageService {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private MessageRepository		repository;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ServiceUtils			serviceUtils;
	@Autowired
	private LoginService			loginService;
	@Autowired(required = false)
	private Validator				validator;


	public Message findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Message> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Message> findAll() {
		return this.repository.findAll();
	}

	public Collection<Message> findAll(final Box dependency) {
		this.serviceUtils.checkObject(dependency);
		return this.findAll(dependency);
	}

	public Message create(final Box dependency) {
		final Message res = new Message();
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		res.setBox(dependency);
		res.setSender(this.actorService.findPrincipal());
		return res;
	}

	public Message save(final Message object, final Boolean isBroadcast) {
		final Message message = (Message) this.serviceUtils.checkObjectSave(object);
		final Administrator system = (Administrator) this.actorService.findByUserAccount((UserAccount) this.loginService.loadUserByUsername("system"));
		Message message1 = null;
		if (message.getId() == 0) {
			message.setMoment(new Date(System.currentTimeMillis() - 1000));
			message.setBox(this.boxService.findBoxByActorAndName(message.getSender(), "outBox"));
		} else
			message.setBox(object.getBox());
		final Message res = this.repository.save(message);
		if (!message.getBox().getActor().equals(system))
			this.serviceUtils.checkPermisionActor(message.getBox().getActor(), null);
		if (message.getId() == 0) {
			message1 = message;
			message1.setId(0);
			message1.setVersion(0);
			if (this.containsSpam(message1))
				message1.setBox(this.boxService.findBoxByActorAndName(message1.getRecipient(), "spamBox"));
			else if (isBroadcast)
				message1.setBox(this.boxService.findBoxByActorAndName(message1.getRecipient(), "notificationBox"));
			else
				message1.setBox(this.boxService.findBoxByActorAndName(message1.getRecipient(), "inBox"));
			this.repository.save(message1);
		}
		return res;
	}
	public void delete(final Message object) {
		final Message message = (Message) this.serviceUtils.checkObject(object);
		final Actor principal = this.actorService.findPrincipal();
		final Box trashboxPrincipal = this.boxService.findBoxByActorAndName(principal, "trashBox");
		if (message.getBox().equals(trashboxPrincipal)) {
			Assert.isTrue(message.getBox().getActor().equals(this.actorService.findPrincipal()));
			this.serviceUtils.checkPermisionActor(message.getBox().getActor(), null);
			for (final Message m : this.findCopies(message))
				this.repository.delete(m);
		} else {
			message.setBox(this.boxService.findBoxByActorAndName(object.getBox().getActor(), "trashBox"));
			this.save(message, false);
		}
	}

	public boolean containsSpam(final Message message) {
		return this.containsSpam(message.getBody()) || this.containsSpam(message.getTags()) || this.containsSpam(message.getSubject());
	}

	public boolean containsSpam(final String s) {
		Boolean res = false;
		for (final String spamWord : this.configurationService.findOne().getSpamWordsES())
			if (s.contains(spamWord)) {
				res = true;
				break;
			}
		for (final String spamWord : this.configurationService.findOne().getSpamWordsEN())
			if (s.contains(spamWord)) {
				res = true;
				break;
			}
		return res;
	}

	public void broadcast(final Message m) {
		final Message message = (Message) this.serviceUtils.checkObjectSave(m);
		this.serviceUtils.checkAuthority(Authority.ADMIN);
		final Actor principal = this.actorService.findPrincipal();
		for (final Actor a : this.actorService.findAllExceptMe()) {
			final Message mes = this.create(this.boxService.findBoxByActorAndName(principal, "notificationBox"));
			mes.setBody(message.getBody());
			mes.setPriority(message.getPriority());
			mes.setSubject(message.getSubject());
			mes.setTags(message.getTags());
			mes.setRecipient(a);
			this.save(mes, true);
		}
	}

	public Collection<Message> findSendedMessages(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() > 0);
		Assert.notNull(this.actorService.findOne(a.getId()));
		return this.repository.findSendedMessages(a.getId());
	}

	public Collection<Message> findReceivedMessages(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() > 0);
		Assert.notNull(this.actorService.findOne(a.getId()));
		return this.repository.findReceivedMessages(a.getId());
	}

	public Collection<Message> findByBox(final Box f) {
		Assert.notNull(f);
		Assert.isTrue(f.getId() > 0);
		Assert.notNull(this.boxService.findOne(f.getId()));
		return this.repository.findByBoxId(f.getId());
	}

	public Collection<Message> findCopies(final Message m) {
		final Message message = (Message) this.serviceUtils.checkObject(m);
		return this.repository.findMessageByMomentSenderReceiverAndSubject(message.getMoment(), message.getSender().getId(), message.getRecipient().getId(), message.getSubject());
	}
	/*
	 * public MessageForm construct(final Message m) {
	 * final MessageForm res = new MessageForm();
	 * res.setBody(m.getBody());
	 * res.setPriority(m.getPriority());
	 * res.setRecipient(m.getRecipient());
	 * res.setSubject(m.getSubject());
	 * res.setTags(m.getTags());
	 * return res;
	 * }
	 */

	public Message deconstruct(final Message message, final BindingResult binding) {
		Message result = null;
		if (message.getId() == 0) {
			final Actor principal = this.actorService.findPrincipal();
			final Box inbox = this.boxService.findBoxByActorAndName(principal, "inBox");
			message.setBox(inbox);
			message.setMoment(new Date(System.currentTimeMillis() - 1000));
			message.setSender(this.actorService.findPrincipal());
			if (message.getRecipient() == null)
				message.setRecipient(principal);
			result = message;
		} else {
			result = this.findOne(message.getId());
			message.setBody(result.getBody());
			message.setMoment(result.getMoment());
			message.setPriority(result.getPriority());
			message.setRecipient(result.getRecipient());
			message.setSender(result.getSender());
			message.setSubject(result.getSubject());
			message.setTags(result.getTags());
			message.setVersion(result.getVersion());
			result = message;
		}
		this.validator.validate(result, binding);
		return result;
	}
}
