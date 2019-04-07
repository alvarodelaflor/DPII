
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
import domain.EducationalData;
import domain.Hacker;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	@Autowired
	private PositionDataRepository	positionDataRepository;

	@Autowired
	private HackerService			hackerService;


	// CRUD Methods

	public PositionData create() {
		return new PositionData();
	}

	/**
	 * Crete a new EducationalData with the given curricula
	 * 
	 * @return {@link EducationalData}
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

	public Collection<PositionData> savaAll(final Collection<PositionData> positionsData) {
		return this.positionDataRepository.save(positionsData);
	}

	public PositionData findOne(final int positionDataId) {
		return this.positionDataRepository.findOne(positionDataId);
	}

	public PositionData save(final PositionData positionData) {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker login");
		Assert.notNull(positionData, "Null positionData");
		Assert.isTrue(hackerLogin.equals(positionData.getCurricula().getHacker()), "Not allow to edit not own EducationalData");
		Assert.isTrue(!this.checkDate(positionData.getStartDate(), positionData.getEndDate()), "Not valid date configuaration");
		return this.positionDataRepository.save(positionData);
	}

	public void delete(final PositionData positionData) {
		Assert.notNull(positionData);
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.isTrue(positionData.getCurricula().getHacker().equals(hackerLogin), "Not allow to delete a educationalData of another hacker");
		this.positionDataRepository.delete(positionData);
	}

	public void deleteAll(final Collection<PositionData> positionDatas) {
		final Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin);
		Assert.isTrue(hackerLogin.equals(this.hackerService.getHackerByCurriculaId(((List<PositionData>) positionDatas).get(0).getCurricula())), "Diferent hackers");
		if (!positionDatas.isEmpty())
			this.positionDataRepository.delete(positionDatas);
	}

	// CRUD Methods

	// AUXILIAR METHODS

	/**
	 * @return The reconstruct curricula
	 * @author Alvaro de la Flor Bonilla
	 */
	public Collection<PositionData> getPositionDataFromCurricula(final Curricula curricula) {
		return this.positionDataRepository.getPositionDataFromCurricula(curricula.getId());
	}

	public void makeCopyAllPositionDataForCurricula(final Curricula origen, final Curricula copy) {
		final List<PositionData> positionsData = (List<PositionData>) this.positionDataRepository.getPositionDataFromCurricula(origen.getId());
		final Collection<PositionData> positionsDataCopy = new ArrayList<>();
		if (!positionsData.isEmpty()) {
			for (final PositionData positionData : positionsData)
				positionsDataCopy.add(this.getCopy(positionData, copy));
			this.savaAll(positionsDataCopy);
		}
	}

	public PositionData getCopy(final PositionData positionData, final Curricula copy) {
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
