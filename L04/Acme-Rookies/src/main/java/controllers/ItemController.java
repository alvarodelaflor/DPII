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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Item;
import domain.Provider;
import security.LoginService;
import security.UserAccount;
import services.ItemService;
import services.ProviderService;

/*
 * CONTROL DE CAMBIOS ItemProviderController.java
 *
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private ItemService		itemService;


	// Constructors -----------------------------------------------------------

	public ItemController() {
		super();
	}

	@RequestMapping(value = "/providerList", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(value = "providerId", defaultValue = "-1") final int providerId) {
		ModelAndView result;
		try {
			Provider provider = this.providerService.findOne(providerId);
			final Provider providerLogin = this.providerService.getProviderByUserAccountId(LoginService.getPrincipal().getId());
			if (provider == null && providerLogin != null)
				provider = providerLogin;
			Assert.notNull(provider, "Not provider found in DB");
			result = new ModelAndView("item/list");
			if (providerLogin != null && provider.equals(providerLogin))
				result.addObject("providerLogger", true);
			Assert.notNull(provider, "Provider is null");
			final Collection<Item> items = this.itemService.getProviderItems(provider.getId());
			result.addObject("items", items);
			result.addObject("requestURI", "items/listProvider.do?providerId=" + provider.getId());
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "itemId", defaultValue = "-1") final int itemId) {

		ModelAndView result;
		try {
			final Item itemDB = this.itemService.findOne(itemId);
			Assert.notNull(itemDB, "Not found item in DB");
			result = new ModelAndView("item/show");
			result.addObject("item", itemDB);
			final UserAccount logged = LoginService.getPrincipal();
			if (logged != null && this.providerService.getProviderByUserAccountId(logged.getId()) != null && this.providerService.getProviderByUserAccountId(logged.getId()).equals(itemDB.getProvider()))
				result.addObject("providerLogin", true);

			result.addObject("requestURI", "item/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
}
