
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.TravelPack;

@Repository
public interface TravelPackRepository extends JpaRepository<TravelPack, Integer> {

	@Query("select p from TravelPack p where p.travelAgency.id = ?1")
	Collection<TravelPack> getTravelAgencyPacks(int id);

	//@Query("select a from Actor a where a.userAccount.banned = false and a not in (select ad from Admin ad)")

	@Query("select t from TravelPack t join t.accomodations ta where ta.accomodation.id = ?1")
	Collection<TravelPack> getTravelPacksAccomodationId(int id);

}
