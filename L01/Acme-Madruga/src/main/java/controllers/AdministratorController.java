
package controllers;

/*
 * CONTROL DE CAMBIOS AdministratorController.java
 * FRAN 19/02/2019 11:36 CREACIÓN DE LA CLASE
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// createEditModelAndView -------------------------------------------------

	private ModelAndView createEditModelAndView(final Administrator admin) {
		return this.createEditModelAndView(admin, null);
	}

	private ModelAndView createEditModelAndView(final Administrator admin, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("administrator/create");
		result.addObject("administrator", admin);
		result.addObject("message", messageCode);

		return result;
	}

	// 12.1: Admin Registration by Admin only 		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView res;
		res = new ModelAndView("administrator/create");
		try {
			final AdministratorForm administratorForm = new AdministratorForm();
			res.addObject("administratorForm", administratorForm);
		} catch (final Exception e) {
			res = new ModelAndView("redirect:index.do");
		}

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final AdministratorForm adminForm, final BindingResult binding) {

		ModelAndView res;

		if (adminForm.getPassword() == adminForm.getConfirmPass()) {
			final ObjectError error = new ObjectError("error", "cpass.error");
			binding.addError(error);
		}

		final Administrator admin = this.administratorService.reconstruct(adminForm, binding);

		if (binding.hasErrors()) {
			System.out.println("El error pasa por AdministratorController");
			System.out.println(binding);
			res = new ModelAndView("administrator/create");
		} else
			try {
				System.out.println("El error pasa por try de AdministratorController: save");
				System.out.println(binding);

				this.administratorService.save(admin);
				res = new ModelAndView("redirect:index.do");
			} catch (final Throwable oops) {
				System.out.println("El error pasa por catch de AdministratorController: save =>" + oops);
				System.out.println(binding);

				if (oops.getMessage().equals("email.error"))
					res = this.createEditModelAndView(admin, "email.error");
				else if (oops.getMessage().equals("username.error"))
					res = this.createEditModelAndView(admin, "username.error");
				else if (oops.getMessage().equals("cpass.error"))
					res = this.createEditModelAndView(admin, "cpass.error");
				else
					res = this.createEditModelAndView(admin, "commit.error");
			}

		return res;
	}
}
