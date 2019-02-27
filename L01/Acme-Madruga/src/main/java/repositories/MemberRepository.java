
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Member;

/*
 * CONTROL DE CAMBIOS MemberRepository.java
 * 
 * ALVARO 17/02/2019 19:56 CREACI�N DE LA CLASE
 * c
 */

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	@Query("select m from Member m join m.userAccount mua where mua.id=?1")
	Member findByUserAccountId(int userAccountId);

	@Query("select count(m) from Member m join m.enrolleds e where m.id=?1 and e.brotherhood.id=?2 and e.dropMoment=null and e.state=true")
	int isBrotherhoodActiveMember(int memberId, int brotherHoodId);

	@Query("select count(r) from Request r where r.member.id=?1 and r.status=true")
	int membersOfProcession(int memberId);

	@Query("select count(e) from Enrolled e where e.member.id=?1 and e.state=true and e.dropMoment is null and e.brotherhood.id=?2")
	int membersOfBrotherhood(int memberId, int brotherhoodId);
	@Query("select m from Member m join m.enrolleds e where e.brotherhood.id=?1")
	Collection<Member> brotherhoodAllMember(int brotherHoodId);

	//12.3.7 --> 
	@Query("select m from Member m where 0.1*(select count(e) from Enrolled e where e.member=m.id) < (select count(e1) from Enrolled  e1 where e1.state=true and e1.member = m.id))")
	Collection<Member> memberAccept();

}
