
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Brotherhood;
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
	
	public Collection<History> findAll() {
		return this.historyRepository.findAll();
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
	
	public Collection<Brotherhood> findHistoryByBrotherhoodTitle(String title) {
		return this.findHistoryByBrotherhoodTitle(title);
	}
	
	public Collection<Brotherhood> findHistoryByBrotherhoodName(String name) {
		return this.findHistoryByBrotherhoodName(name);
	}
	
	public Collection<Brotherhood> findHistoryByBrotherhoodTitleAndName(String title, String name) {
		return this.findHistoryByBrotherhoodTitleAndName(title, name);
	}
}
