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
import services.ActorService;
import services.ApplicationService;
import services.CurriculaService;
import services.MessageService;
import services.PositionService;
import services.ProblemService;
import services.RookieService;
import services.TagService;
import domain.Application;
import domain.Curricula;
import domain.Message;
import domain.Position;
import domain.Problem;
import domain.Rookie;
import domain.Tag;

@Controller
@RequestMapping("/application")
public class ApplicationRookieController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private MessageService		msgService;

	@Autowired
	private TagService			tagService;

	@Autowired
	ActorService				actorService;


	// Constructors -----------------------------------------------------------

	public ApplicationRookieController() {
		super();
	}

	// LIST ---------------------------------------------------------------		

	@RequestMapping(value = "/rookie/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		System.out.println("Carmen, entro en el list");

		try {
			final UserAccount user = LoginService.getPrincipal();
			System.out.println(user.getUsername());
			final Rookie rookie = this.rookieService.getRookieByUserAccountId(user.getId());
			System.out.println("Rookie loggeado: " + rookie);
			Assert.isTrue(rookie != null);
			System.out.println("Rookie loggeado: " + rookie);
			final Collection<Application> applications = this.applicationService.getApplicationsByRookie(rookie.getId());
			System.out.println("Aplicaciones del rookie: " + applications);
			result = new ModelAndView("application/rookie/list");
			result.addObject("applications", applications);
			result.addObject("requestURI", "application/rookie/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SHOW ---------------------------------------------------------------		
	@RequestMapping(value = "/rookie/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id", defaultValue = "-1") final int id) {

		ModelAndView result;
		final Application application;
		try {
			final UserAccount user = LoginService.getPrincipal();
			System.out.println(user.getUsername());
			final Rookie rookie = this.rookieService.getRookieByUserAccountId(user.getId());
			Assert.notNull(rookie);
			application = this.applicationService.findOne(id);
			Assert.isTrue(application.getRookie().equals(rookie));
			System.out.println(application);
			Assert.notNull(application);
			result = new ModelAndView("application/rookie/show");
			result.addObject("application", application);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// CREATE ---------------------------------------------------------------		

	@RequestMapping(value = "/rookie/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam("id") final int id) {
		ModelAndView result;

		Boolean res = false;
		Collection<Curricula> curriculas = new ArrayList<>();

		try {

			final UserAccount user = LoginService.getPrincipal();
			System.out.println(user.getUsername());
			final Rookie rookie = this.rookieService.getRookieByUserAccountId(user.getId());
			Assert.notNull(rookie);

			System.out.println("Postion a aplicar: " + id);
			final Application application = this.applicationService.create();

			final Position position = this.positionService.findOne(id);
			application.setPosition(position);

			try {
				curriculas = this.curriculaService.findAllNotCopyByRookie(this.rookieService.getRookieByUserAccountId(LoginService.getPrincipal().getId()));
				Assert.isTrue(curriculas.size() != 0);
				System.out.println(curriculas);
				res = true;
				application.setRookie(this.rookieService.getRookieByUserAccountId(LoginService.getPrincipal().getId()));
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

	@RequestMapping(value = "/rookie/save", method = RequestMethod.POST)
	public ModelAndView save(@RequestParam(value = "position", defaultValue = "-1") final int position, @RequestParam(value = "curricula", defaultValue = "-1") final int curricula) {
		ModelAndView result = null;

		try {

			final UserAccount user = LoginService.getPrincipal();
			System.out.println(user.getUsername());
			final Rookie rookie = this.rookieService.getRookieByUserAccountId(user.getId());
			Assert.notNull(rookie);

			final Application application = this.applicationService.create();

			application.setCurricula(this.curriculaService.createCurriculaCopyAndSave(this.curriculaService.findOne(curricula)));

			application.setApplyMoment(LocalDateTime.now().toDate());

			application.setRookie(this.rookieService.getRookieByUserAccountId(LoginService.getPrincipal().getId()));

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
			result = new ModelAndView("redirect:/application/rookie/list.do");
			final Collection<Application> applications = this.applicationService.getApplicationsByRookie(this.rookieService.getRookieByUserAccountId(LoginService.getPrincipal().getId()).getId());
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

	@RequestMapping(value = "/rookie/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "applicationid", defaultValue = "-1") final int applicationid) {
		ModelAndView result;

		try {
			Rookie rookie;
			final int idUserAccount = LoginService.getPrincipal().getId();
			rookie = this.rookieService.getRookieByUserAccountId(idUserAccount);
			Assert.notNull(rookie);
			final Application application = this.applicationService.getApplicationRookieById(applicationid);
			Assert.isTrue(application.getRookie().equals(rookie));
			System.out.println(application.getStatus());
			Assert.isTrue(application.getStatus().equals("PENDING"));
			result = new ModelAndView("application/rookie/edit");
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

	@RequestMapping(value = "/rookie/saveE", method = RequestMethod.POST, params = "saveE")
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
			result = new ModelAndView("application/rookie/edit");
		else
			try {

				Rookie rookie;
				final int idUserAccount = LoginService.getPrincipal().getId();
				rookie = this.rookieService.getRookieByUserAccountId(idUserAccount);
				Assert.notNull(rookie);

				System.out.println(a1.getRookie().equals(rookie));
				Assert.isTrue(a1.getRookie().equals(rookie));

//				a1.setCreationMoment(LocalDateTime.now().toDate());
				System.out.println(a1.getCreationMoment());
				System.out.println(a1.getApplyMoment());
				Assert.notNull(a1.getCreationMoment());

				// We can only set an application to SUBMITTED when it is pending
				a1.setStatus("SUBMITTED");

				System.out.println("voy a guardar");
				final Application a2 = this.applicationService.save(a1);
				//result = new ModelAndView("application/rookie/list.do");

				System.out.println("Rookie loggeado: " + rookie);
				final Collection<Application> applications = this.applicationService.getApplicationsByRookie(rookie.getId());
				System.out.println("Aplicaciones del rookie: " + applications);

				// NOTIFICATION
				final Message msg = this.msgService.create();
				msg.setSubject("Application with new status");
				msg.setBody("The application sent about " + a2.getApplyMoment() + " ,right now has submitted status");
				msg.setRecipient(new ArrayList<String>());
				msg.getRecipient().add(rookie.getEmail());
				msg.setSender(rookie.getEmail());

				final Tag tag = this.tagService.create();
				tag.setActorId(rookie.getId());
				tag.setMessageId(msg.getId());
				tag.setTag("SYSTEM");
				final Tag tagSave = this.tagService.save(tag);

				msg.setTags(new ArrayList<Tag>());
				msg.getTags().add(tagSave);

				this.msgService.exchangeMessage(msg, rookie.getId());

				System.out.println("CARMEN LLEGO 3");
				msg.getTags().add(tagSave);
				final Message msgSave1 = this.msgService.save(msg);

				tagSave.setMessageId(msgSave1.getId());
				this.tagService.save(tagSave);
				// NOTIFICATION

				result = new ModelAndView("redirect:/application/rookie/list.do");

			} catch (final Throwable oops) {
				System.out.println(oops);
				//				if (oops.getMessage().equals("error.response"))
				//					result = this.createEditModelAndView(a1, "error.response");
				//				else
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final Application application, final String string) {
		ModelAndView result;

		result = new ModelAndView("application/rookie/edit");
		result.addObject("string", string);
		result.addObject("application", application);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

}
