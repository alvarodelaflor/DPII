
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Transport;
import domain.Transporter;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Integer> {

	@Query("select t from Transport t where t.transporter.id = ?1 order by t.date")
	Collection<Transport> getTransporterTransports(int transporterId);

	@Query("select t from Transport t where t.transporter.id = ?1 and t.date > ?2 order by t.date")
	Collection<Transport> getTransporterTransportsFromDate(int transporterId, Date date);

	@Query("select distinct ta.transport.transporter from TravelPack t join t.transports ta where t.customer.id=?1")
	Collection<Transporter> getTransportersByCustomerId(int id);

	@Query("select t from Transporter t join t.userAccount bua where bua.id=?1")
	Transporter findByUserAccountId(int userAccountId);
}
