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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import domain.Application;

@Controller
@RequestMapping("/application/company")
public class ApplicationCompanyController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;


	// LIST ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Application> submittedApplications = this.applicationService.getSubmittedApplicationsByLoggedCompany();
			final Collection<Application> acceptedApplications = this.applicationService.getAcceptedApplicationsByLoggedCompany();
			final Collection<Application> rejectedApplications = this.applicationService.getRejectedApplicationsByLoggedCompany();
			result = new ModelAndView("application/company/list");
			result.addObject("submittedApplications", submittedApplications);
			result.addObject("acceptedApplications", acceptedApplications);
			result.addObject("rejectedApplications", rejectedApplications);
			result.addObject("requestURI", "/application/company/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Show ----------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "applicationId", defaultValue = "-1") final int applicationId) {
		ModelAndView result;
		try {
			final Application application = this.applicationService.getCompanyApplication(applicationId);
			result = new ModelAndView("application/company/show");
			result.addObject("application", application);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/application/company/list.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Show ----------------
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam(value = "applicationId", defaultValue = "-1") final int applicationId) {
		ModelAndView result;
		try {
			this.applicationService.accept(applicationId);
			result = new ModelAndView("redirect:/application/company/show.do?applicationId=" + applicationId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Show ----------------
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam(value = "applicationId", defaultValue = "-1") final int applicationId) {
		ModelAndView result;
		try {
			this.applicationService.reject(applicationId);
			result = new ModelAndView("redirect:/application/company/show.do?applicationId=" + applicationId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

}
