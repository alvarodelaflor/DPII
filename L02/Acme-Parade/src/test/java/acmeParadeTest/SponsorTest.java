
package acmeParadeTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.SponsorService;
import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorTest extends AbstractTest {

	@Autowired
	private SponsorService	sponsorService;


	@Test
	public void driver1() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				0, 0, 0, 0, null
			}, {
				0, 0, 0, 1, null
			}, {
				0, 0, 1, 0, null
			}, {
				0, 0, 1, 1, null
			}, {
				0, 1, 0, 0, null
			}, {
				0, 1, 0, 1, null
			}, {
				0, 1, 1, 0, null
			}, {
				0, 1, 1, 1, null
			}, {
				1, 0, 0, 0, null
			}, {
				1, 0, 0, 1, null
			}, {
				1, 0, 1, 0, null
			}, {
				1, 0, 1, 1, null
			}, {
				1, 1, 0, 0, null
			}, {
				1, 1, 0, 1, null
			}, {
				1, 1, 1, 0, null
			}, {
				1, 1, 1, 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testRegisterSponsor((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);

	}

	public void testRegisterSponsor(final int middleName, final int address, final int photo, final int phone, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Sponsor sponsorTest = this.sponsorService.create();
			sponsorTest.setName("Manuel");
			sponsorTest.setSurname("Salvat");
			sponsorTest.getUserAccount().setUsername("sponsorTest");

			final String password = "sponsorTest";
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			sponsorTest.getUserAccount().setPassword(hashPassword);

			sponsorTest.setEmail("sponsorTest@mail.com");

			if (middleName != 0)
				sponsorTest.setMiddleName("Toni");

			if (address != 0)
				sponsorTest.setAddress("Torre del campo");

			if (photo != 0)
				sponsorTest.setPhoto("https://album.mediaset.es/eimg/2019/03/18/uDtzWsNlq84HqgNBOLAdr5.jpg");

			if (phone != 0)
				sponsorTest.setPhone("666777888");

			final Sponsor sponsorSave = this.sponsorService.saveR(sponsorTest);
			this.sponsorService.flush();

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
			//	name, surname, userName, password, email
			{
				0, 0, 0, 0, 1, NullPointerException.class
			}, {
				0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 1, 1, NullPointerException.class
			}, {
				0, 0, 1, 0, 0, NullPointerException.class
			}, {
				0, 0, 1, 0, 1, NullPointerException.class
			}, {
				0, 0, 1, 1, 0, NullPointerException.class
			}, {
				0, 0, 1, 1, 1, NullPointerException.class
			}, {
				0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, NullPointerException.class
			}, {
				0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, NullPointerException.class
			}, {
				0, 1, 1, 0, 0, NullPointerException.class
			}, {
				0, 1, 1, 0, 1, NullPointerException.class
			}, {
				0, 1, 1, 1, 0, NullPointerException.class
			}, {
				0, 1, 1, 1, 1, NullPointerException.class
			}, {
				1, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 1, NullPointerException.class
			}, {
				1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 1, 1, NullPointerException.class
			}, {
				1, 0, 1, 0, 0, NullPointerException.class
			}, {
				1, 0, 1, 0, 1, NullPointerException.class
			}, {
				1, 0, 1, 1, 0, NullPointerException.class
			}, {
				1, 0, 1, 1, 1, NullPointerException.class
			}, {
				1, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, NullPointerException.class
			}, {
				1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, NullPointerException.class
			}, {
				1, 1, 1, 0, 0, NullPointerException.class
			}, {
				1, 1, 1, 0, 1, NullPointerException.class
			}, {
				1, 1, 1, 1, 0, NullPointerException.class
			}, {
				1, 1, 1, 1, 1, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testRegisterSponsorNegativeNull((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);

	}

	public void testRegisterSponsorNegativeNull(final int name, final int surName, final int userName, final int passwordd, final int email, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Sponsor sponsorTest = this.sponsorService.create();
			sponsorTest.setName("Manuel");
			sponsorTest.setSurname("Salvat");
			sponsorTest.getUserAccount().setUsername("sponsorTest");

			final String password = "sponsorTest";
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			sponsorTest.getUserAccount().setPassword(hashPassword);

			sponsorTest.setEmail("sponsorTest@mail.com");

			if (name != 0)
				sponsorTest.setName(null);

			if (surName != 0)
				sponsorTest.setSurname(null);

			if (passwordd != 0)
				sponsorTest.getUserAccount().setPassword(null);

			if (userName != 0)
				sponsorTest.getUserAccount().setUsername(null);

			if (email != 0)
				sponsorTest.setEmail(null);

			final Sponsor sponsorSave = this.sponsorService.saveR(sponsorTest);
			this.sponsorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}