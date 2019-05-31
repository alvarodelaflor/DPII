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

import javax.validation.Valid;

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
import services.MailboxService;
import services.MessageService;
import services.TagService;
import domain.Actor;
import domain.Mailbox;
import domain.Message;
import domain.Tag;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private MessageService		messageService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private MailboxService		mailboxService;
	@Autowired
	private TagService		tagService;


	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listMessages(@RequestParam(value = "mailboxId", defaultValue = "-1") final int mailboxId) {
		ModelAndView result;
		
		try {
			final Mailbox m = this.mailboxService.findOne(mailboxId);

			final Collection<Message> msgs = m.getMessages();

			result = new ModelAndView("message/list");
			result.addObject("msgs", msgs);
			result.addObject("mailboxId", mailboxId);
			result.addObject("requestURI", "message/list.do");
		} catch (Exception e) {
			result = new ModelAndView("welcome/index");
		}

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "messageId", defaultValue = "aa") final int messageId, @RequestParam(value = "mailboxId", defaultValue = "-1") final int mailboxId) {
		ModelAndView result;
		
		if (messageId == -1 || mailboxId == -1)
			return new ModelAndView("redirect:/welcome/index.do");
		
		try {
			final Message msg = this.messageService.findOne(messageId);
			final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
			
			Collection<Tag> tags = tagService.getTagByMessage(messageId);

			result = new ModelAndView("message/show");
			result.addObject("msg", msg);
			result.addObject("tags",tags);
			result.addObject("language", language);
			result.addObject("mailboxId", mailboxId);
			result.addObject("requestURI", "message/show.do");
		} catch (Exception e) {
			result = new ModelAndView("welcome/index");
		}

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

	@RequestMapping(value = "/createBroadcast", method = RequestMethod.GET)
	public ModelAndView createBroadcast() {
		ModelAndView result;
		final Message msg;

		System.out.println("Entro en el create");

		msg = this.messageService.create();
		result = this.createEditModelAndViewBroadcast(msg);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "messageId", defaultValue = "-1") final int messageId) {
		ModelAndView result;
		if (messageId == -1) {
			result = new ModelAndView("welcome/index");
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
	public ModelAndView send(@ModelAttribute("msg")  Message msg, final BindingResult binding, @RequestParam(value = "emailReceiver", defaultValue = "FfalsoF@gmail.com") final String emailReceiver) {
		ModelAndView result;
		
		msg = this.messageService.reconstruct(msg, binding);
		
		if (msg.getEmailReceiver() == null)
			msg.setEmailReceiver(Arrays.asList("FfalsoF@gmail.com"));
		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.findByUserAccountId(login.getId());
		final List<String> emails = new ArrayList<>(msg.getEmailReceiver());
		if (emails.get(0) == "FfalsoF@gmail.com") {
			final ObjectError error = new ObjectError("emailReceiver", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("emailReceiver", "error.emailReceiver");
		}
		if (binding.hasErrors()) {
			System.out.println("Entro en el binding messageController");
			System.out.println(binding.getAllErrors().get(0));
			
			
			
			List<Tag> tags = new ArrayList<Tag>();
			tags.addAll(msg.getTags());
			
			if (tags.size() == 0)
				msg.setTags(null);

			final Collection<Mailbox> mailboxes = sender.getMailboxes();
			System.out.println(mailboxes);
			result = new ModelAndView("message/edit");
			result.addObject("messageId", msg.getId());
			
			
			if (tags.size() > 0)
				result.addObject("tag", tags.get(0).getTag());
			
			result.addObject("messageId", msg.getId());
			result.addObject("mailboxes", mailboxes);
			final Collection<String> emails2 = this.actorService.getEmailofActors();
			System.out.println(emails2);
			final List<String> listEmail = new ArrayList<>();
			listEmail.addAll(emails2);
			for (int i = 0; i < listEmail.size(); i++) {
				final Actor a =  this.actorService.getActorByEmail2(listEmail.get(i));
				final Mailbox inbox = this.mailboxService.getInBoxActor(a.getId());
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
					final Actor receiverIndex =  this.actorService.getActorByEmail2(lisEmailReceiver.get(i));
					msg = this.messageService.exchangeMessage(msg, receiverIndex.getId());
					System.out.println(receiverIndex);
					System.out.println(this.mailboxService.getInBoxActor(receiverIndex.getId()).getMessages());
				}			

				this.messageService.save(msg);

				List<Tag> tags = new ArrayList<Tag>();
				tags.addAll(msg.getTags());
				
				for (int i = 0; i < tags.size(); i++) {
					tags.get(i).setMessageId(msg.getId());
					tagService.save(tags.get(i));
				}
				
				result = new ModelAndView("mailbox/list");
				result.addObject("mailboxes", sender.getMailboxes());
				result.addObject("requestURI", "mailbox/list.do");


			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/welcome/index.do");
			}

		return result;
	}
	@RequestMapping(value = "/editBroadcast", method = RequestMethod.GET)
	public ModelAndView editBroadcast(@RequestParam(value = "messageId", defaultValue = "-1") final int messageId) {
		ModelAndView result;
		if (messageId == -1) {
			final UserAccount login = LoginService.getPrincipal();
			final Actor a = this.actorService.findByUserAccountId(login.getId());
			result = new ModelAndView("mailbox/list");
			result.addObject("mailboxes", a.getMailboxes());
			SimpleDateFormat formatter;
			String moment;
			formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			moment = formatter.format(new Date());
			result.addObject("moment", moment);
		} else {
			final Message msg;
			msg = this.messageService.findOne(messageId);
			Assert.notNull(msg);
			result = this.createEditModelAndViewBroadcast(msg);
		}
		return result;
	}

	@RequestMapping(value = "/editBroadcast", method = RequestMethod.POST, params = "save")
	public ModelAndView sendBroadcast(@ModelAttribute("msg")  Message msg, final BindingResult binding) {
		ModelAndView result;
		
		msg = this.messageService.reconstruct(msg, binding);
		
		msg.setTags(new ArrayList<Tag>());
		
		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.findByUserAccountId(login.getId());
	
		
		if (binding.hasErrors()) {
			System.out.println("Entro en el binding messageController");
			System.out.println(binding.getAllErrors().get(0));

			final Collection<Mailbox> mailboxes = sender.getMailboxes();
			System.out.println(mailboxes);
			result = new ModelAndView("message/editBroadcast");
			result.addObject("mailboxes", mailboxes);
			result.addObject("messageId", msg.getId());
		} else
			try {
				System.out.println("intenta el list broadcast");
				msg = this.messageService.sendBroadcast(msg);
				this.messageService.save(msg);
				List<Tag> tags = new ArrayList<Tag>();
				tags.addAll(msg.getTags());
				
				for (int i = 0; i < tags.size(); i++) {
					tags.get(i).setMessageId(msg.getId());
					tagService.save(tags.get(i));
				}
				final Collection<Mailbox> mailboxes = sender.getMailboxes();
				System.out.println(mailboxes);
				result = new ModelAndView("mailbox/list");
				result.addObject("mailboxes", mailboxes);
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

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message msg, final String msgCode) {
		ModelAndView result;
		final Collection<String> emails = this.actorService.getEmailofActors();
		System.out.println(emails);
		final List<String> listEmail = new ArrayList<>();
		listEmail.addAll(emails);
		for (int i = 0; i < listEmail.size(); i++) {
			final Actor a = this.actorService.getActorByEmail2(listEmail.get(i));
			final Mailbox inbox = this.mailboxService.getInBoxActor(a.getId());
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


		return result;
	}
	protected ModelAndView createEditModelAndViewBroadcast(final Message msg) {
		ModelAndView result;

		result = this.createEditModelAndViewBroadcast(msg, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewBroadcast(final Message msg, final String msgCode) {
		ModelAndView result;
		final Collection<String> emails = this.actorService.getEmailofActors();

		result = new ModelAndView("message/editBroadcast");
		result.addObject("msg", msg);
		result.addObject("emails", emails);

		result.addObject("msgCode", msgCode);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("id") final int msgId, @RequestParam("mailboxId") final int mailboxId) {
		ModelAndView result;

		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.findByUserAccountId(login.getId());

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
		final Message msg = this.messageService.findOne(msgId);
		final Collection<Mailbox> boxesSender = sender.getMailboxes();
		final List<Mailbox> boxesListSender = new ArrayList<>();
		boxesListSender.addAll(boxesSender);

		final Collection<Mailbox> boxes = msg.getMailboxes();
		final List<Mailbox> boxesList = new ArrayList<>();
		boxesList.addAll(boxes);

		final Actor creator = this.actorService.getActorMailbox(boxesList.get(0).getId());

		System.out.println(sender);
		System.out.println(creator);

		if (!this.checkUserOwner(sender, msg)) {
			result = new ModelAndView("welcome/index");
			result = new ModelAndView("welcome/index");
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
			this.messageService.delete(msg, mailboxId);
			Message m = messageService.findOne(msg.getId());
			if(m != null) {
			Tag deleteTag = tagService.getTagByMessageDeleted(m.getId());
			if(deleteTag != null) {
				tagService.save(deleteTag);
			}
			}
			final Collection<Mailbox> mailboxes = sender.getMailboxes();
			System.out.println(mailboxes);
			result = new ModelAndView("mailbox/list");
			result.addObject("mailboxes", mailboxes);
		} catch (final Exception e) {
			System.out.println(e);
			System.out.println("entro en el catch");
			result = new ModelAndView("message/show");
			result.addObject("msg", msg);
		}

		result.addObject("language", language);
		return result;
	}

	@RequestMapping(value = "/editMailbox", method = RequestMethod.GET)
	public ModelAndView editMailbox(@RequestParam final int msgId) {
		ModelAndView result;
		final Message message;

		message = this.messageService.findOne(msgId);
		System.out.println("Message de editMailbox");
		System.out.println(message);
		Assert.notNull(message);
		result = this.createEditModelAndViewMailbox(message);
		return result;
	}

	@RequestMapping(value = "/editMailbox", method = RequestMethod.POST, params = "save")
	public ModelAndView saveMailbox(@ModelAttribute("msg")  Message msg, final BindingResult binding) {
		ModelAndView result;
		
		msg = this.messageService.reconstruct(msg, binding);

		try {

			this.messageService.editMailbox(msg);

			final UserAccount login = LoginService.getPrincipal();
			final Actor sender = this.actorService.getActorByUserId(login.getId());

			if (binding.hasErrors()) {
				System.out.println("Entro en el binding messageController");
				System.out.println(binding.getAllErrors().get(0));

				final Collection<Mailbox> mailboxes = sender.getMailboxes();
				System.out.println(mailboxes);
				result = new ModelAndView("mailbox/list");
				result.addObject("mailboxes", mailboxes);
			} else
				try {
					System.out.println("intenta el list notification");

					this.messageService.save(msg);

					final Collection<Mailbox> mailboxes = sender.getMailboxes();
					System.out.println(mailboxes);
					result = new ModelAndView("mailbox/list");
					result.addObject("mailboxes", mailboxes);
				} catch (final Throwable oops) {
					System.out.println("Es el oops");
					System.out.println(oops);
					result = this.createEditModelAndView(msg, "message.commit.error");
				}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	protected ModelAndView createEditModelAndViewMailbox(final Message msg) {
		ModelAndView result;

		result = this.createEditModelAndViewMailbox(msg, null);

		return result;
	}

	private Boolean checkUserOwner(final Actor actor, final Message message) {
		Boolean res = false;
		for (final Mailbox mailbox : actor.getMailboxes())
			if (mailbox.getMessages().contains(message)) {
				res = true;
				break;
			}

		return res;
	}

	protected ModelAndView createEditModelAndViewMailbox(final Message msg, final String msgCode) {
		ModelAndView result;

		final UserAccount logged = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(logged.getId());
		final Collection<Mailbox> mailboxes = a.getMailboxes();
		final Collection<String> nameMailbox = new ArrayList<>();
		for (final Mailbox mailbox : mailboxes)
			nameMailbox.add(mailbox.getName());

		System.out.println("editModelAndView");
		System.out.println(mailboxes);
		System.out.println(nameMailbox);

		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.findByUserAccountId(login.getId());

		final Collection<Mailbox> boxesSender = sender.getMailboxes();
		final List<Mailbox> boxesListSender = new ArrayList<>();
		boxesListSender.addAll(boxesSender);

		final Collection<Mailbox> boxes = msg.getMailboxes();
		final List<Mailbox> boxesList = new ArrayList<>();
		boxesList.addAll(boxes);

		final Actor creator = this.actorService.getActorMailbox(boxesList.get(0).getId());

		System.out.println(sender);
		System.out.println(creator);

		if (!this.checkUserOwner(sender, msg)) {
			result = new ModelAndView("welcome/index");
			result.addObject("nameMailbox", nameMailbox);
			result.addObject("msgCode", msgCode);
		} else {
			result = new ModelAndView("message/editMailbox");
			result.addObject("msg", msg);
			result.addObject("nameMailbox", nameMailbox);
			result.addObject("msgCode", msgCode);
		}
		return result;
	}

}