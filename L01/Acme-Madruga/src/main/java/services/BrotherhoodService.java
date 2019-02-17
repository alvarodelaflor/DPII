
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;

/*
 * CONTROL DE CAMBIOS BrotherhoodService.java
 * 
 * ALVARO 17/02/2019 11:51 CREACIÓN DE LA CLASE
 */

@Service
@Transactional
public class BrotherhoodService {

	//Managed Repository -------------------	
	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;


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
}
