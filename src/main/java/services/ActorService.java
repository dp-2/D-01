
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Member;
import domain.Message;
import forms.ActorForm;

@Service
@Transactional
public class ActorService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private UserAccountRepository	userAccountRepository;

	//Service-----------------------------------------------------------------------------
	@Autowired
	private MemberService			memberService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired(required = false)
	private Validator				validator;

	@Autowired
	private MessageSource			messageSource;

	@Autowired
	private ServiceUtils			serviceUtils;


	public Actor create(final String authority) {
		final Actor actor = new Actor();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		final Authority a = new Authority();
		a.setAuthority(authority);
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		actor.setSpammer(false);
		actor.setUserAccount(userAccount);
		actor.setBanned(false);
		actor.setScore(0.0);
		return actor;
	}

	public Actor findOne(final int ActorId) {
		return this.actorRepository.findOne(ActorId);
	}

	public Actor findByUsername(final String username) {
		final Actor actor = this.actorRepository.findByUsername(username);
		return actor;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.findByUserAccount(userAccount.getId());
	}

	public Collection<Actor> findAll() {
		final Collection<Actor> actors = this.actorRepository.findAll();
		Assert.notNull(actors);

		return actors;
	}

	public Collection<Actor> findAllTypes() {
		final Collection<Actor> actors = new ArrayList<>();
		actors.addAll(this.administratorService.findAll());
		actors.addAll(this.memberService.findAll());
		actors.addAll(this.brotherhoodService.findAll());

		return actors;
	}

	public Actor save(final Actor actor) {

		Assert.notNull(actor);
		final Actor saved = this.actorRepository.save(actor);

		return saved;
	}

	//Other Methods---------------------------------------------------------------------

	public Collection<Actor> findSpammerActors() {
		return this.actorRepository.findSpammerActors();
	}

	public void ban(final Actor actor) {
		actor.setBanned(true);
		actor.getUserAccount().setBanned(true);
		actor.getUserAccount().setEnabled(false);
		this.save(actor);
	}

	public void unban(final Actor actor) {
		actor.setBanned(false);
		actor.setSpammer(false);
		actor.getUserAccount().setEnabled(true);
		this.save(actor);

	}

	public void update(final Actor actor) {

		Assert.notNull(actor);

		final Collection<Authority> authorities = actor.getUserAccount().getAuthorities();
		final Authority mem = new Authority();
		mem.setAuthority(Authority.MEMBER);
		final Authority bro = new Authority();
		bro.setAuthority(Authority.BROTHERHOOD);
		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);

		if (authorities.contains(mem)) {
			Member member = null;
			if (actor.getId() != 0)
				member = this.memberService.findOne(actor.getId());
			else
				member = this.memberService.create();
			member.setUserAccount(actor.getUserAccount());

			member.setEmail(actor.getEmail());
			member.setBanned(actor.getBanned());
			member.setSpammer(actor.getSpammer());
			member.setName(actor.getName());
			member.setPhone(actor.getPhone());
			member.setPhoto(actor.getPhoto());
			member.setSurname(actor.getSurname());
			member.setScore(actor.getScore());
			member.setAddress(actor.getAddress());
			member.setMiddleName(actor.getMiddleName());

			final Actor actor1 = this.memberService.save(member);
			//this.boxService.createIsSystemBoxs(actor1);
		} else if (authorities.contains(bro)) {
			Brotherhood brotherhood = null;
			if (actor.getId() != 0)

				brotherhood = this.brotherhoodService.findOne(actor.getId());
			else {
				brotherhood = this.brotherhoodService.create();
				brotherhood.setUserAccount(actor.getUserAccount());
			}
			brotherhood.setScore(actor.getScore());
			brotherhood.setEmail(actor.getEmail());
			brotherhood.setBanned(actor.getBanned());
			brotherhood.setSpammer(actor.getSpammer());
			brotherhood.setName(actor.getName());
			brotherhood.setPhone(actor.getPhone());
			brotherhood.setPhoto(actor.getPhoto());
			brotherhood.setSurname(actor.getSurname());
			brotherhood.setAddress(actor.getAddress());
			brotherhood.setMiddleName(actor.getMiddleName());

			final Actor actor1 = this.brotherhoodService.save(brotherhood);
			//this.boxService.createIsSystemBoxs(actor1);

		} else if (authorities.contains(admin)) {
			Administrator administrator = null;
			if (actor.getId() != 0)
				administrator = this.administratorService.findOne(actor.getId());
			else {
				administrator = this.administratorService.create();
				administrator.setUserAccount(actor.getUserAccount());
			}

			administrator.setSurname(actor.getSurname());
			administrator.setMiddleName(actor.getMiddleName());
			administrator.setEmail(actor.getEmail());
			administrator.setBanned(actor.getBanned());
			administrator.setSpammer(actor.getSpammer());
			administrator.setName(actor.getName());
			administrator.setPhone(actor.getPhone());
			administrator.setPhoto(actor.getPhoto());
			administrator.setAddress(actor.getAddress());

			final Actor actor1 = this.administratorService.save(administrator);
			//this.boxService.createIsSystemBoxs(actor1);
		}

	}

	public Actor findActorByUsername(final String username) {
		final Actor actor = this.actorRepository.findByUsername(username);
		return actor;
	}

	public Actor findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.actorRepository.findByUserAccount(userAccount.getId());
	}

	public Collection<Actor> findAllExceptMe() {
		final Collection<Actor> res = this.findAll();
		res.remove(this.findPrincipal());
		return res;
	}

	public ActorForm construct(final Actor b) {
		final ActorForm res = new ActorForm();
		res.setEmail(b.getEmail());
		res.setName(b.getName());
		res.setPhone(b.getPhone());
		res.setPhoto(b.getPhoto());
		res.setSurname(b.getSurname());
		res.setUsername(b.getUserAccount().getUsername());
		res.setId(b.getId());
		res.setVersion(b.getVersion());
		res.setMiddleName(b.getMiddleName());
		res.setAddress(b.getAddress());
		final Authority auth = ((List<Authority>) b.getUserAccount().getAuthorities()).get(0);
		res.setAuthority(auth.getAuthority());
		return res;
	}

	public void validateForm(final ActorForm form, final BindingResult binding) {
		if (form.getId() == 0 && !form.getAccept()) {
			/*
			 * binding.addError(new FieldError("brotherhoodForm", "accept", form.getAccept(), false, new String[] {
			 * "brotherhoodForm.accept", "accept"
			 * }, new Object[] {
			 * new DefaultMessageSourceResolvable(new String[] {
			 * "brotherhoodForm.accept", "accept"
			 * }, new Object[] {}, "accept")
			 * }, "brotherhood.mustaccept"));
			 */
			final Locale locale = LocaleContextHolder.getLocale();
			final String errorMessage = this.messageSource.getMessage("brotherhood.mustaccept", null, locale);
			binding.addError(new FieldError("actorForm", "accept", errorMessage));
		}
		if (!form.getConfirmPassword().equals(form.getPassword())) {
			final Locale locale = LocaleContextHolder.getLocale();
			final String errorMessage = this.messageSource.getMessage("brotherhood.mustmatch", null, locale);
			binding.addError(new FieldError("actorForm", "confirmPassword", errorMessage));
		}
		if ((form.getEmail().endsWith("@") || form.getEmail().endsWith("@>")) && !form.getAuthority().equals(Authority.ADMIN)) {
			final Locale locale = LocaleContextHolder.getLocale();
			final String errorMessage = this.messageSource.getMessage("actor.bademail", null, locale);
			binding.addError(new FieldError("actorForm", "email", errorMessage));
		}
	}
	public Actor deconstruct(final ActorForm form, final BindingResult binding) {
		Actor res = null;
		if (form.getId() == 0)
			res = this.create(form.getAuthority());
		else {
			res = this.findOne(form.getId());
			Assert.notNull(res);
		}
		res.setAddress(form.getAddress());
		res.setMiddleName(form.getMiddleName());
		res.setEmail(form.getEmail());
		res.setName(form.getName());
		res.setPhone(form.getPhone());
		res.setPhoto(form.getPhoto());
		res.setSurname(form.getSurname());
		res.getUserAccount().setUsername(form.getUsername());
		res.getUserAccount().setPassword(form.getPassword());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority(form.getAuthority());
		authorities.add(auth);
		res.getUserAccount().setAuthorities(authorities);
		this.validator.validate(form, binding);
		return res;
	}

	public boolean containsSpam(final String s) {
		boolean res = false;
		final List<String> negativeWords = new ArrayList<>();
		negativeWords.addAll(this.configurationService.findOne().getNegativeWordsEN());
		negativeWords.addAll(this.configurationService.findOne().getNegativeWordsES());
		for (final String spamWord : negativeWords)
			if (s.contains(spamWord)) {
				System.out.println(spamWord);
				res = true;
				break;
			}
		return res;
	}
	public boolean isSpammer(final Actor a) {
		boolean res = false;
		Assert.notNull(a);
		this.serviceUtils.checkId(a.getId());
		final Actor actor = this.actorRepository.findOne(a.getId());
		Assert.notNull(actor);

		if (!res)
			for (final Message m : this.messageService.findSendedMessages(actor)) {
				res = this.containsSpam(m.getBody()) || this.containsSpam(m.getSubject());
				if (!res)

					res = this.containsSpam(m.getTags());

				else
					break;
			}

		return res;
	}
}
