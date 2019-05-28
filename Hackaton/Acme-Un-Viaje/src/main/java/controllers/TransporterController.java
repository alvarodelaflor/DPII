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
import java.util.List;

import javax.validation.Valid;

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
import domain.Transporter;
import domain.Valoration;
import forms.RegisterActor;
import security.LoginService;
import services.ConfigService;
import services.CustomerService;
import services.TransporterService;
import services.ValorationService;

@Controller
@RequestMapping("/transporter")
public class TransporterController extends AbstractController {

	@Autowired
	private TransporterService	transporterService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ValorationService	valorationService;

	@Autowired
	private ConfigService		configService;


	// Constructors -----------------------------------------------------------

	public TransporterController() {
		super();
	}

	// RATE CUSTOMER
	// ---------------------------------------------------------------
	@RequestMapping(value = "/rateCustomer", method = RequestMethod.GET)
	public ModelAndView rateCustomer(@RequestParam(value = "customerId", defaultValue = "-1") final int customerId) {

		ModelAndView res;

		try {

			final Transporter logged = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId());
			final List<Customer> customers = this.customerService.getCustomersByTranspoterId(logged.getId());
			final Customer customer = this.customerService.findOne(customerId);
			Assert.isTrue(customers.contains(customer), "get.hack.error");

			res = new ModelAndView("transporter/rateCustomer");
			final Valoration valoration = this.valorationService.create();
			valoration.setTransporter(logged);
			valoration.setCustomer(customer);
			res.addObject("valoration", valoration);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
	@RequestMapping(value = "/rateCustomer", method = RequestMethod.POST, params = "save")
	public ModelAndView rateCustomer(@Valid final Valoration valoration, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors()) {

			res = new ModelAndView("transporter/rateCustomer");
			res.addObject("valoration", valoration);
		} else
			try {

				this.valorationService.save(valoration);
				res = new ModelAndView("redirect:myCustomers.do");
			} catch (final Throwable oops) {

				res = new ModelAndView("redirect:/welcome/index.do");
			}

		return res;
	}

	// MY CUSTOMERS
	// ---------------------------------------------------------------
	@RequestMapping(value = "/myCustomers", method = RequestMethod.GET)
	public ModelAndView myCustomers() {

		ModelAndView res;

		try {

			final Transporter transporter = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId());
			res = new ModelAndView("transporter/myCustomers");
			res.addObject("customers", this.customerService.getCustomersByTranspoterId(transporter.getId()));
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
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
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
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
			result = new ModelAndView("transporter/create");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
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
		final CreditCard creditCard = transporter.getCreditCard();
		result = new ModelAndView("transporter/edit");
		result.addObject("transporter", transporter);
		result.addObject("creditCard", creditCard);
		final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
		result.addObject("makes", makes);
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
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
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

	//EXPORT
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody Transporter export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		Transporter result = new Transporter();
		result = this.transporterService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		return result;
	}

	//DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int transporterId) {
		ModelAndView result;

		final Transporter transporter = this.transporterService.findOne(transporterId);
		System.out.println("Transporter encontrado: " + transporter);
		if (this.transporterService.findOne(transporterId) == null || LoginService.getPrincipal().getId() != transporter.getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(transporter, "transporter.null");

			try {
				this.transporterService.delete(transporter);
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (final Exception e) {
				e.printStackTrace();
				result = new ModelAndView("redirect:/j_spring_security_logout");
			}
		}
		return result;
	}

}
