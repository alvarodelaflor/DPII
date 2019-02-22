
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;

/*
 * CONTROL DE CAMBIOS BrotherhoodRepository.java
 * 
 * ALVARO 17/02/2019 11:42 CREACI�N DE LA CLASE
 * HIPONA 21/02/2019 18:06 Buscar brotherhoods de un member
 */

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select b from Brotherhood b join b.userAccount bua where bua.id=?1")
	Brotherhood findByUserAccountId(int userAccountId);

	@Query("select b from Brotherhood b join b.enrolleds e where e.member.id=?1 and e.state=true")
	Collection<Brotherhood> findFromMember(int member);
}
