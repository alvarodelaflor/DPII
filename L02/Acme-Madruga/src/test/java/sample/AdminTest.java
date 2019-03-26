
package sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ActorService;
import services.AdministratorService;
import services.AreaService;
import services.BrotherhoodService;
import services.EnrolledService;
import services.MemberService;
import services.MessageBoxService;
import services.MessageService;
import services.PositionService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Area;
import domain.Brotherhood;
import domain.Enrolled;
import domain.Message;
import domain.Position;
import domain.Priority;

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
	private PositionService			positionService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private EnrolledService			enrolledService;

	@Autowired
	private MessageBoxService		mailService;

	@Autowired
	private MessageService			msgService;

	@Autowired
	private ActorService			actorService;


	// Drivers:
	@Test
	// Test de CREATE que junto con el de SAVE comprueban el registro de un nuevo admin;
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
				1, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				2, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 2, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 2, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 3, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 2, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 3, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 1, NullPointerException.class
			}, {
				0, 0, 0, 0, 2, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminSave((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
	}

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

	// Test que comprueba el broadcast;
	public void adminBroadcast() {

		final Object testingData[][] = {

			{
				"admin", null
			}, {
				"chapter", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.adminBroadcast((String) testingData[i][0], (Class<?>) testingData[i][1]);
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

	// Auxiliar Methods:
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

			// Set de parametros validos: name, surname, username, password, email
			newAdmin.setName("nameValido");
			newAdmin.setSurname("surnameValido");
			newAdmin.getUserAccount().setUsername("usernameValido");
			final String p = "passwordValido";
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(p, null);
			newAdmin.getUserAccount().setPassword(hashPassword);
			newAdmin.setEmail("validovalido@valid.com");
			newAdmin.setPhone("123456789");

			// Set de parametros no validos: 
			if (name == 1)
				newAdmin.setName(null);
			else if (name == 2)
				newAdmin.setName("");
			else if (surname == 1)
				newAdmin.setSurname(null);
			else if (surname == 2)
				newAdmin.setSurname("");
			else if (username == 1)
				newAdmin.getUserAccount().setUsername(null);
			else if (username == 2)
				newAdmin.getUserAccount().setUsername("");
			else if (username == 3)
				newAdmin.getUserAccount().setUsername("a12345123451234512345123451234512345");
			else if (password == 1)
				newAdmin.getUserAccount().setPassword(null);
			else if (password == 2)
				newAdmin.getUserAccount().setPassword("");
			else if (password == 3)
				newAdmin.getUserAccount().setPassword("a12345123451234512345123451234512345");
			else if (email == 1)
				newAdmin.setEmail(null);
			else if (email == 2)
				newAdmin.setEmail("emailInvalido");

			this.adminService.saveR(newAdmin);

			this.unauthenticate();
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
		}

		super.checkExceptions(expected, caught);
	}

	protected void adminBroadcast(final String username, final Class<?> expected) {

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
			final Date date = new Date(0002, 01, 22); // Como pongo DATE sin el deprecated?
			bro.setEstablishmentDate(date);
			final Brotherhood savedBro = this.brotherhoodService.saveR(bro);

			final int before = this.mailService.getNotificationBoxActor(savedBro.getId()).getMessages().size();

			this.authenticate(username);
			final Message msg = this.msgService.create();
			msg.setBody("testBroadcast");
			final Message savedMsg = this.msgService.save(msg);
			this.msgService.sendNotification(savedMsg);
			this.unauthenticate();

			final int after = this.mailService.getNotificationBoxActor(savedBro.getId()).getMessages().size();

			Assert.isTrue(before < after);
		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
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
			final Collection<String> receiver = new ArrayList<String>();
			receiver.add("bro2@email.com");
			msg.setEmailReceiver(receiver);
			this.msgService.save(msg);

			Assert.isTrue(!savedBro.getUserAccount().getSpammerFlag());

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
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
		}

		super.checkExceptions(expected, caught);
	}
}
