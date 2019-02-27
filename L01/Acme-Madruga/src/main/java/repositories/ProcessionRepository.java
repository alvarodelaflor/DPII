
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

/*
 * CONTROL DE CAMBIOS ProcessionRepository.java
 * 
 * ALVARO 17/02/2019 11:43 CREACIÓN DE LA CLASE
 * ALVARO 17/02/2019 12:17 AÑADIDA QUERY findProcessionsByBrotherhood
 */

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	@Query("select p from Procession p where p.brotherhood.id=?1")
	Collection<Procession> findProcessionsByBrotherhood(int brotherhoodId);

	//12.3.5 --> The processions that are going to be organised in 30 days or less. 
	@Query("select p from Procession p where p.moment between ?1 and ?2")
	Collection<Procession> findAllWithCreationDateTimeBeforeI(Date dateNow, Date dateFinish);

}
