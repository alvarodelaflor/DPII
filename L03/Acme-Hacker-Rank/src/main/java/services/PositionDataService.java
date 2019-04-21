
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
import domain.Hacker;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	@Autowired
	private PositionDataRepository	positionDataRepository;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private CurriculaService		curriculumService;
	
	@Autowired
	private PositionService positionService;


	// CRUD Methods

	/**
	 * Crete a new PositionData<br>
	 * Must exist a {@link Hacker} login and this hacker must have an curriculum and a valid position
	 * 
	 * @return {@link PositionData}
	 * @author Alvaro de la Flor Bonilla
	 */
	public PositionData create() {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(!this.curriculumService.findAllByHacker(hackerLogin).isEmpty(), "This hacker has not any curriculum in database");
		Assert.isTrue(!this.positionService.findValidPositionToCurriculaByHackerId(hackerLogin.getId()).isEmpty(), "This hacker has not any position with accepted application");
		return new PositionData();
	}

	/**
	 * Crete a new PositionData with the given curricula
	 * 
	 * @return {@link PositionData}
	 * @author Alvaro de la Flor Bonilla
	 */
	public PositionData createWithHistory(final Curricula curricula) {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		final Hacker hackerCurricula = this.hackerService.getHackerByCurriculaId(curricula);
		Assert.notNull(hackerCurricula, "No hacker for this curricula");
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(hackerCurricula.equals(hackerLogin), "Login and hacker curricula are diferent");
		final PositionData res = new PositionData();
		res.setCurricula(curricula);
		res.setIsCopy(false);
		return res;
	}

	/**
	 * Save Collection of PositionData given<br>
	 * The hacker is trying to save must be the onwer of the positionsdata
	 * 
	 * @return {@link Collection}<{@link PositionData}>
	 * @author Alvaro de la Flor Bonilla
	 */
	public Collection<PositionData> savaAll(final Collection<PositionData> positionsData) {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(!positionsData.isEmpty(), "Empty collection of positionsData");
		Assert.isTrue(positionsData.iterator().next().getCurricula().getHacker().equals(hackerLogin), "No valid hacker to save");
		return this.positionDataRepository.save(positionsData);
	}

	public PositionData findOne(final int positionDataId) {
		return this.positionDataRepository.findOne(positionDataId);
	}

	/**
	 * Save the position given<br>
	 * The hacker is trying to save must be the onwer of the positiondata
	 * 
	 * @return {@link PositionData}
	 * @author Alvaro de la Flor Bonilla
	 */
	public PositionData save(final PositionData positionData) {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker login");
		Assert.notNull(positionData, "Null positionData");
		Assert.isTrue(hackerLogin.equals(positionData.getCurricula().getHacker()), "Not allow to edit not own EducationalData");
		Assert.isTrue(!this.checkDate(positionData.getStartDate(), positionData.getEndDate()), "Not valid date configuaration");
		Assert.isTrue(this.positionService.findValidPositionToCurriculaByHackerId(hackerLogin.getId()).contains(positionData.getPosition()), "Postion of the PositionData not valid");
		return this.positionDataRepository.save(positionData);
	}

	/**
	 * Delete the position given<br>
	 * The hacker is trying to delete must be the onwer of the positiondata
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void delete(final PositionData positionData) {
		Assert.notNull(positionData);
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.isTrue(positionData.getCurricula().getHacker().equals(hackerLogin), "Not allow to delete a educationalData of another hacker");
		this.positionDataRepository.delete(positionData);
	}

	/**
	 * Delete the collection of position given<br>
	 * The hacker is trying to delete must be the onwer of the positiondata
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void deleteAll(final Collection<PositionData> positionDatas) {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(!positionDatas.isEmpty(), "Empty collection of positionsData");
		Assert.isTrue(positionDatas.iterator().next().getCurricula().getHacker().equals(hackerLogin), "No valid hacker to save");
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
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(origen.getIsCopy().equals(false) && copy.getIsCopy().equals(true), "Origen can not be copyMode and copy must be copyMode");
		Assert.isTrue(origen.getHacker().equals(copy.getHacker()), "Diferent hacker origen-copy");
		Assert.isTrue(origen.getHacker().equals(hackerLogin), "Diferent hacker origen-login");
		final List<PositionData> positionsData = (List<PositionData>) this.positionDataRepository.getPositionDataFromCurricula(origen.getId());
		final Collection<PositionData> positionsDataCopy = new ArrayList<>();
		if (!positionsData.isEmpty()) {
			for (final PositionData positionData : positionsData)
				positionsDataCopy.add(this.getCopy(positionData, copy));
			this.savaAll(positionsDataCopy);
		}
	}

	public PositionData getCopy(final PositionData positionData, final Curricula copy) {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(positionData.getCurricula().getHacker().equals(copy.getHacker()));
		Assert.isTrue(hackerLogin.equals(positionData.getCurricula().getHacker()), "Hacker curricula is diferent to hacker positionData");
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
