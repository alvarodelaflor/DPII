/*
 * PositionRookieController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.PositionDataService;
import services.PositionService;
import services.RookieService;
import domain.Curricula;
import domain.PositionData;
import domain.Rookie;

/*
 * CONTROL DE CAMBIOS PositionDataRookieController.java
 * 
 * ALVARO 09/03/2019 23:47 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/positionData/rookie")
public class PositionDataRookieController extends AbstractController {

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private PositionDataService	positionDataService;

	@Autowired
	private PositionService		positionService;


	// Constructors -----------------------------------------------------------

	public PositionDataRookieController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "curriculaId", defaultValue = "-1") final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula curriculaDB = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curriculaDB, "Curricula not found in DB");
			final Rookie rookieLogin = this.rookieService.getRookieLogin();
			Assert.notNull(rookieLogin, "No rookie is login");
			Assert.isTrue(rookieLogin.equals(curriculaDB.getRookie()));
			final PositionData positionData = this.positionDataService.createWithHistory(curriculaDB);
			result = new ModelAndView("positionData/rookie/create");
			result.addObject("positionData", positionData);
			result.addObject("curricula", curriculaDB);
			result.addObject("positions", this.positionService.findValidPositionToCurriculaByRookieId(rookieLogin.getId()));
		} catch (final Exception e) {
			System.out.println("Error e en GET /create PositionDataController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "positionDataId", defaultValue = "-1") final int positionDataId) {
		ModelAndView result;
		try {
			final PositionData positionDataDB = this.positionDataService.findOne(positionDataId);
			Assert.notNull(positionDataDB, "PositionData not found in DB");
			final Rookie rookieLogin = this.rookieService.getRookieLogin();
			Assert.notNull(rookieLogin, "No rookie is login");
			Assert.isTrue(rookieLogin.equals(positionDataDB.getCurricula().getRookie()), "Not allow to edit not own PositionData");
			result = new ModelAndView("positionData/rookie/edit");
			result.addObject("positionData", positionDataDB);
			result.addObject("curricula", positionDataDB.getCurricula());
			result.addObject("positions", this.positionService.findValidPositionToCurriculaByRookieId(positionDataDB.getCurricula().getRookie().getId()));
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final PositionData positionData, final BindingResult binding) {
		ModelAndView result;

		if (positionData.getStartDate() != null && positionData.getEndDate() != null && this.positionDataService.checkDate(positionData.getStartDate(), positionData.getEndDate())) {
			final ObjectError error = new ObjectError("startDate", "Start date mus be before than end Date");
			binding.addError(error);
			binding.rejectValue("startDate", "error.startDate");
		}

		if (positionData.getStartDate() != null && positionData.getEndDate() != null && this.positionDataService.checkDate(positionData.getStartDate(), positionData.getEndDate())) {
			final ObjectError error = new ObjectError("endDate", "Start date mus be before than end Date");
			binding.addError(error);
			binding.rejectValue("endDate", "error.endDate");
		}

		if (binding.hasErrors()) {
			System.out.println("Error en PositionDataRookieController.java, binding: " + binding);
			result = new ModelAndView("positionData/rookie/edit");
			result.addObject("positionData", positionData);
			result.addObject("curricula", positionData.getCurricula());
			// TODO En este método solo serán añadidos las positiones validas
			result.addObject("positions", this.positionService.findValidPositionToCurriculaByRookieId(positionData.getCurricula().getRookie().getId()));
		} else
			try {
				final Rookie rookieLogin = this.rookieService.getRookieLogin();
				Assert.notNull(rookieLogin, "No rookie is login");
				Assert.isTrue(positionData != null, "positionData.null");
				Assert.isTrue(this.rookieService.getRookieByCurriculaId(positionData.getCurricula()).equals(rookieLogin), "Not allow to edit a not own PositionData");
				final PositionData positionDataSave = this.positionDataService.save(positionData);
				result = new ModelAndView("redirect:/positionData/show.do?positionDataId=" + positionDataSave.getId());
				result.addObject("requestURI", "curricula/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE CurriculaRookieController.java Throwable: " + oops);
				result = new ModelAndView("positionData/rookie/edit");
				result.addObject("positionData", positionData);
				try {
					result.addObject("curricula", positionData.getCurricula());
					// TODO En este método solo serán añadidos las positiones validas
					result.addObject("positions", this.positionService.findALL());
				} catch (final Exception e) {
					return new ModelAndView("redirect:/welcome/index.do");
				}
				result.addObject("message", "positionData.commit.error");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "positionDataId", defaultValue = "-1") final int positionDataId) {
		ModelAndView result;
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		final PositionData positionDataDB = this.positionDataService.findOne(positionDataId);

		try {
			Assert.notNull(rookieLogin, "No rookie is login");
			Assert.notNull(positionDataDB, "Not found positionData in DB");
			Assert.isTrue(positionDataDB.getCurricula().getRookie().equals(rookieLogin), "Not allow to delete, diferent rookie");
			result = new ModelAndView("redirect:/curricula/show.do?curriculaId=" + positionDataDB.getCurricula().getId());
			this.positionDataService.delete(positionDataDB);
		} catch (final Throwable oops) {
			System.out.println("Error en PositionDataRookieController.java Throwable: " + oops);
			if (positionDataDB != null)
				result = new ModelAndView("redirect:/curricula/show.do?curriculaId=" + positionDataDB.getCurricula().getId());
			else
				result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("message", "curricula.commit.error");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
}
