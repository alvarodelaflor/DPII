/*
 * p * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ItemService;
import services.ProviderService;
import domain.Item;
import domain.Provider;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

	@Autowired
	private ItemService		itemService;

	@Autowired
	private ProviderService	providerService;


	// Constructors -----------------------------------------------------------

	public ItemController() {
		super();
	}

	// listByProvider ---------------------------------------------------------------		
	@RequestMapping(value = "/listByProvider", method = RequestMethod.GET)
	public ModelAndView listByProvider(@RequestParam(value = "id", defaultValue = "-1") final int id) {

		ModelAndView result;
		final Provider provider;

		try {
			provider = this.providerService.findOne(id);
			Assert.isTrue(provider != null);

			final Collection<domain.Item> items = this.itemService.getProviderItems(id);

			result = new ModelAndView("item/listByProvider");
			result.addObject("items", items);
			result.addObject("requestURI", "item/listByProvider.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// LIST ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		try {
			final Collection<Item> items = this.itemService.findAll();
			result = new ModelAndView("item/list");
			result.addObject("items", items);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SHOW ---------------------------------------------------------------		
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id", defaultValue = "-1") final int id) {

		ModelAndView result;
		try {

			final Item item = this.itemService.findOne(id);
			List<String> pictures = new ArrayList<>();

			if (item.getPictures() != null)
				pictures = Arrays.asList(item.getPictures().split("'"));

			result = new ModelAndView("item/show");
			result.addObject("pictures", pictures);
			result.addObject("item", item);
			result.addObject("requestURI", "item/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
}
