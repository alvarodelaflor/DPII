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

import security.LoginService;
import services.AccomodationService;
import services.HostService;
import domain.Accomodation;
import domain.Host;

@Controller
@RequestMapping("/accomodation/host")
public class AccomodationHostController extends AbstractController {

	@Autowired
	private HostService			hostService;

	@Autowired
	private AccomodationService	accomodationService;


	// Constructors -----------------------------------------------------------

	public AccomodationHostController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			Accomodation accomodation;
			accomodation = this.accomodationService.create();
			result = new ModelAndView("accomodation/host/create");
			result.addObject("accomodation", accomodation);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Accomodation accomodation, final BindingResult binding) {
		ModelAndView result;

		try {
			final Accomodation accomodationN = this.accomodationService.reconstruct(accomodation, binding);
			if (binding.hasErrors()) {
				System.out.println(binding);
				result = new ModelAndView("accomodation/host/create");
				result.addObject("accomodation", accomodation);
			} else {
				Assert.isTrue(accomodation != null);
				final Accomodation saveaccomodation = this.accomodationService.save(accomodationN);
				result = new ModelAndView("redirect:/accomodation/host/list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Accomodation> accomodations = this.accomodationService.getHostAccomodation();
			result = new ModelAndView("accomodation/host/list");
			result.addObject("accomodations", accomodations);
			result.addObject("requestURI", "accomodation/host/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "accomodationId", defaultValue = "-1") final int accomodationId) {
		ModelAndView result;
		try {
			final Accomodation accomodation = this.accomodationService.findOne(accomodationId);
			System.err.println(accomodation);
			Assert.notNull(accomodation);
			this.accomodationService.delete(accomodation);
			result = new ModelAndView("redirect:/accomodation/host/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "accomodationId", defaultValue = "-1") final int accomodationId) {
		ModelAndView result;
		try {
			final Host actor = this.hostService.getHostByUserAccountId(LoginService.getPrincipal().getId());
			final Accomodation accomodation = this.accomodationService.findOne(accomodationId);
			Assert.notNull(actor);
			Assert.isTrue(accomodation.getHost().equals(actor));
			Assert.isTrue(!this.accomodationService.isReserved(accomodation));

			result = new ModelAndView("accomodation/host/edit");
			result.addObject("accomodation", accomodation);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(final Accomodation accomodation, final BindingResult binding) {
		ModelAndView result = null;

		final Accomodation accomodationN = this.accomodationService.reconstructR(accomodation, binding);

		try {

			if (binding.hasErrors())
				result = new ModelAndView("accomodation/host/edit");
			else {

				final Host actor = this.hostService.getHostByUserAccountId(LoginService.getPrincipal().getId());
				Assert.notNull(actor);
				Assert.isTrue(accomodationN.getHost().equals(actor));
				this.accomodationService.save(accomodationN);
				result = new ModelAndView("redirect:list.do");
			}

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "accomodationId", defaultValue = "-1") final int accomodationId) {
		ModelAndView result;
		try {
			final Accomodation accomodation = this.accomodationService.getLoggedHostAccomodation(accomodationId);
			final Boolean res = this.accomodationService.isReserved(accomodation);
			result = new ModelAndView("accomodation/host/show");
			result.addObject("accomodation", accomodation);
			result.addObject("res", res);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
