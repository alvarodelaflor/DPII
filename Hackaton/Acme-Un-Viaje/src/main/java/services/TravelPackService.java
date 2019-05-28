
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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
import domain.Accomodation;
import domain.BookingAccomodation;
import domain.BookingTransport;
import domain.Customer;
import domain.Host;
import domain.Transport;
import domain.Transporter;
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

	@Autowired
	private TransportService		transportService;


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

	public Collection<Host> getHosts(final TravelPack travelPack) {
		final Collection<Host> res = new HashSet<>();
		for (final BookingAccomodation ba : travelPack.getAccomodations())
			res.add(ba.getAccomodation().getHost());
		return res;
	}

	public Collection<Transporter> getTransporters(final TravelPack travelPack) {
		final Collection<Transporter> res = new HashSet<>();
		for (final BookingTransport bt : travelPack.getTransports())
			res.add(bt.getTransport().getTransporter());
		return res;
	}

	public void accept(final Integer travelPackId) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		final TravelPack travelPack = this.travelPackRepository.findOne(travelPackId);

		System.out.println("TravelPackService::accept -> Reserving a place for 1 person in every transport");
		Transport t = null;
		for (final BookingTransport bt : travelPack.getTransports()) {
			t = this.transportService.findOne(bt.getTransport().getId());
			final int reservedPlaces = t.getReservedPlaces() + 1;
			t.setReservedPlaces(reservedPlaces);
			Assert.isTrue(t.getReservedPlaces() <= t.getNumberOfPlaces());
		}

		System.out.println("TravelPackService::accept -> Reserving each accomodation");
		final Accomodation a = null;
		for (final BookingAccomodation ba : travelPack.getAccomodations())
			// Check if this accomodation has any other booking whose travel pack is not accepted
			Assert.isTrue(this.canBook(ba));

		travelPack.setStatus(true);
		System.out.println("TravelPackService::accept -> Notifying travel agency");
	}
	public void reject(final Integer travelPackId) {
		System.out.println("TravelPackService::reject -> Re-check logic");

		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		final TravelPack travelPack = this.travelPackRepository.findOne(travelPackId);

		travelPack.setStatus(false);
		System.out.println("TravelPackService::accept -> Notifying travel agency");
	}

	public int getDistinctInRangeAccepted(final int id, final Date startDate, final Date endDate) {
		return this.travelPackRepository.getDistinctInRangeAccepted(id, startDate, endDate);
	}

	private boolean canBook(final BookingAccomodation a) {
		final int bookings = this.getDistinctInRangeAccepted(a.getId(), a.getStartDate(), a.getEndDate());
		return bookings == 0;
	}
}
