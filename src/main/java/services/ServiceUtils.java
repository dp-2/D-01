
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.DomainEntityRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.DomainEntity;

@Service
@Transactional
public class ServiceUtils {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private DomainEntityRepository	domainEntityRepository;


	public void checkAuthority(final String auth) {
		if (auth != null) {
			Boolean res = false;
			Assert.notNull(LoginService.getPrincipal());
			Assert.notNull(auth);
			for (final Authority a : LoginService.getPrincipal().getAuthorities())
				if (a.getAuthority().equals(auth)) {
					res = true;
					break;
				}
			Assert.isTrue(res);
		}
	}

	public Boolean checkAuthorityBoolean(final String auth) {
		Boolean res = true;
		if (auth != null) {
			res = false;
			Assert.notNull(LoginService.getPrincipal());
			Assert.notNull(auth);
			for (final Authority a : LoginService.getPrincipal().getAuthorities())
				if (a.getAuthority().equals(auth)) {
					res = true;
					break;
				}
		}
		return res;
	}

	public void checkAnyAuthority(final String[] auths) {
		if (auths != null) {
			Boolean res = false;
			Assert.notNull(auths);
			Assert.notNull(LoginService.getPrincipal());
			for (final Authority a : LoginService.getPrincipal().getAuthorities())
				for (final String s : auths)
					if (s.equals(a.getAuthority())) {
						res = true;
						break;
					}
			Assert.isTrue(res);
		}
	}

	public Boolean checkAnyAuthorityBoolean(final String[] auths) {
		Boolean res = false;
		if (auths != null) {
			Assert.notNull(auths);
			Assert.notNull(LoginService.getPrincipal());
			for (final Authority a : LoginService.getPrincipal().getAuthorities())
				for (final String s : auths)
					if (s.equals(a.getAuthority())) {
						res = true;
						break;
					}

		}
		return res;
	}

	public void checkId(final Integer id) {
		Assert.notNull(id);
		Assert.isTrue(id > 0);
	}

	public void checkIds(final Collection<Integer> ids) {
		Assert.notNull(ids);
		for (final Integer id : ids)
			this.checkId(id);
	}

	public void checkId(final DomainEntity de) {
		Assert.notNull(de.getId());
		Assert.isTrue(de.getId() > 0);
	}

	public void checkIdSave(final DomainEntity de) {
		Assert.notNull(de.getId());
		Assert.isTrue(de.getId() >= 0);
	}

	public void checkNoActor() {
		Actor principal = null;
		try {
			principal = this.actorService.findByUserAccount(LoginService.getPrincipal());
		} catch (final IllegalArgumentException exc) {

		}
		Assert.isNull(principal);
	}

	public void checkActor(final Actor a) {
		if (a != null) {
			// Comprueba el id del Actor
			this.checkId(a);
			// Comprueba que el Actor está en la base de datos
			Assert.notNull(this.actorService.findOne(a.getId()));
			// Comprueba que el actor de entrada y el actor logueado son el mismo
			final Actor principal = this.actorService.findByUserAccount(LoginService.getPrincipal());

			Assert.isTrue(principal.equals(a));
		}
	}

	public Boolean checkActorBoolean(final Actor a) {
		Boolean res = false;
		if (a != null) {
			// Comprueba el id del Actor
			this.checkId(a);
			// Comprueba que el Actor está en la base de datos
			Assert.notNull(this.actorService.findOne(a.getId()));
			// Comprueba que el actor de entrada y el actor logueado son el mismo
			final Actor principal = this.actorService.findByUserAccount(LoginService.getPrincipal());

			if ((principal.equals(a)))
				res = true;
		}
		return res;
	}

	public void checkAnyActor(final Actor[] as) {
		if (as != null) {
			Boolean res = false;
			for (final Actor a : as) {
				try {
					this.checkActor(a);
				} catch (final Throwable t) {
					continue;
				}
				res = true;
				break;
			}
			Assert.isTrue(res);
		}
	}

	public DomainEntity checkObjectSave(final DomainEntity object) {
		this.checkIdSave(object);
		final DomainEntity res = this.checkObjectExists(object);
		return res;
	}

	public DomainEntity checkObject(final DomainEntity object) {
		this.checkId(object);
		final DomainEntity res = this.checkObjectExists(object);
		return res;
	}

	public void checkPermisionActor(final Actor actor, final String[] auths) {
		this.checkActor(actor);
		this.checkAnyAuthority(auths);
	}

	public void checkPermisionActors(final Actor[] actors, final String[] auths) {
		this.checkAnyActor(actors);
		this.checkAnyAuthority(auths);
	}

	public DomainEntity checkObjectExists(final DomainEntity object) {
		DomainEntity res = this.domainEntityRepository.findOne(object.getId());
		if (res == null && object.getId() == 0)
			res = object;
		return res;
	}

}
