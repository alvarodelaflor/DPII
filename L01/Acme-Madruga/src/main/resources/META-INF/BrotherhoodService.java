
package services;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	BrotherhoodRepository	brotherhoodRepository;


	public Brotherhood create() {
		final Brotherhood brotherhood = new Brotherhood();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		brotherhood.setUserAccount(user);
		brotherhood.setDate(LocalDate.now().toDate());
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
		return this.brotherhoodRepository.save(brotherhood);
	}

	public Brotherhood update(final Brotherhood brotherhood) {
		Assert.isTrue(LoginService.getPrincipal().getId() == brotherhood.getUserAccount().getId());
		return this.brotherhoodRepository.save(brotherhood);
	}
}
