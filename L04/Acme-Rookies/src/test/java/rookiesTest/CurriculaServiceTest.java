
package rookiesTest;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CurriculaService;
import services.RookieService;
import utilities.AbstractTest;
import domain.Curricula;
import domain.Rookie;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private RookieService		rookieService;


	/*
	 * 14. The system must be easy to customise at run time, including banner, system message and country code
	 * //
	 * * //
	 * Analysis of sentence coverage
	 * 12.6%
	 * Analysis of data coverage
	 * ~10%
	 */
	@Test
	public void Diver01() {
		final Object testingData[][] = {
			{
				// Test positivo: create curricula
				null
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((Class<?>) testingData[i][0]);

	}

	//Ancillary methods------------------------------------------------------

	protected void Diver01(final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("calle rookie test");
			rookie.setCreditCard(null);
			rookie.setEmail("elcejas@rookie.com");
			rookie.setName("rookiename");
			rookie.setPhone("123456789");
			rookie.setSurname("rookiesurname");
			rookie.setVatNumber("dd33f");
			rookie.getUserAccount().setUsername("rookieuser");
			rookie.getUserAccount().setPassword("rookiepass");
			final Rookie saved = this.rookieService.saveCreate(rookie);

			super.authenticate("rookieuser");

			final Curricula curricula = this.curriculaService.create();
			curricula.setName("curriculatest");
			curricula.setIsCopy(false);
			curricula.setLinkGitHub("jose");
			curricula.setLinkLinkedin("http://www.prueba.com");
			curricula.setRookie(saved);
			curricula.setMiscellaneous("test");
			curricula.setPhone("695456123");
			curricula.setStatement("Test statement");
			this.curriculaService.save(curricula);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 17.1.
	 * . An actor who is authenticated as a rookie must be able to:
	 * Manage his or her curricula, which includes listing, showing, creating, updating, and
	 * deleting them.
	 */
	@Test
	public void Diver02() {
		final Object testingData[][] = {
			{
				// Negative Test: Non logged user creating a curricula
				//
				IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver02((Class<?>) testingData[i][0]);

	}

	//Ancillary methods------------------------------------------------------

	protected void Diver02(final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("calle rookie test");
			rookie.setCreditCard(null);
			rookie.setEmail("elcejas@rookie.com");
			rookie.setName("rookiename");
			rookie.setPhone("123456789");
			rookie.setVatNumber("dd33f");
			rookie.setSurname("rookiesurname");
			rookie.getUserAccount().setUsername("rookieuser");
			rookie.getUserAccount().setPassword("rookiepass");
			this.rookieService.saveCreate(rookie);

			final Curricula curricula = this.curriculaService.create();
			curricula.setName("curriculatest");
			curricula.setIsCopy(false);
			curricula.setLinkGitHub("jose");
			curricula.setLinkLinkedin("http://www.prueba.com");
			curricula.setRookie(rookie);
			curricula.setMiscellaneous("test");
			curricula.setPhone("695456123");
			curricula.setStatement("Test statement");
			this.curriculaService.save(curricula);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 17.1 An actor who is authenticated as a rookie must be able to:
	 * Manage his or her curricula, which includes listing, showing, creating, updating, and
	 * deleting them.
	 * //
	 * Analysis of sentence coverage
	 * 12.7%
	 * Analysis of data coverage
	 * ~18%
	 */
	@Test
	public void Diver03() {
		final Object testingData[][] = {
			{
				// // Positive Test: Rookies can list their curriculas
				//
				null
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver03((Class<?>) testingData[i][0]);

	}

	//Ancillary methods------------------------------------------------------

	protected void Diver03(final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("calle rookie test");
			rookie.setCreditCard(null);
			rookie.setEmail("elcejas@rookie.com");
			rookie.setName("rookiename");
			rookie.setPhone("123456789");
			rookie.setVatNumber("dd33f");

			rookie.setSurname("rookiesurname");
			rookie.getUserAccount().setUsername("rookieuser");
			rookie.getUserAccount().setPassword("rookiepass");
			final Rookie saved = this.rookieService.saveCreate(rookie);

			super.authenticate("rookieuser");

			final Curricula curricula = this.curriculaService.create();
			curricula.setName("curriculatest");
			curricula.setIsCopy(false);
			curricula.setLinkGitHub("jose");
			curricula.setLinkLinkedin("http://www.prueba.com");
			curricula.setRookie(saved);
			curricula.setMiscellaneous("test");
			curricula.setPhone("695456123");
			curricula.setStatement("Test statement");
			this.curriculaService.save(curricula);

			this.curriculaService.findAllByRookie(rookie);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 17.1 An actor who is authenticated as a rookie must be able to:
	 * Manage his or her curricula, which includes listing, showing, creating, updating, and
	 * deleting them.
	 * 12.7%
	 * Analysis of data coverage
	 * ~18%
	 */
	@Test
	public void Diver04() {
		final Object testingData[][] = {
			{
				// Negative Test: Non logged user trying to list another rookie curriculas
				//
				IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver04((Class<?>) testingData[i][0]);

	}

	//Ancillary methods------------------------------------------------------

	protected void Diver04(final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("calle rookie test");
			rookie.setCreditCard(null);
			rookie.setEmail("elcejas@rookie.com");
			rookie.setName("rookiename");
			rookie.setPhone("123456789");
			rookie.setVatNumber("dd33f");
			rookie.setSurname("rookiesurname");
			rookie.getUserAccount().setUsername("rookieuser");
			rookie.getUserAccount().setPassword("rookiepass");
			this.rookieService.saveCreate(rookie);

			final Curricula curricula = this.curriculaService.create();
			curricula.setIsCopy(false);
			curricula.setName("curriculatest");
			curricula.setLinkGitHub("jose");
			curricula.setLinkLinkedin("http://www.prueba.com");
			curricula.setRookie(rookie);
			curricula.setMiscellaneous("test");
			curricula.setPhone("695456123");
			curricula.setStatement("Test statement");
			this.curriculaService.save(curricula);

			this.curriculaService.findAllByRookie(rookie);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 18 . An actor who is authenticated as an administrator must be able to:
	 * Display a dashboard with the following information:
	 * The minimum, the maximum, the average, and the standard deviation of the number of curricula per rookie.
	 * The minimum, the maximum, the average, and the standard deviation of the number of results in the finders
	 * // The ratio of empty versus non-empty finders.
	 * * * Analysis of sentence coverage
	 * 10.3%
	 * Analysis of data coverage
	 * ~15%
	 */
	@Test
	public void Diver05() {
		final Object testingData[][] = {
			{
				// // Positive Test: Only admins can display the dashboard stats
				//
				null
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver05((Class<?>) testingData[i][0]);

	}

	//Ancillary methods------------------------------------------------------

	protected void Diver05(final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate("admin");
			this.curriculaService.stddevNumberOfResultHistory();
			this.curriculaService.avgNumberOfResultHsitory();
			this.curriculaService.maxNumberOfResultHistory();
			this.curriculaService.minNumberOfResultHistory();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();

		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void Diver06() {
		final Object testingData[][] = {
			{
				// Negative Test:  Only admins can display the dashboard stats
				//
				IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver06((Class<?>) testingData[i][0]);

	}

	//Ancillary methods------------------------------------------------------

	protected void Diver06(final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			this.curriculaService.stddevNumberOfResultHistory();
			this.curriculaService.avgNumberOfResultHsitory();
			this.curriculaService.maxNumberOfResultHistory();
			this.curriculaService.minNumberOfResultHistory();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
