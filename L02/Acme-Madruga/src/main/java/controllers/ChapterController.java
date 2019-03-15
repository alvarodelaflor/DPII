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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
				result = new ModelAndView("welcome/index");
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

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;
		try {
			final Chapter chapter = this.chapterService.findOne(id);
			Assert.notNull(chapter);
			final Collection<Proclaim> proclaims = this.chapterService.getChapterByUserAccountId(chapter.getUserAccount().getId()).getProclaim();
			System.out.println(chapter);
			result = new ModelAndView("chapter/show");
			result.addObject("chapter", chapter);
			result.addObject("proclaims", proclaims);
			result.addObject("requestURI", "chapter/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

}
