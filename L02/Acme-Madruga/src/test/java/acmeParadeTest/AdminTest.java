
package acmeParadeTest;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AdministratorService;
import utilities.AbstractTest;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;


	/*
	 * 
	 * In this test we will test the register as Admin.
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	@Test
	public void driver3() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone, title, name, surname
			{
				0, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 0, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 0, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 0, 1, 1, null
			}, {
				0, 1, 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 0, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 0, 1, 1, null
			}, {
				0, 1, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 0, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 1, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, 1, 1, null
			}, {
				0, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 1, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 0, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 1, 1, null
			}, {
				0, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 1, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 0, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 0, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 0, 1, 1, null
			}, {
				1, 1, 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 0, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 0, 1, 1, null
			}, {
				1, 1, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 0, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 1, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, 1, 1, null
			}, {
				1, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 1, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 0, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 1, 1, null
			}, {
				1, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 1, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 1, 1, 1, null
			}, {
				0, 0, 0, 0, 1, 1, 1, null
			}, {
				1, 0, 1, 1, 1, 1, 1, null
			}, {
				1, 1, 1, 0, 1, 1, 1, null
			}, {
				1, 0, 1, 0, 1, 1, 1, null
			}, {
				1, 1, 0, 1, 1, 1, 1, null
			}, {
				1, 0, 1, 1, 1, 1, 1, null
			}, {
				1, 1, 0, 0, 1, 1, 1, null
			}, {
				0, 1, 1, 1, 1, 1, 1, null
			}, {
				1, 0, 0, 0, 1, 1, 1, null
			}, {
				0, 0, 1, 1, 1, 1, 1, null
			}, {
				0, 1, 1, 0, 1, 1, 1, null
			}, {
				0, 0, 1, 0, 1, 1, 1, null
			}, {
				0, 1, 0, 1, 1, 1, 1, null
			}, {
				0, 0, 1, 1, 1, 1, 1, null
			}, {
				0, 1, 0, 0, 1, 1, 1, null
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.testRegister((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);

	}
	public void testRegister(final int middleName, final int address, final int photo, final int phone, final int title, final int name, final int surname, final Class<?> expected) {
		Class<?> caught = null;

		try {
			System.out.println("entro");
			this.startTransaction();

			super.authenticate("admin");

			final Administrator admin = this.administratorService.create();
			admin.getUserAccount().setUsername("adminNoExisto");

			final String password = "adminNoExisto";
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			admin.getUserAccount().setPassword(hashPassword);

			admin.setEmail("adminNoExisto@adminNoExisto");

			System.out.println("entro");

			if (middleName != 0)
				admin.setMiddleName("adminNoExisto");
			else
				admin.setMiddleName("");

			if (address != 0)
				admin.setAddress("adminNoExisto");
			else
				admin.setAddress("");

			if (photo != 0)
				admin.setPhoto("http://adminNoExisto");
			else
				admin.setPhoto("");

			if (phone != 0)
				admin.setPhone("11111111");
			else
				admin.setPhone("");

			if (name != 0)
				admin.setName("adminNoExisto");
			else
				admin.setName("");
			System.out.println("entro");

			if (surname != 0)
				admin.setSurname("adminNoExisto");
			else
				admin.setSurname("");
			System.out.println("entro");

			this.administratorService.saveR(admin);

			System.out.println("entro");

			super.unauthenticate();

			/////////////////// SI NO HAY NINGUN ERROR Y SE CREA EL ADMIN SE EDITA
			//
			//			if (this.administratorService.findOne(admin.getId()) != null) {
			//
			//				super.authenticate(admin.getUserAccount().getUsername());
			//
			//				if (middleName != 0)
			//					admin.setMiddleName("adminNoExisto1");
			//				else
			//					admin.setMiddleName("");
			//
			//				if (address != 0)
			//					admin.setAddress("adminNoExisto1");
			//				else
			//					admin.setAddress("");
			//
			//				if (photo != 0)
			//					admin.setPhoto("http://noExistoEnElPopulate1");
			//				else
			//					admin.setPhoto("");
			//
			//				if (phone != 0)
			//					admin.setPhone("11111111");
			//				else
			//					admin.setPhone("");
			//
			//				if (name != 0)
			//					admin.setName("noExistoEnElPopulate");
			//				else
			//					admin.setName("");
			//
			//				if (surname != 0)
			//					admin.setSurname("noExistoEnElPopulate");
			//				else
			//					admin.setSurname("");
			//
			//				this.administratorService.save(admin);
			//			}

			if (expected != null)
				this.administratorService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * 
	 * In this test we will test the register as Member.
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	@Test
	public void driver4() {

		final Object testingData[][] = {
			//	userName, password, email
			{
				"noExistoEnElPopulate2", "noExistoEnElPopulate2", "noExistoEnElPopulate@noExistoEnElPopulate2", null
			}, {
				//EMAIL INCORRECTO
				"noExistoEnElPopulate3", "noExistoEnElPopulate3", "emailIncorrecto", IllegalArgumentException.class
			}, {
				//EMAIL INCORRECTO
				"noExistoEnElPopulate4", "noExistoEnElPopulate4", "", IllegalArgumentException.class
			}, {
				//USERNAME INCORRECTO
				"", "noExistoEnElPopulate5", "noExistoEnElPopulate@noExistoEnElPopulate5", ConstraintViolationException.class
			}, {
				//USERNAME INCORRECT0
				"d", "noExistoEnElPopulate5", "noExistoEnElPopulate@noExistoEnElPopulate5", ConstraintViolationException.class
			}, {
				//USERNAME Y PASSWORD INCORRECT0
				"d", "noExistoEnElPopulate5", "emailIncorrecto", IllegalArgumentException.class
			}, {
				//USERNAME Y PASSWORD INCORRECT0
				"", "noExistoEnElPopulate5", "emailIncorrecto", IllegalArgumentException.class
			}, {
				//USERNAME Y PASSWORD INCORRECT0
				"d", "noExistoEnElPopulate5", "", IllegalArgumentException.class
			}, {
				//USERNAME Y PASSWORD INCORRECT0
				"", "noExistoEnElPopulate5", "", IllegalArgumentException.class
			}, {
				//EMAIL EXISTE
				"noExistoEnElPopulate5", "noExistoEnElPopulate5", "carferben@carferben", IllegalArgumentException.class
			}, {
				//USERNAME PASSWORD EMAIL INCORRECTO
				"", "", "", IllegalArgumentException.class
			}, {
				// PASSWORD EMAIL INCORRECTO
				"noExistoEnElPopulate5", "", "", IllegalArgumentException.class
			}, {
				// PASSWORD EMAIL INCORRECTO
				"", "", "noExistoEnElPopulate@noExistoEnElPopulate5", ConstraintViolationException.class
			}, {
				// EMAIL  CORRECTO 
				"noExistoEnElPopulate5", "noExistoEnElPopulate5", "carmen <noExistoEnElPopulate@noExistoEnElPopulate5>", null
			}, {
				// EMAIL  CORRECTO 
				"noExistoEnElPopulate5", "noExistoEnElPopulate5", "carmen <noExistoEnElPopulate@>", null
			}, {
				// EMAIL  CORRECTO 
				"noExistoEnElPopulate5", "noExistoEnElPopulate5", "noExistoEnElPopulate@", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testRegister2((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	public void testRegister2(final String userName, final String passwordd, final String email, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			super.authenticate("admin");
			final Administrator a = this.administratorService.create();
			a.getUserAccount().setUsername(userName);
			a.setEmail(email);

			final String password = passwordd;
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			a.getUserAccount().setPassword(hashPassword);

			a.setName("Carmen");
			a.setSurname("Carmen");
			a.setPhone("");
			a.setAddress("Lora del río, n5");
			a.setMiddleName("carmen");
			a.setPhoto("");

			this.administratorService.saveR(a);

			super.unauthenticate();

			if (this.administratorService.findOne(a.getId()) != null) {

				super.authenticate(a.getUserAccount().getUsername());

				a.setEmail(email);

				this.administratorService.save(a);

			}

			super.unauthenticate();

			if (expected != null)
				this.administratorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	///////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void driver1() {
		/*
		 * NEGATIVO
		 * 
		 * In this test we will test the show of admin
		 * 
		 * 
		 * Analysis of sentence coverage
		 * TODO
		 * Analysis of data coverage
		 * TODO
		 */
		final Object testingData[][] = {
			// username, error
			{
				null, IndexOutOfBoundsException.class
			}, {
				"admin", IndexOutOfBoundsException.class
			}, {
				"brotherhood", IndexOutOfBoundsException.class
			}, {
				"brotherhood", IndexOutOfBoundsException.class
			}, {
				"member", IndexOutOfBoundsException.class
			}, {
				"sponsor", IndexOutOfBoundsException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest1((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void checkTest1(final String userName, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (userName != null)
				super.authenticate(userName);

			final List<Administrator> administrators = (List<Administrator>) this.administratorService.findAll();

			if (administrators.size() != 0) {
				administrators.clear();
				administrators.get(0).getId();
				administrators.get(0).getVersion();
			}

			if (userName != null)
				super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	///////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void driver2() {
		/*
		 * POSITIVO
		 * 
		 * In this test we will test the show of admin
		 * 
		 * 
		 * Analysis of sentence coverage
		 * TODO
		 * Analysis of data coverage
		 * TODO
		 */
		final Object testingData[][] = {
			// username, error
			{
				null, null
			}, {
				"admin", null
			}, {
				"brotherhood", null
			}, {
				"brotherhood", null
			}, {
				"member", null
			}, {
				"sponsor", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest2((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void checkTest2(final String userName, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (userName != null)
				super.authenticate(userName);

			final List<Administrator> administrators = (List<Administrator>) this.administratorService.findAll();
			for (final Administrator administrator : administrators) {
				administrator.getId();
				administrator.getVersion();
				administrator.getAddress();
				administrator.getName();
				administrator.getSurname();
				administrator.getPhoto();
				administrator.getEmail();
				administrator.getMiddleName();
				administrator.getPhone();
				administrator.getSocialProfiles();
			}

			if (userName != null)
				super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 
	 * In this test we will test the register as Admin.
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	@Test
	public void driver5() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone, title, name, surname
			{
				0, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 0, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 0, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 0, 1, 1, null
			}, {
				0, 1, 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 0, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 0, 1, 1, null
			}, {
				0, 1, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 0, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 1, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, 1, 1, null
			}, {
				0, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 1, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 0, 1, 0, ConstraintViolationException.class

			}, {
				0, 0, 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 1, 1, null
			}, {
				0, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 1, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 0, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 0, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 0, 1, 1, null
			}, {
				1, 1, 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 0, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 0, 1, 1, null
			}, {
				1, 1, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 0, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 1, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, 1, 1, null
			}, {
				1, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 1, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 0, 1, 0, ConstraintViolationException.class

			}, {
				1, 0, 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 1, 1, null
			}, {
				1, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 1, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 1, 1, 1, null
			}, {
				0, 0, 0, 0, 1, 1, 1, null
			}, {
				1, 0, 1, 1, 1, 1, 1, null
			}, {
				1, 1, 1, 0, 1, 1, 1, null
			}, {
				1, 0, 1, 0, 1, 1, 1, null
			}, {
				1, 1, 0, 1, 1, 1, 1, null
			}, {
				1, 0, 1, 1, 1, 1, 1, null
			}, {
				1, 1, 0, 0, 1, 1, 1, null
			}, {
				0, 1, 1, 1, 1, 1, 1, null
			}, {
				1, 0, 0, 0, 1, 1, 1, null
			}, {
				0, 0, 1, 1, 1, 1, 1, null
			}, {
				0, 1, 1, 0, 1, 1, 1, null
			}, {
				0, 0, 1, 0, 1, 1, 1, null
			}, {
				0, 1, 0, 1, 1, 1, 1, null
			}, {
				0, 0, 1, 1, 1, 1, 1, null
			}, {
				0, 1, 0, 0, 1, 1, 1, null
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.testE((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);

	}
	public void testE(final int middleName, final int address, final int photo, final int phone, final int title, final int name, final int surname, final Class<?> expected) {
		Class<?> caught = null;

		try {
			System.out.println("entro");
			this.startTransaction();

			super.authenticate("admin");

			final Administrator admin = this.administratorService.findOne(super.getEntityId("admin01"));

			if (middleName != 0)
				admin.setMiddleName("adminNoExisto1");
			else
				admin.setMiddleName("");

			if (address != 0)
				admin.setAddress("adminNoExisto1");
			else
				admin.setAddress("");

			if (photo != 0)
				admin.setPhoto("http://noExistoEnElPopulate1");
			else
				admin.setPhoto("");

			if (phone != 0)
				admin.setPhone("11111111");
			else
				admin.setPhone("");

			if (name != 0)
				admin.setName("noExistoEnElPopulate");
			else
				admin.setName("");

			if (surname != 0)
				admin.setSurname("noExistoEnElPopulate");
			else
				admin.setSurname("");

			this.administratorService.save(admin);

			super.unauthenticate();
		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * 
	 * In this test we will test the edit as admin.
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	@Test
	public void driver6() {

		final Object testingData[][] = {
			//	email
			{
				"noExistoEnElPopulate3@", null
			}, {
				"noExistoEnElPopulate3@noExistoEnElPopulate3", null
			}, {
				"noExistoEnElPopulate3", IllegalArgumentException.class
			}, {
				"", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.teste2((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	public void teste2(final String email, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			super.authenticate("admin");

			final Administrator a = this.administratorService.findOne(super.getEntityId("admin01"));
			a.setEmail(email);

			a.setName("Carmen");
			a.setSurname("Carmen");
			a.setPhone("");
			a.setAddress("Lora del río, n5");
			a.setMiddleName("carmen");
			a.setPhoto("");

			this.administratorService.save(a);

			super.unauthenticate();

			if (expected != null)
				this.administratorService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

}
