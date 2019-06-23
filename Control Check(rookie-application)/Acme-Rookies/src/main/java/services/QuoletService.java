
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.QuoletRepository;
import security.Authority;
import utilities.AuthUtils;
import domain.Application;
import domain.Company;
import domain.Quolet;
import domain.Rookie;

@Service
@Transactional
public class QuoletService {

	// Repository
	@Autowired
	private QuoletRepository	quoletRepository;

	// Services
	@Autowired
	private RookieService		rookieService;

	@Autowired
	private Validator			validator;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CompanyService		companyService;


	// Methods
	public Quolet create(final int applicationId) {
		final Quolet res = new Quolet();
		Assert.isTrue(AuthUtils.checkLoggedAuthority(Authority.ROOKIE));
		res.setDraftMode(true);
		final Application application = this.applicationService.findOne(applicationId);
		Assert.notNull(application, "Null application");

		// ALVARO
		final Rookie rookie = this.rookieService.getRookieLogin();
		Assert.isTrue(rookie.equals(application.getRookie()));
		// ALVARO

		res.setApplication(application);
		System.out.println(application);
		return res;
	}

	public void save(final Quolet reconstructed) {
		this.quoletRepository.save(reconstructed);
	}

	public Collection<Quolet> getLoggedQuolets(final int applicationId) {
		final Rookie rookie = this.rookieService.getRookieLogin();
		return this.quoletRepository.getLoggedQuolets(applicationId, rookie.getId());
	}

	// ALVARO
	public Boolean checkRookie(final Application application) {
		Boolean res = true;
		try {
			final Rookie rookie = this.rookieService.getRookieLogin();
			if (!application.getRookie().equals(rookie))
				res = false;
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}
	// ALVARO

	// ALVARO
	public Collection<Quolet> getLoggedQuoletsV2(final int applicationId) {
		final Application application = this.applicationService.findOne(applicationId);
		Assert.notNull(application, "Application not found in DB");
		Collection<Quolet> res = null;
		if (this.checkRookie(application))
			res = this.quoletRepository.getLoggedQuolets(applicationId);
		else
			res = this.getQuoletsNoDraftModeV2(applicationId);

		return res;
	}
	// ALVARO

	public Quolet getLoggedQuolet(final int quoletId) {
		final Rookie rookie = this.rookieService.getRookieLogin();
		final Quolet res = this.quoletRepository.getLoggedQuolet(quoletId, rookie.getId());
		Assert.notNull(res);
		return res;
	}

	public Quolet reconstruct(final Quolet quolet, final BindingResult binding, final int applicationId) {
		System.out.println("reconstructing...");
		quolet.setApplication(this.applicationService.findOne(applicationId));
		Quolet res = quolet;
		if (quolet.getId() != 0) {
			Assert.isTrue(this.copy(quolet).getApplication().getId() == applicationId);
			res = this.copy(quolet); // We create a copy from db
		} else
			res.setTicker(this.createTicker()); // New valid ticker
		Assert.notNull(quolet.getApplication());

		final Rookie logged = this.rookieService.getRookieLogin();
		Assert.isTrue(quolet.getApplication().getRookie().getId() == logged.getId());

		res.setPublicationMoment(new Date()); // Update the publicationMoment

		System.out.println("reconstruction completed!");
		System.out.println("validating...");
		this.validator.validate(res, binding);
		System.out.println("validation completed");
		System.out.println(binding.getFieldErrors());
		return res;
	}
	private String createTicker() {
		final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		final Calendar calendar = Calendar.getInstance();
		final String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
		final int monthNumber = calendar.get(Calendar.MONTH) + 1;
		final String month = monthNumber < 10 ? "0" + String.valueOf(monthNumber) : String.valueOf(monthNumber);
		final String day = calendar.get(Calendar.DATE) < 10 ? "0" + String.valueOf(calendar.get(Calendar.DATE)) : String.valueOf(calendar.get(Calendar.DATE));

		int tickerInUse = 1;
		String ticker = null;
		while (tickerInUse != 0) {
			tickerInUse = 0;
			ticker = year + month + day + "-";

			// 5 Random characters
			for (int i = 0; i < 5; i++) {
				final int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());

				ticker += ALPHA_NUMERIC_STRING.charAt(character);
			}

			tickerInUse = this.quoletRepository.findByTicker(ticker);
		}

		return ticker;
	}
	private Quolet copy(final Quolet quolet) {
		final Quolet res = new Quolet();
		final Quolet dbQuolet = this.quoletRepository.getLoggedQuolet(quolet.getId(), this.rookieService.getRookieLogin().getId());
		Assert.notNull(dbQuolet);
		Assert.isTrue(dbQuolet.getDraftMode());

		// We want this from db
		res.setId(dbQuolet.getId());
		res.setVersion(dbQuolet.getVersion());
		res.setApplication(dbQuolet.getApplication());
		res.setTicker(dbQuolet.getTicker());

		// This is new
		res.setDraftMode(quolet.getDraftMode());
		res.setBody(quolet.getBody());
		res.setPicture(quolet.getPicture());
		return res;
	}

	public void delete(final int quoletId) {
		final Rookie rookie = this.rookieService.getRookieLogin();
		final Quolet res = this.quoletRepository.getLoggedQuolet(quoletId, rookie.getId());
		Assert.isTrue(res.getDraftMode(), "Quolet is not in draft mode, it can't be deleted");
		this.quoletRepository.delete(res.getId());
	}

	public Quolet getLoggedQuoletForEdit(final int quoletId) {
		final Quolet quolet = this.getLoggedQuolet(quoletId);
		Assert.isTrue(quolet.getDraftMode(), "User can't edit a quolet that is not in draft mode");
		return quolet;
	}

	public Collection<Quolet> getQuoletsNoDraftMode(final int applicationId) {
		final Company company = this.companyService.getCompanyLogin();
		Assert.notNull(company);
		Assert.notNull(this.applicationService.findOne(applicationId).getPosition().getCompany().equals(company));
		return this.quoletRepository.getQuoletsNoDraftMode(applicationId);
	}

	// ALVARO
	public Collection<Quolet> getQuoletsNoDraftModeV2(final int applicationId) {
		return this.quoletRepository.getQuoletsNoDraftMode(applicationId);
	}
	// ALVARO

	public Quolet findOne(final int id) {
		return this.quoletRepository.findOne(id);
	}

	public Quolet getQuoletNoDraftMode(final int quoletId) {
		final Company company = this.companyService.getCompanyLogin();
		Assert.notNull(company);
		Assert.notNull(this.findOne(quoletId).getApplication().getPosition().getCompany().equals(company));
		final Quolet res = this.findOne(quoletId);
		return res;
	}

	public void flush() {
		this.quoletRepository.flush();
	}
	public void deleteApplicationQuolets(final Application application) {
		final Collection<Quolet> qs = this.quoletRepository.findApplicationQuolets(application.getId());
		this.quoletRepository.deleteInBatch(qs);

	}

}
