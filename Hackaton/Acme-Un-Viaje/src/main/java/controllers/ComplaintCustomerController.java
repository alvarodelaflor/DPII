
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import domain.Complaint;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController extends AbstractController {

	@Autowired
	private ComplaintService	complaintService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res = null;

		try {
			final Collection<Complaint> complaintsUnassigned = this.complaintService.getLoggedCustomerUnassignedComplaints();
			final Collection<Complaint> complaintsAssigned = this.complaintService.getLoggedCustomerAssignedComplaints();
			res = new ModelAndView("complaint/customer/list");
			res.addObject("complaintsUnassigned", complaintsUnassigned);
			res.addObject("complaintsAssigned", complaintsAssigned);
			res.addObject("requestURI", "/complaint/customer/list.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	//	@RequestMapping(value = "/show", method = RequestMethod.GET)
	//	public ModelAndView show(@RequestParam(required = false, value = "complaintId") final Integer complaintId) {
	//		if (complaintId == null)
	//			return new ModelAndView("redirect:/welcome/index.do");
	//
	//		ModelAndView res = null;
	//		try {
	//			final Complaint complaint = this.complaintService.getLoggedCustomerComplaint(complaintId);
	//			res = new ModelAndView("complaint/customer/show");
	//			res.addObject("complaint", complaint);
	//		} catch (final Throwable oops) {
	//			res = new ModelAndView("redirect:/welcome/index.do");
	//		}
	//
	//		return res;
	//	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res = null;
		try {
			final Complaint complaint = this.complaintService.create();
			res = new ModelAndView("complaint/customer/create");
			res.addObject("complaint", complaint);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false, value = "complaintId") final Integer complaintId) {
		if (complaintId == null)
			return new ModelAndView("redirect:/welcome/index.do");

		ModelAndView res = null;
		try {
			final Complaint complaint = this.complaintService.getLoggedCustomerComplaint(complaintId);
			res = new ModelAndView("complaint/customer/edit");
			res.addObject("complaint", complaint);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView editPost(final Complaint complaint, final BindingResult binding) {
		Complaint reconstructedComplaint;
		try {
			reconstructedComplaint = this.complaintService.reconstruct(complaint, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		ModelAndView res = null;
		try {
			this.complaintService.save(reconstructedComplaint);
			res = new ModelAndView("redirect:/complaint/customer/list.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		return res;
	}

}
