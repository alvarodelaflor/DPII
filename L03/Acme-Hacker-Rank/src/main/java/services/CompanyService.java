
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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
import security.UserAccount;
import domain.Company;
import forms.RegistrationForm;

@Service
@Transactional
public class CompanyService {

	@Autowired
	private CompanyRepository	companyRepository;

	@Autowired
	private ActorRepository		actorRepository;

	@Autowired
	private Validator			validator;


	// CREATE ---------------------------------------------------------------		
	public Company create() {
		final Company company = new Company();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.COMPANY);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		company.setUserAccount(user);
		return company;
	}

	// SAVE-CREATE ---------------------------------------------------------------		
	public Company saveCreate(final Company company) {
		Assert.isTrue(!this.checkEmailFormatter(company), "email.wrong");
		Assert.isTrue(this.checkEmail(company), "error.email");
		//		if (member.getPhone().matches("^([0-9]{4,})$"))
		//			member.setPhone("+" + this.welcomeService.getPhone() + " " + member.getPhone());
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
		if (this.actorRepository.getActorByEmail(company.getEmail()) == null)
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

		System.out.println("valide1");

		if (registrationForm.getAccept() == false) {
			final ObjectError error = new ObjectError("accept", "You have to accepted the terms and condictions");
			binding.addError(error);
			binding.rejectValue("accept", "error.termsAndConditions");
		}

		System.out.println("valide2");

		if (registrationForm.getUserName().length() <= 5 && registrationForm.getUserName().length() <= 5) {
			final ObjectError error = new ObjectError("userName", "");
			binding.addError(error);
			binding.rejectValue("userName", "error.userAcount");
		}

		System.out.println("valide3");

		if (this.actorRepository.getActorByUser(registrationForm.getUserName()) != null) {
			final ObjectError error = new ObjectError("userName", "");
			binding.addError(error);
			binding.rejectValue("userName", "error.userName");
		}

		System.out.println("valide3");

		if (registrationForm.getConfirmPassword().length() <= 5 && registrationForm.getPassword().length() <= 5) {
			final ObjectError error = new ObjectError("password", "");
			binding.addError(error);
			binding.rejectValue("password", "error.password");
		}

		System.out.println("valide4");

		if (!registrationForm.getConfirmPassword().equals(registrationForm.getPassword())) {
			final ObjectError error = new ObjectError("password", "");
			binding.addError(error);
			binding.rejectValue("password", "error.password.confirm");
		}

		if (registrationForm.getCompanyName() == "") {
			final ObjectError error = new ObjectError("companyName", "");
			binding.addError(error);
			binding.rejectValue("companyName", "error.companyName");
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

		result = this.companyRepository.findOne(company.getId());

		System.out.println(result.getName());

		result.setName(company.getName());
		result.setSurname(company.getSurname());
		result.setPhoto(company.getPhoto());
		result.setEmail(company.getEmail());
		result.setPhone(company.getPhone());
		result.setAddress(company.getAddress());
		result.setCommercialName(company.getCommercialName());
		result.setId(company.getId());
		result.setVersion(company.getId());

		this.validator.validate(company, binding);
		System.out.println(binding.getAllErrors());
		return result;
	}

	// QUERY ---------------------------------------------------------------	

	public Company getCompanyByUserAccountId(final int userAccountId) {
		Company res;
		res = this.companyRepository.findByUserAccountId(userAccountId);
		return res;
	}

	// SAVE-EDIT ---------------------------------------------------------------	

	public Company saveEdit(Company company) {
		Assert.isTrue(!this.checkEmail(company), "error.email");
		Assert.isTrue(!this.checkEmailFormatter(company), "email.wrong");
		//		if (company.getPhone().matches("^([0-9]{4,})$"))
		//			company.setPhone("+" + //COMPLETAR//+ " " + company.getPhone());		
		company = this.companyRepository.save(company);
		System.out.println(company);
		return company;
	}

	// FINDONE ---------------------------------------------------------------	
	public Company findOne(final int id) {
		final Company company = this.companyRepository.findOne(id);
		return company;
	}

}
