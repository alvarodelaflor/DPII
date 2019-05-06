/*
 * CurricculaProviderController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ItemService;
import services.ProviderService;
import domain.Item;
import domain.Provider;

/*
 * CONTROL DE CAMBIOS ItemProviderController.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/item/provider")
public class ItemProviderController extends AbstractController {

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private ItemService		itemService;


	// Constructors -----------------------------------------------------------

	public ItemProviderController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		final Provider provider;

		try {

			provider = this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());
			Assert.isTrue(provider != null);

			final Collection<domain.Item> items = this.itemService.getProviderItems(provider.getId());

			result = new ModelAndView("item/provider/list");
			result.addObject("items", items);
			result.addObject("requestURI", "item/provider/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final Provider providerLogin = this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());
			Assert.notNull(providerLogin, "No provider is login");
			final Item item = this.itemService.create();
			result = new ModelAndView("item/provider/edit");
			result.addObject("item", item);
		} catch (final Exception e) {
			System.out.println("Error e en GET /create ItemController.java: " + e);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "itemId", defaultValue = "-1") final int itemId) {
		ModelAndView result;
		final Provider providerLogin = this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());
		try {
			final Item itemDB = this.itemService.findOne(itemId);
			Assert.notNull(itemDB, "Item not found in DB");
			Assert.notNull(providerLogin, "No provider is login");
			Assert.isTrue(providerLogin.equals(itemDB.getProvider()));
			result = new ModelAndView("item/provider/edit");
			result.addObject("item", itemDB);
			result.addObject("provider", providerLogin);
		} catch (final Exception e) {
			if (providerLogin != null)
				result = new ModelAndView("redirect:/item/listProvider.do?providerId=" + providerLogin.getId());
			else
				result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Item item, final BindingResult binding) {
		ModelAndView result;

		try {
			final Provider providerLogin = this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());
			Assert.notNull(providerLogin, "No provider is login");
			Assert.isTrue(item != null, "item.null");
			final Item itemRec = this.itemService.reconstruct(item, binding);
			final Item saveItem = this.itemService.save(itemRec);
			result = new ModelAndView("redirect:list.do");
			result.addObject("requestURI", "item/list.do");
		} catch (final ValidationException oops) {
			result = new ModelAndView("item/provider/edit");
			result.addObject("item", item);
			result.addObject("message", "item.commit.error");
		} catch (final Throwable oops) {
			System.out.println("Error en SAVE ItemProviderController.java Throwable: " + oops);

			result = new ModelAndView("item/provider/edit");
			result.addObject("item", item);
			result.addObject("message", "item.commit.error");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "itemId", defaultValue = "-1") final int itemId) {
		ModelAndView result;
		final Provider providerLogin = this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());

		try {
			Assert.notNull(providerLogin, "No provider is login");
			final Item itemDB = this.itemService.findOne(itemId);
			Assert.notNull(itemDB, "Not found item in DB");
			Assert.isTrue(itemDB.getProvider().equals(providerLogin), "Not allow to delete, diferent provider");
			this.itemService.delete(itemDB);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			System.out.println("Error en ItemProviderController.java Throwable: " + oops);
			if (providerLogin != null)
				result = new ModelAndView("redirect:/item/listProvider.do?providerId=" + providerLogin.getId());
			else
				result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("message", "item.commit.error");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
}
