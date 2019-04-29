/*
 * AuditAuditorController.java
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
import org.springframework.validation.BindingResult;
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
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	// Suporter Services
	@Autowired
	private AuditorService		auditorService;
	@Autowired
	private AuditService auditService;
	@Autowired
	private PositionService positionService;

	// Default Messages
	private String welcomeIndex = "redirect:/welcome/index.do";
	private String auditNotFound = "Audit not found in database";
	private String notAuditorLogin = "Not valid Auditor is login";
	private String editFinal = "Try to edit a final audit";
	private String nullAudit = "The audit is null";
	private String auditorNotOwner = "The auditor login is not the owner of the audit";

	// Constructors -----------------------------------------------------------

	public AuditAuditorController() {
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

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			Auditor auditorLogin = this.auditorService.getAuditorLogin();
			Assert.notNull(auditorLogin, notAuditorLogin);
			Audit audit = this.auditService.create();
			result = new ModelAndView("audit/auditor/edit");
			result.addObject("posFinal", this.positionService.findAllPositionWithStatusTrue());
			result.addObject("audit", audit);
		} catch (final Exception e) {
			System.out.println("Error e en GET /create AuditAuditorController.java: " + e.getMessage());
			result = new ModelAndView(welcomeIndex);
		}
		this.setConfig(result);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "auditId", defaultValue = "-1") final int auditId) {
		ModelAndView result;
		final Auditor auditorLogin = this.auditorService.getAuditorLogin();
		try {
			final Audit auditDB = this.auditService.findOne(auditId);
			Assert.notNull(auditDB, auditNotFound);
			Assert.isTrue(!auditDB.getStatus(), editFinal);
			Assert.notNull(auditorLogin, notAuditorLogin);
			Assert.isTrue(auditorLogin.equals(auditDB.getAuditor()));
			result = new ModelAndView("audit/auditor/edit");
			result.addObject("audit", auditDB);
			result.addObject("posFinal", this.positionService.findAllPositionWithStatusTrue());
		} catch (final Exception e) {
			if (auditorLogin != null) {
				result = new ModelAndView("audit/list");
				result.addObject("finalAudits", this.auditService.findAllByAuditorLogin(auditorLogin.getId()).get(true));
				result.addObject("draftAudits", this.auditService.findAllByAuditorLogin(auditorLogin.getId()).get(false));
				result.addObject("requestURI", "audit/list.do");
			} else {
				result = new ModelAndView(welcomeIndex);
			}
		}
		this.setConfig(result);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Audit audit, final BindingResult binding) {
		ModelAndView result;
		try {
			audit = this.auditService.reconstruct(audit, binding);
		} catch (final Exception e) {
			System.out.println("Error e reconstruct de audit: " + e);
			result = new ModelAndView(welcomeIndex);
			this.setConfig(result);
			return result;
		}

		if (binding.hasErrors()) {
			System.out.println("Error en AuditAuditorController.java, binding: " + binding);
			result = new ModelAndView("audit/auditor/create");
			result.addObject("audit", audit);
			result.addObject("posFinal", this.positionService.findAllPositionWithStatusTrue());
		} else {
			try {
				Auditor auditorLogin = this.auditorService.getAuditorLogin();
				Assert.notNull(auditorLogin, notAuditorLogin);
				Assert.notNull(audit, nullAudit);
				Assert.isTrue(auditorLogin.equals(audit.getAuditor()), auditorNotOwner);
				final Audit saveAudit = this.auditService.save(audit);
				result = new ModelAndView("redirect:/audit/show.do?auditId=" + saveAudit.getId());
				result.addObject("requestURI", "audit/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error en SAVE AuditAuditorController.java Throwable: " + oops);
				result = new ModelAndView("audit/auditor/edit");
				result.addObject("audit", audit);
				result.addObject("message", "audit.commit.error");
			}
		}
		this.setConfig(result);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "auditId", defaultValue = "-1") final int auditId) {
		ModelAndView result;
		final Auditor auditorLogin = this.auditorService.getAuditorLogin();

		try {
			Assert.notNull(auditorLogin, notAuditorLogin);
			final Audit auditDB = this.auditService.findOne(auditId);
			Assert.notNull(auditDB, auditNotFound);
			Assert.isTrue(auditDB.getAuditor().equals(auditorLogin), auditorNotOwner);
			this.auditService.delete(auditDB);
			result = new ModelAndView("redirect:/audit/list.do?auditorId=" + auditorLogin.getId());
		} catch (final Throwable oops) {
			System.out.println("Error en CurriculaRookieController.java Throwable: " + oops);
			if (auditorLogin != null)
				result = new ModelAndView("redirect:/audit/list.do?auditorId=" + auditorLogin.getId());
			else
				result = new ModelAndView(welcomeIndex);
			result.addObject("message", "audit.commit.error");
		}
		this.setConfig(result);
		return result;
	}
}
