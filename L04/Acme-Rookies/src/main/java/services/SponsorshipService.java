
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.LoginService;
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
	Validator				validator;

	Provider				logged	= this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());


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

		Assert.isTrue(sponsorship.getProvider().getId() == this.logged.getId(), "user.logged.doesnt.match");
		return this.sponsorshipRepo.save(sponsorship);
	}

	public Sponsorship findOne(final int id) {

		return this.sponsorshipRepo.findOne(id);
	}

	public void delete(final int id) {

		Assert.isTrue(this.sponsorshipRepo.findOne(id).getProvider().getId() == this.logged.getId(), "user.logged.doesnt.match");
		this.sponsorshipRepo.delete(id);
	}

	public Collection<Sponsorship> findAllByProviderId(final int id) {

		return this.sponsorshipRepo.findAllByProviderId(id);
	}
}
