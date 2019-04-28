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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.RookieService;
import domain.Rookie;
import forms.RegistrationForm;

@Controller
@RequestMapping("/rookie")
public class RookieController extends AbstractController {

	@Autowired
	private RookieService	rookieService;


	// Constructors -----------------------------------------------------------

	public RookieController() {
		super();
	}

	// CREATE ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegistrationForm registrationForm = new RegistrationForm();
			result = new ModelAndView("rookie/create");
			result.addObject("registrationForm", registrationForm);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SAVE-CREATE ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result = null;

		final Rookie rookie;

		rookie = this.rookieService.reconstructCreate(registrationForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = new ModelAndView("rookie/create");
		} else
			try {
				System.out.println("carmen: voy a guardar");
				final Rookie a = this.rookieService.saveCreate(rookie);
				System.out.println(a);
				SimpleDateFormat formatter;
				String moment;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());
				result = new ModelAndView("welcome/index");
				result.addObject("moment", moment);
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(rookie, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.createEditModelAndView(rookie, "error.email");
				else
					result = this.createEditModelAndView(rookie, "error.html");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final Rookie actor, final String string) {
		ModelAndView result;

		result = new ModelAndView("rookie/create");
		result.addObject("message", string);
		result.addObject("actor", actor);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SHOW ---------------------------------------------------------------		
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {

		ModelAndView result;
		try {
			final int userLoggin = LoginService.getPrincipal().getId();
			final Rookie rookie;
			rookie = this.rookieService.getRookieByUserAccountId(userLoggin);
			Assert.isTrue(rookie != null);
			result = new ModelAndView("rookie/show");
			result.addObject("rookie", rookie);
			result.addObject("requestURI", "rookie/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// EDIT ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		try {
			Rookie rookie;
			final int idUserAccount = LoginService.getPrincipal().getId();
			rookie = this.rookieService.getRookieByUserAccountId(idUserAccount);
			Assert.notNull(rookie);
			result = new ModelAndView("rookie/edit");
			result.addObject("rookie", rookie);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SAVE-EDIT ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Rookie rookie, final BindingResult binding) {
		ModelAndView result;
		System.out.println(rookie);

		rookie = this.rookieService.reconstructEdit(rookie, binding);

		System.out.println("c" + binding.getAllErrors());

		if (binding.hasErrors()) {
			System.out.println("Carmen: Hay fallos " + binding);
			result = new ModelAndView("rookie/edit");
		} else
			try {
				rookie = this.rookieService.saveEdit(rookie);
				result = new ModelAndView("redirect:show.do");
				System.out.println(rookie);
				result.addObject("rookie", rookie);
			} catch (final Throwable oops) {
				System.out.println(oops);
				if (oops.getMessage().equals("email.wrong"))
					result = this.editModelAndView(rookie, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.editModelAndView(rookie, "error.email");
				else
					result = new ModelAndView("redirect:/welcome/index.do");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView editModelAndView(final Rookie rookie, final String message) {
		ModelAndView result;

		result = new ModelAndView("rookie/edit");
		result.addObject("message", message);
		result.addObject("rookie", rookie);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody
	Rookie export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		Rookie result = new Rookie();
		result = this.rookieService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		System.out.println(result);
		return result;
	}

	//Nuevo
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int rookieId) {
		ModelAndView result;

		final Rookie rookie = this.rookieService.findOne(rookieId);
		System.out.println("Rookie encontrado: " + rookie);
		if (this.rookieService.findOne(rookieId) == null || LoginService.getPrincipal().getId() != rookie.getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(rookie, "rookie.null");

			try {
				this.rookieService.delete(rookie);
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (final Exception e) {
				result = new ModelAndView("redirect:/j_spring_security_logout");
			}
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
}
