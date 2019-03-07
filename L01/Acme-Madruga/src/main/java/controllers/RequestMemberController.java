
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import domain.Request;

@Controller
@RequestMapping("/request/member")
public class RequestMemberController extends AbstractController {

	@Autowired
	private RequestService	requestService;


	// ---------------------------------------- Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests;
		try {
			requests = this.requestService.getLoggedRequests();

			result = new ModelAndView("request/list");
			result.addObject("requests", requests);
			result.addObject("requestURI", "request/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	// ---------------------------------------- Showing
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int requestId) {
		ModelAndView result;
		Request request;
		try {
			request = this.requestService.findOne(requestId);
			if (request==null) {
				result = new ModelAndView("redirect:/welcome/index.do");
			} else {
				result = new ModelAndView("request/show");
				result.addObject("request", request);	
			}
		} catch (final Exception e) {
			System.out.println("Exception e en Show request: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	// ---------------------------------------- Creating
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createGET(@RequestParam final int processionId) {
		ModelAndView result;
		Request request;
		try {
			request = this.requestService.create(processionId);
			final Request saveRequest = this.requestService.save(request);
			result = new ModelAndView("redirect:/request/member/show.do?requestId=" + saveRequest.getId());
		} catch (final Exception e) {
			System.out.println("Exception e en request create:" + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView createPOST(final Request request, final BindingResult binding) {
		ModelAndView result = null;
		Request req;
		req = this.requestService.reconstruct(request, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(req);
		else
			try {
				this.requestService.save(req);
				result = new ModelAndView("redirect:/request/member/list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(req, "request.commit.error");
			}
		return result;
	}
	// ---------------------------------------- Deleting
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "requestId", defaultValue = "-1") final int requestId) {
		ModelAndView result;
		try {
			this.requestService.delete(requestId);

			result = new ModelAndView("redirect:/request/member/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
	// ---------------------------------------- Private methods
	private ModelAndView createEditModelAndView(final Request request) {
		final ModelAndView res = new ModelAndView("request/edit");
		res.addObject("request", request);
		return res;
	}

	private ModelAndView createEditModelAndView(final Request request, final String message) {
		final ModelAndView res = new ModelAndView("request/edit");
		res.addObject("request", request);
		res.addObject("message", message);
		return res;
	}
}
