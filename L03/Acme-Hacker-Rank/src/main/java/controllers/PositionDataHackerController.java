/*
 * PositionHackerController.java
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

import domain.Curricula;
import domain.Hacker;
import domain.PositionData;
import services.CurriculaService;
import services.HackerService;
import services.PositionDataService;
import services.PositionService;

/*
 * CONTROL DE CAMBIOS PositionDataHackerController.java
 * 
 * ALVARO 09/03/2019 23:47 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/positionData/hacker")
public class PositionDataHackerController extends AbstractController {
	
	@Autowired
	private HackerService hackerService;

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private PositionDataService positionDataService;
	
	@Autowired
	private PositionService positionService;


	// Constructors -----------------------------------------------------------

	public PositionDataHackerController() {
		super();
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "curriculaId", defaultValue = "-1") final int curriculaId) {
		ModelAndView result;
		try {
			Curricula curriculaDB = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curriculaDB, "Curricula not found in DB");
			Hacker hackerLogin = this.hackerService.getHackerLogin();
			Assert.notNull(hackerLogin, "No hacker is login");
			Assert.isTrue(hackerLogin.equals(curriculaDB.getHacker()));
			PositionData positionData = this.positionDataService.createWithHistory(curriculaDB);
			result = new ModelAndView("positionData/hacker/create");
			result.addObject("positionData", positionData);	
			result.addObject("curricula", curriculaDB);
			// TODO En este método solo serán añadidos las positiones validas
			result.addObject("positions", this.positionService.findALL());
		} catch (Exception e) {
			System.out.println("Error e en GET /create PositionDataController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	}
	

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "positionDataId", defaultValue = "-1") final int positionDataId) {
		ModelAndView result;
		try {
			PositionData positionDataDB = this.positionDataService.findOne(positionDataId);
			Assert.notNull(positionDataDB, "PositionData not found in DB");
			Hacker hackerLogin = this.hackerService.getHackerLogin();
			Assert.notNull(hackerLogin, "No hacker is login");
			Assert.isTrue(hackerLogin.equals(positionDataDB.getCurricula().getHacker()), "Not allow to edit not own PositionData");
			result = new ModelAndView("positionData/hacker/edit");
			result.addObject("positionData", positionDataDB);
			result.addObject("curricula", positionDataDB.getCurricula());
			// TODO En este método solo serán añadidos las positiones validas
			result.addObject("positions", this.positionService.findALL());
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid PositionData positionData, final BindingResult binding) {
		ModelAndView result;
		
		if (positionData.getStartDate()!=null && positionData.getEndDate()!=null && this.positionDataService.checkDate(positionData.getStartDate(), positionData.getEndDate())) {
			final ObjectError error = new ObjectError("startDate", "Start date mus be before than end Date");
			binding.addError(error);
			binding.rejectValue("startDate", "error.startDate");
		}
		
		if (positionData.getStartDate()!=null && positionData.getEndDate()!=null && this.positionDataService.checkDate(positionData.getStartDate(), positionData.getEndDate())) {
			final ObjectError error = new ObjectError("endDate", "Start date mus be before than end Date");
			binding.addError(error);
			binding.rejectValue("endDate", "error.endDate");
		}
		
		if (binding.hasErrors()) {
			System.out.println("Error en PositionDataHackerController.java, binding: " + binding);
			result = new ModelAndView("positionData/hacker/edit");
			result.addObject("positionData", positionData);
			result.addObject("curricula", positionData.getCurricula());
			// TODO En este método solo serán añadidos las positiones validas
			result.addObject("positions", this.positionService.findALL());
		} else
			try {
				Hacker hackerLogin = this.hackerService.getHackerLogin();
				Assert.notNull(hackerLogin, "No hacker is login");
				Assert.isTrue(positionData != null, "positionData.null");
				Assert.isTrue(this.hackerService.getHackerByCurriculaId(positionData.getCurricula()).equals(hackerLogin), "Not allow to edit a not own PositionData");
				PositionData positionDataSave = this.positionDataService.save(positionData);
				result = new ModelAndView("redirect:/positionData/show.do?positionDataId="+positionDataSave.getId());
				result.addObject("requestURI", "curricula/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE CurriculaHackerController.java Throwable: " + oops);
				result = new ModelAndView("positionData/hacker/edit");
				result.addObject("positionData", positionData);
				try {
					result.addObject("curricula", positionData.getCurricula());
					// TODO En este método solo serán añadidos las positiones validas
					result.addObject("positions", this.positionService.findALL());
				} catch (Exception e) {
					return new ModelAndView("redirect:/welcome/index.do");
				}
				result.addObject("message", "positionData.commit.error");
			}
		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "positionDataId", defaultValue = "-1") final int positionDataId) {
		ModelAndView result;
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		PositionData positionDataDB = this.positionDataService.findOne(positionDataId);
		
		try {
			Assert.notNull(hackerLogin, "No hacker is login");
			Assert.notNull(positionDataDB, "Not found positionData in DB");
			Assert.isTrue(positionDataDB.getCurricula().getHacker().equals(hackerLogin), "Not allow to delete, diferent hacker");
			result = new ModelAndView("redirect:/curricula/show.do?curriculaId="+positionDataDB.getCurricula().getId());
			this.positionDataService.delete(positionDataDB);
		} catch (final Throwable oops) {
			System.out.println("Error en PositionDataHackerController.java Throwable: " + oops);
			if (positionDataDB!=null) {
				result = new ModelAndView("redirect:/curricula/show.do?curriculaId="+positionDataDB.getCurricula().getId());				
			} else {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
			result.addObject("message", "curricula.commit.error");
		}
		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	}
}
