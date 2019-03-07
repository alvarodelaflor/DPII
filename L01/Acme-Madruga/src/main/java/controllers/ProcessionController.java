/*
 * BrotherhoodController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.MemberService;
import services.PositionAuxService;
import services.ProcessionService;
import services.RequestService;
import domain.Brotherhood;
import domain.Procession;

/*
 * CONTROL DE CAMBIOS ProcessionBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 12:29 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 16:35 AÑADIDO RECONSTRUIDOR PROCESSION
 */

@Controller
@RequestMapping("/procession")
public class ProcessionController extends AbstractController {

	@Autowired
	BrotherhoodService	brotherhoodService;

	@Autowired
	PositionAuxService	positionAuxService;

	@Autowired
	ProcessionService	processionService;

	@Autowired
	MemberService		memberService;

	@Autowired
	RequestService		requestService;


	// Constructors -----------------------------------------------------------

	public ProcessionController() {
		super();
	}

	@RequestMapping(value = "/listProcessions", method = RequestMethod.GET)
	public ModelAndView listProcessions(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;
		try {
			final Brotherhood brotherhood = this.brotherhoodService.findOne(id);
			System.out.println(brotherhood.getId());
			final Collection<Procession> procession = this.processionService.findProcessionsBrotherhoodFinal(brotherhood.getId());
			System.out.println(procession);
			result = new ModelAndView("procession/listProcessions");
			result.addObject("brotherhood", brotherhood);
			result.addObject("procession", procession);
			result.addObject("requestURI", "procession/listProcessions.do");	
		} catch (Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "processionId", defaultValue = "-1") final int processionId) {
		ModelAndView result;
		final Procession procession = this.processionService.findOne(processionId);

		if (this.processionService.findOne(processionId) == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			Assert.notNull(procession, "procession.nul");
			result = new ModelAndView("procession/brotherhood/show");
			result.addObject("procession", procession);
			try {
				if (this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId()) != null)
					result.addObject("validMember", this.validMember(processionId));				
			} catch (Exception e) {

			}
			result.addObject("requestURI", "procession/brotherhood/show.do");
		}
		return result;
	}
	public Boolean validMember(final int processionId) {
		Boolean res = true;
		final Procession procession = this.processionService.findOne(processionId);
		final int brotherhoodId = this.processionService.findOne(processionId).getBrotherhood().getId();
		if (procession == null || !this.requestService.validMemberToCreateRequest(processionId) || !this.memberService.checkIsInBrotherhood(brotherhoodId) || procession.getIsFinal().equals(false))
			res = false;
		return res;
	}
}
