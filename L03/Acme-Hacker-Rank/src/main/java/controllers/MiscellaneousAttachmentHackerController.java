/*
 * PositionHackerController.java
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
import domain.Curricula;
import domain.Hacker;
import domain.MiscellaneousAttachment;
import services.CurriculaService;
import services.HackerService;
import services.MiscellaneousAttachmentService;
import services.PositionService;

/*
 * CONTROL DE CAMBIOS MiscellaneousAttachmentHackerController.java
 * 
 * ALVARO 09/03/2019 23:47 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/miscellaneousAttachment/hacker")
public class MiscellaneousAttachmentHackerController extends AbstractController {
	
	@Autowired
	private HackerService hackerService;

	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private MiscellaneousAttachmentService miscellaneousAttachmentService;
	
	@Autowired
	private PositionService positionService;


	// Constructors -----------------------------------------------------------

	public MiscellaneousAttachmentHackerController() {
		super();
	}
	
//	@RequestMapping(value = "/create", method = RequestMethod.GET)
//	public ModelAndView create(@RequestParam(value = "curriculaId", defaultValue = "-1") final int curriculaId) {
//		ModelAndView result;
//		try {
//			Curricula curriculaDB = this.curriculaService.findOne(curriculaId);
//			Assert.notNull(curriculaDB, "Curricula not found in DB");
//			Hacker hackerLogin = this.hackerService.getHackerLogin();
//			Assert.notNull(hackerLogin, "No hacker is login");
//			Assert.isTrue(hackerLogin.equals(curriculaDB.getHacker()));
//			MiscellaneousAttachment miscellaneousAttachment = this.miscellaneousAttachmentService.createWithHistory(curriculaDB);
//			result = new ModelAndView("miscellaneousAttachment/hacker/create");
//			result.addObject("miscellaneousAttachment", miscellaneousAttachment);	
//			result.addObject("curricula", curriculaDB);
//		} catch (Exception e) {
//			System.out.println("Error e en GET /create MiscellaneousAttachmentController.java: " + e);
//			result = new ModelAndView("redirect:/welcome/index.do");
//		}
//		return result;
//	}
	

//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
//	public ModelAndView edit(@RequestParam(value = "miscellaneousAttachmentId", defaultValue = "-1") final int miscellaneousAttachmentId) {
//		ModelAndView result;
//		try {
//			MiscellaneousAttachment miscellaneousAttachmentDB = this.miscellaneousAttachmentService.findOne(miscellaneousAttachmentId);
//			Assert.notNull(miscellaneousAttachmentDB, "MiscellaneousAttachment not found in DB");
//			Hacker hackerLogin = this.hackerService.getHackerLogin();
//			Assert.notNull(hackerLogin, "No hacker is login");
//			Assert.isTrue(hackerLogin.equals(miscellaneousAttachmentDB.getCurricula().getHacker()), "Not allow to edit not own MiscellaneousAttachment");
//			result = new ModelAndView("miscellaneousAttachment/hacker/edit");
//			result.addObject("miscellaneousAttachment", miscellaneousAttachmentDB);
//			result.addObject("curricula", miscellaneousAttachmentDB.getCurricula());
//			// TODO En este método solo serán añadidos las positiones validas
//			result.addObject("positions", this.positionService.findALL());
//		} catch (final Exception e) {
//			result = new ModelAndView("redirect:/welcome/index.do");
//		}
//		return result;
//	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(MiscellaneousAttachment miscellaneousAttachment, final BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			System.out.println("Error en MiscellaneousAttachmentHackerController.java, binding: " + binding);
			result = new ModelAndView("redirect:/curricula/show.do?curriculaId="+miscellaneousAttachment.getCurriculaM().getId());	
			result.addObject("miscellaneousAttachment", miscellaneousAttachment);
			result.addObject("curricula", miscellaneousAttachment.getCurriculaM());
		} else
			try {
				Hacker hackerLogin = this.hackerService.getHackerLogin();
				Assert.notNull(hackerLogin, "No hacker is login");
				Assert.isTrue(miscellaneousAttachment != null, "miscellaneousAttachment.null");
				Assert.isTrue(this.hackerService.getHackerByCurriculaId(miscellaneousAttachment.getCurriculaM()).equals(hackerLogin), "Not allow to edit a not own MiscellaneousAttachment");
				MiscellaneousAttachment miscellaneousAttachmentSave = this.miscellaneousAttachmentService.save(miscellaneousAttachment);
				result = new ModelAndView("redirect:/curricula/show.do?curriculaId="+miscellaneousAttachmentSave.getCurriculaM().getId());
				result.addObject("requestURI", "curricula/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE CurriculaHackerController.java Throwable: " + oops);
				result = new ModelAndView("miscellaneousAttachment/hacker/edit");
				result.addObject("miscellaneousAttachment", miscellaneousAttachment);
				try {
					result.addObject("curricula", miscellaneousAttachment.getCurriculaM());
					// TODO En este método solo serán añadidos las positiones validas
					result.addObject("positions", this.positionService.findALL());
				} catch (Exception e) {
					return new ModelAndView("redirect:/welcome/index.do");
				}
				result.addObject("message", "miscellaneousAttachment.commit.error");
			}
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "miscellaneousAttachmentId", defaultValue = "-1") final int miscellaneousAttachmentId) {
		ModelAndView result;
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		MiscellaneousAttachment miscellaneousAttachmentDB = this.miscellaneousAttachmentService.findOne(miscellaneousAttachmentId);
		
		try {
			Assert.notNull(hackerLogin, "No hacker is login");
			Assert.notNull(miscellaneousAttachmentDB, "Not found miscellaneousAttachment in DB");
			Assert.isTrue(miscellaneousAttachmentDB.getCurriculaM().getHacker().equals(hackerLogin), "Not allow to delete, diferent hacker");
			result = new ModelAndView("redirect:/curricula/show.do?curriculaId="+miscellaneousAttachmentDB.getCurriculaM().getId());
			this.miscellaneousAttachmentService.delete(miscellaneousAttachmentDB);
		} catch (final Throwable oops) {
			System.out.println("Error en MiscellaneousAttachmentHackerController.java Throwable: " + oops);
			if (miscellaneousAttachmentDB!=null) {
				result = new ModelAndView("redirect:/curricula/show.do?curriculaId="+miscellaneousAttachmentDB.getCurriculaM().getId());				
			} else {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
			result.addObject("message", "curricula.commit.error");
		}
		return result;
	}
}
