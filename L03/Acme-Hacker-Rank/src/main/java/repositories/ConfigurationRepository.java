
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Configuration;

public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

}
