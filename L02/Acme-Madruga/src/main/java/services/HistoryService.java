
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.HistoryRepository;
import domain.Brotherhood;
import domain.History;

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

	public History findHistoryByBrotherhood(final int brotherhoodId) {
		return this.historyRepository.findHistoryByBrotherhood(brotherhoodId);
	}

	public History findHistoryByInceptionRecordId(final int inceptionRecordId) {
		return this.historyRepository.findHistoryByInceptionRecordId(inceptionRecordId);
	}

	public History save(final History history) {
		return this.historyRepository.save(history);
	}

	public Collection<Brotherhood> findHistoryByBrotherhoodTitle(final String title) {
		return this.historyRepository.findHistoryByBrotherhoodTitle(title);
	}

	public Collection<Brotherhood> findHistoryByBrotherhoodName(final String name) {
		return this.historyRepository.findHistoryByBrotherhoodName(name);
	}

	public Collection<Brotherhood> findHistoryByBrotherhoodTitleAndName(final String title, final String name) {
		return this.historyRepository.findHistoryByBrotherhoodTitleAndName(title, name);
	}

	public History findOne(final int id) {
		return this.historyRepository.findOne(id);
	}

}
