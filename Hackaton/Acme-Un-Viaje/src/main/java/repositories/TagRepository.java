
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

	@Query("select t from Tag t where t.actorId=?1 and t.messageId=?2")
	Tag getTagByMessage(int ActorId, int MessageId);

}