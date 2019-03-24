
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

import services.BrotherhoodService;
import services.MemberService;
import services.ParadeService;
import utilities.AbstractTest;
import domain.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MemberTest extends AbstractTest {

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private MemberService		memberService;


	/*
	 * 
	 * In this test we will test the register and edit as Member.
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

			final Member member = this.memberService.create();
			member.getUserAccount().setUsername("memberNoExisto");

			final String password = "memberNoExisto";
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			member.getUserAccount().setPassword(hashPassword);

			member.setEmail("memberNoExisto@memberNoExisto");

			System.out.println("entro");

			if (middleName != 0)
				member.setMiddleName("memberNoExisto");
			else
				member.setMiddleName("");

			if (address != 0)
				member.setAddress("memberNoExisto");
			else
				member.setAddress("");

			if (photo != 0)
				member.setPhoto("http://memberNoExisto");
			else
				member.setPhoto("");

			if (phone != 0)
				member.setPhone("11111111");
			else
				member.setPhone("");

			if (name != 0)
				member.setName("memberNoExisto");
			else
				member.setName("");
			System.out.println("entro");

			if (surname != 0)
				member.setSurname("memberNoExisto");
			else
				member.setSurname("");
			System.out.println("entro");

			this.memberService.saveR(member);

			System.out.println("entro");

			/////////////////// SI NO HAY NINGUN ERROR Y SE CREA EL MEMBER SE EDITA

			if (this.brotherhoodService.findOne(member.getId()) != null) {

				super.authenticate(member.getUserAccount().getUsername());

				if (middleName != 0)
					member.setMiddleName("noExistoEnElPopulate");
				else
					member.setMiddleName("");

				if (address != 0)
					member.setAddress("noExistoEnElPopulate");
				else
					member.setAddress("");

				if (photo != 0)
					member.setPhoto("http://noExistoEnElPopulate");
				else
					member.setPhoto("");

				if (phone != 0)
					member.setPhone("11111111");
				else
					member.setPhone("");

				if (name != 0)
					member.setName("noExistoEnElPopulate");
				else
					member.setName("");

				if (surname != 0)
					member.setSurname("noExistoEnElPopulate");
				else
					member.setSurname("");

				this.memberService.save(member);
			}

			if (expected != null)
				this.memberService.flush();

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
	 * In this test we will test the register and edit as Member.
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
			}, {
				// EMAIL  CORRECTO 
				"noExistoEnElPopulate5", "noExistoEnElPopulate5", "carmen <noExistoEnElPopulate@noExistoEnElPopulate5>", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testRegister2((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	public void testRegister2(final String userName, final String passwordd, final String email, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			final Member m = this.memberService.create();
			m.getUserAccount().setUsername(userName);
			m.setEmail(email);

			final String password = passwordd;
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			m.getUserAccount().setPassword(hashPassword);

			m.setName("Carmen");
			m.setSurname("Carmen");
			m.setPhone("");
			m.setAddress("Lora del río, n5");
			m.setMiddleName("carmen");
			m.setPhoto("");

			this.memberService.saveR(m);

			if (this.memberService.findOne(m.getId()) != null) {

				super.authenticate(m.getUserAccount().getUsername());

				m.setEmail(email);

				this.memberService.save(m);

			}

			super.unauthenticate();

			if (expected != null)
				this.memberService.flush();

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
		 * NEGATIVO
		 * 
		 * In this test we will test the show of brotherhoods
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
			this.checkTest3((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void checkTest3(final String userName, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (userName != null)
				super.authenticate(userName);

			final List<Member> members = (List<Member>) this.memberService.findAll();

			if (members.size() != 0) {
				members.clear();
				members.get(0).getId();
				members.get(0).getVersion();
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
	public void driver4() {
		/*
		 * POSITIVO
		 * 
		 * In this test we will test the show of brotherhoods
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
			this.checkTest1((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void checkTest1(final String userName, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (userName != null)
				super.authenticate(userName);

			final List<Member> members = (List<Member>) this.memberService.findAll();
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
