
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LegalRecordRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.History;
import domain.LegalRecord;

/*
 * CONTROL DE CAMBIOS LegalRecordService.java
 * 
 * ALVARO 10/03/2019 13:38 CREACIÓN DE LA CLASE
 */

@Service
@Transactional
public class LegalRecordService {

	//Managed Repository -------------------	
	@Autowired
	private LegalRecordRepository	legalRecordRepository;

	//Supporting services ------------------
	@Autowired
	BrotherhoodService				brotherhoodService;

	@Autowired
	HistoryService					historyService;


	//Simple CRUD Methods ------------------

	public LegalRecord create() {
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		final LegalRecord legalRecord = new LegalRecord();
		return legalRecord;

	}

	public Collection<LegalRecord> findAll() {
		return this.legalRecordRepository.findAll();
	}

	public LegalRecord findOne(final int id) {
		final LegalRecord legalRecord = this.legalRecordRepository.findOne(id);
		return legalRecord;
	}
	public LegalRecord save(final LegalRecord legalRecord) {
		Assert.notNull(legalRecord, "legalRecordSaveService.null");
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		Assert.notNull(brotherhood.getHistory().getInceptionRecord(), "Brotherhood have not got an inception record");
		LegalRecord legalRecordSaved;
		// Assert legalRecord owner is the same that brotherhood logger
		final LegalRecord legalRecordFromDB = this.legalRecordRepository.findOne(legalRecord.getId());
		final History history = this.historyService.findHistoryByBrotherhood(brotherhood.getId());
		if (brotherhood != null && brotherhood.getHistory() != null && !brotherhood.getHistory().getLegalRecord().isEmpty() && legalRecordFromDB != null) {
			final List<LegalRecord> legalRecordLogger = (List<LegalRecord>) brotherhood.getHistory().getLegalRecord();
			Assert.isTrue(legalRecordLogger.contains(legalRecordFromDB), "legalRecordServiceSave.diferentBrotherhoodLogger");
			history.getLegalRecord().remove(legalRecordFromDB);
		}
		legalRecord.setHistory(history);
		legalRecordSaved = this.legalRecordRepository.save(legalRecord);
		history.getLegalRecord().add(legalRecordSaved);
		this.historyService.save(history);
		return legalRecordSaved;
	}

	public void delete(final LegalRecord legalRecord) {
		Assert.notNull(legalRecord, "legalRecord.null");
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		final LegalRecord legalRecordFromDB = this.legalRecordRepository.findOne(legalRecord.getId());
		final List<LegalRecord> legalRecordLogger = (List<LegalRecord>) brotherhood.getHistory().getLegalRecord();
		Assert.isTrue(legalRecordLogger.contains(legalRecordFromDB), "legalRecordServiceDelete.diferentBrotherhoodLogger");
		brotherhood.getHistory().getLegalRecord().remove(legalRecordFromDB);
		this.legalRecordRepository.delete(legalRecord);
		this.historyService.save(brotherhood.getHistory());
	}
}
