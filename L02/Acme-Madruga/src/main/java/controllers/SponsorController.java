/*
 * BrotherhoodController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SponsorService;
import services.WelcomeService;
import domain.Sponsor;
import forms.RegistrationForm;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {

	@Autowired
	SponsorService	sponsorService;

	@Autowired
	ActorService	actorService;

	@Autowired
	WelcomeService	welcomeService;


	// Constructors -----------------------------------------------------------

	public SponsorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final RegistrationForm registrationForm;
		registrationForm = new RegistrationForm();
		result = new ModelAndView("sponsor/create");
		result.addObject("registrationForm", registrationForm);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegistrationForm registration, final BindingResult binding) {
		ModelAndView result;

		Sponsor sponsor;

		sponsor = this.sponsorService.reconstructR(registration, binding);

		if (binding.hasErrors())
			result = new ModelAndView("sponsor/create");
		else
			try {
				this.sponsorService.saveR(sponsor);

				SimpleDateFormat formatter;
				String moment;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());
				result = new ModelAndView("welcome/index");
				result.addObject("moment", moment);

			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createModelAndView(sponsor, "email.wrong");
				else
					result = this.createModelAndView(sponsor, "error.email");
			}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	private ModelAndView createModelAndView(final Sponsor sponsor, final String string) {
		ModelAndView result;

		result = new ModelAndView("sponsor/create");
		result.addObject("message", string);
		result.addObject("sponsor", sponsor);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

}
