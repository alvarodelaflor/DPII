
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
import domain.Hacker;

@Service
@Transactional
public class EducationalDataService {

	@Autowired
	private EducationalDataRepository	educationalDataRepository;

	@Autowired
	private HackerService				hackerService;


	// CRUD Methods

	/**
	 * Create a {@link EducationalData} instance<br>
	 * Must exist an {@link Hacker} login
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link EducationalData}
	 */
	public EducationalData create() {
		Assert.notNull(this.hackerService.getHackerLogin());
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
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		final Hacker hackerCurricula = this.hackerService.getHackerByCurriculaId(curricula);
		Assert.notNull(hackerCurricula, "No hacker for this curricula");
		Assert.isTrue(hackerCurricula.equals(hackerLogin), "Login and hacker curricula are diferent");
		final EducationalData res = new EducationalData();
		res.setCurricula(curricula);
		res.setIsCopy(false);
		return res;
	}

	/**
	 * Save a EducationalData given <br>
	 * 
	 * Must be a {@link Hacker} login and the owner of the educationalDate to save must be this hacker
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link EducationalData}
	 */
	public EducationalData save(final EducationalData educationalData) {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker login");
		Assert.notNull(educationalData, "Null educationalData");
		Assert.isTrue(hackerLogin.equals(educationalData.getCurricula().getHacker()), "Not allow to edit not own EducationalData");
		Assert.isTrue(!this.checkDate(educationalData.getStartDate(), educationalData.getEndDate()), "Not valid date configuaration");
		return this.educationalDataRepository.save(educationalData);
	}

	/**
	 * Save a collection of EducationalData given <br>
	 * 
	 * Must be a {@link Hacker} login and the owner of the educationalDate to save must be this hacker
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link EducationalData}<{@link EducationalData}>
	 */
	public Collection<EducationalData> savaAll(final Collection<EducationalData> educationalsData) {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(!educationalsData.isEmpty(), "Empty collection of educationalsData");
		Assert.isTrue(educationalsData.iterator().next().getCurricula().getHacker().equals(hackerLogin), "No valid hacker to save");
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
	 * Must be a {@link Hacker} login and the owner of the educationalDate to delete must be this hacker
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void delete(final EducationalData educationalData) {
		Assert.notNull(educationalData);
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(educationalData.getCurricula().getHacker().equals(hackerLogin), "Not allow to delete a educationalData of another hacker");
		this.educationalDataRepository.delete(educationalData);
	}

	/**
	 * Delete a Collection of EducationalData given <br>
	 * 
	 * Must be a {@link Hacker} login and the owner of the educationalDate to delete must be this hacker
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void deleteAll(final Collection<EducationalData> educationalDatas) {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin);
		Assert.isTrue(hackerLogin.equals(this.hackerService.getHackerByCurriculaId(((List<EducationalData>) educationalDatas).get(0).getCurricula())), "Diferent hackers");
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
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(origen.getIsCopy().equals(false) && copy.getIsCopy().equals(true), "Origen can not be copyMode and copy must be copyMode");
		Assert.isTrue(origen.getHacker().equals(copy.getHacker()), "Diferent hacker origen-copy");
		Assert.isTrue(origen.getHacker().equals(hackerLogin), "Diferent hacker origen-login");
		final List<EducationalData> educationalsData = (List<EducationalData>) this.educationalDataRepository.getEducationalDataFromCurriculaId(origen.getId());
		if (!educationalsData.isEmpty()) {
			final Collection<EducationalData> educationalsDataCopy = new ArrayList<>();
			for (final EducationalData educationalData : educationalsData)
				educationalsDataCopy.add(this.getCopy(educationalData, copy));
			this.savaAll(educationalsDataCopy);
		}
	}

	public EducationalData getCopy(final EducationalData educationalData, final Curricula copy) {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(educationalData.getCurricula().getHacker().equals(copy.getHacker()));
		Assert.isTrue(hackerLogin.equals(educationalData.getCurricula().getHacker()), "Hacker curricula is diferent to hacker educationalData");
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
