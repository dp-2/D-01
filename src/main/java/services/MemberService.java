
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MemberRepository;
import security.Authority;
import security.UserAccount;
import domain.Configuration;
import domain.Member;

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
		//los atributos que no pueden estar vac�os
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

			//comprobamos que ning�n actor rest� autenticado (ya que ningun actor puede crear los customers)
			//this.serviceUtils.checkNoActor();

		} else {
			isCreating = false;
			//comprobamos que su id no sea negativa por motivos de seguridad
			this.serviceUtils.checkIdSave(member);

			//este customer ser� el que est� en la base de datos para usarlo si estamos ante un customer que ya existe
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
			//esto es para ver si el actor que est� logueado es el mismo que se est� editando
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

}
