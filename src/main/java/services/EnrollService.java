
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
import domain.Enroll;

@Service
@Transactional
public class EnrollService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private EnrollRepository	enrollRepository;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private ProcessionService	processionService;


	//Constructor----------------------------------------------------------------------------

	public EnrollService() {
		super();
	}

	// Simple CRUD methods -------------------------------------------------------------------
	public Enroll create(final int memberId, final int processionId) {
		final Enroll enroll = new Enroll();
		enroll.setMember(this.memberService.findOne(memberId));
		enroll.setProcession(this.processionService.findOne(processionId));

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
		this.checkPrincipal(enroll);
		Enroll result;
		enroll.setMoment(new Date(System.currentTimeMillis() - 1000));
		result = this.enrollRepository.save(enroll);

		return result;
	}
	public void delete(final Enroll enroll) {

		Assert.notNull(enroll);
		this.checkPrincipal(enroll);
		this.enrollRepository.delete(enroll);
	}
	//Other Methods-----------------------------------------------------------------
	public Boolean checkPrincipal(final Enroll enroll) {
		final UserAccount u = enroll.getMember().getUserAccount();
		Assert.isTrue(u.equals(LoginService.getPrincipal()), "este perfil no corresponde con este Miembro");
		return true;
	}
}
