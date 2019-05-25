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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Cleaner;
import domain.CreditCard;
import domain.Host;
import forms.RegisterActor;
import security.LoginService;
import services.AccomodationService;
import services.CleanerService;
import services.ConfigService;
import services.CustomerService;
import services.HostService;
import services.JobApplicationService;
import services.ValorationService;
import domain.Cleaner;
import domain.CreditCard;
import domain.Customer;
import domain.Host;
import domain.JobApplication;
import domain.Valoration;
import forms.RegisterActor;

@Controller
@RequestMapping("/host")
public class HostController extends AbstractController {

	@Autowired
	private HostService				hostService;

	@Autowired
	private CleanerService cleanerService;
	
	@Autowired
	private JobApplicationService jobApplicationService;
	
	@Autowired
	private ConfigService configService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private CleanerService			cleanerService;

	@Autowired
	private JobApplicationService	jobAppService;

	@Autowired
	private AccomodationService		accService;

	@Autowired
	private ValorationService		valorationService;


	// Constructors -----------------------------------------------------------

	public HostController() {
		super();
	}

	// RATE CLEANER
	// ---------------------------------------------------------------
	@RequestMapping(value = "/rateCleaner", method = RequestMethod.GET)
	public ModelAndView rateCleaner(@RequestParam(value = "cleanerId", defaultValue = "-1") final int cleanerId) {

		ModelAndView res;

		try {

			res = new ModelAndView("host/rateCleaner");
			final Valoration valoration = this.valorationService.create();
			valoration.setHost(this.hostService.getHostByUserAccountId(LoginService.getPrincipal().getId()));
			valoration.setCleaner(this.cleanerService.findOne(cleanerId));
			res.addObject("valoration", valoration);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	@RequestMapping(value = "/rateCleaner", method = RequestMethod.POST, params = "save")
	public ModelAndView rateCleaner(@Valid final Valoration valoration, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors()) {

			res = new ModelAndView("host/rateCleaner");
			res.addObject("valoration", valoration);
		} else
			try {

				this.valorationService.save(valoration);
				res = new ModelAndView("redirect:/accomodation/host/list.do");
			} catch (final Throwable oops) {

				res = new ModelAndView("redirect:/welcome/index.do");
			}

		return res;
	}

	// CLEANER LIST
	// ---------------------------------------------------------------
	@RequestMapping(value = "/cleanerList", method = RequestMethod.GET)
	public ModelAndView cleanerList(@RequestParam(value = "accomodationId", defaultValue = "-1") final int accomodationId) {

		ModelAndView res;

		try {

			res = new ModelAndView("host/cleanerList");
			res.addObject("accomodationId", accomodationId);

			final Collection<JobApplication> jobs = this.jobAppService.getJobApplicationByStatusAndHostId(true, this.accService.findOne(accomodationId).getHost().getId());
			final Collection<Cleaner> cleaners = this.cleanerService.getAllCleanersInJobList(jobs);
			res.addObject("cleaners", cleaners);

		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// RATE CUSTOMER
	// ---------------------------------------------------------------
	@RequestMapping(value = "/rateCustomer", method = RequestMethod.GET)
	public ModelAndView rateCustomer(@RequestParam(value = "customerId", defaultValue = "-1") final int customerId) {

		ModelAndView res;

		try {

			res = new ModelAndView("host/rateCustomer");
			final Valoration valoration = this.valorationService.create();
			valoration.setHost(this.hostService.getHostByUserAccountId(LoginService.getPrincipal().getId()));
			valoration.setCustomer(this.customerService.findOne(customerId));
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

			res = new ModelAndView("host/rateCustomer");
			res.addObject("valoration", valoration);
		} else
			try {

				this.valorationService.save(valoration);
				res = new ModelAndView("redirect:/accomodation/host/list.do");
			} catch (final Throwable oops) {

				res = new ModelAndView("redirect:/welcome/index.do");
			}

		return res;
	}

	// CUSTOMER LIST
	// ---------------------------------------------------------------
	@RequestMapping(value = "/customerList", method = RequestMethod.GET)
	public ModelAndView customerList(@RequestParam(value = "accomodationId", defaultValue = "-1") final int accomodationId) {

		ModelAndView res;

		try {

			res = new ModelAndView("host/customerList");
			res.addObject("accomodationId", accomodationId);
			final Collection<Customer> customers = this.customerService.getCustomersByAccomodationId(accomodationId);
			res.addObject("customers", customers);

		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// REGISTER AS HOST
	// ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegisterActor registerActor = new RegisterActor();
			result = new ModelAndView("host/create");
			result.addObject("registerActor", registerActor);
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		;
		return result;
	}

	// SAVE REGISTER AS HOST
	// ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterActor registerActor, final BindingResult binding) {
		ModelAndView result = null;
		final Host host = this.hostService.reconstructRegisterAsHost(registerActor, binding);
		if (binding.hasErrors()) {
			System.err.println(binding);
			result = new ModelAndView("host/create");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} else
			try {
				this.hostService.saveRegisterAsHost(host);
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
		Host host;
		final int idUserAccount = LoginService.getPrincipal().getId();
		host = this.hostService.getHostByUserAccountId(idUserAccount);
		Assert.notNull(host);
		final CreditCard creditCard = host.getCreditCard();
		result = new ModelAndView("host/edit");
		result.addObject("host", host);
		result.addObject("creditCard", creditCard);
		final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
		result.addObject("makes", makes);
		return result;
	}

	// SAVE EDIT DATA PERSONAL
	// ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Host host, final BindingResult binding) {
		ModelAndView result = null;

		host = this.hostService.reconstructEditDataPeronal(host, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("host/edit");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);

		} else
			try {
				this.hostService.saveRegisterAsHost(host);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// SHOW HOST
	// -------------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "hostId", defaultValue = "-1") final int hotId) {
		ModelAndView result;
		Boolean res;
		try {
			final Host registerActor;
			if (hotId == -1) {
				final int userLoggin = LoginService.getPrincipal().getId();
				registerActor = this.hostService.getHostByUserAccountId(userLoggin);
				res = true;
			} else {
				registerActor = this.hostService.findOne(hotId);
				res = false;
			}
			
			result = new ModelAndView("host/show");
			// ALVARO If an cleaner user show the profile of an valid host to do an application the link appear
			this.validCleaner(registerActor, result);
			// ALVARO
			result.addObject("registerActor", registerActor);
			result.addObject("res", res);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	private ModelAndView validCleaner(Host host, ModelAndView result) {
		ModelAndView res = result;
		try {
			Cleaner cleaner = this.cleanerService.getCleanerLogin();
			Assert.notNull(cleaner, "Any cleaner is login");
			Assert.isTrue(this.jobApplicationService.checkValidForNewApplication(cleaner.getId(), host.getId()), "El usuario dispone con una aplicaci�n pendiente");
			res.addObject("validCleaner", true);
		} catch (Exception e) {
			System.out.println("Bloqueada petici�n de logueo, el limpiador no es un usuario v�lido");
			res.addObject("validCleaner", false);
		}
		return res;
	}

}
