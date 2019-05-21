
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import domain.Customer;
import domain.Request;
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

		UserAccount userL = LoginService.getPrincipal();
		Customer customer = this.customerService.getCustomerByUserAccountId(userL.getId());
		Assert.notNull(customer);
		request.setCustomer(customer);

		this.validator.validate(requestNew, binding);

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

		if (request.getNumberOfPeople() > 0) {
			String cadena = Integer.toString(request.getNumberOfPeople());
			if (!cadena.matches("([0-9])") && cadena != " ") {
				binding.rejectValue("numberOfPeople", "error.number");
			}
		}
		
		if (request.getMaxPrice() != null && request.getMaxPrice() > 0) {
			String cadena = Double.toString(request.getMaxPrice());
			if (!cadena.matches("[0-9]{1,}[.][0-9]{1,}")) {
				binding.rejectValue("maxPrice", "error.number");
			}
		}
	}

}
