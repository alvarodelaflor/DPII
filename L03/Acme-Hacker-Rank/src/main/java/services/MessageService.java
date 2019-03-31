
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.MessageRepository;
import domain.Message;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository	messageRepository;


	public Collection<Message> getSendedMessagesByActor(final int actorId) {
		return this.messageRepository.getSendedMessagesByActor(actorId);
	}

}
