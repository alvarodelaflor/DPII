
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.TravelAgency;
import domain.Warranty;
import repositories.WarrantyRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class WarrantyService {

	@Autowired
	private WarrantyRepository	warrantyRepository;

	@Autowired
	private Validator			validator;

	@Autowired
	private TravelAgencyService	travelAgencyService;


	public Warranty create() {
		final Warranty warranty = new Warranty();
		final UserAccount userL = LoginService.getPrincipal();
		final TravelAgency travelAgency = this.travelAgencyService.getTravelAgencyByUserAccountId(userL.getId());
		Assert.notNull(travelAgency);
		warranty.setTravelAgency(travelAgency);
		return warranty;
	}

	public Warranty save(final Warranty w) {
		return this.warrantyRepository.save(w);
	}

	public Warranty reconstruct(final Warranty warranty, final BindingResult binding) {
		final Warranty warrantyNew = this.create();

		warrantyNew.setTerms(warranty.getTerms());
		warrantyNew.setTitle(warranty.getTitle());
		warrantyNew.setDraftMode(warranty.getDraftMode());

		final UserAccount userL = LoginService.getPrincipal();
		final TravelAgency travelAgency = this.travelAgencyService.getTravelAgencyByUserAccountId(userL.getId());
		Assert.notNull(travelAgency);
		warrantyNew.setTravelAgency(travelAgency);

		this.validator.validate(warrantyNew, binding);

		return warrantyNew;
	}

	public Warranty reconstructR(final Warranty warranty, final BindingResult binding) {
		final TravelAgency actor = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(actor);
		final Warranty warrantyNew = this.findOne(warranty.getId());
		Assert.isTrue(warrantyNew.getTravelAgency().equals(actor));

		warrantyNew.setTerms(warranty.getTerms());
		warrantyNew.setTitle(warranty.getTitle());
		warrantyNew.setDraftMode(warranty.getDraftMode());

		final UserAccount userL = LoginService.getPrincipal();
		final TravelAgency travelAgency = this.travelAgencyService.getTravelAgencyByUserAccountId(userL.getId());
		Assert.notNull(travelAgency);
		warrantyNew.setTravelAgency(travelAgency);

		this.validator.validate(warrantyNew, binding);

		return warrantyNew;
	}

	public Collection<Warranty> getTravelAgencyWarranty() {
		final TravelAgency actor = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(actor != null);
		return this.warrantyRepository.getWarrantiesByActor(actor.getId());
	}

	public void delete(final Warranty warranty) {
		final TravelAgency actor = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(actor);
		Assert.isTrue(warranty.getTravelAgency().equals(actor));
		Assert.isTrue(warranty.getDraftMode() == true);
		this.warrantyRepository.delete(warranty);
	}

	public Warranty findOne(final int id) {
		final Warranty result = this.warrantyRepository.findOne(id);
		return result;
	}

	public void deleteAllByTravelAgency(final TravelAgency travelAgency) {
		final Collection<Warranty> items = this.getTravelAgencyWarranty();
		if (items != null && !items.isEmpty())
			for (final Warranty item : items)
				this.warrantyRepository.delete(item);
	}

}
