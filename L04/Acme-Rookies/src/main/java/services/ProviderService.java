
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.CreditCard;
import domain.Provider;
import repositories.ProviderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ProviderService {

	@Autowired
	private ProviderRepository	providerRepository;

	@Autowired
	private ItemService			itemService;


	// CREATE ---------------------------------------------------------------
	public Provider create() {
		final Provider provider = new Provider();
		final UserAccount user = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PROVIDER);

		final CreditCard creditCard = new CreditCard();
		provider.setCreditCard(creditCard);
		autoridades.add(authority);
		user.setAuthorities(autoridades);
		provider.setUserAccount(user);
		return provider;
	}

	// QUERY ---------------------------------------------------------------

	public Provider getProviderByUserAccountId(final int userAccountId) {
		Provider res;
		res = this.providerRepository.findByUserAccountId(userAccountId);
		return res;
	}

	// FINDONE ---------------------------------------------------------------
	public Provider findOne(final int id) {
		final Provider provider = this.providerRepository.findOne(id);
		return provider;
	}

	// FINDALL  ---------------------------------------------------------------
	public Collection<Provider> findAll() {
		return this.providerRepository.findAll();
	}

	//PROVIDER
	public void delete(final Provider provider) {
		Assert.isTrue(LoginService.getPrincipal().getId() == provider.getUserAccount().getId());
		this.itemService.deleteProviderItems(provider.getId());

		this.providerRepository.delete(provider);
	}

	public void flush() {
		this.providerRepository.flush();
	}

}
