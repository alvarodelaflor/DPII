
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Accomodation;
import domain.FinderAccomodation;

@Repository
public interface FinderAccomodationRepository extends JpaRepository<FinderAccomodation, Integer> {

	@Query("select a from Accomodation a where (a.description like %?1% or a.address like %?1% or a.place like %?1%) and (a.pricePerNight <= ?2) and a.maxPeople >= ?3 and (a.address like %?4% or a.place like %?4%)")
	Collection<Accomodation> findByFilter(final String keyword, final Double price, final Integer people, final String address);

	@Query("select m.finder from TravelAgency m where m.id=?1")
	FinderAccomodation getByTravelAgency(int id);

	@Query("select f from FinderAccomodation f join f.accomodations a where a.id=?1")
	Collection<FinderAccomodation> getFindersWithAccomodation(int id);
}
