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
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.BigDecimal;
import security.LoginService;
import services.FloatService;
import services.WelcomeService;

/*
 * CONTROL DE CAMBIOS domain.FloatBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 19:18 CREACIÓN DE LA CLASE
 */

@Controller
@RequestMapping("/float/brotherhood")
public class FloatBrotherhoodController extends AbstractController {

	@Autowired
	private FloatService	floatService;
	
	@Autowired
	private WelcomeService welcomeService;


	// Constructors -----------------------------------------------------------

	public FloatBrotherhoodController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<domain.BigDecimal> floats = this.floatService.findAllBrotherhoodLogged();
		final String actuallanguage = LocaleContextHolder.getLocale().getDisplayLanguage();
		Boolean language;
		if (actuallanguage.equals("English")) {
			System.out.println("Actual languge: " + actuallanguage);
			language = true;
		} else {
			System.out.println("Actual languge: " + actuallanguage);
			language = false;
		}
		result = new ModelAndView("float/brotherhood/list");
		result.addObject("language", language);
		result.addObject("floats", floats);
		result.addObject("requestURI", "float/brotherhood/list.do");
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "floatId", defaultValue = "-1") final int floatId) {
		ModelAndView result;
		final domain.BigDecimal floatt = this.floatService.findOne(floatId);

		if (this.floatService.findOne(floatId) == null || LoginService.getPrincipal().getId() != floatt.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(floatt, "float.null");
			List<String> pictures = new ArrayList<>();
			if (floatt.getPictures() != null && floatt.getPictures().length()>0) {
				pictures = Arrays.asList(floatt.getPictures().split("'"));	
			}
			result = new ModelAndView("float/brotherhood/show");

			result.addObject("pictures", pictures);
			result.addObject("floatt", floatt);
			result.addObject("requestURI", "float/brotherhood/show.do");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		BigDecimal floatt;
		floatt = this.floatService.create();
		result = this.createEditModelAndView(floatt);
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id", defaultValue = "-1") final int floatId) {
		ModelAndView result;
		domain.BigDecimal floatt;
		floatt = this.floatService.findOne(floatId);
		if (this.floatService.findOne(floatId) == null || LoginService.getPrincipal().getId() != floatt.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(floatt);
			result = this.createEditModelAndView(floatt);
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int floatId) {
		ModelAndView result;

		final domain.BigDecimal floatt = this.floatService.findOne(floatId);
		System.out.println("Float encontrado: " + floatt);
		if (this.floatService.findOne(floatId) == null || LoginService.getPrincipal().getId() != floatt.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(floatt, "float.null");

			try {
				this.floatService.delete(floatt);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(floatt, "float.commit.error");
			}
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(domain.BigDecimal floatt, final BindingResult binding) {
		ModelAndView result;

		floatt = this.floatService.reconstruct(floatt, binding);
		final List<String> pictures = Arrays.asList(floatt.getPictures().split("'"));
		if (floatt.getPictures() != null && floatt.getPictures().length() > 0)
			for (final String picture : pictures)
				if (!picture.startsWith("https:")) {
					final ObjectError error = new ObjectError("pictures", "Must be a link");
					binding.addError(error);
					binding.rejectValue("pictures", "error.float.pictures");
				}
		if (binding.hasErrors()) {
			System.out.println("El error pasa por aquí alvaro (IF de save())");
			System.out.println(binding);
			result = this.createEditModelAndView(floatt);
		} else
			try {
				System.out.println("El error pasa por aquí alvaro (TRY de save())");
				System.out.println(binding);
				this.floatService.save(floatt);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("El error: ");
				System.out.println(oops);
				System.out.println(binding);
				if (oops.getMessage().equals("pictures"))
					result = this.createEditModelAndView(floatt, "float.picturesWrong");
				else if (oops.getMessage().equals("error.float.pictures"))
					result = this.createEditModelAndView(floatt, "float.picturesWrong");
				else
					result = this.createEditModelAndView(floatt, "float.commit.error");
			}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
	private ModelAndView createEditModelAndView(final domain.BigDecimal floatt) {
		ModelAndView result;

		result = new ModelAndView("float/brotherhood/edit");

		result.addObject("float", floatt);
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final domain.BigDecimal floatt, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("float/brotherhood/edit");

		result.addObject("float", floatt);
		result.addObject("message", messageCode);
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
}
