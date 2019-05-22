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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Host;
import domain.CreditCard;
import forms.RegisterActor;
import security.LoginService;
import services.HostService;

@Controller
@RequestMapping("/host")
public class HostController extends AbstractController {

	@Autowired
	private HostService hostService;

	// Constructors -----------------------------------------------------------

	public HostController() {
		super();
	}

	// REGISTER AS HOST
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegisterActor registerActor = new RegisterActor();
			result = new ModelAndView("host/create");
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		;
		return result;
	}

	// SAVE REGISTER AS HOST
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterActor registerActor, final BindingResult binding) {
		ModelAndView result = null;
		final Host host = this.hostService.reconstructRegisterAsHost(registerActor, binding);
		if (binding.hasErrors()) {
			System.err.println(binding);
			result = new ModelAndView("host/create");
		} else
			try {
				this.hostService.saveRegisterAsHost(host);
				result = new ModelAndView("welcome/index");
				final SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final String moment = formatter.format(new Date());
				result.addObject("moment", moment);
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// EDIT DATA PERSONAL
	// ---------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Host host;
		final int idUserAccount = LoginService.getPrincipal().getId();
		host = this.hostService.getHostByUserAccountId(idUserAccount);
		Assert.notNull(host);
		CreditCard creditCard = host.getCreditCard();
		result = new ModelAndView("host/edit");
		result.addObject("host", host);
		result.addObject("creditCard", creditCard);
		return result;
	}

	// SAVE EDIT DATA PERSONAL
	// ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Host host, final BindingResult binding) {
		ModelAndView result = null;

		host = this.hostService.reconstructEditDataPeronal(host, binding);

		if (binding.hasErrors()) {
			System.out.println("HAY ERRORES 2" + binding);
			result = new ModelAndView("host/edit");

		} else
			try {
				this.hostService.saveRegisterAsHost(host);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// SHOW HOST
	// -------------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "hostId", defaultValue = "-1") final int hotId) {
		ModelAndView result;
		try {
			final Host registerActor;
			if (hotId == -1 ) {
				final int userLoggin = LoginService.getPrincipal().getId();
				registerActor = this.hostService.getHostByUserAccountId(userLoggin);
			} else {
				registerActor = this.hostService.findOne(hotId);
			}
			result = new ModelAndView("host/show");				
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
