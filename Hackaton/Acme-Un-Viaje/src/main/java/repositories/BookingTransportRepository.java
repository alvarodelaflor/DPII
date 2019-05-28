
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.BookingTransport;

@Repository
public interface BookingTransportRepository extends JpaRepository<BookingTransport, Integer> {

}
