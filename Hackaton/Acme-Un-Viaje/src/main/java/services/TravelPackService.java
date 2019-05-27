
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TravelPackRepository;
import security.Authority;
import security.LoginService;
import utilities.CommonUtils;
import domain.BookingAccomodation;
import domain.BookingTransport;
import domain.Customer;
import domain.TravelAgency;
import domain.TravelPack;

@Service
@Transactional
public class TravelPackService {

	@Autowired
	private TravelPackRepository	travelPackRepository;
	@Autowired
	private TravelAgencyService		travelAgencyService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private Validator				validator;


	public void delete(final TravelPack pack) {

		Assert.isTrue(pack.getTravelAgency().getUserAccount().getId() == LoginService.getPrincipal().getId());
		this.travelPackRepository.delete(pack.getId());
	}

	public TravelPack create() {
		final TravelPack pack = new TravelPack();
		pack.setDraft(true);
		pack.setTravelAgency(this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId()));
		return pack;

	}

	public TravelPack save(final TravelPack travelPack) {
		if (travelPack.getId() != 0) {
			final TravelPack packDB = this.findOne(travelPack.getId());
			Assert.notNull(packDB);
			Assert.isTrue(packDB.getDraft(), "The travel pack is already in final mode");
		}
		travelPack.setPrice(this.calculatePrice(travelPack));
		return this.travelPackRepository.save(travelPack);
	}

	public TravelPack findOne(final int travelPackId) {
		return this.travelPackRepository.findOne(travelPackId);
	}

	public Collection<TravelPack> getTravelAgencyPacks() {
		final TravelAgency travel = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
		return this.travelPackRepository.getTravelAgencyPacks(travel.getId());
	}
	public Double calculatePrice(final TravelPack pack) {
		Double price = 0.;
		for (final BookingAccomodation b : pack.getAccomodations())
			price += b.getAccomodation().getPricePerNight() * ((b.getEndDate().getTime() - b.getStartDate().getTime()) - 1) / 86400000;

		for (final BookingTransport b : pack.getTransports())
			price += b.getTransport().getPrice();
		return price;
	}

	public TravelPack reconstruct(final TravelPack travelPack, final BindingResult binding) {
		TravelPack result;
		if (travelPack.getId() == 0) {
			travelPack.setTravelAgency(this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId()));
			final List<BookingAccomodation> accs = new ArrayList<>();
			travelPack.setAccomodations(accs);
			final List<BookingTransport> transports = new ArrayList<>();
			travelPack.setTransports(transports);
			result = travelPack;
		} else {
			result = this.travelPackRepository.findOne(travelPack.getId());
			Assert.notNull(result, "Travel Pack is null");
			travelPack.setId(result.getId());
			travelPack.setVersion(result.getVersion());
			travelPack.setTravelAgency(result.getTravelAgency());
			travelPack.setCustomer(result.getCustomer());
			travelPack.setAccomodations(result.getAccomodations());
			travelPack.setTransports(result.getTransports());
			result = travelPack;
		}
		this.validator.validate(result, binding);
		return result;
	}

	public Collection<TravelPack> getTravelAgencyDraftPacks() {
		final TravelAgency travel = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
		return this.travelPackRepository.getTravelAgencyDraftPacks(travel.getId());
	}

	public TravelPack findFromComplaint(final int complaintId) {
		final TravelPack tp = this.travelPackRepository.findFromComplaint(complaintId);
		return tp;
	}

	public Collection<TravelPack> getLoggedNotDraftStatusNull() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		final Customer c = this.customerService.getLoggedCustomer();
		return this.travelPackRepository.getLoggedNotDraftStatusNull(c.getId());
	}

	public Collection<TravelPack> getLoggedNotDraftStatusTrue() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		final Customer c = this.customerService.getLoggedCustomer();
		return this.travelPackRepository.getLoggedNotDraftStatusTrue(c.getId());
	}

	public Collection<TravelPack> getLoggedNotDraftStatusFalse() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		final Customer c = this.customerService.getLoggedCustomer();
		return this.travelPackRepository.getLoggedNotDraftStatusFalse(c.getId());
	}
}
