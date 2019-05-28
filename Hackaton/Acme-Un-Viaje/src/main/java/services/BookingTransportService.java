
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BookingTransportRepository;
import domain.BookingTransport;
import forms.BookingTransportForm;

@Service
@Transactional
public class BookingTransportService {

	@Autowired
	private BookingTransportRepository	bookingTransportRepository;

	@Autowired
	private TravelPackService			travelPackService;

	@Autowired
	private Validator					validator;


	public void delete(final BookingTransport bAccomodation) {
		this.bookingTransportRepository.delete(bAccomodation);
	}

	public BookingTransport create() {
		return new BookingTransport();
	}

	public BookingTransport save(final BookingTransport bookingTransport) {
		Assert.isTrue(!this.isReserved(bookingTransport), "error.transportAlreadyReserved");
		return this.bookingTransportRepository.save(bookingTransport);
	}

	public boolean isReserved(final BookingTransport bookingTransport) {
		boolean res = false;
		final Collection<BookingTransport> bookings = this.bookingTransportRepository.getTransportBookings(bookingTransport.getTransport().getId());
		for (final BookingTransport b : bookings)
			if (b.getDate().getDate() == bookingTransport.getDate().getDate() && b.getDate().getYear() == bookingTransport.getDate().getYear() && b.getDate().getMonth() == bookingTransport.getDate().getMonth()) {
				res = true;
				break;
			}
		return res;
	}

	public BookingTransport reconstructForm(final BookingTransportForm form, final BindingResult binding) {
		final BookingTransport res = this.create();
		res.setTransport(form.getTransport());
		res.setDate(form.getDate());

		this.validator.validate(res, binding);
		return res;
	}

	public BookingTransport findOne(final int id) {
		return this.bookingTransportRepository.findOne(id);
	}
}
