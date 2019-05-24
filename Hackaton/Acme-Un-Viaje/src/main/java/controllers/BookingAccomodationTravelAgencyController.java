/*
 * CustomerController.java
 *
 * Copyright (C) 2018 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Accomodation;
import domain.BookingAccomodation;
import services.AccomodationService;
import services.BookingAccomodationService;
import services.TravelAgencyService;

@Controller
@RequestMapping("/bookinAccomodation/travelAgency")
public class BookingAccomodationTravelAgencyController extends AbstractController {

	@Autowired
	private TravelAgencyService			travelAgencyService;

	@Autowired
	private BookingAccomodationService	bookingAccomodationService;

	@Autowired
	private AccomodationService			accomodationService;


	// Constructors -----------------------------------------------------------

	public BookingAccomodationTravelAgencyController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "accomodationId", defaultValue = "-1") final int accomodationId) {
		ModelAndView result;
		try {
			final Accomodation acc = this.accomodationService.findOne(accomodationId);
			Assert.notNull(acc);
			BookingAccomodation bookingAccomodation;
			bookingAccomodation = this.bookingAccomodationService.create();
			bookingAccomodation.setAccomodation(acc);
			result = new ModelAndView("bookingAccomodation/travelAgency/create");
			result.addObject("bookingAccomodation", bookingAccomodation);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final BookingAccomodation bookingAccomodation, final BindingResult binding) {
		ModelAndView result;

		try {
			if (binding.hasErrors()) {
				System.out.println(binding);
				result = new ModelAndView("bookingAccomodation/travelAgency/create");
				result.addObject("bookingAccomodation", bookingAccomodation);
			} else {
				Assert.isTrue(bookingAccomodation != null);
				final BookingAccomodation savebookingAccomodation = this.bookingAccomodationService.save(bookingAccomodation);
				result = new ModelAndView("redirect:/bookingAccomodation/travelAgency/list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "bookingAccomodationId", defaultValue = "-1") final int bookingAccomodationId) {
		ModelAndView result;
		try {
			final BookingAccomodation bookingAccomodation = this.bookingAccomodationService.findOne(bookingAccomodationId);
			System.err.println(bookingAccomodation);
			Assert.notNull(bookingAccomodation);
			this.bookingAccomodationService.delete(bookingAccomodation);
			result = new ModelAndView("redirect:/bookingAccomodation/travelAgency/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
