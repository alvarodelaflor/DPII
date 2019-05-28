
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BookingAccomodationService;
import domain.BookingAccomodation;

@Controller
@RequestMapping("/bookingAccomodation/customer")
public class BookingAccomodationCustomerController extends AbstractController {

	@Autowired
	private BookingAccomodationService	bookingAccomodationService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false, value = "bookingId") final Integer bookingId) {
		if (bookingId == null)
			return new ModelAndView("redirect:/welcome/index.do");

		ModelAndView res = null;
		try {
			final BookingAccomodation bookingAccomodation = this.bookingAccomodationService.findOne(bookingId);
			if (bookingAccomodation == null)
				res = new ModelAndView("redirect:/welcome/index.do");
			else {
				res = new ModelAndView("bookingAccomodation/customer/show");
				res.addObject("bookingAccomodation", bookingAccomodation);
			}
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
}
