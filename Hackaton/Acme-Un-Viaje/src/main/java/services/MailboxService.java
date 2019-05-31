
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MailboxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Admin;
import domain.Mailbox;
import domain.Message;

@Service
@Transactional
public class MailboxService {

	//Managed repository
	@Autowired
	private MailboxRepository		mailboxRepository;

	@Autowired
	private AdminService	administratorService;

	@Autowired
	private ActorService			actorService;
	
	@Autowired
	private Validator			validator;

	
	public Mailbox reconstruct(final Mailbox mailbox, final BindingResult binding) {
		Mailbox result;

		if (mailbox.getId() == 0) {
			mailbox.setIsDefault(false);
			result = mailbox;
		} else {
			result = this.mailboxRepository.findOne(mailbox.getId());
			//			result.setTitle(floatBro.getTitle());
			//			result.setDescription(floatBro.getDescription());
			//			result.setPictures(floatBro.getPictures());
			//			if (result.getBrotherhood() == null)
			//				result.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
			mailbox.setId(result.getId());
			mailbox.setVersion(result.getVersion());
			mailbox.setIsDefault(result.getIsDefault());

			result = mailbox;
		}
		this.validator.validate(mailbox, binding);
		return result;
	}
	

	public Mailbox create() {
		final Mailbox result = new Mailbox();

		final Collection<Message> messages = new ArrayList<>();
		result.setMessages(messages);
		result.setIsDefault(false);
		return result;
	}

	public Mailbox update(final Mailbox mailbox) {
		final Mailbox oldMailbox = this.mailboxRepository.findOne(mailbox.getId());
		System.out.println(oldMailbox);
		if (oldMailbox != null)
			if (oldMailbox.getName() == "inBox" || oldMailbox.getName() == "outBox" || oldMailbox.getName() == "spamBox" || oldMailbox.getName() == "trashBox" )
				Assert.isTrue(oldMailbox.getName() == mailbox.getName());
		System.out.println("falla aqui");
		final Mailbox result = this.mailboxRepository.save(mailbox);
		System.out.println("no falla donde creo");
		return result;
	}

	public Mailbox save(final Mailbox mailbox) {
		final Mailbox result = this.mailboxRepository.save(mailbox);
		return result;
	}

	public void delete(final Mailbox mailbox) {
		System.out.println(mailbox);
		final Boolean isDefault = mailbox.getIsDefault();
		System.out.println(isDefault);
		Assert.isTrue(isDefault == null || isDefault == false);
		System.out.println("pasa el default");
		Assert.notNull(this.mailboxRepository.findOne(mailbox.getId()), "La fixUp no existe");
		System.out.println("pasa el findOne");
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.findByUserAccountId(user.getId());
		a.getMailboxes().remove(mailbox);
		this.mailboxRepository.delete(mailbox);
	}
	public Mailbox findOne(final int id) {
		final Mailbox result = this.mailboxRepository.findOne(id);
		return result;
	}

	public Collection<Mailbox> findAll() {
		final Collection<Mailbox> result = this.mailboxRepository.findAll();
		return result;
	}

	public Mailbox findOneDefault(final int id) {
		final Mailbox result = this.mailboxRepository.getBoxDefaultId(id);
		return result;
	}

	public Collection<Mailbox> findAllDefault() {
		final Collection<Mailbox> result = this.mailboxRepository.getBoxDefault();
		return result;
	}

	public Collection<Mailbox> getInbox() {
		return this.mailboxRepository.getInBox();
	}

	public Collection<Mailbox> getAdminInBox() {
		final UserAccount adminUser = LoginService.getPrincipal();
		final Admin administrator = this.administratorService.findByUserAccountId(adminUser.getId());
		final Collection<Mailbox> box = this.mailboxRepository.getAdminInBox(administrator.getId());
		return box;
	}

	//broadcast
	public Collection<Mailbox> addMessageCollectionInBpox(final Message message) {
		final Admin a = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Collection<Mailbox> result = this.mailboxRepository.getInBox();

		final Collection<Mailbox> inBoxAdmin = this.getAdminInBox();

		result.removeAll(inBoxAdmin);

		for (final Mailbox mailbox : result) {
			final Collection<Message> messages = mailbox.getMessages();
			messages.add(message);
			mailbox.setMessages(messages);
		}
		return result;
	}

	public Mailbox getInBoxActor(final Integer id) {
		final Collection<Mailbox> inBoxCollection = this.mailboxRepository.getInBoxActor(id);
		final List<Mailbox> inBoxList = (List<Mailbox>) inBoxCollection;
		Mailbox inbox = null;
		if (inBoxList.size() > 0)
			inbox = inBoxList.get(0);
		return inbox;
	}
	

	public Mailbox getInBoxActorEmail(final String email) {
		return mailboxRepository.getInBoxActorEmail(email);
	}
	public Collection<Mailbox> getInBox() {

		return this.mailboxRepository.getInBox();
	}

	public Collection<Mailbox> getspamBox() {

		return this.mailboxRepository.getspamBox();
	}

	public Mailbox getTrashBoxActor(final Integer id) {

		final Collection<Mailbox> inBoxCollection = this.mailboxRepository.getTrashBoxActor(id);
		final List<Mailbox> TrashBoxList = (List<Mailbox>) inBoxCollection;
		final Mailbox trashBox = TrashBoxList.get(0);
		return trashBox;
	}

	public Mailbox getSpamBoxActor(final Integer id) {
		System.out.println("id del getSpamBoxActor");
		System.out.println(id);
		final Collection<Mailbox> spamBoxCollection = this.mailboxRepository.getSpamBoxActor(id);
		System.out.println(spamBoxCollection);
		final List<Mailbox> SpamBoxList = (List<Mailbox>) spamBoxCollection;
		System.out.println(SpamBoxList);
		System.out.println("falla aqui");
		final Mailbox spamBox = SpamBoxList.get(0);
		System.out.println("falla aqui");
		return spamBox;
	}
	public Mailbox getOutBoxActor(final Integer id) {
		final Collection<Mailbox> outBoxCollection = this.mailboxRepository.getOutBoxActor(id);
		final List<Mailbox> outBoxList = (List<Mailbox>) outBoxCollection;
		final Mailbox inBox = outBoxList.get(0);
		return inBox;
	}
}