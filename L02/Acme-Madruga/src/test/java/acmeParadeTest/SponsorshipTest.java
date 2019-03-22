
package acmeParadeTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.SponsorService;
import services.SponsorshipService;
import utilities.AbstractTest;
import domain.Sponsor;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorshipTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;


	@Test
	public void driver1() {
		final Object testingData[][] = {
			//	userNameSponsor,
			{
				791, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testListSponsorship((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testListSponsorship(final int sponsor, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Sponsor sponsorActor = this.sponsorService.getSponsorByUserId(sponsor);

			final Collection<Sponsorship> sponsorships = sponsorActor.getSponsorships();

			this.sponsorshipService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver2() {
		final Object testingData[][] = {
			//	userNameSponsor,
			{
				791, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testListSponsorshipNegative((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testListSponsorshipNegative(final int sponsor, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Sponsor sponsorActor = this.sponsorService.getSponsorByUserId(sponsor);

			final Collection<Sponsorship> sponsorships = sponsorActor.getSponsorships();

			final List<Sponsorship> listSponsorships = new ArrayList<>();

			listSponsorships.addAll(sponsorships);

			Assert.isTrue(sponsorships.size() > 10);

			this.sponsorshipService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
