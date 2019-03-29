
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	// TODO: Companies who have offered more positions:
	@Query("select p.company.commercialName from Position p")
	public String findCompanyWithMorePositions();
}
