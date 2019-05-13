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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CleanerService;
import domain.Cleaner;
import forms.RegisterActor;

@Controller
@RequestMapping("/cleaner")
public class CleanerController extends AbstractController {

	@Autowired
	private CleanerService	cleanerService;


	// Constructors -----------------------------------------------------------

	public CleanerController() {
		super();
	}

	// CREATE ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegisterActor registerActor = new RegisterActor();
			result = new ModelAndView("cleaner/create");
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		;
		return result;
	}

	// SAVE-CREATE ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterActor registerActor, final BindingResult binding) {
		ModelAndView result = null;
		final Cleaner cleaner = this.cleanerService.reconstructRegisterAsCleaner(registerActor, binding);
		if (binding.hasErrors())
			result = new ModelAndView("cleaner/create");
		else
			try {
				this.cleanerService.saveRegisterAsCleaner(cleaner);
				result = new ModelAndView("welcome/index");
				final SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final String moment = formatter.format(new Date());
				result.addObject("moment", moment);
			} catch (final Throwable oops) {
				//				if (oops.getMessage().equals("email.wrong"))
				//					result = this.createEditModelAndView(company, "email.wrong");
				//				else if (oops.getMessage().equals("error.email"))
				//					result = this.createEditModelAndView(company, "error.email");
				//				else
				result = this.createEditModelAndView(cleaner, "error.html");
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Cleaner actor, final String string) {
		ModelAndView result;

		result = new ModelAndView("cleaner/create");
		result.addObject("message", string);
		result.addObject("actor", actor);
		return result;
	}

}
