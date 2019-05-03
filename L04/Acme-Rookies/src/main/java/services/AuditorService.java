
package services;

import java.util.ArrayList;
import java.util.Calendar;
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
import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Audit;
import domain.Auditor;
import domain.CreditCard;
import forms.ActorForm;

@Service
@Transactional
public class AuditorService {

	@Autowired
	private AuditorRepository		auditorRepository;
	@Autowired
	private ActorRepository			actorRepository;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private Validator				validator;


	// CRUD METHODS

	/**
	 * Creates an empty auditor
	 * 
	 * @return {@link Auditor}
	 */
	public Auditor create() {
		final Auditor auditor = new Auditor();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.AUDITOR);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		auditor.setUserAccount(user);
		final CreditCard creditCard = new CreditCard();
		auditor.setCreditCard(creditCard);
		return auditor;
	}

	/**
	 * Saves an auditor in the database (Copied from RookieService)
	 * 
	 * @param auditor
	 * @return {@link Auditor}
	 */
	public Auditor saveCreate(final Auditor rookie) {
		Assert.isTrue(!this.checkEmailFormatter(rookie), "email.wrong");
		Assert.isTrue(this.checkEmail(rookie), "error.email");
		if (rookie.getPhone().matches("^([0-9]{4,})$"))
			rookie.setPhone(this.configurationService.getConfiguration().getCountryCode() + " " + rookie.getPhone());

		return this.auditorRepository.save(rookie);
	}

	// RECONSTRUCT-CREATE ---------------------------------------------------------------		
	public Auditor reconstructCreate(final ActorForm actorForm, final BindingResult binding) {
		final Auditor result = this.create();

		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setPhoto(actorForm.getPhoto());
		result.setEmail(actorForm.getEmail());
		result.setAddress(actorForm.getAddress());
		result.setPhone(actorForm.getPhone());
		result.setVatNumber(actorForm.getVatNumber());

		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(actorForm.getCVV());
		creditCard.setExpiration(actorForm.getExpiration());
		creditCard.setHolder(actorForm.getHolder());
		creditCard.setMake(actorForm.getMake());
		creditCard.setNumber(actorForm.getNumber());

		result.setCreditCard(creditCard);

		this.checkErrors(actorForm, binding);

		result.getUserAccount().setUsername(actorForm.getUserName());

		final String password = actorForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		result.getUserAccount().setPassword(hashPassword);

		this.validator.validate(result, binding);

		return result;
	}

	/**
	 * Get an auditor by auditorId
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit}
	 */
	public Auditor findOne(final int auditorId) {
		return this.auditorRepository.findOne(auditorId);
	}

	// CRUD METHODS

	// AUXILIAR METHODS

	/**
	 * Get an auditor by useraccount ID.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit}
	 */
	public Auditor getAuditorByUserAccountId(final int userAccountId) {
		return this.auditorRepository.getAuditorByUserAccountId(userAccountId);
	}

	/**
	 * Get the auditor login.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Audit} if is login, null otherwise.
	 */
	public Auditor getAuditorLogin() {
		Auditor res;
		try {
			final int aux = LoginService.getPrincipal().getId();
			res = this.getAuditorByUserAccountId(aux);
		} catch (final Exception e) {
			res = null;
		}
		return res;
	}

	// AUXILIAR METHODS
	private Boolean checkEmailFormatter(final Auditor auditor) {
		Boolean res = true;
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		if (auditor.getEmail().matches(pattern))
			res = false;
		return res;
	}

	private Boolean checkEmail(final Auditor auditor) {
		Boolean res = false;
		if (this.actorRepository.getActorByEmail(auditor.getEmail()).size() < 1)
			res = true;
		return res;
	}

	private void checkErrors(final ActorForm actorForm, final BindingResult binding) {
		if (!actorForm.getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}"))
			binding.rejectValue("expiration", "error.expirationFormatter");

		if (actorForm.getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}")) {
			final String[] parts = actorForm.getExpiration().split("/");
			final String part1 = parts[0]; // MM
			final String part2 = parts[1]; // YY

			final Calendar calendar = Calendar.getInstance();
			final int monthRigthNow = calendar.getTime().getMonth();
			final int monthCreditCard = Integer.parseInt(part1);

			int yearRigthNow = calendar.getTime().getYear();
			yearRigthNow = yearRigthNow % 100;
			final int yearCredictCard = Integer.parseInt(part2);

			System.out.println(yearCredictCard >= yearRigthNow);
			System.out.println(monthCreditCard > monthRigthNow);

			if (yearCredictCard < yearRigthNow || monthCreditCard == 00 || monthCreditCard > 12)
				binding.rejectValue("expiration", "error.expirationFuture");

			if (yearCredictCard >= yearRigthNow && monthCreditCard != 00 && monthCreditCard > 12)
				if (yearCredictCard == yearRigthNow)
					if (monthCreditCard < monthRigthNow)
						binding.rejectValue("expiration", "error.expirationFuture");
		}

		if (actorForm.getUserName().length() <= 5 && actorForm.getUserName().length() <= 5)
			binding.rejectValue("userName", "error.userAcount");
		// final ObjectError error = new ObjectError("userName", "Invalid size");
		// binding.addError(error);

		if (this.actorRepository.getActorByUser(actorForm.getUserName()) != null)
			// final ObjectError error = new ObjectError("userName", "Must be blank");
			// binding.addError(error);
			binding.rejectValue("userName", "error.userName");

		if (actorForm.getConfirmPassword().length() <= 5 && actorForm.getPassword().length() <= 5)
			// final ObjectError error = new ObjectError("password", "Invalid size");
			// binding.addError(error);
			binding.rejectValue("password", "error.password");

		if (!actorForm.getConfirmPassword().equals(actorForm.getPassword()))
			// final ObjectError error = new ObjectError("password", "Not equal");
			// binding.addError(error);
			binding.rejectValue("password", "error.password.confirm");

		if (!actorForm.getNumber().matches("([0-9]){16}"))
			// final ObjectError error = new ObjectError("number", "Invalid number");
			// binding.addError(error);
			binding.rejectValue("number", "error.numberCredictCard");

		if (!actorForm.getCVV().matches("([0-9]){3}"))
			// final ObjectError error = new ObjectError("CVV", "Invalid CVV");
			// binding.addError(error);
			binding.rejectValue("CVV", "error.CVVCredictCard");

		if (actorForm.getHolder() == "")
			// final ObjectError error = new ObjectError("holder", "Must be blank");
			// binding.addError(error);
			binding.rejectValue("holder", "error.holderCredictCard");

		if (actorForm.getMake() == "")
			// final ObjectError error = new ObjectError("make", "Must be blank");
			// binding.addError(error);
			binding.rejectValue("make", "error.makeCredictCard");

		if (actorForm.getAccept() == false) {
			final ObjectError error = new ObjectError("accept", "You have to accept terms and condictions");
			binding.addError(error);
			binding.rejectValue("accept", "error.termsAndConditions");
		}
	}
	
	
	public void flush() {
		this.auditorRepository.flush();
	}
}
