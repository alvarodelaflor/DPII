
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.LoginService;
import security.UserAccount;
import domain.Sponsor;
import domain.Sponsorship;

/*
 * CONTROL DE CAMBIOS SponsorshipService.java
 * 
 * Antonio Salvat 09/03/2019 17:42
 */

@Service
public class SponsorshipService {

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private WelcomeService			welcomeService;

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private SponsorService			sponsorService;


	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {
		Sponsorship result;

		if (sponsorship.getId() == 0) {
			final UserAccount user = LoginService.getPrincipal();
			final Sponsor s = this.sponsorService.getSponsorByUserId(user.getId());

			result = sponsorship;
			result.setSponsor(s);
			result.setActive(true);
			//MailBox
		} else {
			result = this.sponsorshipRepository.findOne(sponsorship.getId());

			result.setBanner(sponsorship.getBanner());
			result.setTarget(sponsorship.getTarget());
			result.setCreditCard(sponsorship.getCreditCard());

			this.validator.validate(result, binding);
		}
		return result;
	}
	public Sponsorship create() {
		final Sponsorship sponsorship = new Sponsorship();
		return sponsorship;
	}

	public List<Sponsorship> findAll() {
		return this.sponsorshipRepository.findAll();
	}

	public Sponsorship findOne(final int id) {
		final Sponsorship sponsorship = this.sponsorshipRepository.findOne(id);
		return sponsorship;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		return this.sponsorshipRepository.save(sponsorship);
	}

	public void delete(final Sponsorship sponsorship) {
		if (sponsorship.getActive() == true)
			sponsorship.setActive(false);
		else
			sponsorship.setActive(true);
		final Sponsorship res = this.save(sponsorship);
	}

	public void checkCreditCard(final Sponsorship sponsorship) {
		if (sponsorship.getCreditCard().getExpiration().before(new Date()))
			sponsorship.setActive(false);
		final Sponsorship res = this.save(sponsorship);
	}

	public void deleteParadeSponsorships(final int paradeId) {
		final Collection<Sponsorship> sponsorships = this.sponsorshipRepository.getParadeSponsorships(paradeId);
		for (final Sponsorship sponsorship : sponsorships)
			this.sponsorshipRepository.delete(sponsorship.getId());
	}

}
