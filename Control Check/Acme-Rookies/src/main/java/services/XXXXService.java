
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

import repositories.XXXXRepository;
import domain.Company;
import domain.XXXX;

@Service
@Transactional
public class XXXXService {

	// Repository
	@Autowired
	private XXXXRepository	xxxxRepository;

	// Services
	@Autowired
	private CompanyService	companyService;

	@Autowired
	private Validator		validator;


	// Methods
	public XXXX create() {
		final XXXX res = new XXXX();
		res.setDraftMode(true);
		return res;
	}

	public void save(final XXXX reconstructed) {
		this.xxxxRepository.save(reconstructed);
	}

	public Collection<XXXX> getLoggedXXXXs() {
		final Company company = this.companyService.getLoggedCompany();
		return this.xxxxRepository.getLoggedXXXXs(company.getId());
	}

	public XXXX getLoggedXXXX(final int XXXXId) {
		final Company company = this.companyService.getLoggedCompany();
		final XXXX res = this.xxxxRepository.getLoggedXXXX(XXXXId, company.getId());
		Assert.notNull(res);
		return res;
	}
	public XXXX reconstruct(final XXXX xxxx, final BindingResult binding) {
		System.out.println("reconstructing...");
		XXXX res = xxxx;
		if (xxxx.getId() != 0)
			res = this.copy(xxxx); // We create a copy from db
		else
			res.setTicker(this.createTicker()); // New valid ticker
		res.setPublicationMoment(new Date()); // Update the publicationMoment

		System.out.println("reconstruction completed!");
		System.out.println("validating...");
		this.validator.validate(res, binding);
		System.out.println("problem: " + res.getProblem());
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

			tickerInUse = this.xxxxRepository.findByTicker(ticker);
		}

		return ticker;
	}
	private XXXX copy(final XXXX xxxx) {
		final XXXX res = new XXXX();
		final XXXX dbXXXX = this.xxxxRepository.getLoggedXXXX(xxxx.getId(), this.companyService.getLoggedCompany().getId());
		Assert.notNull(dbXXXX);
		Assert.isTrue(dbXXXX.getDraftMode());

		// We want this from db
		res.setId(dbXXXX.getId());
		res.setVersion(dbXXXX.getVersion());
		res.setProblem(dbXXXX.getProblem());
		res.setTicker(dbXXXX.getTicker());

		// This is new
		res.setDraftMode(xxxx.getDraftMode());
		res.setBody(xxxx.getBody());
		res.setPicture(xxxx.getPicture());
		return res;
	}

	public void delete(final int xxxxId) {
		final Company company = this.companyService.getLoggedCompany();
		final XXXX res = this.xxxxRepository.getLoggedXXXX(xxxxId, company.getId());
		Assert.isTrue(res.getDraftMode(), "XXXX is not in draft mode, it can't be deleted");
		this.xxxxRepository.delete(res.getId());
	}

	public XXXX getLoggedXXXXForEdit(final int xxxxId) {
		final XXXX xxxx = this.getLoggedXXXX(xxxxId);
		Assert.isTrue(xxxx.getDraftMode(), "User can't edit a xxxx that is not in draft mode");
		return xxxx;
	}

}
