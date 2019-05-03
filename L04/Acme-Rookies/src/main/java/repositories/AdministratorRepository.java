
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	public Administrator findOneByUserAccount(int id);
	
	@Query("select avg(a.score), p.company from Audit a join a.position p where a.status=true group by p.company")
	Collection<Object[]> getCompaniesScores();

}
