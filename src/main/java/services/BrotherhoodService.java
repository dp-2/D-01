
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Brotherhood;
import domain.Enroll;
import domain.Member;
import domain.Url;
import forms.BrotherhoodForm;

@Service
@Transactional
public class BrotherhoodService {

	// Repository

	@Autowired
	private BrotherhoodRepository	repository;

	// Services

	@Autowired
	private ActorService			actorService;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private UserAccountRepository	userAccountRepository;
	@Autowired
	private EnrollService			enrollService;

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired(required = false)
	private Validator				validator;
	@Autowired
	private MessageSource			messageSource;


	public Brotherhood findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Brotherhood> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Brotherhood> findAll() {
		return this.repository.findAll();
	}

	public Brotherhood create() {
		this.serviceUtils.checkNoActor();
		final Brotherhood res = new Brotherhood();
		res.setBanned(false);
		res.setSpammer(false);
		res.setEstablishedMoment(new Date(System.currentTimeMillis() - 1000));
		res.setPictures(new ArrayList<Url>());
		res.setUserAccount(new UserAccount());
		res.setScore(0.);
		return res;
	}

	public Brotherhood save(final Brotherhood b) {
		final Brotherhood brotherhood = (Brotherhood) this.serviceUtils.checkObjectSave(b);
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(b.getUserAccount().getPassword(), null);
		if (b.getId() == 0) {
			this.serviceUtils.checkNoActor();
			b.setBanned(false);
			b.setSpammer(false);
			b.setEstablishedMoment(new Date(System.currentTimeMillis() - 1000));
			b.setScore(0.);
			b.getUserAccount().setPassword(hash);
		} else {
			this.serviceUtils.checkAnyAuthority(new String[] {
				Authority.ADMIN, Authority.BROTHERHOOD
			});
			if (this.actorService.findPrincipal() instanceof Brotherhood) {
				this.serviceUtils.checkActor(brotherhood);
				b.setBanned(brotherhood.getBanned());
				if (brotherhood.getUserAccount().getPassword() != b.getUserAccount().getPassword())
					b.getUserAccount().setPassword(hash);
			} else {
				b.setEmail(brotherhood.getEmail());
				b.setName(brotherhood.getName());
				b.setPhone(brotherhood.getPhone());
				b.setPhoto(brotherhood.getPhoto());
				b.setPictures(brotherhood.getPictures());
				b.setSurname(brotherhood.getSurname());
				b.setTitle(brotherhood.getTitle());
				b.setUserAccount(brotherhood.getUserAccount());
			}
			b.setEstablishedMoment(brotherhood.getEstablishedMoment());
		}
		final UserAccount userAccount = this.userAccountRepository.save(b.getUserAccount());
		brotherhood.setUserAccount(userAccount);
		final Brotherhood res = this.repository.save(b);
		this.boxService.createIsSystemBoxs(res);
		return res;
	}

	public void delete(final Brotherhood b) {
		final Brotherhood brotherhood = (Brotherhood) this.serviceUtils.checkObject(b);
		this.serviceUtils.checkActor(brotherhood);
		this.repository.delete(brotherhood);
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
		res.setPassword(b.getUserAccount().getPassword());
		return res;
	}

	public Brotherhood deconstruct(final BrotherhoodForm form, final BindingResult binding) {
		Brotherhood res = null;
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
		if (!form.getConfirmPassword().equals(form.getPassword()))
			binding.addError(new FieldError("brotherhoodForm", "confirmPassword", "brotherhood.brotherhood.mustmatch"));
		if (form.getId() == 0)
			res = this.create();
		else {
			res = this.findOne(form.getId());
			Assert.notNull(res);
		}
		res.setEmail(form.getEmail());
		res.setName(form.getName());
		res.setPhone(form.getPhone());
		res.setPhoto(form.getPhoto());
		res.setPictures(form.getPictures());
		res.setSurname(form.getSurname());
		res.setTitle(form.getTitle());
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
	public Brotherhood findBrotherhoodByUserAcountId(final int userAccountId) {
		return this.repository.findBrotherhoodByUserAcountId(userAccountId);
	}
	//TODO se podria hacer por query pero no lo consigo
	public List<Member> listMembersByBrotherhoodId(final Integer bh) {
		final Brotherhood brother = this.findBrotherhoodByUserAcountId(bh);
		final List<Member> members = new ArrayList<>();
		final List<Enroll> enrolls = (List<Enroll>) this.enrollService.findAll();
		for (final Enroll e : enrolls)
			if (e.getBrotherhood() == brother)
				members.add(e.getMember());
		return members;

	}
}
