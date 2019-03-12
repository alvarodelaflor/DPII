
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PathService;
import services.SegmentService;
import domain.Path;
import domain.Segment;

@Controller
@RequestMapping("/path")
public class PathController extends AbstractController {

	@Autowired
	private PathService		pathService;

	@Autowired
	private SegmentService	segmentService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView listParades(@RequestParam(value = "paradeId", defaultValue = "-1") final int paradeId) {
		ModelAndView result;
		try {
			result = new ModelAndView("path/show");

			final Path path = this.pathService.getParadePath(paradeId);
			final List<Segment> segments = this.segmentService.getAllSegments(path);
			result.addObject("segments", segments);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
}
