
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.email = ?1")
	Actor getActorByEmail(String email);

	@Query("select a from Actor a join a.userAccount u where u.id = ?1")
	Actor getActorByUserId(Integer id);

	@Query("select a.email from Actor a where a.email!=null")
	Collection<String> getEmailofActors();

	@Query("select a from Actor a join a.messageBoxes m where m.id=?1")
	Actor getActorByMessageBox(Integer id);

}
