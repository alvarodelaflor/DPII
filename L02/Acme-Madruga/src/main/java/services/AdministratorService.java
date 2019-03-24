
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
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
import domain.MessageBox;
import forms.RegistrationForm;

/*
 * CONTROL DE CAMBIOS AdministratorService.java
 * 
 * Antonio Salvat 23/02/2019 19:49 Modifico create
 */

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

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private MessageBoxService		messageBoxService;

	private HashSet<String>			scoreWordsPos	= new HashSet<>();
	private HashSet<String>			scoreWordsNeg	= new HashSet<>();


	public HashSet<String> getScoreWordsPos() {
		return this.scoreWordsPos;
	}

	public HashSet<String> getScoreWordsNeg() {
		return this.scoreWordsNeg;
	}

	public void flush() {

		this.administratorRepository.flush();
	}

	public void setScoreWordsPos(final HashSet<String> scoreWordsPos) {
		this.scoreWordsPos = scoreWordsPos;
	}

	public void setScoreWordsNeg(final HashSet<String> scoreWordsNeg) {
		this.scoreWordsNeg = scoreWordsNeg;
	}

	Administrator findByUserAccountId(final int userAccountId) {

		return this.administratorRepository.findByUserAccountId(userAccountId);
	}

	public Administrator reconstructR(final RegistrationForm registrationForm, final BindingResult binding) {
		final Administrator result = this.create();

		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setPhoto(registrationForm.getPhoto());
		result.setEmail(registrationForm.getEmail());
		result.setAddress(registrationForm.getAddress());
		result.setMiddleName(registrationForm.getMiddleName());
		result.setPhone(registrationForm.getPhone());

		//MailBox
		final MessageBox inBox = this.messageBoxService.create();
		final MessageBox outBox = this.messageBoxService.create();
		final MessageBox trashBox = this.messageBoxService.create();
		final MessageBox notificationBox = this.messageBoxService.create();
		final MessageBox spamBox = this.messageBoxService.create();

		inBox.setName("in box");
		outBox.setName("out box");
		trashBox.setName("trash box");
		notificationBox.setName("notification box");
		spamBox.setName("spam box");

		inBox.setIsDefault(true);
		outBox.setIsDefault(true);
		trashBox.setIsDefault(true);
		notificationBox.setIsDefault(true);
		spamBox.setIsDefault(true);

		final MessageBox inBoxSave = this.messageBoxService.save(inBox);
		final MessageBox outBoxSave = this.messageBoxService.save(outBox);
		final MessageBox trashBoxSave = this.messageBoxService.save(trashBox);
		final MessageBox notificationBoxSave = this.messageBoxService.save(notificationBox);
		final MessageBox spamBoxSave = this.messageBoxService.save(spamBox);

		final Collection<MessageBox> boxesDefault = new ArrayList<>();

		boxesDefault.add(inBoxSave);
		boxesDefault.add(outBoxSave);
		boxesDefault.add(trashBoxSave);
		boxesDefault.add(notificationBoxSave);
		boxesDefault.add(spamBoxSave);

		result.setMessageBoxes(boxesDefault);

		result.getUserAccount().setUsername(registrationForm.getUserName());
		result.setConfiguration(this.configurationService.getConfiguration());
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

		if (this.actorService.getActorByUser(registrationForm.getUserName()) != null) {
			final ObjectError error = new ObjectError("userName", "");
			binding.addError(error);
			binding.rejectValue("userName", "error.userName");
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

		this.validator.validate(result, binding);
		return result;
	}
	public Administrator reconstruct(final Administrator admin, final BindingResult binding) {
		Administrator result;

		if (admin.getId() == 0) {
			result = admin;
			//MailBox
			final MessageBox inBox = this.messageBoxService.create();
			final MessageBox outBox = this.messageBoxService.create();
			final MessageBox trashBox = this.messageBoxService.create();
			final MessageBox notificationBox = this.messageBoxService.create();
			final MessageBox spamBox = this.messageBoxService.create();

			inBox.setName("in box");
			outBox.setName("out box");
			trashBox.setName("trash box");
			notificationBox.setName("notification box");
			spamBox.setName("spam box");

			inBox.setIsDefault(true);
			outBox.setIsDefault(true);
			trashBox.setIsDefault(true);
			notificationBox.setIsDefault(true);
			spamBox.setIsDefault(true);

			final MessageBox inBoxSave = this.messageBoxService.save(inBox);
			final MessageBox outBoxSave = this.messageBoxService.save(outBox);
			final MessageBox trashBoxSave = this.messageBoxService.save(trashBox);
			final MessageBox notificationBoxSave = this.messageBoxService.save(notificationBox);
			final MessageBox spamBoxSave = this.messageBoxService.save(spamBox);

			final Collection<MessageBox> boxesDefault = new ArrayList<>();

			boxesDefault.add(inBoxSave);
			boxesDefault.add(outBoxSave);
			boxesDefault.add(trashBoxSave);
			boxesDefault.add(notificationBoxSave);
			boxesDefault.add(spamBoxSave);

			result.setMessageBoxes(boxesDefault);
		} else {
			result = this.administratorRepository.findOne(admin.getId());

			result.setName(admin.getName());
			result.setSurname(admin.getSurname());
			result.setPhoto(admin.getPhoto());
			result.setEmail(admin.getEmail());
			result.setPhone(admin.getPhone());
			result.setMiddleName(admin.getMiddleName());
			result.setAddress(admin.getAddress());
			result.setSocialProfiles(admin.getSocialProfiles());

			this.validator.validate(result, binding);
		}
		return result;
	}

	public Administrator create() {

		Assert.notNull(this.administratorRepository.findByUserAccountId(LoginService.getPrincipal().getId()));

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

	public Administrator saveR(final Administrator admin) {
		Assert.isTrue(!this.checkEmailFormatter(admin), "email.wrong");
		Assert.isTrue(this.checkEmailR(admin), "error.email");
		if (admin.getPhone().matches("^([0-9]{4,})$"))
			admin.setPhone("+" + this.welcomeService.getPhone() + " " + admin.getPhone());
		return this.administratorRepository.save(admin);
	}

	public Administrator save(final Administrator admin) {
		Assert.isTrue(!this.checkEmailFormatter(admin), "email.wrong");
		Assert.isTrue(!this.checkEmail(admin), "error.email");
		if (admin.getPhone().matches("^([0-9]{4,})$"))
			admin.setPhone("+" + this.welcomeService.getPhone() + " " + admin.getPhone());
		return this.administratorRepository.save(admin);
	}

	private Boolean checkEmailFormatter(final Administrator admin) {
		Boolean res = true;
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		final String pattern2 = "(^((([a-zA-Z]|[0-9]){1,}[@])$)|(^(([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]>))$)";
		if (admin.getEmail().matches(pattern) || admin.getEmail().matches(pattern2))
			res = false;
		return res;
	}
	private Boolean checkEmail(final Administrator admin) {
		Boolean res = false;
		if (this.actorService.getActorByEmailE(admin.getEmail()) == null && (admin.getEmail() != null && this.actorService.getActorByEmail(admin.getEmail()).equals(admin.getEmail())))
			res = true;
		return res;
	}

	private Boolean checkEmailR(final Administrator admin) {
		Boolean res = false;
		if (this.actorService.getActorByEmail(admin.getEmail()) == null)
			res = true;
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

	// FERRETE

	// Método para mostrar las score words pos
	public HashSet<String> listScoreWordsPos() {

		final List<String> enP = Arrays.asList("good", "fantastic", "excellent", "great", "amazing", "terrific", "beautiful");
		this.scoreWordsPos.addAll(enP);
		final List<String> esP = Arrays.asList("bueno", "fantástico", "excelente", "genial", "increíble", "excelente", "hermoso");
		this.scoreWordsPos.addAll(esP);

		return this.scoreWordsPos;
	}

	public HashSet<String> listScoreWordsNeg() {

		final List<String> enN = Arrays.asList("not", "bad", "horrible", "average", "disaster");
		this.scoreWordsNeg.addAll(enN);
		final List<String> esN = Arrays.asList("no", "malo", "horrible", "promedio", "desastre");
		this.scoreWordsNeg.addAll(esN);

		return this.scoreWordsNeg;
	}

	// Método para añadir pos
	public HashSet<String> newScoreWordsPos(final String newWord) {
		this.scoreWordsPos.add(newWord);
		return this.getScoreWordsPos();
	}

	// Método para borrar pos
	public HashSet<String> deleteScoreWordsPos(final String word) {
		Assert.isTrue(this.getScoreWordsPos().contains(word), "noScoreWord.error");
		this.scoreWordsPos.remove(word);
		return this.getScoreWordsPos();
	}

	// Método para añadir neg 
	public HashSet<String> newScoreWordsNeg(final String newWord) {
		this.scoreWordsNeg.add(newWord);
		return this.getScoreWordsNeg();
	}

	// Método para borrar neg
	public HashSet<String> deleteScoreWordsNeg(final String word) {
		Assert.isTrue(this.getScoreWordsNeg().contains(word), "noScoreWord.error");
		this.scoreWordsNeg.remove(word);
		return this.getScoreWordsNeg();
	}

	public void flush() {
		this.administratorRepository.flush();
	}

}
