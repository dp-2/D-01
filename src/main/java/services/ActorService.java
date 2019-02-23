
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import repositories.BrotherhoodRepository;
import repositories.MemberRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Member;

@Service
@Transactional
public class ActorService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private ActorRepository			actorRepository;

	//Service-----------------------------------------------------------------------------
	@Autowired
	private MemberRepository		memberRepository;

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private BoxService				boxService;


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
		actor.setScore(0.0);
		return actor;
	}

	public Actor findOne(final int ActorId) {
		return this.actorRepository.findOne(ActorId);
	}

	public Actor findByUsername(final String username) {
		final Actor actor = this.actorRepository.findByUsername(username);
		return actor;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.findByUserAccount(userAccount.getId());
	}

	public Collection<Actor> findAll() {
		final Collection<Actor> actors = this.actorRepository.findAll();
		Assert.notNull(actors);

		return actors;
	}

	public Actor save(final Actor actor) {

		Assert.notNull(actor);
		final Actor saved = this.actorRepository.save(actor);

		return saved;
	}

	//Other Methods---------------------------------------------------------------------

	public void ban(final Actor actor) {
		actor.setBanned(true);

		this.save(actor);
	}

	public void unban(final Actor actor) {
		actor.setBanned(false);

		this.save(actor);

	}

	public void update(final Actor actor) {

		Assert.notNull(actor);

		final Collection<Authority> authorities = actor.getUserAccount().getAuthorities();
		final Authority mem = new Authority();
		mem.setAuthority(Authority.MEMBER);
		final Authority bro = new Authority();
		bro.setAuthority(Authority.BROTHERHOOD);
		final Authority admin = new Authority();
		admin.setAuthority(Authority.ADMIN);

		if (authorities.contains(mem)) {
			Member member = null;
			if (actor.getId() != 0)
				member = this.memberRepository.findOne(actor.getId());
			else
				//member = this.memberRepository.create();
				member.setUserAccount(actor.getUserAccount());

			member.setEmail(actor.getEmail());
			member.setBanned(actor.getBanned());
			member.setSpammer(actor.getSpammer());
			member.setName(actor.getName());
			member.setPhone(actor.getPhone());
			member.setPhoto(actor.getPhoto());
			member.setSurname(actor.getSurname());
			member.setScore(0.0);

			final Actor actor1 = this.memberRepository.save(member);
			this.boxService.createIsSystemBoxs(actor1);
		} else if (authorities.contains(bro)) {
			final Brotherhood brotherhood = null;
			if (actor.getId() != 0)

				//brotherhood = this.brotherhoodRepository.findOne(actor.getId());
				//else {
				//	bro = this.brotherhoodRepository.create();
				brotherhood.setUserAccount(actor.getUserAccount());
			//}
			brotherhood.setScore(0.0);
			brotherhood.setEmail(actor.getEmail());
			brotherhood.setBanned(actor.getBanned());
			brotherhood.setSpammer(actor.getSpammer());
			brotherhood.setName(actor.getName());
			brotherhood.setPhone(actor.getPhone());
			brotherhood.setPhoto(actor.getPhoto());
			brotherhood.setSurname(actor.getSurname());

			//final Actor actor1 = this.brotherhoodRepository.save(brotherhood);
			//this.boxService.createIsSystemBoxs(actor1);

		} else if (authorities.contains(admin)) {
			Administrator administrator = null;
			if (actor.getId() != 0)
				administrator = this.administratorService.findOne(actor.getId());
			else {
				administrator = this.administratorService.create();
				administrator.setUserAccount(actor.getUserAccount());
			}

			administrator.setSurname(actor.getSurname());
			administrator.setEmail(actor.getEmail());
			administrator.setBanned(actor.getBanned());
			administrator.setSpammer(actor.getSpammer());
			administrator.setName(actor.getName());
			administrator.setPhone(actor.getPhone());
			administrator.setPhoto(actor.getPhoto());

			final Actor actor1 = this.administratorService.save(administrator);
			this.boxService.createIsSystemBoxs(actor1);
		}

	}

}
