
package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
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

	@Autowired
	private ParadeService			paradeService;


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
			Sponsorship res = this.sponsorshipRepository.findOne(sponsorship.getId());
			
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
		Assert.notNull(sponsor);
		Assert.isTrue(this.checkCVV(sponsorship.getCreditCard()), "CVV not Valid");
		Assert.isTrue(checkNumber(sponsorship.getCreditCard()), "Number not valid");
		Assert.isTrue(this.checkMoment(sponsorship.getCreditCard()), "Moment must be in the future");
		Assert.isTrue(checkHolderBlank(sponsorship.getCreditCard()), "Must be not blank");
		Assert.isTrue(checkHolderInsecure(sponsorship.getCreditCard()), "Insecure HTML");
		Assert.isTrue(checkMakeBlank(sponsorship.getCreditCard()), "Must be not blank");
		Assert.isTrue(checkMakeInsecure(sponsorship.getCreditCard()), "Insecure HTML");
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
	public Boolean checkCVV(CreditCard creditCard) {
		Boolean res = true;
		try {
			if (Integer.valueOf(creditCard.getCVV())<100 || Integer.valueOf(creditCard.getCVV())>999) {
				res = false;
			}
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
	
	public Boolean checkNumber(CreditCard creditCard) {
		Boolean res = true;
		try {
			Long number = Long.getLong(creditCard.getNumber());
			if (creditCard.getNumber().length()!=16) {
				res = false;
			}
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
	
	public Boolean checkMoment(CreditCard creditCard) {
		Boolean res = true;
		try {
			if (!creditCard.getExpiration().after(DateTime.now().toDate())) {
				res = false;
			}
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
	
	public Boolean checkHolderBlank(CreditCard creditCard) {
		Boolean res = true;
		try {
			if (creditCard.getHolder().length()==0) {
				res = false;
			}
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
	
	public Boolean checkHolderInsecure(CreditCard creditCard) {
		Boolean res = true;
		try {
			if (creditCard.getHolder().contains("<") || creditCard.getHolder().contains(">")) {
				res = false;
			}
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
	
	public Boolean checkMakeBlank(CreditCard creditCard) {
		Boolean res = true;
		try {
			if (creditCard.getMake().length()==0) {
				res = false;
			}
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
	
	public Boolean checkMakeInsecure(CreditCard creditCard) {
		Boolean res = true;
		try {
			if (creditCard.getMake().contains("<") || creditCard.getMake().contains(">")) {
				res = false;
			}
		} catch (Exception e) {
			res = false;
		}
		return res;
	}
	// ALVARO

}
