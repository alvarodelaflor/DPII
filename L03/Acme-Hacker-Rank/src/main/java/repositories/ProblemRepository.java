
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("select distinct p from Problem p join p.position pp where pp.id=?1")
	Collection<Problem> getProblemsByPosition(int positionId);

	@Query("select p from Problem p where p.company.id =?1")
	Collection<Problem> getProblemsByCompany(int id);

}
