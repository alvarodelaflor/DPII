
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import auxiliar.PositionAux;

/*
 * CONTROL DE CAMBIOS PosRepository.java
 * 
 * ALVARO 18/02/2019 09:23 CREACIÓN DE LA CLASE
 */

@Repository
public interface PositionAuxRepository extends JpaRepository<PositionAux, Integer> {

	@Query("select p from PositionAux p where p.status=false and p.procession.id=?1")
	Collection<PositionAux> findAllPositionAuxFreeByProcessionId(int processionId);

	@Query("select p from PositionAux p where p.procession.id=?1")
	Collection<PositionAux> findAllPositionAuxByProcessionId(int processionId);
}
