
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.History;

/*
 * CONTROL DE CAMBIOS ProcessionRepository.java
 * 
 * ALVARO 17/02/2019 11:43 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 12:17 A�ADIDA QUERY findProcessionsByBrotherhood
 */

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

	@Query("select bh from Brotherhood b join b.history bh where b.id=?1")
	History findHistoryByBrotherhood(int brotherhoodId);

	@Query("select h from History h join h.inceptionRecord hir where hir.id=?1")
	History findHistoryByInceptionRecordId(int inceptionRecordId);

	@Query("select b from Brotherhood b where b.title like %?1%")
	Collection<Brotherhood> findHistoryByBrotherhoodTitle(String title);

	@Query("select b from Brotherhood b where b.name like %?1%")
	Collection<Brotherhood> findHistoryByBrotherhoodName(String name);

	@Query("select b from Brotherhood b where b.title like %?1% and b.name like %?2%")
	Collection<Brotherhood> findHistoryByBrotherhoodTitleAndName(String title, String name);

	//Dashboard:
	@Query("select min(cast((select count(r) from Record r where r.history = h) as float)) from History h")
	Float minRecordPerHistory();

	@Query("select max(cast((select count(r) from Record r where r.history = h) as float)) from History h")
	Float maxRecordPerHistory();

	@Query("select avg(cast((select count(r) from Record r where r.history = h) as float)) from History h")
	Float avgRecordPerHistory();

	@Query("select stddev(cast((select count(r) from Record r where r.history = h) as float)) from History h")
	Float stddevRecordPerHistory();

}
