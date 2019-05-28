
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.BookingTransportRepository;
import domain.BookingTransport;

@Service
@Transactional
public class BookingTransportService {

	@Autowired
	private BookingTransportRepository	bookingTransportRepository;


	public BookingTransport findOne(final int id) {
		return this.bookingTransportRepository.findOne(id);
	}
}
