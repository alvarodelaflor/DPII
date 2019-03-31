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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.MessageService;
import domain.Actor;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService	messageService;
	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listMessages() {
		ModelAndView result;
		try {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserId(user.getId());

			final Collection<Message> msgs = this.messageService.getSendedMessagesByActor(a.getId());

			result = new ModelAndView("message/list");
			result.addObject("msgs", msgs);
			result.addObject("requestURI", "message/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("welcome/index");
		}
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);
		return result;
	}

}
