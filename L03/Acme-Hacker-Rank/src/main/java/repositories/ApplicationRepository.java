
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	// Application per Hacker:
	@Query("select avg(cast((select count(a) from Application a where a.hacker = h) as float)) from Hacker a")
	public Float avgApplicationPerHacker();

	@Query("select min(cast((select count(a) from Application a where a.hacker = h) as float)) from Hacker a")
	public Float minApplicationPerHacker();

	@Query("select max(cast((select count(a) from Application a where a.hacker = h) as float)) from Hacker a")
	public Float maxApplicationPerHacker();

	@Query("select stddev(cast((select count(a) from Application a where a.hacker = h) as float)) from Hacker a")
	public Float stddevApplicationPerHacker();

	@Query("select a from Application a where a.hacker.id = ?1")
	public Collection<Application> findHackerApps(int hackerId);
}
