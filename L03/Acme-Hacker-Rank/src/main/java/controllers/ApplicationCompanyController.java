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

}
