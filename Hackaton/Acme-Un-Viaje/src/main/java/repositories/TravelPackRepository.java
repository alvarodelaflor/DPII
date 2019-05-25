
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

	@Query("select p from TravelPack p where p.travelAgency.id = ?1 and p.draft = true")
	Collection<TravelPack> getTravelAgencyDraftPacks(int id);

	@Query("select t from TravelPack t join t.complaints c where c.id = ?1")
	TravelPack findFromComplaint(int complaintId);
}
