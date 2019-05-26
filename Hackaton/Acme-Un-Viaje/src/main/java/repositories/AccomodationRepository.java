
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Accomodation;
import domain.BookingAccomodation;
import domain.Host;

@Repository
public interface AccomodationRepository extends JpaRepository<Accomodation, Integer> {

	@Query("select w from Accomodation w where w.host.id=?1")
	Collection<Accomodation> getAccomodationsByActor(int id);
	
	@Query("select w.address from Accomodation w where w.host.id=?1")
	Collection<String> getAddressAccomodationsByActor(int id);

	@Query("select w from BookingAccomodation w where w.accomodation.id=?1")
	List<BookingAccomodation> findAllByAccomodationId(int id);
	
	@Query("select a from Accomodation a where a.address=?1")
	Accomodation findByAddress(String address);
	
	

	@Query("select distinct ta.accomodation.host from TravelPack t join t.accomodations ta where t.customer.id=?1")
	Collection<Host> getAccomodationsByCustomerId(int id);

	// @Query("select distinct ta.accomodation from TravelPack t join t.accomodations ta where t.customer.id=?1")
}
