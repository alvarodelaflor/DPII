
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.PositionService;
import services.ProviderService;
import services.SponsorshipService;
import domain.Position;
import domain.Provider;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/provider")
public class SponsorshipProviderController extends AbstractController {

	@Autowired
	SponsorshipService	sponsorshipService;

	@Autowired
	ProviderService		providerService;

	@Autowired
	PositionService		positionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView res;
		final Provider logged = this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());

		try {

			final Collection<Sponsorship> sponsorships;
			sponsorships = this.sponsorshipService.findAllByProviderId(logged.getId());
			res = new ModelAndView("provider/sponsorship/list");
			res.addObject("sponsorships", sponsorships);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "sponsorshipId", defaultValue = "-1") final int sponsorshipId) {

		ModelAndView res;

		try {

			final Sponsorship ss = this.sponsorshipService.findOne(sponsorshipId);
			res = new ModelAndView("provider/sponsorship/show.do");
			res.addObject("sponsorship", ss);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView res;

		try {

			final Sponsorship ss = this.sponsorshipService.create();
			res = new ModelAndView("/sponsorship/provider/edit");
			res.addObject("sponsorship", ss);
			final Collection<Position> positions = this.positionService.findALL();
			res.addObject("positions", positions);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "sponsorshipId", defaultValue = "-1") final int sponsorshipId) {

		ModelAndView res;

		try {

			res = new ModelAndView("/sponsorship/provider/edit");
			final Sponsorship ss = this.sponsorshipService.findOne(sponsorshipId);
			res.addObject("sponsorship", ss);
			final Collection<Position> positions = this.positionService.findALL();
			res.addObject("positions", positions);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(sponsorship);
		else
			try {

				this.sponsorshipService.save(sponsorship);
				res = new ModelAndView("redirect:list.do");
				res.addObject("requestURI", "sponsorship/provider/list.do");
			} catch (final Throwable oops) {

				res = this.createEditModelAndView(sponsorship, "ss.commit.error");
			}

		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "sponsorshipId", defaultValue = "-1") final int sponsorshipId) {

		ModelAndView res;

		try {

			this.sponsorshipService.delete(sponsorshipId);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {

			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			res = this.createEditModelAndView(sponsorship, "ss.commit.error");
		}

		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {

		final ModelAndView res = this.createEditModelAndView(sponsorship, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String code) {

		final ModelAndView res;

		res = new ModelAndView("provider/sponsorship/edit");
		res.addObject("sponsorship", sponsorship);
		final Collection<Position> positions = this.positionService.findALL();
		res.addObject("positions", positions);
		res.addObject("requestURI", "sponsorship/provider/list.do");
		res.addObject("message", code);

		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}
}
