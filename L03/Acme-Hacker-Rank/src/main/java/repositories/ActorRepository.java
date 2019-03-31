
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.username = ?1")
	Actor getActorByUser(String userName);

	@Query("select a from Actor a where a.email = ?1")
	Collection<Actor> getActorByEmail(String email);

	@Query("select a from Actor a where a.email = ?1 and a.email != ?2")
	Actor getActorByEmail(String email, String emailA);

	@Query("select a from Actor a join a.userAccount aua where aua.id=?1")
	Actor findByUserAccountId(int userAccountId);

}
