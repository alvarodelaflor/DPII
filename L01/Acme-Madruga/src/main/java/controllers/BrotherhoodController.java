/*
 * BrotherhoodController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.EnrolledService;
import services.FloatBroService;
import services.MemberService;
import domain.Brotherhood;
import forms.RegistrationForm;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	@Autowired
	BrotherhoodService	brotherhoodService;

	@Autowired
	MemberService		memberService;

	@Autowired
	FloatBroService		floatBroService;

	@Autowired
	EnrolledService		enrolledService;

	@Autowired
	ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public BrotherhoodController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final RegistrationForm registrationForm;
		registrationForm = new RegistrationForm();
		result = new ModelAndView("brotherhood/create");
		result.addObject("registrationForm", registrationForm);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegistrationForm registration, final BindingResult binding) {
		ModelAndView result;

		Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.reconstructR(registration, binding);

		System.out.println(binding);

		if (binding.hasErrors())
			result = new ModelAndView("brotherhood/create");
		else
			try {
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("welcome/index");
			} catch (final Throwable oops) {
				result = new ModelAndView("brotherhood/create");
			}
		return result;
	}
	private ModelAndView createEditModelAndView(final Brotherhood brotherhood, final String string) {
		ModelAndView result;

		result = new ModelAndView("brotherhood/edit");
		result.addObject("message", string);
		result.addObject("brotherhood", brotherhood);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		Brotherhood brotherhood;
		final int idUserAccount = LoginService.getPrincipal().getId();
		brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(idUserAccount);
		Assert.notNull(brotherhood);
		result = new ModelAndView("brotherhood/edit");
		result.addObject("brotherhood", brotherhood);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveE(Brotherhood brotherhood, final BindingResult binding) {
		ModelAndView result = null;

		brotherhood = this.brotherhoodService.reconstruct(brotherhood, binding);

		if (!(brotherhood.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w\\.\\w]{1,}(>)") || brotherhood.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}(>)")
			|| brotherhood.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w\\.\\w]{1,}") || brotherhood.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}"))) {
			final ObjectError error = new ObjectError("email", "");
			binding.addError(error);
			binding.rejectValue("email", "email.wrong");
		}

		if (this.actorService.getActorByEmail(brotherhood.getEmail()) != null) {
			final ObjectError error = new ObjectError("email", "");
			binding.addError(error);
			binding.rejectValue("email", "error.email");
		}

		if (binding.hasErrors()) {
			System.out.println(binding);
			result = new ModelAndView("brotherhood/edit");
		} else
			try {
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(brotherhood, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					if (oops.getMessage().equals("email.wrong"))
						result = this.createEditModelAndView(brotherhood, "email.wrong");
					else
						result = this.createEditModelAndView(brotherhood, "error.email");
			}
		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final int userLoggin = LoginService.getPrincipal().getId();
		final Brotherhood brotherhood;
		brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(userLoggin);
		List<String> pictures = new ArrayList<>();
		if (brotherhood.getPictures() != null)
			pictures = Arrays.asList(brotherhood.getPictures().split("'"));
		result = new ModelAndView("brotherhood/show");
		result.addObject("brotherhood", brotherhood);
		result.addObject("pictures", pictures);
		result.addObject("requestURI", "brotherhood/show.do");

		return result;
	}

	private Boolean validMember(final int idBrotherhood) {
		Boolean res = false;
		final int memberId = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId()).getId();
		System.out.println("ChekckIsInBrotherhood: " + this.memberService.checkIsInBrotherhood(memberId));
		if (!this.enrolledService.hasPendingEnrollRequest(memberId, idBrotherhood) && this.enrolledService.getBrotherhoodActiveEnrollment(memberId, idBrotherhood) == null)
			res = true;
		return res;
	}

	@RequestMapping(value = "/showBrotherhood", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;

		final Brotherhood brotherhood = this.brotherhoodService.findOne(id);

		List<String> pictures = new ArrayList<>();
		if (brotherhood.getPictures() != null)
			pictures = Arrays.asList(brotherhood.getPictures().split("'"));
		result = new ModelAndView("brotherhood/showBrotherhood");
		result.addObject("brotherhood", brotherhood);
		result.addObject("pictures", pictures);
		if (this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()) == null) {
			result.addObject("validMember", this.validMember(brotherhood.getId()));
			result.addObject("activeMember", this.brotherhoodService.isActiveFromMemberAndBrotherhood(brotherhood.getId()));
		}
		result.addObject("requestURI", "brotherhood/showBrotherhood.do");
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;

		final Collection<Brotherhood> brotherhood = this.brotherhoodService.findAll();
		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhood", brotherhood);
		result.addObject("requestURI", "brotherhood/list.do");

		return result;
	}

	@RequestMapping(value = "/addPhoto", method = RequestMethod.GET)
	public ModelAndView addPhoto(@RequestParam("picture") final String picture) {
		ModelAndView result;

		final Brotherhood logger = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());

		if (logger.getPictures() == null)
			logger.setPictures(picture);
		else
			logger.setPictures(logger.getPictures() + "'" + picture);

		this.brotherhoodService.save(logger);
		result = new ModelAndView("redirect:show.do");

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("url") final String url) {
		ModelAndView result;

		final Brotherhood logger = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		if (logger.getPictures() != null)
			this.brotherhoodService.deletePicture(url);

		result = new ModelAndView("redirect:show.do");

		return result;
	}

	@RequestMapping(value = "/conditions", method = RequestMethod.GET)
	public ModelAndView conditions() {
		ModelAndView result;
		result = new ModelAndView("brotherhood/conditions");
		return result;
	}

}
