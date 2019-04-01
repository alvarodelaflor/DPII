/*
 * WelcomeController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import domain.Actor;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "") String name) {
		ModelAndView result;
		final SimpleDateFormat formatter;
		String moment;
		String surname = "";

		// CARMEN: NOMBRE DE USUARIO QUE ESTE REGISTRADO
		try {
			final UserAccount userlogger = LoginService.getPrincipal();
			final Actor actor = this.actorService.getActorByUser(userlogger.getUsername());
			name = actor.getName();
			surname = actor.getSurname();
		} catch (final Exception e) {
			name = "";
		}
		// CARMEN: NOMBRE DE USUARIO QUE ESTE REGISTRADO

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("surname", surname);
		result.addObject("moment", moment);

		return result;
	}
}
