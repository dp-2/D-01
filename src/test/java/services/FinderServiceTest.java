
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Finder;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	//Service----------------------------------------------------------------------

	@Autowired
	private FinderService finderService;

	//Test-------------------------------------------------------------------------


	@Test
	public void findFinderPositive() {
		System.out.println("====FINDER TEST findFinderPositive()====");

		this.authenticate("member1");

		try {
			final Finder finder = this.finderService.findOneByPrincipal();
			System.out.println("Keyword: " + finder.getKeyword());

			System.out.println("Successfully!");
		} catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Unlucky!");
		}

		this.unauthenticate();
	}

	@Test
	public void saveFinderPositive() {
		System.out.println("====FINDER TEST saveFinderPositive()====");

		this.authenticate("member1");
		try {
			final Finder finder = this.finderService.findOneByPrincipal();
			System.out.println("id: " + finder.getId());
			System.out.println("Keyword: " + finder.getKeyword());

			System.out.println("----MODIFICADO----");
			finder.setKeyword("Test");

			final Finder saved = this.finderService.save(finder);

			System.out.println("id: " + saved.getId());
			System.out.println("Keyword: " + saved.getKeyword());

			System.out.println("Successfully!");
		} catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Unlucky!");
		}
	}

	@Test
	public void findFinderNegative() {
		System.out.println("====FINDER TEST findFinderNegative()====");

		this.authenticate("admin1");

		try {
			final Finder finder = this.finderService.findOneByPrincipal();
			System.out.println("Keyword: " + finder.getKeyword());

			System.out.println("Successfully!");
		} catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Unlucky!");
		}

		this.unauthenticate();
	}

	@Test
	public void saveFinderNegative() {
		System.out.println("====FINDER TEST saveFinderNegative()====");

		this.authenticate("admin2");
		try {
			final Finder finder = this.finderService.findOneByPrincipal();
			System.out.println("id: " + finder.getId());
			System.out.println("Keyword: " + finder.getKeyword());

			System.out.println("----MODIFICADO----");
			finder.setKeyword("Test");
			final Date date = new Date();
			date.setYear(date.getYear() + 5);

			final Date date1 = new Date();
			date1.setYear(date1.getYear() - 5);

			finder.setMinDate(date);
			finder.setMaxDate(date1);

			final Finder saved = this.finderService.save(finder);

			System.out.println("id: " + saved.getId());
			System.out.println("Keyword: " + saved.getKeyword());

			System.out.println("Successfully!");
		} catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Unlucky!");
		}
	}

}
