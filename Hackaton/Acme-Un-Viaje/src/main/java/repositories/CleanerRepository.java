
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Cleaner;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner, Integer> {

}
