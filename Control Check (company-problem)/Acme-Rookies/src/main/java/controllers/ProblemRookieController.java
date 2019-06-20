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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProblemService;
import domain.Problem;

@Controller
@RequestMapping("/problem")
public class ProblemRookieController extends AbstractController {

	@Autowired
	private ProblemService	problemService;


	// Constructors -----------------------------------------------------------

	public ProblemRookieController() {
		super();
	}

	// SHOW ---------------------------------------------------------------		
	@RequestMapping(value = "/rookie/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "problemId", defaultValue = "-1") final int problemId) {

		ModelAndView result;
		final Problem problem;
		System.out.println(problemId);
		try {
			problem = this.problemService.findOne(problemId);
			System.out.println(problem);
			Assert.notNull(problem);
			result = new ModelAndView("problem/rookie/show");
			result.addObject("problem", problem);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

}
