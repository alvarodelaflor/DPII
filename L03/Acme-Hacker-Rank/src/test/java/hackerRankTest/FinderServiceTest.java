
package hackerRankTest;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Finder;
import domain.Hacker;
import services.FinderService;
import services.HackerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService	finderService;

	@Autowired
	private HackerService	hackerService;


	/*
	 * 17.2 An actor who is authenticated as a hacker must be able to:
	 * Manage his or her finder, which involves updating the search criteria, listing its contents, and clearing it.
	 *
	 * Analysis of sentence coverage
	 * 12.6%
	 * Analysis of data coverage
	 * ~10%
	 */
	@Test
	public void Diver01() {
		final Object testingData[][] = {
			{
				// Test positivo: A hacker updates his finder's search criteria
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
			hacker.getUserAccount().setUsername("hackeruser");
			hacker.getUserAccount().setPassword("hackerpass");
			final Hacker saved = this.hackerService.saveCreate(hacker);

			super.authenticate("hackeruser");
			final Finder finder = saved.getFinder();
			final Finder newFinder = this.finderService.create();
			newFinder.setDeadline(finder.getDeadline());
			newFinder.setExpirationDate(finder.getExpirationDate());
			newFinder.setId(finder.getId());
			newFinder.setKeyword("test");
			newFinder.setMaxSalary(finder.getMaxSalary());
			newFinder.setMinSalary(finder.getMinSalary());
			newFinder.setPositions(finder.getPositions());
			newFinder.setVersion(finder.getVersion());

			this.finderService.save(newFinder);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 *
	 * 17.2 An actor who is authenticated as a hacker must be able to:
	 * Manage his or her finder, which involves updating the search criteria, listing its contents, and clearing it.
	 */
	@Test
	public void Diver02() {
		final Object testingData[][] = {
			{
				// Negative Test: User trying to update another hacker's finder
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
			hacker.setSurname("hackersurname");
			hacker.getUserAccount().setUsername("hackeruser");
			hacker.getUserAccount().setPassword("hackerpass");
			final Hacker saved = this.hackerService.saveCreate(hacker);

			final Finder finder = saved.getFinder();
			final Finder newFinder = this.finderService.create();
			newFinder.setDeadline(finder.getDeadline());
			newFinder.setExpirationDate(finder.getExpirationDate());
			newFinder.setId(finder.getId());
			newFinder.setKeyword("test");
			newFinder.setMaxSalary(finder.getMaxSalary());
			newFinder.setMinSalary(finder.getMinSalary());
			newFinder.setPositions(finder.getPositions());
			newFinder.setVersion(finder.getVersion());

			this.finderService.save(newFinder);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 17.2 An actor who is authenticated as a hacker must be able to:
	 * Manage his or her finder, which involves updating the search criteria, listing its contents, and clearing it.
	 * Analysis of sentence coverage
	 * 12.7%
	 * Analysis of data coverage
	 * ~18%
	 */
	@Test
	public void Diver03() {
		final Object testingData[][] = {
			{
				// // Positive Test: Listing hacker's finder results
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
			hacker.setSurname("hackersurname");
			hacker.getUserAccount().setUsername("hackeruser");
			hacker.getUserAccount().setPassword("hackerpass");
			final Hacker saved = this.hackerService.saveCreate(hacker);
			super.authenticate("hackeruser");

			final Finder finder = saved.getFinder();
			finder.setKeyword("techs");
			this.finderService.save(finder);
			finder.getPositions();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 17.2 An actor who is authenticated as a hacker must be able to:
	 * Manage his or her finder, which involves updating the search criteria, listing its contents, and clearing it.
	 * 12.7%
	 * Analysis of data coverage
	 * ~18%
	 */
	@Test
	public void Diver04() {
		final Object testingData[][] = {
			{
				// Negative Test: Non logged user trying to list hacker finder results
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
			hacker.setSurname("hackersurname");
			hacker.getUserAccount().setUsername("hackeruser");
			hacker.getUserAccount().setPassword("hackerpass");
			final Hacker saved = this.hackerService.saveCreate(hacker);

			final Finder finder = saved.getFinder();
			finder.setKeyword("techs");
			this.finderService.save(finder);
			finder.getPositions();
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
			this.finderService.stddevNumberOfResult();
			this.finderService.avgNumberOfResult();
			this.finderService.maxNumberOfResult();
			this.finderService.minNumberOfResult();
			this.finderService.ratioResult();

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

			this.finderService.stddevNumberOfResult();
			this.finderService.avgNumberOfResult();
			this.finderService.maxNumberOfResult();
			this.finderService.minNumberOfResult();
			this.finderService.ratioResult();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
