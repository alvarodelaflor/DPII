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

import services.InceptionRecordService;
import utilities.AbstractTest;
import domain.InceptionRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class InceptionRecordTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private InceptionRecordService	inceptionRecordService;


	// Tests ------------------------------------------------------------------

	@Test
	public void driverInceptionRercord() {
		final Object testingData[][] = {
			{
				"brotherhood", "Hola caracola", "Carmen", "http://carmen", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templeteInceptionRecord((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templeteInceptionRecord(final String userName, final String description, final String title, final String photos, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();
			super.authenticate(userName);

			final InceptionRecord inceptionRecord = this.inceptionRecordService.create();
			inceptionRecord.setDescription(description);
			inceptionRecord.setPhotos(photos);
			inceptionRecord.setTitle(title);
			this.inceptionRecordService.save(inceptionRecord);

			this.inceptionRecordService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

}
