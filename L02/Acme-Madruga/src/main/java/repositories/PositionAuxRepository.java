
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PositionAux;

/*
 * CONTROL DE CAMBIOS PosRepository.java
 * 
 * ALVARO 18/02/2019 09:23 CREACI�N DE LA CLASE
 */

@Repository
public interface PositionAuxRepository extends JpaRepository<PositionAux, Integer> {

	@Query("select p from PositionAux p where p.status=false and p.parade.id=?1")
	Collection<PositionAux> findAllPositionAuxFreeByParadeId(int paradeId);

	@Query("select p from PositionAux p where p.parade.id=?1")
	Collection<PositionAux> findAllPositionAuxByParadeId(int paradeId);

	@Query("select p from PositionAux p where p.parade.id=?1 and p.row=?2 and p.colum=?3")
	PositionAux findAllPositionAuxByParadeIdRowAndColum(int paradeId, int row, int colum);
}
