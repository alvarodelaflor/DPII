
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import domain.History;
import repositories.HistoryRepository;

/*
 * CONTROL DE CAMBIOS HistroyService.java
 * 
 * ALVARO 17/02/2019 12:02 CREACION DE LA CLASE
 */

@Service
public class HistoryService {

	//Managed Repository -------------------	

	@Autowired
	private HistoryRepository	historyRepository;

	//Supporting services ------------------


	//Simple CRUD Methods ------------------

	public History create() {

		final History history = new History();
		return history;
	}
	
	public History findHistoryByBrotherhood(int brotherhoodId) {
		return this.historyRepository.findHistoryByBrotherhood(brotherhoodId);
	}
	
	public History findHistoryByInceptionRecordId(int inceptionRecordId) {
		return this.historyRepository.findHistoryByInceptionRecordId(inceptionRecordId);
	}
	
	public History save(History history) {
		return this.historyRepository.save(history);
	}
}
