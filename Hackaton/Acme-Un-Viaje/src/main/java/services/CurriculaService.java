
package services;

/**
 * CurriculaService.java
 * 
 * @author Alvaro de la Flor Bonilla GitHub: alvar017
 * 
 *         CONTROL:
 *         30/03/2019 14:47 Creation
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

import repositories.CurriculaRepository;
import security.LoginService;
import domain.Cleaner;
import domain.Curricula;
import domain.EducationalData;
import domain.MiscellaneousAttachment;

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
	private Validator						validator;


	// CRUD Methods

	/**
	 * Create a CurriculaEntity. Must exist a {@link Cleaner} login to create it.
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link Curricula}
	 */
	public Curricula create() {
		Assert.notNull(LoginService.getPrincipal());
		Assert.notNull(this.cleanerService.getCleanerLogin(), "Must exist an cleaner login");
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
	 * <p>
	 * Save a curricula. Check user login ({@link Cleaner}) is the same that user who create the curricula in the case he want to edit it
	 * </p>
	 * Curricula must not be null<br>
	 * Must exist cleaner login
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return The save {@link Curricula}
	 */
	public Curricula save(final Curricula curricula) {
		Assert.notNull(curricula, "Curricula is null");
		Assert.isTrue(this.checkAnyLogger(), "Any user is login");
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner is login");
		final Curricula curriculaDB = this.curriculaRepository.findOne(curricula.getId());
		if (curriculaDB != null)
			Assert.isTrue(curricula.getCleaner().equals(cleanerLogin), "Not allow to edit a not own curricula");

		//TODO
		//		if (curricula.getPhone().matches("^([0-9]{4,})$"))
		//			curricula.setPhone(this.configurationService.getConfiguration().getCountryCode() + " " + curricula.getPhone());

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
		Assert.notNull(cleanerLogin, "No cleaner is login");
		final Curricula curriculaDB = this.curriculaRepository.findOne(curricula.getId());
		Assert.notNull(curriculaDB, "The curricula is not in DB");
		Assert.isTrue(curricula.getCleaner().equals(cleanerLogin), "Not allow to delete a not own curricula");
		final Collection<EducationalData> educationalDatas = this.educationalDataService.getEducationalDataFromCurricula(curriculaDB);
		if (!educationalDatas.isEmpty())
			this.educationalDataService.deleteAll(educationalDatas);
		//		final Collection<PositionData> positionDatas = this.positionDataService.getPositionDataFromCurricula(curriculaDB);
		//		if (!positionDatas.isEmpty())
		//			this.positionDataService.deleteAll(positionDatas);
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
	public Curricula createCurriculaCopyAndSave(final Curricula curricula) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.isTrue(curricula.getCleaner().equals(cleanerLogin), "Try to do a copy of curricula by not own cleaner");
		final Curricula curriculaCopy = this.create();
		curriculaCopy.setCleaner(curricula.getCleaner());
		curriculaCopy.setIsCopy(true);
		curriculaCopy.setLinkLinkedin(curricula.getLinkLinkedin());
		curriculaCopy.setName(curricula.getName());
		curriculaCopy.setPhone(curricula.getPhone());
		curriculaCopy.setStatement(curricula.getStatement());
		final Curricula copy = this.save(curriculaCopy);
		this.educationalDataService.makeCopyAllEducationalDataForCurricula(curricula, copy);
		this.miscellaneousAttachmentService.makeCopyAllMiscellaneousAttachmentForCurricula(curricula, copy);
		return copy;
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
}
