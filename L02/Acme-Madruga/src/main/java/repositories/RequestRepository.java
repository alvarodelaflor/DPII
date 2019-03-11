
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

	@Query("select e from Request e where e.positionAux.parade.id=?1")
	Collection<Request> findAllByParadeByParade(int paradeId);

	@Query("select e from Request e where e.positionAux.parade.id=?1 and e.status=?2")
	Collection<Request> findAllByParade(int paradeId, Boolean status);

	@Query("select e from Request e where e.positionAux.parade.id=?1 and e.status is null")
	Collection<Request> findAllByParadePending(int paradeId);

	@Query("select r from Request r where r.member.id = ?1 order by r.status desc")
	Collection<Request> getMemberRequests(int idMember);

	@Query("select e from Request e where e.member.id=?1 and e.status is null")
	Collection<Request> findAllByMemberAndStatusPending(int memberId);

	@Query("select e from Request e where e.member.id=?1 and e.status is true")
	Collection<Request> findAllByMemberAndStatusAccepted(int memberId);

	@Query("select r from Request r where r.positionAux.parade.id=?1 and r.member.id=?2 and r.status is null")
	Collection<Request> findAllByMemberParadePending(int idParade, int memberId);

	@Query("select r from Request r where r.positionAux.parade.id=?1 and r.member.id=?2 and r.status=true")
	Collection<Request> findAllByMemberParadeAccepted(int idParade, int memberId);

	// 12.3.4 --> 
	@Query("select (count(r1)/(select count(p) from Parade p))*1.0 from Request r1 where r1.status = true")
	Double getRatioRequestParadeStatusTrue();
	@Query("select (count(r1)/(select count(p) from Parade p))*1.0 from Request r1 where r1.status = false")
	Double getRatioRequestParadeStatusFalse();
	@Query("select (count(r1)/(select count(p) from Parade p))*1.0 from Request r1 where r1.status = null")
	Double getRatioRequestParadeStatusNull();

	// 12.3.6 -->
	@Query("select (count(r1)/(select count(r) from Request r))*1.0 from Request r1 where r1.status = true")
	Double getRatioRequestStatusTrue();
	@Query("select (count(r1)/(select count(r) from Request r))*1.0 from Request r1 where r1.status = false")
	Double getRatioRequestStatusFalse();
	@Query("select (count(r1)/(select count(r) from Request r))*1.0 from Request r1 where r1.status = null")
	Double getRatioRequestStatusNull();
}
