/*
 * handyWorkerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
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

import domain.Actor;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;
import domain.SocialProfile;
import security.LoginService;
import services.BrotherhoodService;
import services.HistoryService;
import services.InceptionRecordService;
import services.WelcomeService;

@Controller
@RequestMapping("/history/inceptionRecord")
public class InceptionRecordController extends AbstractController {

	@Autowired
	private HistoryService	historyService;
	
	@Autowired
	private InceptionRecordService inceptionRecordService;

	@Autowired
	private BrotherhoodService brotherhoodService; 
	
	@Autowired
	private WelcomeService			welcomeService;
	


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public InceptionRecordController() {
		super();
	}


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam("inceptionRecordId") final int inceptionRecordId) {
		ModelAndView result;
		try {
			InceptionRecord inceptionRecord = this.inceptionRecordService.findOne(inceptionRecordId);
			result = new ModelAndView("history/inceptionRecord/show");
			result.addObject("inceptionRecord", inceptionRecord);
		} catch (Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");			
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		InceptionRecord inceptionRecord = this.inceptionRecordService.create();

		result = new ModelAndView("history/inceptionRecord/create");
		result.addObject("inceptionRecord", inceptionRecord);
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
	
	private Boolean checkBrotherhoodToEdit(int inceptionRecordId) {
		Boolean res  = false;
		History history = this.historyService.findHistoryByInceptionRecordId(inceptionRecordId);
		if (history!=null) {
			Brotherhood brotherhood = this.brotherhoodService.findBrotherhoodByHistory(history.getId());
			try {
				Brotherhood brotherhoodLogger = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
				System.out.println("BrotherhoodLogger: " + brotherhoodLogger);
				System.out.println("BrotherhoodHistory: " + brotherhood);
				res = brotherhoodLogger.getId()==brotherhood.getId();
			} catch (Exception e) {
				System.out.println("Error e: " + e);
				res = false;
			}
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "inceptionRecordId", defaultValue = "-1") final int inceptionRecordId) {
		ModelAndView result;
		try {
			InceptionRecord inceptionRecord = this.inceptionRecordService.findOne(inceptionRecordId);
			Assert.isTrue(checkBrotherhoodToEdit(inceptionRecordId));
			result = new ModelAndView("history/inceptionRecord/edit");
			result.addObject("inceptionRecord", inceptionRecord);
			result.addObject("brotherhoodId", this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId());
		} catch (Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final InceptionRecord inceptionRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("Error en InceptionRecordController.java, binding: " + binding);
			result = new ModelAndView("history/inceptionRecord/edit");
		} else
			try {
				Assert.isTrue(inceptionRecord != null, "inceptionRecord.null");
				Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
				final InceptionRecord savedInceptionRecord = this.inceptionRecordService.save(inceptionRecord);
				result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
				result.addObject("requestURI", "history/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error en InceptionRecordController.java Throwable: " + oops);
				result = new ModelAndView("redirect:history/inceptionRecord/edit");
				result.addObject("inceptionRecord", inceptionRecord);
				result.addObject("message", "inceptionRecord.commit.error");
			}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

}
