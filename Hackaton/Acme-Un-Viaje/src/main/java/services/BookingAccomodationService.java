
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.BookingAccomodation;
import forms.BookingAccForm;
import repositories.BookingAccomodationRepository;

@Service
@Transactional
public class BookingAccomodationService {

	@Autowired
	private BookingAccomodationRepository	bookingAccomodationRepository;

	@Autowired
	private TravelPackService				travelPackService;

	@Autowired
	private Validator						validator;


	public void delete(final BookingAccomodation bAccomodation) {
		this.bookingAccomodationRepository.delete(bAccomodation);
	}

	public BookingAccomodation create() {
		return new BookingAccomodation();
	}

	public BookingAccomodation save(final BookingAccomodation bookingAccomodation) {
		System.out.println("Service: " + bookingAccomodation.getAccomodation().toString());
		Assert.isTrue(!this.isReserved(bookingAccomodation), "error.accomodationAlreadyReserved");
		Assert.isTrue(this.validateDate(bookingAccomodation.getStartDate()), "error.pastDates");
		Assert.isTrue(this.validateDate(bookingAccomodation.getEndDate()), "error.pastDates");
		Assert.isTrue(bookingAccomodation.getStartDate().before(bookingAccomodation.getEndDate()), "error.startDateAfterEndDate");
		return this.bookingAccomodationRepository.save(bookingAccomodation);
	}

	public BookingAccomodation findOne(final int bookingAccomodationId) {
		return this.bookingAccomodationRepository.findOne(bookingAccomodationId);
	}

	public boolean isReserved(final BookingAccomodation bookingAccomodation) {
		boolean res = false;
		final Collection<BookingAccomodation> bookings = this.bookingAccomodationRepository.getAccomodationBookings(bookingAccomodation.getAccomodation().getId());
		for (final BookingAccomodation b : bookings)
			if ((b.getStartDate().before(bookingAccomodation.getStartDate()) && b.getEndDate().after(bookingAccomodation.getStartDate()))
				|| (b.getStartDate().before(bookingAccomodation.getEndDate()) && b.getEndDate().after(bookingAccomodation.getEndDate()))
				|| (b.getStartDate().after(bookingAccomodation.getStartDate()) && b.getEndDate().before(bookingAccomodation.getEndDate()))
				|| (b.getStartDate().getDate() == bookingAccomodation.getStartDate().getDate() && b.getStartDate().getYear() == bookingAccomodation.getStartDate().getYear() && b.getStartDate().getMonth() == bookingAccomodation.getStartDate().getMonth()
					&& b.getStartDate().getDate() == bookingAccomodation.getStartDate().getDate() && b.getStartDate().getYear() == bookingAccomodation.getStartDate().getYear()
					&& b.getStartDate().getMonth() == bookingAccomodation.getStartDate().getMonth())) {
				res = true;
				break;
			}
		return res;
	}

	public Collection<BookingAccomodation> getAccomodationBookings(final int id) {

		return this.bookingAccomodationRepository.getAccomodationBookings(id);
	}
	public BookingAccomodation reconstructForm(final BookingAccForm form, final BindingResult binding) {
		final BookingAccomodation res = this.create();
		res.setAccomodation(form.getAccomodation());
		res.setStartDate(form.getStartDate());
		res.setEndDate(form.getEndDate());

		this.validator.validate(res, binding);
		return res;
	}

	private Boolean validateDate(final Date date) {
		final Date now = new Date();
		final Boolean res = now.before(date);
		return res;
	}

}
