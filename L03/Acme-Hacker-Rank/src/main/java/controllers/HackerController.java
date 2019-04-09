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
import services.HackerService;
import domain.Hacker;
import forms.RegistrationForm;

@Controller
@RequestMapping("/hacker")
public class HackerController extends AbstractController {

	@Autowired
	private HackerService	hackerService;


	// Constructors -----------------------------------------------------------

	public HackerController() {
		super();
	}

	// CREATE ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegistrationForm registrationForm = new RegistrationForm();
			result = new ModelAndView("hacker/create");
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

		final Hacker hacker;

		hacker = this.hackerService.reconstructCreate(registrationForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = new ModelAndView("hacker/create");
		} else
			try {
				System.out.println("carmen: voy a guardar");
				final Hacker a = this.hackerService.saveCreate(hacker);
				System.out.println(a);
				SimpleDateFormat formatter;
				String moment;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());
				result = new ModelAndView("welcome/index");
				result.addObject("moment", moment);
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(hacker, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.createEditModelAndView(hacker, "error.email");
				else
					result = this.createEditModelAndView(hacker, "error.html");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final Hacker actor, final String string) {
		ModelAndView result;

		result = new ModelAndView("hacker/create");
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
			final Hacker hacker;
			hacker = this.hackerService.getHackerByUserAccountId(userLoggin);
			Assert.isTrue(hacker != null);
			result = new ModelAndView("hacker/show");
			result.addObject("hacker", hacker);
			result.addObject("requestURI", "hacker/show.do");
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
			Hacker hacker;
			final int idUserAccount = LoginService.getPrincipal().getId();
			hacker = this.hackerService.getHackerByUserAccountId(idUserAccount);
			Assert.notNull(hacker);
			result = new ModelAndView("hacker/edit");
			result.addObject("hacker", hacker);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SAVE-EDIT ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Hacker hacker, final BindingResult binding) {
		ModelAndView result;
		System.out.println(hacker);

		hacker = this.hackerService.reconstructEdit(hacker, binding);

		System.out.println("c" + binding.getAllErrors());

		if (binding.hasErrors()) {
			System.out.println("Carmen: Hay fallos " + binding);
			result = new ModelAndView("hacker/edit");
		} else
			try {
				hacker = this.hackerService.saveEdit(hacker);
				result = new ModelAndView("redirect:show.do");
				System.out.println(hacker);
				result.addObject("hacker", hacker);
			} catch (final Throwable oops) {
				System.out.println(oops);
				if (oops.getMessage().equals("email.wrong"))
					result = this.editModelAndView(hacker, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.editModelAndView(hacker, "error.email");
				else
					result = new ModelAndView("redirect:/welcome/index.do");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView editModelAndView(final Hacker hacker, final String message) {
		ModelAndView result;

		result = new ModelAndView("hacker/edit");
		result.addObject("message", message);
		result.addObject("hacker", hacker);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody
	Hacker export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		Hacker result = new Hacker();
		result = this.hackerService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		System.out.println(result);
		return result;
	}

	//Nuevo
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int hackerId) {
		ModelAndView result;

		final Hacker hacker = this.hackerService.findOne(hackerId);
		System.out.println("Hacker encontrado: " + hacker);
		if (this.hackerService.findOne(hackerId) == null || LoginService.getPrincipal().getId() != hacker.getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(hacker, "hacker.null");

			try {
				this.hackerService.delete(hacker);
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (final Exception e) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
}
