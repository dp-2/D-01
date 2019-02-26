
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
		final DFloat dfloat = this.dfloatService.create();
		Assert.notNull(dfloat);
	}

	@Test
	public void testFindAll() {
		Collection<DFloat> dfloats;
		dfloats = this.dfloatService.findAll();

		Assert.notNull(dfloats);
		Assert.notEmpty(dfloats);
	}

	@Test
	public void testFindOneCorrecto() {
		DFloat dfloat;

		final int idBusqueda = super.getEntityId("dfloat1");
		dfloat = this.dfloatService.findOne(idBusqueda);
		Assert.notNull(dfloat);
	}

	@Test(expected = AssertionError.class)
	public void testFindOneIncorrecto() {
		DFloat dfloat;

		final int idBusqueda = super.getEntityId("noExiste");
		dfloat = this.dfloatService.findOne(idBusqueda);
		Assert.isNull(dfloat);
	}

	@Test
	public void testSave() {
		DFloat dfloat, saved;
		int dfloatId;
		dfloatId = this.getEntityId("dfloat1");
		dfloat = this.dfloatService.findOne(dfloatId);
		Assert.notNull(dfloat);

		this.authenticate("brotherhood1");

		dfloat.setTitle("hola");
		saved = this.dfloatService.save(dfloat);
		Assert.isTrue(saved.getTitle().equals("hola"));

		this.unauthenticate();
	}

	//TODO está mal
	@Test
	public void testDelete() {
		DFloat dfloat;
		final int idBusqueda = super.getEntityId("dfloat1");
		dfloat = this.dfloatService.findOne(idBusqueda);
		this.dfloatService.delete(dfloat);
		Assert.notNull(dfloat);

	}

}
