
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Integer> {

	@Query("select h from Hacker h join h.userAccount hua where hua.id=?1")
	Hacker getHackerByUserAccountId(int userAccountId);

	@Query("select m from Hacker m join m.userAccount mua where mua.id=?1")
	Hacker findByUserAccountId(int userAccountId);

	@Query("select ch from Curricula c join c.hacker ch where c.id=?1")
	Hacker getHackerByCurriculaId(int curriculaId);

	//select h.name from Hacker h join h.finder hf where '02' like (concat('%', hf.keyword, '%')) and (10 between hf.minSalary and hf.maxSalary) and '2020-12-10 08:00:00' > hf.deadline;
	//select h from Hacker h join h.finder hf where ('02' like (concat('%', hf.keyword, '%')) or 'ladescripciondesuhermana' like (concat('%', hf.keyword, '%'))) and (?2 between hf.minSalary and hf.maxSalary) and ?3 > hf.deadline
	@Query("select h from Hacker h join h.finder hf where (?1 like (concat('%', hf.keyword, '%')) or ?4 like (concat('%', hf.keyword, '%'))) and (?2 between hf.minSalary and hf.maxSalary) and ?3 > hf.deadline")
	public Collection<Hacker> findHackerRegardlessFinder(String title, Double salary, Date deadline, String description);

}
