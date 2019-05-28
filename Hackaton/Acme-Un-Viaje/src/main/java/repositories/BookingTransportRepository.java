
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BookingTransport;

@Repository
public interface BookingTransportRepository extends JpaRepository<BookingTransport, Integer> {

	@Query("select b from BookingTransport b where b.transport.id = ?1")
	Collection<BookingTransport> getTransportBookings(int id);

}
