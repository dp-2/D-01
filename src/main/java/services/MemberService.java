
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.MemberRepository;
import security.Authority;
import security.UserAccount;
import domain.Brotherhood;
import domain.Configuration;
import domain.Member;
import forms.BrotherhoodForm;
import forms.MemberForm;

@Service
@Transactional
public class MemberService {

	// Managed repository --------------------------------------

	@Autowired
	private MemberRepository		memberRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired(required = false)
	private Validator				validator;

	@Autowired
	private MessageSource			messageSource;


	// Constructors -----------------------------------------------------------

	public MemberService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Member create() {
		Member result;
		result = new Member();
		//establezco ya su tipo de userAccount porque no va a cambiar
		result.setUserAccount(new UserAccount());
		final Authority authority = new Authority();
		authority.setAuthority(Authority.MEMBER);
		result.getUserAccount().addAuthority(authority);
		//los atributos que no pueden estar vacíos
		result.setBanned(false);
		result.setSpammer(false);
		result.setScore(0.0);
		return result;
	}

	public Member findOne(final int customerId) {
		Member res;

		res = this.memberRepository.findOne(customerId);
		Assert.notNull(res);

		return res;

	}

	public Collection<Member> findAll() {
		Collection<Member> res;

		res = this.memberRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Member save(final Member member) {
		//comprobamos que el customer que nos pasan no sea nulo
		Assert.notNull(member);
		Boolean isCreating = null;

		Assert.isTrue(!(member.getEmail().endsWith("@") || member.getEmail().endsWith("@>")));

		if (member.getId() == 0) {
			isCreating = true;
			member.setSpammer(false);

			//comprobamos que ningún actor resté autenticado (ya que ningun actor puede crear los customers)
			//this.serviceUtils.checkNoActor();

		} else {
			isCreating = false;
			//comprobamos que su id no sea negativa por motivos de seguridad
			this.serviceUtils.checkIdSave(member);

			//este customer será el que está en la base de datos para usarlo si estamos ante un customer que ya existe
			Member memberDB;
			Assert.isTrue(member.getId() > 0);

			//cogemos el customer de la base de datos
			memberDB = this.memberRepository.findOne(member.getId());

			member.setSpammer(memberDB.getSpammer());
			member.setUserAccount(memberDB.getUserAccount());

			//Comprobamos que el actor sea un Member
			final String[] auths = new String[] {
				"MEMBER", "ADMIN"
			};
			this.serviceUtils.checkAnyAuthority(auths);
			//esto es para ver si el actor que está logueado es el mismo que se está editando
			Assert.isTrue(this.serviceUtils.checkActorBoolean(member) || this.serviceUtils.checkAuthorityBoolean("ADMIN"));

		}

		if ((!member.getPhone().startsWith("+")) && StringUtils.isNumeric(member.getPhone()) && member.getPhone().length() > 3) {
			final Configuration confs = this.configurationService.findOne();
			member.setPhone(confs.getCountryCode() + member.getPhone());
		}
		Member res;
		//le meto al resultado final el customer que he ido modificando anteriormente
		res = this.memberRepository.save(member);
		this.flush();
		if (isCreating)
			this.boxService.createIsSystemBoxs(res);
		return res;
	}
	//no realizamos el delete porque no se va a borrar nunca un customer

	// Other business methods -------------------------------------------------

	//	public void banActor(final Member a) {
	//		Assert.notNull(a);
	//		this.serviceUtils.checkAuthority("ADMIN");
	//		a.getUserAccount().setBanned(true);
	//		this.customerRepository.save(a);
	//
	//	}

	//	public void unBanActor(final Customer a) {
	//		Assert.notNull(a);
	//		this.serviceUtils.checkAuthority("ADMIN");
	//		a.getUserAccount().setBanned(false);
	//		this.customerRepository.save(a);
	//
	//	}

	public void flush() {
		this.memberRepository.flush();
	}

	public Member findMemberByUserAcountId(final int userAccountId) {
		return this.memberRepository.findMemberByUserAcountId(userAccountId);
	}

	public Member findMemberByEnrollId(final int enrollId) {
		return this.memberRepository.findMemberByEnrollId(enrollId);
	}

	public List<Member> listMembersByBrotherhood(final int brotherhoodId) {
		return this.memberRepository.listMembersByBrotherhood(brotherhoodId);
	}

	public Map<String, Double> membersBrotherhoodStats() {
		final Double max = this.memberRepository.maxMembersPerBrotherhood();
		final Double min = this.memberRepository.minMembersPerBrotherhood();
		final Double avg = this.memberRepository.avgMembersPerBrotherhood();
		final Double stdev = this.memberRepository.stdevMembersPerBrotherhood();

		final Map<String, Double> res = new HashMap<>();

		res.put("MIN", min);
		res.put("MAX", max);
		res.put("AVG", avg);
		res.put("STD", stdev);

		return res;
	}

	public BrotherhoodForm construct(final Brotherhood b) {
		final BrotherhoodForm res = new BrotherhoodForm();
		res.setEmail(b.getEmail());
		res.setName(b.getName());
		res.setPhone(b.getPhone());
		res.setPhoto(b.getPhoto());
		res.setPictures(b.getPictures());
		res.setSurname(b.getSurname());
		res.setTitle(b.getTitle());
		res.setUsername(b.getUserAccount().getUsername());
		res.setId(b.getId());
		res.setVersion(b.getVersion());
		res.setMiddleName(b.getMiddleName());
		res.setAddress(b.getAddress());
		return res;
	}

	public Member deconstruct(final MemberForm form, final BindingResult binding) {
		Member res = null;
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
			binding.addError(new FieldError("brotherhoodForm", "accept", errorMessage));
		}
		if (!form.getConfirmPassword().equals(form.getPassword())) {
			final Locale locale = LocaleContextHolder.getLocale();
			final String errorMessage = this.messageSource.getMessage("brotherhood.mustmatch", null, locale);
			binding.addError(new FieldError("brotherhoodForm", "confirmPassword", errorMessage));
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
