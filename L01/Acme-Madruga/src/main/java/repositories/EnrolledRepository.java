
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Enrolled;

/*
 * CONTROL DE CAMBIOS EnrolledRepository.java
 * 
 * ALVARO 17/02/2019 20:55 CREACIÓN DE LA CLASE
 */

@Repository
public interface EnrolledRepository extends JpaRepository<Enrolled, Integer> {

	@Query("select e from Enrolled e where e.brotherhood.id=?1 and e.state=?2")
	Collection<Enrolled> findAllByBrotherhoodLogged(int brotherhoodId, Boolean state);

	@Query("select e from Enrolled e where e.brotherhood.id=?1 and e.state is null")
	Collection<Enrolled> findAllByBrotherhoodLoggedPending(int brotherhoodId);
}
