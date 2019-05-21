
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
import domain.Tag;

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
	TagService				tagService;
	@Autowired
	private Validator			validator;

	@Autowired
	private CustomerService		customerService;

	public Message reconstruct(final Message message, final BindingResult binding) {
		Message result;
		final Collection<Mailbox> boxes = new ArrayList<>();
		Collection<Tag> tags = new ArrayList<Tag>();
		
		UserAccount user = LoginService.getPrincipal();
		Actor a = actorService.getActorByUserId(user.getId());

		if (message.getId() == 0) {
			message.setMoment(LocalDate.now().toDate());
			message.setMailboxes(boxes);
			message.setSender(a.getEmail());
			result = message;
		} else {
			result = this.messageRepository.findOne(message.getId());
			message.setId(result.getId());
			message.setVersion(result.getVersion());
			message.setBody(result.getBody());
			message.setEmailReceiver(result.getEmailReceiver());
			message.setMoment(result.getMoment());
			message.setSubject(result.getSubject());
			message.setSender(result.getSender());

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
		
			List<Tag> tags = (List<Tag>) message.getTags();
			Tag old = tags.get(0);
			old.setActorId(sender.getId());
			
			
			
			Tag tag = tagService.create();
			tag.setActorId(receiverId);
			
			message.setTags(new ArrayList<Tag>());
			Collection<Tag> tagsActor = new ArrayList<Tag>();
			tagsActor.add(old);
			tagsActor.add(tag);
		
			
		return message;
	}
	public Message sendBroadcast(final Message message) {
		final Admin a = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<Mailbox> inBoxAdmin = this.mailboxService.getAdminInBox();
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

		Assert.isTrue(message.getEmailReceiver().contains(a.getEmail()) || message.getSender().equals(a.getEmail()));
		
		if(message.getTags().contains("DELETED")) {
			messageRepository.delete(message.getId());
		}else {
			Tag tag = tagService.create();
			tag.setActorId(a.getId());
			tag.setMessageId(message.getId());
			tag.setTag("DELETED");
			message.getTags().add(tag);
		}
		
		return message;

	}
	public Message findOne(final int id) {
		return this.messageRepository.findOne(id);
	}

	public Message save(final Message message) {
		return this.messageRepository.save(message);
	}
	
	public void editMailbox(final Message message) {
		System.out.println("new Boxes");
		System.out.println(message.getMailboxes());

		final List<Mailbox> listNewBoxes = new ArrayList<>();
		listNewBoxes.addAll(message.getMailboxes());

		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.getActorByUserId(login.getId());

		final Message oldMessage = this.findOne(message.getId());
		System.out.println("antiguas Boxes");
		System.out.println(oldMessage.getMailboxes());

		final Collection<Mailbox> boxesJsp = message.getMailboxes();

		message.getMailboxes().addAll(oldMessage.getMailboxes());

		System.out.println(message.getMailboxes());

		System.out.println("nuevas");
		System.out.println(listNewBoxes);

		for (final Mailbox mailbox : oldMessage.getMailboxes()) {
			System.out.println(sender.getMailboxes().contains(mailbox));
			System.out.println(boxesJsp.contains(mailbox));
			System.out.println(!listNewBoxes.contains(mailbox));
			if (sender.getMailboxes().contains(mailbox) && boxesJsp.contains(mailbox) && !(listNewBoxes.contains(mailbox)))
				message.getMailboxes().remove(mailbox);
		}
		System.out.println(sender.getName());
		System.out.println(sender.getEmail());
	}
}