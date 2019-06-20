
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select m from Company m join m.userAccount mua where mua.id=?1")
	Company findByUserAccountId(int userAccountId);

	/*
	 * The average, the minimum, the maximum, and the standard deviation of the
	 * audit score of the companies that are registered in the system
	 */
	@Query("select avg(c.auditScore), min(c.auditScore), max(c.auditScore), stddev(c.auditScore) from Company c")
	public List<Object[]> avgMinMaxStddevCompanyAuditScore();

	@Query("select avg(a.score), p.company from Audit a join a.position p where a.status=true group by p.company order by avg(a.score) desc")
	List<Object[]> getCompaniesScores();

	@Query("select c from Company c where c.auditScore != null order by c.auditScore desc")
	public List<Company> getCompaniesOrderedByAuditScore();

	@Query("select avg(p.salary) from Position p join p.company c where c.id = ?1 and p.status = true")
	public Double avgSalaryOfCompany(int companyId);
}
