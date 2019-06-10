/*
 * CurricculaRookieController.java
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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionDataService;
import services.RookieService;
import domain.PositionData;
import domain.Rookie;

/*
 * CONTROL DE CAMBIOS CurriculaRookieController.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/positionData")
public class PositionDataController extends AbstractController {

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private PositionDataService	positionDataService;


	// Constructors -----------------------------------------------------------

	public PositionDataController() {
		super();
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "positionDataId", defaultValue = "-1") final int positionDataId) {

		ModelAndView result;
		try {
			final PositionData positionDataDB = this.positionDataService.findOne(positionDataId);
			Assert.notNull(positionDataDB, "Not found position in DB");
			result = new ModelAndView("positionData/show");

			final Rookie rookieLogin = this.rookieService.getRookieLogin();
			if (rookieLogin != null && positionDataDB.getCurricula().getRookie().equals(rookieLogin))
				result.addObject("rookieLogin", true);

			result.addObject("positionData", positionDataDB);

			result.addObject("requestURI", "positionData/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
}
