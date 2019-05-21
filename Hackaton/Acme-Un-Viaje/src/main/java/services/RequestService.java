
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Customer;
import domain.Mailbox;
import domain.Request;
import domain.SocialProfile;
import repositories.RequestRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private Validator validator;

	@Autowired
	private CustomerService customerService;

	public Request create() {
		final Request request = new Request();
		UserAccount userL = LoginService.getPrincipal();
		Customer customer = this.customerService.getCustomerByUserAccountId(userL.getId());
		Assert.notNull(customer);
		request.setCustomer(customer);
		return request;
	}

	public Request save(final Request request) {
		return this.requestRepository.save(request);
	}

	public Request reconstruct(final Request request, final BindingResult binding) {
		final Request requestNew = this.create();

		this.check(request, binding);

		requestNew.setEndDate(request.getEndDate());
		requestNew.setMaxPrice(request.getMaxPrice());
		requestNew.setNumberOfPeople(request.getNumberOfPeople());
		requestNew.setStartDate(request.getStartDate());
		requestNew.setStatus(false);
		requestNew.setOrigin(request.getOrigin());
		requestNew.setDestination(request.getDestination());

		UserAccount userL = LoginService.getPrincipal();
		Customer customer = this.customerService.getCustomerByUserAccountId(userL.getId());
		Assert.notNull(customer);
		request.setCustomer(customer);

		this.validator.validate(requestNew, binding);


		if (binding.hasFieldErrors("numberOfPeople")) {
			binding.getModel().put("numberOfPeople", "patatoide");
		}
		return requestNew;
	}

	public Collection<Request> getCustomerRequest() {
		final int userLoggin = LoginService.getPrincipal().getId();
		final Customer actor = this.customerService.getCustomerByUserAccountId(userLoggin);
		Assert.isTrue(actor != null);
		return this.requestRepository.getCustomerRequest(actor.getId());
	}

	private void check(Request request, BindingResult binding) {

		if (request.getEndDate() != null && request.getStartDate() != null
				&& request.getStartDate().after(request.getEndDate())) {
			binding.rejectValue("endDate", "error.beforeDate");
		}
	}
	
	public void delete(final Request request) {
		Customer customer = this.customerService.getCustomerByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(customer);
		Assert.isTrue(request.getCustomer().equals(customer));
		Assert.isTrue(request.getStatus()==false);
		this.requestRepository.delete(request);
	}
	
	public Request findOne(final int id) {
		final Request result = this.requestRepository.findOne(id);
		return result;
	}


}
