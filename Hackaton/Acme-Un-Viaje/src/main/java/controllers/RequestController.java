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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Customer;
import domain.Request;
import domain.SocialProfile;
import security.LoginService;
import security.UserAccount;
import services.CustomerService;
import services.RequestService;

@Controller
@RequestMapping("/request/customer")
public class RequestController extends AbstractController {

	@Autowired
	private RequestService requestService;

	@Autowired
	private CustomerService customerService;

	// Constructors -----------------------------------------------------------

	public RequestController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			Request request;
			request = this.requestService.create();
			result = new ModelAndView("request/customer/create");
			result.addObject("request", request);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Request request, final BindingResult binding) {
		ModelAndView result;
		
		final Request requestN = this.requestService.reconstruct(request, binding);

		try {
			if (binding.hasErrors()) {
				result = new ModelAndView("request/customer/create");
				result.addObject("request", request);
			} else
				try {
					Assert.isTrue(request != null);
					final Request savedrequest = this.requestService.save(request);
					final int userLoggin = LoginService.getPrincipal().getId();
					final Customer actor = this.customerService.getCustomerByUserAccountId(userLoggin);
					Assert.isTrue(actor != null);

					result = new ModelAndView("request/customer/list");
					result.addObject("requests",
							this.requestService.getCustomerRequest());
					result.addObject("requestURI", "request/customer/list.do");
				} catch (final Throwable oops) {
					result = new ModelAndView("request/customer/create");
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
			result = new ModelAndView("request/customer/list");
			result.addObject("requests",
					this.requestService.getCustomerRequest());
			result.addObject("requestURI", "request/customer/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
}
