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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.BrotherhoodService;
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

		final Brotherhood brotherhood;

		brotherhood = this.brotherhoodService.reconstructR(registration, binding);

		if (binding.hasErrors())
			result = new ModelAndView("brotherhood/create");
		else
			try {
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("welcome/index");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.creatCreateModelAndView(brotherhood, "email.wrong");
				else
					result = new ModelAndView("brotherhood/create");
			}
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
		ModelAndView result;

		brotherhood = this.brotherhoodService.reconstruct(brotherhood, binding);

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
				else
					result = new ModelAndView("brotherhood/edit");
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

	private ModelAndView creatCreateModelAndView(final Brotherhood brotherhood, final String string) {
		ModelAndView result;

		result = new ModelAndView("brotherhood/create");
		result.addObject("message", string);
		result.addObject("brotherhood", brotherhood);

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final int userLoggin = LoginService.getPrincipal().getId();
		final Brotherhood brotherhood;
		brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(userLoggin);
		Collection<String> pictures;
		pictures = brotherhood.getPictures();
		result = new ModelAndView("brotherhood/show");
		result.addObject("brotherhood", brotherhood);
		result.addObject("pictures", pictures);
		result.addObject("requestURI", "brotherhood/show.do");

		return result;
	}

	@RequestMapping(value = "/showBrotherhood", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		ModelAndView result;

		final Brotherhood brotherhood = this.brotherhoodService.findOne(id);

		Collection<String> pictures;
		pictures = brotherhood.getPictures();
		result = new ModelAndView("brotherhood/showBrotherhood");
		result.addObject("brotherhood", brotherhood);
		result.addObject("pictures", pictures);
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
	public ModelAndView newSpamWord(@RequestParam("picture") final String picture) {
		ModelAndView result;

		final Brotherhood logger = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<String> pictures = logger.getPictures();
		pictures.add(picture);
		this.brotherhoodService.save(logger);
		result = new ModelAndView("redirect:show.do");

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("url") final String url) {
		ModelAndView result;

		final Brotherhood logger = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<String> pictures = logger.getPictures();
		pictures.remove(url);
		this.brotherhoodService.deletePicture(url);
		result = new ModelAndView("redirect:show.do");

		return result;
	}

}
