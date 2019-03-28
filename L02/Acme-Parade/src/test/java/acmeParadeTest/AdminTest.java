
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

import services.ActorService;
import services.AdministratorService;
import services.AreaService;
import services.BrotherhoodService;
import services.EnrolledService;
import services.MessageService;
import services.PositionService;
import services.WelcomeService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Area;
import domain.Brotherhood;
import domain.Enrolled;
import domain.Message;
import domain.Position;
import domain.Priority;
import forms.RegistrationForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AreaService				areaService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private EnrolledService			enrolledService;

	@Autowired
	private MessageService			msgService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private WelcomeService			welcomeService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private PositionService			positionService;


	/*
	 * 
	 * In this test we will test the register as Admin. R12.1
	 * 
	 * Analysis of sentence coverage
	 * 67%
	 * Analysis of data coverage
	 * 47,6%
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
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * 
	 * In this test we will test the register as Admin. R12.1
	 * 
	 * Analysis of sentence coverage
	 * 30,8%
	 * Analysis of data coverage
	 * 30%
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
			super.unauthenticate();
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
		 * 20,6%
		 * Analysis of data coverage
		 * 10%
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
			super.unauthenticate();
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
		 * 20%
		 * Analysis of data coverage
		 * 10%
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
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 
	 * In this test we will test the edit as Admin. R9
	 * 
	 * Analysis of sentence coverage
	 * 63%
	 * Analysis of data coverage
	 * 47,5%
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
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * 
	 * In this test we will test the edit as admin. R9
	 * 
	 * Analysis of sentence coverage
	 * 30,5%
	 * Analysis of data coverage
	 * 20,5%
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
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	// FRAN

	@Test
	// Test de CREATE que junto con el de SAVE comprueban la creacion de un nuevo area;
	public void adminCreateArea() {

		final Object testingData[][] = {

			{
				"admin", null
			}, {
				"chapter", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminCreateArea((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void areaSave() {

		final Object testingData[][] = {

			{
				"admin", 0, null
			}, {
				"chapter", 0, IllegalArgumentException.class
			}, {
				"admin", 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.areaSave((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void assignArea() {

		final Object testingData[][] = {

			{
				"chapterchapter", null
			}, {
				"member", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.assignArea((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	// Test que comprueba DELETE de un Area
	public void adminDeleteArea() {

		final Object testingData[][] = {

			{
				"admin", 0, 0, null
			}, {
				"chapter", 0, 0, IllegalArgumentException.class
			}, {
				"admin", 0, 1, IllegalArgumentException.class
			}, {
				"admin", 1, 0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminDeleteArea((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	// Test de CREATE que junto con SAVE comprueban la creacion de una nueva position;
	public void adminPositionCreate() {

		final Object testingData[][] = {

			{
				"admin", null
			}, {
				"chapter", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminCreatePosition((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void positionSave() {

		final Object testingData[][] = {

			// username, nameES, nameEN, exception
			{
				"admin", 0, 0, 0, null
			}, {
				"admin", 0, 1, 0, IllegalArgumentException.class
			}, {
				"admin", 1, 0, 0, IllegalArgumentException.class
			}, {
				"admin", 0, 0, 1, IllegalArgumentException.class
			}, {
				"chapter", 0, 0, 0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.positionSave((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	@Test
	// Test que comprueba DELETE de una position;
	public void adminDeletePosition() {

		final Object testingData[][] = {

			{
				"admin", 0, null
			}, {
				"admin", 1, IllegalArgumentException.class
			}, {
				"chapter", 0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminDeletePosition((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void adminAddSpamWord() {

		final Object testingData[][] = {

			{
				"admin", 0, null
			}, {
				"chapter", 0, null
			}, {
				"admin", 1, null
			}, {
				"chapter", 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminAddSpamWord((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void adminDeleteSpamWord() {

		final Object testingData[][] = {

			{
				"admin", 0, null
			}, {
				"chapter", 0, null
			}, {
				"admin", 1, null
			}, {
				"chapter", 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminDeleteSpamWord((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void actorSpammer() {

		final Object testingData[][] = {

			{
				0, null
			}, {
				1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.actorSpammer((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void actorPolarity() {

		final Object testingData[][] = {

			{
				0, 0, null
			}, {
				0, 1, null
			}, {
				1, 0, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.actorPolarity((int) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void actorPolarityNeg() {

		final Object testingData[][] = {

			{
				0, 0, IllegalArgumentException.class
			}, {
				0, 1, IllegalArgumentException.class
			}, {
				1, 0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.actorPolarityNeg((int) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	@Test
	public void adminAddScoreWord() {

		final Object testingData[][] = {

			{
				"admin", 0, 0, null
			}, {
				"chapter", 0, 0, null
			}, {
				"admin", 0, 1, null
			}, {
				"admin", 1, 0, null
			}, {
				"chapter", 1, 0, IllegalArgumentException.class
			}, {
				"chapter", 0, 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminAddScoreWord((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	@Test
	public void adminDeleteScoreWord() {

		final Object testingData[][] = {

			{
				"admin", 0, 0, null
			}, {
				"chapter", 0, 0, null
			}, {
				"admin", 0, 1, null
			}, {
				"admin", 1, 0, null
			}, {
				"chapter", 1, 0, IllegalArgumentException.class
			}, {
				"chapter", 0, 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminDeleteScoreWord((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void adminBanActor() {
		final Object testingData[][] = {

			{
				"admin", 0, 0, IllegalArgumentException.class
			}, {
				"chapter", 0, 0, IllegalArgumentException.class
			}, {
				"chapter", 1, 1, IllegalArgumentException.class
			}, {
				"admin", 0, 1, null
			}, {
				"admin", 1, 0, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminBanActor((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void adminUnbanActor() {

		final Object testingData[][] = {

			{
				"admin", null
			}, {
				"chapter", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminUnbanActor((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void adminCreateArea(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(username);
			this.areaService.create();
			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void areaSave(final String username, final int name, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(username);
			final Area newArea = this.areaService.create();

			// Set de parametros validos:
			newArea.setName("nombreValido");
			final Collection<String> vpics = new ArrayList<String>();
			newArea.setPictures(vpics);

			// Set de parametros invalidos:
			if (name == 1)
				newArea.setName("");

			this.areaService.save(newArea);

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminDeleteArea(final String username, final int used, final int nullArea, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(username);
			final Area newArea = this.areaService.create();
			newArea.setName("nombreValido");
			final Collection<String> vpics = new ArrayList<String>();
			newArea.setPictures(vpics);
			final Area savedArea = this.areaService.save(newArea);

			if (used == 1) {
				this.unauthenticate();
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
				bro.setArea(savedArea);
				this.brotherhoodService.saveR(bro);
				this.authenticate(username);
				final Area usedArea = this.areaService.findOne(savedArea.getId());
				this.areaService.delete(usedArea.getId());
			}

			else if (nullArea == 1)
				this.areaService.delete(-1);

			else
				this.areaService.delete(savedArea.getId());

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminCreatePosition(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(username);
			this.positionService.create();
			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void positionSave(final String username, final int nameEs, final int nameEn, final int repeated, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(username);
			final Position position = this.positionService.create();
			// Set de parametros validos:
			position.setNameEn("nameEsValido");
			position.setNameEs("nameEsValido");
			// Set de parametros invalidos:
			if (nameEs == 1)
				position.setNameEs("");
			else if (nameEn == 1)
				position.setNameEn("");

			this.positionService.save(position);

			// Comprueba que no se metan valores repetidos:
			if (repeated == 1) {

				final Position positionRepeated = this.positionService.create();
				positionRepeated.setNameEn("nameEsValido");
				positionRepeated.setNameEs("nameEsValido");
				this.positionService.save(positionRepeated);
			}

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminDeletePosition(final String username, final int used, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(username);
			final Position position = this.positionService.create();
			position.setNameEn("nameEsValido");
			position.setNameEs("nameEsValido");
			this.positionService.save(position);

			if (used == 1) {
				this.unauthenticate();
				final Brotherhood bro = this.brotherhoodService.create();
				bro.setName("broname");
				bro.setSurname("brosurname");
				bro.getUserAccount().setUsername("brousername");
				bro.getUserAccount().setPassword("bropassword");
				bro.setEmail("bro@email.com");
				bro.setTitle("brotitle");
				final Date date = new Date(0002, 01, 22);
				bro.setEstablishmentDate(date);
				final Brotherhood savedBro = this.brotherhoodService.save(bro);
				this.authenticate("member");
				final Enrolled enrolled = this.enrolledService.create(savedBro.getId());
				enrolled.setState(true);
				enrolled.setPosition(position);
				this.enrolledService.save(enrolled);
				this.unauthenticate();
				this.authenticate(username);
			}

			this.positionService.delete(position);
			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void actorSpammer(final int spammer, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Brotherhood bro = this.brotherhoodService.create();
			bro.setName("broname");
			bro.setSurname("brosurname");
			bro.getUserAccount().setUsername("brousername");
			bro.getUserAccount().setPassword("bropassword");
			bro.setEmail("bro@email.com");
			bro.setTitle("brotitle");
			final Date date = new Date(0002, 01, 22);
			bro.setEstablishmentDate(date);
			bro.setPhone("123456789");
			final Brotherhood savedBro = this.brotherhoodService.saveR(bro);

			this.authenticate("brousername");

			final Message msg = this.msgService.create();
			final Priority priority = new Priority();
			priority.setValue("HIGH");
			msg.setPriority(priority);
			msg.setSubject("subject");
			msg.setBody("no spam");
			if (spammer == 1)
				msg.setBody("sex");
			this.welcomeService.newSpamWords("sex");
			final Collection<String> receiver = new ArrayList<String>();
			receiver.add("bro2@email.com");
			msg.setEmailReceiver(receiver);
			this.msgService.save(msg);

			Assert.isTrue(!savedBro.getUserAccount().getSpammerFlag());
			this.welcomeService.deleteSpamWords("sex");
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void actorPolarity(final int good, final int bad, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Brotherhood bro = this.brotherhoodService.create();
			bro.setName("broname");
			bro.setSurname("brosurname");
			bro.getUserAccount().setUsername("brousername");
			bro.getUserAccount().setPassword("bropassword");
			bro.setEmail("bro@email.com");
			bro.setTitle("brotitle");
			final Date date = new Date(0002, 01, 22);
			bro.setEstablishmentDate(date);
			bro.setPhone("123456789");
			final Brotherhood savedBro = this.brotherhoodService.saveR(bro);

			this.authenticate("brousername");

			final Message msg = this.msgService.create();
			final Priority priority = new Priority();
			priority.setValue("HIGH");
			msg.setPriority(priority);
			msg.setSubject("subject");
			msg.setBody("no score");
			if (good == 1)
				msg.setBody("good");
			else if (bad == 1)
				msg.setBody("bad");
			final Collection<String> receiver = new ArrayList<String>();
			receiver.add("bro2@email.com");
			msg.setEmailReceiver(receiver);
			this.msgService.save(msg);

			this.unauthenticate();

			final Brotherhood postBro = this.brotherhoodService.findOne(savedBro.getId());

			if (good == 1)
				Assert.isTrue(postBro.getUserAccount().getPolarity() > 0.);
			else if (bad == 1)
				Assert.isTrue(postBro.getUserAccount().getPolarity() < 0.);
			else
				Assert.isTrue(postBro.getUserAccount().getPolarity() == 0.);

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void actorPolarityNeg(final int good, final int bad, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Brotherhood bro = this.brotherhoodService.create();
			bro.setName("broname");
			bro.setSurname("brosurname");
			bro.getUserAccount().setUsername("brousername");
			bro.getUserAccount().setPassword("bropassword");
			bro.setEmail("bro@email.com");
			bro.setTitle("brotitle");
			final Date date = new Date(0002, 01, 22);
			bro.setEstablishmentDate(date);
			bro.setPhone("123456789");
			final Brotherhood savedBro = this.brotherhoodService.saveR(bro);

			this.authenticate("brousername");

			final Message msg = this.msgService.create();
			final Priority priority = new Priority();
			priority.setValue("HIGH");
			msg.setPriority(priority);
			msg.setSubject("subject");
			msg.setBody("no score");
			if (good == 1)
				msg.setBody("good");
			else if (bad == 1)
				msg.setBody("bad");
			final Collection<String> receiver = new ArrayList<String>();
			receiver.add("bro2@email.com");
			msg.setEmailReceiver(receiver);
			this.msgService.save(msg);

			this.unauthenticate();

			final Brotherhood postBro = this.brotherhoodService.findOne(savedBro.getId());

			if (good == 1)
				Assert.isTrue(postBro.getUserAccount().getPolarity() <= 0.);
			else if (bad == 1)
				Assert.isTrue(postBro.getUserAccount().getPolarity() >= 0.);
			else
				Assert.isTrue(postBro.getUserAccount().getPolarity() != 0.);

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminAddScoreWord(final String username, final int newPos, final int newNeg, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(username);
			final int posBefore = this.adminService.listScoreWordsPos().size();
			final int negBefore = this.adminService.listScoreWordsNeg().size();

			if (newPos == 1) {

				this.adminService.newScoreWordsPos("newPos");
				Assert.isTrue(posBefore < this.adminService.listScoreWordsPos().size());
			} else if (newNeg == 1) {

				this.adminService.newScoreWordsNeg("newNeg");
				Assert.isTrue(negBefore < this.adminService.listScoreWordsNeg().size());
			} else {

				Assert.isTrue(posBefore == this.adminService.listScoreWordsPos().size());
				Assert.isTrue(negBefore == this.adminService.listScoreWordsNeg().size());
			}

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminDeleteScoreWord(final String username, final int newPos, final int newNeg, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(username);
			final int posBefore = this.adminService.listScoreWordsPos().size();
			final int negBefore = this.adminService.listScoreWordsNeg().size();

			if (newPos == 1) {

				this.adminService.deleteScoreWordsPos("newPos");
				Assert.isTrue(posBefore > this.adminService.listScoreWordsPos().size());
			} else if (newNeg == 1) {

				this.adminService.deleteScoreWordsNeg("newNeg");
				Assert.isTrue(negBefore > this.adminService.listScoreWordsNeg().size());
			} else {

				Assert.isTrue(posBefore == this.adminService.listScoreWordsPos().size());
				Assert.isTrue(negBefore == this.adminService.listScoreWordsNeg().size());
			}

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminAddSpamWord(final String username, final int newPos, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(username);
			final int posBefore = this.welcomeService.getSpamWords().size();

			if (newPos == 1) {

				this.welcomeService.newSpamWords("newWord");
				Assert.isTrue(posBefore == this.welcomeService.getSpamWords().size() - 1);
			} else
				Assert.isTrue(posBefore == this.welcomeService.getSpamWords().size());

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminDeleteSpamWord(final String username, final int newPos, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(username);
			final int posBefore = this.welcomeService.getSpamWords().size();

			if (newPos == 1) {

				this.welcomeService.deleteSpamWords("newWord");
				Assert.isTrue(posBefore == this.welcomeService.getSpamWords().size() + 1);
			} else
				Assert.isTrue(posBefore == this.welcomeService.getSpamWords().size());

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminBanActor(final String username, final int spammer, final int lowScore, final Class<?> expected) {

		Class<?> caught = null;

		try {

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
			if (spammer == 1)
				bro.getUserAccount().setSpammerFlag(true);
			if (lowScore == 1)
				bro.getUserAccount().setPolarity(-1.);
			final Brotherhood savedBro = this.brotherhoodService.saveR(bro);

			this.authenticate(username);
			this.actorService.banByActorId(savedBro);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}
	protected void adminUnbanActor(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

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
			bro.getUserAccount().setSpammerFlag(true);
			bro.getUserAccount().setPolarity(-1.);
			bro.getUserAccount().setBanned(true);
			final Brotherhood savedBro = this.brotherhoodService.saveR(bro);

			this.authenticate(username);
			this.actorService.unbanByActorId(savedBro);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	protected void assignArea(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate("admin");
			final Area newArea = this.areaService.create();

			// Set de parametros validos:
			newArea.setName("nombreValido");
			final Collection<String> vpics = new ArrayList<String>();
			newArea.setPictures(vpics);

			// Set de parametros invalidos:

			final Area saved = this.areaService.save(newArea);
			this.unauthenticate();

			this.authenticate(username);
			this.areaService.assignChapter(saved.getId());

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}
	protected void listAreas(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			// Set de parametros invalidos:
			this.startTransaction();
			this.authenticate(username);
			this.areaService.unassignedAreas();

			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
			super.unauthenticate();
		}

		super.checkExceptions(expected, caught);
	}

	@Test
	public void driver10() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				"admin01", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testReconstruct((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testReconstruct(final String id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final Administrator test = this.administratorService.findOne(this.getEntityId(id));
			test.setName("testName");
			final Administrator reconstructTest = this.administratorService.reconstruct(test, null);
			Assert.isTrue(reconstructTest.getName().equals("testName"));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver8() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				"0", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testReconstructForm((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	public void testReconstructForm(final String id, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			final RegistrationForm form = new RegistrationForm();
			form.setAccept(true);
			form.setPassword("password");
			form.setConfirmPassword("password");
			form.setAddress("address");
			form.setEmail("email@test.com");
			form.setMiddleName("testMiddle");
			form.setName("testName");
			form.setPhoto("http://photo.com");
			form.setPhone("666777888");
			form.setUserName("userNameTest");
			form.setSurname("surname");
			form.setTitle("title");

			final Administrator test = this.administratorService.reconstructR(form, null);

			Assert.notNull(test);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	// FRAN
}
