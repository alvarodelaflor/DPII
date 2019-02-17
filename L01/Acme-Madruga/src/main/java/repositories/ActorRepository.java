
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Actor;

/*
 * CONTROL DE CAMBIOS ActorRepository.java
 * 
 * ALVARO 17/02/2019 11:41 CREACIÓN DE LA CLASE
 */

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

}
