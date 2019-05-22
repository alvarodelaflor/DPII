
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.FinderRequest;
import services.FinderRequestService;

@Controller
@RequestMapping("/finderRequest/travelAgency")
public class FinderRequestTravelAgencyController extends AbstractController {

	@Autowired
	private FinderRequestService finderService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;

		try {
			final FinderRequest finder = this.finderService.findByLoggedTravelAgencyWithCache();
			result = new ModelAndView("finderRequest/show");

			result.addObject("finder", finder);
			result.addObject("requestURI", "finderRequest/travelAgency/show.do");
		} catch (final Exception e) {
			e.printStackTrace();
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ModelAndView refresh(final FinderRequest finder, final BindingResult binding) {
		ModelAndView result;
		FinderRequest res = this.finderService.reconstructNoCache(finder, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("finderRequest/show");
			result.addObject("finder", res);
			result.addObject("finderError", "finderError");
		} else
			try {

				res = this.finderService.findNoCache(res);
				final FinderRequest saved = this.finderService.save(res);
				System.out.println(saved.getRequests());
				result = new ModelAndView("finderRequest/show");
				result.addObject("finder", saved);
				result.addObject("requestURI", "finderRequest/travelAgency/show.do");
				result.addObject("finderError", "");
			} catch (final Exception e) {
				e.printStackTrace();
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}
}
