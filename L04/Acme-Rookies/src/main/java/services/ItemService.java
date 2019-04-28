
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Item;
import repositories.ItemRepository;
import security.LoginService;

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

}
