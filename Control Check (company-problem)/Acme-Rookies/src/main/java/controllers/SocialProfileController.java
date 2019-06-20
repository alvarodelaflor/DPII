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
import org.springframework.context.i18n.LocaleContextHolder;
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
import domain.Actor;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile")
public class SocialProfileController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private ActorService			actorService;


	//	@Autowired
	//	private WelcomeService			welcomeService;

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

		final Collection<SocialProfile> socialProfiles = this.socialProfileService.getSocialProfilesByActor(a.getId());

		result = new ModelAndView("socialProfile/list");
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("requestURI", "socialProfile/list.do");
		//		result.addObject("logo", this.welcomeService.getLogo());
		//		result.addObject("system", this.welcomeService.getSystem());
		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam("socialProfileId") final int socialProfileId) {
		ModelAndView result;
		try {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserId(user.getId());

			final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
			final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

			if (!socialProfile.getActor().equals(a))
				result = new ModelAndView("welcome/index");

			else {
				result = new ModelAndView("socialProfile/show");
				result.addObject("socialProfile", socialProfile);
				//		final String system = this.welcomeService.getSystem();
				//		result.addObject("system", system);
				//		final String logo = this.welcomeService.getLogo();
				//		result.addObject("logo", logo);
				result.addObject("language", language);
				result.addObject("requestURI", "socialProfile/show.do");
			}
		} catch (Exception e) {
			result = new ModelAndView("welcome/index");
		}
		
		//		result.addObject("logo", this.welcomeService.getLogo());
		//		result.addObject("system", this.welcomeService.getSystem());
		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();

		result = new ModelAndView("socialProfile/create");
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);
		result.addObject("socialProfile", socialProfile);
		//		result.addObject("logo", this.welcomeService.getLogo());
		//		result.addObject("system", this.welcomeService.getSystem());
		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "socialProfileId", defaultValue = "-1") final int socialProfileId) {
		ModelAndView result;

		if (this.socialProfileService.findOne(socialProfileId) == null || !this.socialProfileService.findOne(socialProfileId).getActor().equals(this.actorService.getActorByUserId(LoginService.getPrincipal().getId())))
			result = new ModelAndView("welcome/index");
		else {
			final SocialProfile socialProfile;
			Assert.isTrue(this.socialProfileService.findOne(socialProfileId) != null);
			socialProfile = this.socialProfileService.findOne(socialProfileId);

			result = new ModelAndView("socialProfile/edit");
			//			final String system = this.welcomeService.getSystem();
			//			result.addObject("system", system);
			//			final String logo = this.welcomeService.getLogo();
			//			result.addObject("logo", logo);
			result.addObject("socialProfile", socialProfile);
		}
		//		result.addObject("logo", this.welcomeService.getLogo());
		//		result.addObject("system", this.welcomeService.getSystem());
		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("El error pasa por aquí alvaro (IF de save())");
			System.out.println(binding);
			result = new ModelAndView("socialProfile/edit");
		} else
			try {
				Assert.isTrue(socialProfile != null);
				final SocialProfile savedSocialProfile = this.socialProfileService.save(socialProfile);
				final int userLoggin = LoginService.getPrincipal().getId();
				final Actor actor = this.actorService.getActorByUserId(userLoggin);
				Assert.isTrue(actor != null);

				result = new ModelAndView("socialProfile/list");
				result.addObject("socialProfiles", this.socialProfileService.getSocialProfilesByActor(actor.getId()));
				result.addObject("requestURI", "socialProfile/list.do");
			} catch (final Throwable oops) {
				System.out.println("El error es en SocialProfileController: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("socialProfile/edit");
			}
		//		result.addObject("logo", this.welcomeService.getLogo());
		//		result.addObject("system", this.welcomeService.getSystem());
		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "socialProfileId", defaultValue = "-1") final int socialProfileId) {
		ModelAndView result;

		final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
		System.out.println("socialProfileId encontrado: " + socialProfileId);
		Assert.notNull(socialProfileId, "socialProfile.null");

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(user.getId());

		if (this.socialProfileService.findOne(socialProfileId) == null || !this.socialProfileService.findOne(socialProfileId).getActor().equals(this.actorService.getActorByUserId(LoginService.getPrincipal().getId()))) {
			result = new ModelAndView("socialProfile/list");
			//			final String system = this.welcomeService.getSystem();
			//			result.addObject("system", system);
			//			final String logo = this.welcomeService.getLogo();
			//			result.addObject("logo", logo);
			result.addObject("socialProfiles", this.socialProfileService.getSocialProfilesByActor(this.actorService.getActorByUserId(LoginService.getPrincipal().getId()).getId()));
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
				//				final String system = this.welcomeService.getSystem();
				//				result.addObject("system", system);
				//				final String logo = this.welcomeService.getLogo();
				//				result.addObject("logo", logo);
				result.addObject("socialProfiles", this.socialProfileService.getSocialProfilesByActor(a.getId()));
				result.addObject("requestURI", "socialProfile/list.do");
			} catch (final Throwable oops) {
				System.out.println("Error al borrar socialProfile desde actor: ");
				System.out.println(oops);
				result = new ModelAndView("socialProfile/list");
				final int userLoggin = LoginService.getPrincipal().getId();
				final Actor actor = this.actorService.getActorByUserId(userLoggin);
				//				final String system = this.welcomeService.getSystem();
				//				result.addObject("system", system);
				//				final String logo = this.welcomeService.getLogo();
				//				result.addObject("logo", logo);
				result.addObject("socialProfiles", this.socialProfileService.getSocialProfilesByActor(a.getId()));
			}
		//		result.addObject("logo", this.welcomeService.getLogo());
		//		result.addObject("system", this.welcomeService.getSystem());
		result.addObject("logo", this.getLogo()); result.addObject("system", this.getSystem()); return result;
	}

}