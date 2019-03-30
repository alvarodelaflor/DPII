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

import security.LoginService;
import services.CompanyService;
import domain.Company;
import forms.RegistrationForm;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	@Autowired
	private CompanyService	companyService;


	// Constructors -----------------------------------------------------------

	public CompanyController() {
		super();
	}

	// CREATE ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegistrationForm registrationForm = new RegistrationForm();
			result = new ModelAndView("company/create");
			result.addObject("registrationForm", registrationForm);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	// SAVE-CREATE ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result = null;

		final Company company;

		company = this.companyService.reconstructCreate(registrationForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = new ModelAndView("company/create");
		} else
			try {
				System.out.println("carmen: voy a guardar");
				final Company a = this.companyService.saveCreate(company);
				System.out.println(a);
				SimpleDateFormat formatter;
				String moment;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());
				result = new ModelAndView("welcome/index");
				result.addObject("moment", moment);
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(company, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.createEditModelAndView(company, "error.email");
				else
					result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Company actor, final String string) {
		ModelAndView result;

		result = new ModelAndView("company/create");
		result.addObject("message", string);
		result.addObject("actor", actor);
		return result;
	}

	// SHOW ---------------------------------------------------------------		
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {

		ModelAndView result;
		try {
			final int userLoggin = LoginService.getPrincipal().getId();
			final Company company;
			company = this.companyService.getCompanyByUserAccountId(userLoggin);
			Assert.isTrue(company != null);
			result = new ModelAndView("company/show");
			result.addObject("company", company);
			result.addObject("requestURI", "company/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	// EDIT ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		try {
			Company company;
			final int idUserAccount = LoginService.getPrincipal().getId();
			company = this.companyService.getCompanyByUserAccountId(idUserAccount);
			Assert.notNull(company);
			result = new ModelAndView("company/edit");
			result.addObject("company", company);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	// SAVE-EDIT ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Company company, final BindingResult binding) {
		ModelAndView result;
		System.out.println(company);

		company = this.companyService.reconstructEdit(company, binding);

		System.out.println("c" + binding.getAllErrors());

		if (binding.hasErrors()) {
			System.out.println("Carmen: Hay fallos " + binding);
			result = new ModelAndView("company/edit");
		} else
			try {
				company = this.companyService.saveEdit(company);
				result = new ModelAndView("redirect:show.do");
				result.addObject("company", company);
			} catch (final Throwable oops) {
				System.out.println(oops);
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(company, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.createEditModelAndView(company, "error.email");
				else
					result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}
}
