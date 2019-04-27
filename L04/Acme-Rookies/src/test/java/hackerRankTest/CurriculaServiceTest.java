
package hackerRankTest;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CurriculaService;
import services.HackerService;
import utilities.AbstractTest;
import domain.Curricula;
import domain.Hacker;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CurriculaServiceTest extends AbstractTest {

	@Autowired
	private CurriculaService	curriculaService;

	@Autowired
	private HackerService		hackerService;


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

			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("calle hacker test");
			hacker.setCreditCard(null);
			hacker.setEmail("elcejas@hacker.com");
			hacker.setName("hackername");
			hacker.setPhone("123456789");
			hacker.setSurname("hackersurname");
			hacker.setVatNumber("dd33f");
			hacker.getUserAccount().setUsername("hackeruser");
			hacker.getUserAccount().setPassword("hackerpass");
			final Hacker saved = this.hackerService.saveCreate(hacker);

			super.authenticate("hackeruser");

			final Curricula curricula = this.curriculaService.create();
			curricula.setName("curriculatest");
			curricula.setIsCopy(false);
			curricula.setLinkGitHub("jose");
			curricula.setLinkLinkedin("http://www.prueba.com");
			curricula.setHacker(saved);
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
	 * . An actor who is authenticated as a hacker must be able to:
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
			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("calle hacker test");
			hacker.setCreditCard(null);
			hacker.setEmail("elcejas@hacker.com");
			hacker.setName("hackername");
			hacker.setPhone("123456789");
			hacker.setVatNumber("dd33f");
			hacker.setSurname("hackersurname");
			hacker.getUserAccount().setUsername("hackeruser");
			hacker.getUserAccount().setPassword("hackerpass");
			this.hackerService.saveCreate(hacker);

			final Curricula curricula = this.curriculaService.create();
			curricula.setName("curriculatest");
			curricula.setIsCopy(false);
			curricula.setLinkGitHub("jose");
			curricula.setLinkLinkedin("http://www.prueba.com");
			curricula.setHacker(hacker);
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
	 * 17.1 An actor who is authenticated as a hacker must be able to:
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
				// // Positive Test: Hackers can list their curriculas
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
			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("calle hacker test");
			hacker.setCreditCard(null);
			hacker.setEmail("elcejas@hacker.com");
			hacker.setName("hackername");
			hacker.setPhone("123456789");
			hacker.setVatNumber("dd33f");

			hacker.setSurname("hackersurname");
			hacker.getUserAccount().setUsername("hackeruser");
			hacker.getUserAccount().setPassword("hackerpass");
			final Hacker saved = this.hackerService.saveCreate(hacker);

			super.authenticate("hackeruser");

			final Curricula curricula = this.curriculaService.create();
			curricula.setName("curriculatest");
			curricula.setIsCopy(false);
			curricula.setLinkGitHub("jose");
			curricula.setLinkLinkedin("http://www.prueba.com");
			curricula.setHacker(saved);
			curricula.setMiscellaneous("test");
			curricula.setPhone("695456123");
			curricula.setStatement("Test statement");
			this.curriculaService.save(curricula);

			this.curriculaService.findAllByHacker(hacker);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 17.1 An actor who is authenticated as a hacker must be able to:
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
				// Negative Test: Non logged user trying to list another hacker curriculas
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
			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("calle hacker test");
			hacker.setCreditCard(null);
			hacker.setEmail("elcejas@hacker.com");
			hacker.setName("hackername");
			hacker.setPhone("123456789");
			hacker.setVatNumber("dd33f");
			hacker.setSurname("hackersurname");
			hacker.getUserAccount().setUsername("hackeruser");
			hacker.getUserAccount().setPassword("hackerpass");
			this.hackerService.saveCreate(hacker);

			final Curricula curricula = this.curriculaService.create();
			curricula.setIsCopy(false);
			curricula.setName("curriculatest");
			curricula.setLinkGitHub("jose");
			curricula.setLinkLinkedin("http://www.prueba.com");
			curricula.setHacker(hacker);
			curricula.setMiscellaneous("test");
			curricula.setPhone("695456123");
			curricula.setStatement("Test statement");
			this.curriculaService.save(curricula);

			this.curriculaService.findAllByHacker(hacker);

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
	 * The minimum, the maximum, the average, and the standard deviation of the number of curricula per hacker.
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
