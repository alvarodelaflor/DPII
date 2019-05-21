
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Mailbox;

@Repository
public interface MailboxRepository extends JpaRepository<Mailbox, Integer> {

	//	@Query("select distinct m from Actor a join a.mailboxes m where m.isDefault=true and a.id = ?1")
	//	Collection<Mailbox> getCustomMailbox(Integer id);

	@Query("select m from Mailbox m where m.name = 'inBox'")
	Collection<Mailbox> getInBox();

	@Query("select m from Mailbox m where m.name = ?1")
	Collection<Mailbox> getBoxWithName(String name);

	@Query("select m from Mailbox m where m.name = 'spamBox'")
	Collection<Mailbox> getspamBox();

	@Query("select m from Admin a join a.mailboxes m where a.id = ?1 and m.name = 'inBox'")
	Collection<Mailbox> getAdminInBox(Integer id);

	@Query("select m from Actor a join a.mailboxes m where a.id = ?1 and m.name = 'inBox'")
	Collection<Mailbox> getInBoxActor(Integer id);

	@Query("select m from Actor a join a.mailboxes m where a.id = ?1 and m.name = 'trashBox'")
	Collection<Mailbox> getTrashBoxActor(Integer id);

	@Query("select m from Actor a join a.mailboxes m where a.id = ?1 and m.name = 'outBox'")
	Collection<Mailbox> getOutBoxActor(Integer id);

	@Query("select m from Actor a join a.mailboxes m where a.id = ?1 and m.name = 'spamBox'")
	Collection<Mailbox> getSpamBoxActor(Integer id);

	@Query("select m from Mailbox m where m.isDefault = false")
	Collection<Mailbox> getBoxDefault();

	@Query("select m from Mailbox m where m.isDefault = false and m.id = ?1")
	Mailbox getBoxDefaultId(Integer Id);

}