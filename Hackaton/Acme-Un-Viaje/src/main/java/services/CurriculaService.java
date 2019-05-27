
package services;

/**
 * CurriculaService.java
 *
 * @author Alvaro de la Flor Bonilla GitHub: alvar017
 *
 *         CONTROL:
 *         21/05/2019 Version 1.0
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Cleaner;
import domain.Curricula;
import domain.EducationalData;
import domain.JobApplication;
import domain.MiscellaneousAttachment;
import repositories.CurriculaRepository;
import security.LoginService;

@Service
@Transactional
public class CurriculaService {

	@Autowired
	private CurriculaRepository				curriculaRepository;

	@Autowired
	private CleanerService					cleanerService;

	@Autowired
	private EducationalDataService			educationalDataService;

	@Autowired
	private MiscellaneousAttachmentService	miscellaneousAttachmentService;

	@Autowired
	private JobApplicationService			jobAplicationService;

	@Autowired
	private Validator						validator;

	// Default messages
	private final String					anyCleanerLogin		= "Any cleaner is login in the system (CURRICULASERVICE)";
	private final String					nullCurricula		= "Curricula is null";
	private final String					curriculaNotFound	= "The curricula has not been found in database";
	private final String					notValidUser		= "This user can not edit the curricula, he is not his owner";


	// CRUD Methods

	/**
	 * Create a Curricula entity. Must exist a {@link Cleaner} login to create it.
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Curricula}
	 */
	public Curricula create() {
		final Cleaner cleaner = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleaner, this.anyCleanerLogin);
		return new Curricula();
	}

	/**
	 *
	 * Return a collection of all {@link Curricula} in database.
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link Curricula}>
	 */
	public List<Curricula> findAll() {
		return this.curriculaRepository.findAll();
	}

	/**
	 * Find a curricula in dataBase by id
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Curricula}
	 */
	public Curricula findOne(final int curriculaId) {
		return this.curriculaRepository.findOne(curriculaId);
	}

	/**
	 * Save a curricula. Check user login ({@link Cleaner}) is the same that user who create the curricula in the case he want to edit it
	 * Curricula must not be null<br>
	 * Must exist cleaner login
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return The save {@link Curricula}
	 */
	public Curricula save(final Curricula curricula) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, this.anyCleanerLogin);
		Assert.notNull(curricula, this.nullCurricula);
		final Curricula curriculaDB = this.curriculaRepository.findOne(curricula.getId());
		if (curriculaDB != null)
			Assert.isTrue(curricula.getCleaner().equals(cleanerLogin), this.notValidUser);
		return this.curriculaRepository.save(curricula);
	}

	/**
	 * Delete a curricula.<br>
	 * Check user login ({@link Cleaner}) is the same that user who created the curricula<br>
	 * Must exist a {@link Cleaner} login and curricula must not be null
	 *
	 * @author Alvaro de la Flor Bonilla
	 */
	public void delete(final Curricula curricula) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, this.anyCleanerLogin);
		final Curricula curriculaDB = this.curriculaRepository.findOne(curricula.getId());
		Assert.notNull(curriculaDB, this.curriculaNotFound);
		Assert.isTrue(curricula.getCleaner().equals(cleanerLogin), this.notValidUser);
		final Collection<EducationalData> educationalDatas = this.educationalDataService.getEducationalDataFromCurricula(curriculaDB);
		if (!educationalDatas.isEmpty())
			this.educationalDataService.deleteAll(educationalDatas);
		final Collection<MiscellaneousAttachment> miscellaneousAttachaments = this.miscellaneousAttachmentService.getMiscellaneousAttachmentFromCurricula(curriculaDB);
		if (!miscellaneousAttachaments.isEmpty())
			this.miscellaneousAttachmentService.deleteAll(miscellaneousAttachaments);
		this.curriculaRepository.delete(curricula);
	}

	// CRUD Methods

	// AUXILIAR METHODS

	/**
	 * This method reconstruct a prunned {@link Curricula} and validate it
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return The reconstruct {@link Curricula}
	 */
	public Curricula reconstruct(final Curricula curricula, final BindingResult binding) {
		Curricula result;

		if (curricula.getId() == 0) {
			curricula.setCleaner(this.cleanerService.getCleanerLogin());
			curricula.setIsCopy(false);
			result = curricula;
		} else {
			result = this.curriculaRepository.findOne(curricula.getId());
			Assert.notNull(result, "Curricula is null");
			curricula.setId(result.getId());
			curricula.setVersion(result.getVersion());
			curricula.setCleaner(result.getCleaner());
			curricula.setIsCopy(false);
			result = curricula;
		}
		this.validator.validate(result, binding);
		return result;
	}

	/**
	 *
	 * This methods create and save a copy of the curricula given and all of their educationalData and positionData<br>
	 * Set to true the isCopy atribute of curriculum, educationalData and positionData instances that they have been copy<br>
	 * The {@link Cleaner} who create the Curricula must be login.
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return The copy {@link Curricula} instance
	 */
	public JobApplication createCurriculaCopyAndSave(final JobApplication jobApplication) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		final Curricula curriculaCopy = this.create();
		Curricula copy;
		JobApplication savedJobApplication;
		Assert.isTrue(jobApplication.getCurricula().getCleaner().equals(cleanerLogin), "Try to do a copy of curricula by not own cleaner");
		final Curricula curricula = jobApplication.getCurricula();
		final JobApplication jobApplicationDB = this.jobAplicationService.findOne(jobApplication.getId());
		if (jobApplicationDB != null && !curricula.equals(this.jobAplicationService.findOne(jobApplication.getId()).getCurricula())) {
			curriculaCopy.setCleaner(curricula.getCleaner());
			curriculaCopy.setIsCopy(true);
			curriculaCopy.setLinkLinkedin(curricula.getLinkLinkedin());
			curriculaCopy.setBannerLogo(curricula.getBannerLogo());
			curriculaCopy.setName(curricula.getName());
			curriculaCopy.setPhone(curricula.getPhone());
			curriculaCopy.setStatement(curricula.getStatement());
			copy = this.save(curriculaCopy);
			final Curricula curriculaToDelete = this.findOne(jobApplicationDB.getCurricula().getId());
			Assert.notNull(curriculaToDelete, "Curricula to delete null");
			this.educationalDataService.makeCopyAllEducationalDataForCurricula(curricula, copy);
			this.miscellaneousAttachmentService.makeCopyAllMiscellaneousAttachmentForCurricula(curricula, copy);
			jobApplication.setCurricula(copy);
			savedJobApplication = this.jobAplicationService.save(jobApplication);
			this.delete(curriculaToDelete);
		} else if (jobApplicationDB != null && jobApplication.getCurricula().equals(jobApplicationDB.getCurricula()))
			savedJobApplication = this.jobAplicationService.save(jobApplication);
		else {
			curriculaCopy.setCleaner(curricula.getCleaner());
			curriculaCopy.setIsCopy(true);
			curriculaCopy.setLinkLinkedin(curricula.getLinkLinkedin());
			curriculaCopy.setBannerLogo(curricula.getBannerLogo());
			curriculaCopy.setName(curricula.getName());
			curriculaCopy.setPhone(curricula.getPhone());
			curriculaCopy.setStatement(curricula.getStatement());
			copy = this.save(curriculaCopy);
			this.educationalDataService.makeCopyAllEducationalDataForCurricula(curricula, copy);
			this.miscellaneousAttachmentService.makeCopyAllMiscellaneousAttachmentForCurricula(curricula, copy);
			jobApplication.setCurricula(copy);
			savedJobApplication = this.jobAplicationService.save(jobApplication);
		}
		return savedJobApplication;
	}

	/**
	 * @author Alvaro de la Flor Bonilla
	 */
	public void flush() {
		this.curriculaRepository.flush();
	}

	/**
	 * Check that any user is login
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Boolean}<br>
	 *         True if an user is login, false in otherwise.
	 */
	public Boolean checkAnyLogger() {
		Boolean res = true;
		try {
			LoginService.getPrincipal().getId();
		} catch (final Exception e) {
			res = false;
		}
		return res;
	}

	/**
	 *
	 * Return all Curricula in dataBase of a {@link Cleaner}.
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection}<{@link Curricula}>
	 */
	public Collection<Curricula> findAllByCleaner(final Cleaner cleaner) {
		List<Curricula> res = new ArrayList<>();
		if (cleaner != null)
			res = (List<Curricula>) this.curriculaRepository.getCurriculasOfCleaner(cleaner.getId());
		return res;
	}

	/**
	 *
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Collection < {@link Curricula} > not copy mode
	 */
	public Collection<Curricula> findAllNotCopyByCleaner(final Cleaner cleaner) {
		List<Curricula> res = new ArrayList<>();
		if (cleaner != null)
			res = (List<Curricula>) this.curriculaRepository.getCurriculasNotCopyOfCleaner(cleaner.getId());
		return res;
	}

	public void deleteCleanerCurriculas(final int cleanerId) {

		final Collection<Curricula> curriculas = this.curriculaRepository.getCurriculasOfCleaner(cleanerId);
		if (!curriculas.isEmpty())
			for (final Curricula curricula : curriculas)
				this.delete(curricula);
	}

	// AUXILIAR METHODS

	public Float minCurriculaPerCleaner() {

		return this.curriculaRepository.minCurriculaPerCleaner();
	}

	public Float maxCurriculaPerCleaner() {

		return this.curriculaRepository.maxCurriculaPerCleaner();
	}

	public Float avgCurriculaPerCleaner() {

		return this.curriculaRepository.avgCurriculaPerCleaner();
	}

	public Float stddevCurriculaPerCleaner() {

		return this.curriculaRepository.stddevCurriculaPerCleaner();
	}

	public void deleteAllByCleaner(final Cleaner cleaner) {
		final Collection<Curricula> cvs = this.findAllByCleaner(cleaner);
		if (cvs != null && !cvs.isEmpty())
			for (final Curricula curricula : cvs)
				this.delete(curricula);
	}
}
