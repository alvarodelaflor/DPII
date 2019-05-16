
package rookiesTest;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ProviderService;
import services.RookieService;
import services.SponsorshipService;
import utilities.AbstractTest;
import domain.Provider;
import domain.Rookie;
import domain.Sponsorship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private ProviderService		providerService;

	@Autowired
	private RookieService		rookieService;


	////////////////////////////////////////////////////////////////////////////////

	@Test
	public void sponsorshipProviderCreate() {

		final Object testingData[][] = {

			{
				"admin", NullPointerException.class
			}, {
				"provider", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.sponsorshipProviderCreate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void sponsorshipProviderSave() {

		final Object testingData[][] = {

			{
				"admin", NullPointerException.class
			}, {
				"provider", null
			}, {
				"rookie", NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.sponsorshipProviderSave((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void sponsorshipProviderDelete() {

		final Object testingData[][] = {

			{
				"admin", NullPointerException.class
			}, {
				"provider", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.sponsorshipProviderDelete((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	////////////////////////////////////////////////////////////////////////////////

	protected void sponsorshipProviderDelete(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			if (username != "admin") {

				final Provider provider = this.providerService.create();
				provider.setAddress("calle provider test");
				provider.setEmail("elcejas@provider.com");
				provider.setCommercialName("commercia");
				provider.setCreditCard(null);
				provider.setVatNumber("ES12345678e");
				provider.setName("providername");
				provider.setPhone("123456789");
				provider.setSurname("providersurname");
				provider.setVatNumber("dd33e");

				provider.getUserAccount().setUsername("provideruser");
				provider.getUserAccount().setPassword("providerpass");
				this.providerService.saveCreate(provider);
			}

			this.authenticate(username);
			final Sponsorship s = this.sponsorshipService.create();
			final Sponsorship ss = this.sponsorshipService.save(s);
			this.sponsorshipService.delete(ss.getId());
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	protected void sponsorshipProviderSave(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			if (username == "providersurname") {

				final Provider provider = this.providerService.create();
				provider.setAddress("calle provider test");
				provider.setEmail("elcejas@provider.com");
				provider.setCommercialName("commercia");
				provider.setCreditCard(null);
				provider.setVatNumber("ES12345678e");
				provider.setName("providername");
				provider.setPhone("123456789");
				provider.setSurname("providersurname");
				provider.setVatNumber("dd33e");

				provider.getUserAccount().setUsername("provideruser");
				provider.getUserAccount().setPassword("providerpass");
				this.providerService.saveCreate(provider);
			} else if (username == "rookiesurname") {

				final Rookie rookie = this.rookieService.create();
				rookie.setAddress("calle rookie test");
				rookie.setCreditCard(null);
				rookie.setEmail("elcejas@rookie.com");
				rookie.setName("rookiename");
				rookie.setPhone("123456789");
				rookie.setSurname("rookiesurname");
				rookie.setVatNumber("dd33e");

				rookie.getUserAccount().setUsername("rookieuser");
				rookie.getUserAccount().setPassword("rookiepass");
				this.rookieService.saveCreate(rookie);
			}

			this.authenticate(username);
			final Sponsorship s = this.sponsorshipService.create();
			this.sponsorshipService.save(s);
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	protected void sponsorshipProviderCreate(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			if (username != "admin") {

				final Provider provider = this.providerService.create();
				provider.setAddress("calle provider test");
				provider.setEmail("elcejas@provider.com");
				provider.setCommercialName("commercia");
				provider.setCreditCard(null);
				provider.setVatNumber("ES12345678e");
				provider.setName("providername");
				provider.setPhone("123456789");
				provider.setSurname("providersurname");
				provider.setVatNumber("dd33e");

				provider.getUserAccount().setUsername("provideruser");
				provider.getUserAccount().setPassword("providerpass");
				this.providerService.saveCreate(provider);
			}

			this.authenticate(username);
			this.sponsorshipService.create();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}
}
