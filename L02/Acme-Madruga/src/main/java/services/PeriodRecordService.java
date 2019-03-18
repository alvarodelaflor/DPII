
package services;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import domain.Brotherhood;
import domain.History;
import domain.PeriodRecord;
import repositories.PeriodRecordRepository;
import security.LoginService;

/*
 * CONTROL DE CAMBIOS PositionService.java
 * 
 * ALVARO 18/02/2019 09:22 CREACIÓN DE LA CLASE
 */

@Service
@Transactional
public class PeriodRecordService {

	//Managed Repository -------------------	
	@Autowired
	private PeriodRecordRepository		periodRecordRepository;

	//Supporting services ------------------
	@Autowired
	private BrotherhoodService brotherhoodService;
	
	@Autowired
	private HistoryService historyService;

	//Simple CRUD Methods ------------------

	public PeriodRecord create() {
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		PeriodRecord periodRecord = new PeriodRecord();
		return periodRecord;

	}

	public Collection<PeriodRecord> findAll() {
		return this.periodRecordRepository.findAll();
	}

	public PeriodRecord findOne(final int id) {
		PeriodRecord periodRecord = this.periodRecordRepository.findOne(id);
		return periodRecord;
	}
	
	public Boolean checkPhotos(String photos) {
		Boolean res = true;
		try {
			if (photos.contains("'")) {
				System.out.println("Son multiples fotos");
				List<String> photosC = Arrays.asList(photos.split("'"));
				for (String photo : photosC) {
					new URL(photo).toURI();	
				}
			} else {
				System.out.println("Es una única foto");
				new URL(photos).toURI();
			}			
		} catch (Exception e) {
			System.out.println("No es una foto");
			res = false;
		}

		return res;
	}
	
	public PeriodRecord save(final PeriodRecord periodRecord) {
		Assert.notNull(periodRecord, "periodRecordSaveService.null");
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		Assert.notNull(brotherhood.getHistory().getInceptionRecord(), "Brotherhood have not got an inception record");
		PeriodRecord periodRecordSaved;
		// Assert periodRecord owner is the same that brotherhood logger
		PeriodRecord periodRecordFromDB = this.periodRecordRepository.findOne(periodRecord.getId());
		History history = this.historyService.findHistoryByBrotherhood(brotherhood.getId());
		if (brotherhood!=null && brotherhood.getHistory()!=null && !brotherhood.getHistory().getPeriodRecord().isEmpty() && periodRecordFromDB!=null) {
			List<PeriodRecord> periodRecordLogger = (List<PeriodRecord>)brotherhood.getHistory().getPeriodRecord();
			Assert.isTrue(periodRecordLogger.contains(periodRecordFromDB), "periodRecordServiceSave.diferentBrotherhoodLogger");
			history.getPeriodRecord().remove(periodRecordFromDB);
		} 
		periodRecordSaved = this.periodRecordRepository.save(periodRecord);
		history.getPeriodRecord().add(periodRecordSaved);
		this.historyService.save(history);
		return periodRecordSaved;
	}

	public void delete(final PeriodRecord periodRecord) {
		Assert.notNull(periodRecord, "periodRecord.null");
		Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		PeriodRecord periodRecordFromDB = this.periodRecordRepository.findOne(periodRecord.getId());
		List<PeriodRecord> periodRecordLogger = (List<PeriodRecord>)brotherhood.getHistory().getPeriodRecord();
		Assert.isTrue(periodRecordLogger.contains(periodRecordFromDB), "periodRecordServiceDelete.diferentBrotherhoodLogger");
		brotherhood.getHistory().getPeriodRecord().remove(periodRecordFromDB);
		this.periodRecordRepository.delete(periodRecord);
		this.historyService.save(brotherhood.getHistory());
	}
}
