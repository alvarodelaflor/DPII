
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

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import domain.MessageBox;
import domain.Sponsor;
import domain.Sponsorship;
import forms.RegistrationForm;

/*
 * CONTROL DE CAMBIOS SponsorService.java
 * 
 * Antonio Salvat 09/03/2019 17:42
 */

@Service
public class SponsorService {

	@Autowired
	private Validator			validator;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private WelcomeService		welcomeService;

	@Autowired
	private SponsorRepository	sponsorRepository;

	@Autowired
	private MessageBoxService	messageBoxService;


	public Sponsor reconstructR(final RegistrationForm registrationForm, final BindingResult binding) {
		final Sponsor result = this.create();

		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setPhoto(registrationForm.getPhoto());
		result.setEmail(registrationForm.getEmail());
		result.setAddress(registrationForm.getAddress());
		result.setMiddleName(registrationForm.getMiddleName());
		result.setPhone(registrationForm.getPhone());

		final Collection<Sponsorship> sponsorships = new ArrayList<>();
		result.setSponsorships(sponsorships);

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

	public Sponsor reconstruct(final Sponsor sponsor, final BindingResult binding) {
		Sponsor result;

		if (sponsor.getId() == 0) {
			result = sponsor;
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
			result = this.sponsorRepository.findOne(sponsor.getId());

			result.setName(sponsor.getName());
			result.setSurname(sponsor.getSurname());
			result.setMiddleName(sponsor.getMiddleName());
			result.setPhoto(sponsor.getPhoto());
			result.setEmail(sponsor.getEmail());
			result.setSponsorships(sponsor.getSponsorships());
			result.setPhone(sponsor.getPhone());
			result.setSocialProfiles(sponsor.getSocialProfiles());

			this.validator.validate(result, binding);
		}
		return result;
	}

	public Sponsor create() {
		final Sponsor sponsor = new Sponsor();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		sponsor.setUserAccount(user);
		return sponsor;
	}

	public List<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	public Sponsor findOne(final int id) {
		final Sponsor sponsor = this.sponsorRepository.findOne(id);
		return sponsor;
	}

	public Sponsor saveR(final Sponsor sponsor) {
		Assert.isTrue(this.checkEmailR(sponsor), "error.email");
		Assert.isTrue(!this.checkEmailFormatter(sponsor), "email.wrong");

		if (sponsor.getPhone().matches("^([0-9]{4,})$"))
			sponsor.setPhone("+" + this.welcomeService.getPhone() + " " + sponsor.getPhone());
		return this.sponsorRepository.save(sponsor);
	}

	private Boolean checkEmailFormatter(final Sponsor sponsor) {
		Boolean res = true;
		final String pattern = "(^(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})$)|(^((([a-zA-Z]|[0-9]){1,}[ ]{1}){1,}<(([a-zA-Z]|[0-9]){1,}[@]{1}([a-zA-Z]|[0-9]){1,}([.]{0,1}([a-zA-Z]|[0-9]){0,}){0,})>)$)";
		if (sponsor.getEmail().matches(pattern))
			res = false;
		return res;
	}

	private Boolean checkEmailR(final Sponsor sponsor) {
		Boolean res = false;
		if (this.actorService.getActorByEmail(sponsor.getEmail()) == null)
			res = true;
		return res;
	}

	public Sponsor getSponsorByUserId(final Integer id) {
		final Sponsor a = this.sponsorRepository.getSponsorByUserId(id);
		return a;
	}

}
