
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EnrollRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Brotherhood;
import domain.Enroll;
import domain.Member;
import domain.Position;

@Service
@Transactional
public class EnrollService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private EnrollRepository	enrollRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private PositionService		positionService;


	//Constructor----------------------------------------------------------------------------

	public EnrollService() {
		super();
	}

	// Simple CRUD methods -------------------------------------------------------------------
	public Enroll create(final int brotherhoodId) {
		final Enroll enroll = new Enroll();
		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Member member = this.memberService.findOne(a.getId());
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		final Position position = this.positionService.findAll().get(0);

		enroll.setStatus("PENDING");
		enroll.getStatus();
		enroll.setStartMoment(new Date(System.currentTimeMillis() - 1000));
		enroll.setMember(member);
		enroll.setBrotherhood(brotherhood);
		enroll.setPosition(position);
		return enroll;
	}
	public Collection<Enroll> findAll() {
		Collection<Enroll> enrolls;

		enrolls = this.enrollRepository.findAll();
		Assert.notNull(enrolls);

		return enrolls;
	}
	public Enroll findOne(final int enrollId) {
		Enroll enroll;
		enroll = this.enrollRepository.findOne(enrollId);
		Assert.notNull(enrollId);

		return enroll;
	}

	public Enroll save(final Enroll enroll) {
		Assert.notNull(enroll);
		Enroll result;
		if (enroll.getStatus() == null)
			enroll.setStatus("PENDING");
		enroll.setStartMoment(new Date(System.currentTimeMillis() - 1000));
		result = this.enrollRepository.save(enroll);

		return result;
	}
	public void delete(final Enroll enroll) {
		Assert.notNull(enroll);
		this.checkPrincipal(enroll);
		Enroll result;

		enroll.setEndMoment(new Date(System.currentTimeMillis() - 1000));
		result = this.enrollRepository.save(enroll);
	}
	//Other Methods-----------------------------------------------------------------
	public Boolean checkPrincipal(final Enroll enroll) {
		final UserAccount u = enroll.getMember().getUserAccount();
		Assert.isTrue(u.equals(LoginService.getPrincipal()), "este perfil no corresponde con esta miembro");
		return true;
	}

	public Collection<Enroll> findEnrollByMember(final int memberId) {
		return this.enrollRepository.findEnrollByMember(memberId);
	}

	public Collection<Enroll> findEnrollByBrotherhood(final int brotherhoodId) {
		return this.enrollRepository.findEnrollByBrotherhood(brotherhoodId);
	}

	public Collection<Enroll> findEnrollsPendingByBrotherhood(final int brotherhoodId) {
		return this.enrollRepository.findEnrollsPendingByBrotherhood(brotherhoodId);
	}

	public Collection<Enroll> findEnrollsAprovedByBrotherhood(final int brotherhoodId) {
		return this.enrollRepository.findEnrollsAprovedByBrotherhood(brotherhoodId);
	}

	public Collection<Brotherhood> findBrotherhoodByMember(final int memberId) {
		return this.enrollRepository.findBrotherhoodByMember(memberId);
	}

	public Collection<Brotherhood> findBrotherhoodByMemberId(final int memberId) {
		return this.enrollRepository.findBrotherhoodByMemberId(memberId);
	}

	public Enroll goOut(final int enrollId) {
		Enroll enroll;
		enroll = this.enrollRepository.findOne(enrollId);
		final Date fechaActual = new Date(System.currentTimeMillis() - 1000);

		enroll.setEndMoment(fechaActual);
		enroll.setStatus("OUT");
		enroll = this.enrollRepository.save(enroll);
		return enroll;
	}
}
