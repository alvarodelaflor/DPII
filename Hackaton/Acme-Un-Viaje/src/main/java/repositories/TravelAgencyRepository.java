
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.TravelAgency;

@Repository
public interface TravelAgencyRepository extends JpaRepository<TravelAgency, Integer> {

	@Query("select b from TravelAgency b join b.userAccount bua where bua.id=?1")
	TravelAgency findByUserAccountId(int userAccountId);

	@Query("select distinct t.travelAgency from TravelPack t where t.customer.id=?1")
	Collection<TravelAgency> getTravelAgenciesByCustomerId(int id);

	@Query("select c from TravelAgency c order by(avg(cast((select avg(v.score) from Valoration v where v.travelAgency = c) as float)))")
	Collection<? extends TravelAgency> bestTravelAgency();
}
