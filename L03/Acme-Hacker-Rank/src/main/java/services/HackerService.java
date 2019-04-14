
package services;

import java.util.ArrayList;
import java.util.Collection;
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
import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.CreditCard;
import domain.Curricula;
import domain.Hacker;
import forms.RegistrationForm;

@Service
@Transactional
public class HackerService {

	@Autowired
	private HackerRepository	hackerRepository;

	@Autowired
	private ActorRepository		actorRepository;

	@Autowired
	private Validator			validator;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CurriculaService	curriculaService;


	// CREATE ---------------------------------------------------------------		
	public Hacker create() {
		final Hacker hacker = new Hacker();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HACKER);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		hacker.setUserAccount(user);
		final CreditCard creditCard = new CreditCard();
		hacker.setCreditCard(creditCard);
		return hacker;
	}

	// SAVE-CREATE ---------------------------------------------------------------		
	public Hacker saveCreate(final Hacker hacker) {
		Assert.isTrue(!this.checkEmailFormatter(hacker), "email.wrong");
		Assert.isTrue(this.checkEmail(hacker), "error.email");
		//		if (member.getPhone().matches("^([0-9]{4,})$"))
		//			member.setPhone("+" + this.welcomeService.getPhone() + " " + member.getPhone());
		return this.hackerRepository.save(hacker);
	}
	private Boolean checkEmailFormatter(final Hacker hacker) {
		Boolean res = true;
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		if (hacker.getEmail().matches(pattern))
			res = false;
		return res;
	}

	private Boolean checkEmail(final Hacker hacker) {
		Boolean res = false;
		System.out.println(this.actorRepository.getActorByEmail(hacker.getEmail()).size());
		if (this.actorRepository.getActorByEmail(hacker.getEmail()).size() < 1)
			res = true;
		return res;
	}

	// RECONSTRUCT-CREATE ---------------------------------------------------------------		
	public Hacker reconstructCreate(final RegistrationForm registrationForm, final BindingResult binding) {
		final Hacker result = this.create();

		System.out.println("carmen: entron en el reconstructCreate");

		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setPhoto(registrationForm.getPhoto());
		result.setEmail(registrationForm.getEmail());
		result.setAddress(registrationForm.getAddress());
		result.setPhone(registrationForm.getPhone());

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

	public Hacker reconstructEdit(final Hacker hacker, final BindingResult binding) {
		Hacker result;
		final Hacker res = this.hackerRepository.findOne(hacker.getId());

		result = hacker;

		result.setName(hacker.getName());
		result.setSurname(hacker.getSurname());
		result.setPhoto(hacker.getPhoto());
		result.setEmail(hacker.getEmail());
		result.setPhone(hacker.getPhone());
		result.setAddress(hacker.getAddress());

		this.validator.validate(result, binding);
		System.out.println(binding.getAllErrors());

		if (binding.getAllErrors().isEmpty()) {
			res.setName(result.getName());
			res.setSurname(result.getSurname());
			res.setPhoto(result.getPhoto());
			res.setEmail(result.getEmail());
			res.setPhone(result.getPhone());
			res.setAddress(result.getAddress());
		}

		return res;

	}
	// QUERY ---------------------------------------------------------------	

	// SAVE-EDIT ---------------------------------------------------------------	

	public Hacker saveEdit(Hacker hacker) {
		Assert.isTrue(!this.checkEmailFormatter(hacker), "email.wrong");
		Assert.isTrue(this.checkEmailEdit(hacker), "error.email");
		//		if (hacker.getPhone().matches("^([0-9]{4,})$"))
		//			hacker.setPhone("+" + //COMPLETAR//+ " " + hacker.getPhone());		
		hacker = this.hackerRepository.save(hacker);
		System.out.println(hacker);
		return hacker;
	}

	private Boolean checkEmailEdit(final Hacker hacker) {
		Boolean res = false;
		System.out.println(this.actorService.getActorByEmailE(hacker.getEmail()) == null);

		if (this.actorService.getActorByEmailE(hacker.getEmail()) == null && this.actorRepository.getActorByEmail(hacker.getEmail()).size() <= 1)
			res = true;
		return res;
	}

	// FINDONE ---------------------------------------------------------------	
	public Hacker findOne(final int id) {
		final Hacker hacker = this.hackerRepository.findOne(id);
		return hacker;
	}

	/**
	 * Find a hacker by his userAccount id.
	 * 
	 * @author �lvaro de la Flor Bonilla
	 * @return Hacker with the given userAccountId.
	 */
	public Hacker getHackerByUserAccountId(final int userAccountId) {
		return this.hackerRepository.getHackerByUserAccountId(userAccountId);
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
	 * Get the hackerLogin. Must exits an user login.
	 * 
	 * @author �lvaro de la Flor Bonilla
	 * @return The hacker login. Null if the user not is a hacker.
	 */
	public Hacker getHackerLogin() {
		Hacker res;
		try {
			res = this.getHackerByUserAccountId(LoginService.getPrincipal().getId());
		} catch (final Exception e) {
			res = null;
		}
		return res;
	}

	/**
	 * Must exits the curricula
	 * 
	 * @author �lvaro de la Flor Bonilla
	 * @return The hacker of the given curricula
	 */
	public Hacker getHackerByCurriculaId(final Curricula curricula) {
		Assert.notNull(curricula);
		return this.hackerRepository.getHackerByCurriculaId(curricula.getId());
	}

	public void delete(final Hacker hacker) {

		Assert.isTrue(LoginService.getPrincipal().getId() == hacker.getUserAccount().getId());
		this.applicationService.deleteHackerApplications(hacker.getId());
		this.curriculaService.deleteHackerCurriculas(hacker.getId());
		this.hackerRepository.delete(hacker);
	}

	public Collection<Hacker> findAll() {
		return this.hackerRepository.findAll();
	}

	public void flush() {
		this.hackerRepository.flush();
	}

}
