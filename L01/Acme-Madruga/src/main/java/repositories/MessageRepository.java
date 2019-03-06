
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

/*
 * CONTROL DE CAMBIOS MessageRepository.java
 * 
 * Antonio Salvat 23/02/2019 19:49 CREACIÓN DE LA CLASE
 */

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from MessageBox ma join ma.messages m where ma.name='spam box'")
	Collection<Message> getMessageSpamBox();

	//	@Query("select ma.messages from Actor a join a.messageBoxes ma where ma.name = 'out box' and a.id=?1")
	//	Collection<Message> getMessageOutBoxFromActor();
}
