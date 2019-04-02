package repositories;

import java.util.Collection;

/*
 * PositionDataRepository.java
 * 
 * author: Álvaro de la Flor Bonilla GitHub: alvar017
 * 
 * CONTROL:
 * 30/03/2019 14:34 Creation
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PositionData;

@Repository
public interface PositionDataRepository extends JpaRepository<PositionData, Integer> {

	@Query("select pd from PositionData pd join pd.curricula pdc where pdc.id=?1")
	Collection<PositionData> getPositionDataFromCurricula(int curriculaId);
	
	@Query("select pd from PositionData pd join pd.curricula pdc where pdc.id=?1 and pd.isCopy=0")
	Collection<PositionData> getPositionDataNotCopyFromCurricula(int curriculaId);
}
