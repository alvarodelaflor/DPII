
package services;

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


	// ---------- public class methods

	public Collection<Accomodation> findAll() {
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

}
