
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select s from Sponsorship s where s.provider.id = ?1")
	Collection<Sponsorship> findAllByProviderId(int id);

	@Query("select s from Sponsorship s where s.position.id=?1")
	Collection<Sponsorship> findAllByPositionId(int id);

	@Query("select avg(cast((select count(s) from Sponsorship s where s.provider = p) as float)) from Provider p")
	public Float avgSponsorshipPerProvider();

	@Query("select min(cast((select count(s) from Sponsorship s where s.provider = p) as float)) from Provider p")
	public Float minSponsorshipPerProvider();

	@Query("select max(cast((select count(s) from Sponsorship s where s.provider = p) as float)) from Provider p")
	public Float maxSponsorshipPerProvider();

	@Query("select stddev(cast((select count(s) from Sponsorship s where s.provider = p) as float)) from Provider p")
	public Float stddevSponsorshipPerProvider();

	@Query("select avg(cast((select count(s) from Sponsorship s where s.position = p) as float)) from Position p")
	public Float avgSponsorshipPerPosition();

	@Query("select min(cast((select count(s) from Sponsorship s where s.position = p) as float)) from Position p")
	public Float minSponsorshipPerPosition();

	@Query("select max(cast((select count(s) from Sponsorship s where s.position = p) as float)) from Position p")
	public Float maxSponsorshipPerPosition();

	@Query("select stddev(cast((select count(s) from Sponsorship s where s.position = p) as float)) from Position p")
	public Float stddevSponsorshipPerPosition();
}
