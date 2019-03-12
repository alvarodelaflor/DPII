
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.ParadeService;
import services.SponsorService;
import services.SponsorshipService;
import services.WelcomeService;
import domain.Parade;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship")
public class SponsorshipController extends AbstractController {

	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private WelcomeService		welcomeService;

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ParadeService		paradeService;


	public SponsorshipController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView res;

		try {
			final UserAccount login = LoginService.getPrincipal();
			final Sponsor sponsor = this.sponsorService.getSponsorByUserId(login.getId());

			res = new ModelAndView("sponsorship/list");
			res.addObject("sponsorships", sponsor.getSponsorships());
			res.addObject("requestURI", "sponsorship/list.do");
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}
		res.addObject("logo", this.welcomeService.getLogo());
		res.addObject("system", this.welcomeService.getSystem());
		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam("sponsorshipId") final int sponsorshipId) {
		ModelAndView result;
		final UserAccount user = LoginService.getPrincipal();
		final Sponsor sponsor = this.sponsorService.getSponsorByUserId(user.getId());

		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		if (!sponsor.getSponsorships().contains(sponsorship))
			result = new ModelAndView("welcome/index");

		else {
			result = new ModelAndView("sponsorship/show");
			result.addObject("sponsorship", sponsorship);
			//		final String system = this.welcomeService.getSystem();
			//		result.addObject("system", system);
			//		final String logo = this.welcomeService.getLogo();
			//		result.addObject("logo", logo);
			result.addObject("language", language);
			result.addObject("requestURI", "sponsorship/show.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create();

		final Collection<Parade> parades = this.paradeService.findAll();

		result = new ModelAndView("sponsorship/create");
		result.addObject("sponsorship", sponsorship);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("parades", parades);
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "sponsorshipId", defaultValue = "-1") final int sponsorshipId) {
		ModelAndView result;

		if (this.sponsorshipService.findOne(sponsorshipId) == null || !this.sponsorService.getSponsorByUserId(LoginService.getPrincipal().getId()).getSponsorships().contains(this.sponsorshipService.findOne(sponsorshipId)))
			result = new ModelAndView("welcome/index");
		else {
			final Sponsorship sponsorship;
			final Collection<Parade> parades = this.paradeService.findAll();
			Assert.isTrue(this.sponsorshipService.findOne(sponsorshipId) != null);
			sponsorship = this.sponsorshipService.findOne(sponsorshipId);

			result = new ModelAndView("sponsorship/edit");
			result.addObject("parades", parades);
			result.addObject("sponsorship", sponsorship);
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		sponsorship = this.sponsorshipService.reconstruct(sponsorship, binding);

		if (binding.hasErrors()) {
			System.out.println("El error pasa por aquí alvaro (IF de save())");
			System.out.println(binding);
			result = new ModelAndView("sponsorship/edit");
		} else
			try {
				Assert.isTrue(sponsorship != null);
				final Sponsorship savedSponsorship = this.sponsorshipService.save(sponsorship);
				final int userLoggin = LoginService.getPrincipal().getId();
				final Sponsor sponsor = this.sponsorService.getSponsorByUserId(userLoggin);
				Assert.isTrue(sponsor != null);

				result = new ModelAndView("sponsorship/list");
				result.addObject("sponsorships", savedSponsorship.getSponsor().getSponsorships());
				result.addObject("requestURI", "sponsorship/list.do");
			} catch (final Throwable oops) {
				System.out.println("El error es en SponsorshipController: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("sponsorship/edit");
			}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "sponsorshipId", defaultValue = "-1") final int sponsorshipId) {
		ModelAndView result;

		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		System.out.println("sponsorshipId encontrado: " + sponsorshipId);
		Assert.notNull(sponsorshipId, "sponsorship.null");

		final UserAccount user = LoginService.getPrincipal();
		final Sponsor a = this.sponsorService.getSponsorByUserId(user.getId());

		if (this.sponsorshipService.findOne(sponsorshipId) == null || !this.sponsorService.getSponsorByUserId(LoginService.getPrincipal().getId()).getSponsorships().contains(this.sponsorshipService.findOne(sponsorshipId))) {
			result = new ModelAndView("sponsorship/list");
			result.addObject("sponsorships", this.sponsorService.getSponsorByUserId(LoginService.getPrincipal().getId()).getSponsorships());
			result.addObject("requestURI", "sponsorship/list.do");
		} else if (!a.getSponsorships().contains(sponsorship))
			result = new ModelAndView("welcome/index");
		else
			try {
				Assert.isTrue(sponsorship != null);

				final int userLoggin = LoginService.getPrincipal().getId();
				final Sponsor sponsor = this.sponsorService.getSponsorByUserId(userLoggin);
				Assert.isTrue(sponsor != null);
				sponsor.getSponsorships().remove(sponsorship);
				result = new ModelAndView("sponsorship/list");
				this.sponsorshipService.delete(sponsorship);
				//				final String system = this.welcomeService.getSystem();
				//				result.addObject("system", system);
				//				final String logo = this.welcomeService.getLogo();
				//				result.addObject("logo", logo);
				result.addObject("sponsorships", sponsor.getSponsorships());
				result.addObject("requestURI", "sponsorship/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error al borrar sponsorship desde actor: ");
				System.out.println(oops);
				result = new ModelAndView("sponsorship/list");
				final int userLoggin = LoginService.getPrincipal().getId();
				final Sponsor sponsor = this.sponsorService.getSponsorByUserId(userLoggin);
				//				final String system = this.welcomeService.getSystem();
				//				result.addObject("system", system);
				//				final String logo = this.welcomeService.getLogo();
				//				result.addObject("logo", logo);
				result.addObject("sponsorships", sponsor.getSponsorships());
			}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}
}
