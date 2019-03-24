
package sample;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.AdministratorService;
import services.AreaService;
import services.ConfigurationService;
import services.MessageBoxService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.MessageBox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminTest extends AbstractTest {

	// SUT:
	@Autowired
	private AdministratorService	adminService;

	// Auxiliary Services
	@Autowired
	private AreaService				areaService;

	@Autowired
	private ConfigurationService	configService;

	@Autowired
	private MessageBoxService		messageBoxService;


	// Positives:

	// Negatives:

	// Drivers:
	@Test
	public void adminCreateAdmin() {

		final Object testingData[][] = {

			{
				"admin", null
			}, {
				"chapter", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminCreateAdmin((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void adminSave() {

		// name, surname, username, passsword, email
		// 0 es un valor "bueno", !=0 un valor "malo"
		final Object testingData[][] = {

			{
				0, 0, 0, 0, 0, null
			}, {
				0, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 2, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 2, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminSave((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	@Test
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

	// Abstract:
	protected void adminCreateAdmin(final String username, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(username);
			this.adminService.create();
			this.unauthenticate();
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminSave(final int name, final int surname, final int username, final int password, final int email, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate("admin");
			final Administrator newAdmin = this.adminService.create();
			this.unauthenticate();

			// Set de parametros validos:
			newAdmin.setConfiguration(this.configService.getConfiguration());
			//MailBox
			final MessageBox inBox = this.messageBoxService.create();
			final MessageBox outBox = this.messageBoxService.create();
			final MessageBox trashBox = this.messageBoxService.create();
			final MessageBox notificationBox = this.messageBoxService.create();
			final MessageBox spamBox = this.messageBoxService.create();

			inBox.setName("in box");
			outBox.setName("out box");
			trashBox.setName("trash box");
			notificationBox.setName("notification box");
			spamBox.setName("spam box");

			inBox.setIsDefault(true);
			outBox.setIsDefault(true);
			trashBox.setIsDefault(true);
			notificationBox.setIsDefault(true);
			spamBox.setIsDefault(true);

			final MessageBox inBoxSave = this.messageBoxService.save(inBox);
			final MessageBox outBoxSave = this.messageBoxService.save(outBox);
			final MessageBox trashBoxSave = this.messageBoxService.save(trashBox);
			final MessageBox notificationBoxSave = this.messageBoxService.save(notificationBox);
			final MessageBox spamBoxSave = this.messageBoxService.save(spamBox);

			final Collection<MessageBox> boxesDefault = new ArrayList<>();

			boxesDefault.add(inBoxSave);
			boxesDefault.add(outBoxSave);
			boxesDefault.add(trashBoxSave);
			boxesDefault.add(notificationBoxSave);
			boxesDefault.add(spamBoxSave);

			newAdmin.setMessageBoxes(boxesDefault);

			// Set de parametros no validos:
			if (name == 1)
				newAdmin.setName("");
			else if (surname == 1)
				newAdmin.setSurname("");
			else if (username == 1)
				newAdmin.getUserAccount().setUsername("aaaa");
			else if (username == 2)
				newAdmin.getUserAccount().setUsername("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			else if (password == 1)
				newAdmin.getUserAccount().setUsername("aaaa");
			else if (password == 2)
				newAdmin.getUserAccount().setUsername("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			else if (email == 1)
				newAdmin.getUserAccount().setUsername("");

			this.adminService.save(newAdmin);

			newAdmin.setName("nameValido");
			newAdmin.setSurname("surnameValido");
			newAdmin.getUserAccount().setUsername("usernameValido");
			newAdmin.getUserAccount().setPassword("passwordValido");
			newAdmin.setEmail("email@valido.com");

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
		}

		super.checkExceptions(expected, caught);
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
		}

		super.checkExceptions(expected, caught);
	}
}
