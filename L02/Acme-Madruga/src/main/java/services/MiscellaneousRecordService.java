
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.History;
import domain.MiscellaneousRecord;

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
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	//Supporting services ------------------
	@Autowired
	BrotherhoodService						brotherhoodService;

	@Autowired
	HistoryService							historyService;


	//Simple CRUD Methods ------------------

	public MiscellaneousRecord create() {
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		final MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		final History history = this.historyService.findHistoryByBrotherhood(brotherhood.getId());
		miscellaneousRecord.setHistory(history);
		return miscellaneousRecord;

	}

	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public MiscellaneousRecord findOne(final int id) {
		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordRepository.findOne(id);
		return miscellaneousRecord;
	}
	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord, "miscellaneousRecordSaveService.null");
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		Assert.notNull(brotherhood.getHistory().getInceptionRecord(), "Brotherhood have not got an inception record");
		MiscellaneousRecord miscellaneousRecordSaved;
		// Assert miscellaneousRecord owner is the same that brotherhood logger
		final MiscellaneousRecord miscellaneousRecordFromDB = this.miscellaneousRecordRepository.findOne(miscellaneousRecord.getId());
		final History history = this.historyService.findHistoryByBrotherhood(brotherhood.getId());
		if (brotherhood != null && brotherhood.getHistory() != null && !brotherhood.getHistory().getMiscellaneousRecord().isEmpty() && miscellaneousRecordFromDB != null) {
			final List<MiscellaneousRecord> miscellaneousRecordLogger = (List<MiscellaneousRecord>) brotherhood.getHistory().getMiscellaneousRecord();
			Assert.isTrue(miscellaneousRecordLogger.contains(miscellaneousRecordFromDB), "miscellaneousRecordServiceSave.diferentBrotherhoodLogger");
			history.getMiscellaneousRecord().remove(miscellaneousRecordFromDB);
		}
		miscellaneousRecord.setHistory(history);
		miscellaneousRecordSaved = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		history.getMiscellaneousRecord().add(miscellaneousRecordSaved);
		this.historyService.save(history);
		return miscellaneousRecordSaved;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null in delete mis");
		final MiscellaneousRecord miscellaneousRecordFromDB = this.miscellaneousRecordRepository.findOne(miscellaneousRecord.getId());
		final List<MiscellaneousRecord> miscellaneousRecordLogger = (List<MiscellaneousRecord>) brotherhood.getHistory().getMiscellaneousRecord();
		Assert.isTrue(miscellaneousRecordLogger.contains(miscellaneousRecordFromDB), "miscellaneousRecordServiceDelete.diferentBrotherhoodLogger");
		brotherhood.getHistory().getMiscellaneousRecord().remove(miscellaneousRecordFromDB);
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
		this.historyService.save(brotherhood.getHistory());
	}

	public void flush() {
		this.miscellaneousRecordRepository.flush();
	}
}
