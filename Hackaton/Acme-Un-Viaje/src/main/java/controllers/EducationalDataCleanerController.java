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

import javax.validation.Valid;

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
import services.EducationalDataService;
import services.CleanerService;
import domain.Curricula;
import domain.EducationalData;
import domain.Cleaner;

/*
 * CONTROL DE CAMBIOS EducationalDataCleanerController.java
 * 
 * ALVARO 09/03/2019 23:47 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/educationalData/cleaner")
public class EducationalDataCleanerController extends AbstractController {

	@Autowired
	private CleanerService			cleanerService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private EducationalDataService	educationalDataService;


	// Constructors -----------------------------------------------------------

	public EducationalDataCleanerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "curriculaId", defaultValue = "-1") final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula curriculaDB = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curriculaDB, "Curricula not found in DB");
			final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
			Assert.notNull(cleanerLogin, "No cleaner is login");
			Assert.isTrue(cleanerLogin.equals(curriculaDB.getCleaner()));
			final EducationalData educationalData = this.educationalDataService.createWithHistory(curriculaDB);
			result = new ModelAndView("educationalData/cleaner/create");
			result.addObject("educationalData", educationalData);
			result.addObject("curricula", curriculaDB);
		} catch (final Exception e) {
			System.out.println("Error e en GET /create EducationalDataController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
//		result.addObject("logo", this.getLogo());
//		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "educationalDataId", defaultValue = "-1") final int educationalDataId) {
		ModelAndView result;
		try {
			final EducationalData educationalDataDB = this.educationalDataService.findOne(educationalDataId);
			Assert.notNull(educationalDataDB, "EducationalData not found in DB");
			Assert.isTrue(!educationalDataDB.getIsCopy(), "Trying to edit a EducatinalData in copy mode");
			final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
			Assert.notNull(cleanerLogin, "No cleaner is login");
			Assert.isTrue(cleanerLogin.equals(educationalDataDB.getCurricula().getCleaner()), "Not allow to edit not own EducationalData");
			result = new ModelAndView("educationalData/cleaner/edit");
			result.addObject("educationalData", educationalDataDB);
			result.addObject("curricula", educationalDataDB.getCurricula());
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
//		result.addObject("logo", this.getLogo());
//		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final EducationalData educationalData, final BindingResult binding) {
		ModelAndView result;

		if (educationalData.getStartDate() != null && educationalData.getEndDate() != null && this.educationalDataService.checkDate(educationalData.getStartDate(), educationalData.getEndDate())) {
			final ObjectError error = new ObjectError("startDate", "Start date mus be before than end Date");
			binding.addError(error);
			binding.rejectValue("startDate", "error.startDate");
		}

		if (educationalData.getStartDate() != null && educationalData.getEndDate() != null && this.educationalDataService.checkDate(educationalData.getStartDate(), educationalData.getEndDate())) {
			final ObjectError error = new ObjectError("endDate", "Start date mus be before than end Date");
			binding.addError(error);
			binding.rejectValue("endDate", "error.endDate");
		}

		if (binding.hasErrors()) {
			System.out.println("Error en EducationalDataCleanerController.java, binding: " + binding);
			result = new ModelAndView("educationalData/cleaner/edit");
			result.addObject("educationalData", educationalData);
			result.addObject("curricula", educationalData.getCurricula());
		} else
			try {
				final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
				Assert.notNull(cleanerLogin, "No cleaner is login");
				Assert.isTrue(educationalData != null, "educationalData.null");
				Assert.isTrue(!educationalData.getIsCopy(), "Trying to save a EducatinalData in copy mode");
				Assert.isTrue(educationalData.getCurricula().getCleaner().equals(cleanerLogin), "Not allow to edit a not own EducationalData");
				final EducationalData educationalDataSave = this.educationalDataService.save(educationalData);
				result = new ModelAndView("redirect:/educationalData/show.do?educationalDataId=" + educationalDataSave.getId());
				result.addObject("requestURI", "curricula/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE CurriculaCleanerController.java Throwable: " + oops);
				result = new ModelAndView("educationalData/cleaner/edit");
				result.addObject("educationalData", educationalData);
				try {
					result.addObject("curricula", educationalData.getCurricula());
				} catch (final Exception e) {
					return new ModelAndView("redirect:/welcome/index.do");
				}
				result.addObject("message", "educationalData.commit.error");
			}
//		result.addObject("logo", this.getLogo());
//		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "educationalDataId", defaultValue = "-1") final int educationalDataId) {
		ModelAndView result;
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		final EducationalData educationalDataDB = this.educationalDataService.findOne(educationalDataId);

		try {
			Assert.notNull(cleanerLogin, "No cleaner is login");
			Assert.notNull(educationalDataDB, "Not found educationalData in DB");
			Assert.isTrue(educationalDataDB.getCurricula().getCleaner().equals(cleanerLogin), "Not allow to delete, diferent cleaner");
			result = new ModelAndView("redirect:/curricula/show.do?curriculaId=" + educationalDataDB.getCurricula().getId());
			this.educationalDataService.delete(educationalDataDB);
		} catch (final Throwable oops) {
			System.out.println("Error en EducationalDataCleanerController.java Throwable: " + oops);
			if (educationalDataDB != null)
				result = new ModelAndView("redirect:/curricula/show.do?curriculaId=" + educationalDataDB.getCurricula().getId());
			else
				result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("message", "curricula.commit.error");
		}
//		result.addObject("logo", this.getLogo());
//		result.addObject("system", this.getSystem());
		return result;
	}
}
