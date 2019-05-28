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

import java.text.DecimalFormat;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Customer;
import domain.TravelAgency;
import domain.TravelPack;
import security.LoginService;
import services.CustomerService;
import services.TravelAgencyService;
import services.TravelPackService;

@Controller
@RequestMapping("/travelPack/travelAgency")
public class TravelPackTravelAgencyController extends AbstractController {

	@Autowired
	private TravelAgencyService	travelAgencyService;

	@Autowired
	private TravelPackService	travelPackService;

	@Autowired
	private CustomerService		customerService;


	// Constructors -----------------------------------------------------------

	public TravelPackTravelAgencyController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "customerId", defaultValue = "-1") final int customerId) {
		ModelAndView result;
		try {
			final Customer customer = this.customerService.findOne(customerId);
			Assert.notNull(customer);
			TravelPack travelPack;
			travelPack = this.travelPackService.create();
			travelPack.setCustomer(customer);
			result = new ModelAndView("travelPack/travelAgency/create");
			result.addObject("travelPack", travelPack);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final TravelPack travelPack, final BindingResult binding) {
		ModelAndView result;

		final TravelPack travelPackN = this.travelPackService.reconstruct(travelPack, binding);

		try {
			if (binding.hasErrors()) {
				System.out.println(binding);
				result = new ModelAndView("travelPack/travelAgency/create");
				result.addObject("travelPack", travelPack);
			} else {
				Assert.isTrue(travelPack != null);
				final TravelPack savetravelPack = this.travelPackService.save(travelPackN);
				result = new ModelAndView("redirect:/travelPack/travelAgency/list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "travelPackId", defaultValue = "-1") final int travelPackId) {
		ModelAndView result;
		try {
			final TravelPack travelPack = this.travelPackService.findOne(travelPackId);
			System.err.println(travelPack);
			Assert.notNull(travelPack);
			this.travelPackService.delete(travelPack);
			result = new ModelAndView("redirect:/travelPack/travelAgency/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<TravelPack> travelPacks = this.travelPackService.getTravelAgencyPacks();
			result = new ModelAndView("travelPack/travelAgency/list");
			result.addObject("travelPacks", travelPacks);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false, value = "travelPackId") final Integer travelPackId) {
		if (travelPackId == null)
			return new ModelAndView("redirect:/welcome/index.do");

		ModelAndView result;
		try {

			final TravelPack travelPack = this.travelPackService.findOne(travelPackId);
			final String price = new DecimalFormat("#.##").format(this.travelPackService.calculatePrice(travelPack));
			result = new ModelAndView("travelPack/travelAgency/show");
			result.addObject("price", price);
			result.addObject("travelPack", travelPack);
			result.addObject("requestURI", "travelPack/travelAgency/show.do?travelPackId=" + travelPackId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "travelPackId", defaultValue = "-1") final int travelPackId) {
		ModelAndView result;
		final TravelAgency travelAgencyLogin = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
		try {
			final TravelPack travelPackDB = this.travelPackService.findOne(travelPackId);
			Assert.notNull(travelPackDB, "TravelPack not found in DB");
			Assert.notNull(travelAgencyLogin, "No travelAgency is login");
			Assert.isTrue(travelAgencyLogin.equals(travelPackDB.getTravelAgency()));
			result = new ModelAndView("travelPack/travelAgency/edit");
			result.addObject("travelPack", travelPackDB);
		} catch (final Exception e) {
			if (travelAgencyLogin != null)
				result = new ModelAndView("redirect:/travelPack/travelAgency/list.do");
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(TravelPack travelPack, final BindingResult binding) {
		ModelAndView result;
		try {
			travelPack = this.travelPackService.reconstruct(travelPack, binding);
		} catch (final Exception e) {
			System.out.println("Error e reconstruct de travelPack: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}

		if (binding.hasErrors()) {
			System.out.println("Error en TravelPackTravelAgencyController.java, binding: " + binding);
			result = new ModelAndView("travelPack/travelAgency/create");
			result.addObject("travelPack", travelPack);
		} else
			try {
				final TravelAgency travelAgencyLogin = this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
				Assert.notNull(travelAgencyLogin, "No travelAgency is login");
				Assert.isTrue(travelPack != null, "Travel pack doesn't exists");
				final TravelPack saveTravelPack = this.travelPackService.save(travelPack);
				result = new ModelAndView("redirect:/travelPack/show.do?travelPackId=" + saveTravelPack.getId());
				result.addObject("requestURI", "travelPack/travelAgency/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE TravelPackTravelAgencyController.java Throwable: " + oops);
				result = new ModelAndView("travelPack/travelAgency/edit");
				result.addObject("travelPack", travelPack);
				result.addObject("message", "travelPack.commit.error");
			}
		return result;
	}

}
