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

import security.LoginService;
import services.FloatBroService;
import domain.FloatBro;

/*
 * CONTROL DE CAMBIOS FloatBroBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 19:18 CREACI�N DE LA CLASE
 */

@Controller
@RequestMapping("/floatBro/brotherhood")
public class FloatBroBrotherhoodController extends AbstractController {

	@Autowired
	private FloatBroService	floatBroService;


	// Constructors -----------------------------------------------------------

	public FloatBroBrotherhoodController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<FloatBro> floatBros = this.floatBroService.findAllBrotherhoodLogged();
		final String actuallanguage = LocaleContextHolder.getLocale().getDisplayLanguage();
		Boolean language;
		if (actuallanguage.equals("English")) {
			System.out.println("Actual languge: " + actuallanguage);
			language = true;
		} else {
			System.out.println("Actual languge: " + actuallanguage);
			language = false;
		}
		result = new ModelAndView("floatBro/brotherhood/list");
		result.addObject("language", language);
		result.addObject("floatBros", floatBros);
		result.addObject("requestURI", "floatBro/brotherhood/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "floatBroId", defaultValue = "-1") final int floatBroId) {
		ModelAndView result;
		final FloatBro floatBro = this.floatBroService.findOne(floatBroId);

		if (this.floatBroService.findOne(floatBroId) == null || LoginService.getPrincipal().getId() != floatBro.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(floatBro, "floatBro.nul");
			List<String> pictures = new ArrayList<>();
			if (floatBro.getPictures() != null)
				pictures = Arrays.asList(floatBro.getPictures().split("'"));
			result = new ModelAndView("floatBro/brotherhood/show");

			result.addObject("pictures", pictures);
			result.addObject("floatBro", floatBro);
			result.addObject("requestURI", "floatBro/brotherhood/show.do");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FloatBro floatBro;
		floatBro = this.floatBroService.create();
		result = this.createEditModelAndView(floatBro);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id", defaultValue = "-1") final int floatBroId) {
		ModelAndView result;
		FloatBro floatBro;
		floatBro = this.floatBroService.findOne(floatBroId);
		if (this.floatBroService.findOne(floatBroId) == null || LoginService.getPrincipal().getId() != floatBro.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(floatBro);
			result = this.createEditModelAndView(floatBro);
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int floatBroId) {
		ModelAndView result;

		final FloatBro floatBro = this.floatBroService.findOne(floatBroId);
		System.out.println("FloatBro encontrado: " + floatBro);
		if (this.floatBroService.findOne(floatBroId) == null || LoginService.getPrincipal().getId() != floatBro.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(floatBro, "floatBro.null");

			try {
				this.floatBroService.delete(floatBro);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(floatBro, "floatBro.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(FloatBro floatBro, final BindingResult binding) {
		ModelAndView result;

		floatBro = this.floatBroService.reconstruct(floatBro, binding);
		final List<String> pictures = Arrays.asList(floatBro.getPictures().split("'"));
		if (floatBro.getPictures() != null && floatBro.getPictures().length() > 0)
			for (final String picture : pictures)
				if (!picture.startsWith("https:")) {
					final ObjectError error = new ObjectError("pictures", "Must be a link");
					binding.addError(error);
					binding.rejectValue("pictures", "error.floatBro.pictures");
				}
		if (binding.hasErrors()) {
			System.out.println("El error pasa por aqu� alvaro (IF de save())");
			System.out.println(binding);
			result = this.createEditModelAndView(floatBro);
		} else
			try {
				System.out.println("El error pasa por aqu� alvaro (TRY de save())");
				System.out.println(binding);
				this.floatBroService.save(floatBro);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("El error: ");
				System.out.println(oops);
				System.out.println(binding);
				if (oops.getMessage().equals("pictures"))
					result = this.createEditModelAndView(floatBro, "floatBro.picturesWrong");
				else if (oops.getMessage().equals("error.floatBro.pictures"))
					result = this.createEditModelAndView(floatBro, "floatBro.picturesWrong");
				else
					result = this.createEditModelAndView(floatBro, "floatBro.commit.error");
			}
		return result;
	}
	private ModelAndView createEditModelAndView(final FloatBro floatBro) {
		ModelAndView result;

		result = new ModelAndView("floatBro/brotherhood/edit");

		result.addObject("floatBro", floatBro);

		return result;
	}

	private ModelAndView createEditModelAndView(final FloatBro floatBro, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("floatBro/brotherhood/edit");

		result.addObject("floatBro", floatBro);
		result.addObject("message", messageCode);

		return result;
	}
}