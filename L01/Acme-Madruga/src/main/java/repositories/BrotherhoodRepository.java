
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;

/*
 * CONTROL DE CAMBIOS BrotherhoodRepository.java
 * 
 * ALVARO 17/02/2019 11:42 CREACI�N DE LA CLASE
 * HIPONA 21/02/2019 18:06 Buscar brotherhoods de un member
 */

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select b from Brotherhood b join b.userAccount bua where bua.id=?1")
	Brotherhood findByUserAccountId(int userAccountId);

	@Query("select b from Brotherhood b where b.area.id=?1")
	Collection<Brotherhood> findByAreaId(int areaId);

	// HIPONA 25/02/19 10:05
	@Query("select b from Brotherhood b join b.enrolleds e where e.member.id=?1 and e.state=true and e.dropMoment=null")
	Collection<Brotherhood> findActiveFromMember(int member);

	// ALVARO 01/03/19 10:28
	@Query("select count(b) from Brotherhood b join b.enrolleds e where e.member.id=?1 and e.state=true and e.dropMoment=null and b.id=?2")
	int isActiveFromMemberAndBrotherhood(int member, int brotherhood);

	// HIPONA 25/02/19 10:05
	@Query("select b from Brotherhood b join b.enrolleds e where e.member.id=?1 and e.state=true and e.dropMoment!=null group by b")
	Collection<Brotherhood> findInactiveFromMember(int member);
	@Query("select b from Brotherhood b join b.enrolleds e where e.member.id=?1 and e.state=true")
	Collection<Brotherhood> findFromMember(int member);

	//12.3.2 CARMEN
	@Query("select b from Brotherhood b where cast((select count(e) from Enrolled e where e.brotherhood = b and e.state = 1 and e.dropMoment = null) as float) = (select max(cast((select count(e) from Enrolled e where e.brotherhood = b and e.state = 1 and e.dropMoment = null) as float)) from Brotherhood b)")
	Brotherhood brotherhoodMaxRow();

	//12.3.3 CARMEN
	@Query("select b from Brotherhood b where cast((select count(e) from Enrolled e where e.brotherhood = b and e.state = 1 and e.dropMoment = null) as float) = (select min(cast((select count(e) from Enrolled e where e.brotherhood = b and e.state = 1 and e.dropMoment = null) as float)) from Brotherhood b)")
	Brotherhood brotherhoodMinRow();

	//12.3.1 CARMEN
	@Query("select count(b) from Brotherhood b")
	Integer numberOfBrotherhood();

	//22.2.1 CARMEN
	@Query("select min(cast((select count(b) from Brotherhood b where b.area = a) as float)) from Area a")
	Float minBrotherhoodPerArea();

	//22.2.1 CARMEN
	@Query("select max(cast((select count(b) from Brotherhood b where b.area = a) as float)) from Area a")
	Float maxBrotherhoodPerArea();

	//22.2.1 CARMEN
	@Query("select avg(cast((select count(b) from Brotherhood b where b.area = a) as float)) from Area a")
	Float avgBrotherhoodPerArea();

	//22.2.1 CARMEN
	@Query("select stddev(cast((select count(b) from Brotherhood b where b.area = a) as float)) from Area a")
	Float stddevBrotherhoodPerArea();

	//22.2.1 CARMEN
	@Query("select a.name,(select count(b) from Brotherhood b where b.area = a) from Area a")
	Collection<Object[]> countBrotherhoodPerArea();

	//22.2.1 CARMEN
	@Query("select (cast((select count(b) from Brotherhood b where b.area = a) as float)/(select count(a1) from Area a1)) from Area a")
	Float ratioBrotherhoodPerArea();

}
