
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select a from Audit a where a.status=?1 and a.auditor.id=?2")
	Collection<Audit> getAuditByStatusAndAuditorId(Boolean status, int auditorId);
}
