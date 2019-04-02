package services;

import java.util.ArrayList;

/**
 * PositionDataServie.java
 * 
 * @author Álvaro de la Flor Bonilla GitHub: alvar017
 * 
 * CONTROL:
 * 30/03/2019 16:35 Creation
 */

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import domain.Curricula;
import domain.Hacker;
import domain.PositionData;
import repositories.PositionDataRepository;

@Service
@Transactional
public class PositionDataService {

	@Autowired
	private PositionDataRepository positionDataRepository;
	
	@Autowired
	private HackerService hackerService;
	
	@Autowired
	private CurriculaService curriculumService;

	// CRUD Methods

	/**
	 * Crete a new PositionData<br>
	 * Must exist a {@link Hacker} login and this hacker must have an curriculum
	 * 
	 * @return {@link PositionData}
	 * @author Alvaro de la Flor Bonilla
	 */
	public PositionData create() {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(!this.curriculumService.findAllByHacker(hackerLogin).isEmpty(), "This hacker have not any curriculum in database");
		return new PositionData();
	}
	
	/**
	 * Crete a new PositionData with the given curricula
	 * 
	 * @return {@link PositionData}
	 * @author Alvaro de la Flor Bonilla
	 */
	public PositionData createWithHistory(Curricula curricula) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Hacker hackerCurricula = this.hackerService.getHackerByCurriculaId(curricula);
		Assert.notNull(hackerCurricula, "No hacker for this curricula");
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(hackerCurricula.equals(hackerLogin), "Login and hacker curricula are diferent");
		PositionData res = new PositionData();
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
	public Collection<PositionData> savaAll(Collection<PositionData> positionsData) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(!positionsData.isEmpty(), "Empty collection of positionsData");
		Assert.isTrue(positionsData.iterator().next().getCurricula().getHacker().equals(hackerLogin), "No valid hacker to save");
		return this.positionDataRepository.save(positionsData);
	}
	
	public PositionData findOne(int positionDataId) {
		return this.positionDataRepository.findOne(positionDataId);
	}
	
	/**
	 * Save the position given<br>
	 * The hacker is trying to save must be the onwer of the positiondata
	 * 
	 * @return {@link PositionData}
	 * @author Alvaro de la Flor Bonilla
	 */
	public PositionData save(PositionData positionData) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker login");
		Assert.notNull(positionData, "Null positionData");
		Assert.isTrue(hackerLogin.equals(positionData.getCurricula().getHacker()), "Not allow to edit not own EducationalData");
		Assert.isTrue(!checkDate(positionData.getStartDate(), positionData.getEndDate()), "Not valid date configuaration");
		return this.positionDataRepository.save(positionData);
	}
	
	/**
	 * Delete the position given<br>
	 * The hacker is trying to delete must be the onwer of the positiondata
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void delete(PositionData positionData) {
		Assert.notNull(positionData);
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.isTrue(positionData.getCurricula().getHacker().equals(hackerLogin), "Not allow to delete a educationalData of another hacker");
		this.positionDataRepository.delete(positionData);
	}

	/**
	 * Delete the collection of position given<br>
	 * The hacker is trying to delete must be the onwer of the positiondata
	 * 
	 * @author Alvaro de la Flor Bonilla
	 */
	public void deleteAll(Collection<PositionData> positionDatas) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(!positionDatas.isEmpty(), "Empty collection of positionsData");
		Assert.isTrue(positionDatas.iterator().next().getCurricula().getHacker().equals(hackerLogin), "No valid hacker to save");
		if (!positionDatas.isEmpty()) {
			this.positionDataRepository.delete(positionDatas);
		}
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
	public Collection<PositionData> getPositionDataFromCurricula(Curricula curricula) {
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

	public void makeCopyAllPositionDataForCurricula(Curricula origen, Curricula copy) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(origen.getIsCopy().equals(false) && copy.getIsCopy().equals(true), "Origen can not be copyMode and copy must be copyMode");
		Assert.isTrue(origen.getHacker().equals(copy.getHacker()), "Diferent hacker origen-copy");
		Assert.isTrue(origen.getHacker().equals(hackerLogin), "Diferent hacker origen-login");
		List<PositionData> positionsData = (List<PositionData>) this.positionDataRepository.getPositionDataFromCurricula(origen.getId());
		Collection<PositionData> positionsDataCopy = new ArrayList<>();
		if (!positionsData.isEmpty()) {
			for (PositionData positionData : positionsData) {
				positionsDataCopy.add(getCopy(positionData, copy));
			}
			this.savaAll(positionsDataCopy);			
		}
	}
	
	public PositionData getCopy(PositionData positionData, Curricula copy) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(positionData.getCurricula().getHacker().equals(copy.getHacker()));
		Assert.isTrue(hackerLogin.equals(positionData.getCurricula().getHacker()), "Hacker curricula is diferent to hacker positionData");
		PositionData positionDataCopy = this.create();
		positionDataCopy.setCurricula(copy);
		positionDataCopy.setDescription(positionData.getDescription());
		positionDataCopy.setEndDate(positionData.getEndDate());
		positionDataCopy.setPosition(positionData.getPosition());
		positionDataCopy.setStartDate(positionData.getStartDate());
		positionDataCopy.setTitle(positionData.getTitle());
		positionDataCopy.setIsCopy(true);
		return positionDataCopy;
	}
	
	public Boolean checkDate(Date startDate, Date endDate) {
		Boolean res = true;
		if (startDate.before(endDate)) {
			res = false;
		}
		return res;
	}

	// AUXILIAR METHODS
}
