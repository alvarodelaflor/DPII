
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

	@Query("select j from JobApplication j where j.cleaner.id=?1 and j.host.id=?2 and j.dropMoment=null and (j.status=null or j.status=true)")
	Collection<JobApplication> getJobApplicationAcceptedAndPending(int cleanerId, int hostId);

	@Query("select j from JobApplication j where j.cleaner.id=?1")
	Collection<JobApplication> findAllByCleanerId(int cleanerId);

	@Query("select j from JobApplication j where j.host.id=?2 and j.status=?1 and j.dropMoment=null")
	Collection<JobApplication> getJobApplicationByStatusAndHostId(Boolean status, int hostId);

	@Query("select j from JobApplication j where j.host.id=?1 and j.status=null and j.dropMoment=null")
	Collection<JobApplication> getJobApplicationPendingByHostId(int hostId);

	@Query("select j from JobApplication j where j.host.id=?1 and j.status=true and j.dropMoment!=null")
	Collection<JobApplication> getExCleaners(int hostId);

	@Query("select j from JobApplication j where j.host.id = ?1")
	Collection<JobApplication> getHostApplications(int id);
	
	@Query("select j from JobApplication j where j.cleaner.id = ?1")
	Collection<JobApplication> getCleanerApplications(int id);
}
