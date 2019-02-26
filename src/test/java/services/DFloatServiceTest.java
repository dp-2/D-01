
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.DFloat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class DFloatServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private DFloatService	dfloatService;


	@Test
	public void testCreate() {
		this.authenticate("brotherhood1");
		System.out.println("------------------1");
		final DFloat dfloat = this.dfloatService.create();
		Assert.notNull(dfloat);
	}
}
