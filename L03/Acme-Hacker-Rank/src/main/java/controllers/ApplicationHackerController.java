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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ApplicationService;
import services.HackerService;
import domain.Application;
import domain.Hacker;

@Controller
@RequestMapping("/application")
public class ApplicationHackerController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private HackerService		hackerService;


	// Constructors -----------------------------------------------------------

	public ApplicationHackerController() {
		super();
	}

	// LIST ---------------------------------------------------------------		

	@RequestMapping(value = "/hacker/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		System.out.println("Carmen, entro en el list");

		try {
			final UserAccount user = LoginService.getPrincipal();
			System.out.println(user.getUsername());
			final Hacker hacker = this.hackerService.getHackerByUserAccountId(user.getId());
			System.out.println("Hacker loggeado: " + hacker);
			Assert.isTrue(hacker != null);
			System.out.println("Hacker loggeado: " + hacker);
			final Collection<Application> applications = this.applicationService.getApplicationsByHacker(hacker.getId());
			System.out.println("Aplicaciones del hacker: " + applications);
			result = new ModelAndView("application/hacker/list");
			result.addObject("applications", applications);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
}
