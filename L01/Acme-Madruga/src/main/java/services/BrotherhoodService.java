
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Enrolled;
import domain.Member;
import forms.RegistrationForm;

@Service
public class BrotherhoodService {

	@Autowired
	BrotherhoodRepository	brotherhoodRepository;

	@Autowired
	private Validator		validator;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private WelcomeService	welcomeService;

	@Autowired
	private EnrolledService	enrollmentService;

	@Autowired
	private MemberService	memberService;

	@Autowired
	private RequestService	requestService;

	@Autowired
	AreaService				areaService;


	public Collection<Brotherhood> findByAreaId(final int areaId) {

		return this.brotherhoodRepository.findByAreaId(areaId);
	}

	public Brotherhood reconstructR(final RegistrationForm registrationForm, final BindingResult binding) {
		final Brotherhood result = this.create();

		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setPhoto(registrationForm.getPhoto());
		result.setEmail(registrationForm.getEmail());
		result.setTitle(registrationForm.getTitle());
		result.setEstablishmentDate(registrationForm.getEstableshmentDate());
		result.setAddress(registrationForm.getAddress());
		result.setMiddleName(registrationForm.getMiddleName());
		result.setPhone(registrationForm.getPhone());

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

	public Brotherhood reconstruct(final Brotherhood brotherhood, final BindingResult binding) {
		Brotherhood result;

		if (brotherhood.getId() == 0) {
			result = brotherhood;
			this.validator.validate(result, binding);

		} else {
			result = this.brotherhoodRepository.findOne(brotherhood.getId());

			result.setPictures(brotherhood.getPictures());
			result.setName(brotherhood.getName());
			result.setSurname(brotherhood.getSurname());
			result.setPhoto(brotherhood.getPhoto());
			result.setEmail(brotherhood.getEmail());
			result.setTitle(brotherhood.getTitle());

			this.validator.validate(result, binding);
		}
		return result;
	}
	public Brotherhood create() {
		final Brotherhood brotherhood = new Brotherhood();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		brotherhood.setUserAccount(user);
		return brotherhood;
	}
	public List<Brotherhood> findAll() {
		return this.brotherhoodRepository.findAll();
	}

	public Brotherhood findOne(final int id) {
		final Brotherhood brotherhood = this.brotherhoodRepository.findOne(id);
		return brotherhood;
	}

	public Brotherhood saveR(final Brotherhood brotherhood) {
		Assert.isTrue(this.checkEmailR(brotherhood), "error.email");
		Assert.isTrue(!this.checkEmailFormatter(brotherhood), "email.wrong");
		Assert.isTrue(this.checkDate(brotherhood), "error.estableshmentDate");

		if (brotherhood.getPhone().matches("^([0-9]{4,})$"))
			brotherhood.setPhone("+" + this.welcomeService.getPhone() + " " + brotherhood.getPhone());
		return this.brotherhoodRepository.save(brotherhood);
	}

	public Brotherhood save(final Brotherhood brotherhood) {
		Assert.isTrue(!this.checkEmail(brotherhood), "error.email");
		Assert.isTrue(!this.checkEmailFormatter(brotherhood), "email.wrong");
		Assert.isTrue(this.checkDate(brotherhood), "error.estableshmentDate");

		if (brotherhood.getPhone().matches("^([0-9]{4,})$"))
			brotherhood.setPhone("+" + this.welcomeService.getPhone() + " " + brotherhood.getPhone());
		return this.brotherhoodRepository.save(brotherhood);
	}

	private Boolean checkEmailFormatter(final Brotherhood brotherhood) {
		Boolean res = true;
		if ((brotherhood.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w\\.\\w]{1,}(>)") || brotherhood.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}(>)")
			|| brotherhood.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w\\.\\w]{1,}") || brotherhood.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}")))
			res = false;
		return res;
	}

	private Boolean checkDate(final Brotherhood brotherhood) {
		Boolean res = true;
		if (brotherhood.getEstablishmentDate().after(LocalDateTime.now().toDate()))
			res = false;
		return res;
	}

	private Boolean checkEmailR(final Brotherhood brotherhood) {
		Boolean res = false;
		if (this.actorService.getActorByEmail(brotherhood.getEmail()) == null)
			res = true;
		return res;
	}
	private Boolean checkEmail(final Brotherhood brotherhood) {
		Boolean res = false;
		if (this.actorService.getActorByEmailE(brotherhood.getEmail()) == null && (brotherhood.getEmail() != null && this.actorService.getActorByEmail(brotherhood.getEmail()).equals(brotherhood.getEmail())))
			res = true;
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

	public String deletePicture(final String url) {
		final Brotherhood logger = this.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());

		String newPicture = null;

		if (logger.getPictures() != null) {

			List<String> pictures = new ArrayList<>();
			pictures = Arrays.asList(logger.getPictures().split("'"));

			for (int i = 0; i < pictures.size(); i++)
				if (!pictures.get(i).equals(url))
					newPicture = newPicture + pictures.get(i) + "'";
		}

		logger.setPictures(newPicture);
		this.brotherhoodRepository.save(logger);

		return newPicture;
	}

	public Collection<Brotherhood> findFromLoggedMember() {
		final Member member = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		return this.brotherhoodRepository.findActiveFromMember(member.getId());
	}

	public Boolean isActiveFromMemberAndBrotherhood(final int brotherhood) {
		Boolean res = false;
		final int memberId = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId()).getId();
		if (this.brotherhoodRepository.isActiveFromMemberAndBrotherhood(memberId, brotherhood) > 0)
			res = true;
		return res;
	}

	// Hipona 25-02-19 9:51

	public Collection<Brotherhood> findActiveFromLoggedMember() {
		final Member member = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		return this.brotherhoodRepository.findActiveFromMember(member.getId());
	}

	public Collection<Brotherhood> findInactiveFromLoggedMember() {
		final Member member = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		return this.brotherhoodRepository.findInactiveFromMember(member.getId());
	}

	public void dropMember(final int memberId, final int brotherhoodId) {
		final Enrolled enrollment = this.enrollmentService.getBrotherhoodActiveEnrollment(memberId, brotherhoodId);
		this.requestService.deleteAllRequestPendingByMember(enrollment.getMember());
		this.requestService.deleteAllRequestAcceptedByMember(enrollment.getMember());
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

	public String largestBrotherhood() {
		final Brotherhood b = this.brotherhoodRepository.brotherhoodMaxRow();
		return b == null ? null : b.getTitle();
	}

	public String smallestBrotherhood() {
		final Brotherhood b = this.brotherhoodRepository.brotherhoodMinRow();
		return b == null ? null : b.getTitle();
	}

	public Integer numberBrotherhood() {
		return this.brotherhoodRepository.numberOfBrotherhood();
	}

	public Float minBrotherhoodPerArea() {
		return this.brotherhoodRepository.minBrotherhoodPerArea();
	}

	public Float maxBrotherhoodPerArea() {
		return this.brotherhoodRepository.maxBrotherhoodPerArea();
	}

	public Float avgBrotherhoodPerArea() {
		return this.brotherhoodRepository.avgBrotherhoodPerArea();
	}

	public Float stddevBrotherhoodPerArea() {
		return this.brotherhoodRepository.stddevBrotherhoodPerArea();
	}

	public Collection<Object[]> countBrotherhoodPerArea() {
		return this.brotherhoodRepository.countBrotherhoodPerArea();
	}

}
