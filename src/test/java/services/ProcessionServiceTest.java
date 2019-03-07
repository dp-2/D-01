
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Procession;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProcessionServiceTest extends AbstractTest {

	//Service-------------------------------------------------------------------------

	@Autowired
	private ProcessionService processionService;


	//Tests---------------------------------------------------------------------------

	@Test
	public void createAndSaveProcessionPositive() {
		System.out.println("====PROCESSION TEST createAndSaveProcessionPositive()====");

		this.authenticate("brotherhood1");

		try {
			final Procession procession = this.processionService.create();
			final Date future = new Date();
			future.setYear(future.getYear() + 5);

			procession.setTitle("Title Test");
			procession.setDescription("Description Test");
			procession.setMomentOrganised(future);

			Assert.notNull(procession);

			System.out.println("--Final Procesions--");
			final List<Procession> processions = this.processionService.findProcessionsFinal();
			for (final Procession procession2 : processions)
				System.out.println(procession2.getTicker() + " " + procession2.getTitle() + " " + procession2.isFfinal());

			System.out.println("Nuestra procession que hemos guardado:");
			final Procession savedNofinal = this.processionService.save(procession);
			System.out.println(savedNofinal.getTicker() + " " + savedNofinal.getTitle() + " " + savedNofinal.isFfinal());

			System.out.println("Lo guardamos ahora en modo final:");

			final Procession savedfinal = this.processionService.save(savedNofinal);
			savedfinal.setFfinal(true);

			System.out.println("--Final Procesions--");
			final List<Procession> processions2 = this.processionService.findProcessionsFinal();
			for (final Procession procession3 : processions2)
				System.out.println(procession3.getTicker() + " " + procession3.getTitle() + " " + procession3.isFfinal());

			System.out.println("Successfully!");
		} catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Unlucky!");
		}

		this.unauthenticate();
	}

	@Test
	public void createAndSaveProcessionNegative() {
		System.out.println("====PROCESSION TEST createAndSaveProcessionNegative()====");

		this.authenticate("member1");

		try {
			final Procession procession = this.processionService.create();
			final Date future = new Date();
			future.setYear(future.getYear() + 5);

			procession.setTitle("Title Test");
			procession.setDescription("Description Test");
			procession.setMomentOrganised(future);

			Assert.notNull(procession);

			System.out.println("--Final Procesions--");
			final List<Procession> processions = this.processionService.findProcessionsFinal();
			for (final Procession procession2 : processions)
				System.out.println(procession2.getTicker() + " " + procession2.getTitle() + " " + procession2.isFfinal());

			System.out.println("Nuestra procession que hemos guardado:");
			final Procession savedNofinal = this.processionService.save(procession);
			System.out.println(savedNofinal.getTicker() + " " + savedNofinal.getTitle() + " " + savedNofinal.isFfinal());

			System.out.println("Lo guardamos ahora en modo final:");

			final Procession savedfinal = this.processionService.save(savedNofinal);
			savedfinal.setFfinal(true);

			System.out.println("--Final Procesions--");
			final List<Procession> processions2 = this.processionService.findProcessionsFinal();
			for (final Procession procession3 : processions2)
				System.out.println(procession3.getTicker() + " " + procession3.getTitle() + " " + procession3.isFfinal());

			System.out.println("Successfully!");
		} catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Unlucky!");
		}

		this.unauthenticate();
	}

	@Test
	public void deleteProcessionPositive() {
		System.out.println("====PROCESSION TEST deleteProcessionPositive()====");

		this.authenticate("brotherhood1");

		try {
			final Procession procession = this.processionService.create();
			final Date future = new Date();
			future.setYear(future.getYear() + 5);

			procession.setTitle("Title Test");
			procession.setDescription("Description Test");
			procession.setMomentOrganised(future);

			Assert.notNull(procession);

			System.out.println("Nuestra procession que hemos guardado en modo borrador:");
			final Procession savedNofinal = this.processionService.save(procession);
			System.out.println(savedNofinal.getTicker() + " " + savedNofinal.getTitle() + " " + savedNofinal.isFfinal());

			this.processionService.delete(savedNofinal);

			System.out.println("Successfully!");
		} catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Unlucky!");
		}

		this.unauthenticate();
	}

	@Test
	public void deleteProcessionNegative() {
		System.out.println("====PROCESSION TEST deleteProcessionPositive()====");

		this.authenticate("brotherhood1");

		try {
			final Procession procession = this.processionService.create();
			final Date future = new Date();
			future.setYear(future.getYear() + 5);

			procession.setTitle("Title Test");
			procession.setDescription("Description Test");
			procession.setMomentOrganised(future);

			Assert.notNull(procession);

			System.out.println("Nuestra procession que hemos guardado en modo borrador:");
			final Procession savedNofinal = this.processionService.save(procession);
			savedNofinal.setFfinal(true);
			System.out.println(savedNofinal.getTicker() + " " + savedNofinal.getTitle() + " " + savedNofinal.isFfinal());

			this.processionService.delete(savedNofinal);

			System.out.println("Successfully!");
		} catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Unlucky!");
		}

		this.unauthenticate();
	}
}
