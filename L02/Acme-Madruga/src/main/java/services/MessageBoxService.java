
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

import repositories.MessageBoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Message;
import domain.MessageBox;

/*
 * CONTROL DE CAMBIOS MessageBoxService.java
 * 
 * Antonio Salvat 23/02/2019 19:49 CREACIÓN DE LA CLASE
 */

@Service
@Transactional
public class MessageBoxService {

	//Managed repository
	@Autowired
	private MessageBoxRepository	messageBoxRepository;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private Validator				validator;


	//	public MessageBox reconstruct(final MessageBox messageBox, final BindingResult binding) {
	//		MessageBox result;
	//
	//		System.out.println(messageBox.getIsDefault());
	//
	//		if (messageBox.getId() == 0) {
	//			result = messageBox;
	//			result.setIsDefault(false);
	//		} else {
	//			result = this.messageBoxRepository.findOne(messageBox.getId());
	//			System.out.println("nino estoy en el reconstruct de messageBox");
	//			System.out.println(result);
	//			System.out.println(messageBox);
	//			System.out.println(messageBox.getIsDefault());
	//			System.out.println(messageBox.getMessages());
	//			System.out.println(messageBox.getParentBox());
	//			result.setIsDefault(messageBox.getIsDefault());
	//			result.setMessages(messageBox.getMessages());
	//			result.setParentBox(messageBox.getParentBox());
	//
	//			this.validator.validate(result, binding);
	//		}
	//		return result;
	//	}

	public MessageBox reconstruct(final MessageBox messageBox, final BindingResult binding, final Integer idParent) {
		MessageBox result;

		final MessageBox parent = this.findOne(idParent);

		if (messageBox.getId() == 0) {
			messageBox.setIsDefault(false);
			messageBox.setParentBox(parent);
			result = messageBox;
		} else {
			result = this.messageBoxRepository.findOne(messageBox.getId());
			//			result.setTitle(floatBro.getTitle());
			//			result.setDescription(floatBro.getDescription());
			//			result.setPictures(floatBro.getPictures());
			//			if (result.getBrotherhood() == null)
			//				result.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
			messageBox.setId(result.getId());
			messageBox.setVersion(result.getVersion());
			messageBox.setIsDefault(result.getIsDefault());
			messageBox.setParentBox(result.getParentBox());

			result = messageBox;
		}
		this.validator.validate(messageBox, binding);
		return result;
	}
	public MessageBox create() {
		final MessageBox result = new MessageBox();

		final Collection<Message> messages = new ArrayList<>();
		result.setMessages(messages);
		result.setIsDefault(false);
		return result;
	}

	public MessageBox update(final MessageBox messageBox) {
		final MessageBox oldMessageBox = this.messageBoxRepository.findOne(messageBox.getId());
		System.out.println(oldMessageBox);
		if (oldMessageBox != null)
			if (oldMessageBox.getName() == "inBox" || oldMessageBox.getName() == "outBox" || oldMessageBox.getName() == "spamBox" || oldMessageBox.getName() == "trashBox")
				Assert.isTrue(oldMessageBox.getName() == messageBox.getName());
		System.out.println("falla aqui");

		System.out.println(messageBox);
		System.out.println(messageBox.getParentBox());

		final MessageBox result = this.messageBoxRepository.save(messageBox);
		System.out.println("no falla donde creo");
		return result;
	}

	public MessageBox save(final MessageBox messageBox) {
		final MessageBox oldMessageBox = this.messageBoxRepository.findOne(messageBox.getId());
		System.out.println(oldMessageBox);
		if (oldMessageBox != null)
			if (oldMessageBox.getName() == "in box" || oldMessageBox.getName() == "out box" || oldMessageBox.getName() == "spam box" || oldMessageBox.getName() == "trash box" || oldMessageBox.getName() == "notification box")
				Assert.isTrue(oldMessageBox.getName() == messageBox.getName());
		System.out.println("falla aqui");
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(user.getId());
		Assert.isTrue(!this.getNameBox(a.getId()).contains(messageBox.getName()));
		final MessageBox result = this.messageBoxRepository.save(messageBox);
		return result;
	}

	public void delete(final MessageBox messageBox) {
		final Collection<MessageBox> sonBoxes = this.getSonBox(messageBox.getId());
		final List<MessageBox> listSonBoxes = new ArrayList<>();
		listSonBoxes.addAll(sonBoxes);

		final List<Message> messages = new ArrayList<>();
		messages.addAll(messageBox.getMessages());

		for (int i = 0; i < messages.size(); i++)
			this.messageService.delete(messages.get(i), messageBox.getId());

		for (int i = 0; i < listSonBoxes.size(); i++)
			this.delete(listSonBoxes.get(i));

		System.out.println(messageBox);
		final Boolean isDefault = messageBox.getIsDefault();
		System.out.println(isDefault);
		Assert.isTrue(isDefault == null || isDefault == false);
		System.out.println("pasa el default");
		Assert.notNull(this.messageBoxRepository.findOne(messageBox.getId()), "La fixUp no existe");
		System.out.println("pasa el findOne");
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(user.getId());
		a.getMessageBoxes().remove(messageBox);
		this.messageBoxRepository.delete(messageBox);
	}
	public MessageBox findOne(final int id) {
		final MessageBox result = this.messageBoxRepository.findOne(id);
		return result;
	}

	public Collection<MessageBox> findAll() {
		final Collection<MessageBox> result = this.messageBoxRepository.findAll();
		return result;
	}

	public MessageBox findOneDefault(final int id) {
		final MessageBox result = this.messageBoxRepository.getBoxDefaultId(id);
		return result;
	}

	public Collection<MessageBox> findAllDefault() {
		final Collection<MessageBox> result = this.messageBoxRepository.getBoxDefault();
		return result;
	}

	public Collection<MessageBox> getInbox() {
		return this.messageBoxRepository.getInBox();
	}

	public Collection<MessageBox> getNotificationbox() {
		return this.messageBoxRepository.getNotificationBox();
	}

	public Collection<MessageBox> getAdministratorInBox() {
		final UserAccount AdministratorUser = LoginService.getPrincipal();
		final Administrator Administratoristrator = this.administratorService.getAdministratorByUserAccountId(AdministratorUser.getId());
		final Collection<MessageBox> box = this.messageBoxRepository.getAdminInBox(Administratoristrator.getId());
		return box;
	}

	public Collection<MessageBox> getAdministratorNotificationBox() {
		final UserAccount AdministratorUser = LoginService.getPrincipal();
		final Administrator Administratoristrator = this.administratorService.getAdministratorByUserAccountId(AdministratorUser.getId());
		final Collection<MessageBox> box = this.messageBoxRepository.getAdminNotificationBox(Administratoristrator.getId());
		return box;
	}

	//broadcast
	public Collection<MessageBox> addMessageCollectionInBpox(final Message message) {
		final Administrator a = this.administratorService.getAdministratorByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Collection<MessageBox> result = this.messageBoxRepository.getInBox();

		final Collection<MessageBox> inBoxAdministrator = this.getAdministratorInBox();

		result.removeAll(inBoxAdministrator);

		for (final MessageBox messageBox : result) {
			final Collection<Message> messages = messageBox.getMessages();
			messages.add(message);
			messageBox.setMessages(messages);
		}
		return result;
	}

	public MessageBox getInBoxActor(final Integer id) {
		final Collection<MessageBox> inBoxCollection = this.messageBoxRepository.getInBoxActor(id);
		final List<MessageBox> inBoxList = (List<MessageBox>) inBoxCollection;
		MessageBox inbox = null;
		if (inBoxList.size() > 0)
			inbox = inBoxList.get(0);
		return inbox;
	}

	public MessageBox getNotificationBoxActor(final Integer id) {
		final Collection<MessageBox> notiBoxCollection = this.messageBoxRepository.getNotificationBoxActor(id);
		final List<MessageBox> notiBoxList = (List<MessageBox>) notiBoxCollection;
		MessageBox notiBox = null;
		if (notiBoxList.size() > 0)
			notiBox = notiBoxList.get(0);
		return notiBox;
	}
	public Collection<MessageBox> getInBox() {

		return this.messageBoxRepository.getInBox();
	}

	public Collection<MessageBox> getspamBox() {

		return this.messageBoxRepository.getspamBox();
	}

	public MessageBox getTrashBoxActor(final Integer id) {

		final Collection<MessageBox> inBoxCollection = this.messageBoxRepository.getTrashBoxActor(id);
		final List<MessageBox> TrashBoxList = (List<MessageBox>) inBoxCollection;
		final MessageBox trashBox = TrashBoxList.get(0);
		return trashBox;
	}

	public MessageBox getSpamBoxActor(final Integer id) {
		System.out.println("id del getSpamBoxActor");
		System.out.println(id);
		final Collection<MessageBox> spamBoxCollection = this.messageBoxRepository.getSpamBoxActor(id);
		System.out.println(spamBoxCollection);
		final List<MessageBox> SpamBoxList = (List<MessageBox>) spamBoxCollection;
		System.out.println(SpamBoxList);
		System.out.println("falla aqui");
		final MessageBox spamBox = SpamBoxList.get(0);
		System.out.println("falla aqui");
		return spamBox;
	}
	public MessageBox getOutBoxActor(final Integer id) {
		final Collection<MessageBox> outBoxCollection = this.messageBoxRepository.getOutBoxActor(id);
		final List<MessageBox> outBoxList = (List<MessageBox>) outBoxCollection;
		final MessageBox inBox = outBoxList.get(0);
		return inBox;
	}

	public Collection<MessageBox> getParentBoxActor(final Integer id) {
		final Collection<MessageBox> parentBoxCollection = this.messageBoxRepository.getParentBoxActor(id);
		return parentBoxCollection;
	}

	public Collection<MessageBox> getSonBox(final Integer id) {
		final Collection<MessageBox> sonBoxCollection = this.messageBoxRepository.getSonBox(id);
		return sonBoxCollection;
	}

	public List<String> getNameBox(final Integer id) {
		final Collection<String> nameBoxCollection = this.messageBoxRepository.getNameBox(id);
		final List<String> nameBoxList = new ArrayList<>();
		nameBoxList.addAll(nameBoxCollection);
		return nameBoxList;
	}
}
