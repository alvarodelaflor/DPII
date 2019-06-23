
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CompanyService;
import services.QuoletService;
import domain.Application;
import domain.Quolet;

@Controller
@RequestMapping("quolet/company")
public class QuoletCompanyController extends AbstractController {

	// Services
	@Autowired
	private QuoletService		quoletService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CompanyService		companyService;


	// Methods

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "-1", value = "applicationId") final int applicationId) {
		ModelAndView res;

		try {
			Assert.isTrue(applicationId != -1);

			// ALVARO
			//			final Collection<Quolet> quolets = this.quoletService.getQuoletsNoDraftMode(auditId);
			final Collection<Quolet> quolets = this.quoletService.getQuoletsNoDraftModeV2(applicationId);
			// ALVARO

			res = new ModelAndView("quolet/company/list");
			res.addObject("quolets", quolets);

			// ALVARO
			final Application application = this.applicationService.findOne(applicationId);
			Assert.notNull(application, "Application not found in DB");
			// ALVARO

			res.addObject("requestURI", "quolet/application/list.do?applicationId=" + applicationId);
			res.addObject("applicationId", applicationId);
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

			res = new ModelAndView("quolet/company/show");
			res.addObject("quolet", quolet);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

}
