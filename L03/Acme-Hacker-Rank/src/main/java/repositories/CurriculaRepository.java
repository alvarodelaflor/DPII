
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	@Query("select c from Curricula c where c.hacker.id=?1")
	Collection<Curricula> getCurriculasOfHacker(int hackerId);

	@Query("select c from Curricula c where c.hacker.id=?1 and c.isCopy=0")
	Collection<Curricula> getCurriculasNotCopyOfHacker(int hackerId);

}
