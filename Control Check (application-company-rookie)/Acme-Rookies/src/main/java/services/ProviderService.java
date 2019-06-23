
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Company;
import domain.CreditCard;
import domain.Provider;
import forms.RegistrationForm;

@Service
@Transactional
public class ProviderService {

	@Autowired
	private ProviderRepository		providerRepository;

	@Autowired
	private ItemService				itemService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private ConfigurationService	configurationService;
	
	@Autowired
	private SponsorshipService	sponsorshipRepository;
	
	@Autowired
	private ActorService actorService;


	// CREATE ---------------------------------------------------------------
	public Provider create() {
		final Provider provider = new Provider();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PROVIDER);

		final CreditCard creditCard = new CreditCard();
		provider.setCreditCard(creditCard);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		provider.setUserAccount(user);
		return provider;
	}

	// QUERY ---------------------------------------------------------------

	public Provider getProviderByUserAccountId(final int userAccountId) {
		Provider res;
		res = this.providerRepository.findByUserAccountId(userAccountId);
		return res;
	}

	// FINDONE ---------------------------------------------------------------
	public Provider findOne(final int id) {
		final Provider provider = this.providerRepository.findOne(id);
		return provider;
	}

	// FINDALL  ---------------------------------------------------------------
	public Collection<Provider> findAll() {
		return this.providerRepository.findAll();
	}

	//PROVIDER
	public void delete(final Provider provider) {
		Assert.isTrue(LoginService.getPrincipal().getId() == provider.getUserAccount().getId());
		this.itemService.deleteProviderItems(provider.getId());

		this.providerRepository.delete(provider);
	}

	public void flush() {
		this.providerRepository.flush();
	}

	// SAVE-CREATE ---------------------------------------------------------------		
	public Provider saveCreate(final Provider provider) {
		Assert.isTrue(!this.checkEmailFormatter(provider), "email.wrong");
		Assert.isTrue(this.checkEmail(provider), "error.email");
		if (provider.getPhone().matches("^([0-9]{4,})$"))
			provider.setPhone(this.configurationService.getConfiguration().getCountryCode() + " " + provider.getPhone());
		return this.providerRepository.save(provider);
	}

	private Boolean checkEmailFormatter(final Provider provider) {
		Boolean res = true;
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		if (provider.getEmail().matches(pattern))
			res = false;
		return res;
	}

	private Boolean checkEmail(final Provider provider) {
		Boolean res = false;
		if (this.actorRepository.getActorByEmail(provider.getEmail()).size() < 1)
			res = true;
		return res;
	}

	// RECONSTRUCT-CREATE ---------------------------------------------------------------		
	public Provider reconstructCreate(final RegistrationForm registrationForm, final BindingResult binding) {
		final Provider result = this.create();

		System.out.println("carmen: entron en el reconstructCreate");

		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setPhoto(registrationForm.getPhoto());
		result.setEmail(registrationForm.getEmail());
		result.setAddress(registrationForm.getAddress());
		result.setPhone(registrationForm.getPhone());
		result.setCommercialName(registrationForm.getCompanyName());
		result.setVatNumber(registrationForm.getVatNumber());

		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(registrationForm.getCVV());
		creditCard.setExpiration(registrationForm.getExpiration());
		creditCard.setHolder(registrationForm.getHolder());
		creditCard.setMake(registrationForm.getMake());
		creditCard.setNumber(registrationForm.getNumber());

		result.setCreditCard(creditCard);

		//ANADIDO

		if (!registrationForm.getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}"))
			binding.rejectValue("expiration", "error.expirationFormatter");

		if (registrationForm.getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}")) {
			final String[] parts = registrationForm.getExpiration().split("/");
			final String part1 = parts[0]; // MM
			final String part2 = parts[1]; // YY

			final int monthRigthNow = LocalDateTime.now().toDate().getMonth();
			final int monthCreditCard = Integer.parseInt(part1);

			int yearRigthNow = LocalDateTime.now().toDate().getYear();
			yearRigthNow = yearRigthNow % 100;
			final int yearCredictCard = Integer.parseInt(part2);

			System.out.println(monthCreditCard);
			System.out.println(monthRigthNow);
			System.out.println(yearCredictCard);
			System.out.println(yearRigthNow);

			System.out.println(yearCredictCard >= yearRigthNow);
			System.out.println(monthCreditCard > monthRigthNow);

			if (yearCredictCard < yearRigthNow || monthCreditCard == 00 || monthCreditCard > 12)
				binding.rejectValue("expiration", "error.expirationFuture");

			if (yearCredictCard >= yearRigthNow && monthCreditCard != 00 && monthCreditCard > 12)
				if (yearCredictCard == yearRigthNow)
					if (monthCreditCard < monthRigthNow)
						binding.rejectValue("expiration", "error.expirationFuture");
		}

		//ANADIDO

		if (registrationForm.getUserName().length() <= 5 && registrationForm.getUserName().length() <= 5)
			binding.rejectValue("userName", "error.userAcount");
		//			final ObjectError error = new ObjectError("userName", "Invalid size");
		//			binding.addError(error);

		if (this.actorRepository.getActorByUser(registrationForm.getUserName()) != null)
			//			final ObjectError error = new ObjectError("userName", "Must be blank");
			//			binding.addError(error);
			binding.rejectValue("userName", "error.userName");

		if (registrationForm.getConfirmPassword().length() <= 5 && registrationForm.getPassword().length() <= 5)
			//			final ObjectError error = new ObjectError("password", "Invalid size");
			//			binding.addError(error);
			binding.rejectValue("password", "error.password");

		if (!registrationForm.getConfirmPassword().equals(registrationForm.getPassword()))
			//			final ObjectError error = new ObjectError("password", "Not equal");
			//			binding.addError(error);
			binding.rejectValue("password", "error.password.confirm");

		if (registrationForm.getCompanyName() == "")
			//			final ObjectError error = new ObjectError("companyName", "Must be blank");
			//			binding.addError(error);
			binding.rejectValue("companyName", "error.companyName");

		if (registrationForm.getCompanyName() != "")
			if (registrationForm.getCompanyName().contains("<") || registrationForm.getCompanyName().contains(">"))
				binding.rejectValue("companyName", "error.html");

		if (!registrationForm.getNumber().matches("([0-9]){16}"))
			//			final ObjectError error = new ObjectError("number", "Invalid number");
			//			binding.addError(error);
			binding.rejectValue("number", "error.numberCredictCard");

		if (!registrationForm.getCVV().matches("([0-9]){3}"))
			//			final ObjectError error = new ObjectError("CVV", "Invalid CVV");
			//			binding.addError(error);
			binding.rejectValue("CVV", "error.CVVCredictCard");

		if (registrationForm.getHolder() == "")
			//			final ObjectError error = new ObjectError("holder", "Must be blank");
			//			binding.addError(error);
			binding.rejectValue("holder", "error.holderCredictCard");

		if (registrationForm.getMake() == "")
			//			final ObjectError error = new ObjectError("make", "Must be blank");
			//			binding.addError(error);
			binding.rejectValue("make", "error.makeCredictCard");

		if (registrationForm.getAccept() == false) {
			final ObjectError error = new ObjectError("accept", "You have to accept terms and condictions");
			binding.addError(error);
			binding.rejectValue("accept", "error.termsAndConditions");
		}

		result.getUserAccount().setUsername(registrationForm.getUserName());

		final String password = registrationForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		result.getUserAccount().setPassword(hashPassword);

		System.out.println("valide todo");

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<Provider> sponsorshipProvider() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		final Collection<Provider> res = new ArrayList<>();

		final List<Object[]> collection = this.providerRepository.sponsorshipProvider();

		for (int i = 0; i < collection.size(); i++) {

			final Provider c = (Provider) collection.get(i)[0];

			final Double valor = (Double) collection.get(i)[1];
			if (valor > this.sponsorshipRepository.avgSponsorshipPerProvider())
				res.add(c);
		}
		System.out.println(res); 

		return res;
	}
	
	public Collection<Provider> ProviderItem() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		final Collection<Provider> res = new ArrayList<>();

		final List<Object[]> collection = this.providerRepository.ProvidersPerNumberItem();

		for (int i = 0; i < collection.size(); i++) {

			final Provider c = (Provider) collection.get(i)[0];
			 
			res.add(c);
			
			
		}
		System.out.println(res); 

		return res;
	}
	
	// RECONSTRUCT-EDIT---------------------------------------------------------------		

	public Provider reconstructEdit(final Provider provider, final BindingResult binding) {
		Provider result;
		final Provider res = this.providerRepository.findOne(provider.getId());

		System.out.println("Carmen: entro en el reconstructEdict");

		result = provider;

		System.out.println(res.getVatNumber());

		System.out.println("Nombre: " + provider.getName());
		result.setName(provider.getName());
		System.out.println("Nombre: " + result.getName());

		result.setSurname(provider.getSurname());
		result.setPhoto(provider.getPhoto());
		result.setEmail(provider.getEmail());
		result.setPhone(provider.getPhone());
		result.setAddress(provider.getAddress());
		result.setCommercialName(provider.getCommercialName());
		result.setVatNumber(res.getVatNumber());

		System.out.println("Carmen: voy a validar");

		binding.addAllErrors(binding);

		System.out.println(result);

		this.validator.validate(provider, binding);
		System.out.println(binding.getAllErrors());

		if (binding.getAllErrors().isEmpty()) {
			res.setSurname(result.getSurname());
			res.setPhoto(result.getPhoto());
			res.setEmail(result.getEmail());
			res.setPhone(result.getPhone());
			res.setAddress(result.getAddress());
			res.setCommercialName(result.getCommercialName());
			res.setName(result.getName());
		}

		return res;
	}

	// SAVE-EDIT ---------------------------------------------------------------	

	public Provider saveEdit(Provider provider) {
		Assert.isTrue(!this.checkEmailFormatter(provider), "email.wrong");
		Assert.isTrue(this.checkEmailEdit(provider), "error.email");
		System.out.println("hola");
		if (provider.getPhone().matches("^([0-9]{4,})$")) {
			final String phoneM = provider.getPhone();
			provider.setPhone(phoneM);
			provider.setPhone(this.configurationService.getConfiguration().getCountryCode() + " " + provider.getPhone());
		}
		provider = this.providerRepository.save(provider);
		System.out.println(provider);

		return provider;
	}

	private Boolean checkEmailEdit(final Provider provider) {
		Boolean res = false;
		System.out.println(this.actorService.getActorByEmailE(provider.getEmail()) == null);

		if (this.actorService.getActorByEmailE(provider.getEmail()) == null && this.actorRepository.getActorByEmail(provider.getEmail()).size() <= 1)
			res = true;
		return res;
	}

}


