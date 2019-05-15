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
import org.springframework.web.servlet.ModelAndView;

import domain.Transporter;
import domain.CreditCard;
import forms.RegisterActor;
import security.LoginService;
import services.TransporterService;

@Controller
@RequestMapping("/transporter")
public class TransporterController extends AbstractController {

	@Autowired
	private TransporterService transporterService;

	// Constructors -----------------------------------------------------------

	public TransporterController() {
		super();
	}

	// REGISTER AS TRASNSPORTER
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegisterActor registerActor = new RegisterActor();
			result = new ModelAndView("transporter/create");
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		;
		return result;
	}

	// SAVE REGISTER AS TRASNSPORTER
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterActor registerActor, final BindingResult binding) {
		ModelAndView result = null;
		final Transporter transporter = this.transporterService.reconstructRegisterAsTransporter(registerActor, binding);
		if (binding.hasErrors()) {
			System.err.println(binding);
			result = new ModelAndView("transporter/create");
		} else
			try {
				this.transporterService.saveRegisterAsTransporter(transporter);
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
		Transporter transporter;
		final int idUserAccount = LoginService.getPrincipal().getId();
		transporter = this.transporterService.getTransporterByUserAccountId(idUserAccount);
		Assert.notNull(transporter);
		CreditCard creditCard = transporter.getCreditCard();
		result = new ModelAndView("transporter/edit");
		result.addObject("transporter", transporter);
		result.addObject("creditCard", creditCard);
		return result;
	}

	// SAVE EDIT DATA PERSONAL
	// ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Transporter transporter, final BindingResult binding) {
		ModelAndView result = null;

		transporter = this.transporterService.reconstructEditDataPeronal(transporter, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("transporter/edit");
		} else
			try {
				this.transporterService.saveRegisterAsTransporter(transporter);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// SHOW TRASNSPORTER
	// -------------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		try {
			final int userLoggin = LoginService.getPrincipal().getId();
			final Transporter registerActor;
			registerActor = this.transporterService.getTransporterByUserAccountId(userLoggin);
			result = new ModelAndView("transporter/show");
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
