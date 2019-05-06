
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ItemRepository;
import security.Authority;
import security.LoginService;
import domain.Item;

@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository	itemRepository;

	@Autowired
	private ProviderService	providerService;


	// FINDALL  ---------------------------------------------------------------
	public Collection<Item> findAll() {
		return this.itemRepository.findAll();
	}

	// getItemsByProvider  ---------------------------------------------------------------
	public Collection<Item> getProviderItems(final int id) {
		return this.itemRepository.findProviderItems(id);
	}

	// FINDONE  ---------------------------------------------------------------
	public Item findOne(final int id) {
		return this.itemRepository.findOne(id);
	}

	// CREATE ---------------------------------------------------------------
	public Item create() {
		final Item item = new Item();

		item.setProvider(this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId()));

		Assert.isTrue(this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId()) != null);

		return item;
	}

	// SAVE ---------------------------------------------------------------
	public Item save(final Item a) {
		final Item app = this.itemRepository.save(a);
		this.itemRepository.flush();
		return app;

	}

	public void deleteProviderItems(final int providerId) {
		final Collection<Item> items = this.itemRepository.findProviderItems(providerId);
		this.itemRepository.deleteInBatch(items);
	}

	public void flush() {
		this.itemRepository.flush();
	}

	public void delete(final Item item) {
		Assert.isTrue(this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId()).getId() == item.getProvider().getId());
		this.itemRepository.delete(item);

	}

	public Float minItemPerProvider() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.itemRepository.minItemPerProvider();
	}

	public Float maxItemPerProvider() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.itemRepository.maxItemPerProvider();
	}

	public Float avgItemPerProvider() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.itemRepository.avgItemPerProvider();
	}

	public Float sttdevItemPerProvider() {
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));
		return this.itemRepository.stddevItemPerProvider();
	}

}
