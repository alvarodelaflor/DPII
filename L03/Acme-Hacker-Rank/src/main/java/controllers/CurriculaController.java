/*
 * CurricculaHackerController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Curricula;
import domain.Hacker;
import services.CurriculaService;
import services.HackerService;

/*
 * CONTROL DE CAMBIOS CurriculaHackerController.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/curricula")
public class CurriculaController extends AbstractController {
	
	@Autowired
	private HackerService hackerService;

	@Autowired
	private CurriculaService curriculaService;


	// Constructors -----------------------------------------------------------

	public CurriculaController() {
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "hackerId", defaultValue = "-1") final int hackerId) {
		ModelAndView result;
		try {
			Hacker hacker = this.hackerService.findOne(hackerId);
			Hacker hackerLogin = this.hackerService.getHackerLogin();
			if (hacker == null && hackerLogin!=null) {
				hacker=hackerLogin;
			}
			Assert.notNull(hacker, "Not hacker found in DB");
			result = new ModelAndView("curricula/list");
			if (hackerLogin!= null && hacker.equals(hackerLogin)) {
				result.addObject("hackerLogger", true);
			}
			Assert.notNull(hacker, "Hacker is null");
			final Collection<Curricula> curriculas = this.curriculaService.findAllByHackerId(hacker);
			result.addObject("curriculas", curriculas);
			result.addObject("requestURI", "curriculas/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
}
