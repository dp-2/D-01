
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Area;
import domain.Brotherhood;
import repositories.AreaRepository;
import security.LoginService;

@Service
@Transactional
public class AreaService {

	//Managed Repository

	@Autowired
	private AreaRepository		areaRepository;

	//Supporting Service
	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Simple CRUD methods
	public Area create() {
		Area area;
		area = new Area();
		return area;
	}
	public Collection<Area> findAll() {
		return this.areaRepository.findAll();
	}

	public Area findOne(final int areaId) {
		return this.areaRepository.findOne(areaId);
	}

	public Area save(final Area area) {
		Assert.notNull(area);

		Area result;

		result = this.areaRepository.save(area);

		return result;
	}

	public void delete(final Area area) {
		Actor actor;
		actor = this.actorService.findByUserAccount(LoginService.getPrincipal());

		Assert.notNull(area);
		Assert.isTrue(area.getBrotherhood() == null);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains("ADMIN"));
		this.areaRepository.delete(area);
	}

	public void assign(final Area area) {

		Assert.isTrue(area.getBrotherhood() == null);
		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Brotherhood bro = this.brotherhoodService.findOne(a.getId());
		area.setBrotherhood(bro);
		this.areaRepository.save(area);
	}
	//--------------Other Methods-------------------------------

	public Map<String, Double> statsBrotherhoodPerArea() {
		final Double min = this.areaRepository.minHermandadesPorArea();
		final Double count = this.areaRepository.countHermandadesPorArea();
		final Double max = this.areaRepository.maxHermandadesPorArea();
		final Double avg = this.areaRepository.avgHermandadesPorArea();
		final Double stdev = this.areaRepository.stddevHermandadesPorArea();

		final Map<String, Double> res = new HashMap<>();

		res.put("COUNT", count);
		res.put("MIN", min);
		res.put("MAX", max);
		res.put("AVG", avg);
		res.put("STD", stdev);
		return res;
	}
	public Area findAreaByBrotherhoodId(final int brotherhoodId) {
		return this.areaRepository.findAreaByBrotherhoodId(brotherhoodId);
	}

	public Double avgHermandadesPorArea() {
		return this.areaRepository.avgHermandadesPorArea();
	}

	public Double minHermandadesPorArea() {
		return this.areaRepository.minHermandadesPorArea();
	}
	public Double maxHermandadesPorArea() {
		return this.areaRepository.maxHermandadesPorArea();
	}
	public Double stddevHermandadesPorArea() {
		return this.areaRepository.stddevHermandadesPorArea();
	}

}
