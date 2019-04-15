
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	// Application per Hacker:
	@Query("select avg(cast((select count(a) from Application a where a.hacker = h) as float)) from Hacker h")
	public Float avgApplicationPerHacker();

	@Query("select min(cast((select count(a) from Application a where a.hacker = h) as float)) from Hacker h")
	public Float minApplicationPerHacker();

	@Query("select max(cast((select count(a) from Application a where a.hacker = h) as float)) from Hacker h")
	public Float maxApplicationPerHacker();

	@Query("select stddev(cast((select count(a) from Application a where a.hacker = h) as float)) from Hacker h")
	public Float stddevApplicationPerHacker();

	// Hackers who have made more applications:
	@Query("select a.hacker.name from Application a group by a.hacker.id order by count(a.hacker.id) desc")
	public List<String> findHackerWithMoreApplications();

	@Query("select a from Application a where a.hacker.id = ?1")
	public Collection<Application> getApplicationsByHacker(int id);

	@Query("select a from Application a where a.id=?1")
	public Application getApplicationHackerById(int id);

	@Query("select a from Application a where a.hacker.id = ?1")
	public Collection<Application> findHackerApps(int hackerId);

	@Query("select a from Application a where a.status = 'SUBMITTED' and a.problem.company.id = ?1")
	public Collection<Application> getSubmittedApplicationsByLoggedCompany(int companyId);

	@Query("select a from Application a where a.status = 'ACCEPTED' and a.problem.company.id = ?1")
	public Collection<Application> getAcceptedApplicationsByLoggedCompany(int companyId);

	@Query("select a from Application a where a.status = 'REJECTED' and a.problem.company.id = ?1")
	public Collection<Application> getRejectedApplicationsByLoggedCompany(int companyId);
}
