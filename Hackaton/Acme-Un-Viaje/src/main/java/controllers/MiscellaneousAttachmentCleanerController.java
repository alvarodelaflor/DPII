/*
 * PositionCleanerController.java
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.MiscellaneousAttachmentService;
import services.CleanerService;
import domain.MiscellaneousAttachment;
import domain.Cleaner;

/*
 * CONTROL DE CAMBIOS MiscellaneousAttachmentCleanerController.java
 * 
 * ALVARO 09/03/2019 23:47 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/miscellaneousAttachment/cleaner")
public class MiscellaneousAttachmentCleanerController extends AbstractController {

	@Autowired
	private CleanerService					cleanerService;

	@Autowired
	private MiscellaneousAttachmentService	miscellaneousAttachmentService;

	// Constructors -----------------------------------------------------------

	public MiscellaneousAttachmentCleanerController() {
		super();
	}

	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView create(@RequestParam(value = "curriculaId", defaultValue = "-1") final int curriculaId) {
	//		ModelAndView result;
	//		try {
	//			Curricula curriculaDB = this.curriculaService.findOne(curriculaId);
	//			Assert.notNull(curriculaDB, "Curricula not found in DB");
	//			Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
	//			Assert.notNull(cleanerLogin, "No cleaner is login");
	//			Assert.isTrue(cleanerLogin.equals(curriculaDB.getCleaner()));
	//			MiscellaneousAttachment miscellaneousAttachment = this.miscellaneousAttachmentService.createWithHistory(curriculaDB);
	//			result = new ModelAndView("miscellaneousAttachment/cleaner/create");
	//			result.addObject("miscellaneousAttachment", miscellaneousAttachment);	
	//			result.addObject("curricula", curriculaDB);
	//		} catch (Exception e) {
	//			System.out.println("Error e en GET /create MiscellaneousAttachmentController.java: " + e);
	//			result = new ModelAndView("redirect:/welcome/index.do");
	//		}
	//		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	//	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam(value = "miscellaneousAttachmentId", defaultValue = "-1") final int miscellaneousAttachmentId) {
	//		ModelAndView result;
	//		try {
	//			MiscellaneousAttachment miscellaneousAttachmentDB = this.miscellaneousAttachmentService.findOne(miscellaneousAttachmentId);
	//			Assert.notNull(miscellaneousAttachmentDB, "MiscellaneousAttachment not found in DB");
	//			Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
	//			Assert.notNull(cleanerLogin, "No cleaner is login");
	//			Assert.isTrue(cleanerLogin.equals(miscellaneousAttachmentDB.getCurricula().getCleaner()), "Not allow to edit not own MiscellaneousAttachment");
	//			result = new ModelAndView("miscellaneousAttachment/cleaner/edit");
	//			result.addObject("miscellaneousAttachment", miscellaneousAttachmentDB);
	//			result.addObject("curricula", miscellaneousAttachmentDB.getCurricula());
	//			// TODO En este método solo serán añadidos las positiones validas
	//			result.addObject("positions", this.positionService.findALL());
	//		} catch (final Exception e) {
	//			result = new ModelAndView("redirect:/welcome/index.do");
	//		}
	//		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	//	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final MiscellaneousAttachment miscellaneousAttachment, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("Error en MiscellaneousAttachmentCleanerController.java, binding: " + binding);
			result = new ModelAndView("redirect:/curricula/show.do?curriculaId=" + miscellaneousAttachment.getCurriculaM().getId());
			result.addObject("miscellaneousAttachment", miscellaneousAttachment);
			result.addObject("curricula", miscellaneousAttachment.getCurriculaM());
		} else
			try {
				if (miscellaneousAttachment.getAttachment() != null && miscellaneousAttachment.getAttachment().length() > 0) {
					final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
					Assert.notNull(cleanerLogin, "No cleaner is login");
					Assert.isTrue(miscellaneousAttachment != null, "miscellaneousAttachment.null");
					Assert.isTrue(miscellaneousAttachment.getCurriculaM().getCleaner().equals(cleanerLogin), "Not allow to edit a not own MiscellaneousAttachment");
					final MiscellaneousAttachment miscellaneousAttachmentSave = this.miscellaneousAttachmentService.save(miscellaneousAttachment);
					result = new ModelAndView("redirect:/curricula/show.do?curriculaId=" + miscellaneousAttachmentSave.getCurriculaM().getId());
					result.addObject("requestURI", "curricula/list.do");
				} else {
					result = new ModelAndView("redirect:/curricula/show.do?curriculaId=" + miscellaneousAttachment.getCurriculaM().getId());
					result.addObject("requestURI", "curricula/list.do");
				}
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE CurriculaCleanerController.java Throwable: " + oops);
				result = new ModelAndView("miscellaneousAttachment/cleaner/edit");
				result.addObject("miscellaneousAttachment", miscellaneousAttachment);
				try {
					result.addObject("curricula", miscellaneousAttachment.getCurriculaM());
				} catch (final Exception e) {
					return new ModelAndView("redirect:/welcome/index.do");
				}
				result.addObject("message", "miscellaneousAttachment.commit.error");
			}
//		result.addObject("logo", this.getLogo());
//		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "miscellaneousAttachmentId", defaultValue = "-1") final int miscellaneousAttachmentId) {
		ModelAndView result;
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		final MiscellaneousAttachment miscellaneousAttachmentDB = this.miscellaneousAttachmentService.findOne(miscellaneousAttachmentId);

		try {
			Assert.notNull(cleanerLogin, "No cleaner is login");
			Assert.notNull(miscellaneousAttachmentDB, "Not found miscellaneousAttachment in DB");
			Assert.isTrue(miscellaneousAttachmentDB.getCurriculaM().getCleaner().equals(cleanerLogin), "Not allow to delete, diferent cleaner");
			result = new ModelAndView("redirect:/curricula/show.do?curriculaId=" + miscellaneousAttachmentDB.getCurriculaM().getId());
			this.miscellaneousAttachmentService.delete(miscellaneousAttachmentDB);
		} catch (final Throwable oops) {
			System.out.println("Error en MiscellaneousAttachmentCleanerController.java Throwable: " + oops);
			if (miscellaneousAttachmentDB != null)
				result = new ModelAndView("redirect:/curricula/show.do?curriculaId=" + miscellaneousAttachmentDB.getCurriculaM().getId());
			else
				result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("message", "curricula.commit.error");
		}
//		result.addObject("logo", this.getLogo());
//		result.addObject("system", this.getSystem());
		return result;
	}
}
