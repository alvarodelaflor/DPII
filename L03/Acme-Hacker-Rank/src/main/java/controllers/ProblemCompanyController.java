
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProblemService;
import domain.Problem;

@Controller
@RequestMapping("/problem/company")
public class ProblemCompanyController extends AbstractController {

	@Autowired
	private ProblemService	problemService;


	// List of my problems ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Problem> problems = this.problemService.findAllProblemsByLoggedCompany();

			result = new ModelAndView("problem/company/list");
			result.addObject("problems", problems);
			result.addObject("requestURI", "problems/company/list.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	// Showing a problem I own -----------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "problemId", defaultValue = "-1") final int problemId) {
		ModelAndView result;
		try {
			final Problem problem = this.problemService.findOneLoggedIsOwner(problemId);
			result = new ModelAndView("problem/company/show");
			result.addObject("problem", problem);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	// Delete a problem I own -----------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "problemId", defaultValue = "-1") final int problemId) {
		ModelAndView result;
		try {
			this.problemService.delete(problemId);
			result = new ModelAndView("redirect:/problem/company/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	// Create a problem form -----------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final Problem problem = this.problemService.create();
			result = new ModelAndView("problem/company/form");
			result.addObject("problem", problem);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	// Creating a new problem --------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create(final Problem problem, final BindingResult binding) {
		ModelAndView result;

		final Problem prob = this.problemService.reconstruct(problem, binding);

		System.out.println(binding.getAllErrors());
		if (binding.hasErrors()) {
			result = new ModelAndView("problem/company/form");
			result.addObject("problem", problem);
		} else
			try {
				this.problemService.save(prob);
				result = new ModelAndView("redirect:/problem/company/list.do");
			} catch (final Exception e) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		return result;
	}
}
