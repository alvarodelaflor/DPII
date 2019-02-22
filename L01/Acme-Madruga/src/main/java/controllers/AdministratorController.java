/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AdministratorService;
import services.WelcomeService;
import domain.Administrator;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private WelcomeService			welcomeService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		Administrator administrator;

		final int idUserAccount = LoginService.getPrincipal().getId();

		administrator = this.administratorService.getAdministratorByUserAccountId(idUserAccount);
		Assert.notNull(administrator);

		System.out.println(administrator.getName());

		result = new ModelAndView("administrator/edit");

		result.addObject("administrator", administrator);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveE(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;
		if (administrator.getEmail() != null
			&& this.actorService.getActorByEmail(administrator.getEmail()) != null
			&& this.administratorService.getAdministratorByUserAccountId(LoginService.getPrincipal().getId()).getId() != this.actorService.getActorByEmail(administrator.getEmail()).getId()
			|| !(administrator.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}") || (administrator.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}") || (administrator.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}(>)") || (administrator
				.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}(>)")))))) {
			final ObjectError error = new ObjectError("actor.email", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("email", "error.actor.email.exits");
		}
		if (binding.hasErrors()) {
			System.out.println(binding);
			result = new ModelAndView("administrator/edit");
		} else
			try {
				Assert.isTrue(this.administratorService.findOne(administrator.getId()) != null);
				if (administrator.getPhone().matches("^([0-9]{4,})$"))
					administrator.setPhone("+" + this.welcomeService.getPhone() + " " + administrator.getPhone());
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				System.out.println("El error es en administratorController: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("administrator/edit");

			}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final int userLoggin = LoginService.getPrincipal().getId();
		final Administrator administrator;
		administrator = this.administratorService.getAdministratorByUserAccountId(userLoggin);
		Assert.isTrue(administrator != null);

		result = new ModelAndView("administrator/show");
		result.addObject("administrator", administrator);

		result.addObject("requestURI", "administrator/show.do");

		return result;
	}

}
