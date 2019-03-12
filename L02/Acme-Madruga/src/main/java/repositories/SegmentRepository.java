
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Segment;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Integer> {

}
