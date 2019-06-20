
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select i from Item i where i.provider.id = ?1")
	List<Item> findProviderItems(int providerId);

	@Query("select avg(cast((select count(i) from Item i where i.provider = p) as float)) from Provider p")
	public Float avgItemPerProvider();

	@Query("select min(cast((select count(i) from Item i where i.provider = p) as float)) from Provider p")
	public Float minItemPerProvider();

	@Query("select max(cast((select count(i) from Item i where i.provider = p) as float)) from Provider p")
	public Float maxItemPerProvider();

	@Query("select stddev(cast((select count(i) from Item i where i.provider = p) as float)) from Provider p")
	public Float stddevItemPerProvider();

}
