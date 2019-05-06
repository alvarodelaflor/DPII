
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	@Query("select m from Provider m join m.userAccount mua where mua.id=?1")
	Provider findByUserAccountId(int userAccountId);

	@Query("select p,(select 1.1*count(s) from Sponsorship s where s.provider = p) from Provider p")
	List<Object[]> sponsorshipProvider();

	//	@Query("select p,(select count(i) from Item i where i.provider = p) from Provider p ")
	//	public Float ProvidersPerNumberItem();

}
