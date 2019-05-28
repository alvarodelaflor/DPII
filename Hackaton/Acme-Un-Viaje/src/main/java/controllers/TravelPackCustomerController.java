
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TravelPackService;
import domain.TravelPack;

@Controller
@RequestMapping("/travelPack/customer")
public class TravelPackCustomerController extends AbstractController {

	@Autowired
	private TravelPackService	travelPackService;


	@RequestMapping(value = "/listOffered", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res = null;
		try {
			final Collection<TravelPack> travelPacks = this.travelPackService.getLoggedNotDraftStatusNull();
			res = new ModelAndView("travelPack/customer/listOffered");
			res.addObject("travelPacks", travelPacks);
			res.addObject("offered", true);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		return res;
	}

	@RequestMapping(value = "/listAccepted", method = RequestMethod.GET)
	public ModelAndView listAccepted() {
		ModelAndView res = null;
		try {
			final Collection<TravelPack> travelPacks = this.travelPackService.getLoggedNotDraftStatusTrue();
			res = new ModelAndView("travelPack/customer/listAccepted");
			res.addObject("travelPacks", travelPacks);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		return res;
	}

	@RequestMapping(value = "/listRejected", method = RequestMethod.GET)
	public ModelAndView listRejected() {
		ModelAndView res = null;
		try {
			final Collection<TravelPack> travelPacks = this.travelPackService.getLoggedNotDraftStatusFalse();
			res = new ModelAndView("travelPack/customer/listRejected");
			res.addObject("travelPacks", travelPacks);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		return res;
	}
}
