
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select max(cast((select count(sp) from Sponsor s join s.sponsorships sp where sp.active=true) as float))from Sponsor so")
	Integer maxActiveSponsorshipPerSponsor();

	@Query("select min(cast((select count(sp) from Sponsor s join s.sponsorships sp where sp.active=true) as float))from Sponsor so")
	Integer minActiveSponsorshipPerSponsor();

	@Query("select avg(cast((select count(sp) from Sponsor s join s.sponsorships sp where sp.active=true) as float))from Sponsor so")
	Integer avgActiveSponsorshipPerSponsor();

	@Query("select stddev(cast((select count(sp) from Sponsor s join s.sponsorships sp where sp.active=true) as float))from Sponsor so")
	Integer stddevActiveSponsorshipPerSponsor();

	//select count(sp) from Sponsor s join s.sponsorships sp where sp.active=true;

	//select max(cast((select count(sp) from Sponsor s join s.sponsorships sp where sp.active=true) as float))from Sponsor so;

}
