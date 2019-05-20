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
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AdminService;
import domain.Actor;
import domain.Admin;
import domain.CreditCard;
import forms.RegisterActor;

@Controller
@RequestMapping("/admin")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdminService	adminService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------
	public AdministratorController() {
		super();
	}

	// BAN/UNBAN ACTOR
	// ---------------------------------------------------------------
	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView banOrUnban(@RequestParam(value = "id", defaultValue = "-1") final int id) {

		ModelAndView res;
		try {

			this.adminService.banOrUnbanActorById(id);
			res = new ModelAndView("redirect:actorList.do");
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:actorList.do");
			if (oops.getMessage() == "not.found.error")
				res.addObject("message", "not.found.error");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// ACTOR LIST
	// ---------------------------------------------------------------
	@RequestMapping(value = "/actorList", method = RequestMethod.GET)
	public ModelAndView actorList() {

		ModelAndView res;
		try {

			final Collection<Actor> bannedActors = this.actorService.findAllBannedButAdmins();
			final Collection<Actor> NonBannedActors = this.actorService.findAllNonBannedButAdmins();

			res = new ModelAndView("admin/actorList");
			res.addObject("bannedActors", bannedActors);
			res.addObject("NonBannedActors", NonBannedActors);
			res.addObject("requestURI", "admin/actorList.do");
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// REGISTER AS ADMIN
	// ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegisterActor registerActor = new RegisterActor();
			result = new ModelAndView("admin/create");
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		;
		return result;
	}

	// SAVE REGISTER AS ADMIN
	// ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterActor registerActor, final BindingResult binding) {
		ModelAndView result = null;
		final Admin admin = this.adminService.reconstructRegisterAsAdmin(registerActor, binding);
		if (binding.hasErrors()) {
			System.err.println(binding);
			result = new ModelAndView("admin/create");
		} else
			try {
				this.adminService.saveRegisterAsAdmin(admin);
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
		Admin admin;
		final int idUserAccount = LoginService.getPrincipal().getId();
		admin = this.adminService.getAdminByUserAccountId(idUserAccount);
		Assert.notNull(admin);
		final CreditCard creditCard = admin.getCreditCard();
		result = new ModelAndView("admin/edit");
		result.addObject("admin", admin);
		result.addObject("creditCard", creditCard);
		return result;
	}

	// SAVE EDIT DATA PERSONAL
	// ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Admin admin, final BindingResult binding) {
		ModelAndView result = null;

		admin = this.adminService.reconstructEditDataPeronal(admin, binding);

		if (binding.hasErrors()) {
			System.out.println("HAY ERRORES 2" + binding);
			result = new ModelAndView("admin/edit");

		} else
			try {
				this.adminService.saveRegisterAsAdmin(admin);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// SHOW ADMIN
	// -------------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		try {
			final int userLoggin = LoginService.getPrincipal().getId();
			final Admin registerActor;
			registerActor = this.adminService.getAdminByUserAccountId(userLoggin);
			result = new ModelAndView("admin/show");
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
