
package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.LoginService;
import services.BrotherhoodService;
import services.MemberService;
import services.PathService;
import services.SegmentService;
import services.WelcomeService;
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
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private WelcomeService		welcomeService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showPath(@RequestParam(value = "paradeId", defaultValue = "-1") final int paradeId) {
		ModelAndView result;

		try {
			result = new ModelAndView("path/show");
			Path path = this.pathService.getParadePath(paradeId);
			if (this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId()) != null && path == null) {
				result.addObject("paradeId", paradeId);
				result.addObject("pathNull", true);
				result.addObject("memberLogged", (this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId()) != null));
				result.addObject("logo", this.welcomeService.getLogo());
				result.addObject("system", this.welcomeService.getSystem());
			} else {

				// We don't have a path? Then we create it, no problem
				if (path == null)
					path = this.pathService.createFromParade(paradeId);

				final Brotherhood loggedBrotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());

				List<Segment> segments = null;
				// In case we are logged as the brotherhood who owns this parade we want to also modify the path
				if (loggedBrotherhood != null && loggedBrotherhood.getId() == path.getParade().getBrotherhood().getId()) {
					result.addObject("owner", true);
					segments = this.segmentService.getAllSegments(path);
				} else {
					result.addObject("owner", false);
					segments = new ArrayList<Segment>(this.segmentService.getAllSegments(path));
					if (segments.size() > 0)
						segments.remove(segments.size() - 1);
				}

				result.addObject("segments", segments);
				result.addObject("paradeId", paradeId);
				result.addObject("pathNull", false);
				result.addObject("memberLogged", (this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId()) != null));
				result.addObject("logo", this.welcomeService.getLogo());
				result.addObject("system", this.welcomeService.getSystem());
			}
		} catch (final IllegalArgumentException e) {
			result = new ModelAndView("path/show");
			Path path = this.pathService.getParadePath(paradeId);
			// We don't have a path? Then we create it, no problem
			if (path == null)
				path = this.pathService.createFromParade(paradeId);
			final List<Segment> segments = new ArrayList<Segment>(this.segmentService.getAllSegments(path));
			if (segments.size() > 0)
				segments.remove(segments.size() - 1);
			result.addObject("segments", segments);
			result.addObject("paradeId", paradeId);
			result.addObject("pathNull", false);
			result.addObject("memberLogged", (this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId()) != null));
			result.addObject("logo", this.welcomeService.getLogo());
			result.addObject("system", this.welcomeService.getSystem());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST)
	public ModelAndView editSegment(@RequestParam("paradeId") final int paradeId, final Segment segment, final BindingResult binding, final RedirectAttributes redirectAttributes) {
		ModelAndView result;

		final Segment res = this.pathService.reconstruct(segment, binding);
		if (binding.hasErrors()) {
			result = new ModelAndView("redirect:/path/show.do?paradeId=" + paradeId);
			redirectAttributes.addFlashAttribute("wrongSegment", true);
		} else
			try {
				final Segment pathOrigin = this.segmentService.save(res, paradeId);
				this.pathService.setOrigin(paradeId, pathOrigin);
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
			this.pathService.delete(segmentId, paradeId);
			result = new ModelAndView("redirect:/path/show.do?paradeId=" + paradeId);
		} catch (final Exception e) {
			e.printStackTrace();
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
}
