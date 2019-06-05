
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Host;

@Repository
public interface HostRepository extends JpaRepository<Host, Integer> {

	@Query("select b from Host b join b.userAccount bua where bua.id=?1")
	Host findByUserAccountId(int userAccountId);

	@Query("select h from JobApplication j join j.host h where j.cleaner.id=?1 and (j.status=null or (j.status=true and j.dropMoment=null))")
	Collection<Host> findHostNotAvailableForCleaner(int cleanerId);

	@Query("select v.host from Valoration v group by v.host order by avg(v.score) desc")
	Collection<Host> bestHost();
}
