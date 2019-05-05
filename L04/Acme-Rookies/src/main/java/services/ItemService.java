
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ItemRepository;
import security.LoginService;
import domain.Item;

@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository	itemRepository;

	@Autowired
	private ProviderService	providerService;

	@Autowired
	Validator				validator;


	//RECONSTRUCT
	public Item reconstruct(final Item item, final BindingResult binding) {

		Item res;

		if (item.getId() == 0)
			res = this.create();
		else
			res = this.findOne(item.getId());

		res.setDescription(item.getDescription());
		res.setName(item.getName());
		res.setLink(item.getLink());
		res.setPictures(item.getPictures());

		this.validator.validate(res, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}
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
		Assert.isTrue(a.getProvider().getUserAccount().getId() == LoginService.getPrincipal().getId());
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

}
