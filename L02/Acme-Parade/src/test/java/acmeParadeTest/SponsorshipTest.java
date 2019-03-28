
package acmeParadeTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ParadeService;
import services.SponsorService;
import services.SponsorshipService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Parade;
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

	@Autowired
	private ParadeService		paradeService;


	//Requirement        -> 16
	//Sentence coverage  -> 15%
	//Data Coverage      -> 20%
	@Test
	public void driver1() {
		final Object testingData[][] = {
			//	userNameSponsor,
			{
				0, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testListSponsorship((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testListSponsorship(final int sponsor, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Sponsor test = this.sponsorService.findOne(super.getEntityId("sponsor01"));

			final Collection<Sponsorship> sponsorships = test.getSponsorships();

			this.sponsorshipService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	//Requirement        -> 16
	//Sentence coverage  -> 15%
	//Data Coverage      -> 20%
	@Test
	public void driver2() {
		final Object testingData[][] = {
			//	userNameSponsor,
			{
				0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testListSponsorshipNegative((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testListSponsorshipNegative(final int sponsor, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Sponsor test = this.sponsorService.findOne(super.getEntityId("sponsor01"));

			final Collection<Sponsorship> sponsorships = test.getSponsorships();

			final List<Sponsorship> listSponsorships = new ArrayList<>();

			listSponsorships.addAll(sponsorships);

			Assert.isTrue(sponsorships.size() > 10);

			this.sponsorshipService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	//Requirement        -> 16
	//Sentence coverage  -> 35%
	//Data Coverage      -> 30%
	@Test
	public void driver4() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				0, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testRegisterSponsorship((int) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testRegisterSponsorship(final int id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			super.authenticate("sponsor");

			final Sponsorship sponsorshipTest = this.sponsorshipService.create();

			sponsorshipTest.setActive(true);
			sponsorshipTest.setBanner("http://google.es");
			sponsorshipTest.setTarget("test");

			final Sponsor test = this.sponsorService.findOne(super.getEntityId("sponsor01"));

			sponsorshipTest.setSponsor(test);

			final Parade parade = this.paradeService.findOne(this.getEntityId("parade01"));

			sponsorshipTest.setParade(parade);

			final CreditCard card = new CreditCard();
			card.setCVV("111");
			card.setMake("VISA");
			card.setNumber("1234567890987654");
			card.setHolder("try");
			final Date date = new Date(2030, 01, 22);
			card.setExpiration(date);

			sponsorshipTest.setCreditCard(card);

			final Sponsorship sponsorshipSave = this.sponsorshipService.save(sponsorshipTest);
			//		this.sponsorshipService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	//Requirement        -> 16
	//Bussiness rule     -> set like null required parameters
	//Sentence coverage  -> 15%
	//Data Coverage      -> 20%
	@Test
	public void driver3() {
		final Object testingData[][] = {
			//	banner, parade, cvv, make, sponsor
			{
				0, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				0, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				1, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 1, 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testRegisterSponsorshipNegative((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);

	}
	public void testRegisterSponsorshipNegative(final int banner, final int parade, final int cvv, final int make, final int sponsor, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			super.authenticate("sponsor");

			final Sponsorship sponsorshipTest = this.sponsorshipService.create();

			sponsorshipTest.setActive(true);
			sponsorshipTest.setTarget("test");

			final Sponsor test = this.sponsorService.findOne(super.getEntityId("sponsor01"));

			sponsorshipTest.setSponsor(test);

			final Parade paradeTest = this.paradeService.findOne(this.getEntityId("parade01"));

			sponsorshipTest.setParade(paradeTest);

			final CreditCard card = new CreditCard();
			card.setCVV("111");
			card.setMake("VISA");
			card.setNumber("1234567890987654");
			card.setHolder("try");
			final Date date = new Date(2030, 01, 22);
			card.setExpiration(date);
			sponsorshipTest.setCreditCard(card);

			sponsorshipTest.setBanner("http://google.es");

			if (banner != 0)
				sponsorshipTest.setBanner(null);
			if (parade != 0)
				sponsorshipTest.setParade(null);
			if (cvv != 0)
				sponsorshipTest.getCreditCard().setCVV(null);
			if (make != 0)
				sponsorshipTest.getCreditCard().setMake(null);
			if (sponsor != 0)
				sponsorshipTest.setSponsor(null);

			final Sponsorship sponsorshipSave = this.sponsorshipService.save(sponsorshipTest);
			this.sponsorshipService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	//Requirement        -> 16
	//Sentence coverage  -> 10%
	//Data Coverage      -> 20%
	@Test
	public void driver5() {
		final Object testingData[][] = {
			//	userNameSponsor,
			{
				"sponsorship01", null
			}, {
				"sponsorship02", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteSponsorship((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testDeleteSponsorship(final String sponsorship, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			super.authenticate("sponsor");

			final Sponsorship sponsorshipTest = this.sponsorshipService.findOne(this.getEntityId(sponsorship));

			if (sponsorship == "sponsorship01")
				Assert.isTrue(sponsorshipTest.getActive() == false);
			if (sponsorship == "sponsorship02")
				Assert.isTrue(sponsorshipTest.getActive() == true);

			this.sponsorshipService.delete(sponsorshipTest);

			if (sponsorship == "sponsorship01")
				Assert.isTrue(sponsorshipTest.getActive() == true);
			if (sponsorship == "sponsorship02")
				Assert.isTrue(sponsorshipTest.getActive() == false);

			this.sponsorshipService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	//Requirement        -> 16
	//Bussiness rule      -> Domain rule check
	//Sentence coverage  -> 15%
	//Data Coverage      -> 20%
	@Test
	public void driver6() {
		final Object testingData[][] = {
			//	userNameSponsor,
			{
				"sponsorship01", NullPointerException.class
			}, {
				"sponsorship02", NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDeleteSponsorshipNegative((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testDeleteSponsorshipNegative(final String sponsorship, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Sponsor test = this.sponsorService.create();
			final Sponsor sponsorTest = this.sponsorService.create();
			sponsorTest.setName("Manuel");
			sponsorTest.setSurname("Salvat");
			sponsorTest.getUserAccount().setUsername("sponsorTest");

			final String password = "sponsorTest";
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			sponsorTest.getUserAccount().setPassword(hashPassword);

			sponsorTest.setEmail("sponsorTest@mail.com");
			sponsorTest.setMiddleName("Toni");
			sponsorTest.setAddress("Torre del campo");
			sponsorTest.setPhoto("https://album.mediaset.es/eimg/2019/03/18/uDtzWsNlq84HqgNBOLAdr5.jpg");
			sponsorTest.setPhone("666777888");
			final Sponsor sponsorSave = this.sponsorService.saveR(sponsorTest);

			super.authenticate("sponsorTest");

			final Sponsorship sponsorshipTest = this.sponsorshipService.findOne(this.getEntityId(sponsorship));

			this.sponsorshipService.delete(sponsorshipTest);

			this.sponsorshipService.flush();
			this.sponsorService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}
}
