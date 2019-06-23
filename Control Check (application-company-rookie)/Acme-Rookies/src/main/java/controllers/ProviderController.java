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

import domain.Company;
import domain.Provider;
import forms.RegistrationForm;
import security.LoginService;
import services.ProviderService;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	@Autowired
	private ProviderService providerService;


	// Constructors -----------------------------------------------------------

	public ProviderController() {
		super();
	}

	// CREATE ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegistrationForm registrationForm = new RegistrationForm();
			result = new ModelAndView("provider/create");
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

		final Provider provider;

		provider = this.providerService.reconstructCreate(registrationForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = new ModelAndView("provider/create");
		} else
			try {
				System.out.println("carmen: voy a guardar");
				final Provider a = this.providerService.saveCreate(provider);
				System.out.println(a);
				result = new ModelAndView("welcome/index");
				final SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final String moment = formatter.format(new Date());
				result.addObject("moment", moment);

			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(provider, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.createEditModelAndView(provider, "error.email");
				else
					result = this.createEditModelAndView(provider, "error.html");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final Provider actor, final String string) {
		ModelAndView result;

		result = new ModelAndView("provider/create");
		result.addObject("message", string);
		result.addObject("actor", actor);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// LIST ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		try {
			final Collection<Provider> providers = this.providerService.findAll();
			result = new ModelAndView("provider/list");
			result.addObject("providers", providers);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SHOW ---------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id", defaultValue = "-1") final int id) {

		Provider provider;
		ModelAndView result;
		Boolean checkprovider = false;
		try {
			if (id == -1) {
				final int userLoggin = LoginService.getPrincipal().getId();
				provider = this.providerService.getProviderByUserAccountId(userLoggin);
				Assert.isTrue(provider != null);
				checkprovider = true;
			} else {
				provider = this.providerService.findOne(id);
				Assert.isTrue(provider != null);
			}

			result = new ModelAndView("provider/show");
			result.addObject("provider", provider);
			result.addObject("checkprovider", checkprovider);
			result.addObject("requestURI", "provider/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		result.addObject("owner", checkprovider);
		return result;
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody Provider export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		Provider result = new Provider();
		result = this.providerService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		System.out.println(result);
		return result;
	}

	//Nuevo
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int providerId) {
		ModelAndView result;

		final Provider provider = this.providerService.findOne(providerId);
		System.out.println("Provider encontrado: " + provider);
		if (this.providerService.findOne(providerId) == null || LoginService.getPrincipal().getId() != provider.getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(provider, "provider.null");

			try {
				this.providerService.delete(provider);
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (final Exception e) {
				result = new ModelAndView("redirect:/j_spring_security_logout");
			}
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
			Provider provider;
			final int idUserAccount = LoginService.getPrincipal().getId();
			provider = this.providerService.getProviderByUserAccountId(idUserAccount);
			Assert.notNull(provider);
			result = new ModelAndView("provider/edit");
			result.addObject("provider", provider);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SAVE-EDIT ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Provider provider, final BindingResult binding) {
		ModelAndView result;

		System.out.println("Provider a editar" + provider);

		provider = this.providerService.reconstructEdit(provider, binding);

		System.out.println("c" + binding.getAllErrors());

		if (binding.hasErrors()) {
			System.out.println("Carmen: Hay fallos " + binding);
			result = new ModelAndView("provider/edit");
		} else
			try {
				provider = this.providerService.saveEdit(provider);
				result = new ModelAndView("redirect:show.do");
				result.addObject("provider", provider);
			} catch (final Throwable oops) {
				System.out.println(oops);
				if (oops.getMessage().equals("email.wrong"))
					result = this.editModelAndView(provider, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.editModelAndView(provider, "error.email");
				else
					result = new ModelAndView("redirect:/welcome/index.do");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView editModelAndView(final Provider provider, final String string) {
		ModelAndView result;

		result = new ModelAndView("provider/edit");
		result.addObject("message", string);
		result.addObject("provider", provider);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

}
