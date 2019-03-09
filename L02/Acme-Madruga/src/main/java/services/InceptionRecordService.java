
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;
import repositories.InceptionRecordRepository;
import security.LoginService;

/*
 * CONTROL DE CAMBIOS PositionService.java
 * 
 * ALVARO 18/02/2019 09:22 CREACIÓN DE LA CLASE
 */

@Service
@Transactional
public class InceptionRecordService {

	//Managed Repository -------------------	
	@Autowired
	private InceptionRecordRepository		inceptionRecordRepository;

	//Supporting services ------------------
	@Autowired
	BrotherhoodService brotherhoodService;
	
	@Autowired
	HistoryService historyService;

	//Simple CRUD Methods ------------------

	public InceptionRecord create() {
		InceptionRecord inceptionRecord = new InceptionRecord();
		return inceptionRecord;

	}

	public Collection<InceptionRecord> findAll() {
		return this.inceptionRecordRepository.findAll();
	}

	public InceptionRecord findOne(final int id) {
		InceptionRecord inceptionRecord = this.inceptionRecordRepository.findOne(id);
		return inceptionRecord;
	}
	public InceptionRecord save(final InceptionRecord inceptionRecord) {
		Assert.notNull(inceptionRecord, "inceptionRecordSaveService.null");
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		InceptionRecord inceptionRecordSaved;
		// Assert inceptionRecord owner is the same that brotherhood logger
		if (brotherhood!=null && brotherhood.getHistory()!=null && brotherhood.getHistory().getInceptionRecord()!=null) {
			/*
			 *  En el caso de que el brotherhood tenga ya una inceptionRecord se comprueba que la id de la que se va a editar sea la 
			 *  misma que la que tiene el brotherhood logueado
			 */
			int idHistoryBrotherhoodLogger = brotherhood.getHistory().getId();
			History history = this.historyService.findHistoryByInceptionRecordId(inceptionRecord.getId());
			Assert.isTrue(idHistoryBrotherhoodLogger==history.getId(), "inceptionRecord.brotherhood.diferent");
		} 
		History history = this.historyService.findHistoryByBrotherhood(brotherhood.getId());
		inceptionRecordSaved = this.inceptionRecordRepository.save(inceptionRecord);
		history.setInceptionRecord(inceptionRecordSaved);
		return inceptionRecordSaved;
	}

	public void delete(final InceptionRecord inceptionRecord) {
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		History history = this.historyService.findHistoryByInceptionRecordId(inceptionRecord.getId());
		History historyBrotherhoodLogger = this.historyService.findHistoryByBrotherhood(brotherhood.getId());
		Assert.notNull(history, "history.null.inceptionRecordService.null");
		Assert.isTrue(historyBrotherhoodLogger.equals(history), "diferentBrotherhoodLogger.inceptionRecordService");
		this.inceptionRecordRepository.delete(inceptionRecord);
	}
}
