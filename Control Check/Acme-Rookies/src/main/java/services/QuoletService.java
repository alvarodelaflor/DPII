
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
import domain.Audit;
import domain.Company;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	// Repository
	@Autowired
	private QuoletRepository	quoletRepository;

	// Services
	@Autowired
	private CompanyService		companyService;

	@Autowired
	private Validator			validator;

	@Autowired
	private AuditService		auditService;


	// Methods
	public Quolet create(final int auditId) {
		final Quolet res = new Quolet();
		res.setDraftMode(true);
		final Audit audit = this.auditService.findOne(auditId);
		res.setAudit(audit);
		System.out.println(audit);
		return res;
	}

	public void save(final Quolet reconstructed) {
		this.quoletRepository.save(reconstructed);
	}

	public Collection<Quolet> getLoggedQuolets(final int auditId) {
		final Company company = this.companyService.getLoggedCompany();
		return this.quoletRepository.getLoggedQuolets(auditId, company.getId());
	}

	public Quolet getLoggedQuolet(final int quoletId) {
		final Company company = this.companyService.getLoggedCompany();
		final Quolet res = this.quoletRepository.getLoggedQuolet(quoletId, company.getId());
		Assert.notNull(res);
		return res;
	}
	public Quolet reconstruct(final Quolet quolet, final BindingResult binding) {
		System.out.println("reconstructing...");
		Quolet res = quolet;
		if (quolet.getId() != 0)
			res = this.copy(quolet); // We create a copy from db
		else
			res.setTicker(this.createTicker()); // New valid ticker
		Assert.notNull(quolet.getAudit());
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
		final String month = calendar.get(Calendar.MONTH) < 10 ? "0" + String.valueOf(calendar.get(Calendar.MONTH)) : String.valueOf(calendar.get(Calendar.MONTH));
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
		final Quolet dbQuolet = this.quoletRepository.getLoggedQuolet(quolet.getId(), this.companyService.getLoggedCompany().getId());
		Assert.notNull(dbQuolet);
		Assert.isTrue(dbQuolet.getDraftMode());

		final Company logged = this.companyService.getLoggedCompany();
		Assert.isTrue(quolet.getAudit().getPosition().getCompany().getId() == logged.getId());

		// We want this from db
		res.setId(dbQuolet.getId());
		res.setVersion(dbQuolet.getVersion());
		res.setAudit(dbQuolet.getAudit());
		res.setTicker(dbQuolet.getTicker());

		// This is new
		res.setDraftMode(quolet.getDraftMode());
		res.setBody(quolet.getBody());
		res.setPicture(quolet.getPicture());
		return res;
	}

	public void delete(final int quoletId) {
		final Company company = this.companyService.getLoggedCompany();
		final Quolet res = this.quoletRepository.getLoggedQuolet(quoletId, company.getId());
		Assert.isTrue(res.getDraftMode(), "Quolet is not in draft mode, it can't be deleted");
		this.quoletRepository.delete(res.getId());
	}

	public Quolet getLoggedQuoletForEdit(final int quoletId) {
		final Quolet quolet = this.getLoggedQuolet(quoletId);
		Assert.isTrue(quolet.getDraftMode(), "User can't edit a quolet that is not in draft mode");
		return quolet;
	}

}
