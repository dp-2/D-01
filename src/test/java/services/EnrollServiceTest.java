
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;

import domain.Actor;
import domain.Brotherhood;
import domain.Enroll;
import domain.Member;
import domain.SocialProfile;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EnrollServiceTest extends AbstractTest {

	//Service-------------------------------------------
	@Autowired
	private EnrollService	enrollService;
	
	@Autowired
	private MemberService	memberService;
	
	@Autowired
	private BrotherhoodService	brotherhoodService;


	//Test
	@Test
	public void testCreate(){
		System.out.println("========== testCreate() ==========");
		final int memberId = this.getEntityId("member1");
		final Member member = this.memberService.findOne(memberId);
		final int brotherhoodId = this.getEntityId("brotherhood1");
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		try {
			final Enroll e = this.enrollService.create(memberId, brotherhoodId);
//			e.setEndMoment(new Date());
//			e.setPosition("Position1");			
			Assert.notNull(e);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
		
	}

@Test
public void testFindOne() {
	System.out.println("========== testFindOne() ==========");
	final int profileId = this.getEntityId("profile1");

	try {
		final SocialProfile profile = this.profileService.findOne(profileId);
		Assert.notNull(profile);

		System.out.println("¡Exito!");

	} catch (final Exception e) {
		System.out.println("¡Fallo," + e.getMessage() + "!");
	}

}
@Test
public void testSave() {
	System.out.println("========== testSave() ==========");
	SocialProfile profile, saved;
	this.authenticate("member1");
	final Collection<SocialProfile> profiles;
	final int actorId = this.getEntityId("member1");
	final Actor actor = this.actorService.findOne(actorId);
	try {
		profile = this.profileService.create(actorId);
		profile.setNameSN("Antonia");
		profile.setLinkSN("http://profile10.com");
		profile.setNick("Antoñita97");
		profile.setActor(actor);
		profile.setNick("piwflow2");
		saved = this.profileService.save(profile);

		profiles = this.profileService.findAll();
		Assert.isTrue(profiles.contains(saved));
		System.out.println("¡Exito!");

	} catch (final Exception e) {
		System.out.println("¡Fallo," + e.getMessage() + "!");
	}
	this.unauthenticate();
}

@Test
public void testFindAll() {
	System.out.println("========== testFindAll() ==========");
	final int profileId = this.getEntityId("socialProfile1");
	try {
		final SocialProfile profile = this.profileService.findOne(profileId);
		final Collection<SocialProfile> profiles = this.profileService.findAll();
		Assert.isTrue(profiles.contains(profile));
		System.out.println("¡Exito!");

	} catch (final Exception e) {
		System.out.println("¡Fallo," + e.getMessage() + "!");
	}

}

@Test
public void testDelete() {
	System.out.println("========== testDelete() ==========");
	this.authenticate("referee1");
	final int profileId = this.getEntityId("socialProfile1");

	try {
		final SocialProfile profile = this.profileService.findOne(profileId);
		this.profileService.delete(profile);
		final Collection<SocialProfile> profiles = this.profileService.findAll();
		Assert.notNull(profiles);
		Assert.isTrue(!profiles.contains(profile));

		System.out.println("¡Exito!");

	} catch (final Exception e) {
		System.out.println("¡Fallo," + e.getMessage() + "!");
	}
	this.unauthenticate();
}

}