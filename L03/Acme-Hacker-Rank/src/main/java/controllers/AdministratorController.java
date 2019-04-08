/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ApplicationService;
import services.CompanyService;
import services.HackerService;
import services.PositionService;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Hacker;
import forms.ActorForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private HackerService			hackerService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Dashboard --------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {

		final ModelAndView res = new ModelAndView("administrator/dashboard");
		res.addObject("requestURI", "administrator/dashboard.do");

		// PositionsPerCompany
		res.addObject("avgPositionPerCompany", this.positionService.avgPositionPerCompany());
		res.addObject("minPositionPerCompany", this.positionService.minPositionPerCompany());
		res.addObject("maxPositionPerCompany", this.positionService.maxPositionPerCompany());
		res.addObject("stddevPositionPerCompany", this.positionService.stddevPositionPerCompany());
		// PositionsPerCompany

		// ApplicationPerHacker
		res.addObject("avgApplicationsPerHacker", this.applicationService.avgApplicationPerHacker());
		res.addObject("minApplicationsPerHacker", this.applicationService.minApplicationPerHacker());
		res.addObject("maxApplicationsPerHacker", this.applicationService.maxApplicationPerHacker());
		res.addObject("stddevApplicationsPerHacker", this.applicationService.stddevApplicationPerHacker());
		// ApplicationPerHacker

		// CompaniesMorePositions
		res.addObject("findCompanyWithMorePositions", this.positionService.findCompanyWithMorePositions());
		// CompaniesMorePositions

		// HackerMoreApplications
		res.addObject("findHackerMoreApplications", this.applicationService.findHackerWithMoreApplications());
		// HackerMoreApplications

		// Salaries
		res.addObject("avgSalaryPerPosition", this.positionService.avgSalaryPerPosition());
		res.addObject("minSalaryPerPosition", this.positionService.minSalaryPerPosition());
		res.addObject("maxSalaryPerPosition", this.positionService.maxSalaryPerPosition());
		res.addObject("stddevSalaryPerPosition", this.positionService.stddevSalaryPerPosition());
		res.addObject("bestPosition", this.positionService.bestPosition());
		res.addObject("worstPosition", this.positionService.worstPosition());
		// Salaries

		return res;
	}

	// Register New Admin ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView res;
		try {
			final ActorForm form = new ActorForm();
			res = new ModelAndView("administrator/create");
			res.addObject("form", form);
		} catch (final Exception e) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorForm form, final BindingResult binding) {

		ModelAndView res = null;
		final Administrator admin;
		admin = this.adminService.reconstruct(form, binding);

		if (binding.hasErrors())

			res = new ModelAndView("administrator/create");
		else

			try {

				this.adminService.save(admin);
				res = new ModelAndView("welcome/index");
			} catch (final Throwable oops) {

				if (oops.getMessage().equals("email.wrong"))
					res = this.createEditModelAndView(admin, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					res = this.createEditModelAndView(admin, "error.email");
				else
					res = this.createEditModelAndView(admin, "error.html");
			}

		return res;
	}

	private ModelAndView createEditModelAndView(final Administrator admin, final String string) {
		ModelAndView result;

		result = new ModelAndView("administrator/create");
		result.addObject("message", string);
		result.addObject("admin", admin);
		return result;
	}

	// Actor List ---------------------------------------------------------------
	@RequestMapping(value = "/actorList", method = RequestMethod.GET)
	public ModelAndView actorList() {

		final ModelAndView res;

		final Collection<Company> companies = this.companyService.findAll();
		final Collection<Hacker> hackers = this.hackerService.findAll();

		res = new ModelAndView("administrator/actorList");

		res.addObject("companies", companies);
		res.addObject("hackers", hackers);
		res.addObject("requestURI", "administrator/actorList.do");
		return res;
	}

	// Ban/Unban ---------------------------------------------------------------

	private ModelAndView createEditModelAndView2(final Actor actor, final String string) {
		ModelAndView result;

		final Collection<Company> companies = this.companyService.findAll();
		final Collection<Hacker> hackers = this.hackerService.findAll();

		result = new ModelAndView("administrator/actorList");
		result.addObject("message", string);
		result.addObject("actor", actor);
		result.addObject("companies", companies);
		result.addObject("hackers", hackers);
		return result;
	}

	@RequestMapping(value = "/banCompany", method = RequestMethod.GET)
	public ModelAndView banMember(@RequestParam(value = "actorId", defaultValue = "-1") final int actorId) {

		ModelAndView res;

		try {
			final Actor actor = this.companyService.findOne(actorId);

			if (actor.getUserAccount().getBanned() == false) {

				this.adminService.banByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			} else {

				this.adminService.unbanByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			}
		} catch (final Throwable oops) {

			final Actor actor = this.companyService.findOne(actorId);
			if (oops.getMessage() == "ban.error")
				res = this.createEditModelAndView2(actor, "ban.error");
			else
				res = new ModelAndView("redirect:../#");
		}

		return res;
	}
	@RequestMapping(value = "/banHacker", method = RequestMethod.GET)
	public ModelAndView banBrotherhood(@RequestParam(value = "actorId", defaultValue = "-1") final int actorId) {

		ModelAndView res;

		try {
			final Actor actor = this.hackerService.findOne(actorId);

			if (actor.getUserAccount().getBanned() == false) {

				this.adminService.banByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			} else {

				this.adminService.unbanByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			}
		} catch (final Throwable oops) {

			final Actor actor = this.hackerService.findOne(actorId);
			if (oops.getMessage() == "ban.error")
				res = this.createEditModelAndView2(actor, "ban.error");
			else
				res = new ModelAndView("redirect:../#");
		}

		return res;
	}

}
