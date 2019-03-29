
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Integer> {

	// TODO: Hackers who have made more applications:
	@Query("select a.hacker.name from Application a group by a.id")
	public String findHackerWithMoreApplications();
}
