
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Transport;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {

	@Query("select t from Transport t where t.transporter.id = ?1")
	Collection<Transport> getTransporterTransports(int transporterId);
}
