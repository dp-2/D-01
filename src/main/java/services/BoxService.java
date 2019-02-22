
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;
import domain.Message;
import forms.BoxForm;

@Service
@Transactional
public class BoxService {

	// Repository

	@Autowired
	private BoxRepository	repository;

	// Services

	@Autowired
	private ActorService	actorService;
	@Autowired
	private MessageService	messageService;
	@Autowired
	private ServiceUtils	serviceUtils;
	@Autowired
	private Validator		validator;


	// CRUD

	public Box findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Box> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Box> findAll() {
		return this.repository.findAll();
	}

	public List<Box> findAllByActor(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() > 0);
		Assert.notNull(this.actorService.findOne(actor.getId()));
		return this.repository.findBoxsByActor(actor.getId());
	}

	public Box create(final Actor a) {
		final Box res = new Box();
		res.setActor(a);
		res.setIsSystem(false);
		res.setRootBox(res);
		return res;
	}

	public Box save(final Box object) {
		final Box box = (Box) this.serviceUtils.checkObjectSave(object);
		if (box.getId() == 0) {
			if (box.getRootBox() == null)
				box.setRootBox(box);
		} else {
			Assert.isTrue(!box.getIsSystem());
			box.setRootBox(object.getRootBox());
			box.setName(object.getName());
			this.serviceUtils.checkActor(box.getActor());
		}
		return this.repository.save(box);
	}

	public void delete(final Box object) {
		final Box box = (Box) this.serviceUtils.checkObject(object);
		this.serviceUtils.checkActor(box.getActor());
		Assert.isTrue(!box.getIsSystem());
		for (final Message m : this.messageService.findByBox(box))
			this.messageService.delete(m);
		for (final Box f : this.findByActorAndRoot(box.getActor(), box))
			this.delete(f);
		box.setRootBox(null);
		this.save(box);
		this.flush();
		this.repository.delete(box);
	}

	public Box findBoxByActorAndName(final Actor actor, final String name) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() > 0);
		Assert.notNull(this.actorService.findOne(actor.getId()));
		return this.repository.findBoxByActorAndUsername(actor.getId(), name);
	}

	public List<Box> createIsSystemBoxs(final Actor actor) {
		final List<Box> resBoxs = new ArrayList<Box>();
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() >= 0);
		if (actor.getId() > 0)
			Assert.notNull(this.actorService.findOne(actor.getId()));
		final String[] names = new String[] {
			"inbox", "outbox", "spambox", "trashbox"
		};
		for (final String name : names) {
			final Box newBox = this.create(actor);
			newBox.setName(name);
			newBox.setIsSystem(true);
			newBox.setRootBox(newBox);
			final Box newBoxSaved = this.save(newBox);
			resBoxs.add(newBoxSaved);
		}
		return resBoxs;
	}
	public Collection<Box> findByRoot(final Box parent) {
		Assert.notNull(parent);
		Assert.isTrue(parent.getId() > 0);
		Assert.notNull(this.repository.findOne(parent.getId()));
		return this.repository.findByRootId(parent.getId());
	}

	public Collection<Box> findByActorAndRoot(final Actor actor, final Box parent) {
		Assert.notNull(parent);
		Assert.isTrue(parent.getId() > 0);
		Assert.notNull(this.repository.findOne(parent.getId()));
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() > 0);
		Assert.notNull(this.actorService.findOne(actor.getId()));
		return this.repository.findByActorIdAndRootId(actor.getId(), parent.getId());
	}

	public Collection<Box> findByActorWithoutRoot(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() > 0);
		Assert.notNull(this.actorService.findOne(actor.getId()));
		return this.repository.findByActorIdWithoutRoot(actor.getId());
	}

	public void flush() {
		this.repository.flush();
	}

	public BoxForm construct(final Box box) {
		final BoxForm res = new BoxForm();
		res.setName(box.getName());
		res.setRootBox(box.getRootBox());
		return res;
	}

	public Box deconstruct(final BoxForm form, final BindingResult binding) {
		Box res = null;
		if (form.getId() == 0)
			this.create(this.actorService.findPrincipal());
		else
			res = (Box) this.serviceUtils.checkObject(form);
		res.setName(form.getName());
		res.setRootBox(form.getRootBox());
		this.validator.validate(res, binding);
		return res;
	}

}
