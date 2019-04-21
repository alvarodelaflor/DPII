
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

	@Query("select min(cast((select count(c) from Curricula c where c.hacker = h) as float)) from Hacker h")
	Float minCurriculaPerHacker();

	@Query("select max(cast((select count(c) from Curricula c where c.hacker = h) as float)) from Hacker h")
	Float maxCurriculaPerHacker();

	@Query("select avg(cast((select count(c) from Curricula c where c.hacker = h) as float)) from Hacker h")
	Float avgCurriculaPerHacker();

	@Query("select stddev(cast((select count(c) from Curricula c where c.hacker = h) as float)) from Hacker h")
	Float sttdevCurriculaPerHacker();

}
