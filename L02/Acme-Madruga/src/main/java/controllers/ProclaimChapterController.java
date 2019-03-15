/*
 * HistoryController.java
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
import org.springframework.web.servlet.ModelAndView;
import domain.Chapter;
import domain.Proclaim;
import security.LoginService;
import services.ChapterService;
import services.ProclaimService;
import services.WelcomeService;

/*
 * CONTROL DE CAMBIOS History.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/proclaim/chapter")
public class ProclaimChapterController extends AbstractController {
	
	@Autowired
	private ChapterService chapterService;

	@Autowired
	private ProclaimService proclaimService;
	
	@Autowired
	private WelcomeService welcomeService;


	// Constructors -----------------------------------------------------------

	public ProclaimChapterController() {
		super();
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			Chapter chapterLogger = this.chapterService.getChapterByUserAccountId(LoginService.getPrincipal().getId());
			Assert.notNull(chapterLogger, "No chapter is logger");
			Proclaim proclaim = this.proclaimService.create();
			result = new ModelAndView("proclaim/chapter/create");
			result.addObject("proclaim", proclaim);
			result.addObject("chapterId", chapterLogger.getId());
			result.addObject("logo", welcomeService.getLogo());
			result.addObject("system", welcomeService.getSystem());			
		} catch (Exception e) {
			System.out.println("Error e en GET /create ProclaimController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Proclaim proclaim, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("Error en ProclaimChapterController.java, binding: " + binding);
			result = new ModelAndView("proclaim/chapter/create");
			result.addObject("proclaim", proclaim);
		} else
			try {
				Chapter chapter = this.chapterService.getChapterByUserAccountId(LoginService.getPrincipal().getId());
				Assert.notNull(chapter, "Chapter is null");
				Assert.isTrue(proclaim != null, "proclaim.null");
				this.proclaimService.save(proclaim);
				result = new ModelAndView("redirect:/chapter/show.do?id="+chapter.getId());
				result.addObject("requestURI", "chapter/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE ProclaimChapterController.java Throwable: " + oops);
				result = new ModelAndView("proclaim/chapter/edit");
				Chapter chapterLogger = this.chapterService.getChapterByUserAccountId(LoginService.getPrincipal().getId());
				result.addObject("chapterId", chapterLogger.getId());
				result.addObject("proclaim", proclaim);
				result.addObject("message", "proclaim.commit.error");
			}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
}
