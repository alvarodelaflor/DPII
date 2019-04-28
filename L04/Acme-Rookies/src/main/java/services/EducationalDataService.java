
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
import domain.Rookie;

@Service
@Transactional
public class EducationalDataService {

	@Autowired
	private EducationalDataRepository	educationalDataRepository;

	@Autowired
	private RookieService				rookieService;


	// CRUD Methods

	/**
	 * Create a {@link EducationalData} instance<br>
	 * Must exist an {@link Rookie} login
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link EducationalData}
	 */
	public EducationalData create() {
		Assert.notNull(this.rookieService.getRookieLogin());
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
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		final Rookie rookieCurricula = this.rookieService.getRookieByCurriculaId(curricula);
		Assert.notNull(rookieCurricula, "No rookie for this curricula");
		Assert.isTrue(rookieCurricula.equals(rookieLogin), "Login and rookie curricula are diferent");
		final EducationalData res = new EducationalData();
		res.setCurricula(curricula);
		res.setIsCopy(false);
		return res;
	}

	/**
	 * Save a EducationalData given <br>
	 * 
	 * Must be a {@link Rookie} login and the owner of the educationalDate to save must be this rookie
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link EducationalData}
	 */
	public EducationalData save(final EducationalData educationalData) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie login");
		Assert.notNull(educationalData, "Null educationalData");
		Assert.isTrue(rookieLogin.equals(educationalData.getCurricula().getRookie()), "Not allow to edit not own EducationalData");
		Assert.isTrue(!this.checkDate(educationalData.getStartDate(), educationalData.getEndDate()), "Not valid date configuaration");
		return this.educationalDataRepository.save(educationalData);
	}

	/**
	 * Save a collection of EducationalData given <br>
	 * 
	 * Must be a {@link Rookie} login and the owner of the educationalDate to save must be this rookie
	 * 
	 * @author Alvaro de la Flor Bonilla
	 * @return {@link EducationalData}<{@link EducationalData}>
	 */
	public Collection<EducationalData> savaAll(final Collection<EducationalData> educationalsData) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(!educationalsData.isEmpty(), "Empty collection of educationalsData");
		Assert.isTrue(educationalsData.iterator().next().getCurricula().getRookie().equals(rookieLogin), "No valid rookie to save");
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
	 * Must be a {@link Rookie} login and the owner of the educationalDate to delete must be this rookie
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void delete(final EducationalData educationalData) {
		Assert.notNull(educationalData);
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(educationalData.getCurricula().getRookie().equals(rookieLogin), "Not allow to delete a educationalData of another rookie");
		this.educationalDataRepository.delete(educationalData);
	}

	/**
	 * Delete a Collection of EducationalData given <br>
	 * 
	 * Must be a {@link Rookie} login and the owner of the educationalDate to delete must be this rookie
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void deleteAll(final Collection<EducationalData> educationalDatas) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin);
		Assert.isTrue(rookieLogin.equals(this.rookieService.getRookieByCurriculaId(((List<EducationalData>) educationalDatas).get(0).getCurricula())), "Diferent rookies");
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
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(origen.getIsCopy().equals(false) && copy.getIsCopy().equals(true), "Origen can not be copyMode and copy must be copyMode");
		Assert.isTrue(origen.getRookie().equals(copy.getRookie()), "Diferent rookie origen-copy");
		Assert.isTrue(origen.getRookie().equals(rookieLogin), "Diferent rookie origen-login");
		final List<EducationalData> educationalsData = (List<EducationalData>) this.educationalDataRepository.getEducationalDataFromCurriculaId(origen.getId());
		if (!educationalsData.isEmpty()) {
			final Collection<EducationalData> educationalsDataCopy = new ArrayList<>();
			for (final EducationalData educationalData : educationalsData)
				educationalsDataCopy.add(this.getCopy(educationalData, copy));
			this.savaAll(educationalsDataCopy);
		}
	}

	public EducationalData getCopy(final EducationalData educationalData, final Curricula copy) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(educationalData.getCurricula().getRookie().equals(copy.getRookie()));
		Assert.isTrue(rookieLogin.equals(educationalData.getCurricula().getRookie()), "Rookie curricula is diferent to rookie educationalData");
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
