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

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.BrotherhoodService;
import services.HistoryService;
import services.LegalRecordService;
import services.PeriodRecordService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.History;
import domain.LegalRecord;
import domain.PeriodRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HistoryTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private PeriodRecordService		periodRecordService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private LegalRecordService		legalRecordService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driverHistory() {
		final Object testingData[][] = {
			{ //CASO: Se crea una history sin InceptionRecord pero si con periodRecord
				"brotherhood", super.getEntityId("periodRecord1"), 0, null
			}, { //CASO: Se crea una history sin InceptionRecord pero si con legalRecord
				"brotherhood", 0, super.getEntityId("legalRecord1"), null
			}, { //CASO: Se crea una history sin InceptionRecord pero si con legalRecord y periodRecord
				"brotherhood", super.getEntityId("periodRecord1"), super.getEntityId("legalRecord1"), null
			// ÁLVARO
			}, { //CASO: Se añade una PeriodRecord y una legalRecord a una history
				"brotherhood2", super.getEntityId("periodRecord1"), super.getEntityId("legalRecord1"), null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templeteHistory((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templeteHistory(final String userName, final int periodRecord, final int legalRecord, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();
			super.authenticate(userName);

			final Collection<PeriodRecord> periodRecords = new ArrayList<>();
			periodRecords.add(this.periodRecordService.findOne(periodRecord));

			final Collection<LegalRecord> legalRecords = new ArrayList<>();
			legalRecords.add(this.legalRecordService.findOne(legalRecord));

			final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByName(userName);

			final History history = brotherhood.getHistory();
			history.setPeriodRecord(periodRecords);
			history.setLegalRecord(legalRecords);

			this.historyService.save(history);

			this.historyService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			System.out.println(oops.getClass());
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

}
