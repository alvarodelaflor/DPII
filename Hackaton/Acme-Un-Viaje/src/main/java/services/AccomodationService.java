
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AccomodationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Accomodation;
import domain.BookingAccomodation;
import domain.CleaningTask;
import domain.FinderAccomodation;
import domain.Host;

@Service
@Transactional
public class AccomodationService {

	@Autowired
	private HostService					hostService;

	@Autowired
	private AccomodationRepository		accomodationRepo;

	@Autowired
	private Validator					validator;

	@Autowired
	private BookingAccomodationService	bookingAccomodationService;

	@Autowired
	private CleaningTaskService			cleaningTaskService;


	// ---------- public class methods

	public Collection<Accomodation> findAll() {
		Assert.isTrue(LoginService.getPrincipal() != null);
		return this.accomodationRepo.findAll();
	}

	public Accomodation findOne(final int id) {
		return this.accomodationRepo.findOne(id);
	}

	public Accomodation create() {
		final Accomodation a = new Accomodation();
		final UserAccount userL = LoginService.getPrincipal();
		final Host host = this.hostService.getHostByUserAccountId(userL.getId());
		Assert.notNull(host);
		a.setHost(host);
		return a;
	}

	public Accomodation save(final Accomodation w) {
		return this.accomodationRepo.save(w);
	}

	public Accomodation reconstruct(final Accomodation accomodation, final BindingResult binding) {
		final Accomodation accomodationNew = this.create();

		accomodationNew.setAddress(accomodation.getAddress());
		accomodationNew.setDescription(accomodation.getDescription());
		accomodationNew.setMaxPeople(accomodation.getMaxPeople());
		accomodationNew.setPictures(accomodation.getPictures());
		accomodationNew.setPlace(accomodation.getPlace());
		accomodationNew.setPricePerNight(accomodation.getPricePerNight());
		accomodationNew.setRating(accomodation.getRating());

		final UserAccount userL = LoginService.getPrincipal();
		final Host host = this.hostService.getHostByUserAccountId(userL.getId());
		Assert.notNull(host);
		accomodationNew.setHost(host);

		this.validator.validate(accomodationNew, binding);

		return accomodationNew;
	}

	public Accomodation reconstructR(final Accomodation accomodation, final BindingResult binding) {

		final Accomodation accomodationNew = this.findOne(accomodation.getId());

		this.validator.validate(accomodation, binding);

		if (!binding.hasErrors()) {
			accomodationNew.setAddress(accomodation.getAddress());
			accomodationNew.setDescription(accomodation.getDescription());
			accomodationNew.setMaxPeople(accomodation.getMaxPeople());
			accomodationNew.setPictures(accomodation.getPictures());
			accomodationNew.setPlace(accomodation.getPlace());
			accomodationNew.setPricePerNight(accomodation.getPricePerNight());
			accomodationNew.setRating(accomodation.getRating());
		}
		return accomodationNew;
	}

	public Collection<Accomodation> getHostAccomodation() {
		final Host actor = this.hostService.getHostByUserAccountId(LoginService.getPrincipal().getId());
		Assert.isTrue(actor != null);
		return this.accomodationRepo.getAccomodationsByActor(actor.getId());
	}

	public void delete(final Accomodation accomodation) {
		final Host actor = this.hostService.getHostByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(actor);
		Assert.isTrue(accomodation.getHost().equals(actor));
		Assert.isTrue(!this.isReserved(accomodation));

		final List<BookingAccomodation> bookingAccomodation = this.accomodationRepo.findAllByAccomodationId(accomodation.getId());
		for (int i = 0; i < bookingAccomodation.size(); i++)
			this.bookingAccomodationService.delete(bookingAccomodation.get(i));

		//Boorado de C.Taks
		final Collection<CleaningTask> items = this.cleaningTaskService.getCleaningTaskAccomodation(accomodation.getId());
		if (items != null && !items.isEmpty())
			for (final CleaningTask item : items)
				this.cleaningTaskService.delete(item);

		final Collection<FinderAccomodation> finders = this.accomodationRepo.getAccomodationFinders(accomodation.getId());
		System.out.println(finders);
		if (finders != null && !finders.isEmpty())
			for (final FinderAccomodation item : finders)
				item.getAccomodations().remove(accomodation);

		this.accomodationRepo.delete(accomodation);
	}

	public Boolean isReserved(final Accomodation accomodation) {
		Boolean res = false;
		final List<BookingAccomodation> bookingAccomodation = this.accomodationRepo.findAllByAccomodationId(accomodation.getId());

		for (int i = 0; i < bookingAccomodation.size(); i++) {

			final Date endDate = bookingAccomodation.get(i).getEndDate();
			final Date nowDate = new Date();

			if (nowDate.before(endDate)) {
				res = true;
				break;
			}
		}
		return res;
	}

	public Accomodation findByAddress(final String address) {
		return this.accomodationRepo.findByAddress(address);
	}

	public Collection<String> getAddressAccomodationsByActor(final int id) {
		return this.accomodationRepo.getAddressAccomodationsByActor(id);
	}

	public List<Host> getAccomodationsByCustomerId(final int id) {

		final List<Host> res = new ArrayList<>();
		res.addAll(this.accomodationRepo.getAccomodationsByCustomerId(id));
		return res;
	}

	public void deleteAllByHost(final Host host) {
		final Collection<Accomodation> items = this.getHostAccomodation();
		if (items != null && !items.isEmpty())
			for (final Accomodation item : items)
				this.delete(item);

	}

	public Accomodation getLoggedHostAccomodation(final int accomodationId) {
		final Accomodation ac = this.accomodationRepo.findOne(accomodationId);
		final Host host = this.hostService.getHostLogin();
		Assert.isTrue(host.getId() == ac.getHost().getId());
		return ac;
	}
}
