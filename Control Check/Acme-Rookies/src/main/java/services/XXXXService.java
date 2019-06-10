
package services;

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
		XXXX res = xxxx;
		if (xxxx.getId() != 0)
			res = this.copy(xxxx); // We create a copy from db
		else
			res.setTicker(this.createTicker()); // New valid ticker
		res.setPublicationMoment(new Date()); // Update the publicationMoment

		this.validator.validate(res, binding);

		return res;
	}

	private String createTicker() {
		return "TEMPLATE TICKER";
	}

	private XXXX copy(final XXXX xxxx) {
		final XXXX res = new XXXX();
		final XXXX dbXXXX = this.xxxxRepository.getLoggedXXXX(xxxx.getId(), this.companyService.getLoggedCompany().getId());
		Assert.notNull(dbXXXX);

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

}
