
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {

}
