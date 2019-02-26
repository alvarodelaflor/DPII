
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import forms.RegistrationForm;

@Service
public class AdministratorService {

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private WelcomeService			welcomeService;


	public Administrator reconstructR(final RegistrationForm registrationForm, final BindingResult binding) {
		final Administrator result = this.create();

		result.setId(0);
		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setPhoto(registrationForm.getPhoto());
		result.setEmail(registrationForm.getEmail());
		result.setAddress(registrationForm.getAddress());
		result.setMiddleName(registrationForm.getMiddleName());
		result.setPhone(registrationForm.getPhone());

		result.getUserAccount().setUsername(registrationForm.getUserName());

		final String password = registrationForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		result.getUserAccount().setPassword(hashPassword);

		if (registrationForm.getAccept() == false) {
			final ObjectError error = new ObjectError("accept", "You have to accepted the terms and condictions");
			binding.addError(error);
			binding.rejectValue("accept", "error.termsAndConditions");
		}

		if (registrationForm.getUserName().length() <= 5 && registrationForm.getUserName().length() <= 5) {
			final ObjectError error = new ObjectError("userName", "");
			binding.addError(error);
			binding.rejectValue("userName", "error.userAcount");
		}

		if (registrationForm.getConfirmPassword().length() <= 5 && registrationForm.getPassword().length() <= 5) {
			final ObjectError error = new ObjectError("password", "");
			binding.addError(error);
			binding.rejectValue("password", "error.userAcount");
		}

		if (!registrationForm.getConfirmPassword().equals(registrationForm.getPassword())) {
			final ObjectError error = new ObjectError("password", "");
			binding.addError(error);
			binding.rejectValue("password", "error.password");
		}

		result.setVersion(0);

		this.validator.validate(result, binding);
		return result;
	}

	public Administrator reconstruct(final Administrator admin, final BindingResult binding) {
		Administrator result;

		if (admin.getId() == 0)
			result = admin;
		else {
			result = this.administratorRepository.findOne(admin.getId());

			result.setName(admin.getName());
			result.setSurname(admin.getSurname());
			result.setPhoto(admin.getPhoto());
			result.setEmail(admin.getEmail());

			this.validator.validate(result, binding);
		}
		return result;
	}

	public Administrator create() {
		final Administrator member = new Administrator();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		member.setUserAccount(user);
		return member;
	}

	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final int id) {
		final Administrator member = this.administratorRepository.findOne(id);
		return member;
	}

	public Administrator save(final Administrator member) {
		Assert.isTrue(!this.checkEmail(member), "email.wrong");
		if (member.getPhone().matches("^([0-9]{4,})$"))
			member.setPhone("+" + this.welcomeService.getPhone() + " " + member.getPhone());
		return this.administratorRepository.save(member);
	}

	private Boolean checkEmail(final Administrator member) {
		Boolean res = true;
		if ((member.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}") || (member.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}") || (member.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}(>)") || (member.getEmail().matches(
			"[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}(>)") || this.actorService.getActorByEmail(member.getEmail()) != null)))))
			res = false;
		return res;
	}

	public Administrator update(final Administrator member) {
		Assert.isTrue(LoginService.getPrincipal().getId() == member.getUserAccount().getId());
		return this.administratorRepository.save(member);
	}

	public Administrator getAdministratorByUserAccountId(final int userAccountId) {
		Administrator res;
		res = this.administratorRepository.findByUserAccountId(userAccountId);
		return res;
	}

}
