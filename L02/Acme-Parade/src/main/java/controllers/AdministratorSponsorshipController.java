
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.SponsorshipService;
import services.WelcomeService;
import domain.Configuration;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/administrator")
public class AdministratorSponsorshipController extends AbstractController {

	@Autowired
	private SponsorshipService		sponsorshipService;
	@Autowired
	private WelcomeService			welcomeService;
	@Autowired
	private ConfigurationService	configurationService;


	public AdministratorSponsorshipController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final Configuration config = this.configurationService.getConfiguration();

		ModelAndView res;

		try {
			res = new ModelAndView("sponsorship/administrator/list");
			res.addObject("sponsorships", this.sponsorshipService.findAll());
			res.addObject("requestURI", "sponsorship/administrator/list.do");
			res.addObject("config", config);
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}
		res.addObject("logo", this.welcomeService.getLogo());
		res.addObject("system", this.welcomeService.getSystem());
		return res;
	}
	@RequestMapping(value = "/checkCreditCard", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "sponsorshipId", defaultValue = "-1") final int sponsorshipId) {
		ModelAndView result;

		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		System.out.println("sponsorshipId encontrado: " + sponsorshipId);
		Assert.notNull(sponsorshipId, "sponsorship.null");

		if (this.sponsorshipService.findOne(sponsorshipId) == null) {
			result = new ModelAndView("sponsorship/list");
			final Configuration config = this.configurationService.getConfiguration();
			result.addObject("config", config);
			result.addObject("sponsorships", this.sponsorshipService.findAll());
			result.addObject("requestURI", "sponsorship/administrator/list.do");
		} else
			try {
				Assert.isTrue(sponsorship != null);
				final Configuration config = this.configurationService.getConfiguration();
				result = new ModelAndView("sponsorship/administrator/list");
				this.sponsorshipService.checkCreditCard(sponsorship);
				//				final String system = this.welcomeService.getSystem();
				//				result.addObject("system", system);
				//				final String logo = this.welcomeService.getLogo();
				//				result.addObject("logo", logo);
				result.addObject("config", config);
				result.addObject("sponsorships", this.sponsorshipService.findAll());
				result.addObject("requestURI", "sponsorship/administrator/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error al borrar sponsorship desde actor: ");
				System.out.println(oops);
				result = new ModelAndView("sponsorship/administrator/list");
				//				final String system = this.welcomeService.getSystem();
				//				result.addObject("system", system);
				//				final String logo = this.welcomeService.getLogo();
				//				result.addObject("logo", logo);
				result.addObject("sponsorships", this.sponsorshipService.findAll());
			}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/collect", method = RequestMethod.GET)
	public ModelAndView collect(@RequestParam(value = "sponsorshipId", defaultValue = "-1") final int sponsorshipId) {

		final ModelAndView res = new ModelAndView("redirect:list.do");

		final Sponsorship s = this.sponsorshipService.findOne(sponsorshipId);
		s.setBannerCount(0);
		this.sponsorshipService.save(s);
		res.addObject("logo", this.welcomeService.getLogo());
		res.addObject("system", this.welcomeService.getSystem());
		return res;
	}
}
