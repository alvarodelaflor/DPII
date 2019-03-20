
package sample;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ChapterService;
import services.ProclaimService;
import utilities.AbstractTest;
import domain.Proclaim;

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
}
