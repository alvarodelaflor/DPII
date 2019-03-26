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
import services.ConfigurationService;
import services.MemberService;
import services.ParadeService;
import services.PositionAuxService;
import services.RequestService;
import services.SponsorshipService;
import services.WelcomeService;
import domain.Brotherhood;
import domain.Configuration;
import domain.Parade;
import domain.Sponsorship;

/*
 * CONTROL DE CAMBIOS ParadeBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 12:29 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 16:35 AÑADIDO RECONSTRUIDOR PARADE
 */

@Controller
@RequestMapping("/parade")
public class ParadeController extends AbstractController {

	@Autowired
	BrotherhoodService		brotherhoodService;

	@Autowired
	PositionAuxService		positionAuxService;

	@Autowired
	ParadeService			paradeService;

	@Autowired
	MemberService			memberService;

	@Autowired
	SponsorshipService		sponsorshipService;

	@Autowired
	RequestService			requestService;

	@Autowired
	ConfigurationService	configurationService;

	@Autowired
	WelcomeService			welcomeService;


	// Constructors -----------------------------------------------------------

	public ParadeController() {
		super();
	}

	@RequestMapping(value = "/listParades", method = RequestMethod.GET)
	public ModelAndView listParades(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;
		try {
			final Brotherhood brotherhood = this.brotherhoodService.findOne(id);
			System.out.println(brotherhood.getId());
			final Collection<Parade> parade = this.paradeService.findParadesBrotherhoodFinal(brotherhood.getId());
			System.out.println(parade);
			result = new ModelAndView("parade/listParades");
			result.addObject("brotherhood", brotherhood);
			result.addObject("parade", parade);
			result.addObject("requestURI", "parade/listParades.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "paradeId", defaultValue = "-1") final int paradeId) {
		ModelAndView result;
		final Parade parade = this.paradeService.findOne(paradeId);
		final Sponsorship sponsorship = this.sponsorshipService.randomSponsorship(paradeId);
		final Configuration config = this.configurationService.getConfiguration();

		if (this.paradeService.findOne(paradeId) == null)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			Assert.notNull(parade, "parade.nul");
			result = new ModelAndView("parade/brotherhood/show");
			result.addObject("sponsorship", sponsorship);
			result.addObject("parade", parade);
			result.addObject("config", config);
			try {
				if (this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId()) != null)
					result.addObject("validMember", this.validMember(paradeId));
			} catch (final Exception e) {

			}
			result.addObject("requestURI", "parade/brotherhood/show.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}
	public Boolean validMember(final int paradeId) {
		Boolean res = true;
		final Parade parade = this.paradeService.findOne(paradeId);
		final int brotherhoodId = this.paradeService.findOne(paradeId).getBrotherhood().getId();
		if (parade == null || !this.requestService.validMemberToCreateRequest(paradeId) || !this.memberService.checkIsInBrotherhood(brotherhoodId) || parade.getIsFinal().equals(false))
			res = false;
		return res;
	}

}
