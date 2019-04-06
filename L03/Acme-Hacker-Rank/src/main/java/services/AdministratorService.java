
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

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import forms.ActorForm;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	adminRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	public Administrator findOne(final int id) {

		return this.adminRepository.findOne(id);
	}

	public Administrator findOneByUserAccount(final int id) {

		return this.adminRepository.findOneByUserAccount(id);
	}

	private Administrator create() {

		Assert.isTrue(this.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);

		final Administrator res = new Administrator();

		final UserAccount uacc = new UserAccount();

		final List<Authority> auths = new ArrayList<>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.COMPANY);
		auths.add(auth);
		uacc.setAuthorities(auths);
		res.setUserAccount(uacc);

		return res;
	}

	private Boolean checkEmail(final Administrator admin) {

		Boolean res = true;
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";

		if (this.actorService.getActorByEmailE(admin.getEmail()) == null && this.actorService.getActorByEmail(admin.getEmail()).size() >= 1)
			res = false;
		else if (admin.getEmail().matches(pattern))
			res = false;

		return res;
	}

	public Administrator reconstruct(final ActorForm form, final BindingResult binding) {

		final Administrator res = this.create();

		res.setName(form.getName());
		res.setSurname(form.getSurname());
		res.setPhoto(form.getPhoto());
		res.setEmail(form.getEmail());
		res.setAddress(form.getAddress());
		res.setPhone(form.getPhone());

		System.out.println("valide1");

		if (form.getAccept() == false) {
			final ObjectError error = new ObjectError("accept", "You have to accepted the terms and condictions");
			binding.addError(error);
			binding.rejectValue("accept", "error.termsAndConditions");
		}

		System.out.println("valide2");

		if (form.getUserName().length() <= 5 && form.getUserName().length() <= 5) {
			final ObjectError error = new ObjectError("userName", "");
			binding.addError(error);
			binding.rejectValue("userName", "error.userAcount");
		}

		System.out.println("valide3");

		if (this.actorService.getActorByUser(form.getUserName()) != null) {
			final ObjectError error = new ObjectError("userName", "");
			binding.addError(error);
			binding.rejectValue("userName", "error.userName");
		}

		System.out.println("valide3");

		if (form.getConfirmPassword().length() <= 5 && form.getPassword().length() <= 5) {
			final ObjectError error = new ObjectError("password", "");
			binding.addError(error);
			binding.rejectValue("password", "error.password");
		}

		System.out.println("valide4");

		if (!form.getConfirmPassword().equals(form.getPassword())) {
			final ObjectError error = new ObjectError("password", "");
			binding.addError(error);
			binding.rejectValue("password", "error.password.confirm");
		}

		res.getUserAccount().setUsername(form.getUserName());

		final String password = form.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		res.getUserAccount().setPassword(hashPassword);

		System.out.println("valide todo");

		this.validator.validate(res, binding);

		return res;
	}

	public Administrator save(final Administrator admin) {

		Assert.isTrue(this.adminRepository.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);
		Assert.isTrue(this.checkEmail(admin), "error.email");
		return this.adminRepository.save(admin);
	}
}
