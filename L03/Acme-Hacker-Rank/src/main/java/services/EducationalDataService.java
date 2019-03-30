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

import domain.Curricula;
import domain.EducationalData;
import repositories.EducationalDataRepository;

@Service
@Transactional
public class EducationalDataService {

	@Autowired
	private EducationalDataRepository educationalDataRepository;

	// CRUD Methods
	
	public EducationalData create() {
		return new EducationalData();
	}
	
	public Collection<EducationalData> savaAll(Collection<EducationalData> educationalsData) {
		return this.educationalDataRepository.save(educationalsData);
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
