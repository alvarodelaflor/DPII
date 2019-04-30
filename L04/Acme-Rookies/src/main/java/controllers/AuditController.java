/*
 * AuditController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Audit;
import domain.Auditor;
import services.AuditService;
import services.AuditorService;
import services.PositionService;

/*
 * CONTROL DE CAMBIOS CurriculaRookieController.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	// Suported Services
	@Autowired
	private AuditService auditService;
	@Autowired
	private AuditorService auditorService;
	@Autowired
	private PositionService positionService;
	
	// Default Messages
	private String notFoundAudit = "The audit has not been found in database by ID.";


	// Constructors -----------------------------------------------------------

	public AuditController() {
		super();
	}
	
	private void setConfig(ModelAndView result) {
		Auditor auditor = this.auditorService.getAuditorLogin();
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		if (auditor!=null) {
			result.addObject("auditorLogger", true);		
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "auditorId", defaultValue = "-1") final int auditorId) {
		ModelAndView result;
		try {
			result = new ModelAndView("audit/list");
			result.addObject("finalAudits", this.auditService.findAllByAuditorLogin(auditorId).get(true));
			result.addObject("draftAudits", this.auditService.findAllByAuditorLogin(auditorId).get(false));
			Auditor auditor = this.auditorService.getAuditorLogin();
			if (auditor!=null) {				
				result.addObject("positions", this.positionService.findAllPositionWithStatusTrueNotCancelNotAudit());
			}
			result.addObject("requestURI", "audit/list.do");
		} catch (final Exception e) {
			System.out.println("Error e en list Audit/Controller: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		this.setConfig(result);
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "auditId", defaultValue = "-1") final int auditId) {

		ModelAndView result;
		try {
			Auditor auditorLogger = this.auditorService.getAuditorLogin();
			final Audit auditDB = this.auditService.findOne(auditId);
			Assert.notNull(auditDB, notFoundAudit);
			result = new ModelAndView("audit/show");
			result.addObject("audit", auditDB);
			if (auditDB.getAuditor()!=null && auditDB.getAuditor().equals(auditorLogger) && auditDB.getStatus()!=null && auditDB.getStatus().equals(false)) {
				result.addObject("auditLogin", true);
			}
			
			result.addObject("requestURI", "audit/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		this.setConfig(result);
		return result;
	}
}
