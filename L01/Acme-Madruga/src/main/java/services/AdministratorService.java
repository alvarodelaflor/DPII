
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	administratorRepository;


	public Administrator create() {
		final Administrator administrator = new Administrator();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		administrator.setUserAccount(user);
		return administrator;
	}

	public List<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final int id) {
		final Administrator administrator = this.administratorRepository.findOne(id);
		return administrator;
	}

	public Administrator save(final Administrator administrator) {
		return this.administratorRepository.save(administrator);
	}

	public Administrator update(final Administrator administrator) {
		Assert.isTrue(LoginService.getPrincipal().getId() == administrator.getUserAccount().getId());
		return this.administratorRepository.save(administrator);
	}

	public Administrator getAdministratorByUserAccountId(final int userAccountId) {
		Administrator res;
		res = this.administratorRepository.findByUserAccountId(userAccountId);
		return res;
	}

}
