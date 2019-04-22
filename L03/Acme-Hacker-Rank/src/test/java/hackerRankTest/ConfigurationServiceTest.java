
package hackerRankTest;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Administrator;
import services.AdministratorService;
import services.ConfigurationService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private AdministratorService	adminService;


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
				// Test positivo: Change banner
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

			final Administrator administrator = this.adminService.create();
			administrator.setAddress("soyUnaCalle");
			administrator.setEmail("soyUnaPrueba@soyUnaPrueba");
			administrator.setName("soyUnNombre");
			administrator.setPhone("123456");
			administrator.setPhoto("http://SoyUnaFoto");
			administrator.setSurname("SoyUnaPreuba");
			administrator.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			administrator.getUserAccount().setPassword(hashPassword);

			final Administrator administratorSave = this.adminService.saveCreate(administrator);

			this.configurationService.newLogo("PRUEBA LOGO");

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 14. The system must be easy to customise at run time, including banner, system message and country code
	 *
	 */
	@Test
	public void Diver02() {
		final Object testingData[][] = {
			{
				// Negative Test: Only admins can change system banner
				//
				IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((Class<?>) testingData[i][0]);

	}

	//Ancillary methods------------------------------------------------------

	protected void Diver02(final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Administrator administrator = this.adminService.create();
			administrator.setAddress("soyUnaCalle");
			administrator.setEmail("soyUnaPrueba@soyUnaPrueba");
			administrator.setName("soyUnNombre");
			administrator.setPhone("123456");
			administrator.setPhoto("http://SoyUnaFoto");
			administrator.setSurname("SoyUnaPreuba");
			administrator.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			administrator.getUserAccount().setPassword(hashPassword);

			final Administrator administratorSave = this.adminService.saveCreate(administrator);

			this.configurationService.newLogo("PRUEBA LOGO");

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 14. The system must be easy to customise at run time, including banner, system message and country code
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
				// // Positive Test: Only admins can change the system's default phone prefix
				//
				IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((Class<?>) testingData[i][0]);

	}

	//Ancillary methods------------------------------------------------------

	protected void Diver03(final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Administrator administrator = this.adminService.create();
			administrator.setAddress("soyUnaCalle");
			administrator.setEmail("soyUnaPrueba@soyUnaPrueba");
			administrator.setName("soyUnNombre");
			administrator.setPhone("123456");
			administrator.setPhoto("http://SoyUnaFoto");
			administrator.setSurname("SoyUnaPreuba");
			administrator.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			administrator.getUserAccount().setPassword(hashPassword);

			final Administrator administratorSave = this.adminService.saveCreate(administrator);
			super.authenticate(administrator.getUserAccount().getUsername());
			this.configurationService.newPhone("+23");

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 14. The system must be easy to customise at run time, including banner, system message and country code
	 * //
	 * * * Analysis of sentence coverage
	 * 12.7%
	 * Analysis of data coverage
	 * ~18%
	 */
	@Test
	public void Diver04() {
		final Object testingData[][] = {
			{
				// Negative Test: Only admins can change the system's default phone prefix
				//
				IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((Class<?>) testingData[i][0]);

	}

	//Ancillary methods------------------------------------------------------

	protected void Diver04(final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Administrator administrator = this.adminService.create();
			administrator.setAddress("soyUnaCalle");
			administrator.setEmail("soyUnaPrueba@soyUnaPrueba");
			administrator.setName("soyUnNombre");
			administrator.setPhone("123456");
			administrator.setPhoto("http://SoyUnaFoto");
			administrator.setSurname("SoyUnaPreuba");
			administrator.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			administrator.getUserAccount().setPassword(hashPassword);

			final Administrator administratorSave = this.adminService.saveCreate(administrator);

			this.configurationService.newPhone("+23");

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 14. The system must be easy to customise at run time, including banner, system message and country code
	 * //
	 * * * Analysis of sentence coverage
	 * 10.3%
	 * Analysis of data coverage
	 * ~15%
	 */
	@Test
	public void Diver05() {
		final Object testingData[][] = {
			{
				// // Positive Test: Only admins can change the system's name
				//
				IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((Class<?>) testingData[i][0]);

	}

	//Ancillary methods------------------------------------------------------

	protected void Diver05(final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Administrator administrator = this.adminService.create();
			administrator.setAddress("soyUnaCalle");
			administrator.setEmail("soyUnaPrueba@soyUnaPrueba");
			administrator.setName("soyUnNombre");
			administrator.setPhone("123456");
			administrator.setPhoto("http://SoyUnaFoto");
			administrator.setSurname("SoyUnaPreuba");
			administrator.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			administrator.getUserAccount().setPassword(hashPassword);

			final Administrator administratorSave = this.adminService.saveCreate(administrator);
			super.authenticate(administrator.getUserAccount().getUsername());
			this.configurationService.newSystem("PRUEBA");

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 14. The system must be easy to customise at run time, including banner, system message and country code
	 * //
	 * * Analysis of sentence coverage
	 * 13,8%
	 * Analysis of data coverage
	 * ~20%
	 */
	@Test
	public void Diver06() {
		final Object testingData[][] = {
			{
				// Negative Test: Only admins can changethe system's name
				//
				IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((Class<?>) testingData[i][0]);

	}

	//Ancillary methods------------------------------------------------------

	protected void Diver06(final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Administrator administrator = this.adminService.create();
			administrator.setAddress("soyUnaCalle");
			administrator.setEmail("soyUnaPrueba@soyUnaPrueba");
			administrator.setName("soyUnNombre");
			administrator.setPhone("123456");
			administrator.setPhoto("http://SoyUnaFoto");
			administrator.setSurname("SoyUnaPreuba");
			administrator.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			administrator.getUserAccount().setPassword(hashPassword);

			final Administrator administratorSave = this.adminService.saveCreate(administrator);

			this.configurationService.newSystem("PRUEBA");

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
