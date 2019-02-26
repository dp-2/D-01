
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import repositories.SocialProfileRepository;
import security.LoginService;
import security.UserAccount;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	@Autowired
	private ActorRepository			actorRepository;


	//Constructor----------------------------------------------------------------------------

	public SocialProfileService() {
		super();
	}

	// Simple CRUD methods -------------------------------------------------------------------
	public SocialProfile create(final int actorId) {
		final SocialProfile socialProfile = new SocialProfile();
		socialProfile.setActor(this.actorRepository.findOne(actorId));
		return socialProfile;
	}

	public Collection<SocialProfile> findAll() {
		Collection<SocialProfile> socialProfiles;

		socialProfiles = this.socialProfileRepository.findAll();
		Assert.notNull(socialProfiles);

		return socialProfiles;
	}
	public SocialProfile findOne(final int socialProfileId) {
		SocialProfile socialProfile;
		socialProfile = this.socialProfileRepository.findOne(socialProfileId);
		Assert.notNull(socialProfileId);

		return socialProfile;
	}

	public SocialProfile save(final SocialProfile socialProfile) {
		Assert.notNull(socialProfile);
		this.checkPrincipal(socialProfile);
		SocialProfile result;

		result = this.socialProfileRepository.save(socialProfile);

		return result;
	}

	public void delete(final SocialProfile socialProfile) {

		Assert.notNull(socialProfile);
		this.checkPrincipal(socialProfile);
		this.socialProfileRepository.delete(socialProfile);
	}
	//Other Methods-----------------------------------------------------------------
	public Boolean checkPrincipal(final SocialProfile socialProfile) {
		final UserAccount u = socialProfile.getActor().getUserAccount();
		Assert.isTrue(u.equals(LoginService.getPrincipal()), "este perfil no corresponde con este actor");
		return true;
	}

	public Collection<SocialProfile> findProfileByActorId(final int actorId) {
		return this.socialProfileRepository.findProfileByActorId(actorId);
	}
}
