
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Message;
import domain.MessageBox;

/*
 * CONTROL DE CAMBIOS MessageService.java
 * 
 * Antonio Salvat 23/02/2019 19:49 CREACIÓN DE LA CLASE
 */

@Service
@Transactional
public class MessageService {

	//Managed repository
	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	MessageBoxService			messageBoxService;
	@Autowired
	AdministratorService		administratorService;
	@Autowired
	ActorService				actorService;
	@Autowired
	private Validator			validator;

	//private final List<String>	spamWords	= Arrays.asList("sex", "viagra", "cialis", "ferrete", "one million", "you've been selected", "Nigeria", "queryfonsiponsypaferrete", "sexo", "un millón", "ha sido seleccionado");

	public HashSet<String>		spamWords;


	//Carmen: Método para añadir spam words (adm)
	//	public HashSet<String> newSpamWords(final String newWord) {
	//		this.listSpamWords().add(newWord);
	//		return this.listSpamWords();
	//	}

	public Message reconstruct(final Message message, final BindingResult binding) {
		Message result;
		final Collection<MessageBox> boxes = new ArrayList<>();

		if (message.getId() == 0) {
			message.setMoment(LocalDate.now().toDate());
			message.setMessageBoxes(boxes);
			result = message;
		} else {
			result = this.messageRepository.findOne(message.getId());
			//			result.setTitle(floatBro.getTitle());
			//			result.setDescription(floatBro.getDescription());
			//			result.setPictures(floatBro.getPictures());
			//			if (result.getBrotherhood() == null)
			//				result.setBrotherhood(this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId()));
			message.setId(result.getId());
			message.setVersion(result.getVersion());

			result = message;
		}
		this.validator.validate(message, binding);
		return result;
	}
	public Message create() {
		final Collection<MessageBox> boxes = new ArrayList<>();
		final Message m = new Message();
		m.setMoment(LocalDateTime.now().toDate());
		m.setBody("");
		m.setMessageBoxes(boxes);
		return m;
	}

	public Message exchangeMessage(final Message message, final Integer receiverId) {
		//this.checkSuspicious(message);
		final Boolean suspicious = this.checkSuspiciousWithBoolean(message);
		System.out.println("suspicious" + suspicious);

		final UserAccount userSender = LoginService.getPrincipal();
		final Actor sender = this.actorService.getActorByUserId(userSender.getId());
		final MessageBox outBoxSender = this.messageBoxService.getOutBoxActor(sender.getId());

		System.out.println(sender);
		System.out.println(outBoxSender);

		System.out.println(!outBoxSender.getMessages().contains(message));
		System.out.println(!message.getMessageBoxes().contains(outBoxSender));

		if (!outBoxSender.getMessages().contains(message) && !message.getMessageBoxes().contains(outBoxSender)) {
			outBoxSender.getMessages().add(message);
			message.getMessageBoxes().add(outBoxSender);
		}
		MessageBox boxReceiver = null;

		if (suspicious) {
			System.out.println("id del exchangeMessafe");
			System.out.println(receiverId);
			boxReceiver = this.messageBoxService.getSpamBoxActor(receiverId);
			System.out.println(boxReceiver);
			boxReceiver.getMessages().add(message);
			message.getMessageBoxes().add(boxReceiver);
		} else {
			boxReceiver = this.messageBoxService.getInBoxActor(receiverId);
			boxReceiver.getMessages().add(message);
			message.getMessageBoxes().add(boxReceiver);
		}

		return message;
	}
	public Message sendNotification(final Message message) {
		final Administrator a = this.administratorService.getAdministratorByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<MessageBox> notificationBoxAdministrator = this.messageBoxService.getAdministratorNotificationBox();
		final MessageBox spamBoxAdministrator = this.messageBoxService.getSpamBoxActor(a.getId());
		final MessageBox outBoxAdministrator = this.messageBoxService.getOutBoxActor(a.getId());

		System.out.println(outBoxAdministrator);

		message.getMessageBoxes().add(outBoxAdministrator);
		outBoxAdministrator.getMessages().add(message);

		Assert.notNull(a);
		final Boolean suspicious = this.checkSuspiciousWithBoolean(message);

		if (suspicious) {
			final Collection<MessageBox> result = this.messageBoxService.getspamBox();
			result.remove(spamBoxAdministrator);
			for (final MessageBox messageBox : result) {
				message.getMessageBoxes().add(messageBox);
				messageBox.getMessages().add(message);
			}
		} else {
			final Collection<MessageBox> result = this.messageBoxService.getNotificationbox();

			result.removeAll(notificationBoxAdministrator);

			for (final MessageBox messageBox : result) {
				message.getMessageBoxes().add(messageBox);
				messageBox.getMessages().add(message);
			}
		}

		return message;
	}
	public Message delete(final Message message, final Integer messageBoxId) {
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(user.getId());

		final Collection<MessageBox> boxes = a.getMessageBoxes();
		final List<MessageBox> boxesList = new ArrayList<>();
		boxesList.addAll(boxes);
		System.out.println("aqui");
		System.out.println(boxesList);
		MessageBox trash = null;
		for (int i = 0; i < boxesList.size(); i++)
			if (boxesList.get(i).getName().equals("trash box")) {
				System.out.println("Entra aqui");
				trash = boxesList.get(i);
			}
		System.out.println("aqui 2");
		System.out.println(trash);

		final MessageBox v = this.messageBoxService.findOne(messageBoxId);

		System.out.println(v);

		if (v.getName().equals("trash box"))
			for (int i = 0; i < boxesList.size(); i++) {
				message.getMessageBoxes().remove(boxesList.get(i));
				boxesList.get(i).getMessages().remove(message);

			}
		else {
			System.out.println("falla aqui");
			message.getMessageBoxes().remove(v);
			v.getMessages().remove(message);

			if (!trash.getMessages().contains(message)) {
				message.getMessageBoxes().add(trash);
				trash.getMessages().add(message);
			}
		}

		if (message.getMessageBoxes().size() == 0)
			this.messageRepository.delete(message);

		return message;

	}
	public Message findOne(final int id) {
		return this.messageRepository.findOne(id);
	}

	public Message save(final Message message) {
		return this.messageRepository.save(message);
	}

	private Boolean checkSuspiciousWithBoolean(final Message msg) {
		System.out.println("Entra en suspicious");
		Boolean res = false;
		this.listSpamWords();
		for (final String word : this.spamWords)
			if (msg.getBody().contains(word)) {
				res = true;
				this.actorService.getActorByUserId(LoginService.getPrincipal().getId()).setIsSuspicious(true);
			}
		return res;
	}

	public HashSet<String> listSpamWords() {
		System.out.println("Entra en el list");
		System.out.println(this.spamWords);
		this.spamWords = new HashSet<String>();
		this.spamWords.add("sex");
		System.out.println("falla el add");
		this.spamWords.add("viagra");
		this.spamWords.add("cialis");
		this.spamWords.add("one millon");
		this.spamWords.add("you've been selected");
		this.spamWords.add("Nigeria");
		this.spamWords.add("sexo");
		this.spamWords.add("un millón");
		this.spamWords.add("ha sido seleccionado");
		System.out.println(this.spamWords);
		return this.spamWords;
	}

}
