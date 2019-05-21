
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Transport;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {

	@Query("select t from Transport t where t.transporter.id = ?1 order by t.date")
	Collection<Transport> getTransporterTransports(int transporterId);

	@Query("select t from Transport t where t.transporter.id = ?1 and t.date > date order by t.date")
	Collection<Transport> getTransporterTransportsFromDate(int transporterId, Date date);
}
