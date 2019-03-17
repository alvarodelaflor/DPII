
package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
import services.PathService;
import services.SegmentService;
import domain.Brotherhood;
import domain.Path;
import domain.Segment;

@Controller
@RequestMapping("/path")
public class PathController extends AbstractController {

	@Autowired
	private PathService			pathService;

	@Autowired
	private SegmentService		segmentService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView listParades(@RequestParam(value = "paradeId", defaultValue = "-1") final int paradeId) {
		ModelAndView result;

		try {
			result = new ModelAndView("path/show");
			final Path path = this.pathService.getParadePath(paradeId);
			final Brotherhood loggedBrotherhood = this.brotherhoodService.findOne(LoginService.getPrincipal().getId());

			// In case we are logged as the brotherhood who owns this parade we want to also modify the path
			if (loggedBrotherhood != null && loggedBrotherhood.getId() == path.getParade().getBrotherhood().getId())
				result.addObject("owner", true);
			else
				result.addObject("owner", false);

			final List<Segment> segments = this.segmentService.getAllSegments(path);
			result.addObject("segments", segments);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
}
