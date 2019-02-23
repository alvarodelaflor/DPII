/*
 * BrotherhoodController.java
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

import services.BrotherhoodService;
import services.FloatBroService;
import services.PositionAuxService;
import domain.Brotherhood;
import domain.Procession;

/*
 * CONTROL DE CAMBIOS ProcessionBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 12:29 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 16:35 AÑADIDO RECONSTRUIDOR PROCESSION
 */

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {

	@Autowired
	BrotherhoodService	brotherhoodService;

	@Autowired
	PositionAuxService	positionAuxService;

	@Autowired
	FloatBroService		floatBroService;


	// Constructors -----------------------------------------------------------

	public ProcessionController() {
		super();
	}

	@RequestMapping(value = "/listProcessions", method = RequestMethod.GET)
	public ModelAndView listProcessions(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;

		final Brotherhood brotherhood = this.brotherhoodService.findOne(id);
		final Collection<Procession> procession = brotherhood.getProcessions();
		result = new ModelAndView("procession/listProcessions");
		result.addObject("brotherhood", brotherhood);
		result.addObject("procession", procession);
		result.addObject("requestURI", "procession/listProcessions.do");
		return result;
	}
}
