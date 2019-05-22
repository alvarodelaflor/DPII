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

import javax.validation.Valid;

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

import services.MailboxService;
import domain.Actor;
import domain.Mailbox;

@Controller
@RequestMapping("/mailbox")
public class MailboxController extends AbstractController {

	@Autowired
	private MailboxService		mailboxService;
	@Autowired
	private ActorService		actorService;


	// Constructors -----------------------------------------------------------

	public MailboxController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listMailbox() {
		final ModelAndView result;

		final UserAccount login = LoginService.getPrincipal();

		final Actor logged = this.actorService.findByUserAccountId(login.getId());
		System.out.println(logged.getName());
		Assert.notNull(logged);

		final Collection<Mailbox> mailboxes = logged.getMailboxes();

		System.out.println(mailboxes);

		result = new ModelAndView("mailbox/list");
		result.addObject("mailboxes", mailboxes);
		result.addObject("requestURI", "mailbox/list.do");

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam("mailboxId") final int mailboxId) {
		ModelAndView result;

		final Mailbox mailbox = this.mailboxService.findOne(mailboxId);
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		result = new ModelAndView("mailbox/show");
		result.addObject("mailbox", mailbox);
		result.addObject("language", language);
		result.addObject("requestURI", "mailbox/show.do");

		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Mailbox mailbox;

		System.out.println("Entro en el create");

		mailbox = this.mailboxService.create();
		result = this.createEditModelAndView(mailbox);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "mailboxId", defaultValue = "-1") final int mailboxId) {
		ModelAndView result;
		final Mailbox mailbox;
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(user.getId());
		if (mailboxId == -1) {
			result = new ModelAndView("mailbox/list");
			result.addObject("mailboxes", a.getMailboxes());
		} else {

			mailbox = this.mailboxService.findOne(mailboxId);
			Assert.notNull(mailbox);
			result = this.createEditModelAndView(mailbox);
		}
		return result;
	}

	private Boolean checkNameMailbox(final Mailbox mailboxToCreate) {
		Boolean res = false;
		final UserAccount login = LoginService.getPrincipal();
		final Actor logged = this.actorService.findByUserAccountId(login.getId());
		final Collection<Mailbox> mailboxes = logged.getMailboxes();
		for (final Mailbox mailboxCheck : mailboxes)
			if (mailboxCheck.getName().equals(mailboxToCreate.getName())) {
				res = true;
				break;
			}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save( Mailbox mailbox, final BindingResult binding) {
		ModelAndView result;
		
		mailbox = mailboxService.reconstruct(mailbox, binding);
		final UserAccount login = LoginService.getPrincipal();
		final Actor logged = this.actorService.findByUserAccountId(login.getId());

		System.out.println(mailbox);

		System.out.println(binding);
		System.out.println(mailbox);
		System.out.println("Entro en el save");
		if (this.checkNameMailbox(mailbox)) {
			final ObjectError error = new ObjectError("mailbox.name", "Ya existe esa box");
			binding.addError(error);
			binding.rejectValue("name", "error.mailbox.name");
		}
		if (binding.hasErrors()) {
			System.out.println("Entro en el binding");
			System.out.println(binding.getAllErrors().get(0));
			result = this.createEditModelAndView(mailbox);
		} else
			try {
				System.out.println("entro en update");
				final Mailbox updated = this.mailboxService.update(mailbox);
				if (!logged.getMailboxes().contains(mailbox)) {
					logged.getMailboxes().add(updated);
					final Actor saved = this.actorService.save(logged);
				}
				System.out.println(logged.getMailboxes());
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("entro en oopss");
				System.out.println(oops);
				result = this.createEditModelAndView(mailbox, "mailbox.commit.error");
			}

		return result;
	}
	protected ModelAndView createEditModelAndView(final Mailbox mailbox) {
		ModelAndView result;

		result = this.createEditModelAndView(mailbox, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Mailbox mailbox, final String messageCode) {
		ModelAndView result;
		final UserAccount login = LoginService.getPrincipal();
		final Actor logged = this.actorService.findByUserAccountId(login.getId());

		final Boolean idMail = mailbox.getId() == 0;

		System.out.println(mailbox.getId() == 0);

		if (mailbox.getIsDefault() == true || mailbox == null || (!logged.getMailboxes().contains(mailbox) && !idMail)) {
			result = new ModelAndView("mailbox/list");
			result.addObject("mailboxes", logged.getMailboxes());
			result.addObject("requestURI", "mailbox/list.do");
			return result;
		}

		result = new ModelAndView("mailbox/edit");
		result.addObject("mailbox", mailbox);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int mailboxId) {
		ModelAndView result;

		final Mailbox mailbox = this.mailboxService.findOne(mailboxId);

		final UserAccount login = LoginService.getPrincipal();
		final Actor logged = this.actorService.findByUserAccountId(login.getId());

		if (mailbox.getIsDefault() == true || mailbox == null || !logged.getMailboxes().contains(mailbox)) {
			result = new ModelAndView("mailbox/list");
			result.addObject("mailboxes", logged.getMailboxes());
			result.addObject("requestURI", "mailbox/list.do");
			return result;
		}


		System.out.println("Mailbox encontrado: " + mailbox);
		Assert.notNull(mailbox, "mailbox.null");

		try {
			System.out.println("entra en delete Mailbox");
			this.mailboxService.delete(mailbox);
			result = listMailbox();
		} catch (final Exception e) {
			System.out.println(e);
			System.out.println("entra en catch");
			result = this.createEditModelAndView(mailbox, "mailbox.commit.error");
		}

		return result;
	}
}