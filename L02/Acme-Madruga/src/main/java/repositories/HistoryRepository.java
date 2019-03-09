
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.History;

/*
 * CONTROL DE CAMBIOS ProcessionRepository.java
 * 
 * ALVARO 17/02/2019 11:43 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 12:17 AÑADIDA QUERY findProcessionsByBrotherhood
 */

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

	@Query("select bh from Brotherhood b join b.history bh where b.id=?1")
	History findHistoryByBrotherhood(int brotherhoodId);
	
	@Query("select h from History h join h.inceptionRecord hir where hir.id=?1")
	History findHistoryByInceptionRecordId(int inceptionRecordId);
}
