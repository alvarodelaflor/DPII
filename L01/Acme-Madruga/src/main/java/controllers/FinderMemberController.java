
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import domain.Finder;

@Controller
@RequestMapping("/finder/member")
public class FinderMemberController extends AbstractController {

	@Autowired
	private FinderService	finderService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;

		try {
			final Finder finder = this.finderService.findByLoggedMemberWithCache();
			result = new ModelAndView("finder/show");
			result.addObject("finder", finder);
		} catch (final Exception e) {
			result = new ModelAndView("redirect: /welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ModelAndView refresh(final Finder finder, final BindingResult binding) {
		ModelAndView result;
		final Finder res = this.finderService.reconstructNoCache(finder, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("finder/show");
			result.addObject("finder", res);
		} else
			try {
				result = new ModelAndView("finder/show");
				result.addObject("finder", res);
			} catch (final Exception e) {
				result = new ModelAndView("redirect: /welcome/index.do");
			}

		return result;
	}
}
