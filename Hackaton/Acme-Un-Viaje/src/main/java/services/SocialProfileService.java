package services;

import java.util.ArrayList;
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
		Assert.isTrue(LoginService.getPrincipal() != null);
		final SocialProfile socialProfile = new SocialProfile();
		return socialProfile;
	}

	public SocialProfile save(final SocialProfile socialProfile) { 
		final UserAccount user = LoginService.getPrincipal();
		final Actor test = this.actorService.getActorByUserId(user.getId());
		Assert.isTrue(socialProfile != null);
		socialProfile.setActor(test);
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
		final Actor a = this.actorService.getActorByUserId(idUserAccount);
		Assert.isTrue(socialProfile.getActor().equals(a));
		socialProfile.setActor(null);
		this.socialProfileRepository.delete(socialProfile);
	}

	public void flush() {
		this.socialProfileRepository.flush();
	}

	public Collection<SocialProfile> getSocialProfilesByActor(final Integer actorId) {
		final Collection<SocialProfile> res = new ArrayList<>();
		res.addAll(this.socialProfileRepository.getSocialProfilesByActor(actorId));
		return res;
	}

}