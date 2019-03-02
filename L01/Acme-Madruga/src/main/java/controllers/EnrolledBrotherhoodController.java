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

import org.joda.time.LocalDateTime;
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
import services.EnrolledService;
import services.PositionService;
import services.RequestService;
import domain.Enrolled;
import domain.Position;

/*
 * CONTROL DE CAMBIOS EnrolledBrotherhoodController.java
 * 
 * ALVARO 17/02/2019 19:18 CREACIÓN DE LA CLASE
 */

@Controller
@RequestMapping("/enrolled/brotherhood")
public class EnrolledBrotherhoodController extends AbstractController {

	@Autowired
	private EnrolledService	enrolledService;
	@Autowired
	private PositionService	positionService;
	@Autowired
	private RequestService	requestService;


	// Constructors -----------------------------------------------------------

	public EnrolledBrotherhoodController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Enrolled> enrolledsAccepted = this.enrolledService.findAllByBrotherhoodLoggedAccepted();
		System.out.println("EnrolledsAccepted: " + enrolledsAccepted);
		final Collection<Enrolled> enrolledsRejected = this.enrolledService.findAllByBrotherhoodLoggedRejected();
		final Collection<Enrolled> enrolledsPending = this.enrolledService.findAllByBrotherhoodLoggedPending();
		final Collection<Enrolled> dropOutMembers = this.enrolledService.findAllDropOutMemberByBrotherhoodLogged();

		result = new ModelAndView("enrolled/brotherhood/list");

		final String actuallanguage = LocaleContextHolder.getLocale().getDisplayLanguage();
		Boolean language;
		if (actuallanguage.equals("English")) {
			System.out.println("Actual languge: " + actuallanguage);
			language = true;
		} else {
			System.out.println("Actual languge: " + actuallanguage);
			language = false;
		}

		result.addObject("language", language);
		result.addObject("enrolledsAccepted", enrolledsAccepted);
		result.addObject("enrolledsRejected", enrolledsRejected);
		result.addObject("enrolledsPending", enrolledsPending);
		result.addObject("dropOutMembers", dropOutMembers);
		result.addObject("requestURI", "enrolled/brotherhood/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "enrolledId", defaultValue = "-1") final int enrolledId) {
		ModelAndView result;
		final Enrolled enrolled = this.enrolledService.findOne(enrolledId);

		if (this.enrolledService.findOne(enrolledId) == null || LoginService.getPrincipal().getId() != enrolled.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(enrolled, "enrolled.nul");

			result = new ModelAndView("enrolled/brotherhood/show");
			result.addObject("enrolled", enrolled);
			result.addObject("requestURI", "enrolled/brotherhood/show.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id", defaultValue = "-1") final int enrolledId) {
		ModelAndView result;
		Enrolled enrolled;
		enrolled = this.enrolledService.findOne(enrolledId);
		if (this.enrolledService.findOne(enrolledId) == null || LoginService.getPrincipal().getId() != enrolled.getBrotherhood().getUserAccount().getId() || enrolled.getState() != null)
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(enrolled);
			result = this.createEditModelAndView(enrolled);
		}
		return result;
	}

	@RequestMapping(value = "/dropOut", method = RequestMethod.GET)
	public ModelAndView dropOut(@RequestParam(value = "id", defaultValue = "-1") final int enrolledId) {
		ModelAndView result;
		Enrolled enrolled;
		enrolled = this.enrolledService.findOne(enrolledId);
		if (this.enrolledService.findOne(enrolledId) == null || LoginService.getPrincipal().getId() != enrolled.getBrotherhood().getUserAccount().getId() || !enrolled.getState().equals(true))
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(enrolled);
			try {
				enrolled.setDropMoment(LocalDateTime.now().toDate());
				this.requestService.deleteAllRequestPendingByMember(enrolled.getMember());
				this.enrolledService.save(enrolled);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(enrolled, "enrolled.commit.error");
			}
		}
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int enrolledId) {
		ModelAndView result;

		final Enrolled enrolled = this.enrolledService.findOne(enrolledId);
		System.out.println("Enrolled encontrado: " + enrolled);
		if (this.enrolledService.findOne(enrolledId) == null || LoginService.getPrincipal().getId() != enrolled.getBrotherhood().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(enrolled, "enrolled.null");

			try {
				this.enrolledService.delete(enrolled);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(enrolled, "enrolled.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Enrolled enrolled, final BindingResult binding) {
		ModelAndView result;

		enrolled = this.enrolledService.reconstruct(enrolled, binding);
		if (binding.hasErrors()) {
			System.out.println("El error pasa por aquí alvaro (IF de save())");
			System.out.println(binding);
			result = this.createEditModelAndView(enrolled);
		} else
			try {
				System.out.println("El error pasa por aquí alvaro (TRY de save())");
				System.out.println(binding);
				this.enrolledService.save(enrolled);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("El error: ");
				System.out.println(oops);
				System.out.println(binding);
				if (oops.getMessage().equals("enrolled.wrongDate"))
					result = this.createEditModelAndView(enrolled, "enrolled.wrongDate");
				else if (oops.getMessage().equals("enrolled.wrongMomentDate"))
					result = this.createEditModelAndView(enrolled, "enrolled.wrongMomentDate");
				else
					result = this.createEditModelAndView(enrolled, "enrolled.commit.error");
			}
		return result;
	}
	private ModelAndView createEditModelAndView(final Enrolled enrolled) {
		ModelAndView result;

		result = new ModelAndView("enrolled/brotherhood/edit");

		final Collection<Position> positions = this.positionService.findAll();
		final String actuallanguage = LocaleContextHolder.getLocale().getDisplayLanguage();
		Boolean language;
		if (actuallanguage.equals("English")) {
			System.out.println("Actual languge: " + actuallanguage);
			language = true;
		} else {
			System.out.println("Actual languge: " + actuallanguage);
			language = false;
		}

		result.addObject("language", language);
		result.addObject("enrolled", enrolled);
		result.addObject("positions", positions);

		return result;
	}
	private ModelAndView createEditModelAndView(final Enrolled enrolled, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("enrolled/brotherhood/edit");

		final Collection<Position> positions = this.positionService.findAll();

		result.addObject("enrolled", enrolled);
		result.addObject("positions", positions);
		result.addObject("message", messageCode);

		return result;
	}
}
