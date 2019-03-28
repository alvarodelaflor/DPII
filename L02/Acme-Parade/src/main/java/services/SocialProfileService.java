
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
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
		final UserAccount user = LoginService.getPrincipal();
		final Actor test = this.actorService.getActorByUserId(user.getId());
		Assert.isTrue(socialProfile != null);
		final SocialProfile socialProfileSaved = this.socialProfileRepository.save(socialProfile);
		test.getSocialProfiles().add(socialProfileSaved);
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
		final Actor a = this.actorService.getActorByUserId(idUserAccount);
		Assert.isTrue(a.getSocialProfiles().contains(socialProfile));
		a.getSocialProfiles().remove(socialProfile);
		this.socialProfileRepository.delete(socialProfile);
	}

	public void flush() {
		this.socialProfileRepository.flush();
	}

}
