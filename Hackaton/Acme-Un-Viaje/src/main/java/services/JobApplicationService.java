
package services;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Cleaner;
import domain.Curricula;
import domain.Host;
import domain.JobApplication;
import repositories.JobApplicationRepository;

/*
 * CONTROL DE CAMBIOS JobApplicationService.java
 * 
 * ALVARO 17/02/2019 11:51 CREACIÓN DE LA CLASE
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
	private CurriculaService curriculaService;
	@Autowired
	private Validator					validator;

	//Default messages ------------------
	
	private String cleanerNotLogin = "Any cleaner is login";
	private String hostNotFound = "This host has not been found in database";
	private String hostNull = "The host is null";
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
		Assert.isTrue(this.checkValidForNewApplication(cleaner.getId(), host.getId()), cleanerAcceptedPending);
		
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
			if (this.cleanerService.getCleanerLogin()!=null) {				
				Assert.isTrue(jobApplicationDB.getStatus()==null, finalMode);
			} else {
				Host hostLogin = this.hostService.getHostLogin();
				Assert.notNull(hostLogin, hostNull);
				Assert.isTrue(jobApplication.getHost().equals(hostLogin), diferentHost);
				Assert.isTrue(jobApplication.getStatus()==null && jobApplication.getDropMoment()==null, "Application must be accepted o rejected");
			}
			Assert.isTrue(jobApplicationDB.getCleaner().equals(jobApplication.getCleaner()), diferentCleaner);
			Assert.isTrue(jobApplicationDB.getHost().equals(jobApplication.getHost()), diferentHost);
		} else {
			Assert.isTrue(checkValidForNewApplication(this.cleanerService.getCleanerLogin().getId(), jobApplication.getHost().getId()), cleanerAcceptedPending);
		}
		return this.jobApplicationRepository.save(jobApplication); 
	}
	
	/**
	 * 
	 * If we make a curriculum change in the edition of the working application<br>
	 * we delete the previous copy
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void checkChangeCurricula(JobApplication jobApplication) {
		Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		if (cleanerLogin!=null) {
			try {
				JobApplication jobApplicationDB = this.findOne(jobApplication.getId());
				Assert.isTrue(cleanerLogin.equals(jobApplicationDB.getCleaner()));
				if (!jobApplication.getCurricula().equals(jobApplicationDB.getCurricula())) {
					this.curriculaService.delete(jobApplicationDB.getCurricula());
				}				
			} catch (Exception e) {

			}
		}
	}

	/**
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return <{@link JobApplication}>
	 */
	public void delete(final JobApplication jobApplication) {
		Assert.notNull(jobApplication, jobApplicationNull);
		JobApplication jobApplicationDB = this.jobApplicationRepository.findOne(jobApplication.getId());
		Assert.isTrue(jobApplicationDB.getStatus()==null, finalMode);
		Assert.isTrue(this.checkValidForEdit(jobApplication), actorNoValid);
		Curricula curricula = this.curriculaService.findOne(jobApplication.getCurricula().getId());
		this.jobApplicationRepository.delete(jobApplication);
		this.curriculaService.delete(curricula);
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
		Boolean res = true;
		int aux = this.jobApplicationRepository.getJobApplicationAcceptedAndPending(cleanerId, hostId).size();
		if (aux > 0) {
			res = false;
		}
		return res;
	}
	
	public Boolean checkValidForEdit(JobApplication jobApplication) {
		Boolean res = false;
		Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Host hostLogin = this.hostService.getHostLogin();
		
		if (cleanerLogin!= null && cleanerLogin.getId()==jobApplication.getCleaner().getId()) {
			res = true;
		}
		
		if (hostLogin!= null && hostLogin.getId()==jobApplication.getHost().getId()) {
			res = true;
		}
		
		return res;
	}
	
	/**
	 * 
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link JobApplication}>
	 */
	public Collection<JobApplication> findAllByCleanerId(int cleanerId) {
		return this.jobApplicationRepository.findAllByCleanerId(cleanerId);
	}
	
	public Collection<JobApplication> getJobApplicationByStatusAndHostId(Boolean status, int hostId){
		Host hostLogin = this.hostService.getHostLogin();
		Host hostToCheck = this.hostService.findOne(hostId);
		Assert.notNull(hostLogin, hostNull);
		Assert.notNull(hostToCheck, hostNotFound);
		Assert.isTrue(hostLogin.equals(hostToCheck), diferentHost);
		return this.jobApplicationRepository.getJobApplicationByStatusAndHostId(status, hostId);
	}
	
	public Collection<JobApplication> getJobApplicationPendingByHostId(int hostId) { 
		Host hostLogin = this.hostService.getHostLogin();
		Host hostToCheck = this.hostService.findOne(hostId);
		Assert.notNull(hostLogin, hostNull);
		Assert.notNull(hostToCheck, hostNotFound);
		Assert.isTrue(hostLogin.equals(hostToCheck), diferentHost);
		return this.jobApplicationRepository.getJobApplicationPendingByHostId(hostId);		
	}
	
	public Collection<JobApplication> getExCleaners(int hostId) {
		Host hostLogin = this.hostService.getHostLogin();
		Host hostToCheck = this.hostService.findOne(hostId);
		Assert.notNull(hostLogin, hostNull);
		Assert.notNull(hostToCheck, hostNotFound);
		Assert.isTrue(hostLogin.equals(hostToCheck), diferentHost);
		return this.jobApplicationRepository.getExCleaners(hostId);
	}
	
	public JobApplication dropUser(int jobApplicationId) {
		Host hostLogin = this.hostService.getHostLogin();
		Assert.notNull(hostLogin, hostNull);
		JobApplication jobApplicationDB = this.findOne(jobApplicationId);
		Assert.notNull(jobApplicationDB, jobApplicationNull);
		Assert.isTrue(jobApplicationDB.getStatus().equals(true), "Application is not accepted");
		Assert.isTrue(jobApplicationDB.getDropMoment()==null, "Cleaner is already drop");
		Assert.isTrue(jobApplicationDB.getHost().equals(hostLogin), "Not allow to drop, diferent host");
		jobApplicationDB.setDropMoment(DateTime.now().toDate());
		return this.save(jobApplicationDB);
	}
	
	public JobApplication rejectUser(JobApplication jobApplication) {
		Host hostLogin = this.hostService.getHostLogin();
		Assert.notNull(hostLogin, hostNull);
		JobApplication jobApplicationDB = this.findOne(jobApplication.getId());
		Assert.notNull(jobApplicationDB, jobApplicationNull);
		Assert.notNull(hostLogin, "No host is login");
		Assert.isTrue(jobApplicationDB != null, "jobApplication.null");
		Assert.isTrue(jobApplicationDB.getCleaner().equals(jobApplication.getCleaner()), diferentCleaner);
		Assert.isTrue(jobApplicationDB.getHost().equals(jobApplication.getHost()), diferentHost);
		Assert.isTrue(jobApplicationDB.getStatus()==null, "Trying to edit an accept JobApplication");
		Assert.isTrue(jobApplicationDB.getDropMoment()==null, "Trying to edit a drop JobApplication");
		Assert.isTrue(jobApplicationDB.getHost().equals(hostLogin), diferentHost);
		jobApplication.setStatus(false);
		return this.jobApplicationRepository.save(jobApplication);
	}
	
	public JobApplication acceptApplication(int jobApplicationId) {
		Host hostLogin = this.hostService.getHostLogin();
		Assert.notNull(hostLogin, hostNull);
		JobApplication jobApplicationDB = this.findOne(jobApplicationId);
		Assert.notNull(jobApplicationDB, jobApplicationNull);
		Assert.isTrue(jobApplicationDB.getStatus()==null, "Trying to edit an acept or reject application");
		Assert.isTrue(jobApplicationDB.getDropMoment()==null, "Trying to edit a drop application");
		Assert.notNull(hostLogin, "No cleaner is login");
		Assert.isTrue(hostLogin.equals(jobApplicationDB.getHost()));
		jobApplicationDB.setStatus(true);
		return this.jobApplicationRepository.save(jobApplicationDB);
	}
	// AUXILIAR METHODS

}
