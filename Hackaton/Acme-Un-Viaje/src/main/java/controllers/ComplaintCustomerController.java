
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
import services.TravelPackService;
import domain.Complaint;
import domain.Host;
import domain.Transporter;
import domain.TravelAgency;
import domain.TravelPack;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController extends AbstractController {

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private TravelPackService	travelPackService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res = null;

		try {
			final Collection<Complaint> complaintsUnassigned = this.complaintService.getLoggedCustomerUnassignedComplaints();
			final Collection<Complaint> complaintsAssigned = this.complaintService.getLoggedCustomerAssignedComplaints();
			res = new ModelAndView("complaint/customer/list");
			res.addObject("complaintsUnassigned", complaintsUnassigned);
			res.addObject("complaintsAssigned", complaintsAssigned);
			res.addObject("requestURI", "complaint/customer/list.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false, value = "travelPackId") final Integer travelPackId) {
		if (travelPackId == null)
			return new ModelAndView("redirect:/welcome/index.do");

		ModelAndView res = null;
		try {
			final Complaint complaint = this.complaintService.create();
			final TravelPack travelPack = this.travelPackService.findOne(travelPackId);
			final TravelAgency travelAgency = travelPack.getTravelAgency();
			final Collection<Host> hosts = this.travelPackService.getHosts(travelPack);
			final Collection<Transporter> transporters = this.travelPackService.getTransporters(travelPack);

			res = new ModelAndView("complaint/customer/create");
			res.addObject("complaint", complaint);
			res.addObject("travelAgency", travelAgency);
			res.addObject("travelPackId", travelPackId);
			res.addObject("hosts", hosts);
			res.addObject("transporters", transporters);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		return res;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createPost(@RequestParam(value = "travelPackId", required = false) final Integer travelPackId, final Complaint complaint, final BindingResult binding) {
		if (travelPackId == null)
			return new ModelAndView("redirect:/welcome/index.do");

		Complaint reconstructedComplaint;
		try {
			reconstructedComplaint = this.complaintService.reconstruct(complaint, binding);
		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/welcome/index.do");
		}

		ModelAndView res = null;
		if (binding.hasErrors()) {
			final TravelPack travelPack = this.travelPackService.findOne(travelPackId);
			final TravelAgency travelAgency = travelPack.getTravelAgency();
			final Collection<Host> hosts = this.travelPackService.getHosts(travelPack);
			final Collection<Transporter> transporters = this.travelPackService.getTransporters(travelPack);

			res = new ModelAndView("complaint/customer/create");
			res.addObject("complaint", complaint);
			res.addObject("travelPackId", travelPackId);
			res.addObject("travelAgency", travelAgency);
			res.addObject("hosts", hosts);
			res.addObject("transporters", transporters);
		} else
			try {
				this.complaintService.save(reconstructedComplaint, travelPackId);
				res = new ModelAndView("redirect:/complaint/customer/list.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
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
			if (complaint == null)
				res = new ModelAndView("redirect:/welcome/index.do");
			else {
				res = new ModelAndView("complaint/customer/edit");
				res.addObject("complaint", complaint);
			}
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
		if (binding.hasErrors()) {
			res = new ModelAndView("complaint/customer/edit");
			res.addObject("complaint", complaint);
		} else
			try {
				this.complaintService.save(reconstructedComplaint, null);
				res = new ModelAndView("redirect:/complaint/customer/list.do");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				res = new ModelAndView("redirect:/welcome/index.do");
			}
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = false, value = "complaintId") final Integer complaintId) {
		if (complaintId == null)
			return new ModelAndView("redirect:/welcome/index.do");

		ModelAndView res = null;
		try {
			this.complaintService.delete(complaintId);
			res = new ModelAndView("redirect:/complaint/customer/list.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}
		return res;
	}
}
