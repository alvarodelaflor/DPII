
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Member;
import domain.MessageBox;
import forms.RegistrationForm;

/*
 * CONTROL DE CAMBIOS MemberService.java
 * 
 * Antonio Salvat 23/02/2019 19:49 Modifico create
 */

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
	private MessageBoxService	messageBoxService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	public Member reconstructR(final RegistrationForm registrationForm, final BindingResult binding) {
		final Member result = this.create();

		result.setId(0);
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

		result.setVersion(0);

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

		member.setMessageBoxes(boxesDefault);
		member.setIsBanned(false);
		member.setIsSuspicious(false);

		return member;
	}

	public Collection<Member> findAll() {
		return this.memberRepository.findAll();
	}

	public Member findOne(final int id) {
		final Member member = this.memberRepository.findOne(id);
		return member;
	}

	public Member save(final Member member) {
		Assert.isTrue(!this.checkEmail(member), "email.wrong");
		if (member.getPhone().matches("^([0-9]{4,})$"))
			member.setPhone("+" + this.welcomeService.getPhone() + " " + member.getPhone());
		return this.memberRepository.save(member);
	}

	private Boolean checkEmail(final Member member) {
		Boolean res = true;
		if ((member.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}") || (member.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}") || (member.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}(>)") || (member.getEmail().matches(
			"[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}(>)") || this.actorService.getActorByEmail(member.getEmail()) != null)))))
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

	public Integer maxNumberOfMemberPerBrotherhood() {
		final Collection<Integer> numbers = this.memberRepository.listNumberOfMembersPerBrotherhood();
		final List<Integer> numberList = new ArrayList<>();
		numberList.addAll(numbers);
		final Integer res = numberList.get(0);
		return res;
	}

	public Integer minNumberOfMemberPerBrotherhood() {
		final Collection<Integer> numbers = this.memberRepository.listNumberOfMembersPerBrotherhood();
		final List<Integer> numberList = new ArrayList<>();
		numberList.addAll(numbers);
		final Integer res = numberList.get(numberList.size() - 1);
		return res;
	}

	public Integer avgNumberOfMemberPerBrotherhood() {
		final Integer numberMember = this.memberRepository.numberOfMemberAccepted();
		final Integer numberBrotherhood = this.brotherhoodService.numberBrotherhood();
		final Integer res = numberMember / numberBrotherhood;
		return res;
	}

	//	public Integer desviationOfNumberOfMemberPerBrotherhood() {
	//		final Integer numberMember = this.memberRepository.numberOfMemberAccepted();
	//		final Integer numberMemberSqrt = numberMember * numberMember;
	//
	//		return res;
	//	}
}
