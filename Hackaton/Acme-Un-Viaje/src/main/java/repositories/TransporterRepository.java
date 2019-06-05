
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Transporter;

@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Integer> {

	@Query("select b from Transporter b join b.userAccount bua where bua.id=?1")
	Transporter findByUserAccountId(int userAccountId);

	@Query("select v.transporter from Valoration v group by v.host order by avg(v.score) desc")
	Collection<Transporter> bestTransporter();

}
