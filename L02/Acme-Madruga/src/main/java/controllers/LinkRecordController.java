/*
 * LinkRecordController.java
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
import domain.LinkRecord;
import security.LoginService;
import services.BrotherhoodService;
import services.LinkRecordService;
import services.WelcomeService;

@Controller
@RequestMapping("/history/linkRecord")
public class LinkRecordController extends AbstractController {

	@Autowired
	private LinkRecordService linkRecordService;

	@Autowired
	private BrotherhoodService brotherhoodService; 
	
	@Autowired
	private WelcomeService			welcomeService;
	


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public LinkRecordController() {
		super();
	}


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "linkRecordId", defaultValue = "-1") final int linkRecordId) {
		ModelAndView result;
		try {
			LinkRecord linkRecord = this.linkRecordService.findOne(linkRecordId);
			Assert.notNull(linkRecord, "linkRecord.null");
			result = new ModelAndView("history/linkRecord/show");
			result.addObject("linkRecord", linkRecord);
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

		LinkRecord linkRecord = this.linkRecordService.create();

		result = new ModelAndView("history/linkRecord/create");
		result.addObject("linkRecord", linkRecord);
		result.addObject("brotherhoodId", this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId());
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
	
	private Boolean checkBrotherhoodToEdit(int linkRecordId) {
		Boolean res  = false;
		LinkRecord linkRecordFromDB = this.linkRecordService.findOne(linkRecordId);
		if (linkRecordFromDB!=null) {
			try {
				Brotherhood brotherhoodLogger = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
				System.out.println("BrotherhoodLogger: " + brotherhoodLogger);
				List<LinkRecord> linkRecords = (List<LinkRecord>)brotherhoodLogger.getHistory().getLinkRecord();
				res = linkRecords.contains(linkRecordFromDB);
			} catch (Exception e) {
				System.out.println("Error e en checkBrotherhoodToEdit LinkRecordController.java: " + e);
				res = false;
			}
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "linkRecordId", defaultValue = "-1") final int linkRecordId) {
		ModelAndView result;
		try {
			LinkRecord linkRecord = this.linkRecordService.findOne(linkRecordId);
			Assert.isTrue(checkBrotherhoodToEdit(linkRecordId));
			result = new ModelAndView("history/linkRecord/edit");
			result.addObject("linkRecord", linkRecord);
			result.addObject("brotherhoodId", this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId());
		} catch (Exception e) {
			System.out.println("Error e en GET /edit LinkRecordController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LinkRecord linkRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("Error en LinkRecordController.java, binding: " + binding);
			result = new ModelAndView("history/linkRecord/edit");
			result.addObject("brotherhoodId", this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()).getId());
		} else
			try {
				Assert.isTrue(linkRecord != null, "linkRecord.null");
				Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
				this.linkRecordService.save(linkRecord);
				result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
				result.addObject("requestURI", "history/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE LinkRecordController.java Throwable: " + oops);
				result = new ModelAndView("history/linkRecord/edit");
				result.addObject("linkRecord", linkRecord);
				result.addObject("message", "linkRecord.commit.error");
			}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "linkRecordId", defaultValue = "-1") final int linkRecordId) {
		ModelAndView result;
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		
		try {
			LinkRecord linkRecord = this.linkRecordService.findOne(linkRecordId);
			this.linkRecordService.delete(linkRecord);
			result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
		} catch (final Throwable oops) {
			System.out.println("Error en LinkRecordController.java Throwable: " + oops);
			result = new ModelAndView("redirect:/history/show.do?brotherhoodId="+brotherhood.getId());
			result.addObject("message", "linkRecord.commit.error");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

}
