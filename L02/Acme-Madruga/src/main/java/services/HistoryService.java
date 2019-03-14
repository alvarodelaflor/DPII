
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Brotherhood;
import domain.History;
import repositories.HistoryRepository;
import security.LoginService;

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
	
	@Autowired
	private BrotherhoodService brotherhoodService;

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
		try {
			Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
			Assert.isTrue(brotherhood.getHistory().getId()==history.getId());	
		} catch (Exception e) {
			System.out.println("El usuario no esta registrado aún");
		}
		return this.historyRepository.save(history);
	}
	
	public Collection<Brotherhood> findHistoryByBrotherhoodTitle(String title) {
		return this.historyRepository.findHistoryByBrotherhoodTitle(title);
	}
	
	public Collection<Brotherhood> findHistoryByBrotherhoodName(String name) {
		return this.historyRepository.findHistoryByBrotherhoodName(name);
	}
	
	public Collection<Brotherhood> findHistoryByBrotherhoodTitleAndName(String title, String name) {
		return this.historyRepository.findHistoryByBrotherhoodTitleAndName(title, name);
	}
}
