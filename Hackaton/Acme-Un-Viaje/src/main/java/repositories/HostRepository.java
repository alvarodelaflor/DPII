
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Host;

@Repository
public interface HostRepository extends JpaRepository<Host, Integer> {

	@Query("select b from Host b join b.userAccount bua where bua.id=?1")
	Host findByUserAccountId(int userAccountId);
}
