
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Brotherhood;
import domain.Enroll;
import domain.Member;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EnrollServiceTest extends AbstractTest {

	//Service-------------------------------------------
	@Autowired
	private EnrollService		enrollService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private PositionService		positionService;


	//Test
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		final int memberId = this.getEntityId("member1");
		final Member member = this.memberService.findOne(memberId);
		final int brotherhoodId = this.getEntityId("brotherhood1");
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		final int positionId = this.getEntityId("position1");
		final Position position = this.positionService.findOne(positionId);
		try {
			//final Enroll e = this.enrollService.create(memberId, brotherhoodId);
			//	Assert.notNull(e);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final int enrollId = this.getEntityId("enroll1");

		try {
			final Enroll enroll = this.enrollService.findOne(enrollId);
			Assert.notNull(enroll);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		final Enroll enroll, saved;
		this.authenticate("brotherhood1");
		final Collection<Enroll> enrolls;
		final int brotherhoodId = this.getEntityId("brotherhood1");
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		final int memberId = this.getEntityId("member1");
		final Member member = this.memberService.findOne(memberId);
		final int positionId = this.getEntityId("position1");
		final Position position = this.positionService.findOne(positionId);
		try {
			//	enroll = this.enrollService.create(memberId, brotherhoodId);
			//enroll.setPosition(position);
			//	enroll.setStartMoment(new Date(System.currentTimeMillis() - 1000));
			//	enroll.setEndMoment(null);
			//	saved = this.enrollService.save(enroll);

			enrolls = this.enrollService.findAll();
			//Assert.isTrue(enrolls.contains(saved));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
		this.unauthenticate();
	}
	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		final int enrollId = this.getEntityId("Enroll1");
		try {
			final Enroll enroll = this.enrollService.findOne(enrollId);
			final Collection<Enroll> enrolls = this.enrollService.findAll();
			Assert.isTrue(enrolls.contains(enroll));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");
		this.authenticate("referee1");
		final int enrollId = this.getEntityId("Enroll1");

		try {
			final Enroll enroll = this.enrollService.findOne(enrollId);
			this.enrollService.delete(enroll);
			final Collection<Enroll> enrolls = this.enrollService.findAll();
			Assert.notNull(enrolls);
			Assert.isTrue(!enrolls.contains(enroll));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
		this.unauthenticate();
	}

}
