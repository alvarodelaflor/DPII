/*
 * CurricculaCleanerController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
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
import services.MiscellaneousAttachmentService;
import services.CleanerService;
import domain.Curricula;
import domain.EducationalData;
import domain.MiscellaneousAttachment;
import domain.Cleaner;

/*
 * CONTROL DE CAMBIOS CurriculaCleanerController.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/curricula")
public class CurriculaController extends AbstractController {

	@Autowired
	private CleanerService					cleanerService;

	@Autowired
	private CurriculaService				curriculaService;

	@Autowired
	private EducationalDataService			educationalDataService;

	@Autowired
	private MiscellaneousAttachmentService	miscellaneousAttachmentService;


	// Constructors -----------------------------------------------------------

	public CurriculaController() {
		super();
	}
	
	private List<Integer> listNumber(int size) {
		List<Integer> res = new ArrayList<>();
		int aux = 0;
		while (aux < size) {
			res.add(aux);
			aux++;
		}
		return res;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "cleanerId", defaultValue = "-1") final int cleanerId) {
		ModelAndView result;
		try {
			Cleaner cleaner = this.cleanerService.findOne(cleanerId);
			final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
			if (cleaner == null && cleanerLogin != null)
				cleaner = cleanerLogin;
			Assert.notNull(cleaner, "Not cleaner found in DB");
			result = new ModelAndView("curricula/list");
			if (cleanerLogin != null && cleaner.equals(cleanerLogin))
				result.addObject("cleanerLogger", true);
			Assert.notNull(cleaner, "Cleaner is null");
			final Collection<Curricula> curriculas = this.curriculaService.findAllNotCopyByCleaner(cleaner);
			result.addObject("curriculas", curriculas);
			result.addObject("numbers", this.listNumber(curriculas.size()));
			result.addObject("requestURI", "curriculas/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	private ModelAndView checkMaximumMiscellaneous(ModelAndView result, int curriculaId) {
		ModelAndView res = result;
		try {
			Integer aux = this.miscellaneousAttachmentService.getMiscellaneousAttachmentFromCurricula(this.curriculaService.findOne(curriculaId)).size();
			if (aux <4) {
				res.addObject("moreAttachment", true);
			}
		} catch (Exception e) {
			res.addObject("moreAttachment", false);
		}
		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "curriculaId", defaultValue = "-1") final int curriculaId) {

		ModelAndView result;
		try {
			final Curricula curriculaDB = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curriculaDB, "Not found curricula in DB");
			result = new ModelAndView("curricula/show");

			final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
			if (cleanerLogin != null && curriculaDB.getCleaner().equals(cleanerLogin) && curriculaDB.getIsCopy().equals(false)) {
				result.addObject("cleanerLogin", true);
				final MiscellaneousAttachment miscellaneousAttachment = this.miscellaneousAttachmentService.createWithHistory(curriculaDB);
				result.addObject("miscellaneousAttachment", miscellaneousAttachment);
				this.checkMaximumMiscellaneous(result, curriculaId);
			}
			

			result.addObject("curricula", curriculaDB);

			final List<EducationalData> educationalDatas = (List<EducationalData>) this.educationalDataService.getEducationalDataFromCurricula(curriculaDB);
			result.addObject("educationalDatas", educationalDatas);

			final List<MiscellaneousAttachment> miscellaneousAttachments = (List<MiscellaneousAttachment>) this.miscellaneousAttachmentService.getMiscellaneousAttachmentFromCurricula(curriculaDB);
			result.addObject("miscellaneousAttachments", miscellaneousAttachments);

			result.addObject("requestURI", "curricula/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
}
