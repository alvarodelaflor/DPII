
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Valoration;

@Repository
public interface ValorationRepository extends JpaRepository<Valoration, Integer> {

	@Query("select v from Valoration v where v.cleaner.id = ?1")
	Collection<Valoration> findValorationsByCleaner(int id);

	@Query("select v from Valoration v where v.customer.id = ?1")
	Collection<Valoration> findValorationsByCustomer(int id);

	@Query("select v from Valoration v where v.host.id = ?1")
	Collection<Valoration> findValorationsByHost(int id);

}
