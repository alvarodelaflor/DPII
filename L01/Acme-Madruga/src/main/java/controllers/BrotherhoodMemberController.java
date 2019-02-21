
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import domain.Brotherhood;

@Controller
@RequestMapping("/brotherhood/member")
public class BrotherhoodMemberController extends AbstractController {

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Brotherhood> brotherhoods;
		try {
			brotherhoods = this.brotherhoodService.findFromLoggedMember();
			result = new ModelAndView("brotherhood/list");
			result.addObject("brotherhoods", brotherhoods);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/drop", method = RequestMethod.GET)
	public ModelAndView drop(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		try {
			this.brotherhoodService.dropLogged(brotherhoodId);
			result = new ModelAndView("redirect:/brotherhood/member/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
}
