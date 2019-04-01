
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	// Positions per Company:
	@Query("select avg(cast((select count(p) from Position p where p.company = c) as float)) from Company c")
	public Float avgPositionPerCompany();

	@Query("select min(cast((select count(p) from Position p where p.company = c) as float)) from Company c")
	public Float minPositionPerCompany();

	@Query("select max(cast((select count(p) from Position p where p.company = c) as float)) from Company c")
	public Float maxPositionPerCompany();

	@Query("select stddev(cast((select count(p) from Position p where p.company = c) as float)) from Company c")
	public Float stddevPositionPerCompany();

	// Salaries:
	@Query("select avg(p.salary) from Position p")
	public Double avgSalaryPerPosition();

	@Query("select min(p.salary) from Position p")
	public Double minSalaryPerPosition();

	@Query("select max(p.salary) from Position p")
	public Double maxSalaryPerPosition();

	@Query("select stddev(p.salary) from Position p")
	public Double stddevSalaryPerPosition();

	// Best and Worst Postions (salary):
	@Query("select p.title from Position p where p.salary = max(p.salary)")
	public String bestPositon();

	@Query("select p.title from Position p where p.salary = min(p.salary)")
	public String worstPositon();

	@Query("select p from Position p where p.company.id =?1 and p.status=true")
	Collection<Position> findAllPositionStatusTrueByCompany(int companyId);

	@Query("select p from Position p where p.status=true")
	Collection<Position> findAllPositionWithStatusTrue();

	/////////////////////////////////////////////////////////////////////////////////

	@Query("select p from Position p where p.description like %?1% and p.status=1")
	Collection<Position> findWithDescription(String description);

	@Query("select p from Position p where p.profile like %?1% and p.status=1")
	Collection<Position> findWitheProfile(String profile);

	@Query("select p from Position p where p.skills like %?1% and p.status=1")
	Collection<Position> findWithSkills(String skills);

	@Query("select p from Position p where p.company.name like %?1% and p.status=1")
	Collection<Position> findWithCompanyName(String companyName);

	@Query("select p from Position p where p.techs like %?1% and p.status=1")
	Collection<Position> findWithTechs(String techs);

	@Query("select p from Position p where p.title like %?1% and p.status=1")
	Collection<Position> findWithTitle(String title);

	@Query("select p from Position p where p.company.id = ?1")
	Collection<Position> findAllPositionsByCompany(int companyId);

}
