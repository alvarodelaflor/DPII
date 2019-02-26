
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

	/*
	 * CONTROL DE CAMBIOS AreaRepository.java
	 * FRAN 20/02/2019 17:31 CREACIÓN DE LA CLASE
	 */

}
