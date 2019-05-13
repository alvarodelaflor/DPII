
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Cleaner;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner, Integer> {

	@Query("select b from Cleaner b join b.userAccount bua where bua.id=?1")
	Cleaner findByUserAccountId(int userAccountId);
}
