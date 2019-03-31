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

import services.PositionService;
import domain.Position;

@Controller
@RequestMapping("/position")
public class PostionController extends AbstractController {

	@Autowired
	private PositionService	positionService;


	// Constructors -----------------------------------------------------------

	public PostionController() {
		super();
	}

	// SHOW ---------------------------------------------------------------		
	@RequestMapping(value = "/listCompany", method = RequestMethod.GET)
	public ModelAndView listCompany(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;
		try {
			System.out.println("entro");
			System.out.println(id);
			final Collection<Position> positions = this.positionService.findAllPositionByCompany(id);
			System.out.println(positions);
			result = new ModelAndView("position/listCompany");
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/listCompany.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	// LIST ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			System.out.println("entro");
			final Collection<Position> positions = this.positionService.findAllPositionWithStatusTrue();
			System.out.println(positions);
			result = new ModelAndView("position/list");
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
