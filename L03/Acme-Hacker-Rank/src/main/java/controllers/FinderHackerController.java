
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.FinderService;
import domain.Finder;

@Controller
@RequestMapping("/finder/hacker")
public class FinderHackerController extends AbstractController {

	@Autowired
	private FinderService			finderService;
	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;

		try {
			final Finder finder = this.finderService.findByLoggedHackerWithCache();
			result = new ModelAndView("finder/show");

			result.addObject("finder", finder);
			result.addObject("requestURI", "finder/hacker/show.do");
		} catch (final Exception e) {
			e.printStackTrace();
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.configurationService.getConfiguration().getBanner());
		result.addObject("system", this.configurationService.getConfiguration().getSystemName());
		return result;
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ModelAndView refresh(final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Finder res = this.finderService.reconstructNoCache(finder, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("finder/show");
			result.addObject("finder", res);
			result.addObject("finderError", "finderError");
		} else
			try {
				res = this.finderService.findNoCache(res);
				final Finder saved = this.finderService.save(res);
				result = new ModelAndView("finder/show");
				result.addObject("finder", saved);
				result.addObject("requestURI", "finder/hacker/show.do");
				result.addObject("finderError", "");
			} catch (final Exception e) {
				e.printStackTrace();
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		result.addObject("logo", this.configurationService.getConfiguration().getBanner());
		result.addObject("system", this.configurationService.getConfiguration().getSystemName());
		return result;
	}
}
