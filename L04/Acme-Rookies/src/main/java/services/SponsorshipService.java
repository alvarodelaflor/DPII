
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.CreditCard;
import domain.Provider;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	SponsorshipRepository	sponsorshipRepo;

	@Autowired
	ProviderService			providerService;

	@Autowired
	ActorService			actorService;

	@Autowired
	Validator				validator;


	public Sponsorship create() {

		final Sponsorship res = new Sponsorship();
		final Provider provider = this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());
		res.setProvider(provider);
		final CreditCard creditCard = provider.getCreditCard();
		res.setCreditCard(creditCard);

		return res;
	}

	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {

		Sponsorship res;

		if (sponsorship.getId() == 0)
			res = this.create();
		else
			res = this.findOne(sponsorship.getId());

		res.setBanner(sponsorship.getBanner());
		res.setTarget(sponsorship.getTarget());
		res.setPosition(sponsorship.getPosition());

		this.validator.validate(res, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}
	public Sponsorship save(final Sponsorship sponsorship) {

		final Actor logged = this.actorService.getActorByUserId(LoginService.getPrincipal().getId());
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(sponsorship.getProvider().getId() == logged.getId() || logged.getUserAccount().getAuthorities().contains(auth), "user.logged.doesnt.match");
		return this.sponsorshipRepo.save(sponsorship);
	}
	public Sponsorship findOne(final int id) {

		return this.sponsorshipRepo.findOne(id);
	}

	public void delete(final int id) {

		final Provider logged = this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(this.sponsorshipRepo.findOne(id).getProvider().getId() == logged.getId(), "user.logged.doesnt.match");
		this.sponsorshipRepo.delete(id);
	}

	public Collection<Sponsorship> findAllByProviderId(final int id) {

		return this.sponsorshipRepo.findAllByProviderId(id);
	}

	public Sponsorship randomSponsorship(final int id) {

		Sponsorship res = null;
		final List<Sponsorship> shs = new ArrayList<>(this.sponsorshipRepo.findAllByPositionId(id));
		final Random r = new Random();

		if (!shs.isEmpty()) {
			res = shs.get(r.nextInt(shs.size()));
			res.setBannerCount((res.getBannerCount() + 1));
			res = this.sponsorshipRepo.save(res);
		}
		return res;
	}

	public Collection<Sponsorship> findAll() {

		return this.sponsorshipRepo.findAll();
	}
}
