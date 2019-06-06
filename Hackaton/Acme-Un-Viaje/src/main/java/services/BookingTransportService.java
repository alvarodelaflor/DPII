
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.BookingTransport;
import domain.Transport;
import forms.BookingTransportForm;
import repositories.BookingTransportRepository;

@Service
@Transactional
public class BookingTransportService {

	@Autowired
	private BookingTransportRepository	bookingTransportRepository;

	@Autowired
	private Validator					validator;


	public void delete(final BookingTransport bAccomodation) {
		final Transport transport = bAccomodation.getTransport();
		Integer newPlaces = transport.getReservedPlaces() - 1;
		newPlaces = newPlaces < 0 ? 0 : newPlaces;
		transport.setReservedPlaces(newPlaces);
		this.bookingTransportRepository.delete(bAccomodation);
	}

	public BookingTransport create() {
		return new BookingTransport();
	}

	public BookingTransport save(final BookingTransport bookingTransport) {
		Assert.isTrue(this.validateDate(bookingTransport.getDate()), "error.pastDates");
		Assert.isTrue(!this.isReserved(bookingTransport), "error.transportAlreadyReserved");
		final Transport transport = bookingTransport.getTransport();
		transport.setReservedPlaces(transport.getReservedPlaces() + 1);
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

	private Boolean validateDate(final Date date) {
		final Date now = new Date();
		final Boolean res = now.before(date);
		return res;
	}
}
