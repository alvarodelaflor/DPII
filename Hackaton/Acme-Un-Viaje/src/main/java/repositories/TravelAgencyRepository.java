
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.TravelAgency;

@Repository
public interface TravelAgencyRepository extends JpaRepository<TravelAgency, Integer> {

	@Query("select b from TravelAgency b join b.userAccount bua where bua.id=?1")
	TravelAgency findByUserAccountId(int userAccountId);
}
