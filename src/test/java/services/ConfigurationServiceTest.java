
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Configuration;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	//Service----------------------------------------------------------------------

	@Autowired
	private ConfigurationService configurationService;

	//Test-------------------------------------------------------------------------


	@Test
	public void findConfiguration() {
		System.out.println("====CONFIGURATION TEST findConfiguration()====");

		this.authenticate("admin1");

		try {
			final Configuration configuration = this.configurationService.findOne();
			System.out.println("Name System: " + configuration.getNameSys());
			System.out.println("Cache Finder: " + configuration.getCacheFinder());
			System.out.println("Mensaje de Bienvenida: " + configuration.getWelcomeMessageES());

			System.out.println("Successfully!");
		} catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Unlucky!");
		}

		this.unauthenticate();
	}

	@Test
	public void saveConfiguration() {
		System.out.println("====CONFIGURATION TEST saveConfiguration()====");

		this.authenticate("admin2");
		try {
			final Configuration configuration = this.configurationService.findOne();
			System.out.println("Name System: " + configuration.getNameSys());
			System.out.println("Cache Finder: " + configuration.getCacheFinder());
			System.out.println("Mensaje de Bienvenida: " + configuration.getWelcomeMessageES());

			System.out.println("----MODIFICADO----");
			configuration.setNameSys("Name System Test");
			configuration.setCacheFinder(18);
			configuration.setWelcomeMessageES("Mensaje de Bienvenida de Prueba");

			final Configuration saved = this.configurationService.save(configuration);

			System.out.println("Name System: " + saved.getNameSys());
			System.out.println("Cache Finder: " + saved.getCacheFinder());
			System.out.println("Mensaje de Bienvenida: " + saved.getWelcomeMessageES());

			System.out.println("Successfully!");
		} catch (final Exception e) {
			System.out.println("Error: " + e.getMessage());
			System.out.println("Unlucky!");
		}
	}

}
