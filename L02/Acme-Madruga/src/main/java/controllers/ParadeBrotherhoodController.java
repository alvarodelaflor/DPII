/*
 * BrotherhoodController.java
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

import security.LoginService;
import services.BrotherhoodService;
import services.ConfigurationService;
import services.FloatService;
import services.MemberService;
import services.MessageService;
import services.ParadeService;
import services.PositionAuxService;
import services.SponsorshipService;
import services.WelcomeService;
import domain.Brotherhood;
import domain.Configuration;
import domain.Message;
import domain.Parade;
import domain.Sponsorship;

/*
 * CONTROL DE CAMBIOS ParadeBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 12:29 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 16:35 A�ADIDO RECONSTRUIDOR PARADE
 */

@Controller
@RequestMapping("/parade/brotherhood")
public class ParadeBrotherhoodController extends AbstractController {

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private PositionAuxService	positionAuxService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private FloatService		floatService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	ConfigurationService		configurationService;

	@Autowired
	private WelcomeService		welcomeService;


	// Constructors -----------------------------------------------------------

	public ParadeBrotherhoodController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Parade> parades = this.paradeService.findAllBrotherhoodLogged();
		final Collection<domain.Float> floats = this.floatService.findAllBrotherhoodLogged();
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Boolean checkValid = false;
		Boolean checkArea = false;
		if (floats.isEmpty())
			checkValid = true;
		if (brotherhood.getArea() == null)
			checkArea = true;
		result = new ModelAndView("parade/brotherhood/list");
		result.addObject("parades", parades);
		result.addObject("checkValid", checkValid);
		result.addObject("checkArea", checkArea);
		result.addObject("requestURI", "parade/brotherhood/list.do");
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "paradeId", defaultValue = "-1") final int paradeId) {
		ModelAndView result;
		final Parade parade = this.paradeService.findOne(paradeId);
		final Sponsorship s = this.sponsorshipService.randomSponsorship(paradeId);
		final Configuration config = this.configurationService.getConfiguration();

		if (this.paradeService.findOne(paradeId) == null || LoginService.getPrincipal().getId() != parade.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(parade, "parade.nul");

			result = new ModelAndView("parade/brotherhood/show");

			result.addObject("config", config);
			result.addObject("sponsorship", s);
			result.addObject("parade", parade);
			result.addObject("requestURI", "parade/brotherhood/show.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Parade parade;
		parade = this.paradeService.create();
		result = this.createEditModelAndView(parade);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id", defaultValue = "-1") final int paradeId) {
		ModelAndView result;
		Parade parade;
		parade = this.paradeService.findOne(paradeId);
		if (this.paradeService.findOne(paradeId) == null || LoginService.getPrincipal().getId() != parade.getBrotherhood().getUserAccount().getId() || parade.getIsFinal().equals(true))
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(parade);
			result = this.createEditModelAndView(parade);
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int paradeId) {
		ModelAndView result;

		final Parade parade = this.paradeService.findOne(paradeId);
		System.out.println("Parade encontrado: " + parade);
		if (this.paradeService.findOne(paradeId) == null || LoginService.getPrincipal().getId() != parade.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(parade, "parade.null");

			try {
				this.paradeService.delete(parade);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(parade, "parade.commit.error");
			}
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public void saveInitial(final Parade parade, final BindingResult binding) {
	//		final Parade toSendParade = this.paradeService.reconstruct(parade, binding);
	//		this.save(toSendParade, binding);
	//	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Parade parade, final BindingResult binding) {
		ModelAndView result;
		
		try {
			parade = this.paradeService.reconstruct(parade, binding);	
		} catch (Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}
		if (binding.hasErrors()) {
			System.out.println("Binding con errores: " + binding.getAllErrors());
			result = this.createEditModelAndView(parade);
		} else
			try {
				System.out.println("El error pasa por aqu� alvaro (TRY de save())");
				System.out.println(binding);
				this.paradeService.save(parade);
				System.out.println("Error despues del save");
				final Message msg = this.messageService.create();
				final Collection<String> membersEmails = this.memberService.brotherhoodAllMemberEmail(parade.getBrotherhood().getId());
				System.out.println(membersEmails);
				msg.setBody("La procesi�n " + parade.getTitle() + " ha sido publicada");
				msg.setSubject("Notifaci�n sobre publicaci�n de procesi�n");
				msg.setEmailReceiver(membersEmails);
				System.out.println("llega al send");
				final Message notification = this.messageService.sendNotificationByEmails(msg);
				this.messageService.save(notification);
				System.out.println("Error despues de message");
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("El error: ");
				System.out.println(oops);
				System.out.println(binding);
				result = this.createEditModelAndView(parade, "parade.commit.error");
			}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public ModelAndView copyParade(@RequestParam(value = "paradeId", defaultValue = "-1") final int paradeId) {
		ModelAndView res;

		try {
			this.paradeService.createCopy(paradeId);
			res = new ModelAndView("redirect:/parade/brotherhood/list.do");
		} catch (final Exception e) {
			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	public ModelAndView createEditModelAndView(final Parade parade) {
		ModelAndView result;

		result = new ModelAndView("parade/brotherhood/edit");

		final Collection<domain.Float> floats = this.floatService.findAllBrotherhoodLogged();

		result.addObject("parade", parade);
		result.addObject("floats", floats);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}
	private ModelAndView createEditModelAndView(final Parade parade, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("parade/brotherhood/edit");

		final Collection<domain.Float> floats = this.floatService.findAllBrotherhoodLogged();

		result.addObject("floats", floats);
		result.addObject("parade", parade);
		result.addObject("message", messageCode);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

}
