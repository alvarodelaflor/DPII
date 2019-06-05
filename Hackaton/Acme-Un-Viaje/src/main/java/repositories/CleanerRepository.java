
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Cleaner;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner, Integer> {

	@Query("select b from Cleaner b join b.userAccount bua where bua.id=?1")
	Cleaner findByUserAccountId(int userAccountId);

	@Query("select j.cleaner from JobApplication j where j.host.id=?1 and j.status=true and j.dropMoment=null")
	Collection<Cleaner> findByHostId(int hostId);

	@Query("select c.name,c.surname from Cleaner c ")
	List<Object[]> getCleanersName();

	@Query("select c from Cleaner c where c.name=?1 and c.surname=?2")
	Cleaner findByNameSurname(String name, String surname);

	@Query("select v.cleaner from Valoration v group by v.host order by avg(v.score) desc")
	Collection<Cleaner> bestCleaner();

}
