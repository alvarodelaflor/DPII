
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Position;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p from Position p where (p.title like %?1% or p.description like %?1% or p.profile like %?1% or p.skills like %?1% or p.techs like %?1% ) and (p.salary between ?2 and ?3) and p.deadline >= ?4")
	Collection<Position> findByFilter(final String keyword, final Double minSalary, final Double maxSalary, final Date deadline);

	//CARMEN DASHBOAR (22.2.2 NIVEL B)
	@Query("select min(f.positions.size) from Finder f")
	Float minNumberOfResult();

	//CARMEN DASHBOAR (22.2.2 NIVEL B)
	@Query("select max(f.positions.size) from Finder f")
	Float maxNumberOfResult();

	//CARMEN DASHBOAR (22.2.2 NIVEL B)
	@Query("select avg(f.positions.size) from Finder f")
	Float avgNumberOfResult();

	//CARMEN DASHBOAR (22.2.2 NIVEL B)
	@Query("select stddev(f.positions.size) from Finder f")
	Float stddevNumberOfResult();

	//CARMEN DASHBOAR (22.2.2 NIVEL B)
	@Query("select distinct((select count(f) from Finder f where f.positions.size = 0 ) / (select count(f1) from Finder f1 where f1.positions.size > 0)) from Finder f2")
	Float ratioFinder();

	@Query("select m.finder from Hacker m where m.id=?1")
	Finder getByHacker(int id);

	@Query("select f from Finder f join f.positions p where p.id=?1")
	Collection<Finder> getFindersWithPosition(int id);
}
