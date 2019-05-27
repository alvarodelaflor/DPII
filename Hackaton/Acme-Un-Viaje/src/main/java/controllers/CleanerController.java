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
import java.util.Collection;
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

import domain.Cleaner;
import domain.CreditCard;
import forms.RegisterActor;
import security.LoginService;
import services.CleanerService;
import services.ConfigService;

@Controller
@RequestMapping("/cleaner")
public class CleanerController extends AbstractController {

	@Autowired
	private CleanerService	cleanerService;

	@Autowired
	private ConfigService	configService;


	// Constructors -----------------------------------------------------------

	public CleanerController() {
		super();
	}

	// REGISTER AS CLEANER
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegisterActor registerActor = new RegisterActor();
			result = new ModelAndView("cleaner/create");
			result.addObject("registerActor", registerActor);
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		;
		return result;
	}

	// SAVE REGISTER AS CLEANER
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterActor registerActor, final BindingResult binding) {
		ModelAndView result = null;
		final Cleaner cleaner = this.cleanerService.reconstructRegisterAsCleaner(registerActor, binding);
		if (binding.hasErrors()) {
			System.err.println(binding);
			result = new ModelAndView("cleaner/create");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} else
			try {
				this.cleanerService.saveRegisterAsCleaner(cleaner);
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
		Cleaner cleaner;
		final int idUserAccount = LoginService.getPrincipal().getId();
		cleaner = this.cleanerService.getCleanerByUserAccountId(idUserAccount);
		Assert.notNull(cleaner);
		final CreditCard creditCard = cleaner.getCreditCard();
		result = new ModelAndView("cleaner/edit");
		result.addObject("cleaner", cleaner);
		result.addObject("creditCard", creditCard);
		final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
		result.addObject("makes", makes);
		return result;
	}

	// SAVE EDIT DATA PERSONAL
	// ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Cleaner cleaner, final BindingResult binding) {
		ModelAndView result = null;

		cleaner = this.cleanerService.reconstructEditDataPeronal(cleaner, binding);

		if (binding.hasErrors()) {
			System.out.println("HAY ERRORES 2" + binding);
			result = new ModelAndView("cleaner/edit");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} else
			try {
				this.cleanerService.saveRegisterAsCleaner(cleaner);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// SHOW CLEANER
	// -------------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		try {
			final int userLoggin = LoginService.getPrincipal().getId();
			final Cleaner registerActor;
			registerActor = this.cleanerService.getCleanerByUserAccountId(userLoggin);
			result = new ModelAndView("cleaner/show");
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody Cleaner export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		final ModelAndView nothing;
		Cleaner result = new Cleaner();
		result = this.cleanerService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		return result;
	}

	//Nuevo
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int cleanerId) {
		ModelAndView result;

		final Cleaner cleaner = this.cleanerService.findOne(cleanerId);
		System.out.println("Cleaner encontrado: " + cleaner);
		if (this.cleanerService.findOne(cleanerId) == null || LoginService.getPrincipal().getId() != cleaner.getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(cleaner, "cleaner.null");

			try {
				this.cleanerService.delete(cleaner);
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (final Exception e) {
				e.printStackTrace();
				result = new ModelAndView("redirect:/j_spring_security_logout");
			}
		}
		return result;
	}

}
