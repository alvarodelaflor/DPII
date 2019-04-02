
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("select count(p) from Problem p where p.position.id=?1")
	int getProblemCount(final int positionId);

	// TODO: ESTA ES LA QUERIPONSI
	@Query("select count(a.problem) from Application a where a.position.status=true and a.position.cancel=false and a.problem.finalMode=true and a.position.id=?1 and a.problem.position.id=?1 group by a.problem having min(a.status)!='ACCEPTED'")
	public int countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse(int positionId);

}
