
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.hibernate.engine.config.spi.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	//--------------------Repositories--------------------------

	@Autowired
	private ActorRepository			actorRepository;

	//--------------------Services------------------------------

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private MessageService			messageService;

	//@Autowired
	//private SocialProfileService	socialProfileService;

	@Autowired
	private ServiceUtils			serviceUtils;


	// ------------------CRUD methods-----------------------------

	public Actor create(final String authority) {
		final Actor actor = new Actor();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		final Authority a = new Authority();
		a.setAuthority(authority);
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		actor.setUserAccount(userAccount);
		actor.setBanned(false);
		actor.setSpammer(false);
		actor.setScore(0.0);
		return actor;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);
		Actor result;
		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Actor> findAllExceptMe(final Actor a) {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		result.remove(a);
		Assert.notNull(result);

		return result;
	}

	public Actor findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.actorRepository.findByUserAccount(userAccount.getId());
	}

	// -------------------------Other methods-------------------------

	public Actor findByUsername(final String username) {
		final Actor actor = this.actorRepository.findByUsername(username);
		return actor;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.findByUserAccount(userAccount.getId());
	}

}
