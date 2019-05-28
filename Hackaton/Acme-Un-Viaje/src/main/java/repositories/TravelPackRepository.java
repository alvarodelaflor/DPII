
package repositories;

import java.util.Collection;
import java.util.Date;

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

	@Query("select t from TravelPack t join t.accomodations ta where ta.accomodation.id = ?1")
	Collection<TravelPack> getTravelPacksAccomodationId(int id);

	@Query("select t from TravelPack t where t.customer.id = ?1")
	Collection<TravelPack> getTravelPacksByCustomerId(int id);

	@Query("select t from TravelPack t join t.complaints c where c.id = ?1")
	TravelPack findFromComplaint(int complaintId);

	@Query("select t from TravelPack t where t.customer.id = ?1 and t.draft = false and t.status=null")
	Collection<TravelPack> getLoggedNotDraftStatusNull(int customerId);

	@Query("select t from TravelPack t where t.customer.id = ?1 and t.draft = false and t.status=true")
	Collection<TravelPack> getLoggedNotDraftStatusTrue(int id);

	@Query("select t from TravelPack t where t.customer.id = ?1 and t.draft = false and t.status=false")
	Collection<TravelPack> getLoggedNotDraftStatusFalse(int id);

	@Query("select t from TravelPack t where t.customer.id = ?1")
	Collection<TravelPack> getCustomerTravelPacks(int id);

	@Query("select count(t) from TravelPack t join t.accomodations ba where t.status = true and ba.id != ?1 and ((?2 < ba.startDate and ?3 > ba.endDate) or (?2 between ba.startDate and ba.endDate) or (?3 between ba.startDate and ba.endDate))")
	int getDistinctInRangeAccepted(int id, Date startDate, Date endDate);
}
