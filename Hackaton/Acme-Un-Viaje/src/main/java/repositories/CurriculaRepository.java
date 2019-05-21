
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer> {

	@Query("select c from Curricula c where c.cleaner.id=?1")
	Collection<Curricula> getCurriculasOfCleaner(int cleanerId);

	@Query("select c from Curricula c where c.cleaner.id=?1 and c.isCopy=0")
	Collection<Curricula> getCurriculasNotCopyOfCleaner(int cleanerId);

	// DASHBOARD:

	@Query("select min(cast((select count(c) from Curricula c where c.cleaner = h) as float)) from Cleaner h")
	Float minCurriculaPerCleaner();

	@Query("select max(cast((select count(c) from Curricula c where c.cleaner = h) as float)) from Cleaner h")
	Float maxCurriculaPerCleaner();

	@Query("select avg(cast((select count(c) from Curricula c where c.cleaner = h) as float)) from Cleaner h")
	Float avgCurriculaPerCleaner();

	@Query("select stddev(cast((select count(c) from Curricula c where c.cleaner = h) as float)) from Cleaner h")
	Float stddevCurriculaPerCleaner();

}
