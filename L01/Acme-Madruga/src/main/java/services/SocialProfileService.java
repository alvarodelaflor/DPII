
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import security.LoginService;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	@Autowired
	private ActorService			actorService;


	public SocialProfile create() {
		final SocialProfile socialProfile = new SocialProfile();
		return socialProfile;
	}

	public SocialProfile save(final SocialProfile socialProfile) {
		Assert.isTrue(socialProfile != null);
		final SocialProfile socialProfileSaved = this.socialProfileRepository.save(socialProfile);
		return socialProfileSaved;
	}
	public Collection<SocialProfile> findAll() {
		return this.socialProfileRepository.findAll();
	}

	public SocialProfile findOne(final Integer id) {
		return this.socialProfileRepository.findOne(id);
	}

	public void delete(final SocialProfile socialProfile) {
		final Integer idUserAccount = LoginService.getPrincipal().getId();
		Assert.notNull(idUserAccount);

		this.socialProfileRepository.delete(socialProfile);
	}

}
