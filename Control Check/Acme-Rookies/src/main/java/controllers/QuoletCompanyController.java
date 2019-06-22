
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AuditService;
import services.CompanyService;
import services.QuoletService;
import domain.Audit;
import domain.Company;
import domain.Quolet;

@Controller
@RequestMapping("quolet/company")
public class QuoletCompanyController extends AbstractController {

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
			//			final Collection<Quolet> quolets = this.quoletService.getLoggedQuolets(auditId);
			final Collection<Quolet> quolets = this.quoletService.getLoggedQuoletsV2(auditId);
			// ALVARO
			res = new ModelAndView("quolet/company/list");
			res.addObject("quolets", quolets);
			res.addObject("requestURI", "quolet/company/list.do?auditId=" + auditId);
			res.addObject("auditId", auditId);
			// ALVARO
			final Audit audit = this.auditService.findOne(auditId);
			final Company companyAudit = audit.getPosition().getCompany();
			final Company companyLogger = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId());
			res.addObject("validToCreate", companyAudit.equals(companyLogger));
			// ALVARO
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
			//			final Quolet quolet = this.quoletService.getLoggedQuolet(quoletId);
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

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "-1", value = "auditId") final int auditId) {
		ModelAndView res;

		try {
			final Quolet quolet = this.quoletService.create(auditId);
			res = new ModelAndView("quolet/company/create");
			res.addObject("quolet", quolet);
			res.addObject("creating", true);
			res.addObject("URI", "quolet/company/create.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
	// Create
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createPOST(final Quolet quolet, final BindingResult binding) {
		ModelAndView res;
		Quolet reconstructed = null;
		try {
			reconstructed = this.quoletService.reconstruct(quolet, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		if (binding.hasErrors()) {
			res = new ModelAndView("quolet/company/create");
			res.addObject("quolet", quolet);
			res.addObject("URI", "quolet/company/create.do");
		} else
			try {
				this.quoletService.save(reconstructed);
				res = new ModelAndView("redirect:/quolet/company/list.do?auditId=" + quolet.getAudit().getId());
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/welcome/index.do");
			}

		return res;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editGET(@RequestParam(defaultValue = "-1", value = "quoletId") final int quoletId) {
		ModelAndView res;

		try {
			final Quolet quolet = this.quoletService.getLoggedQuoletForEdit(quoletId);
			res = new ModelAndView("quolet/company/edit");
			res.addObject("quolet", quolet);
			res.addObject("URI", "quolet/company/edit.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editPOST(final Quolet quolet, final BindingResult binding) {
		ModelAndView res;
		Quolet reconstructed = null;
		try {
			reconstructed = this.quoletService.reconstruct(quolet, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		if (binding.hasErrors()) {
			res = new ModelAndView("quolet/company/edit");
			res.addObject("quolet", quolet);
			res.addObject("URI", "quolet/company/edit.do");
		} else
			try {
				this.quoletService.save(reconstructed);
				res = new ModelAndView("redirect:/quolet/company/list.do?auditId=" + quolet.getAudit().getId());
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/welcome/index.do");
			}

		return res;
	}

	// Edit
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(defaultValue = "-1", value = "quoletId") final int quoletId) {
		ModelAndView res;

		try {
			final Quolet quolet = this.quoletService.getLoggedQuolet(quoletId);
			res = new ModelAndView("redirect:/quolet/company/list.do?auditId=" + quolet.getAudit().getId());
			this.quoletService.delete(quoletId);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
}
