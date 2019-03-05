
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

}
