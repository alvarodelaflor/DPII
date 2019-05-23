
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FinderRequest;
import domain.Request;

@Repository
public interface FinderRequestRepository extends JpaRepository<FinderRequest, Integer> {

	@Query("select a from Request a where (a.destination like %?1%) and (a.maxPrice >= ?2) and a.startDate>= ?3")
	Collection<Request> findByFilter(final String place, final Double price, final Date startMoment);

	@Query("select m.finderRequest from TravelAgency m where m.id=?1")
	FinderRequest getByTravelAgency(int id);

	@Query("select f from FinderRequest f join f.requests a where a.id=?1")
	Collection<FinderRequest> getFindersWithRequest(int id);
}
