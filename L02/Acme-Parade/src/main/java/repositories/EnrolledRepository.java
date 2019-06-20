
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

	@Query("select e from Enrolled e where e.brotherhood.id=?1 and e.state=?2 and e.dropMoment is null")
	Collection<Enrolled> findAllByBrotherhoodLogged(int brotherhoodId, Boolean state);

	@Query("select e from Enrolled e where e.brotherhood.id=?1 and e.state is null")
	Collection<Enrolled> findAllByBrotherhoodLoggedPending(int brotherhoodId);

	@Query("select e from Member m join m.enrolleds e where m.id=?1 and e.brotherhood.id=?2 and e.dropMoment=null and e.state=true")
	Enrolled getBrotherhoodActiveEnrollment(int memberId, int brotherHoodId);

	@Query("select e from Member m join m.enrolleds e where m.id=?1 and e.brotherhood.id=?2 and e.dropMoment is null and e.state is null")
	Enrolled getBrotherhoodPendingEnrollment(int memberId, int brotherHoodId);

	@Query("select e from Enrolled e where e.brotherhood.id=?1 and e.state=true and e.dropMoment is not null")
	Collection<Enrolled> getDropOutMember(int brotherHoodId);

	@Query("select e from Enrolled e where e.position.id=?1")
	Collection<Enrolled> findAllByPositionId(int positionId);

	@Query("select e from Enrolled e where e.position.id=?1 and e.dropMoment is null")
	Collection<Enrolled> findAllByPositionUsedId(int positionId);
}
