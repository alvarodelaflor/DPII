
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

import repositories.ChapterRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chapter;
import domain.MessageBox;
import domain.Proclaim;
import forms.RegistrationForm;

/*
 * CONTROL DE CAMBIOS ChapterService.java
 * 
 * Carmen Fern�ndez 08/03/2019 19:49
 */

@Service
public class ChapterService {

	@Autowired
	private Validator			validator;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageBoxService	messageBoxService;

	@Autowired
	private WelcomeService		welcomeService;

	@Autowired
	private ChapterRepository	chapterRepository;


	public Chapter reconstructR(final RegistrationForm registrationForm, final BindingResult binding) {
		final Chapter result = this.create();

		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setPhoto(registrationForm.getPhoto());
		result.setEmail(registrationForm.getEmail());
		result.setTitle(registrationForm.getTitle());
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
		//MailBox

		//Proclaim
		final Collection<Proclaim> p = new ArrayList<>();
		result.setProclaim(p);
		//Proclaim

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

	public Chapter reconstruct(final Chapter chapter, final BindingResult binding) {
		Chapter result;

		if (chapter.getId() == 0) {
			result = chapter;
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
			this.validator.validate(result, binding);
		} else {
			result = this.chapterRepository.findOne(chapter.getId());

			result.setName(chapter.getName());
			result.setSurname(chapter.getSurname());
			result.setMiddleName(chapter.getMiddleName());
			result.setPhoto(chapter.getPhoto());
			result.setEmail(chapter.getEmail());
			result.setTitle(chapter.getTitle());
			result.setPhone(chapter.getPhone());
			result.setSocialProfiles(chapter.getSocialProfiles());

			this.validator.validate(result, binding);
		}
		return result;
	}

	public Chapter create() {
		final Chapter chapter = new Chapter();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CHAPTER);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		chapter.setUserAccount(user);

		final Collection<Proclaim> p = new ArrayList<>();
		chapter.setProclaim(p);

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

		chapter.setMessageBoxes(boxesDefault);

		return chapter;
	}

	public List<Chapter> findAll() {
		return this.chapterRepository.findAll();
	}

	public Chapter findOne(final int id) {
		final Chapter chapter = this.chapterRepository.findOne(id);
		return chapter;
	}

	public Chapter saveR(final Chapter chapter) {
		Assert.isTrue(this.checkEmailR(chapter), "error.email");
		Assert.isTrue(!this.checkEmailFormatter(chapter), "email.wrong");

		if (chapter.getPhone().matches("^([0-9]{4,})$"))
			chapter.setPhone("+" + this.welcomeService.getPhone() + " " + chapter.getPhone());
		return this.chapterRepository.save(chapter);
	}

	private Boolean checkEmailFormatter(final Chapter chapter) {
		Boolean res = true;
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		if (chapter.getEmail().matches(pattern))
			res = false;
		return res;
	}

	private Boolean checkEmailR(final Chapter chapter) {
		Boolean res = false;
		if (this.actorService.getActorByEmail(chapter.getEmail()) == null)
			res = true;
		return res;
	}

	public Chapter getChapterByUserAccountId(final int userAccountId) {
		Chapter res;
		res = this.chapterRepository.findByUserAccountId(userAccountId);
		return res;
	}

	public void flush() {
		this.chapterRepository.flush();
	}

	public void delete(final Chapter chapter) {
		Assert.isTrue(LoginService.getPrincipal().getId() == chapter.getUserAccount().getId());

	}
}
