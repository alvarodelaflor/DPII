
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.jboss.logging.annotations.Message.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Accomodation;
import domain.BookingAccomodation;
import domain.Host;
import repositories.AccomodationRepository;
import repositories.BookingAccomodationRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class BookingAccomodationService {

	@Autowired
	private BookingAccomodationRepository bookingAccomodationRepository;

		public void delete(final BookingAccomodation bAccomodation) {
		this.bookingAccomodationRepository.delete(bAccomodation);
	}


}
