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
import services.FloatBroService;
import domain.Brotherhood;
import domain.FloatBro;

/*
 * CONTROL DE CAMBIOS FloatBroBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 19:18 CREACI�N DE LA CLASE
 */

@Controller
@RequestMapping("/floatBro")
public class FloatBroController extends AbstractController {

	@Autowired
	private FloatBroService		floatBroService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Constructors -----------------------------------------------------------

	public FloatBroController() {
		super();
	}

	@RequestMapping(value = "/showPictureFloat", method = RequestMethod.GET)
	public ModelAndView showPictureFloat(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;

		final FloatBro floatBro = this.floatBroService.findOne(id);

		List<String> pictures = new ArrayList<>();
		if (floatBro.getPictures() != null && floatBro.getPictures().contains("'"))
			pictures = Arrays.asList(floatBro.getPictures().split("'"));

		final String actuallanguage = LocaleContextHolder.getLocale().getDisplayLanguage();
		Boolean language;
		if (actuallanguage.equals("English")) {
			System.out.println("Actual languge: " + actuallanguage);
			language = true;
		} else {
			System.out.println("Actual languge: " + actuallanguage);
			language = false;
		}

		result = new ModelAndView("floatBro/showPictureFloat");
		result.addObject("language", language);
		result.addObject("pictures", pictures);
		result.addObject("floatBro", floatBro);
		result.addObject("requestURI", "floatBro/showPictureFloat.do");
		return result;
	}

	@RequestMapping(value = "/listFloat", method = RequestMethod.GET)
	public ModelAndView listFloat(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;

		final Brotherhood brotherhood = this.brotherhoodService.findOne(id);
		final Collection<FloatBro> floatbro = brotherhood.getFloatBro();
		result = new ModelAndView("floatBro/listFloat");
		result.addObject("brotherhood", brotherhood);
		result.addObject("floatbro", floatbro);
		result.addObject("requestURI", "floatBro/listFloat.do");
		return result;
	}

}
