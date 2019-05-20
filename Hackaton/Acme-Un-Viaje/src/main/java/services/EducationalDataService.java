
package services;

/**
 * EducationalDataService.java
 * 
 * @author Alvaro de la Flor Bonilla GitHub: alvar017
 * 
 *         CONTROL:
 *         30/03/2019 16:47 Creation
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationalDataRepository;
import domain.Curricula;
import domain.EducationalData;
import domain.Cleaner;

@Service
@Transactional
public class EducationalDataService {

	@Autowired
	private EducationalDataRepository	educationalDataRepository;

	@Autowired
	private CleanerService				cleanerService;


	// CRUD Methods

	/**
	 * Create a {@link EducationalData} instance<br>
	 * Must exist an {@link Cleaner} login
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link EducationalData}
	 */
	public EducationalData create() {
		Assert.notNull(this.cleanerService.getCleanerLogin());
		return new EducationalData();
	}

	/**
	 * Crete a new EducationalData with the given curricula<br>
	 * Atributes isCopy: false and the curricula given are setted by default
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link EducationalData}
	 */
	public EducationalData createWithHistory(final Curricula curricula) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner is login");
		final Cleaner cleanerCurricula = curricula.getCleaner();
		Assert.notNull(cleanerCurricula, "No cleaner for this curricula");
		Assert.isTrue(cleanerCurricula.equals(cleanerLogin), "Login and cleaner curricula are diferent");
		final EducationalData res = new EducationalData();
		res.setCurricula(curricula);
		res.setIsCopy(false);
		return res;
	}

	/**
	 * Save a EducationalData given <br>
	 * 
	 * Must be a {@link Cleaner} login and the owner of the educationalDate to save must be this cleaner
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link EducationalData}
	 */
	public EducationalData save(final EducationalData educationalData) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner login");
		Assert.notNull(educationalData, "Null educationalData");
		Assert.isTrue(cleanerLogin.equals(educationalData.getCurricula().getCleaner()), "Not allow to edit not own EducationalData");
		Assert.isTrue(!this.checkDate(educationalData.getStartDate(), educationalData.getEndDate()), "Not valid date configuaration");
		return this.educationalDataRepository.save(educationalData);
	}

	/**
	 * Save a collection of EducationalData given <br>
	 * 
	 * Must be a {@link Cleaner} login and the owner of the educationalDate to save must be this cleaner
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link EducationalData}<{@link EducationalData}>
	 */
	public Collection<EducationalData> savaAll(final Collection<EducationalData> educationalsData) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner is login");
		Assert.isTrue(!educationalsData.isEmpty(), "Empty collection of educationalsData");
		Assert.isTrue(educationalsData.iterator().next().getCurricula().getCleaner().equals(cleanerLogin), "No valid cleaner to save");
		return this.educationalDataRepository.save(educationalsData);
	}

	/**
	 * Find a EducationalData in the DataBase
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link EducationalData}
	 */
	public EducationalData findOne(final int educationalDataId) {
		return this.educationalDataRepository.findOne(educationalDataId);
	}

	/**
	 * Delete a EducationalData given <br>
	 * 
	 * Must be a {@link Cleaner} login and the owner of the educationalDate to delete must be this cleaner
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void delete(final EducationalData educationalData) {
		Assert.notNull(educationalData);
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner is login");
		Assert.isTrue(educationalData.getCurricula().getCleaner().equals(cleanerLogin), "Not allow to delete a educationalData of another cleaner");
		this.educationalDataRepository.delete(educationalData);
	}

	/**
	 * Delete a Collection of EducationalData given <br>
	 * 
	 * Must be a {@link Cleaner} login and the owner of the educationalDate to delete must be this cleaner
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void deleteAll(final Collection<EducationalData> educationalDatas) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin);
		Assert.isTrue(cleanerLogin.equals(((List<EducationalData>) educationalDatas).get(0).getCurricula().getCleaner()), "Diferent cleaners");
		if (!educationalDatas.isEmpty())
			this.educationalDataRepository.delete(educationalDatas);
	}

	// CRUD Methods

	// AUXILIAR METHODS

	/**
	 * @return The reconstruct curricula
	 * @author Alvaro de la Flor Bonilla
	 */
	public Collection<EducationalData> getEducationalDataFromCurricula(final Curricula curricula) {
		return this.educationalDataRepository.getEducationalDataFromCurriculaId(curricula.getId());
	}

	public void makeCopyAllEducationalDataForCurricula(final Curricula origen, final Curricula copy) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner is login");
		Assert.isTrue(origen.getIsCopy().equals(false) && copy.getIsCopy().equals(true), "Origen can not be copyMode and copy must be copyMode");
		Assert.isTrue(origen.getCleaner().equals(copy.getCleaner()), "Diferent cleaner origen-copy");
		Assert.isTrue(origen.getCleaner().equals(cleanerLogin), "Diferent cleaner origen-login");
		final List<EducationalData> educationalsData = (List<EducationalData>) this.educationalDataRepository.getEducationalDataFromCurriculaId(origen.getId());
		if (!educationalsData.isEmpty()) {
			final Collection<EducationalData> educationalsDataCopy = new ArrayList<>();
			for (final EducationalData educationalData : educationalsData)
				educationalsDataCopy.add(this.getCopy(educationalData, copy));
			this.savaAll(educationalsDataCopy);
		}
	}

	public EducationalData getCopy(final EducationalData educationalData, final Curricula copy) {
		final Cleaner cleanerLogin = this.cleanerService.getCleanerLogin();
		Assert.notNull(cleanerLogin, "No cleaner is login");
		Assert.isTrue(educationalData.getCurricula().getCleaner().equals(copy.getCleaner()));
		Assert.isTrue(cleanerLogin.equals(educationalData.getCurricula().getCleaner()), "Cleaner curricula is diferent to cleaner educationalData");
		final EducationalData educationalDataCopy = this.create();
		educationalDataCopy.setCurricula(copy);
		educationalDataCopy.setDegree(educationalData.getDegree());
		educationalDataCopy.setEndDate(educationalData.getEndDate());
		educationalDataCopy.setInstitution(educationalData.getInstitution());
		educationalDataCopy.setMark(educationalData.getMark());
		educationalDataCopy.setStartDate(educationalData.getStartDate());
		educationalDataCopy.setIsCopy(true);
		return educationalDataCopy;
	}

	public Boolean checkDate(final Date startDate, final Date endDate) {
		Boolean res = true;
		if (startDate.before(endDate))
			res = false;
		return res;
	}

	// AUXILIAR METHODS
}
