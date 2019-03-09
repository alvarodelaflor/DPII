/*
 * HistoryController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import domain.Brotherhood;
import services.BrotherhoodService;
import services.WelcomeService;

/*
 * CONTROL DE CAMBIOS History.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/history")
public class HistoryController extends AbstractController {
	
	@Autowired
	private BrotherhoodService brotherhoodService;
	
	@Autowired
	private WelcomeService welcomeService;


	// Constructors -----------------------------------------------------------

	public HistoryController() {
		super();
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "brotherhoodId", defaultValue = "-1") final int brotherhoodId) {
		ModelAndView result;
		try {
			Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
			result = new ModelAndView("history/show");
			result.addObject("curriculum", brotherhood.getHistory());
			result.addObject("requestURI", "history/show.do");
			result.addObject("system", this.welcomeService.getSystem());
			result.addObject("logo", this.welcomeService.getLogo());
		} catch (Exception e) {
			System.out.println("Error en HistoryController.java SHOW: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
}
