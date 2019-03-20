
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parade;

/*
 * CONTROL DE CAMBIOS ParadeRepository.java
 * 
 * ALVARO 17/02/2019 11:43 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 12:17 A�ADIDA QUERY findParadesByBrotherhood
 */

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	@Query("select p from Parade p where p.brotherhood.id=?1 order by p.status")
	Collection<Parade> findParadesByBrotherhood(int brotherhoodId);

	@Query("select p from Parade p where p.floatt.id=?1")
	Collection<Parade> findParadesByFloat(int floatId);

	@Query("select p from Parade p where p.ticker=?1")
	Collection<Parade> findParadesByTicker(String ticker);

	@Query("select p from Parade p where p.brotherhood.id=?1 and p.isFinal=true")
	Collection<Parade> findParadesBrotherhoodFinal(int brotherhoodId);

	//12.3.5 --> The parades that are going to be organised in 30 days or less. 
	@Query("select p from Parade p where p.moment between ?1 and ?2")
	Collection<Parade> findAllWithCreationDateTimeBeforeI(Date dateNow, Date dateFinish);

	//12.3.6 --> 
	@Query("select p,max(p.maxRow * p.maxColum) from Parade p")
	Parade minParade();

	//12.3.6 --> 
	@Query("select p,max(p.maxRow * p.maxColum) from Parade p")
	Parade maxParade();

	//12.3.6 --> 
	@Query("select max(p.maxRow * p.maxColum) from Parade p")
	Integer minParadeN();

	//12.3.6 --> 
	@Query("select min(p.maxRow * p.maxColum) from Parade p")
	Integer maxParadeN();

	// Requisito 8
	@Query("select distinct(cast((select count(p) from Parade p where p.isFinal= 1 and p.status='SUBMITTED') as float)/(select count(p1) from Parade p1)) from Parade p2")
	Float ratioFinalSUBMITTED();

	// Requisito 8
	@Query("select distinct(cast((select count(p) from Parade p where p.isFinal= 1 and p.status='ACCEPTED') as float)/(select count(p1) from Parade p1)) from Parade p2")
	Float ratioFinalACCEPTED();

	// Requisito 8
	@Query("select distinct(cast((select count(p) from Parade p where p.isFinal= 1 and p.status='REJECTED') as float)/(select count(p1) from Parade p1)) from Parade p2")
	Float ratioFinalREJECTED();

	// Requisito 8
	@Query("select distinct(cast((select count(p) from Parade p where p.isFinal= 0) as float)/(select count(p1) from Parade p1 where p1.isFinal= 1)) from Parade p2")
	Float ratioNoFinalNULL();

}
