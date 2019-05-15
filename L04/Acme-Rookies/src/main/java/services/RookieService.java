
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import domain.CreditCard;
import domain.Curricula;
import domain.Finder;
import domain.Rookie;
import forms.RegistrationForm;
import repositories.ActorRepository;
import repositories.RookieRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class RookieService {

	@Autowired
	private RookieRepository		rookieRepository;

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ConfigurationService	configurationService;


	// CREATE ---------------------------------------------------------------		
	public Rookie create() {
		final Rookie rookie = new Rookie();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ROOKIE);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		rookie.setUserAccount(user);
		final CreditCard creditCard = new CreditCard();
		rookie.setCreditCard(creditCard);
		return rookie;
	}

	// SAVE-CREATE ---------------------------------------------------------------		
	public Rookie saveCreate(final Rookie rookie) {
		Assert.isTrue(!this.checkEmailFormatter(rookie), "email.wrong");
		Assert.isTrue(this.checkEmail(rookie), "error.email");
		final Finder finder = this.finderService.create();
		this.finderService.save(finder);
		rookie.setFinder(finder);
		if (rookie.getPhone().matches("^([0-9]{4,})$"))
			rookie.setPhone(this.configurationService.getConfiguration().getCountryCode() + " " + rookie.getPhone());

		return this.rookieRepository.save(rookie);
	}
	private Boolean checkEmailFormatter(final Rookie rookie) {
		Boolean res = true;
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		if (rookie.getEmail().matches(pattern))
			res = false;
		return res;
	}

	private Boolean checkEmail(final Rookie rookie) {
		Boolean res = false;
		System.out.println(this.actorRepository.getActorByEmail(rookie.getEmail()).size());
		if (this.actorRepository.getActorByEmail(rookie.getEmail()).size() < 1)
			res = true;
		return res;
	}

	// RECONSTRUCT-CREATE ---------------------------------------------------------------		
	public Rookie reconstructCreate(final RegistrationForm registrationForm, final BindingResult binding) {
		final Rookie result = this.create();

		System.out.println("carmen: entron en el reconstructCreate");

		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setPhoto(registrationForm.getPhoto());
		result.setEmail(registrationForm.getEmail());
		result.setAddress(registrationForm.getAddress());
		result.setPhone(registrationForm.getPhone());
		result.setVatNumber(registrationForm.getVatNumber());

		final CreditCard creditCard = new CreditCard();
		creditCard.setCVV(registrationForm.getCVV());
		creditCard.setExpiration(registrationForm.getExpiration());
		creditCard.setHolder(registrationForm.getHolder());
		creditCard.setMake(registrationForm.getMake());
		creditCard.setNumber(registrationForm.getNumber());

		result.setCreditCard(creditCard);

		if (registrationForm.getAccept() == false) {
			final ObjectError error = new ObjectError("accept", "You have to accepted the terms and condictions");
			binding.addError(error);
			binding.rejectValue("accept", "error.termsAndConditions");
		}

		if (registrationForm.getUserName().length() <= 5 && registrationForm.getUserName().length() <= 5)
			//			final ObjectError error = new ObjectError("userName", "");
			//			binding.addError(error);
			binding.rejectValue("userName", "error.userAcount");

		if (this.actorRepository.getActorByUser(registrationForm.getUserName()) != null)
			//			final ObjectError error = new ObjectError("userName", "");
			//			binding.addError(error);
			binding.rejectValue("userName", "error.userName");

		if (registrationForm.getConfirmPassword().length() <= 5 && registrationForm.getPassword().length() <= 5)
			//			final ObjectError error = new ObjectError("password", "");
			//			binding.addError(error);
			binding.rejectValue("password", "error.password");

		if (!registrationForm.getConfirmPassword().equals(registrationForm.getPassword()))
			//			final ObjectError error = new ObjectError("password", "");
			//			binding.addError(error);
			binding.rejectValue("password", "error.password.confirm");

		final String userName = registrationForm.getUserName();
		result.getUserAccount().setUsername(userName);

		final String password = registrationForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		result.getUserAccount().setPassword(hashPassword);

		//A�ADIDO

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

		//A�ADIDO

		if (!registrationForm.getNumber().matches("([0-9]){16}"))
			//			final ObjectError error = new ObjectError("number", "");
			//			binding.addError(error);
			binding.rejectValue("number", "error.numberCredictCard");

		if (!registrationForm.getCVV().matches("([0-9]){3}"))
			//			final ObjectError error = new ObjectError("CVV", "");
			//			binding.addError(error);
			binding.rejectValue("CVV", "error.CVVCredictCard");

		if (registrationForm.getHolder() == "")
			//			final ObjectError error = new ObjectError("holder", " ");
			//			binding.addError(error);
			binding.rejectValue("holder", "error.holderCredictCard");

		if (registrationForm.getMake() == "")
			//			final ObjectError error = new ObjectError("make", " ");
			//			binding.addError(error);
			binding.rejectValue("make", "error.makeCredictCard");
		System.out.println("valide todo");

		this.validator.validate(result, binding);

		return result;
	}
	// RECONSTRUCT-EDIT---------------------------------------------------------------		

	public Rookie reconstructEdit(final Rookie rookie, final BindingResult binding) {
		Rookie result;
		final Rookie res = this.rookieRepository.findOne(rookie.getId());

		result = rookie;

		result.setName(rookie.getName());
		result.setSurname(rookie.getSurname());
		result.setPhoto(rookie.getPhoto());
		result.setEmail(rookie.getEmail());
		result.setPhone(rookie.getPhone());
		result.setAddress(rookie.getAddress());
		result.setVatNumber(res.getVatNumber());

		this.validator.validate(result, binding);
		System.out.println(binding.getAllErrors());

		if (binding.getAllErrors().isEmpty()) {
			res.setName(result.getName());
			res.setSurname(result.getSurname());
			res.setPhoto(result.getPhoto());
			res.setEmail(result.getEmail());
			res.setPhone(result.getPhone());
			res.setAddress(result.getAddress());
			res.setName(result.getName());

		}

		return res;

	}
	// QUERY ---------------------------------------------------------------	

	public Collection<Rookie> findRookieRegardlessFinder(final String keyword, final Double salary, final Date deadline, final String description) {

		return this.rookieRepository.findRookieRegardlessFinder(keyword, salary, deadline, description);
	}

	// SAVE-EDIT ---------------------------------------------------------------	

	public Rookie saveEdit(Rookie rookie) {
		Assert.isTrue(!this.checkEmailFormatter(rookie), "email.wrong");
		Assert.isTrue(!this.checkEmailFormatter(rookie), "email.wrong");
		if (rookie.getPhone().matches("^([0-9]{4,})$")) {
			String phoneM = rookie.getPhone();
			rookie.setPhone(phoneM);
			rookie.setPhone(this.configurationService.getConfiguration().getCountryCode() + " " + rookie.getPhone());
		}
		rookie = this.rookieRepository.save(rookie);
		System.out.println(rookie);
		return rookie;
	}

	private Boolean checkEmailEdit(final Rookie rookie) {
		Boolean res = false;
		System.out.println(this.actorService.getActorByEmailE(rookie.getEmail()) == null);

		if (this.actorService.getActorByEmailE(rookie.getEmail()) == null && this.actorRepository.getActorByEmail(rookie.getEmail()).size() <= 1)
			res = true;
		return res;
	}

	// FINDONE ---------------------------------------------------------------	
	public Rookie findOne(final int id) {
		final Rookie rookie = this.rookieRepository.findOne(id);
		return rookie;
	}

	/**
	 * Find a rookie by his userAccount id.
	 * 
	 * @author �lvaro de la Flor Bonilla
	 * @return Rookie with the given userAccountId.
	 */
	public Rookie getRookieByUserAccountId(final int userAccountId) {
		return this.rookieRepository.getRookieByUserAccountId(userAccountId);
	}

	/**
	 * Check that any user is login
	 * 
	 * @author �lvaro de la Flor Bonilla
	 * @return True if an user is login, false in otherwise.
	 */
	public Boolean checkAnyLogger() {
		Boolean res = true;
		try {
			LoginService.getPrincipal().getId();
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	/**
	 * Get the rookieLogin. Must exits an user login.
	 * 
	 * @author �lvaro de la Flor Bonilla
	 * @return The rookie login. Null if the user not is a rookie.
	 */
	public Rookie getRookieLogin() {
		Rookie res;
		try {
			res = this.getRookieByUserAccountId(LoginService.getPrincipal().getId());
		} catch (final Exception e) {
			res = null;
		}
		return res;
	}

	/**
	 * Must exits the curricula
	 * 
	 * @author �lvaro de la Flor Bonilla
	 * @return The rookie of the given curricula
	 */
	public Rookie getRookieByCurriculaId(final Curricula curricula) {
		Assert.notNull(curricula);
		return this.rookieRepository.getRookieByCurriculaId(curricula.getId());
	}

	public void delete(final Rookie rookie) {

		Assert.isTrue(LoginService.getPrincipal().getId() == rookie.getUserAccount().getId());
		this.applicationService.deleteRookieApplications(rookie.getId());
		this.curriculaService.deleteRookieCurriculas(rookie.getId());
		this.rookieRepository.delete(rookie);
	}

	public Collection<Rookie> findAll() {
		return this.rookieRepository.findAll();
	}

	public void flush() {
		this.rookieRepository.flush();
	}

	public Collection<Rookie> findByProblem(final int problemId) {
		return this.rookieRepository.findByProblem(problemId);
	}
	
	/**
	 * Get the rookie login.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Hacker} if is login, null otherwise.
	 */
	public Rookie getHackerLogin() {
		Rookie res;
		try {
			final int aux = LoginService.getPrincipal().getId();
			res = this.getRookieByUserAccountId(aux);
		} catch (final Exception e) {
			res = null;
		}
		return res;
	}
}
