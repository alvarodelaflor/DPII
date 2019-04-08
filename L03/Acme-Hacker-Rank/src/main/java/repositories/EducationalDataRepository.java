
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EducationalData;

@Repository
public interface EducationalDataRepository extends JpaRepository<EducationalData, Integer> {

	@Query("select ed from EducationalData ed join ed.curricula edc where edc.id=?1")
	Collection<EducationalData> getEducationalDataFromCurriculaId(int curriculaId);
}
