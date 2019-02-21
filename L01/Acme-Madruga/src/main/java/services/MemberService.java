
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Member;

/*
 * CONTROL DE CAMBIOS MemberService.java
 * 
 * ALVARO 17/02/2019 11:51 CREACIÓN DE LA CLASE
 * HIPONA 21/02/2019 9:57 isBrotherhoodActiveMember
 */

@Service
@Transactional
public class MemberService {

	//Managed Repository -------------------	
	@Autowired
	private MemberRepository	memberRepository;


	//Supporting services ------------------

	//Simple CRUD Methods ------------------

	public Member create() {

		final Member member = new Member();
		final UserAccount cuenta = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		autoridades.add(authority);
		cuenta.setAuthorities(autoridades);
		member.setUserAccount(cuenta);
		return member;
	}

	public Collection<Member> findAll() {
		return this.memberRepository.findAll();
	}

	public Member findOne(final int id) {
		final Member result = this.memberRepository.findOne(id);
		return result;
	}

	public Member save(final Member member) {
		return this.memberRepository.save(member);
	}

	public Member update(final Member member) {
		Assert.isTrue(member.getUserAccount().getId() == LoginService.getPrincipal().getId(), "userAccountLoggerNotSame");
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
}
