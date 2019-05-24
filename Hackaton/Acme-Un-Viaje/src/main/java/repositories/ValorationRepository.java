
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Valoration;

@Repository
public interface ValorationRepository extends JpaRepository<Valoration, Integer> {

}
