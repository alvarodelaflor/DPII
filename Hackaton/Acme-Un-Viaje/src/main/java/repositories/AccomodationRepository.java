
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Accomodation;

@Repository
public interface AccomodationRepository extends JpaRepository<Accomodation, Integer> {

}
