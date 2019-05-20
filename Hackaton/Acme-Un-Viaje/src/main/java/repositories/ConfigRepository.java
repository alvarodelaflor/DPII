
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Config;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {

}
