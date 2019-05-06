
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	// Application per Rookie:
	@Query("select avg(cast((select count(a) from Application a where a.rookie = h) as float)) from Rookie h")
	public Float avgApplicationPerRookie();

	@Query("select min(cast((select count(a) from Application a where a.rookie = h) as float)) from Rookie h")
	public Float minApplicationPerRookie();

	@Query("select max(cast((select count(a) from Application a where a.rookie = h) as float)) from Rookie h")
	public Float maxApplicationPerRookie();

	@Query("select stddev(cast((select count(a) from Application a where a.rookie = h) as float)) from Rookie h")
	public Float stddevApplicationPerRookie();

	// Rookies who have made more applications:
	@Query("select a.rookie.name from Application a group by a.rookie.id order by count(a.rookie.id) desc")
	public List<String> findRookieWithMoreApplications();

	@Query("select a from Application a where a.rookie.id = ?1")
	public Collection<Application> getApplicationsByRookie(int id);

	@Query("select a from Application a where a.id=?1")
	public Application getApplicationRookieById(int id);

	@Query("select a from Application a where a.rookie.id = ?1")
	public Collection<Application> findRookieApps(int rookieId);

	@Query("select a from Application a where a.status = 'SUBMITTED' and a.problem.company.id = ?1")
	public Collection<Application> getSubmittedApplicationsByLoggedCompany(int companyId);

	@Query("select a from Application a where a.status = 'ACCEPTED' and a.problem.company.id = ?1")
	public Collection<Application> getAcceptedApplicationsByLoggedCompany(int companyId);

	@Query("select a from Application a where a.status = 'REJECTED' and a.problem.company.id = ?1")
	public Collection<Application> getRejectedApplicationsByLoggedCompany(int companyId);
}
