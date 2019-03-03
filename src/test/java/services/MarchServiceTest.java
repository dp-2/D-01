
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.March;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MarchServiceTest extends AbstractTest {

	// Service ------------------------------
	@Autowired
	private MarchService	marchService;


	// Test
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		this.authenticate("member1");
		final int memberId = this.getEntityId("member1");
		final int processionId = this.getEntityId("procession1");
		try {
			final March march = this.marchService.create(processionId, memberId);

			march.setStatus("PENDING");
			march.setReason("");

			Assert.notNull(march);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		final March march, saved;

		final Collection<March> marchs;

		this.authenticate("member1");
		final int memberId = this.getEntityId("member1");
		final int processionId = this.getEntityId("procession1");
		final List<Integer> a = new ArrayList<>();
		try {
			march = this.marchService.create(processionId, memberId);
			march.setReason("aaa");
			march.setStatus("PENDING");
			march.setLocation(a);
			saved = this.marchService.save(march);
			marchs = this.marchService.findAll();
			Assert.isTrue(marchs.contains(saved));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

	}
	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final int marchId = this.getEntityId("march1");

		try {
			final March march = this.marchService.findOne(marchId);
			Assert.notNull(march);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		try {
			final int march1 = this.getEntityId("march1");
			final March march = this.marchService.findOne(march1);
			final Collection<March> marchs = this.marchService.findAll();
			Assert.isTrue(marchs.contains(march));
			System.out.println("¡Exito!");
		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");
		this.authenticate("member1");
		final int marchId = this.getEntityId("march1");

		try {
			final March march = this.marchService.findOne(marchId);
			Assert.isTrue(march.getStatus().equals("PENDING"));
			this.marchService.delete(march);

			final Collection<March> marchs = new ArrayList<>(this.marchService.findAll());
			Assert.isTrue(!marchs.contains(march));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
		this.unauthenticate();
	}

}
