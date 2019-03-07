
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Configuration;
import domain.Message;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	//--------------Managed repository---------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	//-------------- Supporting Services-----------------------

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			messageService;

	@Autowired(required = false)
	private Validator				validator;

	@Autowired
	private MessageSource			messageSource;


	// --------------------------Constructor-----------------------

	public AdministratorService() {
		super();
	}

	// --------------------CRUD methods----------------------------

	public Administrator create() {
		Administrator result;
		result = new Administrator();
		result.setBanned(false);
		result.setSpammer(false);
		//establezco ya su tipo de userAccount porque no va a cambiar

		return result;

	}

	public Administrator save(final Administrator administrator) {
		//comprobamos que el customer que nos pasan no sea nulo
		Assert.notNull(administrator);
		Boolean isCreating = null;

		//Si el admin que estamos guardando es nuevo (no está en la base de datos) le ponemos todos sus atributos vacíos
		if (administrator.getId() == 0) {
			isCreating = true;
			administrator.setSpammer(false);

			//comprobamos que ningún actor resté autenticado (ya que ningun actor puede crear los customers)
			//this.serviceUtils.checkNoActor();

		} else {
			isCreating = false;
			//comprobamos que su id no sea negativa por motivos de seguridad
			this.serviceUtils.checkIdSave(administrator);

			//este admin será el que está en la base de datos para usarlo si estamos ante un admin que ya existe
			Administrator adminDB;
			Assert.isTrue(administrator.getId() > 0);

			//cogemos el admin de la base de datos
			adminDB = this.administratorRepository.findOne(administrator.getId());

			administrator.setSpammer(adminDB.getSpammer());
			administrator.setUserAccount(adminDB.getUserAccount());

			//Comprobamos que el actor sea un admin
			this.serviceUtils.checkAuthority("ADMIN");
			//esto es para ver si el actor que está logueado es el mismo que se está editando
			//this.serviceUtils.checkActor(administrator);

		}
		if ((!administrator.getPhone().startsWith("+")) && StringUtils.isNumeric(administrator.getPhone()) && administrator.getPhone().length() > 3) {
			final Configuration configuration = this.configurationService.findOne();
			administrator.setPhone(configuration.getCountryCode() + administrator.getPhone());
		}
		Administrator res;
		//le meto al resultado final el admin que he ido modificando anteriormente
		res = this.administratorRepository.save(administrator);
		this.flush();
		if (isCreating)
			this.boxService.createIsSystemBoxs(res);
		return res;
	}

	//----------------------------

	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> res;
		res = this.administratorRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	// -------------------------Other business methods ------------------------------

	public void flush() {
		this.administratorRepository.flush();
	}

	public void banActor(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getSpammer() == true || a.getScore() < -0.5);
		this.serviceUtils.checkAuthority("ADMIN");
		a.setBanned(true);
		this.actorService.save(a);

	}
	public void unBanActor(final Actor a) {
		Assert.notNull(a);
		this.serviceUtils.checkAuthority("ADMIN");
		a.setBanned(false);
		this.actorService.save(a);

		//	//Ban actor
		//	public Boolean banActor(final Administrator a) {
		//		Boolean banned = false;
		//		Assert.notNull(a);
		//		//if (checkBan(a)){
		//		a.setBanned(true);
		//		//}
		//		this.save(a);
		//		return banned;
		//	}
		//
		//	//unban actor
		//	public Boolean unbanActor(final Administrator a) {
		//		Boolean banned = true;
		//		Assert.notNull(a);
		//		Assert.isTrue(a.getBanned());
		//		a.setBanned(false);
		//		this.save(a);
		//		return banned;
		//	}
		//
		//	//comprueba que tenga spam,
		//
		////	public Boolean checkBan(final Actor a){
		////		Boolean res= false;
		////
		////	}
		//
		//	public
	}

	public boolean isPrincipalAdmin() {
		boolean res = false;
		final UserAccount u = LoginService.getPrincipal();
		final Authority a = new Authority();
		a.setAuthority("ADMIN");
		if (u.getAuthorities().contains(a))
			res = true;
		return res;
	}

	public void generateAllScore() {
		this.serviceUtils.checkAuthority(Authority.ADMIN);
		final Collection<Actor> res = this.actorService.findAllTypes();
		for (final Actor a : res) {
			final Double d = this.generateScore(a);
			a.setScore(d);
			this.actorService.save(a);
		}

	}

	public void generateAllSpammers() {
		this.serviceUtils.checkAuthority(Authority.ADMIN);
		final Collection<Actor> res = this.actorService.findAllTypes();
		for (final Actor a : res) {
			final Boolean spammer = this.actorService.isSpammer(a);
			System.out.println(a.getUserAccount().getUsername() + "-----spammer----->" + spammer);
			a.setSpammer(spammer);
			this.actorService.save(a);
		}

	}

	public Double generateScore(final Actor a) {
		this.serviceUtils.checkAuthority(Authority.ADMIN);
		Double res = 0.0;
		Double cont = 0.0;

		final Collection<Message> messages = new ArrayList<Message>();
		final Collection<String> buenasEN = this.configurationService.findOne().getPositiveWordsEN();
		final Collection<String> buenasES = this.configurationService.findOne().getPositiveWordsES();
		final Collection<String> malasEN = this.configurationService.findOne().getNegativeWordsEN();
		final Collection<String> malasES = this.configurationService.findOne().getNegativeWordsES();
		messages.addAll(this.messageService.findReceivedMessages(a));
		messages.addAll(this.messageService.findSendedMessages(a));
		if (messages.isEmpty())
			return res;
		else
			for (final Message m : messages) {
				final String[] palabras = m.getBody().split(" ");
				for (final String word : palabras) {

					if (buenasEN.contains(word) || buenasES.contains(word))
						res++;
					if (malasEN.contains(word) || malasES.contains(word))
						res--;
					cont++;
				}

			}

		return res / cont;
	}

	public AdministratorForm construct(final Administrator b) {
		final AdministratorForm res = new AdministratorForm();
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
		return res;
	}

	public Administrator deconstruct(final AdministratorForm form, final BindingResult binding) {
		Administrator res = null;
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
			binding.addError(new FieldError("administratorForm", "accept", errorMessage));
		}
		if (!form.getConfirmPassword().equals(form.getPassword())) {
			final Locale locale = LocaleContextHolder.getLocale();
			final String errorMessage = this.messageSource.getMessage("brotherhood.mustmatch", null, locale);
			binding.addError(new FieldError("admnistratorForm", "confirmPassword", errorMessage));
		}
		if (form.getId() == 0)
			res = this.create();
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
		auth.setAuthority(Authority.BROTHERHOOD);
		authorities.add(auth);
		res.getUserAccount().setAuthorities(authorities);
		this.validator.validate(form, binding);
		return res;
	}

}
