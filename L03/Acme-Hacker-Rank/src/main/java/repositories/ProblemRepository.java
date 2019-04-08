
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("select count(p) from Problem p join p.position p1 where p1.id=?1 ")
	public int getProblemCount(int positionId);

	// TODO: ESTA ES LA QUERIPONSI
	@Query("select count(p) from Problem p join p.position p1 where p1.id=?1 and p1.cancel=0 and p1.status=1 and p.finalMode=1")
	public int countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse(int positionId);

	// TODO: ESTA ES LA QUERIPONSI
	@Query("select p from Problem p join p.position p1 where p1.id=?1 and p1.cancel=0 and p1.status=1 and p.finalMode=1")
	public Collection<Problem> allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(int positionId);


	@Query("select distinct p from Problem p join p.position pp where pp.id=?1")
	Collection<Problem> getProblemsByPosition(int positionId);

	@Query("select p from Problem p where p.company.id =?1")
	Collection<Problem> getProblemsByCompany(int id);

}