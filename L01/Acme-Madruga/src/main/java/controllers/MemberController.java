/*
 * MemberController.java
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import domain.Brotherhood;
import domain.Member;
import forms.RegistrationForm;
import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	@Autowired
	MemberService		memberService;

	@Autowired
	BrotherhoodService	brotherhoodService;

	@Autowired
	ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public MemberController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final RegistrationForm registrationForm = new RegistrationForm();
		result = new ModelAndView("member/create");
		result.addObject("registrationForm", registrationForm);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;

		final Member member;

		member = this.memberService.reconstructR(registrationForm, binding);

		if (binding.hasErrors())
			result = new ModelAndView("member/create");
		else
			try {
				this.memberService.saveR(member);
				result = new ModelAndView("welcome/index");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createModelAndView(member, "email.wrong");
				else
					result = this.createModelAndView(member, "error.email");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		Member member;

		final int idUserAccount = LoginService.getPrincipal().getId();

		member = this.memberService.getMemberByUserAccountId(idUserAccount);
		Assert.notNull(member);

		result = new ModelAndView("member/edit");

		result.addObject("member", member);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveE(Member member, final BindingResult binding) {
		ModelAndView result;

		member = this.memberService.reconstruct(member, binding);

		if (binding.hasErrors())
			result = new ModelAndView("member/edit");
		else
			try {
				this.memberService.save(member);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(member, "email.wrong");
				else
					result = this.createEditModelAndView(member, "error.email");
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Member member, final String string) {
		ModelAndView result;

		result = new ModelAndView("member/edit");
		result.addObject("message", string);
		result.addObject("member", member);

		return result;
	}

	private ModelAndView createModelAndView(final Member member, final String string) {
		ModelAndView result;

		result = new ModelAndView("member/create");
		result.addObject("message", string);
		result.addObject("member", member);

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final int userLoggin = LoginService.getPrincipal().getId();
		final Member member;
		member = this.memberService.getMemberByUserAccountId(userLoggin);
		Assert.isTrue(member != null);

		result = new ModelAndView("member/show");
		result.addObject("member", member);

		result.addObject("requestURI", "member/show.do");

		return result;
	}

	@RequestMapping(value = "/listMembers", method = RequestMethod.GET)
	public ModelAndView listMembers(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		final ModelAndView result;

		final Brotherhood brotherhood = this.brotherhoodService.findOne(id);
		final Collection<Member> member = this.memberService.brotherhoodAllMember(brotherhood.getId());
		result = new ModelAndView("member/listMembers");
		result.addObject("brotherhood", brotherhood);
		result.addObject("member", member);
		result.addObject("requestURI", "member/listMembers.do");
		return result;
	}
	@RequestMapping(value = "/conditions", method = RequestMethod.GET)
	public ModelAndView conditions() {
		ModelAndView result;
		result = new ModelAndView("member/conditions");
		return result;
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody
	Member export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		Member result = new Member();
		result = this.memberService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		return result;
	}
}
