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
import domain.Referee;
import forms.RegisterActor;
import security.LoginService;
import services.ConfigService;
import services.RefereeService;

@Controller
@RequestMapping("/referee")
public class RefereeController extends AbstractController {

	@Autowired
	private RefereeService	refereeService;

	@Autowired
	private ConfigService	configService;


	// Constructors -----------------------------------------------------------

	public RefereeController() {
		super();
	}

	// REGISTER AS REFEREE
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegisterActor registerActor = new RegisterActor();
			result = new ModelAndView("referee/create");
			result.addObject("registerActor", registerActor);
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		;
		return result;
	}

	// SAVE REGISTER AS REFEREE
	// ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterActor registerActor, final BindingResult binding) {
		ModelAndView result = null;
		final Referee referee = this.refereeService.reconstructRegisterAsReferee(registerActor, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("referee/create");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} else
			try {
				this.refereeService.saveRegisterAsReferee(referee);
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
		Referee referee;
		final int idUserAccount = LoginService.getPrincipal().getId();
		referee = this.refereeService.getRefereeByUserAccountId(idUserAccount);
		Assert.notNull(referee);
		final CreditCard creditCard = referee.getCreditCard();
		result = new ModelAndView("referee/edit");
		result.addObject("referee", referee);
		result.addObject("creditCard", creditCard);
		final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
		result.addObject("makes", makes);
		return result;
	}

	// SAVE EDIT DATA PERSONAL
	// ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Referee referee, final BindingResult binding) {
		ModelAndView result = null;

		referee = this.refereeService.reconstructEditDataPeronal(referee, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("referee/edit");
			final Collection<String> makes = this.configService.getConfiguration().getCreditCardMakeList();
			result.addObject("makes", makes);
		} else
			try {
				this.refereeService.saveRegisterAsReferee(referee);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// SHOW REFEREE
	// -------------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		try {
			final int userLoggin = LoginService.getPrincipal().getId();
			final Referee registerActor;
			registerActor = this.refereeService.getRefereeByUserAccountId(userLoggin);
			result = new ModelAndView("referee/show");
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	//EXPORT
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody Referee export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		Referee result = new Referee();
		result = this.refereeService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		return result;
	}

	//DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int refereeId) {
		ModelAndView result;

		final Referee referee = this.refereeService.findOne(refereeId);
		System.out.println("Referee encontrado: " + referee);
		if (this.refereeService.findOne(refereeId) == null || LoginService.getPrincipal().getId() != referee.getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(referee, "referee.null");

			try {
				this.refereeService.delete(referee);
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (final Exception e) {
				e.printStackTrace();
				result = new ModelAndView("redirect:/j_spring_security_logout");
			}
		}
		return result;
	}

}
