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

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AdminService;
import services.ConfigService;
import services.CurriculaService;
import domain.Actor;
import domain.Admin;
import domain.Config;
import domain.CreditCard;
import forms.RegisterActor;

@Controller
@RequestMapping("/admin")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdminService		adminService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ConfigService		configService;

	@Autowired
	private CurriculaService	curriculaService;


	// Constructors -----------------------------------------------------------
	public AdministratorController() {
		super();
	}

	// DASHBOARD
	// ---------------------------------------------------------------
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {

		ModelAndView res;
		try {

			res = new ModelAndView("admin/dashboard");
			//CurriculaPerCleaner:
			final Float minCurriculaPerCleaner = this.curriculaService.minCurriculaPerCleaner();
			final Float maxCurriculaPerCleaner = this.curriculaService.maxCurriculaPerCleaner();
			final Float avgCurriculaPerCleaner = this.curriculaService.avgCurriculaPerCleaner();
			final Float stddevCurriculaPerCleaner = this.curriculaService.stddevCurriculaPerCleaner();
			res.addObject("minCurriculaPerCleaner", minCurriculaPerCleaner);
			res.addObject("maxCurriculaPerCleaner", maxCurriculaPerCleaner);
			res.addObject("avgCurriculaPerCleaner", avgCurriculaPerCleaner);
			res.addObject("stddevCurriculaPerCleaner", stddevCurriculaPerCleaner);
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/dashboard");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// NEW DEFAULT PHONE CODE
	// ---------------------------------------------------------------
	@RequestMapping(value = "/newdpc", method = RequestMethod.GET)
	public ModelAndView newDefaultPhoneCode(@RequestParam(value = "newdpc", defaultValue = "-1") final String newdpc) {

		ModelAndView res;
		try {

			this.configService.newDefaultPhoneCode(newdpc);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// NEW BANNER
	// ---------------------------------------------------------------
	@RequestMapping(value = "/newbnn", method = RequestMethod.GET)
	public ModelAndView newBanner(@RequestParam(value = "newbnn", defaultValue = "-1") final String newbnn) {

		ModelAndView res;
		try {

			this.configService.newBanner(newbnn);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "not.url") {
				res = this.config();
				res.addObject("message", "not.url");
			} else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// NEW SYSTEM NAME
	// ---------------------------------------------------------------
	@RequestMapping(value = "/newsysna", method = RequestMethod.GET)
	public ModelAndView newSystemName(@RequestParam(value = "newsysna", defaultValue = "-1") final String newsysna) {

		ModelAndView res;
		try {

			this.configService.newSystemName(newsysna);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// NEW SYSTEM NOMBRE
	// ---------------------------------------------------------------
	@RequestMapping(value = "/newsysno", method = RequestMethod.GET)
	public ModelAndView newSystemNombre(@RequestParam(value = "newsysno", defaultValue = "-1") final String newsysno) {

		ModelAndView res;
		try {

			this.configService.newSystemNombre(newsysno);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// NEW WELCOME MESSAGE EN
	// ---------------------------------------------------------------
	@RequestMapping(value = "/newwmen", method = RequestMethod.GET)
	public ModelAndView newWelcomeMessageEn(@RequestParam(value = "newwmen", defaultValue = "-1") final String newwmen) {

		ModelAndView res;
		try {

			this.configService.newWelcomeMessageEn(newwmen);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// NEW WELCOME MESSAGE ES
	// ---------------------------------------------------------------
	@RequestMapping(value = "/newwmes", method = RequestMethod.GET)
	public ModelAndView newWelcomeMessageEs(@RequestParam(value = "newwmes", defaultValue = "-1") final String newwmes) {

		ModelAndView res;
		try {

			this.configService.newWelcomeMessageEs(newwmes);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// NEW TRANSPORTER BAN RATIO
	// ---------------------------------------------------------------
	@RequestMapping(value = "/traratio", method = RequestMethod.GET)
	public ModelAndView newTransporterBanRatio(@RequestParam(value = "traratio", defaultValue = "-1") final int traratio) {

		ModelAndView res;
		try {

			this.configService.newTransporterBanRatio(traratio);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "not.in.range.error") {
				res = this.config();
				res.addObject("message", "not.in.range.error");
			} else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// NEW CREDIT CARD MAKE
	// ---------------------------------------------------------------
	@RequestMapping(value = "/newccm", method = RequestMethod.GET)
	public ModelAndView newCreditCardMake(@RequestParam(value = "newccm", defaultValue = "") final String newccm) {

		ModelAndView res;
		try {

			this.configService.newCreditCardMake(newccm);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// DELETE CREDIT CARD MAKE
	// ---------------------------------------------------------------
	@RequestMapping(value = "/delccm", method = RequestMethod.GET)
	public ModelAndView deleteCreditCardMake(@RequestParam(value = "delccm", defaultValue = "") final String delccm) {

		ModelAndView res;
		try {

			this.configService.deleteCreditCardMake(delccm);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// NEW SCORE WORD
	// ---------------------------------------------------------------
	@RequestMapping(value = "/newscw", method = RequestMethod.GET)
	public ModelAndView newScoreWord(@RequestParam(value = "newscw", defaultValue = "") final String newscw) {

		ModelAndView res;
		try {

			this.configService.newScoreWord(newscw);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// DELETE SCORE WORD
	// ---------------------------------------------------------------
	@RequestMapping(value = "/delscw", method = RequestMethod.GET)
	public ModelAndView deleteScoreWord(@RequestParam(value = "delscw", defaultValue = "") final String delscw) {

		ModelAndView res;
		try {

			this.configService.deleteScoreWord(delscw);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// NEW SPAM WORD
	// ---------------------------------------------------------------
	@RequestMapping(value = "/newsw", method = RequestMethod.GET)
	public ModelAndView newSpamWord(@RequestParam(value = "newsw", defaultValue = "") final String newsw) {

		ModelAndView res;
		try {

			this.configService.newSpamWord(newsw);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// DELETE SPAM WORD
	// ---------------------------------------------------------------
	@RequestMapping(value = "/delsw", method = RequestMethod.GET)
	public ModelAndView deleteSpamWord(@RequestParam(value = "delsw", defaultValue = "") final String delsw) {

		ModelAndView res;
		try {

			this.configService.deleteSpamWord(delsw);
			res = new ModelAndView("redirect:config.do");
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// CONFIGURATION
	// ---------------------------------------------------------------
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public ModelAndView config() {

		ModelAndView res;
		try {

			final Config config = this.adminService.getConfig();
			res = new ModelAndView("admin/config");
			res.addObject("config", config);
		} catch (final Throwable oops) {

			if (oops.getMessage() == "rellenar.con.msg.code.del.service")
				res = new ModelAndView("admin/config");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}
	// BAN/UNBAN ACTOR
	// ---------------------------------------------------------------
	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView banOrUnban(@RequestParam(value = "id", defaultValue = "-1") final int id) {

		ModelAndView res;
		try {

			this.adminService.banOrUnbanActorById(id);
			res = new ModelAndView("redirect:actorList.do");
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:actorList.do");
			if (oops.getMessage() == "not.found.error")
				res.addObject("message", "not.found.error");
			else
				res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// ACTOR LIST
	// ---------------------------------------------------------------
	@RequestMapping(value = "/actorList", method = RequestMethod.GET)
	public ModelAndView actorList() {

		ModelAndView res;
		try {

			final Collection<Actor> bannedActors = this.actorService.findAllBannedButAdmins();
			final Collection<Actor> NonBannedActors = this.actorService.findAllNonBannedButAdmins();

			res = new ModelAndView("admin/actorList");
			res.addObject("bannedActors", bannedActors);
			res.addObject("NonBannedActors", NonBannedActors);
			res.addObject("requestURI", "admin/actorList.do");
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/welcome/index.do");
		}

		return res;
	}

	// REGISTER AS ADMIN
	// ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegisterActor registerActor = new RegisterActor();
			result = new ModelAndView("admin/create");
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		;
		return result;
	}

	// SAVE REGISTER AS ADMIN
	// ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegisterActor registerActor, final BindingResult binding) {
		ModelAndView result = null;
		final Admin admin = this.adminService.reconstructRegisterAsAdmin(registerActor, binding);
		if (binding.hasErrors()) {
			System.err.println(binding);
			result = new ModelAndView("admin/create");
		} else
			try {
				this.adminService.saveRegisterAsAdmin(admin);
				result = new ModelAndView("welcome/index");
				final SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final String moment = formatter.format(new Date());
				result.addObject("moment", moment);
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// EDIT DATA PERSONAL
	// ---------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Admin admin;
		final int idUserAccount = LoginService.getPrincipal().getId();
		admin = this.adminService.getAdminByUserAccountId(idUserAccount);
		Assert.notNull(admin);
		final CreditCard creditCard = admin.getCreditCard();
		result = new ModelAndView("admin/edit");
		result.addObject("admin", admin);
		result.addObject("creditCard", creditCard);
		return result;
	}

	// SAVE EDIT DATA PERSONAL
	// ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Admin admin, final BindingResult binding) {
		ModelAndView result = null;

		admin = this.adminService.reconstructEditDataPeronal(admin, binding);

		if (binding.hasErrors()) {
			System.out.println("HAY ERRORES 2" + binding);
			result = new ModelAndView("admin/edit");

		} else
			try {
				this.adminService.saveRegisterAsAdmin(admin);
				result = new ModelAndView("redirect:show.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}
		return result;
	}

	// SHOW ADMIN
	// -------------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;
		try {
			final int userLoggin = LoginService.getPrincipal().getId();
			final Admin registerActor;
			registerActor = this.adminService.getAdminByUserAccountId(userLoggin);
			result = new ModelAndView("admin/show");
			result.addObject("registerActor", registerActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

}
