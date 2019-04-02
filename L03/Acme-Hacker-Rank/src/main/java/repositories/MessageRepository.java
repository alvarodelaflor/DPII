
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m where m.sender=?1")
	Collection<Message> getSendedMessagesByActor(String emailActor);

	@Query("select m from Message m where m.recipient=?1")
	Collection<Message> getReceivedMessagesByActor(String emailActors);
}
