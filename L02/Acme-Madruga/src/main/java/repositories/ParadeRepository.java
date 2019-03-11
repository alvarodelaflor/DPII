
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Parade;

/*
 * CONTROL DE CAMBIOS PosRepository.java
 * 
 * ALVARO 18/02/2019 09:23 CREACIÓN DE LA CLASE
 */

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

}
