
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
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
		if (this.actorService.getActorByEmailE(admin.getEmail()) == null && this.actorService.getActorByEmail(admin.getEmail()).size() >= 1)
			res = false;

		return res;
	}

	public Administrator reconstruct(final ActorForm form, final BindingResult binding) {

		Assert.isTrue(form.getAccept(), "form.accept.error");
		Assert.isTrue(form.getPassword().length() >= 5 && form.getPassword().length() <= 32, "form.length.error");
		Assert.isTrue(form.getPassword() == form.getConfirmPassword(), "form.confirmPass.error");
		Assert.isTrue(form.getUserName().length() >= 5 && form.getUserName().length() <= 32, "form.length.error");
		Assert.isTrue(this.actorService.getActorByUser(form.getUserName()) == null, "form.actor.already.exists.error");

		final Administrator res = this.create();

		res.setPhoto(form.getPhoto());
		res.setName(form.getName());
		res.setSurname(form.getSurname());

		res.setAddress(form.getAddress());
		res.setPhone(form.getPhone());
		res.setEmail(form.getEmail());

		Assert.isTrue(this.checkEmail(res));

		res.getUserAccount().setUsername(form.getUserName());
		final String pass = form.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPass = encoder.encodePassword(pass, null);
		res.getUserAccount().setPassword(hashPass);

		this.validator.validate(res, binding);

		return res;
	}

	public Administrator save(final Administrator admin) {

		Assert.isTrue(this.adminRepository.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);
		return this.adminRepository.save(admin);
	}
}
