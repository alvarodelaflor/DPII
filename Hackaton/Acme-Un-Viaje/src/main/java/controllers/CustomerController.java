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

import domain.CreditCard;
import domain.Customer;
import forms.RegisterActorE;
import security.LoginService;
import services.ConfigService;
import services.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	@Autowired
	private CustomerService	customerService;

	@Autowired
	private ConfigService	configService;


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
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
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
			result = new ModelAndView("customer/create");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
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
		final CreditCard creditCard = customer.getCreditCard();
		result = new ModelAndView("customer/edit");
		result.addObject("customer", customer);
		result.addObject("creditCard", creditCard);
		final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
		result.addObject("makes", makes);
		return result;
	}

	// SAVE EDIT DATA PERSONAL
	// ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Customer customer, final BindingResult binding) {
		ModelAndView result = null;

		customer = this.customerService.reconstructEditDataPeronal(customer, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("customer/edit");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
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

	//EXPORT
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody Customer export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		Customer result = new Customer();
		result = this.customerService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		return result;
	}

	//DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int customerId) {
		ModelAndView result;

		final Customer customer = this.customerService.findOne(customerId);
		System.out.println("Customer encontrado: " + customer);
		if (this.customerService.findOne(customerId) == null || LoginService.getPrincipal().getId() != customer.getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(customer, "customer.null");

			try {
				this.customerService.delete(customer);
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (final Exception e) {
				e.printStackTrace();
				result = new ModelAndView("redirect:/j_spring_security_logout");
			}
		}
		return result;
	}

}
