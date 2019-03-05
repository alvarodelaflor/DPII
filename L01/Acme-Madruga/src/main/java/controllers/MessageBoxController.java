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
import org.springframework.context.i18n.LocaleContextHolder;
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
import services.MessageBoxService;
import services.WelcomeService;
import domain.Actor;
import domain.MessageBox;

@Controller
@RequestMapping("/messageBox")
public class MessageBoxController extends AbstractController {

	@Autowired
	private MessageBoxService	messageBoxService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private WelcomeService		welcomeService;


	// Constructors -----------------------------------------------------------

	public MessageBoxController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listMessageBox() {
		final ModelAndView result;

		final UserAccount login = LoginService.getPrincipal();

		final Actor logged = this.actorService.getActorByUserId(login.getId());
		System.out.println(logged.getName());
		Assert.notNull(logged);

		final Collection<MessageBox> messageBoxes = this.messageBoxService.getParentBoxActor(logged.getId());

		System.out.println(messageBoxes);

		result = new ModelAndView("messageBox/list");
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);
		result.addObject("messageBoxes", messageBoxes);
		result.addObject("requestURI", "messageBox/list.do");

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam("messageBoxId") final int messageBoxId) {
		ModelAndView result;

		final MessageBox messageBox = this.messageBoxService.findOne(messageBoxId);
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		result = new ModelAndView("messageBox/show");
		result.addObject("messageBox", messageBox);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);
		result.addObject("language", language);
		result.addObject("requestURI", "messageBox/show.do");

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final MessageBox messageBox;

		System.out.println("Entro en el create");

		messageBox = this.messageBoxService.create();
		result = this.createEditModelAndView(messageBox);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "messageBoxId", defaultValue = "-1") final int messageBoxId) {

		System.out.println("el primer id del padre nino");
		System.out.println(messageBoxId);

		ModelAndView result;
		final MessageBox messageBox;
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(user.getId());
		if (messageBoxId == -1) {
			result = new ModelAndView("messageBox/list");
			result.addObject("messageBoxes", a.getMessageBoxes());
			//			final String logo = this.welcomeService.getLogo();
			//			result.addObject("logo", logo);
		} else {

			messageBox = this.messageBoxService.findOne(messageBoxId);
			Assert.notNull(messageBox);
			result = this.createEditModelAndView(messageBox);
			//	final String logo = this.welcomeService.getLogo();
			//result.addObject("logo", logo);
		}
		return result;
	}

	private Boolean checkNameMailbox(final MessageBox mailboxToCreate) {
		Boolean res = false;
		final UserAccount login = LoginService.getPrincipal();
		final Actor logged = this.actorService.getActorByUserId(login.getId());
		final Collection<MessageBox> mailboxes = logged.getMessageBoxes();
		for (final MessageBox messageBoxCheck : mailboxes)
			if (messageBoxCheck.getName().equals(mailboxToCreate.getName())) {
				res = true;
				break;
			}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(MessageBox messageBox, final BindingResult binding, @RequestParam(value = "idParent", defaultValue = "-1") final int idParent) {
		System.out.println("El id del padre niño");
		System.out.println(idParent);

		ModelAndView result;
		final UserAccount login = LoginService.getPrincipal();
		final Actor logged = this.actorService.getActorByUserId(login.getId());

		System.out.println("Entro en el edit post");

		System.out.println(messageBox);
		System.out.println(messageBox.getName());
		System.out.println(messageBox.getIsDefault());
		System.out.println(messageBox.getMessages());
		System.out.println(messageBox.getParentBox());

		messageBox = this.messageBoxService.reconstruct(messageBox, binding, idParent);

		System.out.println(messageBox);

		System.out.println(binding);
		System.out.println(messageBox);
		System.out.println(messageBox.getParentBox());
		System.out.println("Entro en el save");
		if (this.checkNameMailbox(messageBox)) {
			final ObjectError error = new ObjectError("mailbox.name", "Ya existe esa box");
			binding.addError(error);
			binding.rejectValue("name", "error.mailbox.name");
		}
		if (binding.hasErrors()) {
			System.out.println("Entro en el binding");
			System.out.println(binding.getAllErrors().get(0));
			result = this.createEditModelAndView(messageBox);
		} else
			try {
				System.out.println("entro en update");
				final MessageBox updated = this.messageBoxService.update(messageBox);
				if (!logged.getMessageBoxes().contains(messageBox)) {
					logged.getMessageBoxes().add(updated);
					final Actor saved = this.actorService.save(logged);
				}
				System.out.println(logged.getMessageBoxes());
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("entro en oopss");
				System.out.println(oops);
				result = this.createEditModelAndView(messageBox, "messageBox.commit.error");
			}

		return result;
	}
	protected ModelAndView createEditModelAndView(final MessageBox messageBox) {
		ModelAndView result;

		result = this.createEditModelAndView(messageBox, null);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageBox messageBox, final String messageCode) {
		System.out.println("model and view");
		System.out.println(messageBox);
		System.out.println(messageBox.getParentBox());
		ModelAndView result;
		final UserAccount login = LoginService.getPrincipal();
		System.out.println(login);
		final Actor logged = this.actorService.getActorByUserId(login.getId());
		System.out.println(logged);

		final Boolean idMail = messageBox.getId() == 0;

		System.out.println(messageBox.getId() == 0);

		if (messageBox.getIsDefault() == true || messageBox == null || (!logged.getMessageBoxes().contains(messageBox) && !idMail)) {
			System.out.println("entro en el isDefault");
			result = new ModelAndView("messageBox/list");
			result.addObject("messageBoxes", logged.getMessageBoxes());
			result.addObject("requestURI", "messageBox/list.do");
			return result;
		}

		System.out.println("messageBox del modelAndView");
		System.out.println(messageBox);
		System.out.println(messageBox.getParentBox());

		result = new ModelAndView("messageBox/edit");
		result.addObject("messageBox", messageBox);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int messageBoxId) {
		ModelAndView result;

		final MessageBox messageBox = this.messageBoxService.findOne(messageBoxId);

		final UserAccount login = LoginService.getPrincipal();
		final Actor logged = this.actorService.getActorByUserId(login.getId());

		if (messageBox.getIsDefault() == true || messageBox == null || !logged.getMessageBoxes().contains(messageBox)) {
			result = new ModelAndView("messageBox/list");
			result.addObject("messageBoxes", logged.getMessageBoxes());
			result.addObject("requestURI", "messageBox/list.do");
			return result;
		}

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		System.out.println("Mailbox encontrado: " + messageBox);
		Assert.notNull(messageBox, "messageBox.null");

		try {
			System.out.println("entra en delete MessageBox");
			this.messageBoxService.delete(messageBox);
			result = new ModelAndView("redirect:list.do");
		} catch (final Exception e) {
			System.out.println(e);
			System.out.println("entra en catch");
			result = this.createEditModelAndView(messageBox, "mailbox.commit.error");
		}

		result.addObject("language", language);
		return result;
	}

	@RequestMapping(value = "/createFolder", method = RequestMethod.GET)
	public ModelAndView createFolder(@RequestParam(value = "messageBoxId", defaultValue = "-1") final int messageBoxId) {
		ModelAndView result;
		final MessageBox messageBox;

		System.out.println("Entro en el create folder");
		System.out.println(messageBoxId);

		messageBox = this.messageBoxService.create();

		if (messageBoxId != -1) {
			final MessageBox messageBoxParent = this.messageBoxService.findOne(messageBoxId);
			messageBox.setParentBox(messageBoxParent);
		}

		System.out.println(messageBox.getParentBox());

		result = this.createEditModelAndView(messageBox);
		result.addObject("idParent", messageBox.getParentBox().getId());
		return result;
	}
}
