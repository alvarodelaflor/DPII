
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

	@Query("select x from Quolet x where x.audit.id = ?1 and x.audit.position.company.id = ?2")
	Collection<Quolet> getLoggedQuolets(int id, int i);

	@Query("select x from Quolet x where x.id = ?1 and x.audit.position.company.id = ?2")
	Quolet getLoggedQuolet(int quoletId, int companyId);

	@Query("select count(x) from Quolet x where x.ticker = ?1")
	int findByTicker(String ticker);
	
	@Query("select x from Quolet x where x.audit.id = ?1 and x.draftMode=false")
	Collection<Quolet> getQuoletsNoDraftMode(int auditorId);

}
