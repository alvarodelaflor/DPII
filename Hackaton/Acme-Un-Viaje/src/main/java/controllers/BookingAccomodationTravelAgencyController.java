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

import java.util.Collection;

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
import domain.TravelPack;
import forms.BookingAccForm;
import services.AccomodationService;
import services.BookingAccomodationService;
import services.TravelAgencyService;
import services.TravelPackService;

@Controller
@RequestMapping("/bookingAccomodation/travelAgency")
public class BookingAccomodationTravelAgencyController extends AbstractController {

	@Autowired
	private TravelAgencyService			travelAgencyService;

	@Autowired
	private BookingAccomodationService	bookingAccomodationService;

	@Autowired
	private AccomodationService			accomodationService;

	@Autowired
	private TravelPackService			travelPackService;


	// Constructors -----------------------------------------------------------

	public BookingAccomodationTravelAgencyController() {
		super();
	}

	// ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "accomodationId", defaultValue = "-1") final int accomodationId) {
		ModelAndView result;
		try {
			final BookingAccForm form = new BookingAccForm();
			final Accomodation acc = this.accomodationService.findOne(accomodationId);
			Assert.notNull(acc, "The accomodation does not exist");
			form.setAccomodation(acc);
			result = new ModelAndView("bookingAccomodation/travelAgency/create");
			result.addObject("form", form);
			final Collection<TravelPack> packs = this.travelPackService.getTravelAgencyDraftPacks();
			result.addObject("packs", packs);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	// ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final BookingAccForm form, final BindingResult binding) {
		ModelAndView result = null;

		try {
			final BookingAccomodation book = this.bookingAccomodationService.reconstructForm(form, binding);
			if (binding.hasErrors()) {
				System.err.println(binding);
				result = new ModelAndView("bookingAccomodation/travelAgency/create");
				result.addObject("form", form);
				final Collection<TravelPack> packs = this.travelPackService.getTravelAgencyDraftPacks();
				result.addObject("packs", packs);
				result.addObject("message", "error.createbAccomodation");
			} else {

				final BookingAccomodation bc = this.bookingAccomodationService.save(book);
				final TravelPack pack = this.travelPackService.findOne(form.getTravelPackId());
				pack.getAccomodations().add(bc);
				result = new ModelAndView("redirect:/travelPack/travelAgency/show.do?travelPackId=" + form.getTravelPackId());
			}
		} catch (final Throwable oops) {
			if (oops.getMessage() != null) {
				result = new ModelAndView("bookingAccomodation/travelAgency/create");
				result.addObject("form", form);
				final Collection<TravelPack> packs = this.travelPackService.getTravelAgencyDraftPacks();
				result.addObject("packs", packs);
				result.addObject("message", oops.getMessage());
			} else {
				oops.printStackTrace();
				result = new ModelAndView("redirect:/welcome/index.do");
			}
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
