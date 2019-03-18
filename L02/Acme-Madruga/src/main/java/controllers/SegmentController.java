
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SegmentService;
import domain.Segment;

@Controller
@RequestMapping("/segment")
public class SegmentController extends AbstractController {

	@Autowired
	private SegmentService	segmentService;


	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST)
	public ModelAndView editSegment(@RequestParam("paradeId") final int paradeId, final Segment segment, final BindingResult binding) {
		ModelAndView result;

		final Segment res = this.segmentService.reconstruct(segment, binding);

		if (binding.hasErrors()) {
			result = new ModelAndView("redirect:/path/show.do?paradeId=" + paradeId);
			result.addObject("wrongSegment", res);
		} else
			try {
				this.segmentService.save(res, paradeId);
				result = new ModelAndView("redirect:/path/show.do?paradeId=" + paradeId);
			} catch (final Exception e) {
				e.printStackTrace();
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		return result;
	}

	@RequestMapping(value = "/brotherhood/delete", method = RequestMethod.GET)
	public ModelAndView deleteSegment(@RequestParam("paradeId") final int paradeId, final int segmentId) {
		ModelAndView result;

		try {
			this.segmentService.delete(segmentId, paradeId);
			result = new ModelAndView("redirect:/path/show.do?paradeId=" + paradeId);
		} catch (final Exception e) {
			e.printStackTrace();
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
}
