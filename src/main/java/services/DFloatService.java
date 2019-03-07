
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DFloatRepository;
import security.LoginService;
import domain.Actor;
import domain.Brotherhood;
import domain.DFloat;
import domain.Procession;

@Service
@Transactional
public class DFloatService {

	//Managed Repository

	@Autowired
	private DFloatRepository	dfloatRepository;
	//Supporting Service

	@Autowired
	ActorService				actorService;

	@Autowired
	ServiceUtils				serviceUtils;


	// Simple CRUD methods
	public DFloat create() {
		DFloat res;
		Brotherhood brotherhood;
		final Collection<Procession> processions = new ArrayList<>();
		brotherhood = (Brotherhood) this.actorService.findByUserAccount(LoginService.getPrincipal());
		res = new DFloat();
		res.setBrotherhood(brotherhood);
		res.setProcessions(processions);

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
		this.serviceUtils.checkObjectSave(dfloat);

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

	public Collection<DFloat> SearchDFloatsByBrotherhood(final int brotherhoodId) {
		return this.dfloatRepository.SearchDFloatsByBrotherhood(brotherhoodId);
	}

	public Collection<DFloat> findAllDFloatsWithoutBrotherhood() {
		return this.dfloatRepository.SearchDFloatsWithoutBrotherhood();
	}

	//	public List<DFloat> findDFloatsByProcessionId(final int processionId) {
	//		return this.dfloatRepository.findDFloatsByProcessionId(processionId);
	//	

	public Collection<DFloat> searchFloatNotInProcessionByIdByActorId(final Procession procession, final Actor actor) {
		this.serviceUtils.checkObject(procession);
		this.serviceUtils.checkObject(actor);
		return this.dfloatRepository.searchFloatNotInProcessionByIdByActorId(procession.getId(), actor.getId());
	}

	public Collection<DFloat> searchFloatInProcessionByIdByActorId(final Procession procession, final Actor actor) {
		this.serviceUtils.checkObject(procession);
		this.serviceUtils.checkObject(actor);
		return this.dfloatRepository.searchFloatInProcessionByIdByActorId(procession.getId(), actor.getId());
	}
}
