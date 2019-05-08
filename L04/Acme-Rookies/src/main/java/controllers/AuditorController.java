
package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import domain.Auditor;
import forms.RegistrationForm;
import security.LoginService;
import services.AuditorService;

@Controller
@RequestMapping("/auditor")
public class AuditorController extends AbstractController {

	@Autowired
	private AuditorService auditorService;


	// CREATE ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final RegistrationForm registrationForm = new RegistrationForm();
			result = new ModelAndView("auditor/create");
			result.addObject("registrationForm", registrationForm);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SAVE-CREATE ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final RegistrationForm registrationForm, final BindingResult binding) {
		ModelAndView result = null;

		final Auditor auditor;

		auditor = this.auditorService.reconstructCreate(registrationForm, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = new ModelAndView("auditor/create");
		} else
			try {
				System.out.println("carmen: voy a guardar");
				final Auditor a = this.auditorService.saveCreate(auditor);
				System.out.println(a);
				result = new ModelAndView("welcome/index");
				final SimpleDateFormat formatter;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final String moment = formatter.format(new Date());
				result.addObject("moment", moment);

			} catch (final Throwable oops) {
				if (oops.getMessage().equals("email.wrong"))
					result = this.createEditModelAndView(auditor, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.createEditModelAndView(auditor, "error.email");
				else
					result = this.createEditModelAndView(auditor, "error.html");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView createEditModelAndView(final Auditor actor, final String string) {
		ModelAndView result;

		result = new ModelAndView("auditor/create");
		result.addObject("message", string);
		result.addObject("actor", actor);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// EDIT ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		try {
			Auditor auditor;
			final int idUserAccount = LoginService.getPrincipal().getId();
			auditor = this.auditorService.getAuditorByUserAccountId(idUserAccount);
			Assert.notNull(auditor);
			result = new ModelAndView("auditor/edit");
			result.addObject("auditor", auditor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SAVE-EDIT ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(Auditor auditor, final BindingResult binding) {
		ModelAndView result;

		System.out.println("Auditor a editar" + auditor);

		auditor = this.auditorService.reconstructEdit(auditor, binding);

		System.out.println("c" + binding.getAllErrors());

		if (binding.hasErrors()) {
			System.out.println("Carmen: Hay fallos " + binding);
			result = new ModelAndView("auditor/edit");
		} else
			try {
				auditor = this.auditorService.saveEdit(auditor);
				result = new ModelAndView("redirect:show.do");
				result.addObject("auditor", auditor);
			} catch (final Throwable oops) {
				System.out.println(oops);
				if (oops.getMessage().equals("email.wrong"))
					result = this.editModelAndView(auditor, "email.wrong");
				else if (oops.getMessage().equals("error.email"))
					result = this.editModelAndView(auditor, "error.email");
				else
					result = new ModelAndView("redirect:/welcome/index.do");
			}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	private ModelAndView editModelAndView(final Auditor auditor, final String string) {
		ModelAndView result;

		result = new ModelAndView("auditor/edit");
		result.addObject("message", string);
		result.addObject("auditor", auditor);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	// SHOW ---------------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "id", defaultValue = "-1") final int id) {

		ModelAndView result;
		final Auditor auditor;
		Boolean checkAuditor = false;
		try {
			if (id == -1) {
				final int userLoggin = LoginService.getPrincipal().getId();
				auditor = this.auditorService.getAuditorByUserAccountId(userLoggin);
				Assert.isTrue(auditor != null);
				checkAuditor = true;
			} else {
				auditor = this.auditorService.findOne(id);
				Assert.isTrue(auditor != null);
			}
			result = new ModelAndView("auditor/show");
			result.addObject("auditor", auditor);
			result.addObject("checkAuditor", checkAuditor);
			result.addObject("requestURI", "auditor/show.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public @ResponseBody Auditor export(@RequestParam(value = "id", defaultValue = "-1") final int id) {
		Auditor result = new Auditor();
		result = this.auditorService.findOne(id);
		if (result == null || LoginService.getPrincipal().getId() != result.getUserAccount().getId())
			return null;
		System.out.println(result);
		return result;
	}

	//Nuevo
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int auditorId) {
		ModelAndView result;

		final Auditor auditor = this.auditorService.findOne(auditorId);
		System.out.println("Auditor encontrado: " + auditor);
		if (this.auditorService.findOne(auditorId) == null || LoginService.getPrincipal().getId() != auditor.getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(auditor, "auditor.null");

			try {
				this.auditorService.delete(auditor);
				result = new ModelAndView("redirect:/j_spring_security_logout");
			} catch (final Exception e) {
				result = new ModelAndView("redirect:/j_spring_security_logout");
			}
		}
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

}
