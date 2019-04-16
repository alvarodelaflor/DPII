/*
 * CurricculaHackerController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.EducationalDataService;
import services.HackerService;
import services.MiscellaneousAttachmentService;
import services.PositionDataService;
import domain.Curricula;
import domain.EducationalData;
import domain.Hacker;
import domain.MiscellaneousAttachment;
import domain.PositionData;

/*
 * CONTROL DE CAMBIOS CurriculaHackerController.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/curricula")
public class CurriculaController extends AbstractController {

	@Autowired
	private HackerService					hackerService;

	@Autowired
	private CurriculaService				curriculaService;

	@Autowired
	private EducationalDataService			educationalDataService;

	@Autowired
	private PositionDataService				positionDataService;

	@Autowired
	private MiscellaneousAttachmentService	miscellaneousAttachmentService;


	// Constructors -----------------------------------------------------------

	public CurriculaController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "hackerId", defaultValue = "-1") final int hackerId) {
		ModelAndView result;
		try {
			Hacker hacker = this.hackerService.findOne(hackerId);
			final Hacker hackerLogin = this.hackerService.getHackerLogin();
			if (hacker == null && hackerLogin != null)
				hacker = hackerLogin;
			Assert.notNull(hacker, "Not hacker found in DB");
			result = new ModelAndView("curricula/list");
			if (hackerLogin != null && hacker.equals(hackerLogin))
				result.addObject("hackerLogger", true);
			Assert.notNull(hacker, "Hacker is null");
			final Collection<Curricula> curriculas = this.curriculaService.findAllNotCopyByHacker(hacker);
			result.addObject("curriculas", curriculas);
			result.addObject("requestURI", "curriculas/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "curriculaId", defaultValue = "-1") final int curriculaId) {

		ModelAndView result;
		try {
			final Curricula curriculaDB = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curriculaDB, "Not found curricula in DB");
			result = new ModelAndView("curricula/show");

			final Hacker hackerLogin = this.hackerService.getHackerLogin();
			if (hackerLogin != null && curriculaDB.getHacker().equals(hackerLogin) && curriculaDB.getIsCopy().equals(false)) {
				result.addObject("hackerLogin", true);
				final MiscellaneousAttachment miscellaneousAttachment = this.miscellaneousAttachmentService.createWithHistory(curriculaDB);
				result.addObject("miscellaneousAttachment", miscellaneousAttachment);
			}

			result.addObject("curricula", curriculaDB);

			final List<EducationalData> educationalDatas = (List<EducationalData>) this.educationalDataService.getEducationalDataFromCurricula(curriculaDB);
			result.addObject("educationalDatas", educationalDatas);

			final List<PositionData> positionDatas = (List<PositionData>) this.positionDataService.getPositionDataFromCurricula(curriculaDB);
			result.addObject("positionDatas", positionDatas);

			final List<MiscellaneousAttachment> miscellaneousAttachments = (List<MiscellaneousAttachment>) this.miscellaneousAttachmentService.getMiscellaneousAttachmentFromCurricula(curriculaDB);
			result.addObject("miscellaneousAttachments", miscellaneousAttachments);

			result.addObject("requestURI", "hacker/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
}
