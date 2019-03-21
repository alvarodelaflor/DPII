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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.ChapterService;
import services.FloatService;
import services.MemberService;
import services.MessageService;
import services.ParadeService;
import services.PositionAuxService;
import services.WelcomeService;
import domain.Chapter;
import domain.Parade;

/*
 * CONTROL DE CAMBIOS ParadeBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 12:29 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 16:35 A�ADIDO RECONSTRUIDOR PROCESSION
 */

@Controller
@RequestMapping("/parade/chapter")
public class ParadeChapterController extends AbstractController {

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	BrotherhoodService		brotherhoodService;

	@Autowired
	PositionAuxService		positionAuxService;

	@Autowired
	FloatService			floatService;

	@Autowired
	MessageService			messageService;

	@Autowired
	MemberService			memberService;

	@Autowired
	WelcomeService			welcomeService;

	@Autowired
	private ChapterService	chapterService;


	// Constructors -----------------------------------------------------------

	public ParadeChapterController() {
		super();
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "paradeId", defaultValue = "-1") final int paradeId) {
		ModelAndView result;
		final Parade parade = this.paradeService.findOne(paradeId);

		if (this.paradeService.findOne(paradeId) == null)
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(parade, "parade.nul");

			result = new ModelAndView("parade/brotherhood/show");
			result.addObject("parade", parade);
			result.addObject("requestURI", "parade/brotherhood/show.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	//LIST PARADES BY AREA
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listParades() {
		ModelAndView result;
		try {
			final Chapter chapter = this.chapterService.findByUserAccount(LoginService.getPrincipal().getId());
			System.out.println(chapter.getId());
			final Collection<Parade> submittedParades = this.paradeService.findSubmittedParadesByChapter(chapter.getId());
			final Collection<Parade> acceptedParades = this.paradeService.findAcceptedParadesByChapter(chapter.getId());
			final Collection<Parade> rejectedParades = this.paradeService.findRejectedParadesByChapter(chapter.getId());
			System.out.println(submittedParades);
			result = new ModelAndView("parade/chapter/list");
			result.addObject("submittedParades", submittedParades);
			result.addObject("acceptedParades", acceptedParades);
			result.addObject("rejectedParades", rejectedParades);
			result.addObject("requestURI", "parade/chapter/list.do");
		} catch (final Exception e) {
			System.out.println(e.getMessage());
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id", defaultValue = "-1") final int paradeId) {
		ModelAndView result;
		Parade parade;
		parade = this.paradeService.findOne(paradeId);
		if (this.paradeService.findOne(paradeId) == null || LoginService.getPrincipal().getId() != parade.getBrotherhood().getArea().getChapter().getUserAccount().getId() || parade.getIsFinal().equals(false))
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(parade);
			result = this.createEditModelAndView(parade);
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Parade parade, final BindingResult binding) {
		ModelAndView result;

		parade = this.paradeService.reconstructStatus(parade, binding);
		if (binding.hasErrors()) {
			System.out.println("Binding con errores: " + binding.getAllErrors());
			result = this.createEditModelAndView(parade);
		} else
			try {
				this.paradeService.updateStatus(parade);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("El error: ");
				System.out.println(oops);
				System.out.println(binding);
				result = this.createEditModelAndView(parade, "parade.commit.error");
			}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	public ModelAndView createEditModelAndView(final Parade parade) {
		ModelAndView result;

		result = new ModelAndView("parade/chapter/edit");

		result.addObject("parade", parade);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}
	private ModelAndView createEditModelAndView(final Parade parade, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("parade/chapter/edit");

		result.addObject("parade", parade);
		result.addObject("message", messageCode);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}
}
