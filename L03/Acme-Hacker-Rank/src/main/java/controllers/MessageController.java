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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

			result = new ModelAndView("message/list");
			result.addObject("msgs", a.getMessages());
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
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Message msg;

		System.out.println("Entro en el create");

		msg = this.messageService.create();
		result = this.createEditModelAndView(msg);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Message msg) {
		ModelAndView result;

		result = this.createEditModelAndView(msg, null);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message msg, final String msgCode) {
		ModelAndView result;
		final Collection<String> emails = this.actorService.getEmailOfActors();

		//		final UserAccount user = LoginService.getPrincipal();
		//		final Actor a = this.actorService.getActorByUserId(user.getId());
		//		
		//		emails.remove(a.getEmail());

		result = new ModelAndView("message/edit");
		result.addObject("msg", msg);
		result.addObject("emails", emails);
		result.addObject("msgCode", msgCode);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "messageId", defaultValue = "-1") final int messageId) {
		ModelAndView result;
		if (messageId == -1) {
			result = new ModelAndView("welcome/index");
			//			final String system = this.welcomeService.getSystem();
			//			result.addObject("system", system);
			//			final String logo = this.welcomeService.getLogo();
			//			result.addObject("logo", logo);
			SimpleDateFormat formatter;
			String moment;
			formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			moment = formatter.format(new Date());
			result.addObject("moment", moment);
		} else {
			final Message msg;

			msg = this.messageService.findOne(messageId);
			Assert.notNull(msg);
			result = this.createEditModelAndView(msg);
		}
		return result;
	}
	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView send(@ModelAttribute("msg") Message msg, final BindingResult binding, @RequestParam(value = "recipient", defaultValue = "null") final Collection<String> actors) {
		ModelAndView result;
		msg = this.messageService.reconstruct(msg, binding);
		if (binding.hasErrors()) {
			System.out.println("Entro en el binding messageController");
			final Collection<String> emails = this.actorService.getEmailOfActors();
			System.out.println(binding.getAllErrors().get(0));
			result = new ModelAndView("message/edit");
			result.addObject("messageId", msg.getId());
			result.addObject("actors", this.actorService.findAll());
			result.addObject("emails", emails);
		} else
			try {
				final UserAccount user = LoginService.getPrincipal();
				final Actor a = this.actorService.getActorByUserId(user.getId());
				System.out.println("antes de exchangeMessage");
				final List<String> lisRecipient = new ArrayList<>();
				lisRecipient.addAll(actors);
				for (int i = 0; i < lisRecipient.size(); i++)
					msg = this.messageService.exchangeMessage(msg, this.actorService.getActorByEmailOnly(lisRecipient.get(i)).getId());
				this.messageService.save(msg);

				result = new ModelAndView("message/list");

				result.addObject("msgs", a.getMessages());
				result.addObject("requestURI", "message/list.do");

			} catch (final Throwable oops) {
				System.out.println("Es el oops");
				System.out.println(oops);
				result = this.createEditModelAndView(msg, "message.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "messageId", defaultValue = "-1") final int messageId) {
		ModelAndView result;

		final Message msg = this.messageService.findOne(messageId);
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
		result = new ModelAndView("message/show");
		result.addObject("msg", msg);
		result.addObject("language", language);
		result.addObject("requestURI", "message/show.do");
		//		result.addObject("logo", this.welcomeService.getLogo());
		//		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int id, @RequestParam(value = "messageBoxId", defaultValue = "-1") final int messageBoxId) {
		ModelAndView result;

		final UserAccount login = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(login.getId());

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
		final Message msg = this.messageService.findOne(id);

		System.out.println("Message encontrado: " + msg);
		Assert.notNull(msg, "msg.null");

		try {
			System.out.println("entra en el try");
			this.messageService.remove(msg);
			result = new ModelAndView("message/list");
			result.addObject("msgs", a.getMessages());
			result.addObject("requestURI", "message/list.do");
		} catch (final Exception e) {
			System.out.println(e);
			System.out.println("entro en el catch");
			result = new ModelAndView("message/show");
			result.addObject("msg", msg);
		}

		result.addObject("language", language);
		//		result.addObject("logo", this.welcomeService.getLogo());
		//		result.addObject("system", this.welcomeService.getSystem());
		return result;
	}

}
