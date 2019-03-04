
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

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Member;
import forms.RegistrationForm;

@Service
public class MemberService {

	@Autowired
	private MemberRepository	memberRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;

	@Autowired
	private WelcomeService		welcomeService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	public Member reconstructR(final RegistrationForm registrationForm, final BindingResult binding) {
		final Member result = this.create();

		result.setName(registrationForm.getName());
		result.setSurname(registrationForm.getSurname());
		result.setPhoto(registrationForm.getPhoto());
		result.setEmail(registrationForm.getEmail());
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
	public Member reconstruct(final Member member, final BindingResult binding) {
		Member result;

		if (member.getId() == 0)
			result = member;
		else {
			result = this.memberRepository.findOne(member.getId());

			result.setName(member.getName());
			result.setSurname(member.getSurname());
			result.setPhoto(member.getPhoto());
			result.setEmail(member.getEmail());

			this.validator.validate(result, binding);
		}
		return result;
	}

	public Member create() {
		final Member member = new Member();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.MEMBER);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		member.setUserAccount(user);
		return member;
	}

	public Collection<Member> findAll() {
		return this.memberRepository.findAll();
	}

	public Member findOne(final int id) {
		final Member member = this.memberRepository.findOne(id);
		return member;
	}

	public Member saveR(final Member member) {
		Assert.isTrue(!this.checkEmailFormatter(member), "email.wrong");
		Assert.isTrue(!this.checkEmailR(member), "error.email");
		if (member.getPhone().matches("^([0-9]{4,})$"))
			member.setPhone("+" + this.welcomeService.getPhone() + " " + member.getPhone());
		return this.memberRepository.save(member);
	}
	public Member save(final Member member) {
		Assert.isTrue(!this.checkEmailFormatter(member), "email.wrong");
		Assert.isTrue(!this.checkEmail(member), "error.email");
		if (member.getPhone().matches("^([0-9]{4,})$"))
			member.setPhone("+" + this.welcomeService.getPhone() + " " + member.getPhone());
		return this.memberRepository.save(member);
	}

	private Boolean checkEmailFormatter(final Member member) {
		Boolean res = true;
		if ((member.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w\\.\\w]{1,}(>)") || member.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}(>)") || member.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w\\.\\w]{1,}") || member
			.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}")))
			res = false;
		return res;
	}

	private Boolean checkEmail(final Member member) {
		Boolean res = false;
		if (this.actorService.getActorByEmail(member.getEmail()) != null && (member.getEmail() != null && this.actorService.getActorByEmail(member.getEmail()).equals(member.getEmail())))
			res = true;
		return res;
	}

	private Boolean checkEmailR(final Member member) {
		Boolean res = true;
		if (this.actorService.getActorByEmail(member.getEmail()) == null)
			res = false;
		return res;
	}

	public Member update(final Member member) {
		Assert.isTrue(LoginService.getPrincipal().getId() == member.getUserAccount().getId());
		return this.memberRepository.save(member);
	}

	public Member getMemberByUserAccountId(final int userAccountId) {
		Member res;
		res = this.memberRepository.findByUserAccountId(userAccountId);
		return res;
	}
	public Boolean isBrotherhoodActiveMember(final int memberId, final int brotherHoodId) {
		return this.memberRepository.isBrotherhoodActiveMember(memberId, brotherHoodId) > 0;
	}

	public Collection<Member> brotherhoodAllMember(final int brotherHoodId) {
		return this.memberRepository.brotherhoodAllMember(brotherHoodId);
	}

	public Collection<Member> lisMemberAccept() {
		return this.memberRepository.memberAccept();
	}

	public Boolean checkAlreadyInProcession(final int memberId) {
		Boolean res = false;
		if (this.memberRepository.membersOfProcession(memberId) > 0)
			res = true;
		return res;
	}

	public Boolean checkIsInBrotherhood(final int brotherhoodId) {
		Boolean res = false;
		final int memberId = this.memberRepository.findByUserAccountId(LoginService.getPrincipal().getId()).getId();
		if (this.memberRepository.membersOfBrotherhood(memberId, brotherhoodId) > 0)
			res = true;
		return res;
	}

	public Float maxNumberOfMemberPerBrotherhood() {
		return this.memberRepository.maxNumberOfMembersPerBrotherhood();
	}

	public Float minNumberOfMemberPerBrotherhood() {
		return this.memberRepository.minNumberOfMembersPerBrotherhood();
	}
	public Float avgNumberOfMemberPerBrotherhood() {
		return this.memberRepository.avgNumberOfMembersPerBrotherhood();
	}

	public Float desviationOfNumberOfMemberPerBrotherhood() {
		return this.memberRepository.stddevNumberOfMembersPerBrotherhood();
	}
}