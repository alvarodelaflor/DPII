
package unViaje;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import services.CleanerService;
import services.CustomerService;
import services.HostService;
import services.ValorationService;
import utilities.AbstractTest;
import domain.Cleaner;
import domain.Customer;
import domain.Host;
import domain.Valoration;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ValidationServiceTest extends AbstractTest {

	@Autowired
	private ValorationService	valorationService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HostService			hostService;

	@Autowired
	private CleanerService		cleanerService;


	@Test
	public void hostRateCustomer() {

		final Object testingData[][] = {

			{
				"hosthost", null
			// everything nice
			}, {
				"admin", NullPointerException.class
			// user is not host
			}, {
				"hackerhost", IllegalArgumentException.class
			// host is not owner 
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.hostRateCustomer((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void hostRateCleaner() {

		final Object testingData[][] = {

			{
				"hosthost", null
			// everything nice
			}, {
				"admin", NullPointerException.class
			// user is not host
			}, {
				"hackerhost", IllegalArgumentException.class
			// host is not owner 
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.hostRateCleaner((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected void hostRateCustomer(final String logged, final Class<?> expected) {

		Class<?> caught = null;
		try {

			this.startTransaction();

			this.authenticate("hosthost");
			Host actorLogged = this.hostService.getHostByUserAccountId(LoginService.getPrincipal().getId());
			List<Customer> customers = this.customerService.getCustomersByHostId(actorLogged.getId());
			final Customer customer = customers.get(0);
			this.unauthenticate();

			this.authenticate(logged);
			actorLogged = this.hostService.getHostByUserAccountId(LoginService.getPrincipal().getId());
			customers = this.customerService.getCustomersByHostId(actorLogged.getId());
			Assert.isTrue(customers.contains(customer));

			final Valoration val = this.valorationService.create();
			val.setHost(actorLogged);
			val.setCustomer(customer);
			val.setScore(3);
			this.valorationService.save(val);
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	protected void hostRateCleaner(final String logged, final Class<?> expected) {

		Class<?> caught = null;
		try {

			this.startTransaction();

			this.authenticate("cleaner");
			final Cleaner cleaner = this.cleanerService.getCleanerByUserAccountId(LoginService.getPrincipal().getId());
			this.unauthenticate();

			this.authenticate(logged);
			final Host actorLogged = this.hostService.getHostByUserAccountId(LoginService.getPrincipal().getId());
			Assert.isTrue(this.valorationService.checkValorationHostCleaner(actorLogged, cleaner));

			final Valoration val = this.valorationService.create();
			val.setHost(actorLogged);
			val.setCleaner(cleaner);
			val.setScore(3);
			this.valorationService.save(val);
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}
}
