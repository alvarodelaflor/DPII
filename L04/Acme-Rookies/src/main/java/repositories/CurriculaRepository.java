
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	@Query("select c from Curricula c where c.rookie.id=?1")
	Collection<Curricula> getCurriculasOfRookie(int rookieId);

	@Query("select c from Curricula c where c.rookie.id=?1 and c.isCopy=0")
	Collection<Curricula> getCurriculasNotCopyOfRookie(int rookieId);

	@Query("select min(cast((select count(c) from Curricula c where c.rookie = h) as float)) from Rookie h")
	Float minCurriculaPerRookie();

	@Query("select max(cast((select count(c) from Curricula c where c.rookie = h) as float)) from Rookie h")
	Float maxCurriculaPerRookie();

	@Query("select avg(cast((select count(c) from Curricula c where c.rookie = h) as float)) from Rookie h")
	Float avgCurriculaPerRookie();

	@Query("select stddev(cast((select count(c) from Curricula c where c.rookie = h) as float)) from Rookie h")
	Float sttdevCurriculaPerRookie();

}
