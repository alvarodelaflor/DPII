
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import domain.Curricula;
import domain.PositionData;
import domain.Rookie;

@Service
@Transactional
public class PositionDataService {

	@Autowired
	private PositionDataRepository	positionDataRepository;

	@Autowired
	private RookieService			rookieService;

	@Autowired
	private CurriculaService		curriculumService;

	@Autowired
	private PositionService			positionService;


	// CRUD Methods

	/**
	 * Crete a new PositionData<br>
	 * Must exist a {@link Rookie} login and this rookie must have an curriculum and a valid position
	 * 
	 * @return {@link PositionData}
	 * @author Alvaro de la Flor Bonilla
	 */
	public PositionData create() {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(!this.curriculumService.findAllByRookie(rookieLogin).isEmpty(), "This rookie has not any curriculum in database");
		Assert.isTrue(!this.positionService.findValidPositionToCurriculaByRookieId(rookieLogin.getId()).isEmpty(), "This rookie has not any position with accepted application");
		return new PositionData();
	}

	/**
	 * Crete a new PositionData with the given curricula
	 * 
	 * @return {@link PositionData}
	 * @author Alvaro de la Flor Bonilla
	 */
	public PositionData createWithHistory(final Curricula curricula) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		final Rookie rookieCurricula = this.rookieService.getRookieByCurriculaId(curricula);
		Assert.notNull(rookieCurricula, "No rookie for this curricula");
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(rookieCurricula.equals(rookieLogin), "Login and rookie curricula are diferent");
		final PositionData res = new PositionData();
		res.setCurricula(curricula);
		res.setIsCopy(false);
		return res;
	}

	/**
	 * Save Collection of PositionData given<br>
	 * The rookie is trying to save must be the onwer of the positionsdata
	 * 
	 * @return {@link Collection}<{@link PositionData}>
	 * @author Alvaro de la Flor Bonilla
	 */
	public Collection<PositionData> savaAll(final Collection<PositionData> positionsData) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(!positionsData.isEmpty(), "Empty collection of positionsData");
		Assert.isTrue(positionsData.iterator().next().getCurricula().getRookie().equals(rookieLogin), "No valid rookie to save");
		return this.positionDataRepository.save(positionsData);
	}

	public PositionData findOne(final int positionDataId) {
		return this.positionDataRepository.findOne(positionDataId);
	}

	/**
	 * Save the position given<br>
	 * The rookie is trying to save must be the onwer of the positiondata
	 * 
	 * @return {@link PositionData}
	 * @author Alvaro de la Flor Bonilla
	 */
	public PositionData save(final PositionData positionData) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie login");
		Assert.notNull(positionData, "Null positionData");
		Assert.isTrue(rookieLogin.equals(positionData.getCurricula().getRookie()), "Not allow to edit not own EducationalData");
		if (positionData.getEndDate() != null)
			Assert.isTrue(!this.checkDate(positionData.getStartDate(), positionData.getEndDate()), "Not valid date configuaration");
		Assert.isTrue(this.positionService.findValidPositionToCurriculaByRookieId(rookieLogin.getId()).contains(positionData.getPosition()), "Postion of the PositionData not valid");
		return this.positionDataRepository.save(positionData);
	}

	/**
	 * Delete the position given<br>
	 * The rookie is trying to delete must be the onwer of the positiondata
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void delete(final PositionData positionData) {
		Assert.notNull(positionData);
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.isTrue(positionData.getCurricula().getRookie().equals(rookieLogin), "Not allow to delete a educationalData of another rookie");
		this.positionDataRepository.delete(positionData);
	}

	/**
	 * Delete the collection of position given<br>
	 * The rookie is trying to delete must be the onwer of the positiondata
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void deleteAll(final Collection<PositionData> positionDatas) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(!positionDatas.isEmpty(), "Empty collection of positionsData");
		Assert.isTrue(positionDatas.iterator().next().getCurricula().getRookie().equals(rookieLogin), "No valid rookie to save");
		if (!positionDatas.isEmpty())
			this.positionDataRepository.delete(positionDatas);
	}
	// CRUD Methods

	// AUXILIAR METHODS

	/**
	 * 
	 * Get all positionData from a Curricula
	 * 
	 * @return {@link Collection}<{@link PositionData}>
	 * @author Alvaro de la Flor Bonilla
	 */
	public Collection<PositionData> getPositionDataFromCurricula(final Curricula curricula) {
		return this.positionDataRepository.getPositionDataFromCurricula(curricula.getId());
	}

	//	/**
	//	 * 
	//	 * Get all positionData in not Copy mode from a Curricula
	//	 * 
	//	 * @return {@link Collection}<{@link PositionData}>
	//	 * @author Alvaro de la Flor Bonilla
	//	 */
	//	public Collection<PositionData> getPositionDataNotCopyFromCurricula(Curricula curricula) {
	//		return this.positionDataRepository.getPositionDataNotCopyFromCurricula(curricula.getId());
	//	}

	public void makeCopyAllPositionDataForCurricula(final Curricula origen, final Curricula copy) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(origen.getIsCopy().equals(false) && copy.getIsCopy().equals(true), "Origen can not be copyMode and copy must be copyMode");
		Assert.isTrue(origen.getRookie().equals(copy.getRookie()), "Diferent rookie origen-copy");
		Assert.isTrue(origen.getRookie().equals(rookieLogin), "Diferent rookie origen-login");
		final List<PositionData> positionsData = (List<PositionData>) this.positionDataRepository.getPositionDataFromCurricula(origen.getId());
		final Collection<PositionData> positionsDataCopy = new ArrayList<>();
		if (!positionsData.isEmpty()) {
			for (final PositionData positionData : positionsData)
				positionsDataCopy.add(this.getCopy(positionData, copy));
			this.savaAll(positionsDataCopy);
		}
	}

	public PositionData getCopy(final PositionData positionData, final Curricula copy) {
		final Rookie rookieLogin = this.rookieService.getRookieLogin();
		Assert.notNull(rookieLogin, "No rookie is login");
		Assert.isTrue(positionData.getCurricula().getRookie().equals(copy.getRookie()));
		Assert.isTrue(rookieLogin.equals(positionData.getCurricula().getRookie()), "Rookie curricula is diferent to rookie positionData");
		final PositionData positionDataCopy = this.create();
		positionDataCopy.setCurricula(copy);
		positionDataCopy.setDescription(positionData.getDescription());
		positionDataCopy.setEndDate(positionData.getEndDate());
		positionDataCopy.setPosition(positionData.getPosition());
		positionDataCopy.setStartDate(positionData.getStartDate());
		positionDataCopy.setTitle(positionData.getTitle());
		positionDataCopy.setIsCopy(true);
		return positionDataCopy;
	}

	public Boolean checkDate(final Date startDate, final Date endDate) {
		Boolean res = true;
		if (startDate.before(endDate))
			res = false;
		return res;
	}

	public void deleteAllByPosition(final int positionId) {
		final Collection<PositionData> positionDatas = this.positionDataRepository.getPositionDatasFromPosition(positionId);
		this.positionDataRepository.deleteInBatch(positionDatas);
	}

	// AUXILIAR METHODS
}
