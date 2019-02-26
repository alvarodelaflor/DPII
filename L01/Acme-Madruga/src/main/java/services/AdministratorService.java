
package services;

/*
 * CONTROL DE CAMBIOS AdministratorService.java
 * FRAN 19/02/2019 11:36 CREACIÓN DE LA CLASE
 */

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {

	//Managed Repository -------------------	
	@Autowired
	private AdministratorRepository	adminRepository;
	//Supporting services ------------------
	@Autowired
	private WelcomeService			welcomeService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private Validator				validator;


	//Simple CRUD Methods ------------------

	public Administrator create() {

		// "Check that an Admin is creating the new Admin´s Acc"
		final Administrator creatorAdmin = this.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin);
		// "New Admin Creation"
		final Administrator admin = new Administrator();
		final UserAccount acc = new UserAccount();
		final List<Authority> auths = new ArrayList<>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		auths.add(auth);
		acc.setAuthorities(auths);
		admin.setUserAccount(acc);
		// "Return new Admin"
		return admin;
	}

	public Administrator save(final Administrator admin) {

		Assert.isTrue(!this.checkEmail(admin), "email.error");
		Assert.isTrue(!this.checkUsername(admin), "username.error");

		// add +XX if the phone pattern needs it
		if (admin.getPhone().matches("^([0-9]{4,})$"))
			admin.setPhone("+" + this.welcomeService.getPhone() + " " + admin.getPhone());

		return this.adminRepository.save(admin);
	}

	private Boolean checkEmail(final Administrator admin) {
		Boolean res = true;
		if ((admin.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}") || (admin.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}") || (admin.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}(>)") || (admin.getEmail().matches(
			"[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}(>)") || this.actorService.getActorByEmail(admin.getEmail()) != null)))))
			res = false;
		return res;
	}

	private Boolean checkUsername(final Administrator admin) {
		Boolean res = true;
		final String username = admin.getUserAccount().getUsername();
		if (this.actorService.getActorByUsername(username) != null)
			res = false;
		return res;
	}

	public Administrator reconstruct(final AdministratorForm adminForm, final BindingResult binding) {

		final Administrator res = this.create();

		res.setId(0);
		res.setVersion(0);

		res.setPhoto(adminForm.getPhoto());

		res.setName(adminForm.getName());
		res.setMiddleName(adminForm.getMiddleName());
		res.setSurname(adminForm.getSurname());

		res.setEmail(adminForm.getEmail());
		res.setPhone(adminForm.getPhoto());
		res.setAddress(adminForm.getAddress());

		final UserAccount uacc = res.getUserAccount();
		uacc.setUsername(adminForm.getUsername());
		uacc.setPassword(adminForm.getPassword());

		this.validator.validate(res, binding);

		return res;
	}

	//Other Methods ------------------

	public Administrator findByUserAccountId(final int id) {

		final Administrator admin = this.adminRepository.findByUserAccountId(id);
		return admin;
	}
}
