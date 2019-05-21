
package controllers;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Accomodation;
import services.AccomodationService;

@Controller
@RequestMapping("/accomodation/travelAgency")
public class AccomodationTravelAgencyController extends AbstractController {

	@Autowired
	private AccomodationService accomodationService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Accomodation> accomodations = this.accomodationService.findAll();
			result = new ModelAndView("accomodation/travelAgency/list");
			result.addObject("accomodations", accomodations);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false, value = "accomodationId") final Integer accomodationId) {
		if (accomodationId == null)
			return new ModelAndView("redirect:/welcome/index.do");

		ModelAndView result;
		try {
			final Accomodation accomodation = this.accomodationService.findOne(accomodationId);
			Collection<String> pictures = null;
			if (accomodation.getPictures() != null)
				pictures = Arrays.asList(accomodation.getPictures().split("'"));
			result = new ModelAndView("accomodation/travelAgency/show");
			result.addObject("accomodation", accomodation);
			result.addObject("pictures", pictures);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
}
