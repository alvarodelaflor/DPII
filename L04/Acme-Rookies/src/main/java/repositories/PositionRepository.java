
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	@Query("select p.title from Position p where p.salary = (select max(p1.salary) from Position p1)")
	public List<String> bestPositon();

	@Query("select p.title from Position p where p.salary = (select min(p1.salary) from Position p1)")
	public List<String> worstPositon();

	// Company More Positions:

	@Query("select p.company.commercialName from Position p group by p.company.id order by count(p.company.id) desc")
	public List<String> findCompanyWithMorePositions();

	/////////////////////////////////////////////////////////////////////////////////

	@Query("select p from Position p where p.company.id =?1 and p.status=true and p.cancel=false")
	Collection<Position> findAllPositionStatusTrueCancelFalseByCompany(int companyId);

	@Query("select p from Position p where p.status=true")
	Collection<Position> findAllPositionWithStatusTrue();
	
	@Query("select p from Position p where p.status=true and p.cancel=false")
	Collection<Position> findAllPositionWithStatusTrueCancelFalse();

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

	@Query("select count(p) from Position p where p.ticker = ?1")
	public int countByTicker(String ticker);

	@Modifying
	@Query("update Application a set status='REJECTED' where (a.status='PENDING' or a.status='SUBMITTED') and a.problem.id=?1 and a.position.id=?2")
	public void rejectAllApplications(int problemId, int positionId);

	/**
	 * 
	 * Return a collection of all {@link Position} in database that is valid for a curricula.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link Position}>
	 */
	@Query("select p from Application a join a.position p where p.status=1 and a.status='ACCEPTED' and a.rookie.id = ?1")
	Collection<Position> findValidPositionToCurriculaByRookieId(int rookieId);
	
	/**
	 * 
	 * Return a collection of all {@link Position} in final mode no cancel that it has not
	 * audit asociated
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link Position}>
	 */
	@Query("select p from Audit a right join a.position p where p.status=true and p.cancel=false and a.status is null")
	Collection<Position> findAllPositionWithStatusTrueNotCancelNotAudit();
	
	/**
	 * 
	 * Return a collection of all {@link Position} by auditor ID.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link Position}>
	 */
	@Query("select p from Audit a join a.position p where p.status=true and p.cancel=false and a.auditor.id=?1")
	Collection<Position> findAllPositionByAuditor(int auditorId);

	/* The average, the minimum, the maximum, and the standard deviation of the
	 * audit score of the positions stored in the system
	 */
	@Query("select avg(a.score), min(a.score), max(a.score), stddev(a.score) from Audit a where a.status = true")
	public List<Object[]> avgMinMaxStddevPositionAuditScore();


}
