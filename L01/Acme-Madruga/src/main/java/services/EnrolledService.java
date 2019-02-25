
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EnrolledRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Enrolled;
import domain.Member;

/*
 * CONTROL DE CAMBIOS EnrolledService.java
 * 
 * ALVARO 17/02/2019 11:51 CREACIÓN DE LA CLASE
 */

@Service
public class EnrolledService {

	//Managed Repository -------------------	
	@Autowired
	private EnrolledRepository	enrolledRepository;

	//Supporting services ------------------
	@Autowired
	MemberService				memberService;
	@Autowired
	Validator					validator;
	@Autowired
	BrotherhoodService			brotherhoodService;


	//Simple CRUD Methods ------------------

	public Enrolled create(final int brotherhoodId) {
		// We have to check member authority
		Assert.isTrue(this.checkAuthority("MEMBER"));
		final Enrolled enrolled = new Enrolled();
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		final Member owner = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());

		// We check if we are not an active member of the brotherhood
		Assert.isTrue(this.memberService.isBrotherhoodActiveMember(owner.getId(), brotherhood.getId()) == false);

		// We check if we don't have any pending enroll request
		Assert.isTrue(this.hasPendingEnrollRequest(owner.getId(), brotherhood.getId()) == false);

		enrolled.setBrotherhood(brotherhood);
		enrolled.setMember(owner);

		return enrolled;
	}

	public Collection<Enrolled> findAll() {
		return this.enrolledRepository.findAll();
	}

	public Enrolled findOne(final int id) {
		final Enrolled result = this.enrolledRepository.findOne(id);
		return result;
	}

	public Enrolled save(final Enrolled enrolled) {
		return this.enrolledRepository.save(enrolled);
	}

	public void delete(final Enrolled enrolled) {
		Assert.notNull(enrolled, "enrrolled.null");
		this.enrolledRepository.delete(enrolled);
	}

	public Collection<Enrolled> findAllDropOutMemberByBrotherhoodLogged() {
		System.out.println("IdLogged:" + LoginService.getPrincipal().getId());
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		return this.enrolledRepository.getDropOutMember(brotherhood.getId());
	}

	public Collection<Enrolled> findAllByBrotherhoodLoggedAccepted() {
		System.out.println("IdLogged:" + LoginService.getPrincipal().getId());
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		return this.enrolledRepository.findAllByBrotherhoodLogged(brotherhood.getId(), true);
	}
	public Collection<Enrolled> findAllByBrotherhoodLoggedRejected() {
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		return this.enrolledRepository.findAllByBrotherhoodLogged(brotherhood.getId(), false);
	}

	public Collection<Enrolled> findAllByBrotherhoodLoggedPending() {
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		return this.enrolledRepository.findAllByBrotherhoodLoggedPending(brotherhood.getId());
	}

	public Enrolled reconstruct(final Enrolled enrolled, final BindingResult binding) {
		Enrolled result;

		if (enrolled.getId() == 0)
			result = enrolled;
		else {
			result = this.enrolledRepository.findOne(enrolled.getId());
			result.setState(enrolled.getState());
			if (enrolled.getPosition() != null)
				result.setPosition(enrolled.getPosition());
			this.validator.validate(enrolled, binding);
		}
		return result;
	}

	private boolean checkAuthority(final String authority) {
		final UserAccount acc = LoginService.getPrincipal();
		final Authority member = new Authority();
		member.setAuthority(authority);
		return acc.getAuthorities().contains(member);
	}

	public Boolean hasPendingEnrollRequest(final int memberId, final int brotherHoodId) {
		return this.enrolledRepository.getBrotherhoodActiveEnrollment(memberId, brotherHoodId) != null;
	}

	public Enrolled getBrotherhoodActiveEnrollment(final int memberId, final int brotherHoodId) {
		return this.enrolledRepository.getBrotherhoodActiveEnrollment(memberId, brotherHoodId);
	}

}
