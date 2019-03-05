
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Enrolled;
import domain.Member;
import domain.MessageBox;
import forms.RegistrationForm;

/*
 * CONTROL DE CAMBIOS BrotherhoodService.java
 * 
 * Antonio Salvat 23/02/2019 19:49 Modifico create
 */

@Service
public class BrotherhoodService {

	@Autowired
	BrotherhoodRepository		brotherhoodRepository;

	@Autowired
	private Validator			validator;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private WelcomeService		welcomeService;

	@Autowired
	private EnrolledService		enrollmentService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private MessageBoxService	messageBoxService;


	public Brotherhood reconstructR(final RegistrationForm registrationForm, final BindingResult binding) {
		final Brotherhood result = this.create();

		result.setId(0);
		result.setVersion(0);

		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setPhoto(registrationForm.getPhoto());
		result.setEmail(registrationForm.getEmail());
		result.setTitle(registrationForm.getTitle());
		result.setEstablishmentDate(registrationForm.getEstableshmentDate());
		result.setAddress(registrationForm.getAddress());
		result.setMiddleName(registrationForm.getMiddleName());
		result.setPhone(registrationForm.getPhone());
		final Collection<String> pictures = new ArrayList<>();
		result.setPictures(pictures);

		result.getUserAccount().setUsername(registrationForm.getUserName());

		final String password = registrationForm.getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hashPassword = encoder.encodePassword(password, null);
		result.getUserAccount().setPassword(hashPassword);

		this.validator.validate(result, binding);
		return result;
	}

	public Brotherhood reconstruct(final Brotherhood brotherhood, final BindingResult binding) {
		Brotherhood result;

		if (brotherhood.getId() == 0) {
			result = brotherhood;
			this.validator.validate(result, binding);

		} else {
			result = this.brotherhoodRepository.findOne(brotherhood.getId());

			result.setName(brotherhood.getName());
			result.setSurname(brotherhood.getSurname());
			result.setPhoto(brotherhood.getPhoto());
			result.setEmail(brotherhood.getEmail());
			result.setTitle(brotherhood.getTitle());
			result.setEstablishmentDate(brotherhood.getEstablishmentDate());
			result.setPictures(brotherhood.getPictures());

			this.validator.validate(result, binding);
		}
		return result;
	}

	public Brotherhood create() {
		final Brotherhood brotherhood = new Brotherhood();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		final Collection<String> pictures = new ArrayList<>();
		brotherhood.setPictures(pictures);
		authority.setAuthority(Authority.BROTHERHOOD);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		brotherhood.setUserAccount(user);

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

		brotherhood.setMessageBoxes(boxesDefault);
		brotherhood.setIsBanned(false);
		brotherhood.setIsSuspicious(false);

		return brotherhood;
	}
	public List<Brotherhood> findAll() {
		return this.brotherhoodRepository.findAll();
	}

	public Brotherhood findOne(final int id) {
		final Brotherhood brotherhood = this.brotherhoodRepository.findOne(id);
		return brotherhood;
	}

	public Brotherhood save(final Brotherhood brotherhood) {
		Assert.isTrue(!this.checkEmail(brotherhood), "email.wrong");
		if (brotherhood.getPhone().matches("^([0-9]{4,})$"))
			brotherhood.setPhone("+" + this.welcomeService.getPhone() + " " + brotherhood.getPhone());
		return this.brotherhoodRepository.save(brotherhood);
	}

	private Boolean checkEmail(final Brotherhood brotherhood) {
		Boolean res = true;
		if ((brotherhood.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}") || (brotherhood.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}") || (brotherhood.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}(>)") || (brotherhood
			.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}(>)") || this.actorService.getActorByEmail(brotherhood.getEmail()) != null)))))
			res = false;
		return res;
	}
	public Brotherhood update(final Brotherhood brotherhood) {
		Assert.isTrue(LoginService.getPrincipal().getId() == brotherhood.getUserAccount().getId());
		return this.brotherhoodRepository.save(brotherhood);
	}

	public Brotherhood getBrotherhoodByUserAccountId(final int userAccountId) {
		Brotherhood res;
		res = this.brotherhoodRepository.findByUserAccountId(userAccountId);
		return res;
	}

	public Collection<String> deletePicture(final String picture) {
		final Brotherhood logger = this.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		final List<String> pictures = new ArrayList<>();
		pictures.addAll(logger.getPictures());
		for (int i = 0; i < pictures.size(); i++)
			if (pictures.get(i).equals(picture)) {
				System.out.println(pictures.get(i));
				pictures.remove(picture);
			}
		logger.setPictures(pictures);
		this.brotherhoodRepository.save(logger);
		return pictures;
	}
	public Collection<Brotherhood> findFromLoggedMember() {
		final Member member = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		return this.brotherhoodRepository.findFromMember(member.getId());
	}

	public void dropMember(final int memberId, final int brotherhoodId) {
		final Enrolled enrollment = this.enrollmentService.getBrotherhoodActiveEnrollment(memberId, brotherhoodId);
		System.out.println("Dropping member");
		Assert.notNull(enrollment);
		// We have to check if we are an active member
		enrollment.setDropMoment(new Date());
		this.enrollmentService.save(enrollment);
	}
	public void dropLogged(final int brotherhoodId) {
		final Member member = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		this.dropMember(member.getId(), brotherhoodId);
	}

	public Integer numberBrotherhood() {
		return this.brotherhoodRepository.numberOfBrotherhood();
	}
}
