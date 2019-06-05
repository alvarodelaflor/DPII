
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select b from Customer b join b.userAccount bua where bua.id=?1")
	Customer findByUserAccountId(int userAccountId);

	@Query("select distinct t.customer from TravelPack t join t.transports tt where tt.transport.transporter.id = ?1")
	Collection<Customer> getCustomersByTransporterId(int id);

	@Query("select v.customer from Valoration v group by v.customer order by avg(v.score) desc")
	Collection<Customer> bestCustomer();

	@Query("select distinct t.customer from TravelPack t join t.accomodations ta where ta.accomodation.host.id = ?1")
	Collection<Customer> getCustomersByHostId(int id);

}
// select v.customer, avg(v.score) from Valoration v group by v.customer order by avg(v.score) desc
