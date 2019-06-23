
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
import services.ApplicationService;
import services.QuoletService;
import services.RookieService;
import domain.Application;
import domain.Quolet;
import domain.Rookie;

@Controller
@RequestMapping("quolet/rookie")
public class QuoletRookieController extends AbstractController {

	// Services
	@Autowired
	private QuoletService		quoletService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private RookieService		rookieService;


	// Methods

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "-1", value = "applicationId") final int applicationId) {
		ModelAndView res;

		try {
			Assert.isTrue(applicationId != -1);
			// ALVARO
			//			final Collection<Quolet> quolets = this.quoletService.getLoggedQuolets(applicationId);
			final Collection<Quolet> quolets = this.quoletService.getLoggedQuoletsV2(applicationId);
			// ALVARO
			res = new ModelAndView("quolet/rookie/list");
			res.addObject("quolets", quolets);
			res.addObject("requestURI", "quolet/rookie/list.do?applicationId=" + applicationId);
			res.addObject("applicationId", applicationId);
			// ALVARO
			final Application application = this.applicationService.findOne(applicationId);
			final Rookie rookieApplication = application.getRookie();
			final Rookie rookieLogger = this.rookieService.getRookieByUserAccountId(LoginService.getPrincipal().getId());
			res.addObject("validToCreate", rookieApplication.equals(rookieLogger));
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
			res = new ModelAndView("quolet/rookie/show");
			res.addObject("quolet", quolet);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "-1", value = "applicationId") final int applicationId) {
		ModelAndView res;

		try {
			final Quolet quolet = this.quoletService.create(applicationId);
			res = new ModelAndView("quolet/rookie/create");
			res.addObject("quolet", quolet);
			res.addObject("creating", true);
			res.addObject("applicationId", applicationId);
			res.addObject("URI", "quolet/rookie/create.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
	// Create
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createPOST(final Quolet quolet, final BindingResult binding, @RequestParam(defaultValue = "-1", value = "applicationId") final int applicationId) {
		ModelAndView res;
		Quolet reconstructed = null;
		try {
			reconstructed = this.quoletService.reconstruct(quolet, binding, applicationId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		if (binding.hasErrors()) {
			res = new ModelAndView("quolet/rookie/create");
			res.addObject("quolet", quolet);
			res.addObject("applicationId", applicationId);
			res.addObject("URI", "quolet/rookie/create.do");
		} else
			try {
				this.quoletService.save(reconstructed);
				res = new ModelAndView("redirect:/quolet/rookie/list.do?applicationId=" + quolet.getApplication().getId());
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/welcome/index.do");
			}

		return res;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editGET(@RequestParam(defaultValue = "-1", value = "quoletId") final int quoletId, @RequestParam(defaultValue = "-1", value = "applicationId") final int applicationId) {
		ModelAndView res;

		try {
			final Quolet quolet = this.quoletService.getLoggedQuoletForEdit(quoletId);
			res = new ModelAndView("quolet/rookie/edit");
			res.addObject("quolet", quolet);
			res.addObject("applicationId", applicationId);
			res.addObject("URI", "quolet/rookie/edit.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editPOST(final Quolet quolet, final BindingResult binding, @RequestParam(defaultValue = "-1", value = "applicationId") final int applicationId) {
		ModelAndView res;
		Quolet reconstructed = null;
		System.out.println("applicationId..." + applicationId);
		try {
			reconstructed = this.quoletService.reconstruct(quolet, binding, applicationId);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		if (binding.hasErrors()) {
			res = new ModelAndView("quolet/rookie/edit");
			res.addObject("quolet", quolet);
			res.addObject("applicationId", applicationId);
			res.addObject("URI", "quolet/rookie/edit.do");
		} else
			try {
				this.quoletService.save(reconstructed);
				res = new ModelAndView("redirect:/quolet/rookie/list.do?applicationId=" + quolet.getApplication().getId());
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
			res = new ModelAndView("redirect:/quolet/rookie/list.do?applicationId=" + quolet.getApplication().getId());
			this.quoletService.delete(quoletId);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
}
