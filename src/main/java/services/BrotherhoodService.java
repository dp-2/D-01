
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.BrotherhoodRepository;
import security.UserAccount;
import domain.Brotherhood;

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
	private MessageService			messageService;
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
		res.setPictures(new ArrayList<String>());
		res.setUserAccount(new UserAccount());
	}

	public Brotherhood save(final Brotherhood b) {
		// TODO todo
	}

	public void delete(final Brotherhood b) {
		// TODO todo
	}

}
