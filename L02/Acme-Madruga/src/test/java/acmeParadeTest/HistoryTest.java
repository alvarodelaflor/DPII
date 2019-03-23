/*
 * HistoryTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package acmeParadeTest;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;
import services.BrotherhoodService;
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

	@Autowired
	private PeriodRecordService		periodRecordService;
	
	@Autowired
	private LegalRecordService		legalRecordService;
	
	@Autowired
	private LinkRecordService		linkRecordService;
	
	@Autowired
	private BrotherhoodService brotherhoodService;
	
	@Autowired
	private InceptionRecordService inceptionRecordService;
	
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
		 * NEGATIVE TEST
		 * 
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
	
	@Test
	public void test3() {
		/*
		 * POSITIVE TEST
		 * 
		 * In this test we will test the show and list of brotherhoods and their histories and records
		 * 
		 * 
		 *  Information requirements
		 *  1. Brotherhoods can manage their histories. A history is composed of one inception record, ze-ro or more period records, 
		 *     zero or more legal records, zero or more link records, and zero or more miscellaneous records. For every record, the 
		 *     system must store its title and a piece of text that describes it. For every inception record, it must also store some photos; 
		 *     for every period record, it must also store a start year, an end year, and some photos; for every legal record, it must also store 
		 *     a legal name, a VAT number, and the applicable laws; for every link record, it must also store a link to another brotherhood 
		 *     with which the original brotherhood is linked.
		 *     
		 *  1. Display the history of every brotherhood that he or she can display.
		 *
		 *	Analysis of sentence coverage 
		 *			TODO
		 *	Analysis of data coverage
		 *			TODO
		 *
		 */
		final Object testingData[][] = {
			// username, error
			{ 
				null, null
			}, { 
				"admin", null
			}, { 
				"chapter", null				
			}, { 
				"brotherhood", null
			}, { 
				"member", null
			}, { 
				"sponsor", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTestPositive((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	
	protected void checkTestPositive(final String userName, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();
			
			if (userName!=null) {
				super.authenticate(userName);				
			}

			List<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			for (Brotherhood brotherhood : brotherhoods) {
				brotherhood.getId();
				brotherhood.getVersion();
				brotherhood.getName();
				brotherhood.getSurname();
				brotherhood.getPhoto();
				brotherhood.getEmail();
				brotherhood.getMiddleName();
				brotherhood.getPhone();
				brotherhood.getTitle();
				brotherhood.getSocialProfiles();
				History history = brotherhood.getHistory();
				if (history!=null) {
					InceptionRecord inceptionRecord = history.getInceptionRecord();
					if (inceptionRecord!=null) {
						inceptionRecord.getId();
						inceptionRecord.getVersion();
						inceptionRecord.getDescription();
						inceptionRecord.getPhotos();
						inceptionRecord.getTitle();
					}
					
					List<LegalRecord> legalrecords = (List<LegalRecord>) history.getLegalRecord();
					for (LegalRecord legalRecord : legalrecords) {
						if (legalRecord!=null) {
							legalRecord.getId();
							legalRecord.getVersion();
							legalRecord.getDescription();
							legalRecord.getLaws();
							legalRecord.getLegalName();
							legalRecord.getVatNumber();
							legalRecord.getTitle();
						}
					}
					
					List<LinkRecord> linkRecords = (List<LinkRecord>) history.getLinkRecord();
					for (LinkRecord linkRecord : linkRecords) {
						if (linkRecord!=null) {
							linkRecord.getId();
							linkRecord.getVersion();
							linkRecord.getDescription();
							linkRecord.getLink();
							linkRecord.getTitle();
						}
					}
					
					List<MiscellaneousRecord> miscellaneousRecords = (List<MiscellaneousRecord>) history.getMiscellaneousRecord();
					for (MiscellaneousRecord miscellaneousRecord : miscellaneousRecords) {
						if (miscellaneousRecord!=null) {
							miscellaneousRecord.getId();
							miscellaneousRecord.getVersion();
							miscellaneousRecord.getDescription();
							miscellaneousRecord.getTitle();
						}
					}
					
					List<PeriodRecord> periodRecords = (List<PeriodRecord>) history.getPeriodRecord();
					for (PeriodRecord periodRecord : periodRecords) {
						if (periodRecord!=null) {
							periodRecord.getId();
							periodRecord.getVersion();
							periodRecord.getDescription();
							periodRecord.getTitle();
							periodRecord.getStartYear();
							periodRecord.getEndYear();
							periodRecord.getPhotos();
						}
					}
				}
			}

			if (userName!=null) {
				super.unauthenticate();				
			}

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void test4() {
		/*
		 * NEGATIVE TEST
		 * 
		 * In this test we will test the show and list of brotherhoods and their histories and records.
		 * 
		 * 
		 *  Information requirements
		 *  1. Brotherhoods can manage their histories. A history is composed of one inception record, ze-ro or more period records, 
		 *     zero or more legal records, zero or more link records, and zero or more miscellaneous records. For every record, the 
		 *     system must store its title and a piece of text that describes it. For every inception record, it must also store some photos; 
		 *     for every period record, it must also store a start year, an end year, and some photos; for every legal record, it must also store 
		 *     a legal name, a VAT number, and the applicable laws; for every link record, it must also store a link to another brotherhood 
		 *     with which the original brotherhood is linked.
		 *     
		 *  1. Display the history of every brotherhood that he or she can display.
		 *
		 *	Analysis of sentence coverage 
		 *			TODO
		 *	Analysis of data coverage
		 *			TODO
		 *
		 */
		final Object testingData[][] = {
			// username, error
			{ 
				null, IndexOutOfBoundsException.class
			}, { 
				"admin", IndexOutOfBoundsException.class
			}, { 
				"chapter", IndexOutOfBoundsException.class				
			}, { 
				"brotherhood", IndexOutOfBoundsException.class
			}, { 
				"member", IndexOutOfBoundsException.class
			}, { 
				"sponsor", IndexOutOfBoundsException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTestNegative((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	
	protected void checkTestNegative(final String userName, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();
			
			if (userName!=null) {
				super.authenticate(userName);				
			}

			List<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			for (Brotherhood brotherhood : brotherhoods) {
				brotherhood.getId();
				brotherhood.getVersion();
				brotherhood.getName();
				brotherhood.getSurname();
				brotherhood.getPhoto();
				brotherhood.getEmail();
				brotherhood.getMiddleName();
				brotherhood.getPhone();
				brotherhood.getTitle();
				brotherhood.getSocialProfiles();
				History history = brotherhood.getHistory();
				if (history!=null) {
					InceptionRecord inceptionRecord = history.getInceptionRecord();
					if (inceptionRecord!=null) {
						inceptionRecord.getId();
						inceptionRecord.getVersion();
						inceptionRecord.getDescription();
						inceptionRecord.getPhotos();
						inceptionRecord.getTitle();
					}
					
					List<LegalRecord> legalrecords = (List<LegalRecord>) history.getLegalRecord();
					if (legalrecords.size()==0) {
						legalrecords.get(0).getId();
					}

					
					List<LinkRecord> linkRecords = (List<LinkRecord>) history.getLinkRecord();
					if (linkRecords.size()==0) {
						linkRecords.get(0).getId();
					}
					
					List<MiscellaneousRecord> miscellaneousRecords = (List<MiscellaneousRecord>) history.getMiscellaneousRecord();
					if (miscellaneousRecords.size()==0) {
						miscellaneousRecords.get(0).getId();
					}
					
					List<PeriodRecord> periodRecords = (List<PeriodRecord>) history.getPeriodRecord();
					if (periodRecords.size()==0) {
						periodRecords.get(0).getId();
					}
				}
			}

			if (userName!=null) {
				super.unauthenticate();				
			}

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void test5() {
		/*
		 * POSITIVE TEST
		 * 
		 * 	  In this test we will test edit a inceptionRecord.
		 * 
		 * 	  1. Brotherhoods can manage their histories. A history is composed of one inception record, ze-ro or more period records, zero or more legal records, 
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
			// brotherhoodId, ¿editTitle?, ¿editDescription?, ¿editPhotos? 
			{ 
				"brotherhood", 0, 0, 0, null
			}, { 
				"brotherhood", 0, 0, 1, null
			}, {  
				"brotherhood", 0, 1, 0, null
			}, { 
				"brotherhood", 0, 1, 1, null
			}, { 
				"brotherhood", 1, 0, 0, null
			}, { 
				"brotherhood", 1, 0, 1, null
			}, { 
				"brotherhood", 1, 1, 0, null
			}, { 
				"brotherhood", 1, 1, 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTestEditInception((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3],(Class<?>) testingData[i][4]);
	}

	protected void checkTestEditInception(final String userName, final int title, final int description, final int photos, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);
			
			InceptionRecord inceptionRecord = this.inceptionRecordService.create();
			inceptionRecord.setTitle("El título");
			inceptionRecord.setDescription("La descripción");
			inceptionRecord.setPhotos("https://www.myPhoto.com/idPhoto=4569");
			InceptionRecord inceptionRecordSaved = this.inceptionRecordService.save(inceptionRecord);
			
			if (title!=0) {
				inceptionRecordSaved.setTitle("Nuevo título");
			}
			
			if (description!=0) {
				inceptionRecord.setDescription("Nueva descripción");
			}
			
			if (photos!=0) {
				inceptionRecord.setPhotos("https://www.myPhoto.com/idePhoto=666");
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
	public void test6() {
		/*
		 * NEGATIVE TEST
		 * 
		 * 	  In this test we will test edit a inceptionRecord.
		 * 
		 * 	  1. Brotherhoods can manage their histories. A history is composed of one inception record, ze-ro or more period records, zero or more legal records, 
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
			// brotherhoodId, ¿malURL? ¿editTitle?, ¿editDescription?, ¿editPhotos? 
			{ 
				"brotherhood", 0, 0, 0, 0, 0, 0, 0, null
			}, { 
				"brotherhood", 0, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {  
				"brotherhood", 0, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 0, 0, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 0, 1, 0, 1, 1, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 0, 1, 1, 1, 1, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 0, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 0, 1, 1, 1, 1, 1, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {  
				"brotherhood", 0, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 0, 1, 1, 1, 1, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, 0, 1, 1, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 0, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"brotherhood", 1, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"brotherhood", 1, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood", 1, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {  
				"brotherhood2", 0, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 0, 0, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 0, 1, 0, 1, 1, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 0, 1, 1, 1, 1, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 0, 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 1, 0, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 0, 1, 1, 1, 1, 1, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {  
				"brotherhood2", 0, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 0, 1, 1, 1, 1, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 1, 0, 1, 1, 0, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 1, 1, 0, 1, 1, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, { 
				"brotherhood2", 0, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 0, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"brotherhood2", 1, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"brotherhood2", 1, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"brotherhood2", 1, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"admin", 0, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"admin", 0, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 0, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"admin", 1, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"admin", 1, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"admin", 1, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"member", 0, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"member", 0, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 0, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"member", 1, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"member", 1, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"member", 1, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"chapter", 0, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"chapter", 0, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 0, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"chapter", 1, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"chapter", 1, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"chapter", 1, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"sponsor", 0, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"sponsor", 0, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 0, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"sponsor", 1, 0, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {  
				"sponsor", 1, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, { 
				"sponsor", 1, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTestEditInceptionNegative((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (int) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	protected void checkTestEditInceptionNegative(final String userName, final int malUrl, final int titleBlank, final int descriptionBlank, final int photosBlank, final int titleNull, final int descriptionNull, final int photosNull, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);
			
			InceptionRecord inceptionRecordSaved;
			
			if (userName.equals("brotherhood")) {
				InceptionRecord inceptionRecord = this.inceptionRecordService.create();
				inceptionRecord.setTitle("El título");
				inceptionRecord.setDescription("La descripción");
				inceptionRecord.setPhotos("https://www.myPhoto.com/idPhoto=4569");
				inceptionRecordSaved = this.inceptionRecordService.save(inceptionRecord);
			} else {
				super.unauthenticate();
				super.authenticate("brotherhood");
				
				InceptionRecord inceptionRecord = this.inceptionRecordService.create();
				inceptionRecord.setTitle("El título");
				inceptionRecord.setDescription("La descripción");
				inceptionRecord.setPhotos("https://www.myPhoto.com/idPhoto=4569");
				inceptionRecordSaved = this.inceptionRecordService.save(inceptionRecord);				
				
				super.unauthenticate();
				super.authenticate(userName);
			}
			
			
			if (titleBlank!=0 ) {
				inceptionRecordSaved.setTitle("");
			}
			
			if (titleNull!=0) {
				inceptionRecordSaved.setTitle(null);
			}
			
			if (titleBlank!=0 && titleNull!=0 && ((photosBlank - photosNull)+(descriptionBlank - descriptionNull))!=0) {
				inceptionRecordSaved.setTitle("Título editado");
			}
			
			if (descriptionBlank!=0) {
				inceptionRecordSaved.setDescription("");
			}
			
			if (descriptionNull!=0) {
				inceptionRecordSaved.setDescription(null);
			}
			
			if (descriptionBlank!=0 && descriptionNull!=0 && ((titleBlank - titleNull)+(photosBlank - photosNull))!=0) {
				inceptionRecordSaved.setDescription("Descripción editada");
			}
			
			if (photosBlank!=0) {
				inceptionRecordSaved.setPhotos("");
			}
			
			if (photosNull!=0) {
				inceptionRecordSaved.setPhotos(null);
			}
			
			if (photosBlank!=0 && photosNull !=0 && ((titleBlank - titleNull)+(descriptionBlank - descriptionNull))!=0) {
				inceptionRecordSaved.setPhotos("https://www.myPhoto.com/idPhoto=43");
			}
			
			if (malUrl!=0) {
				inceptionRecordSaved.setPhotos("esto no es una URL");
			}
			
			this.inceptionRecordService.save(inceptionRecordSaved);
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
