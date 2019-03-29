
package repositories;

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
	@Query("select p.title from Postion p where p.salary = max(p.salary)")
	public String bestPositon();

	@Query("select p.title from Postion p where p.salary = min(p.salary)")
	public String worstPositon();

}
