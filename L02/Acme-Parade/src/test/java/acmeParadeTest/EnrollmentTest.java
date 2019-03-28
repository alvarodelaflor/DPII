
package acmeParadeTest;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.BrotherhoodService;
import services.EnrolledService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.Enrolled;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EnrollmentTest extends AbstractTest {

	@Autowired
	private EnrolledService		enrollService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@Test
	public void createEnrolled() {

		final Object testingData[][] = {

			{
				"member", null
			}, {
				"chapter", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createEnrolled((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void createEnrolled(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			// Set de parametros invalidos:
			this.startTransaction();
			final Brotherhood bro = this.brotherhoodService.create();
			bro.setName("broname");
			bro.setSurname("brosurname");
			bro.getUserAccount().setUsername("brousername");
			bro.getUserAccount().setPassword("bropassword");
			bro.setEmail("bro@email.com");
			bro.setTitle("brotitle");
			bro.setPhone("123456789");
			final Date date = new Date(0002, 01, 22);
			bro.setEstablishmentDate(date);
			final Brotherhood savedBro = this.brotherhoodService.saveR(bro);
			this.authenticate(username);
			final Enrolled enroll = this.enrollService.create(savedBro.getId());
			this.enrollService.save(enroll);
			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);
	}

}
