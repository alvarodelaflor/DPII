
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FloatBro;

/*
 * CONTROL DE CAMBIOS ProcessionRepository.java
 * 
 * ALVARO 17/02/2019 11:43 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 12:17 AÑADIDA QUERY findProcessionsByBrotherhood
 */

@Repository
public interface FloatBroRepository extends JpaRepository<FloatBro, Integer> {

	@Query("select f from FloatBro f where f.brotherhood.id=?1")
	Collection<FloatBro> findFloatBroByBrotherhood(int brotherhoodId);
}
