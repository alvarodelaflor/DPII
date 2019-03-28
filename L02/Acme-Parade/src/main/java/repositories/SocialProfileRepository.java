
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.SocialProfile;

@Repository
public interface SocialProfileRepository extends JpaRepository<SocialProfile, Integer> {

	@Query("select a from Actor a join a.socialProfiles s where s.id = ?1")
	Actor findActorBySocialProfile(int socialProfileId);

}
