
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

}
