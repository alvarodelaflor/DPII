
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Administrator;
import domain.CreditCard;
import domain.Parade;
import domain.Sponsor;
import domain.Sponsorship;
import repositories.SponsorshipRepository;
import security.LoginService;
import security.UserAccount;

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

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private AdministratorService	adminService;


	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {
		Sponsorship result;

		if (sponsorship.getId() == 0) {
			System.out.println("id 0");
			final UserAccount user = LoginService.getPrincipal();
			final Sponsor s = this.sponsorService.getSponsorByUserId(user.getId());

			result = sponsorship;
			result.setSponsor(s);
			result.setActive(true);
			this.validator.validate(result, binding);
			//MailBox
		} else {
			final Sponsorship res = this.sponsorshipRepository.findOne(sponsorship.getId());

			result = sponsorship;

			result.setId(res.getId());
			result.setVersion(res.getVersion());
			result.setSponsor(this.sponsorService.getSponsorByUserId(LoginService.getPrincipal().getId()));
			result.setActive(res.getActive());
			this.validator.validate(result, binding);
			System.out.println("binding reconstruct: " + binding);
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
		final UserAccount user = LoginService.getPrincipal();
		final Sponsor sponsor = this.sponsorService.getSponsorByUserId(user.getId());
		final Administrator admin = this.adminService.getAdministratorByUserAccountId(user.getId());
		Assert.isTrue(sponsor != null || admin != null);
		Assert.isTrue(this.checkCVV(sponsorship.getCreditCard()), "CVV not Valid");
		Assert.isTrue(this.checkNumber(sponsorship.getCreditCard()), "Number not valid");
		Assert.isTrue(this.checkMoment(sponsorship.getCreditCard()), "Moment must be in the future");
		Assert.isTrue(this.checkHolderBlank(sponsorship.getCreditCard()), "Must be not blank");
		Assert.isTrue(this.checkHolderInsecure(sponsorship.getCreditCard()), "Insecure HTML");
		Assert.isTrue(this.checkMakeBlank(sponsorship.getCreditCard()), "Must be not blank");
		Assert.isTrue(this.checkMakeInsecure(sponsorship.getCreditCard()), "Insecure HTML");
		Assert.notNull(sponsorship.getCreditCard().getCVV());
		Assert.notNull(sponsorship.getCreditCard().getMake());
		Assert.notNull(sponsorship.getCreditCard().getNumber());
		Assert.notNull(sponsorship.getCreditCard().getHolder());
		Assert.notNull(sponsorship.getCreditCard().getExpiration());
		return this.sponsorshipRepository.save(sponsorship);
	}
	public void delete(final Sponsorship sponsorship) {
		final UserAccount user = LoginService.getPrincipal();
		final Sponsor sponsor = this.sponsorService.getSponsorByUserId(user.getId());
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getSponsorships().contains(sponsorship));
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

	public Double getRatioActiveSponsorships() {

		return this.sponsorshipRepository.getRatioActiveSponsorships();
	}
	public void deleteParadeSponsorships(final int paradeId) {
		final Collection<Sponsorship> sponsorships = this.sponsorshipRepository.getParadeSponsorships(paradeId);
		for (final Sponsorship sponsorship : sponsorships)
			this.sponsorshipRepository.delete(sponsorship.getId());
	}

	public Sponsorship randomSponsorship(final int paradeId) {

		Sponsorship res = null;
		final List<Sponsorship> shs = new ArrayList<>(this.sponsorshipRepository.getParadeSponsorships(paradeId));
		final Random r = new Random();

		if (!shs.isEmpty()) {
			res = shs.get(r.nextInt(shs.size()));
			res.setBannerCount((res.getBannerCount() + 1));
			res = this.sponsorshipRepository.save(res);
		}
		return res;
	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}

	// ALVARO
	public Boolean checkCVV(final CreditCard creditCard) {
		Boolean res = true;
		try {
			if (Integer.valueOf(creditCard.getCVV()) < 100 || Integer.valueOf(creditCard.getCVV()) > 999)
				res = false;
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	public Boolean checkNumber(final CreditCard creditCard) {
		Boolean res = true;
		System.out.println(creditCard.getNumber().length());
		try {
			final Long number = Long.getLong(creditCard.getNumber());
			if (creditCard.getNumber().length() != 16)
				res = false;
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	public Boolean checkMoment(final CreditCard creditCard) {
		Boolean res = true;
		try {
			if (!creditCard.getExpiration().after(DateTime.now().toDate()))
				res = false;
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	public Boolean checkHolderBlank(final CreditCard creditCard) {
		Boolean res = true;
		try {
			if (creditCard.getHolder().length() == 0)
				res = false;
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	public Boolean checkHolderInsecure(final CreditCard creditCard) {
		Boolean res = true;
		try {
			if (creditCard.getHolder().contains("<") || creditCard.getHolder().contains(">"))
				res = false;
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	public Boolean checkMakeBlank(final CreditCard creditCard) {
		Boolean res = true;
		try {
			if (creditCard.getMake().length() == 0)
				res = false;
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	public Boolean checkMakeInsecure(final CreditCard creditCard) {
		Boolean res = true;
		try {
			if (creditCard.getMake().contains("<") || creditCard.getMake().contains(">"))
				res = false;
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}
	
	public Boolean checkAnyParade() {
		Boolean res = false;
		try {
			for (Parade parade : this.paradeService.findAll()) {
				if (parade.getIsFinal().equals(true) && parade.getStatus().equals("ACCEPTED")) {
					res = true;
					break;
				}
			}
		} catch (Exception e) {
			// Se ha producido un error
		}
		return res;
	}
	// ALVARO

}
