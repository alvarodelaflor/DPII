
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

/*
 * CONTROL DE CAMBIOS ProcessionRepository.java
 * 
 * ALVARO 17/02/2019 11:43 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 12:17 AÑADIDA QUERY findProcessionsByBrotherhood
 */

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	@Query("select p from Procession p where p.brotherhood.id=?1")
	Collection<Procession> findProcessionsByBrotherhood(int brotherhoodId);

	@Query("select p from Procession p where p.floatBro.id=?1")
	Collection<Procession> findProcessionsByFloat(int floatId);
}
