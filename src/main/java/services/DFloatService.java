
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DFloatRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.DFloat;

@Service
@Transactional
public class DFloatService {

	//Managed Repository

	@Autowired
	private DFloatRepository	dfloatRepository;
	//Supporting Service

	@Autowired
	ActorService				actorService;


	// Simple CRUD methods
	public DFloat create() {
		DFloat res;
		Brotherhood brotherhood;
		brotherhood = (Brotherhood) this.actorService.findByUserAccount(LoginService.getPrincipal());
		res = new DFloat();
		res.setBrotherhood(brotherhood);

		return res;
	}

	public Collection<DFloat> findAll() {
		return this.dfloatRepository.findAll();
	}

	public DFloat findOne(final int dfloatId) {
		return this.dfloatRepository.findOne(dfloatId);
	}

	public DFloat save(final DFloat dfloat) {
		DFloat res = null;
		Assert.notNull(dfloat);

		res = this.dfloatRepository.save(dfloat);

		return res;
	}

	public void delete(final DFloat dfloat) {
		Assert.notNull(dfloat);
		this.dfloatRepository.delete(dfloat);
	}

	//----------------- Other business methods----------------------------------

	public Collection<DFloat> findAllDFloatsByBrotherhood(final Brotherhood brotherhood) {
		return this.dfloatRepository.SearchDFloatsByBrotherhood(brotherhood.getId());
	}

	public Collection<DFloat> findAllDFloatsWithoutBrotherhood() {
		return this.dfloatRepository.SearchDFloatsWithoutBrotherhood();
	}

}
