
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PathService;
import domain.Path;

@Controller
@RequestMapping("/path")
public class PathController extends AbstractController {

	@Autowired
	private PathService	pathService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listParades(@RequestParam(value = "paradeId", defaultValue = "-1") final int paradeId) {
		ModelAndView result;
		try {
			result = new ModelAndView("path/list");

			final Collection<Path> paths = this.pathService.getParadePaths(paradeId);
			result.addObject("paths", paths);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
}
