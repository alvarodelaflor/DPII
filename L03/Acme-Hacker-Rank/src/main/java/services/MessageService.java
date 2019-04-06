
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Message;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configService;

	@Autowired
	private Validator				validator;


	public Message reconstruct(final Message message, final BindingResult binding) {
		Message result;
		final Collection<String> actors = new ArrayList<>();

		if (message.getId() == 0) {
			message.setMoment(LocalDate.now().toDate());
			message.setRecipient(actors);
			message.setSender("init");
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
			message.setSender(result.getSender());
			message.setRecipient(result.getRecipient());
			result = message;
		}
		System.out.println(message.getMoment());
		this.validator.validate(message, binding);
		return result;
	}

	public Message create() {
		final Message m = new Message();
		m.setMoment(LocalDateTime.now().toDate());
		m.setBody("");
		return m;
	}

	private Boolean checkSuspiciousWithBoolean(final Message msg) {

		Boolean res = false;
		final Collection<String> spamWords = this.configService.getConfiguration().getSpamWords();

		for (final String word : spamWords)
			if (msg.getBody().contains(word)) {
				res = true;
				break;
			}
		return res;
	}

	// spammerFlag = true if condition is fulfilled
	private Boolean spammerFlagCheck(final UserAccount uacc) {

		Boolean res = false;

		if (uacc.getMsgCounter() != 0) {

			final Double ratiospam = uacc.getSpamMsgCounter() / uacc.getMsgCounter();
			final Double percentage = uacc.getMsgCounter() * 0.1;
			res = ratiospam > percentage;
		}

		return res;
	}

	public Message save(final Message message) {

		//Capturo actor logeado seg�n su Username
		final UserAccount uacc = LoginService.getPrincipal();
		final Actor actor = this.actorService.getActorByUser(uacc.getUsername());
		//Actualizo contador total de msg
		actor.getUserAccount().setMsgCounter(uacc.getMsgCounter() + 1.);
		//Actualizo contador de msg de spam
		if (this.checkSuspiciousWithBoolean(message) == true)
			actor.getUserAccount().setSpamMsgCounter(uacc.getSpamMsgCounter() + 1.);
		//Calculo el spammerFlag del UserAcc
		actor.getUserAccount().setSpammerFlag(this.spammerFlagCheck(actor.getUserAccount()));
		//Guardo Actor con el UserAcc modificado
		this.actorService.save(actor);
		//Guardo el Msg
		return this.messageRepository.save(message);
	}

	public Collection<Message> getSendedMessagesByActor(final String emailActor) {
		return this.messageRepository.getSendedMessagesByActor(emailActor);
	}

	public Collection<Message> getReceivedMessagesByActor(final String emailActor) {
		return this.messageRepository.getReceivedMessagesByActor(emailActor);
	}

	public Message findOne(final int messageId) {
		return this.messageRepository.findOne(messageId);
	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message exchangeMessage(final Message message, final Integer receiverId) {
		//this.checkSuspicious(message);

		final UserAccount userSender = LoginService.getPrincipal();
		final Actor sender = this.actorService.getActorByUserId(userSender.getId());

		final Actor receiver = this.actorService.findOne(receiverId);

		message.setSender(sender.getEmail());
		if (!sender.getMessages().contains(message))
			sender.getMessages().add(message);
		message.getRecipient().add(receiver.getEmail());
		if (receiverId != sender.getId())
			receiver.getMessages().add(message);
		return message;
	}

	public void remove(final Message message) {
		final UserAccount userSender = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(userSender.getId());
		if (!message.getTag().equals("DELETED"))
			message.setTag("DELETED");
		else if (this.actorService.getActorsThatContainsAMessage(message.getId()).size() != 1)
			a.getMessages().remove(message);
		else
			this.messageRepository.delete(message);
	}
}
