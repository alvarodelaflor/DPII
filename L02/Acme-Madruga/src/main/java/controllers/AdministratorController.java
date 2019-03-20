/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
import services.ActorService;
import services.AdministratorService;
import services.AreaService;
import services.BrotherhoodService;
import services.ConfigurationService;
import services.FinderService;
import services.HistoryService;
import services.MemberService;
import services.ParadeService;
import services.RequestService;
import services.WelcomeService;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Configuration;
import domain.Member;
import domain.Parade;
import forms.RegistrationForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	AdministratorService	administratorService;

	@Autowired
	BrotherhoodService		brotherhoodService;

	@Autowired
	ParadeService			paradeService;

	@Autowired
	RequestService			requestService;

	@Autowired
	MemberService			memberService;

	@Autowired
	ActorService			actorService;

	@Autowired
	AreaService				areaService;

	@Autowired
	FinderService			finderService;

	@Autowired
	WelcomeService			welcomeService;

	@Autowired
	ConfigurationService	configurationService;

	@Autowired
	HistoryService			historyService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final RegistrationForm registrationForm = new RegistrationForm();
		result = new ModelAndView("administrator/create");
		result.addObject("registrationForm", registrationForm);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result;

		final Administrator administrator;

		administrator = this.administratorService.reconstructR(registrationForm, binding);

		if (binding.hasErrors())
			result = new ModelAndView("administrator/create");
		else
			try {
				this.administratorService.saveR(administrator);
				result = new ModelAndView("welcome/index");
				result.addObject("logo", this.welcomeService.getLogo());
				result.addObject("system", this.welcomeService.getSystem());
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createModelAndView(administrator, "email.wrong");
				else {
					result = this.createModelAndView(administrator, "error.email");
					result.addObject("logo", this.welcomeService.getLogo());
					result.addObject("system", this.welcomeService.getSystem());
				}
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		Administrator administrator;

		final int idUserAccount = LoginService.getPrincipal().getId();

		administrator = this.administratorService.getAdministratorByUserAccountId(idUserAccount);
		Assert.notNull(administrator != null);

		result = new ModelAndView("administrator/edit");

		result.addObject("administrator", administrator);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveE(Administrator admin, final BindingResult binding) {
		ModelAndView result;

		admin = this.administratorService.reconstruct(admin, binding);

		System.out.println(binding);
		if (binding.hasErrors())
			result = new ModelAndView("administrator/edit");
		else
			try {
				System.out.println("hola");
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:show.do");
				result.addObject("logo", this.welcomeService.getLogo());
				result.addObject("system", this.welcomeService.getSystem());
			} catch (final Throwable oops) {
				System.out.println("hola2");
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(admin, "email.wrong");
				else
					result = this.createEditModelAndView(admin, "error.email");
			}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}
	private ModelAndView createEditModelAndView(final Administrator administrator, final String string) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("message", string);
		result.addObject("administrator", administrator);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	private ModelAndView createModelAndView(final Administrator administrator, final String string) {
		ModelAndView result;

		result = new ModelAndView("administrator/create");
		result.addObject("message", string);
		result.addObject("administrator", administrator);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final int userLoggin = LoginService.getPrincipal().getId();
		final Administrator administrator;
		administrator = this.administratorService.getAdministratorByUserAccountId(userLoggin);
		Assert.isTrue(administrator != null);

		result = new ModelAndView("administrator/show");
		result.addObject("administrator", administrator);

		result.addObject("requestURI", "administrator/show.do");
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView result;

		final int userLoggin = LoginService.getPrincipal().getId();

		if (this.administratorService.findOne(userLoggin) != null)
			result = new ModelAndView("welcome/index");
		else {

			final Collection<String> largestBrotherhood = this.brotherhoodService.largestBrotherhood();
			final Collection<String> smallestBrotherhood = this.brotherhoodService.smallestBrotherhood();
			final Collection<Parade> paradeOrganised = this.paradeService.paradeOrganised();
			final Double getRatioRequestStatusTrue = this.requestService.getRatioRequestStatusTrue();
			final Double getRatioRequestStatusFalse = this.requestService.getRatioRequestStatusFalse();
			final Double getRatioRequestStatusNull = this.requestService.getRatioRequestStatusNull();
			final Collection<Member> lisMemberAccept = this.memberService.lisMemberAccept();
			final Double getRatioRequestParadeStatusTrue = this.requestService.getRatioRequestParadeStatusTrue();
			final Double getRatioRequestParadeStatusFalse = this.requestService.getRatioRequestParadeStatusFalse();
			final Double getRatioRequestParadeStatusNull = this.requestService.getRatioRequestParadeStatusNull();

			final Float maxNumberOfMemberPerBrotherhood = this.memberService.maxNumberOfMemberPerBrotherhood();
			final Float minNumberOfMemberPerBrotherhood = this.memberService.minNumberOfMemberPerBrotherhood();
			final Float avgNumberOfMemberPerBrotherhood = this.memberService.avgNumberOfMemberPerBrotherhood();
			final Float desviationOfNumberOfMemberPerBrotherhood = this.memberService.desviationOfNumberOfMemberPerBrotherhood();
			final String minParade = this.paradeService.minParade();
			final String maxParade = this.paradeService.maxParade();
			final Integer minParadeN = this.paradeService.minParadeN();
			final Integer maxParadeN = this.paradeService.maxParadeN();
			final Collection<Object[]> countBrotherhoodPerArea = this.brotherhoodService.countBrotherhoodPerArea();

			final Map<String, Long> map = new HashMap<>();
			for (final Object[] o : countBrotherhoodPerArea)
				map.put((String) o[0], (Long) o[1]);

			final Float avgBrotherhoodPerArea = this.brotherhoodService.avgBrotherhoodPerArea();
			final Float minBrotherhoodPerArea = this.brotherhoodService.minBrotherhoodPerArea();
			final Float maxBrotherhoodPerArea = this.brotherhoodService.maxBrotherhoodPerArea();
			final Float stddevBrotherhoodPerArea = this.brotherhoodService.stddevBrotherhoodPerArea();

			final Float avgRecordPerHistory = this.historyService.avgRecordPerHistory();
			final Float minRecordPerHistory = this.historyService.minRecordPerHistory();
			final Float maxRecordPerHistory = this.historyService.maxRecordPerHistory();
			final Float stddevRecordPerHistory = this.historyService.stddevRecordPerHistory();
			final Collection<Brotherhood> brotherhoodLargestHistory = this.brotherhoodService.findBrotherhoodWithLargestHistory();
			System.out.println("Listas en el controlador: ");
			System.out.println(brotherhoodLargestHistory);
			final Collection<Brotherhood> brotherhoodWithLargerHistoryThanAvg = this.brotherhoodService.findBrotherhoodsWithHistoryLargerThanAvg();
			System.out.println(brotherhoodWithLargerHistoryThanAvg);
			final Float minNumberOfResult = this.finderService.minNumberOfResult();
			final Float maxNumberOfResult = this.finderService.maxNumberOfResult();
			final Float avgNumberOfResult = this.finderService.avgNumberOfResult();
			final Float stddevNumberOfResult = this.finderService.stddevNumberOfResult();
			final Float ratioFinder = this.finderService.ratioResult();

			//CARMEN --> A+
			final Float noSpammersRation = this.actorService.noSpammersRation();
			final Float spammersRation = this.actorService.spammersRation();
			//CARMEN --> A+

			final Float ratioAreaNoCoordinate = this.areaService.ratioAreaNoCoordinate();
			final Float ratioFinalSUBMITTED = this.paradeService.ratioFinalSUBMITTED();
			final Float ratioFinalACCEPTED = this.paradeService.ratioFinalACCEPTED();
			final Float ratioFinalREJECTED = this.paradeService.ratioFinalREJECTED();
			final Float ratioNoFinalNULL = this.paradeService.ratioNoFinalNULL();

			final Float minParadeCapter = this.paradeService.minParadeCapter();
			final Float maxParadeCapter = this.paradeService.maxParadeCapter();
			final Float avgParadeCapter = this.paradeService.avgParadeCapter();
			final Float stddevParadeCapter = this.paradeService.stddevParadeCapter();
			final Collection<String> paradeChapter = this.paradeService.ParadeChapter();

			result = new ModelAndView("administrator/dashboard");

			result.addObject("minParadeCapter", minParadeCapter);
			result.addObject("maxParadeCapter", maxParadeCapter);
			result.addObject("avgParadeCapter", avgParadeCapter);
			result.addObject("stddevParadeCapter", stddevParadeCapter);
			result.addObject("paradeChapter", paradeChapter);

			result.addObject("ratioAreaNoCoordinate", ratioAreaNoCoordinate);
			result.addObject("ratioFinalSUBMITTED", ratioFinalSUBMITTED);
			result.addObject("ratioFinalACCEPTED", ratioFinalACCEPTED);
			result.addObject("ratioFinalREJECTED", ratioFinalREJECTED);
			result.addObject("ratioNoFinalNULL", ratioNoFinalNULL);

			result.addObject("noSpammersRation", noSpammersRation);
			result.addObject("spammersRation", spammersRation);

			result.addObject("minNumberOfResult", minNumberOfResult);
			result.addObject("maxNumberOfResult", maxNumberOfResult);
			result.addObject("avgNumberOfResult", avgNumberOfResult);
			result.addObject("stddevNumberOfResult", stddevNumberOfResult);
			result.addObject("ratioFinder", ratioFinder);

			result.addObject("map", map);

			result.addObject("avgBrotherhoodPerArea", avgBrotherhoodPerArea);
			result.addObject("minBrotherhoodPerArea", minBrotherhoodPerArea);
			result.addObject("maxBrotherhoodPerArea", maxBrotherhoodPerArea);
			result.addObject("stddevBrotherhoodPerArea", stddevBrotherhoodPerArea);

			result.addObject("avgRecordPerHistory", avgRecordPerHistory);
			result.addObject("minRecordPerHistory", minRecordPerHistory);
			result.addObject("maxRecordPerHistory", maxRecordPerHistory);
			result.addObject("stddevRecordPerHistory", stddevRecordPerHistory);

			result.addObject("largestBrotherhood", largestBrotherhood);
			result.addObject("getRatioRequestParadeStatusTrue", getRatioRequestParadeStatusTrue);
			result.addObject("getRatioRequestParadeStatusFalse", getRatioRequestParadeStatusFalse);
			result.addObject("getRatioRequestParadeStatusNull", getRatioRequestParadeStatusNull);

			result.addObject("maxNumberOfMemberPerBrotherhood", maxNumberOfMemberPerBrotherhood);
			result.addObject("minNumberOfMemberPerBrotherhood", minNumberOfMemberPerBrotherhood);
			result.addObject("avgNumberOfMemberPerBrotherhood", avgNumberOfMemberPerBrotherhood);
			result.addObject("desviationOfNumberOfMemberPerBrotherhood", desviationOfNumberOfMemberPerBrotherhood);
			result.addObject("brotherhoodLargestHistory", brotherhoodLargestHistory);
			result.addObject("brotherhoodWithLargerHistoryThanAvg", brotherhoodWithLargerHistoryThanAvg);

			result.addObject("minParade", minParade);
			result.addObject("maxParade", maxParade);
			result.addObject("minParadeN", minParadeN);
			result.addObject("maxParadeN", maxParadeN);

			result.addObject("smallestBrotherhood", smallestBrotherhood);
			result.addObject("paradeOrganised", paradeOrganised);
			result.addObject("lisMemberAccept", lisMemberAccept);
			result.addObject("getRatioRequestStatusTrue", getRatioRequestStatusTrue);
			result.addObject("getRatioRequestStatusFalse", getRatioRequestStatusFalse);
			result.addObject("getRatioRequestStatusNull", getRatioRequestStatusNull);

			result.addObject("requestURI", "administrator/dashboard.do");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/actorList", method = RequestMethod.GET)
	public ModelAndView actorList() {

		final ModelAndView res;

		final Collection<Member> members = this.memberService.findAll();

		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();

		res = new ModelAndView("administrator/actorList");

		res.addObject("members", members);
		res.addObject("brotherhoods", brotherhoods);
		res.addObject("requestURI", "administrator/actorList.do");
		res.addObject("logo", this.welcomeService.getLogo());
		res.addObject("system", this.welcomeService.getSystem());
		return res;
	}

	//	@RequestMapping(value = "/editWords", method = RequestMethod.GET)
	//	public ModelAndView editWords() {
	//
	//		final ModelAndView res;
	//
	//		final WordList wordList = new WordList();
	//
	//		res = new ModelAndView("administrator/editWords");
	//		res.addObject("wordList", wordList);
	//		return res;
	//	}
	//
	//	@RequestMapping(value = "/editWords", method = RequestMethod.POST, params = "save")
	//	public ModelAndView editWords(final String posWords, final String posWordsEs) {
	//
	//		System.out.println(posWords);
	//		System.out.println(posWordsEs);
	//
	//		final ModelAndView res;
	//
	//		final WordList wordList = new WordList();
	//
	//		final String[] spliten = posWords.split(",");
	//		final List<String> wl = Arrays.asList(spliten);
	//
	//		final String[] splites = posWordsEs.split(",");
	//		final List<String> wle = Arrays.asList(splites);
	//
	//		wordList.setPosWords(wl);
	//		wordList.setPosWordsEs(wle);
	//
	//		res = this.actorList(wordList);
	//
	//		return res;
	//	}
	//
	//	// This will be used if wordlist is edited
	//	@RequestMapping(value = "/actorList2", method = RequestMethod.GET)
	//	public ModelAndView actorList(final WordList wordList) {
	//
	//		final ModelAndView res;
	//
	//		final Collection<Member> members = this.memberService.findAll();
	//		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
	//
	//		res = new ModelAndView("administrator/actorList");
	//
	//		res.addObject("members", members);
	//		res.addObject("brotherhoods", brotherhoods);
	//		res.addObject("wordList", wordList);
	//		res.addObject("requestURI", "administrator/actorList.do");
	//
	//		return res;
	//	}

	@RequestMapping(value = "/banMember", method = RequestMethod.GET)
	public ModelAndView banMember(@RequestParam(value = "actorId", defaultValue = "-1") final int actorId) {

		ModelAndView res;

		try {
			final Actor actor = this.memberService.findOne(actorId);

			if (actor.getUserAccount().getBanned() == false) {

				this.actorService.banByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			} else {

				this.actorService.unbanByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			}
		} catch (final Throwable oops) {

			final Actor actor = this.memberService.findOne(actorId);
			if (oops.getMessage() == "ban.error")
				res = this.createEditModelAndView2(actor, "ban.error");
			else
				res = new ModelAndView("redirect:../#");
		}
		res.addObject("logo", this.welcomeService.getLogo());
		res.addObject("system", this.welcomeService.getSystem());
		return res;
	}
	@RequestMapping(value = "/banBrotherhood", method = RequestMethod.GET)
	public ModelAndView banBrotherhood(@RequestParam(value = "actorId", defaultValue = "-1") final int actorId) {

		ModelAndView res;

		try {
			final Actor actor = this.brotherhoodService.findOne(actorId);

			if (actor.getUserAccount().getBanned() == false) {

				this.actorService.banByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			} else {

				this.actorService.unbanByActorId(actor);
				res = new ModelAndView("redirect:actorList.do");
			}
		} catch (final Throwable oops) {

			final Actor actor = this.brotherhoodService.findOne(actorId);
			if (oops.getMessage() == "ban.error")
				res = this.createEditModelAndView2(actor, "ban.error");
			else
				res = new ModelAndView("redirect:../#");
		}
		res.addObject("logo", this.welcomeService.getLogo());
		res.addObject("system", this.welcomeService.getSystem());
		return res;
	}

	private ModelAndView createEditModelAndView2(final Actor actor, final String string) {
		ModelAndView result;

		final Collection<Member> members = this.memberService.findAll();
		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();

		result = new ModelAndView("administrator/actorList");
		result.addObject("message", string);
		result.addObject("actor", actor);
		result.addObject("members", members);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	//FERRETE
	//CONFIGURATION
	@RequestMapping(value = "/list")
	public ModelAndView list2() {
		ModelAndView result;
		result = new ModelAndView("administrator/list");

		HashSet<String> spamWords = new HashSet<>();
		HashSet<String> scoreWordsPos = new HashSet<>();
		HashSet<String> scoreWordsNeg = new HashSet<>();

		//CreditCards'Makes
		// ------------------------------------------------------------
		HashSet<String> creditCardMakes = new HashSet<>();
		if (this.welcomeService.getCreditCardsMakes().size() == 0)
			creditCardMakes = this.welcomeService.defaultCCsMakes();
		else
			creditCardMakes = this.welcomeService.getCreditCardsMakes();
		result.addObject("creditCardMakes", creditCardMakes);
		// ------------------------------------------------------------

		//Priorities
		// ------------------------------------------------------------
		HashSet<String> priorities = new HashSet<>();
		if (this.welcomeService.getPriorities().isEmpty())
			priorities = this.welcomeService.defaultPriorities();
		else
			priorities = this.welcomeService.getPriorities();
		result.addObject("priorities", priorities);
		// ------------------------------------------------------------
		//Logo
		final String logo = this.welcomeService.getLogo();

		//Configuration (FINDER)
		final Configuration configuration = this.configurationService.getConfiguration();

		//Spam words
		if (this.welcomeService.getSpamWords().isEmpty())
			spamWords = this.welcomeService.listSpamWords();
		else
			spamWords = this.welcomeService.getSpamWords();

		//Score Words
		if (this.administratorService.getScoreWordsPos().isEmpty())
			scoreWordsPos = this.administratorService.listScoreWordsPos();
		else
			scoreWordsPos = this.administratorService.getScoreWordsPos();

		if (this.administratorService.getScoreWordsNeg().isEmpty())
			scoreWordsNeg = this.administratorService.listScoreWordsNeg();
		else
			scoreWordsNeg = this.administratorService.getScoreWordsNeg();

		//Welcome page
		final String ingles = this.welcomeService.getS();
		final String spanish = this.welcomeService.getE();

		//System
		final String system = this.welcomeService.getSystem();

		//Phone
		final String phone = this.welcomeService.getPhone();

		//Country´s Phone
		final String phoneCountry = this.welcomeService.getCountry();

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		System.out.println("Carmen: Entro en el list");

		result.addObject("logo", logo);
		result.addObject("ingles", ingles);
		result.addObject("spanish", spanish);
		result.addObject("spamWords", spamWords);
		result.addObject("scoreWordsPos", scoreWordsPos);
		result.addObject("scoreWordsNeg", scoreWordsNeg);
		result.addObject("configuration", configuration);
		result.addObject("system", system);
		result.addObject("phone", phone);
		result.addObject("phoneCountry", phoneCountry);
		result.addObject("language", language);
		result.addObject("requestURI", "administrator/list.do");
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	// CreditCardMakes Methods:
	// ------------------------------------------------------------
	@RequestMapping(value = "/newCreditCardMake", method = RequestMethod.GET)
	public ModelAndView newCreditCardMake(@RequestParam("newCreditCardMake") final String newCreditCardMake) {

		final ModelAndView res = new ModelAndView("redirect:list.do");
		this.welcomeService.addCCMake(newCreditCardMake);
		res.addObject("logo", this.welcomeService.getLogo());
		res.addObject("system", this.welcomeService.getSystem());
		return res;
	}

	@RequestMapping(value = "/deleteCreditCardMake", method = RequestMethod.GET)
	public ModelAndView deleteCreditCardMake(@RequestParam("deleteCreditCardMake") final String deleteCreditCardMake) {
		ModelAndView result = new ModelAndView("administrator/list");

		try {
			this.welcomeService.removeCCMake(deleteCreditCardMake);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage() == "noCCMake.error") {
				result = this.list2();
				result.addObject("message", "noCCMake.error");
			}
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}
	// ------------------------------------------------------------

	// PRIORITIES
	@RequestMapping(value = "/newPriority", method = RequestMethod.GET)
	public ModelAndView addPriority(@RequestParam("newPriority") final String newPriority) {
		ModelAndView result;

		this.welcomeService.addPriority(newPriority);
		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/deletePriority", method = RequestMethod.GET)
	public ModelAndView deletePriority(@RequestParam("deletePriority") final String deletePriority) {
		ModelAndView result = new ModelAndView("administrator/list");

		try {
			System.out.println("Carmen: Voy a intentar guardar");
			this.welcomeService.deletePriority(deletePriority);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage() == "noPriority.error") {
				result = this.list2();
				result.addObject("message", "noPriority.error");
			}
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/newSpamWord", method = RequestMethod.GET)
	public ModelAndView newSpamWord(@RequestParam("newSpamWord") final String newSpamWord) {
		ModelAndView result;

		System.out.println("Carmen: Voy a intentar guardar");
		this.welcomeService.newSpamWords(newSpamWord);
		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/deleteSpamWord", method = RequestMethod.GET)
	public ModelAndView deleteSpamWord(@RequestParam("deleteSpamWord") final String spamWord) {
		ModelAndView result = new ModelAndView("administrator/list");

		try {
			System.out.println("Carmen: Voy a intentar guardar");
			this.welcomeService.deleteSpamWords(spamWord);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage() == "noSpamWord.error") {
				result = this.list2();
				result.addObject("message", "noSpamWord.error");
			}
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	//SCORE WORDS
	@RequestMapping(value = "/newScoreWordPos", method = RequestMethod.GET)
	public ModelAndView newScoreWordPos(@RequestParam("newScoreWord") final String newScoreWord) {
		ModelAndView result;

		this.administratorService.newScoreWordsPos(newScoreWord);
		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/deleteScoreWordPos", method = RequestMethod.GET)
	public ModelAndView deleteScoreWordPos(@RequestParam("deleteScoreWord") final String scoreWord) {

		ModelAndView result = new ModelAndView("administrator/list");

		try {
			this.administratorService.deleteScoreWordsPos(scoreWord);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage() == "noScoreWord.error") {
				result = this.list2();
				result.addObject("message", "noScoreWord.error");
			}
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	//SCORE WORDS NEG
	@RequestMapping(value = "/newScoreWordNeg", method = RequestMethod.GET)
	public ModelAndView newScoreWord(@RequestParam("newScoreWord") final String newScoreWord) {
		ModelAndView result;

		this.administratorService.newScoreWordsNeg(newScoreWord);
		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/deleteScoreWordNeg", method = RequestMethod.GET)
	public ModelAndView deleteScoreWord(@RequestParam("deleteScoreWord") final String scoreWord) {
		ModelAndView result = new ModelAndView("administrator/list");

		try {
			System.out.println("Carmen: Voy a intentar guardar");
			this.administratorService.deleteScoreWordsNeg(scoreWord);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			if (oops.getMessage() == "noScoreWord.error") {
				result = this.list2();
				result.addObject("message", "noScoreWord.error");
			}
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/newWelcome", method = RequestMethod.GET)
	public ModelAndView newWelcome(@RequestParam("newIngles") final String newIngles, @RequestParam("newSpanish") final String newSpanish) {
		ModelAndView result;

		System.out.println(newIngles);
		System.out.println(newSpanish);

		System.out.println("Carmen: Voy a intentar guardar");

		final String ingles = this.welcomeService.newE(newIngles);
		System.out.println(ingles);

		final String spanish = this.welcomeService.newS(newSpanish);
		System.out.println(spanish);

		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/newSystem", method = RequestMethod.GET)
	public ModelAndView newSystem(@RequestParam("newSystem") final String newSystem) {
		ModelAndView result;

		System.out.println(newSystem);

		System.out.println("Carmen: Voy a intentar guardar");

		final String system = this.welcomeService.newSystem(newSystem);
		System.out.println(system);

		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public ModelAndView header() {
		ModelAndView result;

		final String system = this.welcomeService.getSystem();

		result = new ModelAndView("master-page/header");

		result.addObject("requestURI", "master-page/header.do");
		result.addObject("system", system);
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/newLogo", method = RequestMethod.GET)
	public ModelAndView newLogo(@RequestParam("newLogo") final String newLogo) {
		ModelAndView result;
		try {
			final String logo = this.welcomeService.newLogo(newLogo);

			System.out.println("Carmen: Voy a intentar guardar");

			result = new ModelAndView("redirect:list.do");

		} catch (final Exception e) {
			result = this.createEditModelAndView(newLogo, "logo.bad");
		}
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final String newLogo, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("administrator/list");
		//Logo
		final String logo = this.welcomeService.getLogo();

		//Priorities
		final HashSet<String> priorities = this.welcomeService.defaultPriorities();

		//Spam words
		final HashSet<String> spamWords = this.welcomeService.listSpamWords();

		System.out.println("Carmen: Esta es la lista de spam words");
		System.out.println(spamWords);

		//Welcome page
		final String ingles = this.welcomeService.getS();
		final String spanish = this.welcomeService.getE();

		//System
		final String system = this.welcomeService.getSystem();

		//Phone
		final String phone = this.welcomeService.getPhone();

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		//CreditCards'Makes
		// ------------------------------------------------------------
		HashSet<String> creditCardMakes = new HashSet<>();
		if (this.welcomeService.getCreditCardsMakes().size() == 0)
			creditCardMakes = this.welcomeService.defaultCCsMakes();
		else
			creditCardMakes = this.welcomeService.getCreditCardsMakes();
		result.addObject("creditCardMakes", creditCardMakes);
		// ------------------------------------------------------------

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
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/newPhone", method = RequestMethod.GET)
	public ModelAndView newPhone(@RequestParam("newPhone") final String newPhone) {
		ModelAndView result;

		final String phone = this.welcomeService.newPhone(newPhone);

		System.out.println("Carmen: Voy a intentar guardar");

		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/newPhoneCountry", method = RequestMethod.GET)
	public ModelAndView newPhoneCountry(@RequestParam("newPhoneCountry") final String newPhoneCountry) {
		ModelAndView result;

		this.welcomeService.newCountry(newPhoneCountry);
		result = new ModelAndView("redirect:list.do");
		result.addObject("logo", this.welcomeService.getLogo());
		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}
}
