
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Brotherhood;
import domain.History;
import domain.MiscellaneousRecord;
import repositories.MiscellaneousRecordRepository;
import security.LoginService;

/*
 * CONTROL DE CAMBIOS MiscellaneousRecordService.java
 * 
 * ALVARO 10/03/2019 13:38 CREACIÓN DE LA CLASE
 */

@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed Repository -------------------	
	@Autowired
	private MiscellaneousRecordRepository		miscellaneousRecordRepository;

	//Supporting services ------------------
	@Autowired
	BrotherhoodService brotherhoodService;
	
	@Autowired
	HistoryService historyService;

	//Simple CRUD Methods ------------------

	public MiscellaneousRecord create() {
		MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		return miscellaneousRecord;

	}

	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public MiscellaneousRecord findOne(final int id) {
		MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordRepository.findOne(id);
		return miscellaneousRecord;
	}
	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord, "miscellaneousRecordSaveService.null");
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		MiscellaneousRecord miscellaneousRecordSaved;
		// Assert miscellaneousRecord owner is the same that brotherhood logger
		MiscellaneousRecord miscellaneousRecordFromDB = this.miscellaneousRecordRepository.findOne(miscellaneousRecord.getId());
		History history = this.historyService.findHistoryByBrotherhood(brotherhood.getId());
		if (brotherhood!=null && brotherhood.getHistory()!=null && !brotherhood.getHistory().getMiscellaneousRecord().isEmpty() && miscellaneousRecordFromDB!=null) {
			List<MiscellaneousRecord> miscellaneousRecordLogger = (List<MiscellaneousRecord>)brotherhood.getHistory().getMiscellaneousRecord();
			Assert.isTrue(miscellaneousRecordLogger.contains(miscellaneousRecordFromDB), "miscellaneousRecordServiceSave.diferentBrotherhoodLogger");
			history.getMiscellaneousRecord().remove(miscellaneousRecordFromDB);
		} 
		miscellaneousRecordSaved = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		history.getMiscellaneousRecord().add(miscellaneousRecordSaved);
		this.historyService.save(history);
		return miscellaneousRecordSaved;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		MiscellaneousRecord miscellaneousRecordFromDB = this.miscellaneousRecordRepository.findOne(miscellaneousRecord.getId());
		List<MiscellaneousRecord> miscellaneousRecordLogger = (List<MiscellaneousRecord>)brotherhood.getHistory().getMiscellaneousRecord();
		Assert.isTrue(miscellaneousRecordLogger.contains(miscellaneousRecordFromDB), "miscellaneousRecordServiceDelete.diferentBrotherhoodLogger");
		brotherhood.getHistory().getMiscellaneousRecord().remove(miscellaneousRecordFromDB);
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
		this.historyService.save(brotherhood.getHistory());
	}
}
