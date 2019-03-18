/*
 * MiscellaneousRecordController.java
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
import domain.MiscellaneousRecord;
import security.LoginService;
import services.BrotherhoodService;
import services.MiscellaneousRecordService;
import services.WelcomeService;

@Controller
@RequestMapping("/history/miscellaneousRecord")
public class MiscellaneousRecordController extends AbstractController {

	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;

	@Autowired
	private BrotherhoodService brotherhoodService; 
	
	@Autowired
	private WelcomeService			welcomeService;
	


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public MiscellaneousRecordController() {
		super();
	}


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "miscellaneousRecordId", defaultValue = "-1") final int miscellaneousRecordId) {
		ModelAndView result;
		try {
			MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			Assert.notNull(miscellaneousRecord, "miscellaneousRecord.null");
			result = new ModelAndView("history/miscellaneousRecord/show");
			result.addObject("miscellaneousRecord", miscellaneousRecord);
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
		try {
			MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
			Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
			Assert.notNull(brotherhood.getHistory(), "brotherhood.history.null");
			Assert.notNull(brotherhood.getHistory().getInceptionRecord(), "brotherthood.inceptionRecord.null");
			result = new ModelAndView("history/miscellaneousRecord/create");
			result.addObject("miscellaneousRecord", miscellaneousRecord);
			result.addObject("brotherhoodId", this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId());
			result.addObject("logo", welcomeService.getLogo());
			result.addObject("system", welcomeService.getSystem());			
		} catch (Exception e) {
			System.out.println("Error e en GET /create MiscellaneousRecordController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}


		return result;
	}
	
	private Boolean checkBrotherhoodToEdit(int miscellaneousRecordId) {
		Boolean res  = false;
		MiscellaneousRecord miscellaneousRecordFromDB = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
		if (miscellaneousRecordFromDB!=null) {
			try {
				Brotherhood brotherhoodLogger = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
				System.out.println("BrotherhoodLogger: " + brotherhoodLogger);
				List<MiscellaneousRecord> miscellaneousRecords = (List<MiscellaneousRecord>)brotherhoodLogger.getHistory().getMiscellaneousRecord();
				res = miscellaneousRecords.contains(miscellaneousRecordFromDB);
			} catch (Exception e) {
				System.out.println("Error e en checkBrotherhoodToEdit MiscellaneousRecordController.java: " + e);
				res = false;
			}
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "miscellaneousRecordId", defaultValue = "-1") final int miscellaneousRecordId) {
		ModelAndView result;
		try {
			MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
			Assert.isTrue(brotherhood.getHistory().getInceptionRecord()!=null, "brotherhood.null.inceptionRecord");
			Assert.isTrue(checkBrotherhoodToEdit(miscellaneousRecordId));
			result = new ModelAndView("history/miscellaneousRecord/edit");
			result.addObject("miscellaneousRecord", miscellaneousRecord);
			result.addObject("brotherhoodId", brotherhood.getId());
		} catch (Exception e) {
			System.out.println("Error e en GET /edit MiscellaneousRecordController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("Error en MiscellaneousRecordController.java, binding: " + binding);
			result = new ModelAndView("history/miscellaneousRecord/edit");
			result.addObject("brotherhoodId", this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId());
		} else
			try {
				Assert.isTrue(miscellaneousRecord != null, "miscellaneousRecord.null");
				Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
				this.miscellaneousRecordService.save(miscellaneousRecord);
				result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
				result.addObject("requestURI", "history/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE MiscellaneousRecordController.java Throwable: " + oops);
				result = new ModelAndView("history/miscellaneousRecord/edit");
				result.addObject("miscellaneousRecord", miscellaneousRecord);
				result.addObject("message", "miscellaneousRecord.commit.error");
			}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "miscellaneousRecordId", defaultValue = "-1") final int miscellaneousRecordId) {
		ModelAndView result;
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		
		try {
			MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
		} catch (final Throwable oops) {
			System.out.println("Error en MiscellaneousRecordController.java Throwable: " + oops);
			result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
			result.addObject("message", "miscellaneousRecord.commit.error");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

}
