/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ApplicationService;
import services.CompanyService;
import services.ConfigurationService;
import services.CurriculaService;
import services.FinderService;
import services.ItemService;
import services.PositionService;
import services.ProviderService;
import services.RookieService;
import services.SponsorshipService;
import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Configuration;
import domain.Rookie;
import domain.Sponsorship;
import forms.ActorForm;
import security.LoginService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CompanyService			companyService;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private SponsorshipService		sponsorshipService;
	
	@Autowired
	private ItemService			itemService;
	
	@Autowired
	private ProviderService			providerService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Dashboard --------------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {

		final ModelAndView res = new ModelAndView("administrator/dashboard");
		res.addObject("requestURI", "administrator/dashboard.do");

		// PositionsPerCompany
		res.addObject("avgPositionPerCompany", this.positionService.avgPositionPerCompany());
		res.addObject("minPositionPerCompany", this.positionService.minPositionPerCompany());
		res.addObject("maxPositionPerCompany", this.positionService.maxPositionPerCompany());
		res.addObject("stddevPositionPerCompany", this.positionService.stddevPositionPerCompany());
		// PositionsPerCompany

		// ApplicationPerRookie
		res.addObject("avgApplicationsPerRookie", this.applicationService.avgApplicationPerRookie());
		res.addObject("minApplicationsPerRookie", this.applicationService.minApplicationPerRookie());
		res.addObject("maxApplicationsPerRookie", this.applicationService.maxApplicationPerRookie());
		res.addObject("stddevApplicationsPerRookie", this.applicationService.stddevApplicationPerRookie());
		// ApplicationPerRookie

		// CompaniesMorePositions
		res.addObject("findCompanyWithMorePositions", this.positionService.findCompanyWithMorePositions());
		// CompaniesMorePositions

		// RookieMoreApplications
		res.addObject("findRookieMoreApplications", this.applicationService.findRookieWithMoreApplications());
		// RookieMoreApplications

		// Salaries
		res.addObject("avgSalaryPerPosition", this.positionService.avgSalaryPerPosition());
		res.addObject("minSalaryPerPosition", this.positionService.minSalaryPerPosition());
		res.addObject("maxSalaryPerPosition", this.positionService.maxSalaryPerPosition());
		res.addObject("stddevSalaryPerPosition", this.positionService.stddevSalaryPerPosition());
		res.addObject("bestPosition", this.positionService.bestPosition());
		res.addObject("worstPosition", this.positionService.worstPosition());
		// Salaries
		//AM
		res.addObject("minNumberOfResultFinder", this.finderService.minNumberOfResult());
		res.addObject("maxNumberOfResultFinder", this.finderService.maxNumberOfResult());
		res.addObject("avgNumberOfResultFinder", this.finderService.avgNumberOfResult());
		res.addObject("stddevNumberOfResultFinder", this.finderService.stddevNumberOfResult());
		res.addObject("ratioResultFinder", this.finderService.ratioResult());
		res.addObject("minNumberOfHistory", this.curriculaService.minNumberOfResultHistory());
		res.addObject("maxNumberOfHistory", this.curriculaService.maxNumberOfResultHistory());
		res.addObject("avgNumberOfHistory", this.curriculaService.avgNumberOfResultHsitory());
		res.addObject("stddevNumberOfHistory", this.curriculaService.stddevNumberOfResultHistory());
		//AM

		// The average, the minimum, the maximum, and the standard deviation of the
		// audit score of the positions stored in the system
		Object[] positionScoreStats = this.positionService.avgMinMaxStddevPositionAuditScore();
		res.addObject("avgPositionScore", positionScoreStats[0]);
		res.addObject("minPositionScore", positionScoreStats[1]);
		res.addObject("maxPositionScore", positionScoreStats[2]);
		res.addObject("stddevPositionScore", positionScoreStats[3]);
		
		// The average, the minimum, the maximum, and the standard deviation of the
		// audit score of the companies that are registered in the system.
		Object[] companyScoreStats = this.companyService.avgMinMaxStddevCompanyAuditScore();
		res.addObject("avgCompanyScore", companyScoreStats[0]);
		res.addObject("minCompanyScore", companyScoreStats[1]);
		res.addObject("maxCompanyScore", companyScoreStats[2]);
		res.addObject("stddevCompanyScore", companyScoreStats[3]);

		// The companies with the highest audit score
		res.addObject("companiesHighestScore", this.companyService.getCompaniesWithHighestAuditScore());

		// The average salary offered by the positions that have the highest average
		// audit score (This translates to the avg salary of the company with highest audit score)
		res.addObject("avgSalaryCompanyHighestScore", this.companyService.avgSalaryOfCompanyHighestScore());
		
		res.addObject("minItemPerProvider", this.itemService.minItemPerProvider());
		res.addObject("maxItemPerProvider", this.itemService.maxItemPerProvider());
		res.addObject("avgItemPerProvider", this.itemService.avgItemPerProvider());
		res.addObject("sttdevItemPerProvider", this.itemService.sttdevItemPerProvider());

		res.addObject("minSponsorshipPerProvider", this.sponsorshipService.minSponsorshipPerProvider());
		res.addObject("maxSponsorshipPerProvider", this.sponsorshipService.maxSponsorshipPerProvider());
		res.addObject("avgSponsorshipPerProvider", this.sponsorshipService.avgSponsorshipPerProvider());
		res.addObject("sttdevSponsorshipPerProvider", this.sponsorshipService.sttdevSponsorshipPerProvider());

		res.addObject("minSponsorshipPerPosition", this.sponsorshipService.minSponsorshipPerPosition());
		res.addObject("maxSponsorshipPerPosition", this.sponsorshipService.maxSponsorshipPerPosition());
		res.addObject("avgSponsorshipPerPosition", this.sponsorshipService.avgSponsorshipPerPosition());
		res.addObject("sttdevSponsorshipPerPosition", this.sponsorshipService.sttdevSponsorshipPerPosition());

		res.addObject("sponsorshipProvider", this.providerService.sponsorshipProvider());
		//AM2
		return res;
	}

	// CREATE ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final ActorForm actorForm = new ActorForm();
			result = new ModelAndView("administrator/create");
			result.addObject("actorForm", actorForm);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SAVE-CREATE ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result = null;

		final Administrator administrator;

		administrator = this.adminService.reconstructCreate(actorForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = new ModelAndView("administrator/create");
		} else
			try {
				System.out.println("carmen: voy a guardar");
				final Administrator a = this.adminService.saveCreate(administrator);
				System.out.println(a);
				result = new ModelAndView("welcome/index");
				final SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final String moment = formatter.format(new Date());
				result.addObject("moment", moment);
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(administrator, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.createEditModelAndView(administrator, "error.email");
				else
					result = this.createEditModelAndView(administrator, "error.html");
			}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final Administrator actor, final String string) {
		ModelAndView result;

		result = new ModelAndView("administrator/create");
		result.addObject("message", string);
		result.addObject("actor", actor);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// Actor List ---------------------------------------------------------------
	@RequestMapping(value = "/actorList", method = RequestMethod.GET)
	public ModelAndView actorList() {

		final ModelAndView res;

		final Collection<Company> companies = this.companyService.findAll();
		final Collection<Rookie> rookies = this.rookieService.findAll();

		res = new ModelAndView("administrator/actorList");

		res.addObject("companies", companies);
		res.addObject("rookies", rookies);
		res.addObject("requestURI", "administrator/actorList.do");
		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	// Ban/Unban ---------------------------------------------------------------

	private ModelAndView createEditModelAndView2(final Actor actor, final String string) {
		ModelAndView result;

		final Collection<Company> companies = this.companyService.findAll();
		final Collection<Rookie> rookies = this.rookieService.findAll();

		result = new ModelAndView("administrator/actorList");
		result.addObject("message", string);
		result.addObject("actor", actor);
		result.addObject("companies", companies);
		result.addObject("rookies", rookies);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/banCompany", method = RequestMethod.GET)
	public ModelAndView banMember(@RequestParam(value = "actorId", defaultValue = "-1") final int actorId) {

		ModelAndView res;

		try {
			final Actor actor = this.companyService.findOne(actorId);

			if (actor.getUserAccount().getBanned() == false) {

				this.adminService.banByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			} else {

				this.adminService.unbanByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			}
		} catch (final Throwable oops) {

			final Actor actor = this.companyService.findOne(actorId);
			if (oops.getMessage() == "ban.error")
				res = this.createEditModelAndView2(actor, "ban.error");
			else
				res = new ModelAndView("redirect:../#");
		}
		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());

		return res;
	}
	@RequestMapping(value = "/banRookie", method = RequestMethod.GET)
	public ModelAndView banBrotherhood(@RequestParam(value = "actorId", defaultValue = "-1") final int actorId) {

		ModelAndView res;

		try {
			final Actor actor = this.rookieService.findOne(actorId);

			if (actor.getUserAccount().getBanned() == false) {

				this.adminService.banByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			} else {

				this.adminService.unbanByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			}
		} catch (final Throwable oops) {

			final Actor actor = this.rookieService.findOne(actorId);
			if (oops.getMessage() == "ban.error")
				res = this.createEditModelAndView2(actor, "ban.error");
			else
				res = new ModelAndView("redirect:../#");
		}
		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());

		return res;
	}

	//FERRETE
	//CONFIGURATION
	@RequestMapping(value = "/list")
	public ModelAndView list2() {
		ModelAndView result;
		result = new ModelAndView("administrator/list");

		HashSet<String> spamWords = new HashSet<>();
		final HashSet<String> scoreWordsPos = new HashSet<>();
		final HashSet<String> scoreWordsNeg = new HashSet<>();

		//Configuration
		// ------------------------------------------------------------
		final Configuration configuration = this.configurationService.getConfiguration();
		final Double fair = configuration.getFair();
		final Double VAT = configuration.getVAT();
		// ------------------------------------------------------------

		// ------------------------------------------------------------

		//Priorities
		// ------------------------------------------------------------
		Collection<String> priorities = new HashSet<>();
		priorities = configuration.getPriorities();
		result.addObject("priorities", priorities);
		// ------------------------------------------------------------
		//Logo
		final String logo = this.getLogo();

		//Spam words

		spamWords = new HashSet<>(configuration.getSpamWords());

		//Welcome page
		final String ingles = configuration.getSystemMessageEn();
		final String spanish = configuration.getSystemMessageEs();

		//System
		final String system = this.getSystem();

		//Phone
		final String phone = configuration.getCountryCode();

		System.out.println("Carmen: Entro en el list");

		result.addObject("fair", fair);
		result.addObject("VAT", VAT);
		result.addObject("logo", logo);
		result.addObject("ingles", ingles);
		result.addObject("spanish", spanish);
		result.addObject("spamWords", spamWords);
		result.addObject("scoreWordsPos", scoreWordsPos);
		result.addObject("scoreWordsNeg", scoreWordsNeg);
		result.addObject("configuration", configuration);
		result.addObject("system", system);
		result.addObject("phone", phone);
		result.addObject("requestURI", "administrator/list.do");
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// PRIORITIES
	@RequestMapping(value = "/newPriority", method = RequestMethod.GET)
	public ModelAndView addPriority(@RequestParam("newPriority") final String newPriority) {
		ModelAndView result;

		this.configurationService.addPriority(newPriority);
		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/deletePriority", method = RequestMethod.GET)
	public ModelAndView deletePriority(@RequestParam("deletePriority") final String deletePriority) {
		ModelAndView result = new ModelAndView("administrator/list");

		try {
			System.out.println("Carmen: Voy a intentar guardar");
			this.configurationService.deletePriority(deletePriority);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage() == "noPriority.error") {
				result = this.list2();
				result.addObject("message", "noPriority.error");
			}
		}
		result.addObject("logo", this.configurationService.getConfiguration().getBanner());
		result.addObject("system", this.configurationService.getConfiguration().getSystemName());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/newSpamWord", method = RequestMethod.GET)
	public ModelAndView newSpamWord(@RequestParam("newSpamWord") final String newSpamWord) {
		ModelAndView result;

		System.out.println("Carmen: Voy a intentar guardar");
		this.configurationService.newSpamWords(newSpamWord);
		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.configurationService.getConfiguration().getBanner());
		result.addObject("system", this.configurationService.getConfiguration().getSystemName());
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/deleteSpamWord", method = RequestMethod.GET)
	public ModelAndView deleteSpamWord(@RequestParam("deleteSpamWord") final String spamWord) {
		ModelAndView result = new ModelAndView("administrator/list");

		try {
			System.out.println("Carmen: Voy a intentar guardar");
			this.configurationService.deleteSpamWords(spamWord);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage() == "noSpamWord.error") {
				result = this.list2();
				result.addObject("message", "noSpamWord.error");
			}
		}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/newWelcome", method = RequestMethod.GET)
	public ModelAndView newWelcome(@RequestParam("newIngles") final String newIngles, @RequestParam("newSpanish") final String newSpanish) {
		ModelAndView result;

		System.out.println(newIngles);
		System.out.println(newSpanish);

		System.out.println("Carmen: Voy a intentar guardar");

		this.configurationService.newE(newIngles);

		this.configurationService.newS(newSpanish);

		result = new ModelAndView("redirect:list.do");

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/newSystem", method = RequestMethod.GET)
	public ModelAndView newSystem(@RequestParam("newSystem") final String newSystem) {
		ModelAndView result;

		System.out.println(newSystem);

		System.out.println("Carmen: Voy a intentar guardar");

		this.configurationService.newSystem(newSystem);

		result = new ModelAndView("redirect:list.do");

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public ModelAndView header() {
		ModelAndView result;

		final String system = this.configurationService.getConfiguration().getSystemName();

		result = new ModelAndView("master-page/header");

		result.addObject("requestURI", "master-page/header.do");
		result.addObject("system", system);

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/newLogo", method = RequestMethod.GET)
	public ModelAndView newLogo(@RequestParam("newLogo") final String newLogo) {
		ModelAndView result;
		try {
			this.configurationService.newLogo(newLogo);

			System.out.println("Carmen: Voy a intentar guardar");

			result = new ModelAndView("redirect:list.do");

		} catch (final Exception e) {
			result = this.createEditModelAndView(newLogo, "logo.bad");
		}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final String newLogo, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("administrator/list");
		final Configuration config = this.configurationService.getConfiguration();

		//Logo
		final String logo = this.configurationService.getConfiguration().getBanner();

		//Priorities
		final HashSet<String> priorities = new HashSet<>(config.getPriorities());

		//Spam words
		final HashSet<String> spamWords = new HashSet<>(config.getSpamWords());

		System.out.println("Carmen: Esta es la lista de spam words");
		System.out.println(spamWords);

		//Welcome page
		final String ingles = config.getSystemMessageEn();
		final String spanish = config.getSystemMessageEs();

		//System
		final String system = this.configurationService.getConfiguration().getSystemName();

		//Phone
		final String phone = config.getCountryCode();

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		System.out.println("Carmen: Entro en el list");

		result.addObject("logo", logo);

		result.addObject("ingles", ingles);
		result.addObject("spanish", spanish);

		result.addObject("priorities", priorities);

		result.addObject("spamWords", spamWords);

		result.addObject("system", system);

		result.addObject("phone", phone);

		result.addObject("language", language);
		result.addObject("requestURI", "administrator/list.do");

		result.addObject("message", messageCode);

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/newPhone", method = RequestMethod.GET)
	public ModelAndView newPhone(@RequestParam("newPhone") final String newPhone) {
		ModelAndView result;

		this.configurationService.newPhone(newPhone);

		System.out.println("Carmen: Voy a intentar guardar");

		result = new ModelAndView("redirect:list.do");

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// ------------------------------------------------------------
	// Fair and VAT Methods:
	// ------------------------------------------------------------
	@RequestMapping(value = "/newFair", method = RequestMethod.GET)
	public ModelAndView newFair(@RequestParam("newFair") final Double newFair) {

		ModelAndView res = new ModelAndView("redirect:list.do");

		try {

			final Configuration config = this.configurationService.getConfiguration();
			config.setFair(newFair);
			this.configurationService.save(config);
		} catch (final Throwable oops) {

			if (oops.getMessage() == "number.positive.error") {
				res = new ModelAndView("redirect:list.do");
				res.addObject("message", "number.positive.error");

			}
		}
		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	@RequestMapping(value = "/newVAT", method = RequestMethod.GET)
	public ModelAndView newVAT(@RequestParam("newVAT") final Double newVAT) {

		ModelAndView res = new ModelAndView("redirect:list.do");

		try {

			final Configuration config = this.configurationService.getConfiguration();
			config.setVAT(newVAT);
			this.configurationService.save(config);

		} catch (final Throwable oops) {

			if (oops.getMessage() == "number.positive.error") {
				res = new ModelAndView("redirect:list.do");
				res.addObject("message", "number.positive.error");
			}
		}
		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	@RequestMapping(value = "/collect", method = RequestMethod.GET)
	public ModelAndView collect(@RequestParam(value = "sponsorshipId", defaultValue = "-1") final int sponsorshipId) {

		final ModelAndView res = new ModelAndView("redirect:listSponsorships.do");

		final Sponsorship s = this.sponsorshipService.findOne(sponsorshipId);
		s.setBannerCount(0);
		this.sponsorshipService.save(s);
		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	@RequestMapping(value = "/listSponsorships", method = RequestMethod.GET)
	public ModelAndView listSponsorships() {

		ModelAndView res;

		try {

			final Collection<Sponsorship> sponsorships;
			sponsorships = this.sponsorshipService.findAll();
			res = new ModelAndView("administrator/listSponsorships");
			res.addObject("sponsorships", sponsorships);
			final Configuration config = this.configurationService.getConfiguration();
			res.addObject("config", config);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		res.addObject("logo", this.getLogo());
		res.addObject("system", this.getSystem());
		return res;
	}

	@RequestMapping(value = "/calculateCompaniesScores", method = RequestMethod.GET)
	public ModelAndView calculateCompaniesScores() {

		ModelAndView res;

		try {
			this.adminService.calculateCompaniesScore();
			res = new ModelAndView("redirect:/administrator/dashboard.do");
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}
		return res;
	}
	
	// SHOW ---------------------------------------------------------------		
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id", defaultValue = "-1") final int id) {

		ModelAndView result;
		final Administrator administrator;
		Boolean checkAdministrator = false;
		try {
			if (id == -1) {
				final int userLoggin = LoginService.getPrincipal().getId();
				administrator = this.adminService.findOneByUserAccount(userLoggin);
				Assert.isTrue(administrator != null);
				checkAdministrator = true;
			} else {
				administrator = this.adminService.findOne(id);
				Assert.isTrue(administrator != null);
			}
			result = new ModelAndView("administrator/show");
			result.addObject("administrator", administrator);
			result.addObject("checkAdministrator", checkAdministrator);
			result.addObject("requestURI", "administrator/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// EDIT ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		try {
			Administrator administrator;
			final int idUserAccount = LoginService.getPrincipal().getId();
			administrator = this.adminService.findOneByUserAccount(idUserAccount);
			Assert.notNull(administrator);
			result = new ModelAndView("administrator/edit");
			result.addObject("administrator", administrator);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SAVE-EDIT ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		System.out.println("Administrator a editar" + administrator);

		administrator = this.adminService.reconstructEdit(administrator, binding);

		System.out.println("c" + binding.getAllErrors());

		if (binding.hasErrors()) {
			System.out.println("Carmen: Hay fallos " + binding);
			result = new ModelAndView("administrator/edit");
		} else
			try {
				administrator = this.adminService.saveEdit(administrator);
				result = new ModelAndView("redirect:show.do");
				result.addObject("administrator", administrator);
			} catch (final Throwable oops) {
				System.out.println(oops);
				if (oops.getMessage().equals("email.wrong"))
					result = this.editModelAndView(administrator, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.editModelAndView(administrator, "error.email");
				else
					result = new ModelAndView("redirect:/welcome/index.do");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView editModelAndView(final Administrator administrator, final String string) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("message", string);
		result.addObject("administrator", administrator);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}


}

	