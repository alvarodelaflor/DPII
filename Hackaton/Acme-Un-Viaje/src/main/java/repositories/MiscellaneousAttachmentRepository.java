package repositories;

import java.util.Collection;

/*
 * EducationalDataRepository.java
 * 
 * author: Álvaro de la Flor Bonilla GitHub: alvar017
 * 
 * CONTROL:
 * 30/03/2019 14:45 Creation
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import domain.MiscellaneousAttachment;

@Repository
public interface MiscellaneousAttachmentRepository extends JpaRepository<MiscellaneousAttachment, Integer> {
	
	@Query("select ma from MiscellaneousAttachment ma join ma.curriculaM mac where mac.id=?1")
	Collection<MiscellaneousAttachment> getMiscellaneousAttachmentFromCurriculaId(int curriculaId);
}
