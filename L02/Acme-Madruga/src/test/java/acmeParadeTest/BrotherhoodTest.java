
package acmeParadeTest;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.BrotherhoodService;
import services.MemberService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.Member;
import domain.Parade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class BrotherhoodTest extends AbstractTest {

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private MemberService		memberService;


	/*
	 * 
	 * In this test we will test the register and edit as Brotherhood.
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
			this.testRegisterB((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);

	}
	public void testRegisterB(final int middleName, final int address, final int photo, final int phone, final int title, final int name, final int surname, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Brotherhood b = this.brotherhoodService.create();
			b.getUserAccount().setUsername("noExistoEnElPopulate");

			final String password = "noExistoEnElPopulate";
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			b.getUserAccount().setPassword(hashPassword);

			b.setEmail("noExistoEnElPopulate@noExistoEnElPopulate");

			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() - 1);

			System.out.println(res);

			b.setEstablishmentDate(res);

			if (middleName != 0)
				b.setMiddleName("noExistoEnElPopulate");
			else
				b.setMiddleName("");

			if (address != 0)
				b.setAddress("noExistoEnElPopulate");
			else
				b.setAddress("");

			if (photo != 0)
				b.setPhoto("http://noExistoEnElPopulate");
			else
				b.setPhoto("");

			if (phone != 0)
				b.setPhone("11111111");
			else
				b.setPhone("");

			if (title != 0)
				b.setTitle("noExistoEnElPopulate");
			else
				b.setTitle("");

			if (name != 0)
				b.setName("noExistoEnElPopulate");
			else
				b.setName("");

			if (surname != 0)
				b.setSurname("noExistoEnElPopulate");
			else
				b.setSurname("");

			this.brotherhoodService.saveR(b);

			/////////////////// SI NO HAY NINGUN ERROR Y SE CREA EL BROTHEHOOD SE EDITA

			if (this.brotherhoodService.findOne(b.getId()) != null) {

				super.authenticate(b.getUserAccount().getUsername());

				if (middleName != 0)
					b.setMiddleName("noExistoEnElPopulate");
				else
					b.setMiddleName("");

				if (address != 0)
					b.setAddress("noExistoEnElPopulate");
				else
					b.setAddress("");

				if (photo != 0)
					b.setPhoto("http://noExistoEnElPopulate");
				else
					b.setPhoto("");

				if (phone != 0)
					b.setPhone("11111111");
				else
					b.setPhone("");

				if (title != 0)
					b.setTitle("noExistoEnElPopulate");
				else
					b.setTitle("");

				if (name != 0)
					b.setName("noExistoEnElPopulate");
				else
					b.setName("");

				if (surname != 0)
					b.setSurname("noExistoEnElPopulate");
				else
					b.setSurname("");

				System.out.println(b.getTitle());

				this.brotherhoodService.save(b);

			}
			this.brotherhoodService.flush();

			super.unauthenticate();

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
	 * In this test we will test the register and edit as Brotherhood.
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	@Test
	public void driver2() {

		final Date res = LocalDateTime.now().toDate();
		res.setMonth(res.getMonth() - 1);

		final Date res1 = LocalDateTime.now().toDate();
		res1.setMonth(res.getMonth() + 3);

		final Object testingData[][] = {
			//	userName, password, email
			{
				"noExistoEnElPopulate2", "noExistoEnElPopulate2", "noExistoEnElPopulate@noExistoEnElPopulate2", res, null
			}, {
				//EMAIL INCORRECTO
				"noExistoEnElPopulate3", "noExistoEnElPopulate3", "emailIncorrecto", res, IllegalArgumentException.class
			}, {
				//EMAIL INCORRECTO
				"noExistoEnElPopulate4", "noExistoEnElPopulate4", "", res, IllegalArgumentException.class
			}, {
				//USERNAME INCORRECTO
				"", "noExistoEnElPopulate5", "noExistoEnElPopulate@noExistoEnElPopulate5", res, ConstraintViolationException.class
			}, {
				//USERNAME INCORRECT0
				"d", "noExistoEnElPopulate5", "noExistoEnElPopulate@noExistoEnElPopulate5", res, ConstraintViolationException.class
			}, {
				//USERNAME Y PASSWORD INCORRECT0
				"d", "noExistoEnElPopulate5", "emailIncorrecto", res, IllegalArgumentException.class
			}, {
				//USERNAME Y PASSWORD INCORRECT0
				"", "noExistoEnElPopulate5", "emailIncorrecto", res, IllegalArgumentException.class
			}, {
				//USERNAME Y PASSWORD INCORRECT0
				"d", "noExistoEnElPopulate5", "", res, IllegalArgumentException.class
			}, {
				//USERNAME Y PASSWORD INCORRECT0
				"", "noExistoEnElPopulate5", "", res, IllegalArgumentException.class
			}, {
				//EMAIL EXISTE
				"noExistoEnElPopulate5", "noExistoEnElPopulate5", "carferben@carferben", res, IllegalArgumentException.class
			}, {
				//USERNAME PASSWORD EMAIL INCORRECTO
				"", "", "", res, IllegalArgumentException.class
			}, {
				// PASSWORD EMAIL INCORRECTO
				"noExistoEnElPopulate5", "", "", res, IllegalArgumentException.class
			}, {
				// PASSWORD EMAIL INCORRECTO
				"", "", "noExistoEnElPopulate@noExistoEnElPopulate5", res, ConstraintViolationException.class
			}, {
				// PASSWORD EMAIL FECHA INCORRECTO 
				"", "", "noExistoEnElPopulate@noExistoEnElPopulate5", res1, IllegalArgumentException.class
			}, {
				// PASSWORD EMAIL FECHA INCORRECTO 
				"noExistoEnElPopulate5", "noExistoEnElPopulate5", "noExistoEnElPopulate", res1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testRegister2((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	public void testRegister2(final String userName, final String passwordd, final String email, final Date date, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Brotherhood b = this.brotherhoodService.create();
			b.getUserAccount().setUsername(userName);
			b.setEmail(email);
			b.setEstablishmentDate(date);

			final String password = passwordd;
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			b.getUserAccount().setPassword(hashPassword);

			b.setTitle("Carmen");
			b.setName("Carmen");
			b.setSurname("Carmen");
			b.setPhone("");
			b.setAddress("Lora del río, n5");
			b.setMiddleName("carmen");
			b.setPhoto("");

			this.brotherhoodService.saveR(b);
			System.out.println(b);

			if (this.brotherhoodService.findOne(b.getId()) != null) {

				b.getUserAccount().setUsername(userName);
				b.setEmail(email);
				b.setEstablishmentDate(date);

				b.getUserAccount().setPassword(hashPassword);

				this.brotherhoodService.save(b);

			}

			this.brotherhoodService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	///////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void driver3() {
		/*
		 * POSITIVO
		 * 
		 * In this test we will test the show and list of brotherhoods and their areas and proclaims.
		 * 
		 * I. R.
		 * 
		 * 5. There's a new kind of actor in the system: brotherhoods. For every brotherhood, the system must store its title.
		 * Every brotherhood co-ordinates and area and, thus, the parades organised by the brotherhoods in that area.
		 * No area can be co-ordinated by more than one brotherhood.
		 * 12. Chapters can publish proclaims. For every proclaim, the system must store the moment when it's published
		 * and a piece of text that can't be longer than 250 characters.
		 * 
		 * F. R.
		 * 
		 * 1. List the brotherhoods that are registered in the system, navigate to the areas that they co-ordinate, to the
		 * brotherhoods that have settle in those areas, and to the parades that they organise.
		 * 
		 * 2. Browse the proclaims of the brotherhoods.
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
			this.checkTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void checkTest(final String userName, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (userName != null)
				super.authenticate(userName);

			final List<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			for (final Brotherhood brotherhood : brotherhoods) {
				brotherhood.getId();
				brotherhood.getVersion();
				brotherhood.getName();
				brotherhood.getSurname();
				brotherhood.getPhoto();
				brotherhood.getEmail();
				brotherhood.getMiddleName();
				brotherhood.getPhone();
				brotherhood.getTitle();
				brotherhood.getSocialProfiles();
			}

			for (final Brotherhood brotherhood : brotherhoods) {
				final List<Parade> parades = (List<Parade>) brotherhood.getParades();
				for (final Parade parade : parades) {
					parade.getBrotherhood();
					parade.getDescription();
					parade.getFloatt();
					parade.getId();
					parade.getVersion();
					parade.getMaxColum();
					parade.getMaxRow();
					parade.getTicker();
					parade.getStatus();
				}
			}

			for (final Brotherhood brotherhood : brotherhoods) {
				final List<domain.Float> floatts = (List<domain.Float>) brotherhood.getFloats();
				for (final domain.Float floatt : floatts) {
					floatt.getId();
					floatt.getVersion();
					floatt.getDescription();
					floatt.getPictures();
					floatt.getTitle();
				}
			}

			for (final Brotherhood brotherhood : brotherhoods) {
				final List<Member> members = (List<Member>) this.memberService.brotherhoodAllMember(brotherhood.getId());
				for (final Member member : members) {
					member.getId();
					member.getVersion();
					member.getAddress();
					member.getName();
					member.getSurname();
					member.getPhoto();
					member.getEmail();
					member.getMiddleName();
					member.getPhone();
					member.getSocialProfiles();
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

	////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void driver4() {
		/*
		 * NEGATIVO
		 * 
		 * In this test we will test the show and list of brotherhoods and their areas and proclaims.
		 * 
		 * I. R.
		 * 
		 * 5. There's a new kind of actor in the system: brotherhoods. For every brotherhood, the system must store its title.
		 * Every brotherhood co-ordinates and area and, thus, the parades organised by the brotherhoods in that area.
		 * No area can be co-ordinated by more than one brotherhood.
		 * 12. Chapters can publish proclaims. For every proclaim, the system must store the moment when it's published
		 * and a piece of text that can't be longer than 250 characters.
		 * 
		 * F. R.
		 * 
		 * 1. List the brotherhoods that are registered in the system, navigate to the areas that they co-ordinate, to the
		 * brotherhoods that have settle in those areas, and to the parades that they organise.
		 * 
		 * 2. Browse the proclaims of the brotherhoods.
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

			final List<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			if (brotherhoods.isEmpty()) {
				brotherhoods.get(0).getId();
				brotherhoods.get(0).getVersion();
			}

			for (final Brotherhood brotherhood : brotherhoods) {
				final List<Parade> parades = (List<Parade>) brotherhood.getParades();
				if (parades.isEmpty()) {
					parades.get(0).getId();
					parades.get(0).getVersion();
				}
			}

			for (final Brotherhood brotherhood : brotherhoods) {
				final List<domain.Float> floatts = (List<domain.Float>) brotherhood.getFloats();
				if (floatts.isEmpty()) {
					floatts.get(0).getId();
					floatts.get(0).getVersion();
				}
			}

			for (final Brotherhood brotherhood : brotherhoods) {
				final List<Member> members = (List<Member>) this.memberService.brotherhoodAllMember(brotherhood.getId());
				if (members.isEmpty()) {
					members.get(0).getId();
					members.get(0).getVersion();
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
