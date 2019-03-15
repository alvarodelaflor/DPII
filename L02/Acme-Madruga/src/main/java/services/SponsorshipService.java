
package services;

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
			//MailBox
		} else {
			result = this.sponsorshipRepository.findOne(sponsorship.getId());

			result.setBanner(sponsorship.getBanner());
			result.setTarget(sponsorship.getTarget());

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
		this.sponsorshipRepository.delete(sponsorship);
	}

}
