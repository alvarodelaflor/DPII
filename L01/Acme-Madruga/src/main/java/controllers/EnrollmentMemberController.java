
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EnrolledService;
import domain.Enrolled;

@Controller
@RequestMapping("/enroll/member")
public class EnrollmentMemberController extends AbstractController {

	@Autowired
	private EnrolledService	enrollmentService;


	// ---------------------------------------- Listing
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Enrolled enrollment;
		try {
			enrollment = this.enrollmentService.create(brotherhoodId);
			this.enrollmentService.save(enrollment);
			result = new ModelAndView("redirect:/brotherhood/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
}
