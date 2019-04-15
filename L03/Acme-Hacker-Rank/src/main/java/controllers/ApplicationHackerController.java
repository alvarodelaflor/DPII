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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDateTime;
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
import security.UserAccount;
import services.ApplicationService;
import services.CurriculaService;
import services.HackerService;
import services.PositionService;
import services.ProblemService;
import domain.Application;
import domain.Curricula;
import domain.Hacker;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping("/application")
public class ApplicationHackerController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private CurriculaService	curriculaService;


	// Constructors -----------------------------------------------------------

	public ApplicationHackerController() {
		super();
	}

	// LIST ---------------------------------------------------------------		

	@RequestMapping(value = "/hacker/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		System.out.println("Carmen, entro en el list");

		try {
			final UserAccount user = LoginService.getPrincipal();
			System.out.println(user.getUsername());
			final Hacker hacker = this.hackerService.getHackerByUserAccountId(user.getId());
			System.out.println("Hacker loggeado: " + hacker);
			Assert.isTrue(hacker != null);
			System.out.println("Hacker loggeado: " + hacker);
			final Collection<Application> applications = this.applicationService.getApplicationsByHacker(hacker.getId());
			System.out.println("Aplicaciones del hacker: " + applications);
			result = new ModelAndView("application/hacker/list");
			result.addObject("applications", applications);
			result.addObject("requestURI", "application/hacker/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SHOW ---------------------------------------------------------------		
	@RequestMapping(value = "/hacker/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id", defaultValue = "-1") final int id) {

		ModelAndView result;
		final Application application;
		try {
			application = this.applicationService.findOne(id);
			System.out.println(application);
			Assert.notNull(application);
			result = new ModelAndView("application/hacker/show");
			result.addObject("application", application);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// CREATE ---------------------------------------------------------------		

	@RequestMapping(value = "/hacker/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam("id") final int id) {
		ModelAndView result;

		Boolean res = false;
		Collection<Curricula> curriculas = new ArrayList<>();

		try {
			System.out.println("Postion a aplicar: " + id);
			final Application application = this.applicationService.create();

			final Position position = this.positionService.findOne(id);
			application.setPosition(position);

			try {
				curriculas = this.curriculaService.findAllNotCopyByHacker(this.hackerService.getHackerByUserAccountId(LoginService.getPrincipal().getId()));
				Assert.isTrue(curriculas.size() != 0);
				System.out.println(curriculas);
				res = true;
				application.setHacker(this.hackerService.getHackerByUserAccountId(LoginService.getPrincipal().getId()));
			} catch (final Exception e) {
				res = false;
			}
			result = new ModelAndView("position/show");
			System.out.println(res);
			System.out.println("carmen vamos a la vista de crear application");
			result.addObject("curriculas", curriculas);
			result.addObject("application", application);
			result.addObject("position", position);
			result.addObject("res", res);
		} catch (final Exception e) {
			res = false;
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SAVE-CREATE ---------------------------------------------------------------		

	@RequestMapping(value = "/hacker/save", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam(value = "position", defaultValue = "-1") final int position, @RequestParam(value = "curricula", defaultValue = "-1") final int curricula) {
		ModelAndView result = null;

		try {
			final Application application = this.applicationService.create();

			application.setCurricula(this.curriculaService.createCurriculaCopyAndSave(this.curriculaService.findOne(curricula)));

			application.setApplyMoment(LocalDateTime.now().toDate());

			application.setHacker(this.hackerService.getHackerByUserAccountId(LoginService.getPrincipal().getId()));

			application.setPosition(this.positionService.findOne(position));

			final List<Problem> problems = (List<Problem>) this.problemService.allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(application.getPosition().getId());
			Assert.isTrue(problems.size() != 0);

			final Integer alatorio = (int) Math.floor(Math.random() * (problems.size()));
			final Problem seleccion = problems.get(alatorio);
			application.setProblem(seleccion);

			application.setStatus("PENDING");

			System.out.println("carmen: voy a guardar");

			final Application a1 = this.applicationService.save(application);
			System.out.println(a1);
			result = new ModelAndView("application/hacker/list");
			final Collection<Application> applications = this.applicationService.getApplicationsByHacker(this.hackerService.getHackerByUserAccountId(LoginService.getPrincipal().getId()).getId());
			result.addObject("applications", applications);

		} catch (final Throwable oops) {
			System.out.println(oops);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// EDIT ---------------------------------------------------------------		

	@RequestMapping(value = "/hacker/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "applicationid", defaultValue = "-1") final int applicationid) {
		ModelAndView result;

		try {
			Hacker hacker;
			final int idUserAccount = LoginService.getPrincipal().getId();
			hacker = this.hackerService.getHackerByUserAccountId(idUserAccount);
			Assert.notNull(hacker);
			final Application application = this.applicationService.getApplicationHackerById(applicationid);
			System.out.println(application.getStatus());
			Assert.isTrue(application.getStatus().equals("PENDING"));
			result = new ModelAndView("application/hacker/edit");
			result.addObject("application", application);
		} catch (final Throwable oops) {
			System.out.println(oops);
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
	// SAVE-EDIT ---------------------------------------------------------------		

	@RequestMapping(value = "/hacker/saveE", method = RequestMethod.GET, params = "saveE")
	public ModelAndView save(final Application application, final BindingResult binding) {
		ModelAndView result;

		System.out.println("Carmen vamos al reconstruct");

		final Application a1 = this.applicationService.reconstructEdit(application, binding);

		if (application.getResponse() == "") {
			final ObjectError error = new ObjectError("response", "");
			binding.addError(error);
			binding.rejectValue("response", "error.response");
		}

		if (application.getLink() == "") {
			final ObjectError error = new ObjectError("link", "");
			binding.addError(error);
			binding.rejectValue("link", "error.link");
		}

		if (binding.hasErrors())
			result = new ModelAndView("application/hacker/edit");
		else
			try {

				Hacker hacker;
				final int idUserAccount = LoginService.getPrincipal().getId();
				hacker = this.hackerService.getHackerByUserAccountId(idUserAccount);
				Assert.notNull(hacker);

				a1.setCreationMoment(LocalDateTime.now().toDate());
				Assert.notNull(a1.getCreationMoment());

				final Application a2 = this.applicationService.save(a1);
				result = new ModelAndView("application/hacker/list.do");

				System.out.println("Hacker loggeado: " + hacker);
				final Collection<Application> applications = this.applicationService.getApplicationsByHacker(hacker.getId());
				System.out.println("Aplicaciones del hacker: " + applications);
				result = new ModelAndView("application/hacker/list");
				result.addObject("applications", applications);
				result.addObject("requestURI", "application/hacker/list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);
				if (oops.getMessage().equals("error.response"))
					result = this.createEditModelAndView(a1, "error.response");
				else
					result = new ModelAndView("redirect:/welcome/index.do");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final Application application, final String string) {
		ModelAndView result;

		result = new ModelAndView("hacker/create");
		result.addObject("string", string);
		result.addObject("application", application);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

}
