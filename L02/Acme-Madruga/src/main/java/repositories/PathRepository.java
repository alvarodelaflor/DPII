
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Path;

@Repository
public interface PathRepository extends JpaRepository<Path, Integer> {

	@Query("select p from Path p where p.parade.id=?1")
	Path findFromParade(int paradeId);

	@Query("select p from Path p where p.origin.id=?1")
	Path findFromOriginSegment(int segmentId);
}
