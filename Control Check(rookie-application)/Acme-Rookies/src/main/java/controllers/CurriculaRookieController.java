/*
 * CurricculaRookieController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

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
import services.RookieService;
import domain.Curricula;
import domain.Rookie;

/*
 * CONTROL DE CAMBIOS CurriculaRookieController.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/curricula/rookie")
public class CurriculaRookieController extends AbstractController {

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private CurriculaService	curriculaService;


	// Constructors -----------------------------------------------------------

	public CurriculaRookieController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final Rookie rookieLogin = this.rookieService.getRookieLogin();
			Assert.notNull(rookieLogin, "No rookie is login");
			final Curricula curricula = this.curriculaService.create();
			result = new ModelAndView("curricula/rookie/edit");
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			System.out.println("Error e en GET /create CurriculaController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "curriculaId", defaultValue = "-1") final int curriculaId) {
		ModelAndView result;
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		try {
			final Curricula curriculaDB = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curriculaDB, "Curricula not found in DB");
			Assert.isTrue(!curriculaDB.getIsCopy(), "Trying to edit a copyCurricula");
			Assert.notNull(rookieLogin, "No rookie is login");
			curriculaDB.setLinkGitHub(curriculaDB.getLinkGitHub().replaceAll("https://www.github.com/", ""));
			Assert.isTrue(rookieLogin.equals(curriculaDB.getRookie()));
			result = new ModelAndView("curricula/rookie/edit");
			result.addObject("curricula", curriculaDB);
		} catch (final Exception e) {
			if (rookieLogin != null) {
				result = new ModelAndView("curricula/list");
				final Collection<Curricula> curriculas = this.curriculaService.findAllNotCopyByRookie(rookieLogin);
				result.addObject("curriculas", curriculas);
			} else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Curricula curricula, final BindingResult binding) {
		ModelAndView result;
		try {
			curricula = this.curriculaService.reconstruct(curricula, binding);
		} catch (final Exception e) {
			System.out.println("Error e reconstruct de curricula: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("logo", this.getLogo());
			result.addObject("system", this.getSystem());
			return result;
		}

		if (!this.checkLinkGitHub(curricula.getLinkGitHub())) {
			curricula.setLinkGitHub(curricula.getLinkGitHub().replaceAll("https://www.github.com/", ""));
			final ObjectError error = new ObjectError("linkGitHub", "Must be a GitHub link");
			binding.addError(error);
			binding.rejectValue("linkGitHub", "error.linkGitHub");
		}

		if (binding.hasErrors()) {
			System.out.println("Error en CurriculaRookieController.java, binding: " + binding);
			result = new ModelAndView("curricula/rookie/create");
			curricula.setLinkGitHub(curricula.getLinkGitHub().replaceAll("https://www.github.com/", ""));
			result.addObject("curricula", curricula);
		} else
			try {
				final Rookie rookieLogin = this.rookieService.getRookieLogin();
				Assert.notNull(rookieLogin, "No rookie is login");
				Assert.isTrue(curricula != null, "curricula.null");
				Assert.isTrue(!curricula.getIsCopy(), "Trying to edit a copyCurricula");
				final Curricula saveCurricula = this.curriculaService.save(curricula);
				// CHECK COPY CURRICULA WORK
				//				this.curriculaService.createCurriculaCopyAndSave(saveCurricula);
				//CHECK COPY CURRICULA WORK
				result = new ModelAndView("redirect:/curricula/show.do?curriculaId=" + saveCurricula.getId());
				result.addObject("requestURI", "curricula/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE CurriculaRookieController.java Throwable: " + oops);
				result = new ModelAndView("curricula/rookie/edit");
				curricula.setLinkGitHub(curricula.getLinkGitHub().replaceAll("https://www.github.com/", ""));
				result.addObject("curricula", curricula);
				result.addObject("message", "curricula.commit.error");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private Boolean checkLinkGitHub(final String linkGitHub) {
		Boolean res = true;
		try {
			final String linkTocheck = linkGitHub.replaceAll("https://www.github.com/", "");
			if (linkTocheck.contains("<") || linkTocheck.contains(">") || linkTocheck.contains(":") || linkTocheck.contains("/") || linkTocheck.contains("|") || linkTocheck.contains("'") || linkTocheck.contains(","))
				res = false;
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "curriculaId", defaultValue = "-1") final int curriculaId) {
		ModelAndView result;
		final Rookie rookieLogin = this.rookieService.getRookieLogin();

		try {
			Assert.notNull(rookieLogin, "No rookie is login");
			final Curricula curriculaDB = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curriculaDB, "Not found curricula in DB");
			Assert.isTrue(curriculaDB.getRookie().equals(rookieLogin), "Not allow to delete, diferent rookie");
			this.curriculaService.delete(curriculaDB);
			result = new ModelAndView("redirect:/curricula/list.do?rookieId=" + rookieLogin.getId());
		} catch (final Throwable oops) {
			System.out.println("Error en CurriculaRookieController.java Throwable: " + oops);
			if (rookieLogin != null)
				result = new ModelAndView("redirect:/curricula/list.do?rookieId=" + rookieLogin.getId());
			else
				result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("message", "curricula.commit.error");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
}
