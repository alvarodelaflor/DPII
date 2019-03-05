/*
 * handyWorkerController.java
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

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.SocialProfileService;
import services.WelcomeService;
import domain.Actor;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile")
public class SocialProfileController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private WelcomeService			welcomeService;


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public SocialProfileController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final UserAccount login = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(login.getId());

		final Collection<SocialProfile> socialProfiles = a.getSocialProfiles();

		result = new ModelAndView("socialProfile/list");
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("requestURI", "socialProfile/list.do");
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();

		result = new ModelAndView("socialProfile/actor/create");
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);
		result.addObject("socialProfile", socialProfile);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "socialProfileId", defaultValue = "-1") final int socialProfileId) {
		ModelAndView result;

		if (this.socialProfileService.findOne(socialProfileId) == null || !this.actorService.getActorByUserId(LoginService.getPrincipal().getId()).getSocialProfiles().contains(this.socialProfileService.findOne(socialProfileId)))
			result = new ModelAndView("welcome/index");
		else {
			final SocialProfile socialProfile;
			Assert.isTrue(this.socialProfileService.findOne(socialProfileId) != null);
			socialProfile = this.socialProfileService.findOne(socialProfileId);

			result = new ModelAndView("socialProfile/actor/edit");
			//			final String system = this.welcomeService.getSystem();
			//			result.addObject("system", system);
			//			final String logo = this.welcomeService.getLogo();
			//			result.addObject("logo", logo);
			result.addObject("socialProfile", socialProfile);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("El error pasa por aquí alvaro (IF de save())");
			System.out.println(binding);
			result = new ModelAndView("socialProfile/actor/edit");
		} else
			try {
				Assert.isTrue(socialProfile != null);
				final SocialProfile savedSocialProfile = this.socialProfileService.save(socialProfile);
				final int userLoggin = LoginService.getPrincipal().getId();
				final Actor actor = this.actorService.getActorByUserId(userLoggin);
				Assert.isTrue(actor != null);

				if (actor.getSocialProfiles().contains(savedSocialProfile)) {
					actor.getSocialProfiles().remove(savedSocialProfile);
					actor.getSocialProfiles().add(savedSocialProfile);
				} else
					actor.getSocialProfiles().add(socialProfile);
				final Actor savedActor = this.actorService.save(actor);

				result = new ModelAndView("actor/show");
				result.addObject("actor", savedActor);
				//				final String system = this.welcomeService.getSystem();
				//				result.addObject("system", system);
				//				final String logo = this.welcomeService.getLogo();
				//				result.addObject("logo", logo);
				result.addObject("socialProfiles", savedActor.getSocialProfiles());
				result.addObject("requestURI", "actor/show.do");
			} catch (final Throwable oops) {
				System.out.println("El error es en SocialProfileController: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("socialProfile/actor/edit");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "socialProfileId", defaultValue = "-1") final int socialProfileId) {
		ModelAndView result;

		final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
		System.out.println("socialProfileId encontrado: " + socialProfileId);
		Assert.notNull(socialProfileId, "socialProfile.null");

		if (this.socialProfileService.findOne(socialProfileId) == null || !this.actorService.getActorByUserId(LoginService.getPrincipal().getId()).getSocialProfiles().contains(this.socialProfileService.findOne(socialProfileId))) {
			result = new ModelAndView("actor/show");
			//			final String system = this.welcomeService.getSystem();
			//			result.addObject("system", system);
			//			final String logo = this.welcomeService.getLogo();
			//			result.addObject("logo", logo);
			result.addObject("actor", this.actorService.getActorByUserId(LoginService.getPrincipal().getId()));
			result.addObject("socialProfiles", this.actorService.getActorByUserId(LoginService.getPrincipal().getId()).getSocialProfiles());
			result.addObject("requestURI", "actor/show.do");
		} else
			try {
				Assert.isTrue(socialProfile != null);

				final int userLoggin = LoginService.getPrincipal().getId();
				final Actor actor = this.actorService.getActorByUserId(userLoggin);
				Assert.isTrue(actor != null);
				actor.getSocialProfiles().remove(socialProfile);
				result = new ModelAndView("actor/show");
				final Actor savedActor = this.actorService.save(actor);
				this.socialProfileService.delete(socialProfile);
				result.addObject("actor", savedActor);
				//				final String system = this.welcomeService.getSystem();
				//				result.addObject("system", system);
				//				final String logo = this.welcomeService.getLogo();
				//				result.addObject("logo", logo);
				result.addObject("socialProfiles", savedActor.getSocialProfiles());
				result.addObject("requestURI", "actor/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error al borrar socialProfile desde actor: ");
				System.out.println(oops);
				result = new ModelAndView("actor/show");
				final int userLoggin = LoginService.getPrincipal().getId();
				final Actor actor = this.actorService.getActorByUserId(userLoggin);
				result.addObject("actor", actor);
				//				final String system = this.welcomeService.getSystem();
				//				result.addObject("system", system);
				//				final String logo = this.welcomeService.getLogo();
				//				result.addObject("logo", logo);
				result.addObject("socialProfiles", actor.getSocialProfiles());
			}
		return result;
	}

}
