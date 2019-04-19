
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
import repositories.CompanyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Company;
import domain.CreditCard;
import forms.RegistrationForm;

@Service
@Transactional
public class CompanyService {

	@Autowired
	private CompanyRepository		companyRepository;

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private ProblemService			problemService;

	@Autowired
	private ConfigurationService	configurationService;


	// CREATE ---------------------------------------------------------------		
	public Company create() {
		final Company company = new Company();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);

		final CreditCard creditCard = new CreditCard();
		company.setCreditCard(creditCard);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		company.setUserAccount(user);
		return company;
	}

	// SAVE-CREATE ---------------------------------------------------------------		
	public Company saveCreate(final Company company) {
		Assert.isTrue(!this.checkEmailFormatter(company), "email.wrong");
		Assert.isTrue(this.checkEmail(company), "error.email");
		if (company.getPhone().matches("^([0-9]{4,})$"))
			company.setPhone(this.configurationService.getConfiguration().getCountryCode() + " " + company.getPhone());
		return this.companyRepository.save(company);
	}

	private Boolean checkEmailFormatter(final Company company) {
		Boolean res = true;
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		if (company.getEmail().matches(pattern))
			res = false;
		return res;
	}

	private Boolean checkEmail(final Company company) {
		Boolean res = false;
		if (this.actorRepository.getActorByEmail(company.getEmail()).size() < 1)
			res = true;
		return res;
	}

	// RECONSTRUCT-CREATE ---------------------------------------------------------------		
	public Company reconstructCreate(final RegistrationForm registrationForm, final BindingResult binding) {
		final Company result = this.create();

		System.out.println("carmen: entron en el reconstructCreate");

		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setPhoto(registrationForm.getPhoto());
		result.setEmail(registrationForm.getEmail());
		result.setAddress(registrationForm.getAddress());
		result.setPhone(registrationForm.getPhone());
		result.setCommercialName(registrationForm.getCompanyName());

		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(registrationForm.getCVV());
		creditCard.setExpiration(registrationForm.getExpiration());
		creditCard.setHolder(registrationForm.getHolder());
		creditCard.setMake(registrationForm.getMake());
		creditCard.setNumber(registrationForm.getNumber());

		result.setCreditCard(creditCard);

		//AÑADIDO

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

			if (yearCredictCard < yearRigthNow || monthCreditCard < monthRigthNow || monthCreditCard == 00 || monthCreditCard > 12)
				binding.rejectValue("expiration", "error.expirationFuture");
		}

		//AÑADIDO

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
	// RECONSTRUCT-EDIT---------------------------------------------------------------		

	public Company reconstructEdit(final Company company, final BindingResult binding) {
		Company result;
		final Company res = this.companyRepository.findOne(company.getId());

		System.out.println("Carmen: entro en el reconstructEdict");

		result = company;

		System.out.println("Nombre: " + company.getName());
		result.setName(company.getName());
		System.out.println("Nombre: " + result.getName());

		result.setSurname(company.getSurname());
		result.setPhoto(company.getPhoto());
		result.setEmail(company.getEmail());
		result.setPhone(company.getPhone());
		result.setAddress(company.getAddress());
		result.setCommercialName(company.getCommercialName());

		System.out.println("Carmen: voy a validar");

		binding.addAllErrors(binding);

		System.out.println(result);

		this.validator.validate(company, binding);
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
	// QUERY ---------------------------------------------------------------	

	public Company getCompanyByUserAccountId(final int userAccountId) {
		Company res;
		res = this.companyRepository.findByUserAccountId(userAccountId);
		return res;
	}

	// SAVE-EDIT ---------------------------------------------------------------	

	public Company saveEdit(Company company) {
		Assert.isTrue(!this.checkEmailFormatter(company), "email.wrong");
		Assert.isTrue(this.checkEmailEdit(company), "error.email");
		System.out.println("hola");
		if (company.getPhone().matches("^([0-9]{4,})$"))
			company.setPhone(this.configurationService.getConfiguration().getCountryCode() + " " + company.getPhone());

		company = this.companyRepository.save(company);
		System.out.println(company);

		return company;
	}

	private Boolean checkEmailEdit(final Company company) {
		Boolean res = false;
		System.out.println(this.actorService.getActorByEmailE(company.getEmail()) == null);

		if (this.actorService.getActorByEmailE(company.getEmail()) == null && this.actorRepository.getActorByEmail(company.getEmail()).size() <= 1)
			res = true;
		return res;
	}
	// FINDONE ---------------------------------------------------------------	
	public Company findOne(final int id) {
		final Company company = this.companyRepository.findOne(id);
		return company;
	}

	// FINDALL  ---------------------------------------------------------------	
	public Collection<Company> findAll() {
		return this.companyRepository.findAll();
	}

	public void delete(final Company company) {
		Assert.isTrue(LoginService.getPrincipal().getId() == company.getUserAccount().getId());
		this.problemService.deleteCompanyProblems(company.getId());
		this.positionService.deleteCompanyPositions(company.getId());
		this.companyRepository.delete(company);
	}

	public void flush() {
		this.companyRepository.flush();
	}

}
