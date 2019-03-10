/*
 * PeriodRecordController.java
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
import domain.PeriodRecord;
import security.LoginService;
import services.BrotherhoodService;
import services.PeriodRecordService;
import services.WelcomeService;

@Controller
@RequestMapping("/history/periodRecord")
public class PeriodRecordController extends AbstractController {

	@Autowired
	private PeriodRecordService periodRecordService;

	@Autowired
	private BrotherhoodService brotherhoodService; 
	
	@Autowired
	private WelcomeService			welcomeService;
	


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public PeriodRecordController() {
		super();
	}


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "periodRecordId", defaultValue = "-1") final int periodRecordId) {
		ModelAndView result;
		try {
			PeriodRecord periodRecord = this.periodRecordService.findOne(periodRecordId);
			Assert.notNull(periodRecord, "periodRecord.null");
			result = new ModelAndView("history/periodRecord/show");
			result.addObject("periodRecord", periodRecord);
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

		PeriodRecord periodRecord = this.periodRecordService.create();

		result = new ModelAndView("history/periodRecord/create");
		result.addObject("periodRecord", periodRecord);
		result.addObject("brotherhoodId", this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId());
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
	
	private Boolean checkBrotherhoodToEdit(int periodRecordId) {
		Boolean res  = false;
		PeriodRecord periodRecordFromDB = this.periodRecordService.findOne(periodRecordId);
		if (periodRecordFromDB!=null) {
			try {
				Brotherhood brotherhoodLogger = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
				System.out.println("BrotherhoodLogger: " + brotherhoodLogger);
				List<PeriodRecord> periodRecords = (List<PeriodRecord>)brotherhoodLogger.getHistory().getPeriodRecord();
				res = periodRecords.contains(periodRecordFromDB);
			} catch (Exception e) {
				System.out.println("Error e en checkBrotherhoodToEdit PeriodRecordController.java: " + e);
				res = false;
			}
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "periodRecordId", defaultValue = "-1") final int periodRecordId) {
		ModelAndView result;
		try {
			Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
			Assert.isTrue(brotherhood.getHistory().getInceptionRecord()!=null, "brotherhood.null.inceptionRecord");
			PeriodRecord periodRecord = this.periodRecordService.findOne(periodRecordId);
			Assert.notNull(periodRecord, "periodRecord.null");
			Assert.isTrue(checkBrotherhoodToEdit(periodRecordId));
			result = new ModelAndView("history/periodRecord/edit");
			result.addObject("periodRecord", periodRecord);
			result.addObject("brotherhoodId", this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId());
		} catch (Exception e) {
			System.out.println("Error e en GET /edit PeriodRecordController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PeriodRecord periodRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("Error en PeriodRecordController.java, binding: " + binding);
			result = new ModelAndView("history/periodRecord/edit");
			result.addObject("brotherhoodId", this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId());
		} else
			try {
				Assert.isTrue(periodRecord != null, "periodRecord.null");
				Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
				this.periodRecordService.save(periodRecord);
				result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
				result.addObject("requestURI", "history/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE PeriodRecordController.java Throwable: " + oops);
				result = new ModelAndView("history/periodRecord/edit");
				result.addObject("periodRecord", periodRecord);
				result.addObject("message", "periodRecord.commit.error");
			}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "periodRecordId", defaultValue = "-1") final int periodRecordId) {
		ModelAndView result;
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		
		try {
			PeriodRecord periodRecord = this.periodRecordService.findOne(periodRecordId);
			Assert.notNull(periodRecord, "periodRecord.null");
			this.periodRecordService.delete(periodRecord);
			result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
		} catch (final Throwable oops) {
			System.out.println("Error en PeriodRecordController.java Throwable: " + oops);
			result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
			result.addObject("message", "periodRecord.commit.error");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

}
