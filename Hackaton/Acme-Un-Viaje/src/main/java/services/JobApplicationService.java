
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
	private HostService					hostService;
	@Autowired
	private CleanerService				cleanerService;
	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private Validator					validator;

	//Default messages ------------------

	private final String				cleanerNotLogin			= "Any cleaner is login";
	private final String				hostNotFound			= "This host has not been found in database";
	private final String				hostNull				= "The host is null";
	private final String				cleanerAcceptedPending	= "This cleaner has an already application (accepted or pending) for this host";
	private final String				diferentHost			= "The host is diferent";
	private final String				diferentCleaner			= "The cleaner is diferent";
	private final String				jobApplicationNull		= "JobApplication is null";
	private final String				finalMode				= "JobApplication in final mode can be manage (edit or delete)";
	private final String				actorNoValid			= "This actor is not valid for edit de application, he no is the cleaner/host";


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

		final Cleaner cleaner = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleaner, this.cleanerNotLogin);
		final Host host = this.hostService.findOne(hostId);
		Assert.notNull(host, this.hostNotFound);
		final JobApplication jobApplication = new JobApplication();

		// We check if the cleaner is valid for a new application
		Assert.isTrue(this.checkValidForNewApplication(cleaner.getId(), host.getId()), this.cleanerAcceptedPending);

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
	 * This method save an application. The host or cleaner owner must be login.
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return <{@link JobApplication}>
	 */
	public JobApplication save(final JobApplication jobApplication) {
		Assert.notNull(jobApplication, this.jobApplicationNull);

		Assert.isTrue(this.checkValidForEdit(jobApplication), this.actorNoValid);

		final JobApplication jobApplicationDB = this.jobApplicationRepository.findOne(jobApplication.getId());
		if (jobApplicationDB != null) {
			if (this.cleanerService.getCleanerLogin() != null)
				Assert.isTrue(jobApplicationDB.getStatus() == null, this.finalMode);
			else {
				final Host hostLogin = this.hostService.getHostLogin();
				Assert.notNull(hostLogin, this.hostNull);
				Assert.isTrue(jobApplication.getHost().equals(hostLogin), this.diferentHost);
				Assert.isTrue(jobApplication.getStatus() == null && jobApplication.getDropMoment() == null, "Application must be accepted o rejected");
			}
			Assert.isTrue(jobApplicationDB.getCleaner().equals(jobApplication.getCleaner()), this.diferentCleaner);
			Assert.isTrue(jobApplicationDB.getHost().equals(jobApplication.getHost()), this.diferentHost);
		} else
			Assert.isTrue(this.checkValidForNewApplication(this.cleanerService.getCleanerLogin().getId(), jobApplication.getHost().getId()), this.cleanerAcceptedPending);
		return this.jobApplicationRepository.save(jobApplication);
	}

	/**
	 *
	 * This method save an application. The host or cleaner owner must be login.
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return <{@link JobApplication}>
	 */
	public void delete(final JobApplication jobApplication) {
		Assert.notNull(jobApplication, this.jobApplicationNull);
		final JobApplication jobApplicationDB = this.jobApplicationRepository.findOne(jobApplication.getId());
		Assert.isTrue(jobApplicationDB.getStatus() == null, this.finalMode);
		Assert.isTrue(this.checkValidForEdit(jobApplication), this.actorNoValid);
		final Curricula curricula = this.curriculaService.findOne(jobApplication.getCurricula().getId());
		this.jobApplicationRepository.delete(jobApplication);
		this.curriculaService.delete(curricula);
	}

	// CRUD METHODS

	//AUXILIAR METHODS

	/**
	 *
	 * This method validate an application. The host or cleaner owner must be login.
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return <{@link JobApplication}>
	 */
	public JobApplication reconstruct(final JobApplication jobApplication, final BindingResult binding) {
		JobApplication result;
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		final Host hostLogin = this.hostService.getHostLogin();

		if (jobApplication.getId() == 0) {
			Assert.notNull(cleanerLogin, this.cleanerNotLogin);
			jobApplication.setCleaner(cleanerLogin);
			jobApplication.setCreateMoment(DateTime.now().toDate());
			// The application is pending so his status is null
			jobApplication.setStatus(null);
		} else {
			result = this.jobApplicationRepository.findOne(jobApplication.getId());
			jobApplication.setId(result.getId());
			jobApplication.setVersion(result.getVersion());
			if (hostLogin != null) {
				jobApplication.setHost(hostLogin);
				jobApplication.setCleaner(result.getCleaner());
				jobApplication.setCleanerMessage(result.getCleanerMessage());
				jobApplication.setCurricula(result.getCurricula());
			}
			if (cleanerLogin != null) {
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

	/**
	 *
	 * Check if a cleaner if valid to create a jobapplication for an host
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return <{@link Boolean}>
	 */
	public Boolean checkValidForNewApplication(final int cleanerId, final int hostId) {
		Boolean res = true;
		final int aux = this.jobApplicationRepository.getJobApplicationAcceptedAndPending(cleanerId, hostId).size();
		if (aux > 0)
			res = false;
		return res;
	}


	/**
	 *
	 * Check if a cleaner if valid to edit a jobapplication for an host
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return <{@link Boolean}>
	 */
	public Boolean checkValidForEdit(final JobApplication jobApplication) {
		Boolean res = false;
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		final Host hostLogin = this.hostService.getHostLogin();

		if (cleanerLogin != null && cleanerLogin.getId() == jobApplication.getCleaner().getId())
			res = true;

		if (hostLogin != null && hostLogin.getId() == jobApplication.getHost().getId())
			res = true;

		return res;
	}

	/**
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link JobApplication}>
	 */
	public Collection<JobApplication> findAllByCleanerId(final int cleanerId) {
		return this.jobApplicationRepository.findAllByCleanerId(cleanerId);
	}

	/**
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link JobApplication}>
	 */
	public Collection<JobApplication> getJobApplicationByStatusAndHostId(final Boolean status, final int hostId) {
		final Host hostLogin = this.hostService.getHostLogin();
		final Host hostToCheck = this.hostService.findOne(hostId);
		Assert.notNull(hostLogin, this.hostNull);
		Assert.notNull(hostToCheck, this.hostNotFound);
		Assert.isTrue(hostLogin.equals(hostToCheck), this.diferentHost);
		return this.jobApplicationRepository.getJobApplicationByStatusAndHostId(status, hostId);
	}

	/**
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link JobApplication}>
	 */
	public Collection<JobApplication> getJobApplicationPendingByHostId(final int hostId) {
		final Host hostLogin = this.hostService.getHostLogin();
		final Host hostToCheck = this.hostService.findOne(hostId);
		Assert.notNull(hostLogin, this.hostNull);
		Assert.notNull(hostToCheck, this.hostNotFound);
		Assert.isTrue(hostLogin.equals(hostToCheck), this.diferentHost);
		return this.jobApplicationRepository.getJobApplicationPendingByHostId(hostId);
	}

	/**
	 *
	 * Get cleaners who their jobApplications are accepted but they have been drop of the company
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link JobApplication}>
	 */
	public Collection<JobApplication> getExCleaners(final int hostId) {
		final Host hostLogin = this.hostService.getHostLogin();
		final Host hostToCheck = this.hostService.findOne(hostId);
		Assert.notNull(hostLogin, this.hostNull);
		Assert.notNull(hostToCheck, this.hostNotFound);
		Assert.isTrue(hostLogin.equals(hostToCheck), this.diferentHost);
		return this.jobApplicationRepository.getExCleaners(hostId);
	}

	/**
	 *
	 * Drop an user (set a drop moment)
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link JobApplication}>
	 */
	public JobApplication dropUser(final int jobApplicationId) {
		final Host hostLogin = this.hostService.getHostLogin();
		Assert.notNull(hostLogin, this.hostNull);
		final JobApplication jobApplicationDB = this.findOne(jobApplicationId);
		Assert.notNull(jobApplicationDB, this.jobApplicationNull);
		Assert.isTrue(jobApplicationDB.getStatus().equals(true), "Application is not accepted");
		Assert.isTrue(jobApplicationDB.getDropMoment() == null, "Cleaner is already drop");
		Assert.isTrue(jobApplicationDB.getHost().equals(hostLogin), "Not allow to drop, diferent host");
		jobApplicationDB.setDropMoment(DateTime.now().toDate());
		return this.jobApplicationRepository.save(jobApplicationDB);
	}

	/**
	 *
	 * Get the reject users
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link JobApplication}>
	 */
	public JobApplication rejectUser(final JobApplication jobApplication) {
		final Host hostLogin = this.hostService.getHostLogin();
		Assert.notNull(hostLogin, this.hostNull);
		final JobApplication jobApplicationDB = this.findOne(jobApplication.getId());
		Assert.notNull(jobApplicationDB, this.jobApplicationNull);
		Assert.notNull(hostLogin, "No host is login");
		Assert.isTrue(jobApplicationDB != null, "jobApplication.null");
		Assert.isTrue(jobApplicationDB.getCleaner().equals(jobApplication.getCleaner()), this.diferentCleaner);
		Assert.isTrue(jobApplicationDB.getHost().equals(jobApplication.getHost()), this.diferentHost);
		Assert.isTrue(jobApplicationDB.getStatus() == null, "Trying to edit an accept JobApplication");
		Assert.isTrue(jobApplicationDB.getDropMoment() == null, "Trying to edit a drop JobApplication");
		Assert.isTrue(jobApplicationDB.getHost().equals(hostLogin), this.diferentHost);
		jobApplication.setStatus(false);
		return this.jobApplicationRepository.save(jobApplication);
	}

	/**
	 *
	 * Get the accept application
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link JobApplication}>
	 */
	public JobApplication acceptApplication(final int jobApplicationId) {
		final Host hostLogin = this.hostService.getHostLogin();
		Assert.notNull(hostLogin, this.hostNull);
		final JobApplication jobApplicationDB = this.findOne(jobApplicationId);
		Assert.notNull(jobApplicationDB, this.jobApplicationNull);
		Assert.isTrue(jobApplicationDB.getStatus() == null, "Trying to edit an acept or reject application");
		Assert.isTrue(jobApplicationDB.getDropMoment() == null, "Trying to edit a drop application");
		Assert.notNull(hostLogin, "No cleaner is login");
		Assert.isTrue(hostLogin.equals(jobApplicationDB.getHost()));
		jobApplicationDB.setStatus(true);
		return this.jobApplicationRepository.save(jobApplicationDB);
	}
	// AUXILIAR METHODS

	public void deleteHostApplications(final Host host) {
		final Collection<JobApplication> items = this.jobApplicationRepository.getHostApplications(host.getId());
		if (items != null && !items.isEmpty())
			for (final JobApplication item : items)
				this.jobApplicationRepository.delete(item);
	}

	public void deleteCleanerApps(Cleaner cleaner) {
		final Collection<JobApplication> items = this.jobApplicationRepository.getCleanerApplications(cleaner.getId());
		if (items != null && !items.isEmpty())
			for (final JobApplication item : items)
				this.jobApplicationRepository.delete(item);
	}

}
