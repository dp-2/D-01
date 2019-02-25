
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
import domain.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MemberServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private MemberService	memberService;


	//test-------------------------------------------------------------------

	@Test
	public void createCorrecto() {
		final Member member = this.memberService.create();
		Assert.notNull(member);
	}

	@Test
	public void testSaveCorrecto() {

		Member member, saved;
		int memberId;
		memberId = this.getEntityId("member1");
		member = this.memberService.findOne(memberId);
		Assert.notNull(member);

		this.authenticate("member1");

		member.setSurname("otro");
		saved = this.memberService.save(member);
		Assert.isTrue(saved.getSurname().equals("otro"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveIncorrecto() {

		Member member, saved;
		int memberId;
		memberId = this.getEntityId("member1");
		member = this.memberService.findOne(memberId);
		Assert.notNull(member);

		this.authenticate("member1");

		member.setEmail(null);
		Assert.notNull(member.getEmail());
		saved = this.memberService.save(member);
		Assert.isTrue(saved.getSurname().equals("otro"));
	}

	@Test
	public void testFindOneCorrecto() {
		Member member;

		final int idBusqueda = super.getEntityId("member1");
		member = this.memberService.findOne(idBusqueda);
		Assert.notNull(member);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		Member member;

		final int idBusqueda = super.getEntityId("member2");
		member = this.memberService.findOne(idBusqueda);
		Assert.isTrue(member.getName().equals("member2"));
		Assert.isNull(member);
	}
	@Test
	public void testFindAll() {
		Collection<Member> members;

		members = this.memberService.findAll();
		Assert.notNull(members);
		Assert.notEmpty(members); //porque sabemos que hemos creado algunos con el populate
	}

	//---------------------Other Methods-----------------------------------

}
