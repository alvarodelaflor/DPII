
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.CompanyService;
import services.QuoletService;
import domain.Audit;
import domain.Quolet;

@Controller
@RequestMapping("quolet/auditor")
public class QuoletAuditorController extends AbstractController {

	// Services
	@Autowired
	private QuoletService	quoletService;

	@Autowired
	private AuditService	auditService;

	@Autowired
	private CompanyService	companyService;


	// Methods

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "-1", value = "auditId") final int auditId) {
		ModelAndView res;

		try {
			Assert.isTrue(auditId != -1);

			// ALVARO
			//			final Collection<Quolet> quolets = this.quoletService.getQuoletsNoDraftMode(auditId);
			final Collection<Quolet> quolets = this.quoletService.getQuoletsNoDraftModeV2(auditId);
			// ALVARO

			res = new ModelAndView("quolet/auditor/list");
			res.addObject("quolets", quolets);

			// ALVARO
			final Audit audit = this.auditService.findOne(auditId);
			Assert.notNull(audit, "Audit not found in DB");
			// ALVARO

			res.addObject("requestURI", "quolet/auditor/list.do?auditId=" + auditId);
			res.addObject("auditId", auditId);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(defaultValue = "-1", value = "quoletId") final int quoletId) {
		ModelAndView res;

		try {
			// ALVARO
			//			final Quolet quolet = this.quoletService.getQuoletNoDraftMode(quoletId);
			final Quolet quolet = this.quoletService.findOne(quoletId);
			Assert.notNull(quolet, "Not found in DB");
			// ALVARO

			res = new ModelAndView("quolet/auditor/show");
			res.addObject("quolet", quolet);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

}
