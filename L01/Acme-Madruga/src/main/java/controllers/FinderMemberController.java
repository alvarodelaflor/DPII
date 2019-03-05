
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import services.FinderService;
import domain.Area;
import domain.Finder;

@Controller
@RequestMapping("/finder/member")
public class FinderMemberController extends AbstractController {

	@Autowired
	private FinderService	finderService;
	@Autowired
	private AreaService		areaService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;

		try {
			final Finder finder = this.finderService.findByLoggedMemberWithCache();
			result = new ModelAndView("finder/show");

			result.addObject("finder", finder);
			final Collection<Area> areas = this.areaService.findAll();
			result.addObject("areas", areas);
			result.addObject("requestURI", "finder/member/show.do");
		} catch (final Exception e) {
			e.printStackTrace();
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ModelAndView refresh(final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Finder res = this.finderService.reconstructNoCache(finder, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("finder/show");
			final Collection<Area> areas = this.areaService.findAll();
			result.addObject("finder", res);
			result.addObject("areas", areas);
			result.addObject("finderError", "finderError");
		} else
			try {
				res = this.finderService.findNoCache(res);
				final Finder saved = this.finderService.save(res);
				result = new ModelAndView("finder/show");
				result.addObject("finder", saved);
				final Collection<Area> areas = this.areaService.findAll();
				result.addObject("areas", areas);
				result.addObject("requestURI", "finder/member/show.do");
				result.addObject("finderError", "");
			} catch (final Exception e) {
				e.printStackTrace();
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		return result;
	}
}
