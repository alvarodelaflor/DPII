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



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Curricula;
import domain.EducationalData;
import domain.Hacker;
import services.CurriculaService;
import services.EducationalDataService;
import services.HackerService;

/*
 * CONTROL DE CAMBIOS EducationalDataHackerController.java
 * 
 * ALVARO 09/03/2019 23:47 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/educationalData/hacker")
public class EducationalDataHackerController extends AbstractController {
	
	@Autowired
	private HackerService hackerService;

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private EducationalDataService educationalDataService;


	// Constructors -----------------------------------------------------------

	public EducationalDataHackerController() {
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
			EducationalData educationalData = this.educationalDataService.createWithHistory(curriculaDB);
			result = new ModelAndView("educationalData/hacker/create");
			result.addObject("educationalData", educationalData);	
			result.addObject("curricula", curriculaDB);
		} catch (Exception e) {
			System.out.println("Error e en GET /create EducationalDataController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "educationalDataId", defaultValue = "-1") final int educationalDataId) {
		ModelAndView result;
		try {
			EducationalData educationalDataDB = this.educationalDataService.findOne(educationalDataId);
			Assert.notNull(educationalDataDB, "EducationalData not found in DB");
			Hacker hackerLogin = this.hackerService.getHackerLogin();
			Assert.notNull(hackerLogin, "No hacker is login");
			Assert.isTrue(hackerLogin.equals(educationalDataDB.getCurricula().getHacker()), "Not allow to edit not own EducationalData");
			result = new ModelAndView("educationalData/hacker/edit");
			result.addObject("educationalData", educationalDataDB);
			result.addObject("curricula", educationalDataDB.getCurricula());
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid EducationalData educationalData, final BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			System.out.println("Error en EducationalDataHackerController.java, binding: " + binding);
			result = new ModelAndView("curricula/hacker/create");
			result.addObject("educationalData", educationalData);
			result.addObject("curricula", educationalData.getCurricula());
		} else
			try {
				Hacker hackerLogin = this.hackerService.getHackerLogin();
				Assert.notNull(hackerLogin, "No hacker is login");
				Assert.isTrue(educationalData != null, "educationalData.null");
				Assert.isTrue(this.hackerService.getHackerByCurriculaId(educationalData.getCurricula()).equals(hackerLogin), "Not allow to edit a not own EducationalData");
				this.educationalDataService.save(educationalData);
				result = new ModelAndView("redirect:/curricula/show.do?curriculaId="+educationalData.getCurricula().getId());
				result.addObject("requestURI", "curricula/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE CurriculaHackerController.java Throwable: " + oops);
				result = new ModelAndView("educationalData/hacker/edit");
				result.addObject("educationalData", educationalData);
				try {
					result.addObject("curricula", educationalData.getCurricula());	
				} catch (Exception e) {
					return new ModelAndView("redirect:/welcome/index.do");
				}
				result.addObject("message", "educationalData.commit.error");
			}
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "educationalDataId", defaultValue = "-1") final int educationalDataId) {
		ModelAndView result;
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		EducationalData educationalDataDB = this.educationalDataService.findOne(educationalDataId);
		
		try {
			Assert.notNull(hackerLogin, "No hacker is login");
			Assert.notNull(educationalDataDB, "Not found educationalData in DB");
			Assert.isTrue(educationalDataDB.getCurricula().getHacker().equals(hackerLogin), "Not allow to delete, diferent hacker");
			result = new ModelAndView("redirect:/curricula/show.do?curriculaId="+educationalDataDB.getCurricula().getId());
			this.educationalDataService.delete(educationalDataDB);
		} catch (final Throwable oops) {
			System.out.println("Error en EducationalDataHackerController.java Throwable: " + oops);
			if (educationalDataDB!=null) {
				result = new ModelAndView("redirect:/curricula/show.do?curriculaId="+educationalDataDB.getCurricula().getId());				
			} else {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
			result.addObject("message", "curricula.commit.error");
		}
		return result;
	}
}
