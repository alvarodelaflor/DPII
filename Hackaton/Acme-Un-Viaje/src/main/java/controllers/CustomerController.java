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

import domain.Customer;
import domain.CreditCard;
import forms.RegisterActorE;
import security.LoginService;
import services.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	@Autowired
	private CustomerService customerService;

	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// REGISTER AS CUSTOMER
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegisterActorE registerActorE = new RegisterActorE();
			result = new ModelAndView("customer/create");
			result.addObject("registerActorE", registerActorE);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		;
		return result;
	}

	// SAVE REGISTER AS CUSTOMER
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterActorE registerActorE, final BindingResult binding) {
		ModelAndView result = null;
		final Customer customer = this.customerService.reconstructRegisterAsCustomer(registerActorE, binding);
		if (binding.hasErrors()) {
			System.err.println(binding);
			result = new ModelAndView("customer/create");
		} else
			try {
				this.customerService.saveRegisterAsCustomer(customer);
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
		Customer customer;
		final int idUserAccount = LoginService.getPrincipal().getId();
		customer = this.customerService.getCustomerByUserAccountId(idUserAccount);
		Assert.notNull(customer);
		CreditCard creditCard = customer.getCreditCard();
		result = new ModelAndView("customer/edit");
		result.addObject("customer", customer);
		result.addObject("creditCard", creditCard);
		return result;
	}

	// SAVE EDIT DATA PERSONAL
	// ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Customer customer, final BindingResult binding) {
		ModelAndView result = null;

		customer = this.customerService.reconstructEditDataPeronal(customer, binding);

		if (binding.hasErrors()) {
			System.out.println("HAY ERRORES 2" + binding);
			result = new ModelAndView("customer/edit");

		} else
			try {
				this.customerService.saveRegisterAsCustomer(customer);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// SHOW CUSTOMER
	// -------------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		try {
			final int userLoggin = LoginService.getPrincipal().getId();
			final Customer registerActorE;
			registerActorE = this.customerService.getCustomerByUserAccountId(userLoggin);
			result = new ModelAndView("customer/show");
			result.addObject("registerActorE", registerActorE);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
