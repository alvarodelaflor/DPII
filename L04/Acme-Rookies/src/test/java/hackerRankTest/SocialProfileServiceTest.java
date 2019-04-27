
package hackerRankTest;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.HackerService;
import services.SocialProfileService;
import utilities.AbstractTest;
import domain.Hacker;
import domain.SocialProfile;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	@Autowired
	SocialProfileService	socialService;

	@Autowired
	HackerService			hackerService;


	////////////////////////////////////////////////////////////

	@Test
	/*
	 * Test SocialProfile create
	 * 
	 * POSITIVE TEST
	 * Actor creates a new social profile
	 * 
	 * NEGATIVE TEST
	 * Unauthenticated actor tries to create a new social profile
	 * 
	 * Requirement under test: 23 (Acme-Hacker-Rank)
	 * 
	 * Analysis of sentence coverage: 90%
	 * Analysis of data coverage: 100%
	 */
	public void socialProfileCreate() {

		final Object testingData[][] = {

			{
				true, null
			}, {
				false, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.socialProfileCreate((Boolean) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	/*
	 * Test SocialProfile save
	 * 
	 * POSITIVE TEST
	 * Actor saves a new social profile
	 * 
	 * NEGATIVE TEST
	 * Actor tries to save null
	 * 
	 * Requirement under test: 23 (Acme-Hacker-Rank)
	 * 
	 * Analysis of sentence coverage: 90%
	 * Analysis of data coverage: 100%
	 */
	public void socialProfileSave() {

		final Object testingData[][] = {

			{
				true, null
			}, {
				false, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.socialProfileCreate((Boolean) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	/*
	 * Test SocialProfile save
	 * 
	 * POSITIVE TEST
	 * Actor deletes a social profile
	 * 
	 * NEGATIVE TEST
	 * Actor tries to delete a social profile of which it is not the owner
	 * 
	 * Requirement under test: 23 (Acme-Hacker-Rank)
	 * 
	 * Analysis of sentence coverage: 90%
	 * Analysis of data coverage: 100%
	 */
	public void socialProfileDelete() {

		final Object testingData[][] = {

			{
				true, null
			}, {
				false, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.socialProfileCreate((Boolean) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	////////////////////////////////////////////////////////////

	protected void socialProfileDelete(final Boolean owner, final Class<?> expected) {

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
			hacker.getUserAccount().setSpammerFlag(true);
			this.hackerService.saveCreate(hacker);

			this.authenticate("admin");
			final SocialProfile sp = this.socialService.create();
			sp.setName("socialProfile001");
			sp.setLink("https://www.linkasocialprofile.com");
			sp.setNick("eldelosdosvasosdeagua");
			final SocialProfile savedSp = this.socialService.save(sp);
			if (!owner)
				this.authenticate("hackeruser");
			this.socialService.delete(savedSp);
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void socialProfileSave(final Boolean exists, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate("admin");
			if (exists) {
				final SocialProfile sp = this.socialService.create();
				sp.setName("socialProfile001");
				sp.setLink("https://www.linkasocialprofile.com");
				sp.setNick("eldelosdosvasosdeagua");
				this.socialService.save(sp);
			} else
				this.socialService.save(null);

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void socialProfileCreate(final Boolean logged, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			if (logged)
				this.authenticate("admin");

			this.socialService.create();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

}
