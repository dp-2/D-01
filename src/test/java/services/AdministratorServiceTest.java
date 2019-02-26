
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
import domain.Administrator;
import domain.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MemberService			memberService;


	@Test
	public void testCreate() {
		final Administrator a = this.administratorService.create();
		Assert.notNull(a);
	}
	@Test
	public void testFindOneCorrecto() {
		Administrator admin;

		final int idBusqueda = super.getEntityId("administrator1");
		admin = this.administratorService.findOne(idBusqueda);
		Assert.notNull(admin);
	}

	@Test(expected = AssertionError.class)
	public void testFindOneIncorrecto() {
		Administrator admin;

		final int idBusqueda = super.getEntityId("noExiste");
		admin = this.administratorService.findOne(idBusqueda);
		Assert.isNull(admin);
	}

	@Test
	public void testFindAll() {
		Collection<Administrator> admins;

		admins = this.administratorService.findAll();
		Assert.notNull(admins);
		Assert.notEmpty(admins); //porque sabemos que hemos creado algunos con el populate
	}

	@Test
	public void testSaveCorrecto() {

		Administrator administrator, saved;
		int adminId;
		adminId = this.getEntityId("administrator1");
		administrator = this.administratorService.findOne(adminId);
		Assert.notNull(administrator);

		this.authenticate("admin1");

		administrator.setSurname("Pablo");
		saved = this.administratorService.save(administrator);
		Assert.isTrue(saved.getSurname().equals("Pablo"));

		this.unauthenticate();
	}

	@Test
	public void banActorSpammer() {
		this.authenticate("admin1");
		Member member;
		final int idBusqueda = super.getEntityId("member2");
		member = this.memberService.findOne(idBusqueda);
		this.administratorService.banActor(member);
		Assert.isTrue(member.getBanned() == true);
	}

	@Test
	public void banActorWithBadScore() {
		this.authenticate("admin1");
		Member member;
		final int idBusqueda = super.getEntityId("member1");
		member = this.memberService.findOne(idBusqueda);
		this.administratorService.banActor(member);
		Assert.isTrue(member.getBanned() == true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void banActorNonSpammerAndGoodScore() {
		this.authenticate("admin1");
		Member member;
		final int idBusqueda = super.getEntityId("administrator2");
		member = this.memberService.findOne(idBusqueda);
		this.administratorService.banActor(member);
		Assert.isTrue(member.getBanned() == true);
	}

	@Test
	public void isPrincipalAdmin() {
		this.authenticate("admin1");

		Assert.isTrue(this.administratorService.isPrincipalAdmin());
	}

}
