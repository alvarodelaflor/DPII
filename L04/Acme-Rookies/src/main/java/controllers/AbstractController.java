/*
 * AbstractController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;

@Controller
public class AbstractController {

	@Autowired
	private ConfigurationService	configurationService;


	// Panic handler ----------------------------------------------------------

	public String getLogo() {
		return this.configurationService.getConfiguration().getBanner();
	}

	public String getSystem() {
		return this.configurationService.getConfiguration().getSystemName();
	}

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("welcome/index");

		return result;
	}

}
