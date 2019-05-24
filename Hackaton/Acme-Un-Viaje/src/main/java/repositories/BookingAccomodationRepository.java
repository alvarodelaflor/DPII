
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BookingAccomodation;

@Repository
public interface BookingAccomodationRepository extends JpaRepository<BookingAccomodation, Integer> {

	@Query("select b from BookingAccomodation b where b.accomodation.id = ?1")
	Collection<BookingAccomodation> getAccomodationBookings(int id);

}
