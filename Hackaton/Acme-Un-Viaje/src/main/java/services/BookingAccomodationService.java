
package services;

import java.util.Collection;

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
		Assert.isTrue(!this.isReserved(bookingAccomodation), "The accomodation is already reserved for those days");
		return this.bookingAccomodationRepository.save(bookingAccomodation);
	}

	public BookingAccomodation findOne(final int bookingAccomodationId) {
		return this.bookingAccomodationRepository.findOne(bookingAccomodationId);
	}

	public boolean isReserved(final BookingAccomodation bookingAccomodation) {
		boolean res = false;
		final Collection<BookingAccomodation> bookings = this.bookingAccomodationRepository.getAccomodationBookings(bookingAccomodation.getAccomodation().getId());
		for (final BookingAccomodation b : bookings)
			if ((b.getStartDate().before(bookingAccomodation.getStartDate()) || b.getEndDate().after(bookingAccomodation.getStartDate()))
				&& (b.getStartDate().before(bookingAccomodation.getEndDate()) || b.getEndDate().after(bookingAccomodation.getEndDate()))) {
				res = true;
				break;
			}
		return res;
	}

	public BookingAccomodation reconstructForm(final BookingAccForm form, final BindingResult binding) {
		final BookingAccomodation res = this.create();
		res.setAccomodation(form.getAccomodation());
		res.setStartDate(form.getStartDate());
		res.setEndDate(form.getEndDate());

		this.validator.validate(res, binding);
		return res;
	}

}
