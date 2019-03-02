
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

/*
 * CONTROL DE CAMBIOS PosRepository.java
 * ALVARO 18/02/2019 09:23 CREACIÓN DE LA CLASE
 */

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.nameEs=?1 and p.nameEn=?2")
	Position findByNames(String nameEs, String nameEn);
}
