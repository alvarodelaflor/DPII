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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Item;
import domain.Provider;
import security.LoginService;
import services.ItemService;
import services.ProviderService;

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

		if (binding.hasErrors()) {
			System.out.println("Error en ItemProviderController.java, binding: " + binding);
			result = new ModelAndView("item/provider/create");
			result.addObject("item", item);
		} else
			try {
				final Provider providerLogin = this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());
				Assert.notNull(providerLogin, "No provider is login");
				Assert.isTrue(item != null, "item.null");
				final Item saveItem = this.itemService.save(item);
				result = new ModelAndView("redirect:/item/show.do?itemId=" + saveItem.getId());
				result.addObject("requestURI", "item/list.do");
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
			result = new ModelAndView("redirect:/item/listProvider.do?providerId=" + providerLogin.getId());
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
