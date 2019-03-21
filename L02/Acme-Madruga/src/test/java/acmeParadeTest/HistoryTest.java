/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package acmeParadeTest;

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

	@Autowired
	private PeriodRecordService		periodRecordService;
	
	@Autowired
	private LegalRecordService		legalRecordService;
	
	@Autowired
	private LinkRecordService		linkRecordService;
	
	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;

	// Tests ------------------------------------------------------------------

	@Test
	public void test1() {
		/*
		 * In this test we will test the creation of records in a history that already had an inception Record.
		 * 
		 * 1. Brotherhoods can manage their histories. A history is composed of one inception record, ze-ro or more period records, zero or more legal records, 
		 *    zero or more link records, and zero or more miscellaneous records. For every record, the system must store its title and a piece of text that describes it. 
		 *    For every inception record, it must also store some photos; for every period record, it must also store a start year, an end year, and some photos; for every 
		 *    legal record, it must also store a legal name, a VAT number, and the applicable laws; for every link record, it must also store a link to another brotherhood 
		 *    with which the original brotherhood is linked.
		 *    
		 *    3. An actor who is authenticated as a brotherhood must be able to:
		 *			1. Manage their history, which includes listing, displaying, creating, updating, and de-leting its records.
		 *
		 *	Analysis of sentence coverage 
		 *			TODO
		 *	Analysis of data coverage
		 *			TODO
		 *
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
		 * In this test we will test the creation of a linkRecord in a history that does not have a Record inception.
		 * 
		 * 		 * 1. Brotherhoods can manage their histories. A history is composed of one inception record, ze-ro or more period records, zero or more legal records, 
		 *    zero or more link records, and zero or more miscellaneous records. For every record, the system must store its title and a piece of text that describes it. 
		 *    For every inception record, it must also store some photos; for every period record, it must also store a start year, an end year, and some photos; for every 
		 *    legal record, it must also store a legal name, a VAT number, and the applicable laws; for every link record, it must also store a link to another brotherhood 
		 *    with which the original brotherhood is linked.
		 *    
		 *    3. An actor who is authenticated as a brotherhood must be able to:
		 *			1. Manage their history, which includes listing, displaying, creating, updating, and de-leting its records.
		 *
		 *	Analysis of sentence coverage 
		 *			TODO
		 *	Analysis of data coverage
		 *			TODO
		 *
		 */
		final Object testingData[][] = {
			// brotherhoodId, periodRecord, legalRecord, linkRecord, miscellaneousRecord 
			{ 
//				"brotherhood", 0, 0, 0, 1, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
				"brotherhood", 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"brotherhood", 0, 0, 1, 1, IllegalArgumentException.class
//			}, { 
//				"brotherhood", 0, 1, 0, 0, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
//				"brotherhood", 0, 1, 0, 1, IllegalArgumentException.class NOT LinkRecord created
			}, { 
				"brotherhood", 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, IllegalArgumentException.class
//			}, { 
//				"brotherhood", 1, 0, 0, 0, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
//				"brotherhood", 1, 0, 0, 1, IllegalArgumentException.class NOT LinkRecord created
			}, { 
				"brotherhood", 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, IllegalArgumentException.class
//			}, { 
//				"admin", 0, 0, 0, 1, IllegalArgumentException.class NOT LinkRecord created
//			}, {
			}, { 				
				"admin", 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"admin", 0, 0, 1, 1, IllegalArgumentException.class
//			}, { 
//				"admin", 0, 1, 0, 0, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
//				"admin", 0, 1, 0, 1, IllegalArgumentException.class NOT LinkRecord created
			}, { 
				"admin", 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 1, IllegalArgumentException.class
//			}, { 
//				"admin", 1, 0, 0, 0, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
//				"admin", 1, 0, 0, 1, IllegalArgumentException.class NOT LinkRecord created
			}, { 
				"admin", 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 1, IllegalArgumentException.class
			}, { 	
//				"member", 0, 0, 0, 1, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
				"member", 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"member", 0, 0, 1, 1, IllegalArgumentException.class
//			}, { 
//				"member", 0, 1, 0, 0, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
//				"member", 0, 1, 0, 1, IllegalArgumentException.class NOT LinkRecord created
			}, { 
				"member", 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 1, IllegalArgumentException.class
//			}, { 
//				"member", 1, 0, 0, 0, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
//				"member", 1, 0, 0, 1, IllegalArgumentException.class NOT LinkRecord created
			}, { 
				"member", 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 1, IllegalArgumentException.class
//			}, { 
//				"chapter", 0, 0, 0, 1, IllegalArgumentException.class NOT LinkRecord created
			}, { 				
				"chapter", 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"chapter", 0, 0, 1, 1, IllegalArgumentException.class
//			}, { 
//				"chapter", 0, 1, 0, 0, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
//				"chapter", 0, 1, 0, 1, IllegalArgumentException.class NOT LinkRecord created
			}, { 
				"chapter", 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 1, IllegalArgumentException.class
//			}, { 
//				"chapter", 1, 0, 0, 0, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
//				"chapter", 1, 0, 0, 1, IllegalArgumentException.class NOT LinkRecord created
			}, { 
				"chapter", 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 1, IllegalArgumentException.class		
			}, { 	
//				"sponsor", 0, 0, 0, 1, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
				"sponsor", 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"sponsor", 0, 0, 1, 1, IllegalArgumentException.class
//			}, { 
//				"sponsor", 0, 1, 0, 0, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
//				"sponsor", 0, 1, 0, 1, IllegalArgumentException.class NOT LinkRecord created
			}, { 
				"sponsor", 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 1, IllegalArgumentException.class
//			}, { 
//				"sponsor", 1, 0, 0, 0, IllegalArgumentException.class NOT LinkRecord created
//			}, { 
//				"sponsor", 1, 0, 0, 1, IllegalArgumentException.class NOT LinkRecord created
			}, { 
				"sponsor", 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 1, IllegalArgumentException.class
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
