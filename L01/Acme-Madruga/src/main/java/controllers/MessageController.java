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
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.MessageBoxService;
import services.MessageService;
import services.WelcomeService;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService		messageService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private MessageBoxService	messageBoxService;
	@Autowired
	private WelcomeService		welcomeService;


	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listMessages(@RequestParam(value = "messageBoxId", defaultValue = "-1") final int messageBoxId) {
		final ModelAndView result;

		final MessageBox m = this.messageBoxService.findOne(messageBoxId);

		final Collection<Message> msgs = m.getMessages();

		final Collection<MessageBox> messageBox = this.messageBoxService.getSonBox(m.getId());

		result = new ModelAndView("message/list");
		result.addObject("msgs", msgs);
		result.addObject("messageBoxes", messageBox);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);
		result.addObject("messageBoxId", messageBoxId);
		result.addObject("requestURI", "message/list.do");

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam("messageId") final int messageId, @RequestParam("messageBoxId") final int messageBoxId) {
		ModelAndView result;

		final Message msg = this.messageService.findOne(messageId);
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		result = new ModelAndView("message/show");
		result.addObject("msg", msg);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//result.addObject("logo", logo);
		result.addObject("language", language);
		result.addObject("messageBoxId", messageBoxId);
		result.addObject("requestURI", "message/show.do");

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

	@RequestMapping(value = "/createNotification", method = RequestMethod.GET)
	public ModelAndView createNotification() {
		ModelAndView result;
		final Message msg;

		System.out.println("Entro en el create");

		msg = this.messageService.create();
		result = this.createEditModelAndViewNotification(msg);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "messageId", defaultValue = "-1") final int messageId) {
		ModelAndView result;
		if (messageId == -1) {
			result = new ModelAndView("welcome/index");
			//			final String system = this.welcomeService.getSystem();
			//			result.addObject("system", system);
			//			final String logo = this.welcomeService.getLogo();
			//result.addObject("logo", logo);
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
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView send(@ModelAttribute("msg") Message msg, final BindingResult binding, @RequestParam(value = "emailReceiver", defaultValue = "FfalsoF@gmail.com") final String emailReceiver) {

		msg = this.messageService.reconstruct(msg, binding);

		ModelAndView result;
		if (msg.getEmailReceiver() == null)
			msg.setEmailReceiver(Arrays.asList("FfalsoF@gmail.com"));
		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.getActorByUserId(login.getId());
		final List<String> emails = new ArrayList<>(msg.getEmailReceiver());
		if (emails.get(0) == "FfalsoF@gmail.com") {
			final ObjectError error = new ObjectError("emailReceiver", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("emailReceiver", "error.emailReceiver");
		}
		if (binding.hasErrors()) {
			System.out.println("Entro en el binding messageController");
			System.out.println(binding.getAllErrors().get(0));

			final Collection<MessageBox> messageBoxes = sender.getMessageBoxes();
			System.out.println(messageBoxes);
			result = new ModelAndView("message/edit");
			result.addObject("messageId", msg.getId());
			result.addObject("messageId", msg.getId());
			result.addObject("messageBoxes", messageBoxes);
			final Collection<String> emails2 = this.actorService.getEmailofActors();
			System.out.println(emails2);
			final List<String> listEmail = new ArrayList<>();
			listEmail.addAll(emails2);
			for (int i = 0; i < listEmail.size(); i++) {
				final Actor a = this.actorService.getActorByEmail(listEmail.get(i));
				final MessageBox inbox = this.messageBoxService.getInBoxActor(a.getId());
				System.out.println("inbox y actor");
				System.out.println(inbox);
				System.out.println(a);
				if (inbox == null)
					emails2.remove(listEmail.get(i));
				result.addObject("emails", emails2);
			}
		} else
			try {
				System.out.println(msg.getEmailReceiver());
				System.out.println("antes de exchangeMessage");
				final List<String> lisEmailReceiver = new ArrayList<>();
				lisEmailReceiver.addAll(msg.getEmailReceiver());
				for (int i = 0; i < msg.getEmailReceiver().size(); i++) {
					final Actor receiverIndex = this.actorService.getActorByEmail(lisEmailReceiver.get(i));
					System.out.println("actor que lo recibe");
					System.out.println(receiverIndex);
					msg = this.messageService.exchangeMessage(msg, receiverIndex.getId());
					System.out.println("se queda en servicio");
					System.out.println(this.messageBoxService.getInBoxActor(receiverIndex.getId()).getMessages());
				}

				System.out.println("despues de exchangeMessage");
				System.out.println(sender);
				System.out.println(msg);
				System.out.println(msg.getMessageBoxes());
				System.out.println(this.messageBoxService.getOutBoxActor(sender.getId()).getMessages());

				System.out.println("LA SUPER PRUEBA DEL COMBO");
				System.out.println(msg.getEmailReceiver());

				System.out.println("Entro en el save");

				System.out.println("intenta el list de exchange message");
				this.messageService.save(msg);

				result = new ModelAndView("messageBox/list");
				//		final String system = this.welcomeService.getSystem();
				//		result.addObject("system", system);
				//		final String logo = this.welcomeService.getLogo();
				//		result.addObject("logo", logo);
				result.addObject("messageBoxes", sender.getMessageBoxes());
				result.addObject("requestURI", "messageBox/list.do");

			} catch (final Throwable oops) {
				System.out.println("Es el oops");
				System.out.println(oops);
				result = this.createEditModelAndView(msg, "message.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/editNotification", method = RequestMethod.GET)
	public ModelAndView editNotification(@RequestParam(value = "messageId", defaultValue = "-1") final int messageId) {
		ModelAndView result;
		if (messageId == -1) {
			final UserAccount login = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserId(login.getId());
			result = new ModelAndView("messageBox/list");
			result.addObject("messageBoxes", a.getMessageBoxes());
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
			result = this.createEditModelAndViewNotification(msg);
		}
		return result;
	}

	@RequestMapping(value = "/editNotification", method = RequestMethod.POST, params = "save")
	public ModelAndView sendNotification(@ModelAttribute("msg") Message msg, final BindingResult binding, @RequestParam final String emailReceiver) {
		msg = this.messageService.reconstruct(msg, binding);

		ModelAndView result;
		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.getActorByUserId(login.getId());
		System.out.println("antes de exchangeMessage");
		System.out.println("despues de exchangeMessage");
		System.out.println(sender);
		System.out.println(msg);
		System.out.println(msg.getMessageBoxes());
		System.out.println(this.messageBoxService.getOutBoxActor(sender.getId()).getMessages());

		System.out.println("Entro en el save");
		//		if (msg.getBody() == null || msg.getBody().isEmpty()) {
		//			final ObjectError error = new ObjectError("message.body", "An account already exists for this email.");
		//			binding.addError(error);
		//			binding.rejectValue("body", "error.message.body.blank");
		//		}
		if (binding.hasErrors()) {
			System.out.println("Entro en el binding messageController");
			System.out.println(binding.getAllErrors().get(0));

			final Collection<MessageBox> messageBoxes = sender.getMessageBoxes();
			System.out.println(messageBoxes);
			result = new ModelAndView("message/editNotification");
			result.addObject("messageBoxes", messageBoxes);
			result.addObject("messageId", msg.getId());
		} else
			try {
				System.out.println("intenta el list notification");
				msg = this.messageService.sendNotification(msg);
				this.messageService.save(msg);
				final Collection<MessageBox> messageBoxes = sender.getMessageBoxes();
				System.out.println(messageBoxes);
				result = new ModelAndView("messageBox/list");
				result.addObject("messageBoxes", messageBoxes);
			} catch (final Throwable oops) {
				System.out.println("Es el oops");
				System.out.println(oops);
				result = this.createEditModelAndView(msg, "message.commit.error");
			}

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
		final Collection<String> emails = this.actorService.getEmailofActors();
		System.out.println(emails);
		final List<String> listEmail = new ArrayList<>();
		listEmail.addAll(emails);
		for (int i = 0; i < listEmail.size(); i++) {
			final Actor a = this.actorService.getActorByEmail(listEmail.get(i));
			final MessageBox inbox = this.messageBoxService.getInBoxActor(a.getId());
			System.out.println("inbox y actor");
			System.out.println(inbox);
			System.out.println(a);
			if (inbox == null)
				emails.remove(listEmail.get(i));
		}
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
	protected ModelAndView createEditModelAndViewNotification(final Message msg) {
		ModelAndView result;

		result = this.createEditModelAndViewNotification(msg, null);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);

		return result;
	}

	protected ModelAndView createEditModelAndViewNotification(final Message msg, final String msgCode) {
		ModelAndView result;
		final Collection<String> emails = this.actorService.getEmailofActors();

		result = new ModelAndView("message/editNotification");
		result.addObject("msg", msg);
		result.addObject("emails", emails);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);
		result.addObject("msgCode", msgCode);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("id") final int msgId, @RequestParam("messageBoxId") final int messageBoxId) {
		ModelAndView result;

		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.getActorByUserId(login.getId());

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
		final Message msg = this.messageService.findOne(msgId);
		final Collection<MessageBox> boxesSender = sender.getMessageBoxes();
		final List<MessageBox> boxesListSender = new ArrayList<>();
		boxesListSender.addAll(boxesSender);

		final Collection<MessageBox> boxes = msg.getMessageBoxes();
		final List<MessageBox> boxesList = new ArrayList<>();
		boxesList.addAll(boxes);

		final Actor creator = this.actorService.getActorMessageBox(boxesList.get(0).getId());

		System.out.println(sender);
		System.out.println(creator);

		if (!this.checkUserOwner(sender, msg)) {
			result = new ModelAndView("welcome/index");
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
			return result;
		}

		System.out.println("Message encontrado: " + msg);
		Assert.notNull(msg, "msg.null");

		try {
			System.out.println("entra en el try");
			final Message m = this.messageService.delete(msg, messageBoxId);
			final Collection<MessageBox> messageBoxes = sender.getMessageBoxes();
			System.out.println(messageBoxes);
			result = new ModelAndView("messageBox/list");
			result.addObject("messageBoxes", messageBoxes);
		} catch (final Exception e) {
			System.out.println(e);
			System.out.println("entro en el catch");
			result = new ModelAndView("message/show");
			result.addObject("msg", msg);
		}

		result.addObject("language", language);
		return result;
	}

	@RequestMapping(value = "/editMessageBox", method = RequestMethod.GET)
	public ModelAndView editMessageBox(@RequestParam final int msgId) {
		ModelAndView result;
		final Message message;

		message = this.messageService.findOne(msgId);
		System.out.println("Message de editMessageBox");
		System.out.println(message);
		Assert.notNull(message);
		result = this.createEditModelAndViewMessageBox(message);
		return result;
	}

	@RequestMapping(value = "/editMessageBox", method = RequestMethod.POST, params = "save")
	public ModelAndView saveMessageBox(@ModelAttribute("msg") Message msg, final BindingResult binding) {
		msg = this.messageService.reconstruct(msg, binding);

		ModelAndView result;
		System.out.println(msg.getMessageBoxes());
		System.out.println("Entro en el save");
		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.getActorByUserId(login.getId());

		final Message oldMessage = this.messageService.findOne(msg.getId());
		System.out.println(oldMessage.getMessageBoxes());

		msg.getMessageBoxes().addAll(oldMessage.getMessageBoxes());

		System.out.println(msg.getMessageBoxes());

		for (final MessageBox messageBox : oldMessage.getMessageBoxes())
			if (sender.getMessageBoxes().contains(messageBox))
				msg.getMessageBoxes().remove(messageBox);

		System.out.println(sender.getName());
		System.out.println(sender.getEmail());

		if (binding.hasErrors()) {
			System.out.println("Entro en el binding messageController");
			System.out.println(binding.getAllErrors().get(0));

			final Collection<MessageBox> messageBoxes = sender.getMessageBoxes();
			System.out.println(messageBoxes);
			result = new ModelAndView("messageBox/list");
			result.addObject("messageBoxes", messageBoxes);
		} else
			try {
				System.out.println("intenta el list notification");

				this.messageService.save(msg);

				final Collection<MessageBox> messageBoxes = sender.getMessageBoxes();
				System.out.println(messageBoxes);
				result = new ModelAndView("messageBox/list");
				result.addObject("messageBoxes", messageBoxes);
			} catch (final Throwable oops) {
				System.out.println("Es el oops");
				System.out.println(oops);
				result = this.createEditModelAndView(msg, "message.commit.error");
			}

		return result;
	}
	protected ModelAndView createEditModelAndViewMessageBox(final Message msg) {
		ModelAndView result;

		result = this.createEditModelAndViewMessageBox(msg, null);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);

		return result;
	}

	private Boolean checkUserOwner(final Actor actor, final Message message) {
		Boolean res = false;
		for (final MessageBox mailbox : actor.getMessageBoxes())
			if (mailbox.getMessages().contains(message)) {
				res = true;
				break;
			}

		return res;
	}

	protected ModelAndView createEditModelAndViewMessageBox(final Message msg, final String msgCode) {
		ModelAndView result;

		final UserAccount logged = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(logged.getId());
		final Collection<MessageBox> messageBoxes = a.getMessageBoxes();
		final Collection<String> nameMessageBox = new ArrayList<>();
		for (final MessageBox mailbox : messageBoxes)
			nameMessageBox.add(mailbox.getName());

		System.out.println("editModelAndView");
		System.out.println(messageBoxes);
		System.out.println(nameMessageBox);

		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.getActorByUserId(login.getId());

		final Collection<MessageBox> boxesSender = sender.getMessageBoxes();
		final List<MessageBox> boxesListSender = new ArrayList<>();
		boxesListSender.addAll(boxesSender);

		final Collection<MessageBox> boxes = msg.getMessageBoxes();
		final List<MessageBox> boxesList = new ArrayList<>();
		boxesList.addAll(boxes);

		final Actor creator = this.actorService.getActorMessageBox(boxesList.get(0).getId());

		System.out.println(sender);
		System.out.println(creator);

		if (!this.checkUserOwner(sender, msg)) {
			result = new ModelAndView("welcome/index");
			//			final String system = this.welcomeService.getSystem();
			//			result.addObject("system", system);
			//			final String logo = this.welcomeService.getLogo();
			//			result.addObject("logo", logo);
			result.addObject("nameMessageBox", nameMessageBox);
			result.addObject("msgCode", msgCode);
		} else {
			result = new ModelAndView("message/editMessageBox");
			result.addObject("msg", msg);
			//			final String system = this.welcomeService.getSystem();
			//			result.addObject("system", system);
			//			final String logo = this.welcomeService.getLogo();
			//			result.addObject("logo", logo);
			result.addObject("nameMessageBox", nameMessageBox);
			result.addObject("msgCode", msgCode);
		}
		return result;
	}

}
