
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

import domain.Curricula;
import domain.Hacker;
import forms.RegistrationForm;
import repositories.ActorRepository;
import repositories.HackerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class HackerService {

	@Autowired
	private HackerRepository	hackerRepository;

	@Autowired
	private ActorRepository		actorRepository;

	@Autowired
	private Validator			validator;


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
		if (this.actorRepository.getActorByEmail(hacker.getEmail()) == null)
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

	public Hacker reconstructEdit(final Hacker hacker, final BindingResult binding) {
		Hacker result;

		result = this.hackerRepository.findOne(hacker.getId());

		result.setName(hacker.getName());
		result.setSurname(hacker.getSurname());
		result.setPhoto(hacker.getPhoto());
		result.setEmail(hacker.getEmail());
		result.setPhone(hacker.getPhone());
		result.setAddress(hacker.getAddress());
		result.setId(hacker.getId());
		result.setVersion(hacker.getId());

		this.validator.validate(result, binding);
		System.out.println(binding.getAllErrors());

		return result;
	}
	// QUERY ---------------------------------------------------------------	


	// SAVE-EDIT ---------------------------------------------------------------	

	public Hacker saveEdit(Hacker hacker) {
		Assert.isTrue(!this.checkEmail(hacker), "error.email");
		Assert.isTrue(!this.checkEmailFormatter(hacker), "email.wrong");
		//		if (hacker.getPhone().matches("^([0-9]{4,})$"))
		//			hacker.setPhone("+" + //COMPLETAR//+ " " + hacker.getPhone());		
		hacker = this.hackerRepository.save(hacker);
		System.out.println(hacker);
		return hacker;
	}

	// FINDONE ---------------------------------------------------------------	
	public Hacker findOne(final int id) {
		final Hacker hacker = this.hackerRepository.findOne(id);
		return hacker;
	}

	// DashBoard:
	public String findHackerWithMoreApplications() {

		return this.hackerRepository.findHackerWithMoreApplications();
	}
	
	/**
	 * Find a hacker by his userAccount id.
	 * 
	 * @author �lvaro de la Flor Bonilla
	 * @return Hacker with the given userAccountId.
	 */
	public Hacker getHackerByUserAccountId(int userAccountId) {
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
		} catch (Exception e) {
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
		} catch (Exception e) {
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
	public Hacker getHackerByCurriculaId(Curricula curricula) {
		Assert.notNull(curricula);
		return this.hackerRepository.getHackerByCurriculaId(curricula.getId());
	}
}