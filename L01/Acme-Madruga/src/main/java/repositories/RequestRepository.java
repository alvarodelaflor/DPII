
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

/*
 * CONTROL DE CAMBIOS PosRepository.java
 * 
 * ALVARO 18/02/2019 09:23 CREACI�N DE LA CLASE
 */

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select e from Request e where e.positionAux.procession.id=?1 and e.status=?2")
	Collection<Request> findAllByProcession(int processionId, Boolean status);

	@Query("select e from Request e where e.positionAux.procession.id=?1 and e.status is null")
	Collection<Request> findAllByProcessionPending(int processionId);

	@Query("select r from Request r where r.member.id = ?1 order by r.status desc")
	Collection<Request> getMemberRequests(int idMember);

	// 12.3.4 --> 
	//select ((select count(e) from Request e where count(e.positionAux.) > 0)/count(e))*1.0 from Request e;

}
