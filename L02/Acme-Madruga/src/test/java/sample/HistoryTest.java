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
import services.PeriodRecordService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;
import domain.PeriodRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HistoryTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private HistoryService		historyService;

	@Autowired
	private PeriodRecordService	periodRecordService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverHistory() {
		final Object testingData[][] = {
			{
				"brotherhood", null, super.getEntityId("periodRecord1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templeteHistory((String) testingData[i][0], (InceptionRecord) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templeteHistory(final String userName, final InceptionRecord inceptionRecord, final int periodRecord, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();
			super.authenticate(userName);

			final Collection<PeriodRecord> periodRecords = new ArrayList<>();
			periodRecords.add(this.periodRecordService.findOne(periodRecord));

			final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByName(userName);

			final History history = brotherhood.getHistory();
			history.setInceptionRecord(inceptionRecord);
			history.setPeriodRecord(periodRecords);

			this.historyService.save(history);

			this.historyService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

}
