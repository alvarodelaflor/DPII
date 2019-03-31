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
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Curricula;
import domain.EducationalData;
import domain.Hacker;
import repositories.EducationalDataRepository;

@Service
@Transactional
public class EducationalDataService {

	@Autowired
	private EducationalDataRepository educationalDataRepository;
	
	@Autowired
	private HackerService hackerService;
	
	@Autowired
	private CurriculaService curriculaService;

	// CRUD Methods
	
	public EducationalData create() {
		Assert.notNull(this.hackerService.getHackerLogin());
		return new EducationalData();
	}
	
	/**
	 * Crete a new EducationalData with the given curricula
	 * 
	 * @return {@link EducationalData}
	 * @author Alvaro de la Flor Bonilla
	 */
	public EducationalData createWithHistory(Curricula curricula) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Hacker hackerCurricula = this.hackerService.getHackerByCurriculaId(curricula);
		Assert.notNull(hackerCurricula, "No hacker for this curricula");
		Assert.notNull(hackerLogin, "No hacker is login");
		Assert.isTrue(hackerCurricula.equals(hackerLogin), "Login and hacker curricula are diferent");
		EducationalData res = new EducationalData();
		res.setCurricula(curricula);
		res.setIsCopy(false);
		return res;
	}
	
	public EducationalData save(EducationalData educationalData) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin, "No hacker login");
		Assert.notNull(educationalData, "Null educationalData");
		Assert.isTrue(hackerLogin.equals(educationalData.getCurricula().getHacker()), "Not allow to edit not own EducationalData");
		return this.educationalDataRepository.save(educationalData);
	}
	
	public Collection<EducationalData> savaAll(Collection<EducationalData> educationalsData) {
		return this.educationalDataRepository.save(educationalsData);
	}
	
	public EducationalData findOne(int educationalDataId) {
		return this.educationalDataRepository.findOne(educationalDataId);
	}
	
	public void delete(EducationalData educationalData) {
		Assert.notNull(educationalData);
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.isTrue(educationalData.getCurricula().getHacker().equals(hackerLogin), "Not allow to delete a educationalData of another hacker");
		this.educationalDataRepository.delete(educationalData);
	}
	
	public void deleteAll(Collection<EducationalData> educationalDatas) {
		Hacker hackerLogin = this.hackerService.getHackerLogin();
		Assert.notNull(hackerLogin);
		Assert.isTrue(hackerLogin.equals(this.hackerService.getHackerByCurriculaId(((List<EducationalData>) educationalDatas).get(0).getCurricula())), "Diferent hackers");
		this.educationalDataRepository.delete(educationalDatas);
	}
	
	
	
	// CRUD Methods
	
	// AUXILIAR METHODS
	
	/**
	 * @return The reconstruct curricula
	 * @author Alvaro de la Flor Bonilla
	 */
	public Collection<EducationalData> getEducationalDataFromCurricula(Curricula curricula) {
		return this.educationalDataRepository.getEducationalDataFromCurriculaId(curricula.getId());
	}
	
	public void makeCopyAllEducationalDataForCurricula(Curricula origen, Curricula copy) {
		List<EducationalData> educationalsData = (List<EducationalData>) this.educationalDataRepository.getEducationalDataFromCurriculaId(origen.getId());
		if (!educationalsData.isEmpty()) {
			Collection<EducationalData> educationalsDataCopy = new ArrayList<>();
			for (EducationalData educationalData : educationalsData) {
				educationalsDataCopy.add(getCopy(educationalData, copy));
			}
			this.savaAll(educationalsDataCopy);	
		}
	}
	
	public EducationalData getCopy(EducationalData educationalData, Curricula copy) {
		EducationalData educationalDataCopy = this.create();
		educationalDataCopy.setCurricula(copy);
		educationalDataCopy.setDegree(educationalData.getDegree());
		educationalDataCopy.setEndDate(educationalData.getEndDate());
		educationalDataCopy.setInstitution(educationalData.getInstitution());
		educationalDataCopy.setMark(educationalData.getMark());
		educationalDataCopy.setStartDate(educationalData.getStartDate());
		educationalDataCopy.setIsCopy(true);
		return educationalDataCopy;
	}

	// AUXILIAR METHODS
}
