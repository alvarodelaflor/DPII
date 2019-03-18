
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import domain.Brotherhood;
import domain.History;
import domain.LinkRecord;
import repositories.LinkRecordRepository;
import security.LoginService;

/*
 * CONTROL DE CAMBIOS LinkRecordService.java
 * 
 * ALVARO 10/03/2019 13:38 CREACIÓN DE LA CLASE
 */

@Service
@Transactional
public class LinkRecordService {

	//Managed Repository -------------------	
	@Autowired
	private LinkRecordRepository		linkRecordRepository;

	//Supporting services ------------------
	@Autowired
	BrotherhoodService brotherhoodService;
	
	@Autowired
	HistoryService historyService;

	//Simple CRUD Methods ------------------

	public LinkRecord create() {
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		LinkRecord linkRecord = new LinkRecord();
		return linkRecord;

	}

	public Collection<LinkRecord> findAll() {
		return this.linkRecordRepository.findAll();
	}

	public LinkRecord findOne(final int id) {
		LinkRecord linkRecord = this.linkRecordRepository.findOne(id);
		return linkRecord;
	}
	public LinkRecord save(final LinkRecord linkRecord) {
		Assert.notNull(linkRecord, "linkRecordSaveService.null");
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		Assert.notNull(brotherhood.getHistory().getInceptionRecord(), "Brotherhood have not got an inception record");
		LinkRecord linkRecordSaved;
		// Assert linkRecord owner is the same that brotherhood logger
		LinkRecord linkRecordFromDB = this.linkRecordRepository.findOne(linkRecord.getId());
		History history = this.historyService.findHistoryByBrotherhood(brotherhood.getId());
		if (brotherhood!=null && brotherhood.getHistory()!=null && !brotherhood.getHistory().getLinkRecord().isEmpty() && linkRecordFromDB!=null) {
			List<LinkRecord> linkRecordLogger = (List<LinkRecord>)brotherhood.getHistory().getLinkRecord();
			Assert.isTrue(linkRecordLogger.contains(linkRecordFromDB), "linkRecordServiceSave.diferentBrotherhoodLogger");
			history.getLinkRecord().remove(linkRecordFromDB);
		} 
		linkRecordSaved = this.linkRecordRepository.save(linkRecord);
		history.getLinkRecord().add(linkRecordSaved);
		this.historyService.save(history);
		return linkRecordSaved;
	}

	public void delete(final LinkRecord linkRecord) {
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		LinkRecord linkRecordFromDB = this.linkRecordRepository.findOne(linkRecord.getId());
		List<LinkRecord> linkRecordLogger = (List<LinkRecord>)brotherhood.getHistory().getLinkRecord();
		Assert.isTrue(linkRecordLogger.contains(linkRecordFromDB), "linkRecordServiceDelete.diferentBrotherhoodLogger");
		brotherhood.getHistory().getLinkRecord().remove(linkRecordFromDB);
		this.linkRecordRepository.delete(linkRecord);
		this.historyService.save(brotherhood.getHistory());
	}
}
