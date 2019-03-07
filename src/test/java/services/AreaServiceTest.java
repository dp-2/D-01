
package services;

import java.util.Collection;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Area;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AreaServiceTest extends AbstractTest {

	//Service under test ----------------------------------------

	@Autowired
	private AreaService	areaService;


	//------------------------------------------------------------

	@Test
	public void testCreate() {
		this.authenticate("brotherhood1");
		final Area a = this.areaService.create();
		a.setName("Hi");
		System.out.println(a.getName());
		Assert.notNull(a);
	}

	@Test
	public void testFindOneCorrecto() {
		Area app;
		final int idBusqueda = super.getEntityId("area1");
		app = this.areaService.findOne(idBusqueda);
		Assert.notNull(app);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIncorrecto() {
		Area app;
		final int idBusqueda = super.getEntityId("area2");
		app = this.areaService.findOne(idBusqueda);
		Assert.isNull(app);
	}

	@Test
	public void testFindAll() {
		Collection<Area> apps;

		apps = this.areaService.findAll();
		Assert.notNull(apps);
		Assert.notEmpty(apps); //porque sabemos que hemos creado algunos con el populate
	}

	@Test
	public void testSaveAreaCorrecto() {
		Area area;
		area = this.areaService.findOne(this.getEntityId("area2"));
		Assert.notNull(area);
		area.setName("Nombre del area 2");
		area = this.areaService.save(area);

		Assert.isTrue(area.getName() == ("Nombre del area 2"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSaveAreaNombreDiferente() {
		Area area;
		area = this.areaService.findOne(this.getEntityId("area2"));
		Assert.notNull(area);
		area.setName("Nombre del area 2");
		area = this.areaService.save(area);

		Assert.isTrue(area.getName() == ("nombre erroneo"));
	}

	@Test
	public void testAreaPriceStats() {
		this.authenticate("admin1");
		final Map<String, Double> result = this.areaService.statsBrotherhoodPerArea();
		System.out.println("Las Estadisticas de las areas por hermandad son :" + result);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteAreaAsignada() {
		Area area;
		area = this.areaService.findOne(this.getEntityId("area1"));
		this.areaService.delete(area);
	}
}
