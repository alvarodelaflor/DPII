
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.FinderAccomodation;
import services.FinderAccomodationService;

@Controller
@RequestMapping("/finder/travelAgency")
public class FinderAccomodationTravelAgencyController extends AbstractController {

	@Autowired
	private FinderAccomodationService finderService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;

		try {
			final FinderAccomodation finder = this.finderService.findByLoggedTravelAgencyWithCache();
			result = new ModelAndView("finder/show");

			result.addObject("finder", finder);
			result.addObject("requestURI", "finder/travelAgency/show.do");
		} catch (final Exception e) {
			e.printStackTrace();
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ModelAndView refresh(final FinderAccomodation finder, final BindingResult binding) {
		ModelAndView result;
		FinderAccomodation res = this.finderService.reconstructNoCache(finder, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("finder/show");
			result.addObject("finder", res);
			result.addObject("finderError", "finderError");
		} else
			try {

				res = this.finderService.findNoCache(res);
				final FinderAccomodation saved = this.finderService.save(res);
				System.out.println(saved.getAccomodations());
				result = new ModelAndView("finder/show");
				result.addObject("finder", saved);
				result.addObject("requestURI", "finder/travelAgency/show.do");
				result.addObject("finderError", "");
			} catch (final Exception e) {
				e.printStackTrace();
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}
}
