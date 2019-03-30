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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	// CONDITIONS ---------------------------------------------------------------		

	@RequestMapping(value = "/conditions", method = RequestMethod.GET)
	public ModelAndView conditions() {
		ModelAndView result;
		result = new ModelAndView("actor/conditions");
		return result;
	}
}
