
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Float;

/*
 * CONTROL DE CAMBIOS ParadeRepository.java
 * 
 * ALVARO 17/02/2019 11:43 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 12:17 AÑADIDA QUERY findParadesByBrotherhood
 */

@Repository
public interface FloatRepository extends JpaRepository<Float, Integer> {

	@Query("select f from domain.Float f where f.brotherhood.id=?1")
	Collection<domain.Float> findFloatByBrotherhood(int brotherhoodId);
}
