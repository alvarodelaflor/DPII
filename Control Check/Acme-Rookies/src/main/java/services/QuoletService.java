
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
import security.LoginService;
import utilities.AuthUtils;
import domain.Audit;
import domain.Auditor;
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

	@Autowired
	private AuditorService		auditorService;


	// Methods
	public Quolet create(final int auditId) {
		final Quolet res = new Quolet();
		Assert.isTrue(AuthUtils.checkLoggedAuthority(Authority.COMPANY));
		res.setDraftMode(true);
		final Audit audit = this.auditService.findOne(auditId);
		Assert.notNull(audit, "Null audit");

		// ALVARO
		final Company company = audit.getPosition().getCompany();
		final Company companyLogger = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(company.equals(companyLogger));
		// ALVARO

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

	// ALVARO
	public Boolean checkCompany(final Audit audit) {
		Boolean res = true;
		try {
			final Company company = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId());
			if (!audit.getPosition().getCompany().equals(company))
				res = false;
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}
	// ALVARO

	// ALVARO
	public Collection<Quolet> getLoggedQuoletsV2(final int auditId) {
		final Audit audit = this.auditService.findOne(auditId);
		Assert.notNull(audit, "Audit not found in DB");
		Collection<Quolet> res = null;
		if (this.checkCompany(audit))
			res = this.quoletRepository.getLoggedQuolets(auditId);
		else
			res = this.getQuoletsNoDraftModeV2(auditId);

		return res;
	}
	// ALVARO

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

		final Company logged = this.companyService.getLoggedCompany();
		Assert.isTrue(quolet.getAudit().getPosition().getCompany().getId() == logged.getId());

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
		final Quolet dbQuolet = this.quoletRepository.getLoggedQuolet(quolet.getId(), this.companyService.getLoggedCompany().getId());
		Assert.notNull(dbQuolet);
		Assert.isTrue(dbQuolet.getDraftMode());

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

	public Collection<Quolet> getQuoletsNoDraftMode(final int auditId) {
		final Auditor auditorL = this.auditorService.getAuditorLogin();
		Assert.notNull(auditorL);
		Assert.notNull(this.auditService.findOne(auditId).getAuditor().equals(auditorL));
		return this.quoletRepository.getQuoletsNoDraftMode(auditId);
	}

	// ALVARO
	public Collection<Quolet> getQuoletsNoDraftModeV2(final int auditId) {
		return this.quoletRepository.getQuoletsNoDraftMode(auditId);
	}
	// ALVARO

	public Quolet findOne(final int id) {
		return this.quoletRepository.findOne(id);
	}

	public Quolet getQuoletNoDraftMode(final int quoletId) {
		final Auditor auditorL = this.auditorService.getAuditorLogin();
		Assert.notNull(auditorL);
		Assert.notNull(this.findOne(quoletId).getAudit().getAuditor().equals(auditorL));
		final Quolet res = this.findOne(quoletId);
		return res;
	}

	public void flush() {
		this.quoletRepository.flush();
	}
	public void deleteAuditQuolets(final Audit audit) {
		final Collection<Quolet> qs = this.quoletRepository.findAuditQuolets(audit.getId());
		this.quoletRepository.deleteInBatch(qs);

	}

}
