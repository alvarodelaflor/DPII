/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Audit;
import domain.Position;
import forms.PositionForm;
import services.AuditService;
import services.PositionFormService;
import services.PositionService;
import services.ProblemService;
import services.SponsorshipService;
import domain.Position;
import domain.Sponsorship;
import forms.PositionForm;

@Controller
@RequestMapping("/position")
public class PostionController extends AbstractController {

	@Autowired
	private PositionService		positionService;

	@Autowired
	private PositionFormService	positionFormService;

	@Autowired
	private ProblemService		problemService;
	
	@Autowired
	private AuditService auditService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	// Constructors -----------------------------------------------------------

	public PostionController() {
		super();
	}

	// listCompany ---------------------------------------------------------------		
	@RequestMapping(value = "/listCompany", method = RequestMethod.GET)
	public ModelAndView listCompany(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;
		try {
			System.out.println("entro");
			System.out.println(id);
			final Collection<Position> positions = this.positionService.findAllPositionStatusTrueCancelFalseByCompany(id);
			System.out.println(positions);
			result = new ModelAndView("position/listCompany");
			result.addObject("positions", positions);
			result.addObject("requestURI", "position/listCompany.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// LIST ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final PositionForm positionForm = this.positionFormService.create();
			System.out.println("entro");
			final Collection<Position> positions = this.positionService.findAllPositionWithStatusTrueCancelFalse();
			System.out.println(positions);
			result = new ModelAndView("position/list");
			result.addObject("positions", positions);
			result.addObject("positionForm", positionForm);
			result.addObject("requestURI", "position/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SHOW ---------------------------------------------------------------		
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id", defaultValue = "-1") final int id) {

		ModelAndView result;
		final Position position;

		Boolean hasProblem = false;

		try {
			position = this.positionService.findOne(id);
			final Sponsorship s = this.sponsorshipService.randomSponsorship(id);
			System.out.println(position);
			Assert.notNull(position);

			try {
				System.out.println(this.problemService.countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse(id));
				if (this.problemService.countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse(id) > 0)

					hasProblem = true;
			} catch (final Exception e) {
				hasProblem = false;
			}

			result = new ModelAndView("position/show");
			result.addObject("position", position);
			this.setAuditOfPosition(position.getId(), result);
			result.addObject("hasProblem", hasProblem);
			result.addObject("requestURI", "position/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
	
	
	/**
	 * 
	 * This method set audit to the view if the position has one
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	private void setAuditOfPosition(int positionId, ModelAndView result) {
		try {
			Collection<Audit> audits = this.auditService.getAuditByPositionId(positionId);
			Collection<Audit> aux = new ArrayList<Audit>();
			for (Audit audit : audits) {				
				if (audit!=null && audit.getStatus()!= null && audit.getStatus().equals(true)) {
					aux.add(audit);
				}
			}
			if (!aux.isEmpty()) {
				result.addObject("audits", aux);
			} else {
				result.addObject("audits", null);
			}
		} catch (Exception e) {
			result.addObject("audit", null);
		}
	}

	// SAVE ---------------------------------------------------------------		
	@RequestMapping(value = "/newPalabra", method = RequestMethod.GET)
	public ModelAndView newPalabra(@RequestParam("newPalabra") final String newPalabra) {
		ModelAndView result;

		try {
			System.out.println(newPalabra);
			System.out.println("Carmen: Voy a intentar guardar");
			final Collection<Position> positions = this.positionService.search(newPalabra);
			System.out.println(positions);
			result = new ModelAndView("position/list");
			result.addObject("positions", positions);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

}
