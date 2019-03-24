
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

import services.AreaService;
import services.ChapterService;
import services.ProclaimService;
import utilities.AbstractTest;
import domain.Area;
import domain.Chapter;
import domain.Proclaim;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChapterTest extends AbstractTest {

	@Autowired
	private ChapterService	chapterService;

	@Autowired
	private AreaService		areaService;

	@Autowired
	private ProclaimService	proclaimService;


	/*
	 * 
	 * In this test we will test the register as Chapter.
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	@Test
	public void driver1() {
		final Object testingData[][] = {
			//	middleName, address, photo, phone, title, name, surname
			{
				0, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 1, 1, 1, null
			}, {
				0, 0, 0, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 1, 1, null
			}, {
				0, 0, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 1, 1, 1, null
			}, {
				0, 0, 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 1, 1, null
			}, {
				0, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 1, 1, 1, null
			}, {
				0, 0, 1, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 1, 1, 1, null
			}, {
				0, 0, 1, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 1, 1, 1, null
			}, {
				0, 0, 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 1, 1, 0, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 1, 1, 1, null
			}, {
				1, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 1, 1, 1, null
			}, {
				1, 0, 0, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 1, 1, null
			}, {
				1, 0, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 1, 1, 1, null
			}, {
				1, 0, 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 1, 1, null
			}, {
				1, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 1, 1, 1, null
			}, {
				1, 0, 1, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 1, 1, 1, null
			}, {
				1, 0, 1, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 1, 1, 1, null
			}, {
				1, 0, 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 1, 1, 0, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 1, 1, 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testRegisterChapter((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);

	}
	public void testRegisterChapter(final int middleName, final int address, final int photo, final int phone, final int title, final int name, final int surname, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Chapter chapter = this.chapterService.create();
			chapter.getUserAccount().setUsername("noExistoEnElPopulate");

			final String password = "noExistoEnElPopulate";
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			chapter.getUserAccount().setPassword(hashPassword);

			chapter.setEmail("noExistoEnElPopulate@noExistoEnElPopulate");

			if (middleName != 0)
				chapter.setMiddleName("noExistoEnElPopulate");
			else
				chapter.setMiddleName("");

			if (address != 0)
				chapter.setAddress("noExistoEnElPopulate");
			else
				chapter.setAddress("");

			if (photo != 0)
				chapter.setPhoto("http://noExistoEnElPopulate");
			else
				chapter.setPhoto("");

			if (phone != 0)
				chapter.setPhone("11111111");
			else
				chapter.setPhone("");

			if (title != 0)
				chapter.setTitle("noExistoEnElPopulate");
			else
				chapter.setTitle("");

			if (name != 0)
				chapter.setName("noExistoEnElPopulate");
			else
				chapter.setName("");

			if (surname != 0)
				chapter.setSurname("noExistoEnElPopulate");
			else
				chapter.setSurname("");

			this.chapterService.saveR(chapter);
			this.chapterService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * 
	 * In this test we will test the register as Chapter.
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	@Test
	public void driver2() {
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
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testRegister2((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	public void testRegister2(final String userName, final String passwordd, final String email, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Chapter chapter = this.chapterService.create();
			chapter.getUserAccount().setUsername(userName);
			chapter.setEmail(email);

			final String password = passwordd;
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			chapter.getUserAccount().setPassword(hashPassword);

			chapter.setTitle("Carmen");
			chapter.setName("Carmen");
			chapter.setSurname("Carmen");
			chapter.setPhone("");
			chapter.setAddress("Lora del río, n5");
			chapter.setMiddleName("carmen");
			chapter.setPhoto("");

			this.chapterService.saveR(chapter);
			System.out.println(chapter);

			this.chapterService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void driver3() {
		/*
		 * POSITIVO
		 * 
		 * In this test we will test the show and list of chapters and their areas and proclaims.
		 * 
		 * I. R.
		 * 
		 * 5. There's a new kind of actor in the system: chapters. For every chapter, the system must store its title.
		 * Every chapter co-ordinates and area and, thus, the parades organised by the brotherhoods in that area.
		 * No area can be co-ordinated by more than one chapter.
		 * 12. Chapters can publish proclaims. For every proclaim, the system must store the moment when it's published
		 * and a piece of text that can't be longer than 250 characters.
		 * 
		 * F. R.
		 * 
		 * 1. List the chapters that are registered in the system, navigate to the areas that they co-ordinate, to the
		 * brotherhoods that have settle in those areas, and to the parades that they organise.
		 * 
		 * 2. Browse the proclaims of the chapters.
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
				"chapter", null
			}, {
				"brotherhood", null
			}, {
				"member", null
			}, {
				"sponsor", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void checkTest(final String userName, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (userName != null)
				super.authenticate(userName);

			final List<Chapter> chapters = this.chapterService.findAll();
			for (final Chapter chapter : chapters) {
				chapter.getId();
				chapter.getVersion();
				chapter.getName();
				chapter.getSurname();
				chapter.getPhoto();
				chapter.getEmail();
				chapter.getMiddleName();
				chapter.getPhone();
				chapter.getTitle();
				chapter.getSocialProfiles();
			}
			for (final Chapter chapter : chapters) {
				final List<Proclaim> proclaims = (List<Proclaim>) chapter.getProclaim();
				for (final Proclaim proclaim : proclaims) {
					proclaim.getId();
					proclaim.getVersion();
					proclaim.getMoment();
					proclaim.getText();
				}
			}
			for (final Chapter chapter : chapters) {
				final Area area = this.areaService.findAreaChapter(chapter);
				if (area != null) {
					area.getId();
					area.getVersion();
					area.getName();
					area.getPictures();

				}
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
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void driver4() {
		/*
		 * NEGATIVO
		 * 
		 * In this test we will test the show and list of chapters and their areas and proclaims.
		 * 
		 * I. R.
		 * 
		 * 5. There's a new kind of actor in the system: chapters. For every chapter, the system must store its title.
		 * Every chapter co-ordinates and area and, thus, the parades organised by the brotherhoods in that area.
		 * No area can be co-ordinated by more than one chapter.
		 * 12. Chapters can publish proclaims. For every proclaim, the system must store the moment when it's published
		 * and a piece of text that can't be longer than 250 characters.
		 * 
		 * F. R.
		 * 
		 * 1. List the chapters that are registered in the system, navigate to the areas that they co-ordinate, to the
		 * brotherhoods that have settle in those areas, and to the parades that they organise.
		 * 
		 * 2. Browse the proclaims of the chapters.
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
				"chapter", IndexOutOfBoundsException.class
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

			final List<Chapter> chapters = this.chapterService.findAll();
			if (chapters.isEmpty()) {
				chapters.get(0).getId();
				chapters.get(0).getVersion();
			}

			for (final Chapter chapter : chapters) {
				final List<Proclaim> proclaims = (List<Proclaim>) chapter.getProclaim();
				if (proclaims.isEmpty()) {
					proclaims.get(0).getId();
					proclaims.get(0).getVersion();
				}

			}
			for (final Chapter chapter : chapters) {
				final Area area = this.areaService.findAreaChapter(chapter);
				if (area == null) {
					area.getId();
					area.getVersion();
					area.getName();
					area.getPictures();

				}
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
}
