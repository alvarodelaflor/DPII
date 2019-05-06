
package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import domain.Auditor;
import forms.RegistrationForm;

@Controller
@RequestMapping("/auditor")
public class AuditorController extends AbstractController {

	@Autowired
	private AuditorService	auditorService;


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
}
