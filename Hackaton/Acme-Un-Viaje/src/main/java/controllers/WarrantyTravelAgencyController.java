/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.TravelAgency;
import domain.Warranty;
import security.LoginService;
import services.TravelAgencyService;
import services.WarrantyService;

@Controller
@RequestMapping("/warranty/travelAgency")
public class WarrantyTravelAgencyController extends AbstractController {

	@Autowired
	private TravelAgencyService travelAgencyService;

	@Autowired
	private WarrantyService warrantyService;

	// Constructors -----------------------------------------------------------

	public WarrantyTravelAgencyController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			Warranty warranty;
			warranty = this.warrantyService.create();
			result = new ModelAndView("warranty/travelAgency/create");
			result.addObject("warranty", warranty);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Warranty warranty, final BindingResult binding) {
		ModelAndView result;

		final Warranty warrantyN = this.warrantyService.reconstruct(warranty, binding);

		try {
			if (binding.hasErrors()) {
				result = new ModelAndView("warranty/travelAgency/create");
				result.addObject("warranty", warranty);
			} else {
				Assert.isTrue(warranty != null);
				final Warranty savewarranty = this.warrantyService.save(warrantyN);
				result = new ModelAndView("redirect:/warranty/travelAgency/list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			Collection<Warranty> warranties = this.warrantyService.getTravelAgencyWarranty();
			System.out.println(warranties);
			result = new ModelAndView("warranty/travelAgency/list");
			result.addObject("warranties", warranties);
			result.addObject("requestURI", "warranty/travelAgency/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "warrantyId", defaultValue = "-1") final int warrantyId) {
		ModelAndView result;
		try {
			final Warranty warranty = this.warrantyService.findOne(warrantyId);
			System.err.println(warranty);
			Assert.notNull(warranty);
			this.warrantyService.delete(warranty);
			result = new ModelAndView("redirect:/warranty/travelAgency/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "warrantyId", defaultValue = "-1") final int warrantyId) {
		ModelAndView result;
		try {
			final TravelAgency actor = this.travelAgencyService
					.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId());
			Warranty warranty = this.warrantyService.findOne(warrantyId);
			Assert.notNull(actor);
			Assert.isTrue(warranty.getTravelAgency().equals(actor));
			Assert.isTrue(warranty.getDraftMode() == true);

			result = new ModelAndView("warranty/travelAgency/edit");
			result.addObject("warranty", warranty);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Warranty warranty, final BindingResult binding) {
		ModelAndView result = null;

		try {
			final Warranty warrantyN = this.warrantyService.reconstructR(warranty, binding);

			if (binding.hasErrors()) {
				result = new ModelAndView("warranty/travelAgency/edit");
			} else {
				this.warrantyService.save(warrantyN);
				result = new ModelAndView("redirect:list.do");
			}

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
