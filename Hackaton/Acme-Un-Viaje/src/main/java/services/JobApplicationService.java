
package services;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Cleaner;
import domain.Host;
import domain.JobApplication;
import repositories.JobApplicationRepository;

/*
 * CONTROL DE CAMBIOS JobApplicationService.java
 * 
 * ALVARO 17/02/2019 11:51 CREACI�N DE LA CLASE
 */

@Service
public class JobApplicationService {

	//Managed Repository -------------------	
	@Autowired
	private JobApplicationRepository	jobApplicationRepository;

	//Supporting services ------------------
	@Autowired
	private HostService				hostService;
	@Autowired
	private CleanerService			cleanerService;
	@Autowired
	private Validator					validator;

	//Default messages ------------------
	
	private String cleanerNotLogin = "Any cleaner is login";
	private String hostNotFound = "This host has not been found in database";
	private String cleanerAcceptedPending = "This cleaner has an already application (accepted or pending) for this host";
	private String diferentHost = "The host is diferent";
	private String diferentCleaner = "The cleaner is diferent";
	private String jobApplicationNull = "JobApplication is null";
	private String finalMode = "JobApplication in final mode can be manage (edit or delete)";
	private String actorNoValid = "This actor is not valid for edit de application, he no is the cleaner/host";

	// CRUD METHODS

	/**
	 * 
	 * This method need a valid Cleaner loging and a valid host
	 * Cleaner can have an pending or accepted application for this host
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link JobApplication}
	 */
	public JobApplication create(final int hostId) {

		Cleaner cleaner = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleaner, cleanerNotLogin);
		final Host host = this.hostService.findOne(hostId);
		Assert.notNull(host, hostNotFound);
		final JobApplication jobApplication = new JobApplication();

		// We check if the cleaner is valid for a new application
		Assert.isTrue(!this.checkValidForNewApplication(cleaner.getId(), host.getId()), cleanerAcceptedPending);
		
		jobApplication.setHost(host);
		jobApplication.setCleaner(cleaner);
		jobApplication.setCreateMoment(DateTime.now().toDate());
		// The application is pending so his status is null
		jobApplication.setStatus(null);

		return jobApplication;
	}

	/**
	 * 
	 * @return {@link Collection}<{@link JobApplication}>
	 * @author Alvaro de la Flor Bonilla
	 */
	public Collection<JobApplication> findAll() {
		return this.jobApplicationRepository.findAll();
	}

	/**
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return <{@link JobApplication}>
	 */
	public JobApplication findOne(final int id) {
		return this.jobApplicationRepository.findOne(id);
	}

	/**
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return <{@link JobApplication}>
	 */
	public JobApplication save(final JobApplication jobApplication) {
		Assert.notNull(jobApplication, jobApplicationNull);
		
		Assert.isTrue(this.checkValidForEdit(jobApplication), actorNoValid);
		
		JobApplication jobApplicationDB = this.jobApplicationRepository.findOne(jobApplication.getId());
		if (jobApplicationDB != null) {
			Assert.isTrue(jobApplicationDB.getStatus()!=null && jobApplicationDB.getStatus().equals(false), finalMode);
			Assert.isTrue(jobApplicationDB.getCleaner().equals(jobApplication.getCleaner()), diferentCleaner);
			Assert.isTrue(jobApplicationDB.getHost().equals(jobApplication.getHost()), diferentHost);
		}
		return this.jobApplicationRepository.save(jobApplication);
	}

	/**
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return <{@link JobApplication}>
	 */
	public void delete(final JobApplication jobApplication) {
		Assert.notNull(jobApplication, jobApplicationNull);
		JobApplication jobApplicationDB = this.jobApplicationRepository.findOne(jobApplication.getId());
		Assert.isTrue(jobApplicationDB.getStatus()!=null && jobApplicationDB.getStatus().equals(false), finalMode);
		Assert.isTrue(this.checkValidForEdit(jobApplication), actorNoValid);
		this.jobApplicationRepository.delete(jobApplication);
	}
	
	// CRUD METHODS
	
	//AUXILIAR METHODS

	public JobApplication reconstruct(final JobApplication jobApplication, final BindingResult binding) {
		JobApplication result;
		Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Host hostLogin = this.hostService.getHostLogin();

		if (jobApplication.getId() == 0) {
			Assert.notNull(cleanerLogin, cleanerNotLogin);
			jobApplication.setCleaner(cleanerLogin);
			jobApplication.setCreateMoment(DateTime.now().toDate());
			// The application is pending so his status is null
			jobApplication.setStatus(null);
		} else {
			result = this.jobApplicationRepository.findOne(jobApplication.getId());
			jobApplication.setId(result.getId());
			jobApplication.setVersion(result.getVersion());
			if (hostLogin!=null) {
				jobApplication.setHost(hostLogin);
				jobApplication.setCleaner(result.getCleaner());
				jobApplication.setCleanerMessage(result.getCleanerMessage());
				jobApplication.setCurricula(result.getCurricula());
			}
			if (cleanerLogin!=null) {
				jobApplication.setCleaner(cleanerLogin);
				jobApplication.setHost(result.getHost());
				jobApplication.setRejectMessage(result.getRejectMessage());
				jobApplication.setDropMoment(result.getDropMoment());
			}

			jobApplication.setCreateMoment(result.getCreateMoment());
			jobApplication.setStatus(result.getStatus());
		}
		this.validator.validate(jobApplication, binding);
		return jobApplication;
	}
	
	public Boolean checkValidForNewApplication(int cleanerId, int hostId) {
		Boolean res = false;
		int aux = this.jobApplicationRepository.getJobApplicationAcceptedAndPending(cleanerId, hostId).size();
		if (aux > 0) {
			res = true;
		}
		return res;
	}
	
	private Boolean checkValidForEdit(JobApplication jobApplication) {
		Boolean res = false;
		Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Host hostLogin = this.hostService.getHostLogin();
		
		if (cleanerLogin.getId()==jobApplication.getId()) {
			res = true;
		}
		
		if (hostLogin.getId()==jobApplication.getId()) {
			res = true;
		}
		
		return res;
	}
	// AUXILIAR METHODS
}