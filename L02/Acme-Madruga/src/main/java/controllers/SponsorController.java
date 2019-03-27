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
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
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

	//Nuevo
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int sponsorId) {
		ModelAndView result;

		final Sponsor sponsor = this.sponsorService.findOne(sponsorId);
		System.out.println("Sponsor encontrado: " + sponsor);
		if (this.sponsorService.findOne(sponsorId) == null || LoginService.getPrincipal().getId() != sponsor.getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(sponsor, "sponsor.null");

			try {
				this.sponsorService.delete(sponsor);
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (final Exception e) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody
	Sponsor export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		Sponsor result = new Sponsor();
		result = this.sponsorService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		try {
			final int userLoggin = LoginService.getPrincipal().getId();
			final Sponsor sponsor;
			sponsor = this.sponsorService.getSponsorByUserId(userLoggin);
			Assert.isTrue(sponsor != null);

			result = new ModelAndView("sponsor/show");
			result.addObject("sponsor", sponsor);

			result.addObject("requestURI", "sponsor/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}
}
