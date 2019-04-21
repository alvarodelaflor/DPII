
package services;

import java.util.ArrayList;
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
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.CreditCard;
import forms.ActorForm;

@Service
@Transactional
public class AdministratorService extends ActorService {

	@Autowired
	private AdministratorRepository	adminRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private ConfigurationService	configurationService;


	@Override
	public Administrator findOne(final int id) {

		return this.adminRepository.findOne(id);
	}

	public Administrator findOneByUserAccount(final int id) {

		return this.adminRepository.findOneByUserAccount(id);
	}

	public Administrator create() {

		Assert.isTrue(this.findOneByUserAccount(LoginService.getPrincipal().getId()) != null);

		final Administrator res = new Administrator();

		final UserAccount uacc = new UserAccount();

		final List<Authority> auths = new ArrayList<>();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		final CreditCard creditCard = new CreditCard();
		res.setCreditCard(creditCard);
		auths.add(auth);
		uacc.setAuthorities(auths);
		res.setUserAccount(uacc);

		return res;
	}

	// RECONSTRUCT-CREATE ---------------------------------------------------------------		
	public Administrator reconstructCreate(final ActorForm actorForm, final BindingResult binding) {
		final Administrator result = this.create();

		System.out.println("carmen: entron en el reconstructCreate");

		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setPhoto(actorForm.getPhoto());
		result.setEmail(actorForm.getEmail());
		result.setAddress(actorForm.getAddress());
		result.setPhone(actorForm.getPhone());

		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(actorForm.getCVV());
		creditCard.setExpiration(actorForm.getExpiration());
		creditCard.setHolder(actorForm.getHolder());
		creditCard.setMake(actorForm.getMake());
		creditCard.setNumber(actorForm.getNumber());

		result.setCreditCard(creditCard);

		//A�ADIDO

		if (!actorForm.getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}"))
			binding.rejectValue("expiration", "error.expirationFormatter");

		if (actorForm.getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}")) {
			final String[] parts = actorForm.getExpiration().split("/");
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

		//A�ADIDO

		if (actorForm.getAccept() == false) {
			final ObjectError error = new ObjectError("accept", "You have to accepted the terms and condictions");
			binding.addError(error);
			binding.rejectValue("accept", "error.termsAndConditions");
		}

		if (actorForm.getUserName().length() <= 5 && actorForm.getUserName().length() <= 5)
			//			final ObjectError error = new ObjectError("userName", "");
			//			binding.addError(error);
			binding.rejectValue("userName", "error.userAcount");

		if (this.actorRepository.getActorByUser(actorForm.getUserName()) != null)
			//			final ObjectError error = new ObjectError("userName", "");
			//			binding.addError(error);
			binding.rejectValue("userName", "error.userName");

		if (actorForm.getConfirmPassword().length() <= 5 && actorForm.getPassword().length() <= 5)
			//			final ObjectError error = new ObjectError("password", "");
			//			binding.addError(error);
			binding.rejectValue("password", "error.password");

		if (!actorForm.getConfirmPassword().equals(actorForm.getPassword()))
			//			final ObjectError error = new ObjectError("password", "");
			//			binding.addError(error);
			binding.rejectValue("password", "error.password.confirm");

		if (!actorForm.getNumber().matches("([0-9]){16}"))
			//			final ObjectError error = new ObjectError("number", "");
			//			binding.addError(error);
			binding.rejectValue("number", "error.numberCredictCard");

		if (!actorForm.getCVV().matches("([0-9]){3}"))
			//			final ObjectError error = new ObjectError("CVV", "");
			//			binding.addError(error);
			binding.rejectValue("CVV", "error.CVVCredictCard");

		if (actorForm.getHolder() == "")
			//			final ObjectError error = new ObjectError("holder", " ");
			//			binding.addError(error);
			binding.rejectValue("holder", "error.holderCredictCard");

		if (actorForm.getMake() == "")
			//			final ObjectError error = new ObjectError("make", " ");
			//			binding.addError(error);
			binding.rejectValue("make", "error.makeCredictCard");

		result.getUserAccount().setUsername(actorForm.getUserName());

		final String password = actorForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		result.getUserAccount().setPassword(hashPassword);

		System.out.println("valide todo");

		this.validator.validate(result, binding);

		return result;
	}

	// SAVE-CREATE ---------------------------------------------------------------		
	public Administrator saveCreate(final Administrator administrator) {
		Assert.isTrue(!this.checkEmailFormatter(administrator), "email.wrong");
		Assert.isTrue(this.checkEmail(administrator), "error.email");
		if (administrator.getPhone().matches("^([0-9]{4,})$"))
			administrator.setPhone(this.configurationService.getConfiguration().getCountryCode() + " " + administrator.getPhone());
		return this.adminRepository.save(administrator);
	}

	private Boolean checkEmailFormatter(final Administrator administrator) {
		Boolean res = true;
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		final String pattern2 = "(^((([a-zA-Z]|[0-9]){1,}[@])$)|(^(([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]>))$)";
		if (administrator.getEmail().matches(pattern) || administrator.getEmail().matches(pattern2))
			res = false;
		return res;
	}

	private Boolean checkEmail(final Administrator administrator) {
		Boolean res = false;
		if (this.actorRepository.getActorByEmail(administrator.getEmail()).size() < 1)
			res = true;
		return res;
	}
}
