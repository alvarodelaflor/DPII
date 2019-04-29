
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


	public Sponsorship create() {

		final Sponsorship res = new Sponsorship();

		final Provider provider = this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());
		res.setProvider(provider);
		final CreditCard creditCard = provider.getCreditCard();
		res.setCreditCard(creditCard);

		return res;
	}

	public Sponsorship save(final Sponsorship sponsorship) {

		return this.sponsorshipRepo.save(sponsorship);
	}

	public Sponsorship findOne(final int id) {

		return this.sponsorshipRepo.findOne(id);
	}

	public void delete(final int id) {

		this.sponsorshipRepo.delete(id);
	}

	public Collection<Sponsorship> findAllByProviderId(final int id) {

		return this.sponsorshipRepo.findAllByProviderId(id);
	}
}
