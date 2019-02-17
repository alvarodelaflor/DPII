
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;

/*
 * CONTROL DE CAMBIOS BrotherhoodRepository.java
 * 
 * ALVARO 17/02/2019 11:42 CREACIÓN DE LA CLASE
 */

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select b from Brotherhood b join b.userAccount bua where bua.id=?1")
	Brotherhood findByUserAccountId(int userAccountId);
}
