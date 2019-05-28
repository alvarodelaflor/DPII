
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BookingTransportService;
import domain.BookingTransport;

@Controller
@RequestMapping("/bookingTransport/customer")
public class BookingTransportCustomerController extends AbstractController {

	@Autowired
	private BookingTransportService	bookingTransportService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false, value = "bookingId") final Integer bookingId) {
		if (bookingId == null)
			return new ModelAndView("redirect:/welcome/index.do");

		ModelAndView res = null;
		try {
			final BookingTransport bookingTransport = this.bookingTransportService.findOne(bookingId);
			if (bookingTransport == null)
				res = new ModelAndView("redirect:/welcome/index.do");
			else {
				res = new ModelAndView("bookingTransport/customer/show");
				res.addObject("bookingTransport", bookingTransport);
			}
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
}
