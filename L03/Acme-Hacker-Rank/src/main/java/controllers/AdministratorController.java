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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ApplicationService;
import services.PositionService;
import domain.Administrator;
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
}
