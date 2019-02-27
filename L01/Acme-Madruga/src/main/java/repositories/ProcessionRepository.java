
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
 * ALVARO 17/02/2019 11:43 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 12:17 A�ADIDA QUERY findProcessionsByBrotherhood
 */

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	@Query("select p from Procession p where p.brotherhood.id=?1")
	Collection<Procession> findProcessionsByBrotherhood(int brotherhoodId);

	@Query("select p from Procession p where p.floatBro.id=?1")
	Collection<Procession> findProcessionsByFloat(int floatId);

	@Query("select p from Procession p where p.ticker=?1")
	Collection<Procession> findProcessionsByTicker(String ticker);

	//12.3.5 --> The processions that are going to be organised in 30 days or less. 
	@Query("select p from Procession p where p.moment between ?1 and ?2")
	Collection<Procession> findAllWithCreationDateTimeBeforeI(Date dateNow, Date dateFinish);

}
