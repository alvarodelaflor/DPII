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

import services.AreaService;
import services.BrotherhoodService;
import services.ChapterService;
import services.WelcomeService;
import domain.Area;
import domain.Brotherhood;
import domain.Chapter;

/*
 * CONTROL DE CAMBIOS ParadeBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 12:29 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 16:35 AÑADIDO RECONSTRUIDOR PARADE
 */

@Controller
@RequestMapping("/area")
public class AreaController extends AbstractController {

	@Autowired
	WelcomeService		welcomeService;

	@Autowired
	AreaService			areaService;

	@Autowired
	ChapterService		chapterService;

	@Autowired
	BrotherhoodService	brotherhoodService;


	// Constructors -----------------------------------------------------------

	public AreaController() {
		super();
	}

	@RequestMapping(value = "/areaChapter", method = RequestMethod.GET)
	public ModelAndView listArea(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;
		Boolean status = false;
		try {
			result = new ModelAndView("area/areaChapter");
			final Chapter chapter = this.chapterService.findOne(id);
			System.out.println(chapter);
			if (this.areaService.findAreaChapter(chapter) != null) {
				status = true;
				final Area area = this.areaService.findAreaChapter(chapter);
				result.addObject("area", area);
				final Collection<Brotherhood> brotherhood = this.brotherhoodService.brotherhoodArea(area.getId());
				result.addObject("brotherhood", brotherhood);
			}
			result.addObject("chapter", chapter);
			result.addObject("requestURI", "area/areaChapter.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		result.addObject("status", status);
		System.out.println(status);
		return result;
	}

}
