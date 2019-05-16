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

import org.joda.time.LocalDate;
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
import services.AdministratorService;
import services.ConfigurationService;
import services.MessageService;
import services.TagService;
import domain.Actor;
import domain.Configuration;
import domain.Message;
import domain.Rookie;
import domain.Tag;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService			messageService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private ConfigurationService	configService;
	@Autowired
	private TagService				tagService;


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

			final Collection<Message> sendMessages = new ArrayList<>();
			sendMessages.addAll(a.getMessages());
			sendMessages.retainAll(this.messageService.getSendedMessagesByActor(a.getEmail()));

			final Collection<Message> receivedMessages = new ArrayList<>();
			receivedMessages.addAll(a.getMessages());
			receivedMessages.removeAll(this.messageService.getSendedMessagesByActor(a.getEmail()));

			List<Message> sendedMessageFilter = new ArrayList<>();
			sendedMessageFilter.addAll(sendMessages);

			for (int i = 0; i < sendedMessageFilter.size(); i++) {
				final Tag tag = this.tagService.getTagByMessage(sendedMessageFilter.get(i).getId());
				final Collection<Tag> tagsFilter = new ArrayList<>();
				tagsFilter.add(tag);
				sendedMessageFilter.get(i).getTags().retainAll(tagsFilter);
			}

			List<Message> receiveMessageFilter = new ArrayList<>();
			receiveMessageFilter.addAll(receivedMessages);

			for (int i = 0; i < receiveMessageFilter.size(); i++) {
				final Tag tag = this.tagService.getTagByMessage(receiveMessageFilter.get(i).getId());
				final Collection<Tag> tagsFilter = new ArrayList<>();
				tagsFilter.add(tag);
				receiveMessageFilter.get(i).getTags().retainAll(tagsFilter);
			}

			for (int i = 0; i < sendedMessageFilter.size(); i++)
				if (sendedMessageFilter.get(i).getTags().size() == 0) {
					final Tag update = this.tagService.getTagByMessage(sendedMessageFilter.get(i).getId());
					final Collection<Tag> tags = new ArrayList<>();
					tags.add(update);
					sendedMessageFilter.get(i).setTags(tags);
				}

			for (int i = 0; i < receiveMessageFilter.size(); i++)
				if (receiveMessageFilter.get(i).getTags().size() == 0) {
					final Tag update = this.tagService.getTagByMessage(receiveMessageFilter.get(i).getId());
					final Collection<Tag> tags = new ArrayList<>();
					tags.add(update);
					receiveMessageFilter.get(i).setTags(tags);
				}

			result = new ModelAndView("message/list");

			if (sendedMessageFilter.size() > 0) {
				final Message[] arraySended = sendedMessageFilter.toArray(new Message[sendedMessageFilter.size()]);
				Arrays.sort(arraySended);
				sendedMessageFilter = Arrays.asList(arraySended);
			} else if (receiveMessageFilter.size() > 0) {
				final Message[] arrayReceive = receiveMessageFilter.toArray(new Message[receiveMessageFilter.size()]);
				Arrays.sort(arrayReceive);
				receiveMessageFilter = Arrays.asList(arrayReceive);
			}
			result.addObject("msgsSend", sendedMessageFilter);
			result.addObject("msgsReceive", receiveMessageFilter);
			result.addObject("requestURI", "message/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("welcome/index");
		}
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Message msg;

		System.out.println("Entro en el create");

		msg = this.messageService.create();
		result = this.createEditModelAndView(msg);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	protected ModelAndView createEditModelAndView(final Message msg) {
		ModelAndView result;

		result = this.createEditModelAndView(msg, null);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
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

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
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
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView send(@ModelAttribute("msg") Message msg, final BindingResult binding, @RequestParam(value = "recipient", defaultValue = "null") final Collection<String> actors) {
		ModelAndView result;
		msg = this.messageService.reconstruct(msg, binding);
		final List<Tag> tags = new ArrayList<>();
		tags.addAll(msg.getTags());

		if (actors.contains("null")) {
			final ObjectError error = new ObjectError("actorNull", "Must be choose an actor");
			binding.addError(error);
			binding.rejectValue("recipient", "error.actorNull");
		}

		if (binding.hasErrors()) {
			if (tags.size() == 0)
				msg.setTags(null);
			System.out.println("Entro en el binding messageController");
			final Collection<String> emails = this.actorService.getEmailOfActors();
			System.out.println(binding.getAllErrors().get(0));
			result = new ModelAndView("message/edit");
			if (tags.size() > 0)
				result.addObject("tag", tags.get(0).getTag());
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
				final List<Tag> tags2 = new ArrayList<>();
				tags2.addAll(msg.getTags());
				for (int i = 0; i < tags2.size(); i++)
					tags2.get(i).setMessageId(msg.getId());
				result = this.listMessages();

			} catch (final Throwable oops) {
				System.out.println("Es el oops");
				final Collection<String> emails = this.actorService.getEmailOfActors();
				System.out.println(oops);
				result = new ModelAndView("message/edit");
				if (tags.size() > 0)
					result.addObject("tag", tags.get(0).getTag());
				result.addObject("messageId", msg.getId());
				result.addObject("actors", this.actorService.findAll());
				result.addObject("emails", emails);
			}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "messageId", defaultValue = "-1") final int messageId) {
		ModelAndView result;
		try {
				
			UserAccount user = LoginService.getPrincipal();
			Actor logged = actorService.getActorByUserId(user.getId());
			
			
			
			final Tag tag = this.tagService.getTagByMessage(messageId);

			final Message msg = this.messageService.findOne(messageId);
			
			Assert.isTrue(logged.getMessages().contains(msg));
			
			
			final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
			result = new ModelAndView("message/show");
			result.addObject("msg", msg);
			result.addObject("tag", tag);
			result.addObject("language", language);
			result.addObject("requestURI", "message/show.do");
			//		result.addObject("logo", this.welcomeService.getLogo());
			//		result.addObject("system", this.welcomeService.getSystem());
			result.addObject("logo", this.getLogo());
			result.addObject("system", this.getSystem());
		} catch (Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int id, @RequestParam(value = "messageBoxId", defaultValue = "-1") final int messageBoxId) {
		ModelAndView result;

		final UserAccount login = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(login.getId());

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
		final Message msg = this.messageService.findOne(id);

		try {
			System.out.println("Message encontrado: " + msg);
			Assert.notNull(msg, "msg.null");
			Assert.isTrue(a.getMessages().contains(msg));
			System.out.println("entra en el try");
			this.messageService.remove(msg);
			//			final Collection<Message> sendMessages = new ArrayList<>();
			//			sendMessages.addAll(a.getMessages());
			//			sendMessages.retainAll(this.messageService.getSendedMessagesByActor(a.getEmail()));
			//
			//			final Collection<Message> receivedMessages = new ArrayList<>();
			//			receivedMessages.addAll(a.getMessages());
			//			receivedMessages.removeAll(this.messageService.getSendedMessagesByActor(a.getEmail()));

			result = this.listMessages();
			//			result.addObject("msgsSend", sendMessages);
			//			result.addObject("msgsReceive", receivedMessages);
			//			result.addObject("requestURI", "message/list.do");
		} catch (final Exception e) {
			System.out.println(e);
			System.out.println("entro en el catch");
			
			try {
				Assert.notNull(msg);
				result = show(msg.getId());
			} catch (Exception e2) {
				result = new ModelAndView("welcome/index");
			}
					
		}
		return result;
	}

	@RequestMapping(value = "/sendNoti", method = RequestMethod.GET)
	public ModelAndView editNoti(@RequestParam(value = "messageId", defaultValue = "-1") final int messageId) {
		ModelAndView result;
		final Message msg;
		msg = this.messageService.create();
		Assert.notNull(msg);
		result = this.createEditModelAndViewNoti(msg);
		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}
	@RequestMapping(value = "/sendNoti", method = RequestMethod.POST, params = "save")
	public ModelAndView sendNoti(@ModelAttribute("msg") Message msg, final BindingResult binding) {
		ModelAndView result;
		msg = this.messageService.reconstruct(msg, binding);

		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(this.adminService.findOneByUserAccount(user.getId()));
		final Actor a = this.actorService.getActorByUserId(user.getId());

		final List<String> emailsReceiver = (List<String>) this.actorService.getEmailOfActors();

		emailsReceiver.remove(a.getEmail());

		if (emailsReceiver.size() == 0) {
			final ObjectError error = new ObjectError("actorNull", "Must be minimum one actor");
			binding.addError(error);
			binding.rejectValue("recipient", "error.actorNull");
		}

		if (binding.hasErrors()) {
			System.out.println("Entro en el binding messageController");
			final Collection<String> emails = this.actorService.getEmailOfActors();
			System.out.println(binding.getAllErrors().get(0));
			result = new ModelAndView("message/editNoti");
			result.addObject("messageId", msg.getId());
			result.addObject("actors", this.actorService.findAll());
			result.addObject("emails", emails);
		} else
			try {

				System.out.println("antes de exchangeMessage");
				final List<String> lisRecipient = new ArrayList<>();
				lisRecipient.addAll(emailsReceiver);
				final Tag noti = this.tagService.create();
				noti.setTag("SYSTEM");
				final Collection<Tag> tagsNoti = new ArrayList<>();
				tagsNoti.add(noti);

				msg.setTags(tagsNoti);
				for (int i = 0; i < lisRecipient.size(); i++)
					msg = this.messageService.exchangeMessage(msg, this.actorService.getActorByEmailOnly(lisRecipient.get(i)).getId());
				this.messageService.save(msg);
				final List<Tag> tags2 = new ArrayList<>();
				tags2.addAll(msg.getTags());
				for (int i = 0; i < tags2.size(); i++)
					tags2.get(i).setMessageId(msg.getId());
				result = this.listMessages();

			} catch (final Throwable oops) {
				System.out.println("Es el oops");
				System.out.println(oops);
				result = this.createEditModelAndViewNoti(msg, "message.commit.error");
			}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	protected ModelAndView createEditModelAndViewNoti(final Message msg) {
		ModelAndView result;

		result = this.createEditModelAndViewNoti(msg, null);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	protected ModelAndView createEditModelAndViewNoti(final Message msg, final String msgCode) {
		ModelAndView result;
		final Collection<String> emails = this.actorService.getEmailOfActors();

		//		final UserAccount user = LoginService.getPrincipal();
		//		final Actor a = this.actorService.getActorByUserId(user.getId());
		//		
		//		emails.remove(a.getEmail());

		result = new ModelAndView("message/editNoti");
		result.addObject("msg", msg);
		result.addObject("emails", emails);
		result.addObject("msgCode", msgCode);
		//		final String system = this.welcomeService.getSystem();
		//		result.addObject("system", system);
		//		final String logo = this.welcomeService.getLogo();
		//		result.addObject("logo", logo);

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());
		return result;
	}

	@RequestMapping(value = "/sendRebranding", method = RequestMethod.GET)
	public ModelAndView sendRebranding() {
		ModelAndView result;

		try {
			Configuration config = configService.getConfiguration();
			
			if (config.getFirsTime() == 0) {
				final UserAccount log = LoginService.getPrincipal();
				final Actor logged = this.actorService.getActorByUserId(log.getId());

				
				final List<String> emails = (List<String>) actorService.getEmailOfActors();
				emails.remove(logged.getEmail());
				

				Message sended = this.messageService.create();
				sended.setSubject("Change in application state");
				final Collection<String> me = new ArrayList<>();
				sended.setRecipient(me);
				sended.setBody("Rebranding");
				sended.setMoment(LocalDate.now().toDate());

				final Tag noti = this.tagService.create();
				noti.setTag("SYSTEM");
				final Collection<Tag> tags = new ArrayList<>();
				tags.add(noti);
				sended.setTags(tags);
				for (int i = 0; i < emails.size(); i++) {
					final Actor a = this.actorService.getActorByEmailOnly(emails.get(i));
					sended = this.messageService.exchangeMessage(sended, a.getId());
				}
				this.messageService.save(sended);

				final List<Tag> newList = new ArrayList<>();
				newList.addAll(sended.getTags());
				for (int i = 0; i < newList.size(); i++) {
					newList.get(i).setMessageId(sended.getId());
					final Tag save = this.tagService.save(newList.get(i));
				}
				config.setFirsTime(1);
				configService.save(config);
				result = new ModelAndView("redirect:/administrator/list.do");
			} else {
				result = new ModelAndView("redirect:/administrator/list.do");
			}
			

		} catch (final Throwable oops) {
			final Configuration config = this.configService.getConfiguration();

			result = new ModelAndView("redirect:/administrator/list.do");
		}

		result.addObject("logo", this.getLogo());
		result.addObject("system", this.getSystem());

		return result;
	}
}
