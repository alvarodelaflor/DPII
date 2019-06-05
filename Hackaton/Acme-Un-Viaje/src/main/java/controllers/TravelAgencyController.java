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
import domain.TravelAgency;
import forms.RegisterActor;
import security.LoginService;
import services.ConfigService;
import services.TravelAgencyService;

@Controller
@RequestMapping("/travelAgency")
public class TravelAgencyController extends AbstractController {

	@Autowired
	private TravelAgencyService	travelAgencyService;

	@Autowired
	private ConfigService		configService;


	// Constructors -----------------------------------------------------------

	public TravelAgencyController() {
		super();
	}

	// REGISTER AS TRAVEL
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegisterActor registerActor = new RegisterActor();
			result = new ModelAndView("travelAgency/create");
			result.addObject("registerActor", registerActor);
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		;
		return result;
	}

	// SAVE REGISTER AS TRAVEL
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterActor registerActor, final BindingResult binding) {
		ModelAndView result = null;
		final TravelAgency travelAgency = this.travelAgencyService.reconstructRegisterAsTravelAgency(registerActor, binding);
		if (binding.hasErrors()) {
			System.err.println(binding);
			result = new ModelAndView("travelAgency/create");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} else
			try {
				this.travelAgencyService.saveRegisterAsTravelAgency(travelAgency);
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
		TravelAgency travelAgency;
		final int idUserAccount = LoginService.getPrincipal().getId();
		travelAgency = this.travelAgencyService.getTravelAgencyByUserAccountId(idUserAccount);
		Assert.notNull(travelAgency);
		final CreditCard creditCard = travelAgency.getCreditCard();
		result = new ModelAndView("travelAgency/edit");
		result.addObject("travelAgency", travelAgency);
		result.addObject("creditCard", creditCard);
		final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
		result.addObject("makes", makes);
		return result;
	}

	// SAVE EDIT DATA PERSONAL
	// ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(TravelAgency travelAgency, final BindingResult binding) {
		ModelAndView result = null;

		travelAgency = this.travelAgencyService.reconstructEditDataPeronal(travelAgency, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("travelAgency/edit");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} else
			try {
				this.travelAgencyService.saveRegisterAsTravelAgency(travelAgency);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// SHOW TRAVEL
	// -------------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		try {
			final int userLoggin = LoginService.getPrincipal().getId();
			final TravelAgency registerActor;
			registerActor = this.travelAgencyService.getTravelAgencyByUserAccountId(userLoggin);
			result = new ModelAndView("travelAgency/show");
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	//EXPORT
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody TravelAgency export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		TravelAgency result = new TravelAgency();
		result = this.travelAgencyService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		return result;
	}

	//DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int travelAgencyId) {
		ModelAndView result;

		final TravelAgency travelAgency = this.travelAgencyService.findOne(travelAgencyId);
		if (this.travelAgencyService.findOne(travelAgencyId) == null || LoginService.getPrincipal().getId() != travelAgency.getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(travelAgency, "travelAgency.null");

			try {
				this.travelAgencyService.delete(travelAgency);
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (final Exception e) {
				e.printStackTrace();
				result = new ModelAndView("redirect:/j_spring_security_logout");
			}
		}
		return result;
	}

}
