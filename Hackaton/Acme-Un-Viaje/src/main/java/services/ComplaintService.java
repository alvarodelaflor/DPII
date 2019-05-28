
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ComplaintRepository;
import security.Authority;
import utilities.CommonUtils;
import domain.Complaint;
import domain.Customer;
import domain.TravelPack;

@Service
public class ComplaintService {

	@Autowired
	private ComplaintRepository	complaintRepository;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private TravelPackService	travelPackService;

	@Autowired
	private TravelAgencyService	travelAgencyService;

	@Autowired
	private HostService			hostService;

	@Autowired
	private TransporterService	transporterService;

	@Autowired
	private Validator			validator;


	public Collection<Complaint> getLoggedCustomerAssignedComplaints() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		final Customer c = this.customerService.getLoggedCustomer();
		return this.complaintRepository.getLoggedCustomerAssignedComplaints(c.getId());
	}

	public Collection<Complaint> getLoggedCustomerUnassignedComplaints() {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		final Customer c = this.customerService.getLoggedCustomer();
		return this.complaintRepository.getLoggedCustomerUnassignedComplaints(c.getId());
	}

	public Complaint getLoggedCustomerComplaint(final Integer complaintId) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		final Customer c = this.customerService.getLoggedCustomer();
		return this.complaintRepository.getLoggedCustomerComplaint(c.getId(), complaintId);
	}

	public Complaint create() {
		final Complaint res = new Complaint();
		return res;
	}

	public Complaint reconstruct(final Complaint complaint, final BindingResult binding) {
		final Complaint res = complaint;
		if (complaint.getId() != 0) {
			final Complaint dbComplaint = this.complaintRepository.findOne(res.getId());
			Assert.isTrue(this.isAssigned(res.getId()) == false);
			res.setId(dbComplaint.getId());
			res.setVersion(dbComplaint.getVersion());

			res.setTravelAgency(dbComplaint.getTravelAgency());
			res.setHost(dbComplaint.getHost());
			res.setTransporter(dbComplaint.getTransporter());
			res.setCustomer(dbComplaint.getCustomer());
		} else
			res.setCustomer(this.customerService.getLoggedCustomer());

		res.setMoment(new Date());

		this.validator.validate(res, binding);
		System.out.println(binding.getFieldErrors());
		return res;
	}
	public void save(final Complaint reconstructedComplaint, final Integer travelPackId) {
		final Complaint c = this.complaintRepository.save(reconstructedComplaint);
		if (travelPackId != null)
			this.travelPackService.findOne(travelPackId).getComplaints().add(c);
	}

	public void saveWithoutSetMoment(final Complaint reconstructedComplaint) {
		this.complaintRepository.save(reconstructedComplaint);
	}

	private boolean isAssigned(final int complaintId) {
		return this.complaintRepository.findOne(complaintId).getReview() != null;
	}

	public void delete(final int complaintId) {
		Assert.isTrue(CommonUtils.hasAuthority(Authority.CUSTOMER));
		Assert.isTrue(this.isAssigned(complaintId) == false);
		final TravelPack tp = this.travelPackService.findFromComplaint(complaintId);
		tp.getComplaints().remove(this.complaintRepository.findOne(complaintId));
		this.complaintRepository.delete(complaintId);
	}

	public Collection<Complaint> getComplaintsWithoutReview() {

		return this.complaintRepository.getComplaintsWithoutReview();
	}

	public Complaint findOne(final Integer complaintId) {
		return this.complaintRepository.findOne(complaintId);
	}

	public Complaint getComplaintOfReview(final int reviewId) {
		final Complaint res = this.complaintRepository.getComplaintOfReview(reviewId);
		return res;
	}

}
