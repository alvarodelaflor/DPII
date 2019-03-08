
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Area;
import domain.Finder;
import domain.Procession;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p from Procession p where (p.title like %?1% or p.description like %?1%) and p.moment between ?2 and ?3 and p.isFinal=true")
	Collection<Procession> findByFilterNoArea(final String keyword, final Date minDate, final Date maxDate);

	@Query("select p from Procession p join p.brotherhood b where (p.title like %?1% or p.description like %?1%) and p.moment between ?2 and ?3 and b.area=?4 and p.isFinal=true")
	Collection<Procession> findByFilterWithArea(final String keyword, final Date minDate, final Date maxDate, final Area area);

	//CARMEN DASHBOAR (22.2.2 NIVEL B)
	@Query("select min(f.processions.size) from Finder f")
	Float minNumberOfResult();

	//CARMEN DASHBOAR (22.2.2 NIVEL B)
	@Query("select max(f.processions.size) from Finder f")
	Float maxNumberOfResult();

	//CARMEN DASHBOAR (22.2.2 NIVEL B)
	@Query("select avg(f.processions.size) from Finder f")
	Float avgNumberOfResult();

	//CARMEN DASHBOAR (22.2.2 NIVEL B)
	@Query("select stddev(f.processions.size) from Finder f")
	Float stddevNumberOfResult();

	//CARMEN DASHBOAR (22.2.2 NIVEL B)
	@Query("select distinct((select count(f) from Finder f where f.processions.size = 0 ) / (select count(f1) from Finder f1 where f1.processions.size > 0)) from Finder f2")
	Float ratioFinder();

	@Query("select m.finder from Member m where m.id=?1")
	Finder getByMember(int id);

	@Query("select f from Finder f join f.processions p where p.id=?1")
	Collection<Finder> getFindersWithProcession(int id);
}
