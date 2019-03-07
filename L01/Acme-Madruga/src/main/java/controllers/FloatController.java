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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.FloatService;
import domain.Brotherhood;
import domain.Float;

/*
 * CONTROL DE CAMBIOS FloatBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 19:18 CREACI�N DE LA CLASE
 */

@Controller
@RequestMapping("/float")
public class FloatController extends AbstractController {

	@Autowired
	private FloatService		floatService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Constructors -----------------------------------------------------------

	public FloatController() {
		super();
	}

	@RequestMapping(value = "/showPictureFloat", method = RequestMethod.GET)
	public ModelAndView showPictureFloat(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;
		try {
			final domain.Float floatt = this.floatService.findOne(id);

			List<String> pictures = new ArrayList<>();
			if (floatt.getPictures() != null && floatt.getPictures().contains("'"))
				pictures = Arrays.asList(floatt.getPictures().split("'"));

			final String actuallanguage = LocaleContextHolder.getLocale().getDisplayLanguage();
			Boolean language;
			if (actuallanguage.equals("English")) {
				System.out.println("Actual languge: " + actuallanguage);
				language = true;
			} else {
				System.out.println("Actual languge: " + actuallanguage);
				language = false;
			}

			result = new ModelAndView("float/showPictureFloat");
			result.addObject("language", language);
			result.addObject("pictures", pictures);
			result.addObject("float", floatt);
			result.addObject("requestURI", "float/showPictureFloat.do");	
		} catch (Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/listFloat", method = RequestMethod.GET)
	public ModelAndView listFloat(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;
		try {
			final Brotherhood brotherhood = this.brotherhoodService.findOne(id);
			final Collection<Float> floatt = brotherhood.getFloats();
			result = new ModelAndView("float/listFloat");
			result.addObject("brotherhood", brotherhood);
			result.addObject("float", floatt);
			result.addObject("requestURI", "float/listFloat.do");	
		} catch (Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;		
	}

}
