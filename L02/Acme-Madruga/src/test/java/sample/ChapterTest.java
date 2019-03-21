
package sample;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.Area;
import domain.Chapter;
import domain.Proclaim;
import services.AreaService;
import services.ChapterService;
import services.ProclaimService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChapterTest extends AbstractTest {

	// SUT:
	@Autowired
	private ChapterService	chapterService;

	// Auxiliar Services:
	@Autowired
	private ProclaimService	proclaimService;
	
	@Autowired
	private AreaService areaService;


	// Drivers:
	@Test
	public void chapterRegisterValidation() {

	}

	@Test
	public void chapterCreateProclaim() {

		final Object testingData[][] = {

			{
				"chapter", null
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.chapterCreateProclaim((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void chapterSaveProclaim() {

		this.authenticate("chapter");
		final Proclaim proclaim = this.proclaimService.create();
		this.unauthenticate();

		final Proclaim proclaimDB = this.proclaimService.findOne(758);

		final Object testingData[][] = {

			{
				"chapter", proclaim, null
			}, {
				"chapter", proclaimDB, ConstraintViolationException.class
			}, {
				"admin", proclaim, ConstraintViolationException.class
			}, {
				"chapter", null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.chapterSaveProclaim((String) testingData[i][0], (Proclaim) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Positives Tests:
	@Test
	public void registerAsChapterTestPos() {

		Class<?> caught = null;

		try {

			this.unauthenticate();
			this.chapterService.create();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		}

		super.checkExceptions(null, caught);
	}

	// Negatives Tests:

	// Abstract:
	protected void chapterCreateProclaim(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.authenticate(username);
			this.proclaimService.create();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);
	}

	protected void chapterSaveProclaim(final String username, final Proclaim proclaim, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.authenticate(username);
			this.proclaimService.save(proclaim);
		} catch (final Throwable oops) {

			caught = oops.getClass();

		} finally {

			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);
	}
	
	@Test
	public void test1() {
		/*
		 * In this test we will test the show and list of chapters and their areas and proclaims.
		 * 
		 * 	I. R.
		 * 
		 * 5.   There's a new kind of actor in the system: chapters. For every chapter, the system must store its title.
		 *      Every chapter co-ordinates and area and, thus, the parades organised by the brotherhoods in that area. 
		 *      No area can be co-ordinated by more than one chapter.
		 * 12.  Chapters can publish proclaims. For every proclaim, the system must store the moment when it's published 
		 * 		and a piece of text that can't be longer than 250 characters.	
		 * 
		 * F. R.
		 * 
		 * 1.  List the chapters that are registered in the system, navigate to the areas that they co-ordinate, to the 
		 * 	   brotherhoods that have settle in those areas, and to the parades that they organise.
		 * 
		 * 2.  Browse the proclaims of the chapters.
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
			this.checkTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	
	protected void checkTest(final String userName, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();
			
			if (userName!=null) {
				super.authenticate(userName);				
			}

			List<Chapter> chapters = this.chapterService.findAll();
			for (Chapter chapter : chapters) {
				chapter.getId();
				chapter.getVersion();
				chapter.getName();
				chapter.getSurname();
				chapter.getPhoto();
				chapter.getEmail();
				chapter.getMiddleName();
				chapter.getPhone();
				chapter.getTitle();
				chapter.getSocialProfiles();
			}
			for (Chapter chapter : chapters) {
				List<Proclaim> proclaims = (List<Proclaim>)  chapter.getProclaim();
				for (Proclaim proclaim : proclaims) {
					proclaim.getId();
					proclaim.getVersion();
					proclaim.getMoment();
					proclaim.getText();
				}
			}
			for (Chapter chapter : chapters) {
				Area area = this.areaService.findAreaChapter(chapter);
				area.getId();
				area.getVersion();
				area.getName();
				area.getPictures();
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
}
