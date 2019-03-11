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
import services.FloatService;
import services.MemberService;
import services.MessageService;
import services.PositionAuxService;
import services.ProcessionService;
import services.WelcomeService;
import domain.Brotherhood;
import domain.Message;
import domain.Procession;

/*
 * CONTROL DE CAMBIOS ProcessionBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 12:29 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 16:35 A�ADIDO RECONSTRUIDOR PROCESSION
 */

@Controller
@RequestMapping("/procession/brotherhood")
public class ProcessionBrotherhoodController extends AbstractController {

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	BrotherhoodService			brotherhoodService;

	@Autowired
	PositionAuxService			positionAuxService;

	@Autowired
	FloatService				floatService;

	@Autowired
	MessageService				messageService;

	@Autowired
	MemberService				memberService;
	
	@Autowired
	WelcomeService welcomeService;


	// Constructors -----------------------------------------------------------

	public ProcessionBrotherhoodController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Procession> processions = this.processionService.findAllBrotherhoodLogged();
		final Collection<domain.BigDecimal> floats = this.floatService.findAllBrotherhoodLogged();
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Boolean checkValid = false;
		Boolean checkArea = false;
		if (floats.isEmpty())
			checkValid = true;
		if (brotherhood.getArea()==null) {
			checkArea = true;
		}
		result = new ModelAndView("procession/brotherhood/list");
		result.addObject("processions", processions);
		result.addObject("checkValid", checkValid);
		result.addObject("checkArea", checkArea);
		result.addObject("requestURI", "procession/brotherhood/list.do");
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "processionId", defaultValue = "-1") final int processionId) {
		ModelAndView result;
		final Procession procession = this.processionService.findOne(processionId);

		if (this.processionService.findOne(processionId) == null || LoginService.getPrincipal().getId() != procession.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(procession, "procession.nul");

			result = new ModelAndView("procession/brotherhood/show");
			result.addObject("procession", procession);
			result.addObject("requestURI", "procession/brotherhood/show.do");
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Procession procession;
		procession = this.processionService.create();
		result = this.createEditModelAndView(procession);
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id", defaultValue = "-1") final int processionId) {
		ModelAndView result;
		Procession procession;
		procession = this.processionService.findOne(processionId);
		if (this.processionService.findOne(processionId) == null || LoginService.getPrincipal().getId() != procession.getBrotherhood().getUserAccount().getId() || procession.getIsFinal().equals(true))
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(procession);
			result = this.createEditModelAndView(procession);
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int processionId) {
		ModelAndView result;

		final Procession procession = this.processionService.findOne(processionId);
		System.out.println("Procession encontrado: " + procession);
		if (this.processionService.findOne(processionId) == null || LoginService.getPrincipal().getId() != procession.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(procession, "procession.null");

			try {
				this.processionService.delete(procession);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(procession, "procession.commit.error");
			}
		}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public void saveInitial(final Procession procession, final BindingResult binding) {
	//		final Procession toSendProcession = this.processionService.reconstruct(procession, binding);
	//		this.save(toSendProcession, binding);
	//	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Procession procession, final BindingResult binding) {
		ModelAndView result;

		procession = this.processionService.reconstruct(procession, binding);
		if (binding.hasErrors()) {
			System.out.println("Binding con errores: " + binding.getAllErrors());
			result = this.createEditModelAndView(procession);
		} else
			try {
				System.out.println("El error pasa por aqu� alvaro (TRY de save())");
				System.out.println(binding);
				this.processionService.save(procession);
				System.out.println("Error despues del save");
				final Message msg = this.messageService.create();
				final Collection<String> membersEmails = this.memberService.brotherhoodAllMemberEmail(procession.getBrotherhood().getId());
				System.out.println(membersEmails);
				msg.setBody("La procesi�n " + procession.getTitle() + " ha sido publicada");
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
				result = this.createEditModelAndView(procession, "procession.commit.error");
			}
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
	public ModelAndView createEditModelAndView(final Procession procession) {
		ModelAndView result;

		result = new ModelAndView("procession/brotherhood/edit");

		final Collection<domain.BigDecimal> floats = this.floatService.findAllBrotherhoodLogged();

		result.addObject("procession", procession);
		result.addObject("floats", floats);
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
	private ModelAndView createEditModelAndView(final Procession procession, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("procession/brotherhood/edit");

		final Collection<domain.BigDecimal> floats = this.floatService.findAllBrotherhoodLogged();

		result.addObject("floats", floats);
		result.addObject("procession", procession);
		result.addObject("message", messageCode);
		result.addObject("logo", welcomeService.getLogo());
		result.addObject("system", welcomeService.getSystem());
		return result;
	}
}
