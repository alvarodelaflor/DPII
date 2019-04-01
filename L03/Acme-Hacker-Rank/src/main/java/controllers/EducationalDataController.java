/*
 * EducationalDataController.java
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
import domain.Hacker;
import domain.EducationalData;
import services.HackerService;
import services.EducationalDataService;

/*
 * CONTROL DE CAMBIOS CurriculaHackerController.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/educationalData")
public class EducationalDataController extends AbstractController {
	
	@Autowired
	private HackerService hackerService;
	
	@Autowired
	private EducationalDataService educationalDataService;


	// Constructors -----------------------------------------------------------

	public EducationalDataController() {
		super();
	}
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "educationalDataId", defaultValue = "-1") final int educationalDataId) {

		ModelAndView result;
		try {
			EducationalData educationalDataDB = this.educationalDataService.findOne(educationalDataId);
			Assert.notNull(educationalDataDB, "Not found educational in DB");
			result = new ModelAndView("educationalData/show");
			
			Hacker hackerLogin = this.hackerService.getHackerLogin();
			if (hackerLogin!=null && educationalDataDB.getCurricula().getHacker().equals(hackerLogin)) {
				result.addObject("hackerLogin", true);	
			}
			
			result.addObject("educationalData", educationalDataDB);
						
			result.addObject("requestURI", "educationalData/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
}
