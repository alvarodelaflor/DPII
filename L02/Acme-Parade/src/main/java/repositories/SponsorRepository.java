
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

	@Query("select s from Sponsor s join s.userAccount u where u.id = ?1")
	Sponsor getSponsorByUserId(Integer id);

	// LA SIGUIENTE QUERY SIRVE PARA PROBAR EL METODO, NO BORRAR:
	// select s.id, count(ss) as css from Sponsor s join s.sponsorships ss where ss.active = true group by s.id order by css desc; 
	@Query("select s from Sponsor s join s.sponsorships ss where ss.active = true group by s.id order by count(ss) desc")
	List<Sponsor> findAllOrderByActiveSponsorshipSize();

}
