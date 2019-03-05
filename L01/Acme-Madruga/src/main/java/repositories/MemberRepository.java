
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
	@Query("select m from Member m join m.enrolleds e where e.brotherhood.id=?1 and e.state=true and e.dropMoment=null")
	Collection<Member> brotherhoodAllMember(int brotherHoodId);

	//12.3.7 --> 
	@Query("select m from Member m where (select 1.1*count(e) from Request e where e.member=m.id and e.status=true) > (select count(e1) from Request  e1 where  e1.member = m.id)")
	Collection<Member> memberAccept();

	//12.3.1 -->	
	@Query("select min(cast((select count(e) from Enrolled e where e.brotherhood = b and e.state = true) as float)) from Brotherhood b")
	Float minNumberOfMembersPerBrotherhood();

	@Query("select max(cast((select count(e) from Enrolled e where e.brotherhood = b and e.state = true) as float)) from Brotherhood b")
	Float maxNumberOfMembersPerBrotherhood();

	@Query("select avg(cast((select count(e) from Enrolled e where e.brotherhood = b and e.state = true ) as float)) from Brotherhood b")
	Float avgNumberOfMembersPerBrotherhood();

	@Query("select stddev(cast((select count(e) from Enrolled e where e.brotherhood = b and e.state = true) as float)) from Brotherhood b")
	Float stddevNumberOfMembersPerBrotherhood();

	@Query("select count(e) from Enrolled e join e.member m join e.brotherhood b where e.state=true and e.dropMoment=null group by b order by b asc")
	Collection<Integer> listNumberOfMembersPerBrotherhood();
	@Query("select count(e) from Enrolled e where e.state=true and e.dropMoment=null")
	Integer numberOfMemberAccepted();

}
