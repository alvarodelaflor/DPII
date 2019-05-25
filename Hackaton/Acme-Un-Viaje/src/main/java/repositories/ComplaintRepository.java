
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select c from Complaint c where c.customer.id = ?1 order by c.moment")
	Collection<Complaint> getLoggedCustomerComplaints(int id);

	@Query("select c from Complaint c where c.customer.id = ?1 and c.id = ?2")
	Complaint getLoggedCustomerComplaint(int id, Integer complaintId);

}
