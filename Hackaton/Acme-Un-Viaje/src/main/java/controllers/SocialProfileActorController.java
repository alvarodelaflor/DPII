/*
 * ProfileController.java
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Cleaner;
import domain.SocialProfile;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.CleanerService;
import services.SocialProfileService;

@Controller
@RequestMapping("/socialProfile")
public class SocialProfileActorController extends AbstractController {

	@Autowired
	private SocialProfileService socialProfileService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private CleanerService cleanerService;

	// Constructors -----------------------------------------------------------
	public SocialProfileActorController() {
		super();
	}

	@RequestMapping(value = "/listOnly", method = RequestMethod.GET)
	public ModelAndView listOnly(@RequestParam(value = "actorId", defaultValue = "-1") final int actorId) {
		ModelAndView result;
		try {

			Actor a;

			Cleaner cleaner = this.cleanerService.findOne(actorId);
			System.out.println(cleaner);
			a = this.actorService.findByUserAccountId(cleaner.getUserAccount().getId());

			final Collection<SocialProfile> socialProfiles = this.socialProfileService
					.getSocialProfilesByActor(a.getId());
			result = new ModelAndView("socialProfile/listOnly");
			result.addObject("socialProfiles", socialProfiles);
			result.addObject("requestURI", "socialProfile/listOnly.do");
		} catch (final Throwable oops) {
			System.out.println(oops);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {

			Actor a;

			final UserAccount login = LoginService.getPrincipal();
			a = this.actorService.getActorByUserId(login.getId());

			final Collection<SocialProfile> socialProfiles = this.socialProfileService
					.getSocialProfilesByActor(a.getId());
			result = new ModelAndView("socialProfile/list");
			result.addObject("socialProfiles", socialProfiles);
			result.addObject("requestURI", "socialProfile/list.do");
		} catch (final Throwable oops) {
			System.out.println(oops);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam("socialProfileId") final int socialProfileId) {
		ModelAndView result;
		try {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserId(user.getId());
			final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
			final Collection<SocialProfile> socialProfiles = this.socialProfileService
					.getSocialProfilesByActor(a.getId());
			if (!socialProfile.getActor().equals(a))
				result = new ModelAndView("welcome/index");
			else {
				result = new ModelAndView("socialProfile/show");
				result.addObject("socialProfile", socialProfile);
				result.addObject("socialProfiles", socialProfiles);
				result.addObject("requestURI", "socialProfile/list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			SocialProfile socialProfile;
			socialProfile = this.socialProfileService.create();
			final UserAccount login = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserId(login.getId());
			final Collection<SocialProfile> socialProfiles = this.socialProfileService
					.getSocialProfilesByActor(a.getId());
			result = new ModelAndView("socialProfile/create");
			result.addObject("socialProfile", socialProfile);
			result.addObject("socialProfiles", socialProfiles);
			result.addObject("requestURI", "socialProfile/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "socialProfileId", defaultValue = "-1") final int socialProfileId) {
		ModelAndView result;
		try {
			if (this.socialProfileService.findOne(socialProfileId) == null
					|| !this.socialProfileService.findOne(socialProfileId).getActor()
							.equals(this.actorService.getActorByUserId(LoginService.getPrincipal().getId())))
				result = new ModelAndView("welcome/index");
			else {
				final SocialProfile socialProfile;
				Assert.isTrue(this.socialProfileService.findOne(socialProfileId) != null);
				socialProfile = this.socialProfileService.findOne(socialProfileId);
				final UserAccount login = LoginService.getPrincipal();
				final Actor a = this.actorService.getActorByUserId(login.getId());
				final Collection<SocialProfile> socialProfiles = this.socialProfileService
						.getSocialProfilesByActor(a.getId());
				result = new ModelAndView("socialProfile/edit");
				result.addObject("socialProfile", socialProfile);
				result.addObject("socialProfiles", socialProfiles);
				result.addObject("requestURI", "socialProfile/list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;
		try {
			if (binding.hasErrors()) {
				final UserAccount login = LoginService.getPrincipal();
				final Actor a = this.actorService.getActorByUserId(login.getId());
				final Collection<SocialProfile> socialProfiles = this.socialProfileService
						.getSocialProfilesByActor(a.getId());
				result = new ModelAndView("socialProfile/edit");
				result.addObject("socialProfiles", socialProfiles);
				result.addObject("requestURI", "socialProfile/list.do");
			} else
				try {
					Assert.isTrue(socialProfile != null);
					final SocialProfile savedSocialProfile = this.socialProfileService.save(socialProfile);
					final int userLoggin = LoginService.getPrincipal().getId();
					final Actor actor = this.actorService.getActorByUserId(userLoggin);
					Assert.isTrue(actor != null);

					result = new ModelAndView("socialProfile/list");
					result.addObject("socialProfiles",
							this.socialProfileService.getSocialProfilesByActor(actor.getId()));
					result.addObject("requestURI", "socialProfile/list.do");
				} catch (final Throwable oops) {
					result = new ModelAndView("socialProfile/edit");
				}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(
			@RequestParam(value = "socialProfileId", defaultValue = "-1") final int socialProfileId) {
		ModelAndView result;

		final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
		System.out.println("socialProfileId encontrado: " + socialProfileId);
		Assert.notNull(socialProfileId, "socialProfile.null");

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(user.getId());

		if (this.socialProfileService.findOne(socialProfileId) == null
				|| !this.socialProfileService.findOne(socialProfileId).getActor()
						.equals(this.actorService.getActorByUserId(LoginService.getPrincipal().getId()))) {
			result = new ModelAndView("socialProfile/list");
			result.addObject("socialProfiles", this.socialProfileService.getSocialProfilesByActor(
					this.actorService.getActorByUserId(LoginService.getPrincipal().getId()).getId()));
			result.addObject("requestURI", "socialProfile/list.do");
		} else if (!socialProfile.getActor().equals(a))
			result = new ModelAndView("welcome/index");
		else
			try {
				Assert.isTrue(socialProfile != null);

				final int userLoggin = LoginService.getPrincipal().getId();
				final Actor actor = this.actorService.getActorByUserId(userLoggin);
				Assert.isTrue(actor != null);
				result = new ModelAndView("socialProfile/list");
				this.socialProfileService.delete(socialProfile);
				result.addObject("socialProfiles", this.socialProfileService.getSocialProfilesByActor(a.getId()));
				result.addObject("requestURI", "socialProfile/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error al borrar socialProfile desde actor: ");
				System.out.println(oops);
				result = new ModelAndView("socialProfile/list");
				final int userLoggin = LoginService.getPrincipal().getId();
				final Actor actor = this.actorService.getActorByUserId(userLoggin);
				result.addObject("socialProfiles", this.socialProfileService.getSocialProfilesByActor(a.getId()));
			}
		return result;

	}

}
