
package services;

import java.util.ArrayList;
import java.util.Arrays;
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
 * Antonio Salvat 23/02/2019 19:49 CREACI�N DE LA CLASE
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
	WelcomeService				welcomeService;
	@Autowired
	private Validator			validator;


	//private final List<String>	spamWords	= Arrays.asList("sex", "viagra", "cialis", "ferrete", "one million", "you've been selected", "Nigeria", "queryfonsiponsypaferrete", "sexo", "un mill�n", "ha sido seleccionado");

	//Carmen: M�todo para a�adir spam words (adm)
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
		System.out.println(message.getMoment());
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

		System.out.println("======> Esto es el moment de message en el msgService.save" + message.getMoment());
		//Capturo actor logeado seg�n su UserAcc.Id
		final UserAccount uacc = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccountId(uacc.getId());
		//Actualizo contador total de msg
		actor.getUserAccount().setMsgCounter(uacc.getMsgCounter() + 1.);
		//Actualizo contador de msg de spam
		if (this.checkSuspiciousWithBoolean(message) == true)
			actor.getUserAccount().setSpamMsgCounter(uacc.getSpamMsgCounter() + 1.);
		//Calculo el spammerFlag del UserAcc
		actor.getUserAccount().setSpammerFlag(this.spammerFlagCheck(actor.getUserAccount()));
		System.out.println("====================================" + "se ha ejecutado spammerFlagCheck con resultado: " + this.spammerFlagCheck(actor.getUserAccount()));
		//Guardo Actor con el UserAcc modificado
		this.actorService.save(actor);
		System.out.println("Hace el save");
		//Calculo la nueva polaridad segun el msg
		final Double newPolarity = this.polarityScoreCalculation(message, uacc);
		System.out.println("<<<<<<<<<<<<<<<<<Calcula la polarity");
		actor.getUserAccount().setPolarity(newPolarity);
		//Guardo Actor con el UserAcc modificado 2
		this.actorService.save(actor);
		//Guardo el Msg
		System.out.println("=======--------LLEGA AL RETURN DE MSG SAVE----------======");
		return this.messageRepository.save(message);
	}

	// Check for spam: return true if the msg contains an spam word
	private Boolean checkSuspiciousWithBoolean(final Message msg) {
		System.out.println("check suspicious");
		Boolean res = false;

		final Collection<String> spamWords = this.initializeSpamWordsMsg();

		for (final String word : spamWords)
			if (msg.getBody().contains(word)) {
				System.out.println("es spam");
				res = true;
				//break;
			}

		return res;
	}

	private Collection<String> initializeSpamWordsMsg() {

		Collection<String> res;

		if (this.welcomeService.getSpamWords().size() == 0)
			res = this.welcomeService.listSpamWords();
		else
			res = this.welcomeService.getSpamWords();
		return res;
	}

	// spammerFlag = true if condition is fulfilled
	private Boolean spammerFlagCheck(final UserAccount uacc) {

		Boolean res = false;

		if (uacc.getMsgCounter() != 0) {

			final Double ratiospam = uacc.getSpamMsgCounter() / uacc.getMsgCounter();
			System.out.println("----------------------------" + ratiospam);
			final Double percentage = uacc.getMsgCounter() * 0.1;
			System.out.println("----------------------------" + percentage);
			res = ratiospam > percentage;
		}

		return res;
	}

	// polarityScore calculation whenever a msg is saved
	private Double polarityScoreCalculation(final Message message, final UserAccount uacc) {

		final Message msg = message;
		// Traigo la polarity de la cuenta
		final Double res = uacc.getPolarity();
		// Traigo la lista de scoring words positivas de adminService
		final HashSet<String> scoringWordsPos;
		System.out.println("se queda aqui 0");
		if (this.administratorService.getScoreWordsPos().size() != 0)
			scoringWordsPos = this.administratorService.getScoreWordsPos();
		else
			scoringWordsPos = this.administratorService.listScoreWordsPos();
		// Traigo la lista de scoring words negativas de adminService
		final HashSet<String> scoringWordsNeg;
		System.out.println("se queda aqui 1");
		if (this.administratorService.getScoreWordsNeg().size() != 0)
			scoringWordsNeg = this.administratorService.getScoreWordsNeg();
		else
			scoringWordsNeg = this.administratorService.listScoreWordsNeg();
		// Paso el body del msg a una lista de string
		String[] msgWords;
		System.out.println("se queda aqui 2");

		final String msgBody = msg.getBody();
		msgBody.trim();
		msgBody.replace(",", "");
		msgBody.replace(".", "");
		msgBody.replace(":", "");
		msgBody.replace(";", "");
		msgWords = msgBody.split(" ");
		final List<String> msgWordsList = Arrays.asList(msgWords);
		// Guardo el tama�o inicial de la lista
		final Double msgWordsSize = (double) msgWordsList.size();
		// Calculo score positivo
		final List<String> copia = new ArrayList<String>(msgWordsList);
		final List<String> scoringWordPosList = new ArrayList<String>(scoringWordsPos);
		copia.removeAll(scoringWordPosList);
		System.out.println("se queda aqui 1");
		final Double posCount = (msgWordsSize - copia.size());
		// Calculo score negativo
		final List<String> copia2 = new ArrayList<String>(msgWordsList);
		final List<String> scoringWordNegList = new ArrayList<String>(scoringWordsNeg);
		copia2.removeAll(scoringWordNegList);
		final Double negCount = (msgWordsSize - copia2.size());
		// Calculo nuevo score total
		final Double count = ((posCount / msgWordsSize) - (negCount / msgWordsSize));
		// Devuelvo la polarityScore nueva (media de las antiguas, podria cambiarse la ponderacion de la media para que la nueva polarity afectara mas o menos)
		// LA PONDERACION NO ESTA DEFINIDA EN LOS REQUISITOS, ASI QUE POR DEFECTO SERA UNA MEDIA NORMAL
		return (res + count) / 2;
	}
	public Message sendNotification(final Message message, final Actor a) {
		//this.checkSuspicious(message);
		final Boolean suspicious = this.checkSuspiciousWithBoolean(message);
		System.out.println("suspicious" + suspicious);

		MessageBox boxReceiver = null;

		if (suspicious) {
			System.out.println("id del exchangeMessafe");
			System.out.println(a.getId());
			boxReceiver = this.messageBoxService.getSpamBoxActor(a.getId());
			System.out.println(boxReceiver);
			boxReceiver.getMessages().add(message);
			message.getMessageBoxes().add(boxReceiver);
		} else {
			boxReceiver = this.messageBoxService.getNotificationBoxActor(a.getId());
			boxReceiver.getMessages().add(message);
			message.getMessageBoxes().add(boxReceiver);
		}

		return message;
	}

	public Message sendNotificationByEmails(final Message message) {
		//this.checkSuspicious(message);
		final Boolean suspicious = this.checkSuspiciousWithBoolean(message);
		System.out.println("suspicious" + suspicious);

		final Collection<String> emails = message.getEmailReceiver();
		final List<String> emailsList = new ArrayList<>();
		emailsList.addAll(emails);

		for (int i = 0; i < emailsList.size(); i++) {
			MessageBox boxReceiver = null;
			final Actor a = this.actorService.getActorByEmail(emailsList.get(i));
			if (suspicious) {
				System.out.println("id del exchangeMessafe");
				System.out.println(a.getId());
				boxReceiver = this.messageBoxService.getSpamBoxActor(a.getId());
				System.out.println(boxReceiver);
				boxReceiver.getMessages().add(message);
				message.getMessageBoxes().add(boxReceiver);
			} else {
				boxReceiver = this.messageBoxService.getNotificationBoxActor(a.getId());
				boxReceiver.getMessages().add(message);
				message.getMessageBoxes().add(boxReceiver);
			}
		}

		return message;
	}

	public void editMessageBox(final Message message) {
		System.out.println("new Boxes");
		System.out.println(message.getMessageBoxes());

		final List<MessageBox> listNewBoxes = new ArrayList<>();
		listNewBoxes.addAll(message.getMessageBoxes());

		final UserAccount login = LoginService.getPrincipal();
		final Actor sender = this.actorService.getActorByUserId(login.getId());

		final Message oldMessage = this.findOne(message.getId());
		System.out.println("antiguas Boxes");
		System.out.println(oldMessage.getMessageBoxes());

		final Collection<MessageBox> boxesJsp = message.getMessageBoxes();

		message.getMessageBoxes().addAll(oldMessage.getMessageBoxes());

		System.out.println(message.getMessageBoxes());

		System.out.println("nuevas");
		System.out.println(listNewBoxes);

		for (final MessageBox messageBox : oldMessage.getMessageBoxes()) {
			System.out.println(sender.getMessageBoxes().contains(messageBox));
			System.out.println(boxesJsp.contains(messageBox));
			System.out.println(!listNewBoxes.contains(messageBox));
			if (sender.getMessageBoxes().contains(messageBox) && boxesJsp.contains(messageBox) && !(listNewBoxes.contains(messageBox)))
				message.getMessageBoxes().remove(messageBox);
		}
		System.out.println(sender.getName());
		System.out.println(sender.getEmail());
	}

}
