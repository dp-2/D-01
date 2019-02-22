
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.UserAccount;
import domain.Brotherhood;
import domain.Url;

@Service
@Transactional
public class BrotherhoodService {

	// Repository

	@Autowired
	private BrotherhoodRepository	repository;

	// Services

	@Autowired
	private ActorService			actorService;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private ServiceUtils			serviceUtils;


	public Brotherhood findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Brotherhood> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Brotherhood> findAll() {
		return this.repository.findAll();
	}

	public Brotherhood create() {
		final Brotherhood res = new Brotherhood();
		res.setBanned(false);
		res.setSpammer(false);
		res.setEstablishedMoment(new Date(System.currentTimeMillis() - 1000));
		res.setPictures(new ArrayList<Url>());
		res.setUserAccount(new UserAccount());
		res.setScore(0.);
		return res;
	}

	public Brotherhood save(final Brotherhood b) {
		final Brotherhood brotherhood = (Brotherhood) this.serviceUtils.checkObjectSave(b);
		if (b.getId() == 0) {
			this.serviceUtils.checkNoActor();
			b.setBanned(false);
			b.setSpammer(false);
			b.setEstablishedMoment(new Date(System.currentTimeMillis() - 1000));
			b.setScore(0.);
			this.boxService.createIsSystemBoxs(b);
		} else {
			this.serviceUtils.checkAnyAuthority(new String[] {
				Authority.ADMIN, Authority.BROTHERHOOD
			});
			if (this.actorService.findPrincipal() instanceof Brotherhood) {
				this.serviceUtils.checkActor(brotherhood);
				b.setBanned(brotherhood.getBanned());
			} else {
				b.setEmail(brotherhood.getEmail());
				b.setName(brotherhood.getName());
				b.setPhone(brotherhood.getPhone());
				b.setPhoto(brotherhood.getPhoto());
				b.setPictures(brotherhood.getPictures());
				b.setSurname(brotherhood.getSurname());
				b.setTitle(brotherhood.getTitle());
				b.setUserAccount(brotherhood.getUserAccount());
			}
			b.setEstablishedMoment(brotherhood.getEstablishedMoment());
		}
		final Brotherhood res = this.repository.save(b);
		return res;
	}

	public void delete(final Brotherhood b) {
		final Brotherhood brotherhood = (Brotherhood) this.serviceUtils.checkObject(b);
		this.serviceUtils.checkActor(brotherhood);
		this.repository.delete(brotherhood);
	}

}
