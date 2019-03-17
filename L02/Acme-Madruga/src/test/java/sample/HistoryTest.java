/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package sample;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import domain.LegalRecord;
import domain.PeriodRecord;
import services.LegalRecordService;
import services.PeriodRecordService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HistoryTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private PeriodRecordService		periodRecordService;
	
	@Autowired
	private LegalRecordService		legalRecordService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driverHistory() {
		final Object testingData[][] = {
			{ //CASO: Se crea una history sin InceptionRecord pero si con periodRecord
				"brotherhood", super.getEntityId("periodRecord1"), 0, IllegalArgumentException.class
			}, { //CASO: Se crea una history sin InceptionRecord pero si con legalRecord
				"brotherhood", 0, super.getEntityId("legalRecord1"), IllegalArgumentException.class
			}, { //CASO: Se crea una history sin InceptionRecord pero si con legalRecord y periodRecord
				"brotherhood", super.getEntityId("periodRecord1"), super.getEntityId("legalRecord1"), IllegalArgumentException.class
			// ÁLVARO
			}, { //CASO: Se añade una PeriodRecord y una legalRecord a una history
				"brotherhood2", super.getEntityId("periodRecord1"), super.getEntityId("legalRecord1"), null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templeteHistory((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templeteHistory(final String userName, final int periodRecordID, final int legalRecordID, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();
			super.authenticate(userName);

//			final Collection<PeriodRecord> periodRecords = new ArrayList<>();
//			periodRecords.add(this.periodRecordService.findOne(periodRecord));
			if (periodRecordID!=0) {
				PeriodRecord periodRecord = this.periodRecordService.create();
				periodRecord.setTitle("El título");
				periodRecord.setDescription("La descripción");
				periodRecord.setStartYear(2001);
				periodRecord.setEndYear(2015);
				periodRecord.setPhotos("https://www.myPhoto.com/idPhoto=543");
				this.periodRecordService.save(periodRecord);
			}
			
//			final Collection<LegalRecord> legalRecords = new ArrayList<>();
//			legalRecords.add(this.legalRecordService.findOne(legalRecord));
			
			if (legalRecordID!=0) {
				LegalRecord legalRecord = this.legalRecordService.create();
				legalRecord.setTitle("El título");
				legalRecord.setDescription("La descripción");
				legalRecord.setLegalName("Nombre Legal");
				legalRecord.setLaws("Leyes");
				legalRecord.setVatNumber("ES1234567B");
				this.legalRecordService.save(legalRecord);
			}
//			final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByName(userName);

//			final History history = brotherhood.getHistory();
//			history.setPeriodRecord(periodRecords);
//			history.setLegalRecord(legalRecords);

//			this.historyService.save(history);

//			this.historyService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

}
