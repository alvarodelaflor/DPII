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
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;
import services.InceptionRecordService;
import services.LegalRecordService;
import services.LinkRecordService;
import services.MiscellaneousRecordService;
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
	
	@Autowired
	private LinkRecordService		linkRecordService;
	
	@Autowired
	private InceptionRecordService		inceptionRecordService;
	
	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;

	// Tests ------------------------------------------------------------------

	@Test
	public void driverHistory() {
		final Object testingData[][] = {
			{ //CASO: Se crea una history sin InceptionRecord pero si con periodRecord
				"brotherhood", super.getEntityId("periodRecord1"), 0, 0,IllegalArgumentException.class
			}, { //CASO: Se crea una history sin InceptionRecord pero si con legalRecord
				"brotherhood", 0, super.getEntityId("legalRecord1"), 0 ,IllegalArgumentException.class
			}, { //CASO: Se crea una history sin InceptionRecord pero si con legalRecord y periodRecord
				"brotherhood", super.getEntityId("periodRecord1"), super.getEntityId("legalRecord1"), 0, IllegalArgumentException.class
			// ÁLVARO
			}, { //CASO: Se añade una PeriodRecord y una legalRecord a una history
				"brotherhood2", super.getEntityId("periodRecord1"), super.getEntityId("legalRecord1"), 0,null
			}, { //CASO: Se añade una PeriodRecord y una legalRecord a una history
				"brotherhood", 0, 0, super.getEntityId("linkRecord1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templeteHistory((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3],(Class<?>) testingData[i][4]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void templeteHistory(final String userName, final int periodRecordID, final int legalRecordID, final int linkRecordID, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();
			super.authenticate(userName);

			if (periodRecordID!=0) {
				PeriodRecord periodRecord = this.periodRecordService.create();
				periodRecord.setTitle("El título");
				periodRecord.setDescription("La descripción");
				periodRecord.setStartYear(2001);
				periodRecord.setEndYear(2015);
				periodRecord.setPhotos("https://www.myPhoto.com/idPhoto=543");
				this.periodRecordService.save(periodRecord);
			}
			
			if (legalRecordID!=0) {
				LegalRecord legalRecord = this.legalRecordService.create();
				legalRecord.setTitle("El título");
				legalRecord.setDescription("La descripción");
				legalRecord.setLegalName("Nombre Legal");
				legalRecord.setLaws("Leyes");
				legalRecord.setVatNumber("ES1234567B");
				this.legalRecordService.save(legalRecord);
			}
			
			if (linkRecordID!=0) {
				LinkRecord linkRecord = this.linkRecordService.create();
				linkRecord.setTitle("El título");
				linkRecord.setDescription("La descripción");
				linkRecord.setLink("https://www.elEnlace.com/id=32534");
				this.linkRecordService.save(linkRecord);
			}


			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void test1() {
		/*
		 * In this test we will test the creation of records in a history that already had an inception Record.
		 */
		final Object testingData[][] = {
			// brotherhoodId, periodRecord, legalRecord, linkRecord, miscellaneousRecord 
			{ 
				"brotherhood2", 0, 0, 0, 1, null
			}, { 
				"brotherhood2", 0, 0, 1, 0, null
			}, { 
				"brotherhood2", 0, 0, 1, 1, null
			}, { 
				"brotherhood2", 0, 1, 0, 0, null
			}, { 
				"brotherhood2", 0, 1, 0, 1, null
			}, { 
				"brotherhood2", 0, 1, 1, 0, null
			}, { 
				"brotherhood2", 0, 1, 1, 1, null
			}, { 
				"brotherhood2", 1, 0, 0, 0, null
			}, { 
				"brotherhood2", 1, 0, 0, 1, null
			}, { 
				"brotherhood2", 1, 0, 1, 0, null
			}, { 
				"brotherhood2", 1, 0, 1, 1, null
			}, { 
				"brotherhood2", 1, 1, 0, 0, null
			}, { 
				"brotherhood2", 1, 1, 0, 1, null
			}, { 
				"brotherhood2", 1, 1, 1, 0, null
			}, { 
				"brotherhood2", 1, 1, 1, 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4],(Class<?>) testingData[i][5]);
	}
	
	@Test
	public void test2() {
		/*
		 * In this test we will test the creation of records in a history that already had an inception Record.
		 */
		final Object testingData[][] = {
			// brotherhoodId, periodRecord, legalRecord, linkRecord, miscellaneousRecord 
			{ 
				"brotherhood", 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 0, IllegalArgumentException.class
//			}, {  REPETIDO
//				"brotherhood", 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, IllegalArgumentException.class
//			}, { REPETIDO
//				"brotherhood", 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, IllegalArgumentException.class
//			}, { REPETIDO
//				"brotherhood", 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4],(Class<?>) testingData[i][5]);
	}

	protected void checkTest(final String userName, final int periodRecordID, final int legalRecordID, final int linkRecordID, final int miscellaneousRecordID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);
			
			if (periodRecordID!=0) {
				PeriodRecord periodRecord = this.periodRecordService.create();
				periodRecord.setTitle("El título");
				periodRecord.setDescription("La descripción");
				periodRecord.setStartYear(2001);
				periodRecord.setEndYear(2015);
				periodRecord.setPhotos("https://www.myPhoto.com/idPhoto=543");
				this.periodRecordService.save(periodRecord);
			}
			
			if (legalRecordID!=0) {
				LegalRecord legalRecord = this.legalRecordService.create();
				legalRecord.setTitle("El título");
				legalRecord.setDescription("La descripción");
				legalRecord.setLegalName("Nombre Legal");
				legalRecord.setLaws("Leyes");
				legalRecord.setVatNumber("ES1234567B");
				this.legalRecordService.save(legalRecord);
			}
			
			if (linkRecordID!=0) {
				LinkRecord linkRecord = this.linkRecordService.create();
				linkRecord.setTitle("El título");
				linkRecord.setDescription("La descripción");
				linkRecord.setLink("https://www.elEnlace.com/id=32534");
				this.linkRecordService.save(linkRecord);
			}
			
			if (miscellaneousRecordID!=0) {
				MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
				miscellaneousRecord.setTitle("El título");
				miscellaneousRecord.setDescription("La descripción");
				this.miscellaneousRecordService.save(miscellaneousRecord);
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
