
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageBox;

/*
 * CONTROL DE CAMBIOS MessageBoxRepository.java
 * 
 * Antonio Salvat 23/02/2019 19:49 CREACIÓN DE LA CLASE
 */

@Repository
public interface MessageBoxRepository extends JpaRepository<MessageBox, Integer> {

	//	@Query("select distinct m from Actor a join a.mailboxes m where m.isDefault=true and a.id = ?1")
	//	Collection<MessageBox> getCustomMessageBox(Integer id);

	@Query("select m from MessageBox m where m.name = 'in box'")
	Collection<MessageBox> getInBox();

	@Query("select m from MessageBox m where m.name = ?1")
	Collection<MessageBox> getBoxWithName(String name);

	@Query("select m from MessageBox m where m.name = 'spam box'")
	Collection<MessageBox> getspamBox();

	@Query("select m from MessageBox m where m.name = 'notification box'")
	Collection<MessageBox> getNotificationBox();

	@Query("select m from Administrator a join a.messageBoxes m where a.id = ?1 and m.name = 'in box'")
	Collection<MessageBox> getAdminInBox(Integer id);

	@Query("select m from Administrator a join a.messageBoxes m where a.id = ?1 and m.name = 'notification box'")
	Collection<MessageBox> getAdminNotificationBox(Integer id);

	@Query("select m from Actor a join a.messageBoxes m where a.id = ?1 and m.name = 'in box'")
	Collection<MessageBox> getInBoxActor(Integer id);

	@Query("select m from Actor a join a.messageBoxes m where a.id = ?1 and m.name = 'notification box'")
	Collection<MessageBox> getNotificationBoxActor(Integer id);

	@Query("select m from Actor a join a.messageBoxes m where a.id = ?1 and m.name = 'trash box'")
	Collection<MessageBox> getTrashBoxActor(Integer id);

	@Query("select m from Actor a join a.messageBoxes m where a.id = ?1 and m.name = 'out box'")
	Collection<MessageBox> getOutBoxActor(Integer id);

	@Query("select m from Actor a join a.messageBoxes m where a.id = ?1 and m.name = 'spam box'")
	Collection<MessageBox> getSpamBoxActor(Integer id);

	@Query("select m from MessageBox m where m.isDefault = false")
	Collection<MessageBox> getBoxDefault();

	@Query("select m from MessageBox m where m.isDefault = false and m.id = ?1")
	MessageBox getBoxDefaultId(Integer Id);

	@Query("select m from Actor a join a.messageBoxes m where a.id = ?1 and m.parentBox = null")
	Collection<MessageBox> getParentBoxActor(Integer id);

	@Query("select m from MessageBox m where m.parentBox.id = ?1")
	Collection<MessageBox> getSonBox(Integer id);

	@Query("select m.name from Actor a join a.messageBoxes m where a.id=?1")
	Collection<String> getNameBox(Integer id);

}
