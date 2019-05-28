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

import domain.BookingTransport;
import domain.Transport;
import domain.TravelPack;
import forms.BookingTransportForm;
import services.BookingTransportService;
import services.TransportService;
import services.TravelAgencyService;
import services.TravelPackService;

@Controller
@RequestMapping("/bookingTransport/travelAgency")
public class BookingTransportTravelAgencyController extends AbstractController {

	@Autowired
	private TravelAgencyService		travelAgencyService;

	@Autowired
	private BookingTransportService	bookingTransportService;

	@Autowired
	private TransportService		transportService;

	@Autowired
	private TravelPackService		travelPackService;


	// Constructors -----------------------------------------------------------

	public BookingTransportTravelAgencyController() {
		super();
	}

	// ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "transportId", defaultValue = "-1") final int transportId) {
		ModelAndView result;
		try {
			final BookingTransportForm form = new BookingTransportForm();
			final Transport acc = this.transportService.findOne(transportId);
			Assert.notNull(acc, "The transport does not exist");
			form.setTransport(acc);
			result = new ModelAndView("bookingTransport/travelAgency/create");
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
	public ModelAndView save(final BookingTransportForm form, final BindingResult binding) {
		ModelAndView result = null;
		final BookingTransport book = this.bookingTransportService.reconstructForm(form, binding);
		if (binding.hasErrors()) {
			System.err.println(binding);
			result = new ModelAndView("bookingTransport/travelAgency/create");
			result.addObject("form", form);
			final Collection<TravelPack> packs = this.travelPackService.getTravelAgencyDraftPacks();
			result.addObject("packs", packs);
			result.addObject("message", "error.createbTransport");
		} else
			try {

				final BookingTransport bc = this.bookingTransportService.save(book);
				final TravelPack pack = this.travelPackService.findOne(form.getTravelPackId());
				pack.getTransports().add(bc);
				result = new ModelAndView("redirect:/travelPack/travelAgency/show.do?travelPackId=" + form.getTravelPackId());

			} catch (final Throwable oops) {
				if (oops.getMessage() != null) {
					result = new ModelAndView("bookingTransport/travelAgency/create");
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
	public ModelAndView delete(@RequestParam(value = "bookingTransportId", defaultValue = "-1") final int bookingTransportId) {
		ModelAndView result;
		try {
			final BookingTransport bookingTransport = this.bookingTransportService.findOne(bookingTransportId);
			System.err.println(bookingTransport);
			Assert.notNull(bookingTransport);
			this.bookingTransportService.delete(bookingTransport);
			result = new ModelAndView("redirect:/bookingTransport/travelAgency/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
