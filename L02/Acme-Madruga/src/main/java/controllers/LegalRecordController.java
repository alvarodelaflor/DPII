/*
 * LegalRecordController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import domain.Brotherhood;
import domain.LegalRecord;
import security.LoginService;
import services.BrotherhoodService;
import services.LegalRecordService;
import services.WelcomeService;

@Controller
@RequestMapping("/history/legalRecord")
public class LegalRecordController extends AbstractController {

	@Autowired
	private LegalRecordService legalRecordService;

	@Autowired
	private BrotherhoodService brotherhoodService; 
	
	@Autowired
	private WelcomeService			welcomeService;
	


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public LegalRecordController() {
		super();
	}


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "legalRecordId", defaultValue = "-1") final int legalRecordId) {
		ModelAndView result;
		try {
			LegalRecord legalRecord = this.legalRecordService.findOne(legalRecordId);
			Assert.notNull(legalRecord, "legalRecord.null");
			result = new ModelAndView("history/legalRecord/show");
			result.addObject("legalRecord", legalRecord);
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

		LegalRecord legalRecord = this.legalRecordService.create();

		result = new ModelAndView("history/legalRecord/create");
		result.addObject("legalRecord", legalRecord);
		result.addObject("brotherhoodId", this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId());
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
	
	private Boolean checkBrotherhoodToEdit(int legalRecordId) {
		Boolean res  = false;
		LegalRecord legalRecordFromDB = this.legalRecordService.findOne(legalRecordId);
		if (legalRecordFromDB!=null) {
			try {
				Brotherhood brotherhoodLogger = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
				System.out.println("BrotherhoodLogger: " + brotherhoodLogger);
				List<LegalRecord> legalRecords = (List<LegalRecord>)brotherhoodLogger.getHistory().getLegalRecord();
				res = legalRecords.contains(legalRecordFromDB);
			} catch (Exception e) {
				System.out.println("Error e en checkBrotherhoodToEdit LegalRecordController.java: " + e);
				res = false;
			}
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "legalRecordId", defaultValue = "-1") final int legalRecordId) {
		ModelAndView result;
		try {
			LegalRecord legalRecord = this.legalRecordService.findOne(legalRecordId);
			Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
			Assert.isTrue(brotherhood.getHistory().getInceptionRecord()!=null, "brotherhood.null.inceptionRecord");
			Assert.notNull(legalRecord, "legalRecord.null");
			Assert.isTrue(checkBrotherhoodToEdit(legalRecordId));
			result = new ModelAndView("history/legalRecord/edit");
			result.addObject("legalRecord", legalRecord);
			result.addObject("brotherhoodId", brotherhood.getId());
		} catch (Exception e) {
			System.out.println("Error e en GET /edit LegalRecordController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LegalRecord legalRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("Error en LegalRecordController.java, binding: " + binding);
			result = new ModelAndView("history/legalRecord/edit");
			result.addObject("brotherhoodId", this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId());
		} else
			try {
				Assert.isTrue(legalRecord != null, "legalRecord.null");
				Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
				this.legalRecordService.save(legalRecord);
				result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
				result.addObject("requestURI", "history/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE LegalRecordController.java Throwable: " + oops);
				result = new ModelAndView("history/legalRecord/edit");
				result.addObject("legalRecord", legalRecord);
				result.addObject("message", "legalRecord.commit.error");
			}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "legalRecordId", defaultValue = "-1") final int legalRecordId) {
		ModelAndView result;
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		
		try {
			LegalRecord legalRecord = this.legalRecordService.findOne(legalRecordId);
			this.legalRecordService.delete(legalRecord);
			result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
		} catch (final Throwable oops) {
			System.out.println("Error en LegalRecordController.java Throwable: " + oops);
			result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
			result.addObject("message", "legalRecord.commit.error");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

}
