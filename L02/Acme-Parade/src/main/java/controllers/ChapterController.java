/*
 * BrotherhoodController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ChapterService;
import services.WelcomeService;
import domain.Chapter;
import domain.Proclaim;
import forms.RegistrationForm;

@Controller
@RequestMapping("/chapter")
public class ChapterController extends AbstractController {

	@Autowired
	ChapterService	chapterService;

	@Autowired
	ActorService	actorService;

	@Autowired
	WelcomeService	welcomeService;


	// Constructors -----------------------------------------------------------

	public ChapterController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final RegistrationForm registrationForm;
		registrationForm = new RegistrationForm();
		result = new ModelAndView("chapter/create");
		result.addObject("registrationForm", registrationForm);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegistrationForm registration, final BindingResult binding) {
		ModelAndView result;

		Chapter chapter;

		chapter = this.chapterService.reconstructR(registration, binding);

		if (binding.hasErrors())
			result = new ModelAndView("chapter/create");
		else
			try {
				this.chapterService.saveR(chapter);

				SimpleDateFormat formatter;
				String moment;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());
				result = new ModelAndView("welcome/index");
				result.addObject("moment", moment);

			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createModelAndView(chapter, "email.wrong");
				else
					result = this.createModelAndView(chapter, "error.email");
			}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	private ModelAndView createModelAndView(final Chapter chapter, final String string) {
		ModelAndView result;

		result = new ModelAndView("chapter/create");
		result.addObject("message", string);
		result.addObject("chapter", chapter);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Chapter> chapter = this.chapterService.findAll();
			result = new ModelAndView("chapter/list");
			result.addObject("chapter", chapter);
			result.addObject("requestURI", "chapter/list.do");
			result.addObject("logo", this.welcomeService.getLogo());
			result.addObject("system", this.welcomeService.getSystem());
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	private Boolean checkChapterLoggerSameChapterToShow(final int chapterId) {
		Boolean res = false;
		try {
			final Chapter chapterLogger = this.chapterService.getChapterByUserAccountId(LoginService.getPrincipal().getId());
			if (chapterLogger.getId() == chapterId) {
				System.out.println("Se esta mostrando la información del chapter logueado");
				res = true;
			} else
				System.out.println("El usuario logueado es un chapter diferente al que se va a mostrar");
		} catch (final Exception e) {
			System.out.println("El usuario logueado no es un chapter");
		}
		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;
		try {
			final Chapter chapter = this.chapterService.findOne(id);
			Assert.notNull(chapter);
			final Boolean checkChapter = LoginService.getPrincipal().getId() == chapter.getUserAccount().getId();
			final Collection<Proclaim> proclaims = this.chapterService.getChapterByUserAccountId(chapter.getUserAccount().getId()).getProclaim();
			System.out.println(chapter);
			result = new ModelAndView("chapter/show");
			result.addObject("chapter", chapter);
			result.addObject("proclaims", proclaims);
			result.addObject("checkChapter", checkChapter);
			// ALVARO 15/03/2019 11:35 -- Si el logueado es el mismo que se va a mostrar se habilita el botón de crear una proclaim
			if (this.checkChapterLoggerSameChapterToShow(id))
				result.addObject("validChapter", true);
			result.addObject("requestURI", "chapter/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	//Nuevo
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int chapterId) {
		ModelAndView result;

		final Chapter chapter = this.chapterService.findOne(chapterId);
		System.out.println("Chapter encontrado: " + chapter);
		if (this.chapterService.findOne(chapterId) == null || LoginService.getPrincipal().getId() != chapter.getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(chapter, "chapter.null");

			try {
				this.chapterService.delete(chapter);
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (final Exception e) {
				System.out.println(e.getMessage());
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}
	//Nuevo
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody
	Chapter export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		Chapter result = new Chapter();
		result = this.chapterService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		return result;
	}

}
