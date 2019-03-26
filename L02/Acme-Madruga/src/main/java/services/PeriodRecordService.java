
package services;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PeriodRecordRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.History;
import domain.PeriodRecord;

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
	private PeriodRecordRepository	periodRecordRepository;

	//Supporting services ------------------
	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private HistoryService			historyService;


	//Simple CRUD Methods ------------------

	public PeriodRecord create() {
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		final PeriodRecord periodRecord = new PeriodRecord();
		return periodRecord;

	}

	public Collection<PeriodRecord> findAll() {
		return this.periodRecordRepository.findAll();
	}

	public PeriodRecord findOne(final int id) {
		final PeriodRecord periodRecord = this.periodRecordRepository.findOne(id);
		return periodRecord;
	}

	public Boolean checkPhotos(final String photos) {
		Boolean res = true;
		try {
			if (photos.contains("'")) {
				final List<String> photosC = Arrays.asList(photos.split("'"));
				for (final String photo : photosC)
					new URL(photo).toURI();
			} else
				new URL(photos).toURI();
		} catch (final Exception e) {
			res = false;
		}

		return res;
	}

	public PeriodRecord save(final PeriodRecord periodRecord) {
		Assert.notNull(periodRecord, "periodRecordSaveService.null");
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(brotherhood, "Brotherhood is null");
		Assert.notNull(brotherhood.getHistory().getInceptionRecord(), "Brotherhood have not got an inception record");
		Assert.isTrue(this.checkPhotos(periodRecord.getPhotos()), "Photos are not URLs");
		PeriodRecord periodRecordSaved;
		// Assert periodRecord owner is the same that brotherhood logger
		final PeriodRecord periodRecordFromDB = this.periodRecordRepository.findOne(periodRecord.getId());
		final History history = this.historyService.findHistoryByBrotherhood(brotherhood.getId());
		if (brotherhood != null && brotherhood.getHistory() != null && !brotherhood.getHistory().getPeriodRecord().isEmpty() && periodRecordFromDB != null) {
			final List<PeriodRecord> periodRecordLogger = (List<PeriodRecord>) brotherhood.getHistory().getPeriodRecord();
			Assert.isTrue(periodRecordLogger.contains(periodRecordFromDB), "periodRecordServiceSave.diferentBrotherhoodLogger");
			history.getPeriodRecord().remove(periodRecordFromDB);
		}
		periodRecord.setHistory(history);
		periodRecordSaved = this.periodRecordRepository.save(periodRecord);
		history.getPeriodRecord().add(periodRecordSaved);
		this.historyService.save(history);
		return periodRecordSaved;
	}

	public void delete(final PeriodRecord periodRecord) {
		Assert.notNull(periodRecord, "periodRecord.null");
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		final PeriodRecord periodRecordFromDB = this.periodRecordRepository.findOne(periodRecord.getId());
		final List<PeriodRecord> periodRecordLogger = (List<PeriodRecord>) brotherhood.getHistory().getPeriodRecord();
		Assert.isTrue(periodRecordLogger.contains(periodRecordFromDB), "periodRecordServiceDelete.diferentBrotherhoodLogger");
		brotherhood.getHistory().getPeriodRecord().remove(periodRecordFromDB);
		this.periodRecordRepository.delete(periodRecord);
		this.historyService.save(brotherhood.getHistory());
	}
}
