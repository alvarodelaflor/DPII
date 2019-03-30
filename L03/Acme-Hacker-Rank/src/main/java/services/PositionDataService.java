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
import domain.PositionData;
import repositories.PositionDataRepository;

@Service
@Transactional
public class PositionDataService {

	@Autowired
	private PositionDataRepository positionDataRepository;

	// CRUD Methods

	public PositionData create() {
		return new PositionData();
	}
	
	public Collection<PositionData> savaAll(Collection<PositionData> positionsData) {
		return this.positionDataRepository.save(positionsData);
	}
	
	// CRUD Methods
	
	// AUXILIAR METHODS
	
	/**
	 * @return The reconstruct curricula
	 * @author Alvaro de la Flor Bonilla
	 */
	public Collection<PositionData> getPositionDataFromCurricula(Curricula curricula) {
		return this.positionDataRepository.getPositionDataFromCurricula(curricula.getId());
	}

	public void makeCopyAllPositionDataForCurricula(Curricula origen, Curricula copy) {
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

	// AUXILIAR METHODS
}
