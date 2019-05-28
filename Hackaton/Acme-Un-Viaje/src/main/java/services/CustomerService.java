
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.CreditCard;
import domain.Customer;
import forms.RegisterActorE;
import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Customer;
import domain.TravelPack;
import forms.RegisterActorE;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository		customerRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private TravelPackService	travelPackService;

	@Autowired
	private ConfigService		configService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private RequestService			requestService;

	@Autowired
	private ComplaintService		complaintService;

	@Autowired
	private TravelPackService		travelPackService;

	@Autowired
	private ValorationService		valorationService;


	// REGISTER AS CLEANER
	// ---------------------------------------------------------------
	public Customer create() {
		final Customer customer = new Customer();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);

		autoridades.add(authority);
		user.setAuthorities(autoridades);
		customer.setUserAccount(user);
		return customer;
	}

	// SAVE REGISTER AS CLEANER
	// ---------------------------------------------------------------
	public Customer saveRegisterAsCustomer(final Customer customer) {
		if (customer.getPhone().matches("^([0-9]{4,})$"))
			customer.setPhone("+" + this.configService.getConfiguration().getDefaultPhoneCode() + " " + customer.getPhone());
		return this.customerRepository.save(customer);
	}

	// RECONSTRUCT REGISTER AS CLEANER
	// ---------------------------------------------------------------
	public Customer reconstructRegisterAsCustomer(final RegisterActorE registerActorE, final BindingResult binding) {
		final Customer customer = this.create();

		this.actorService.checkActor(registerActorE, binding);

		customer.getUserAccount().setUsername(registerActorE.getUserName());
		final String password = registerActorE.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		customer.getUserAccount().setPassword(hashPassword);
		customer.setBirthDate(registerActorE.getBirthDate());
		customer.setEmail(registerActorE.getEmail());
		customer.setPhone(registerActorE.getPhone());
		customer.setName(registerActorE.getName());
		customer.setSurname(registerActorE.getSurname());
		customer.setPhoto(registerActorE.getPhoto());
		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(registerActorE.getCVV());
		creditCard.setExpiration(registerActorE.getExpiration());
		creditCard.setHolder(registerActorE.getHolder());
		creditCard.setMake(registerActorE.getMake());
		creditCard.setNumber(registerActorE.getNumber());
		customer.setCreditCard(creditCard);
		customer.setCity(registerActorE.getCity());

		this.validator.validate(customer, binding);
		return customer;
	}

	// FIND CLEANER
	// ------------------------------------------------------------------------------------
	public Customer getCustomerByUserAccountId(final int userAccountId) {
		return this.customerRepository.findByUserAccountId(userAccountId);
	}

	// RECONSTRUCT EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public Customer reconstructEditDataPeronal(final Customer registerActorE, final BindingResult binding) {
		Customer customer;

		customer = this.customerRepository.findOne(registerActorE.getId());

		this.checkActorEdit(registerActorE, binding);
		this.validator.validate(registerActorE, binding);

		if (!binding.hasErrors()) {
			customer.setBirthDate(registerActorE.getBirthDate());
			customer.setEmail(registerActorE.getEmail());
			customer.setPhone(registerActorE.getPhone());
			customer.setName(registerActorE.getName());
			customer.setSurname(registerActorE.getSurname());
			customer.setPhoto(registerActorE.getPhoto());
			final CreditCard creditCard = customer.getCreditCard();
			creditCard.setCVV(registerActorE.getCreditCard().getCVV());
			creditCard.setExpiration(registerActorE.getCreditCard().getExpiration());
			creditCard.setHolder(registerActorE.getCreditCard().getHolder());
			creditCard.setMake(registerActorE.getCreditCard().getMake());
			creditCard.setNumber(registerActorE.getCreditCard().getNumber());
			customer.setCreditCard(creditCard);
			customer.setCity(registerActorE.getCity());
		}

		return customer;
	}

	// CHECK EDIT DATA PERONAL
	// ---------------------------------------------------------------
	public void checkActorEdit(final Customer registerActorE, final BindingResult binding) {

		if (!registerActorE.getCreditCard().getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}"))
			binding.rejectValue("creditCard.expiration", "error.expirationFormatter");

		final Calendar calendar = Calendar.getInstance();
		if (registerActorE.getCreditCard().getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}")) {
			final String[] parts = registerActorE.getCreditCard().getExpiration().split("/");
			final String part1 = parts[0]; // MM
			final String part2 = parts[1]; // YY

			final int monthRigthNow = calendar.getTime().getMonth();
			final int monthCreditCard = Integer.parseInt(part1);

			int yearRigthNow = calendar.getTime().getYear();
			yearRigthNow = yearRigthNow % 100;
			final int yearCredictCard = Integer.parseInt(part2);

			System.out.println(yearCredictCard >= yearRigthNow);
			System.out.println(monthCreditCard > monthRigthNow);

			if (yearCredictCard < yearRigthNow || monthCreditCard == 00 || monthCreditCard > 12)
				binding.rejectValue("creditCard.expiration", "error.expirationFuture");

			if (yearCredictCard >= yearRigthNow && monthCreditCard != 00 && monthCreditCard > 12)
				if (yearCredictCard == yearRigthNow)
					if (monthCreditCard < monthRigthNow)
						binding.rejectValue("creditCard.expiration", "error.expirationFuture");
		}

		if (registerActorE.getCreditCard().getHolder().contains(">") || registerActorE.getCreditCard().getHolder().contains("<"))
			binding.rejectValue("creditCard.holder", "error.html");

		if (registerActorE.getCreditCard().getMake().contains(">") || registerActorE.getCreditCard().getMake().contains("<"))
			binding.rejectValue("creditCard.make", "error.html");

		if (!registerActorE.getCreditCard().getNumber().matches("([0-9]){16}"))
			binding.rejectValue("creditCard.number", "error.numberCredictCard");

		if (!registerActorE.getCreditCard().getCVV().matches("([0-9]){3}"))
			binding.rejectValue("creditCard.CVV", "error.CVVCredictCard");

		if (registerActorE.getCreditCard().getHolder() == "")
			binding.rejectValue("creditCard.holder", "error.holderCredictCard");

		if (registerActorE.getCreditCard().getMake() == "")
			binding.rejectValue("creditCard.make", "error.makeCredictCard");

		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		if (!registerActorE.getEmail().matches(pattern))
			binding.rejectValue("email", "email.wrong");

		final UserAccount user = LoginService.getPrincipal();
		final Customer t = this.getCustomerByUserAccountId(user.getId());

		if (!registerActorE.getEmail().equals(t.getEmail()) && this.actorService.getActorByEmail(registerActorE.getEmail()).size() >= 1)
			binding.rejectValue("email", "error.email");

		if (registerActorE.getBirthDate() != null && registerActorE.getBirthDate().after(calendar.getTime())) {
			binding.rejectValue("birthDate", "error.birthDate");
			final Integer ageActor = calendar.getTime().getYear() - registerActorE.getBirthDate().getYear();
			if (ageActor < 18)
				binding.rejectValue("birthDate", "error.birthDateM");
		}
	}

	public Customer findOne(final int customerId) {
		return this.customerRepository.findOne(customerId);
	}

	public Customer getLoggedCustomer() {
		return this.customerRepository.findByUserAccountId(LoginService.getPrincipal().getId());
	}

	public void delete(final Customer customer) {
		Assert.isTrue(customer.getUserAccount().getId() == LoginService.getPrincipal().getId());
		this.socialProfileService.deleteActorSocialProfiles(customer);
		this.requestService.deleteCustomerRequests(customer);
		this.complaintService.deleteCustomerComplaints(customer);
		this.travelPackService.deleteCustomerTravelPacks(customer);
		this.valorationService.deleteAllByCustomer(customer);
		this.customerRepository.delete(customer);
	}
	public Collection<Customer> getCustomersByAccomodationId(final int id) {

		final Collection<Customer> res = new ArrayList<>();

		final Collection<TravelPack> travelPacks = this.travelPackService.getTravelPacksAccomodationId(id);
		for (final TravelPack travelPack : travelPacks)
			res.add(travelPack.getCustomer());

		return res;
	}

	public List<Customer> getCustomersByTranspoterId(final int id) {

		final List<Customer> res = new ArrayList<>();
		res.addAll(this.customerRepository.getCustomersByTransporterId(id));
		return res;
	}

	public List<String> bestCustomer() {

		final List<String> res = new ArrayList<>();
		final List<Customer> customers = new ArrayList<>();
		customers.addAll(this.customerRepository.bestCustomer());
		for (final Customer customer : customers)
			res.add(customer.getUserAccount().getUsername());
		if (customers.size() <= 3)
			return res;
		else
			return res.subList(0, 2);
	}

	public List<Customer> getCustomersByHostId(final int id) {

		final List<Customer> res = new ArrayList<>();
		res.addAll(this.customerRepository.getCustomersByHostId(id));
		return res;
	}
}
