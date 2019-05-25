
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.ComplaintRepository;
import security.Authority;
import utilities.CommonUtils;
import domain.Complaint;
import domain.Customer;

@Service
@Transactional
public class ComplaintService {

	@Autowired
	private ComplaintRepository	complaintRepository;

	@Autowired
	private CustomerService		customerService;


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
		final Complaint res = this.create();
		if (complaint.getId() == 0) {

		} else
			Assert.isTrue(this.isNotAssigned(complaint.getId()));

		return res;
	}

	public void save(final Complaint reconstructedComplaint) {
		reconstructedComplaint.setMoment(new Date());

		this.complaintRepository.save(reconstructedComplaint);
	}

	private boolean isNotAssigned(final int id) {
		// TODO: Finish
		return false;
	}
}
