
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

	@Query("select a from Actor a join a.userAccount aua where aua.id=?1")
	Actor findByUserAccountId(int userAccountId);

	@Query("select a from Actor a where a.email = ?1 and a.email != ?2")
	Actor getCompareEmailActor(String email, String emailA);

	@Query("select a from Actor a where a.email = ?1")
	Collection<Actor> getActoresSameEmail(String email);
	
	@Query("select a.email from Actor a where a.email!=null")
	Collection<String> getEmailofActors();
	
	@Query("select a from Actor a join a.mailboxes m where m.id=?1")
	Actor getActorByMailbox(Integer id);
	
	@Query("select a from Actor a where a.email = ?1")
	Actor getActorByEmail2(String email);

	@Query("select a from Actor a join a.userAccount u where u.id = ?1")
	Actor getActorByUserId(Integer id);

	@Query("select a from Actor a where a.userAccount.banned = true and a not in (select ad from Admin ad)")
	Collection<Actor> findAllBannedButAdmins();

	@Query("select a from Actor a where a.userAccount.banned = false and a not in (select ad from Admin ad)")
	Collection<Actor> findAllNonBannedButAdmins();
}
