
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.validation.Validator;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Admin;
import domain.Mailbox;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Managed repository
	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	MailboxService				mailboxService;
	@Autowired
	AdminService		administratorService;
	@Autowired
	ActorService				actorService;
	@Autowired
	private Validator			validator;

	@Autowired
	private CustomerService		customerService;

	public Message reconstruct(final Message message, final BindingResult binding) {
		Message result;
		final Collection<Mailbox> boxes = new ArrayList<>();

		if (message.getId() == 0) {
			message.setMoment(LocalDate.now().toDate());
			message.setMailboxes(boxes);
			result = message;
		} else {
			result = this.messageRepository.findOne(message.getId());
			message.setId(result.getId());
			message.setVersion(result.getVersion());

			result = message;
		}
		System.out.println(message.getMoment());
		this.validator.validate(message, binding);
		return result;
	}
	public Message create() {
		final Collection<Mailbox> boxes = new ArrayList<>();
		final Message m = new Message();
		m.setMoment(LocalDateTime.now().toDate());
		m.setBody("");
		m.setMailboxes(boxes);
		return m;
	}

	public Message exchangeMessage(final Message message, final Integer receiverId) {
		//this.checkSuspicious(message);

		final UserAccount userSender = LoginService.getPrincipal();
		final Actor sender = this.actorService.findByUserAccountId(userSender.getId());
		final Mailbox outBoxSender = this.mailboxService.getOutBoxActor(sender.getId());

		if (!outBoxSender.getMessages().contains(message) && !message.getMailboxes().contains(outBoxSender)) {
			outBoxSender.getMessages().add(message);
			message.getMailboxes().add(outBoxSender);
		}
		Mailbox boxReceiver = null;

			boxReceiver = this.mailboxService.getInBoxActor(receiverId);
			boxReceiver.getMessages().add(message);
			message.getMailboxes().add(boxReceiver);
		

		return message;
	}
	public Message sendBroadcast(final Message message) {
		final Admin a = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Mailbox> inBoxAdmin = this.mailboxService.getAdminInBox();
		final Mailbox spamBoxAdmin = this.mailboxService.getSpamBoxActor(a.getId());
		final Mailbox outBoxAdmin = this.mailboxService.getOutBoxActor(a.getId());

		System.out.println(outBoxAdmin);

		message.getMailboxes().add(outBoxAdmin);
		outBoxAdmin.getMessages().add(message);

		Assert.notNull(a);

			final Collection<Mailbox> result = this.mailboxService.getInbox();

			result.removeAll(inBoxAdmin);

			for (final Mailbox mailbox : result) {
				message.getMailboxes().add(mailbox);
				mailbox.getMessages().add(message);
			
		}

		return message;
	}
	public Message delete(final Message message, final Integer mailboxId) {
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(user.getId());

		final Collection<Mailbox> boxes = a.getMailboxes();
		final List<Mailbox> boxesList = new ArrayList<>();
		boxesList.addAll(boxes);
		System.out.println("aqui");
		System.out.println(boxesList);
		Mailbox trash = null;
		for (int i = 0; i < boxesList.size(); i++)
			if (boxesList.get(i).getName().equals("trashBox")) {
				System.out.println("Entra aqui");
				trash = boxesList.get(i);
			}
		System.out.println("aqui 2");
		System.out.println(trash);

		final Mailbox v = this.mailboxService.findOne(mailboxId);

		System.out.println(v);

		if (v.getName().equals("trashBox"))
			for (int i = 0; i < boxesList.size(); i++) {
				message.getMailboxes().remove(boxesList.get(i));
				boxesList.get(i).getMessages().remove(message);

			}
		else {
			System.out.println("falla aqui");
			message.getMailboxes().remove(v);
			v.getMessages().remove(message);

			if (!trash.getMessages().contains(message)) {
				message.getMailboxes().add(trash);
				trash.getMessages().add(message);
			}
		}

		if (message.getMailboxes().size() == 0)
			this.messageRepository.delete(message);

		return message;

	}
	public Message findOne(final int id) {
		return this.messageRepository.findOne(id);
	}

	public Message save(final Message message) {
		return this.messageRepository.save(message);
	}
}