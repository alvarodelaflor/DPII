
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Enrolled;
import domain.Member;

/*
 * CONTROL DE CAMBIOS BrotherhoodService.java
 * 
 * ALVARO 17/02/2019 11:51 CREACIÓN DE LA CLASE
 * HIPONA 21/02/2019 18:10 findFromLoggedMember
 * HIPONA 21/02/2019 18:27 drop
 */

@Service
public class BrotherhoodService {

	//Managed Repository -------------------	
	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private EnrolledService			enrollmentService;


	//Supporting services ------------------

	//Simple CRUD Methods ------------------

	public Brotherhood create() {

		final Brotherhood brotherhood = new Brotherhood();
		final UserAccount cuenta = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		autoridades.add(authority);
		cuenta.setAuthorities(autoridades);
		brotherhood.setUserAccount(cuenta);
		return brotherhood;
	}

	public Collection<Brotherhood> findAll() {
		return this.brotherhoodRepository.findAll();
	}

	public Brotherhood findOne(final int id) {
		final Brotherhood result = this.brotherhoodRepository.findOne(id);
		return result;
	}

	public Brotherhood save(final Brotherhood brotherhood) {
		return this.brotherhoodRepository.save(brotherhood);
	}

	public Brotherhood update(final Brotherhood brotherhood) {
		Assert.isTrue(brotherhood.getUserAccount().getId() == LoginService.getPrincipal().getId(), "userAccountLoggerNotSame");
		return this.brotherhoodRepository.save(brotherhood);
	}
	public Brotherhood getBrotherhoodByUserAccountId(final int userAccountId) {
		Brotherhood res;
		res = this.brotherhoodRepository.findByUserAccountId(userAccountId);
		return res;
	}

	public Collection<Brotherhood> findFromLoggedMember() {
		final Member member = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		return this.brotherhoodRepository.findFromMember(member.getId());
	}

	public void dropMember(final int memberId, final int brotherhoodId) {
		System.out.println("Dropping member");
		final Enrolled enrollment = this.enrollmentService.getBrotherhoodActiveEnrollment(memberId, brotherhoodId);
		// We have to check if we are an active member
		Assert.notNull(enrollment);
		enrollment.setDropMoment(new Date());
		this.enrollmentService.save(enrollment);
	}

	public void dropLogged(final int brotherhoodId) {
		final Member member = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		this.dropMember(member.getId(), brotherhoodId);
	}
}
