
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select c from Complaint c join c.review r where c.customer.id = ?1 order by c.moment desc")
	Collection<Complaint> getLoggedCustomerAssignedComplaints(int id);

	@Query("select c from Complaint c where c.customer.id = ?1 and c.review = null order by c.moment desc")
	Collection<Complaint> getLoggedCustomerUnassignedComplaints(int id);

	@Query("select c from Complaint c where c.customer.id = ?1 and c.id = ?2")
	Complaint getLoggedCustomerComplaint(int id, Integer complaintId);

	@Query("select c from Complaint c where c.review=null")
	Collection<Complaint> getComplaintsWithoutReview();

	@Query("select c from Complaint c where c.review.id=?1")
	Complaint getComplaintOfReview(int reviewId);

	@Query("select c from Complaint c where c.customer.id = ?1")
	Collection<Complaint> getCustomerComplaints(int id);

	@Query("select c from Complaint c where c.host.id = ?1")
	Collection<Complaint> getHostComplaints(int id);

	@Query("select c from Complaint c where c.transporter.id = ?1")
	Collection<Complaint> getTransporterComplaints(int id);

	@Query("select c from Complaint c where c.travelAgency.id = ?1")
	Collection<Complaint> getTravelAgencyComplaints(int id);

}
