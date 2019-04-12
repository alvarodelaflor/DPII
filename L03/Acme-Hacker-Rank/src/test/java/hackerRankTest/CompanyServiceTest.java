/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package hackerRankTest;

import java.util.ArrayList;
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
import org.springframework.util.Assert;

import services.CompanyService;
import services.PositionService;
import utilities.AbstractTest;
import domain.Company;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CompanyServiceTest extends AbstractTest {

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private PositionService	positionService;


	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 1. Register to the system as a company.
	 * 
	 * Requisitos a tener en cuenta:
	 * 
	 * 1.The actors of the system are administrators, companies, and hackers. For every actor, the system must store a name, one or more surnames, a VAT number, a valid credit card, an optional photo, an email, an optional phone number, and an optional
	 * address. The system must also store the commercial name of the companies.
	 * 
	 * 2.Phone numbers should adhere to the following patterns: “+CC (AC) PN”, "+CC PN", or "PN":“+CC” denotes a country code in range “+1” up to “+999”, “(AC)” denotes an area code in range “(1)” up to “(999)”, and “PN” denotes a number that must have at
	 * least four digits. Phone numbers with pattern “PN” must be added automatically a default country, which is a parameter that can be changed by administrators. Note that phone numbers should adhere
	 * to the previous patterns, but they are not required to. Whenever a phone number that does not match this pattern is entered, the system must ask for confirmation; if the user confirms the number, it then must be stored.
	 * 
	 * 3.Email addresses must adhere to any of the following patterns: "identifier@domain", "alias <identifier@domain>"; administrators may have email addresses of the form "identifier@", or "alias <identifier@>". The identifier is an alpha-numeric string,
	 * the domain is a sequence
	 * of alpha-numeric strings that are separated by dots, and the alias is a sequence of alpha-numeric strings that are separated by spaces.
	 * 
	 * Analysis of sentence coverage
	 * 17,8%
	 * Analysis of data coverage
	 * ~35%
	 */
	@Test
	public void Diver01() {
		final Object testingData[][] = {
			{
				// Test positivo: Create a company
				// name, surname, photo, email, phone, address, userName, password, commercialName
				"pruebaCreateCompany", "pruebaCreateCompany", "http://pruebaCreateCompany", "pruebaCreateCompany@pruebaCreateCompany", "", "", "pruebaCreateCompany", "pruebaCreateCompany", "pruebaCreateCompany", null
			}, {
				// Test negativo: Create a company
				// name, surname, photo, email, phone, address, userName, password, commercialName
				"", "", "pruebaCreateCompany", "pruebaCreateCompany", "", "", "", "", "", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver01(final String name, final String surname, final String photo, final String email, final String phone, final String address, final String userName, final String password, final String comercialName, final Class<?> expected) {
		Class<?> caught = null;

		try {

			final Company company = this.companyService.create();

			company.setAddress(address);
			company.setCommercialName(comercialName);
			company.setEmail(email);
			company.setName(name);
			company.setPhone(phone);
			company.setPhoto(photo);
			company.setSurname(surname);

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			company.getUserAccount().setPassword(hashPassword);

			company.getUserAccount().setUsername(userName);

			this.companyService.saveCreate(company);
			System.out.println(company);

			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 
	 * 2. List the positions available and navigate to the corresponding companies.
	 * 
	 * Analysis of sentence coverage
	 * 19,8%
	 * Analysis of data coverage
	 * ~69%
	 */
	@Test
	public void Diver02() {
		final Object testingData[][] = {
			{
				// Test positivo: navigate to the corresponding companies.
				// Usuario
				null, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver02((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver02(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Company company = this.companyService.create();
			company.setAddress("soyUnaCalle");
			company.setCommercialName("soyUnaPrueba");
			company.setEmail("soyUnaPrueba@soyUnaPrueba");
			company.setName("soyUnNombre");
			company.setPhone("123456");
			company.setPhoto("http://SoyUnaFoto");
			company.setSurname("SoyUnaPreuba");
			company.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			company.getUserAccount().setPassword(hashPassword);

			final Company companySave = this.companyService.saveCreate(company);

			super.authenticate(company.getUserAccount().getUsername());

			final Position position = this.positionService.create();
			position.setCancel(false);
			position.setCompany(companySave);
			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 1);
			position.setDeadline(res);
			position.setDescription("soyUnaDescripcion");
			position.setProfile("SoyUnPerfil");
			position.setSalary(100.4);
			position.setSkills("soyUnaHabilidad");
			position.setStatus(true);
			position.setTechs("soyUnTechs");
			position.setTicker("SOYU-1234");
			position.setTitle("soyUnTitulo");
			final Position positionSave = this.positionService.save(position);

			super.unauthenticate();

			final Company companyByPosition = positionSave.getCompany();
			companyByPosition.getAddress();
			companyByPosition.getCommercialName();
			companyByPosition.getEmail();

			this.positionService.flush();
			this.companyService.flush();

		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 
	 * 2. List the positions available and navigate to the corresponding companies.
	 * 
	 * Analysis of sentence coverage
	 * 19,8%
	 * Analysis of data coverage
	 * ~69%
	 */
	@Test
	public void Diver03() {
		final Object testingData[][] = {
			{
				// Test negativo: navigate to the corresponding companies.
				// Usuario
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver03((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver03(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (user != null)
				super.authenticate(user);

			final Company company = this.companyService.create();
			company.setAddress("soyUnaCalle");
			company.setCommercialName("soyUnaPrueba");
			company.setEmail("soyUnaPrueba@soyUnaPrueba");
			company.setName("soyUnNombre");
			company.setPhone("123456");
			company.setPhoto("http://SoyUnaFoto");
			company.setSurname("SoyUnaPreuba");
			company.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			company.getUserAccount().setPassword(hashPassword);

			final Company companySave = this.companyService.saveCreate(company);

			super.authenticate(company.getUserAccount().getUsername());

			final Position position = this.positionService.create();
			position.setCancel(false);
			position.setCompany(companySave);
			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 1);
			position.setDeadline(res);
			position.setDescription("soyUnaDescripcion");
			position.setProfile("SoyUnPerfil");
			position.setSalary(100.4);
			position.setSkills("soyUnaHabilidad");
			position.setStatus(true);
			position.setTechs("soyUnTechs");
			position.setTicker("SOYU-1234");
			position.setTitle("soyUnTitulo");
			final Position positionSave = this.positionService.save(position);
			super.unauthenticate();

			final Company companyError = this.companyService.create();
			companyError.setAddress("companyError");
			companyError.setCommercialName("companyError");
			companyError.setEmail("companyError@companyError");
			companyError.setName("companyError");
			companyError.setPhone("123456");
			companyError.setPhoto("http://companyError");
			companyError.setSurname("companyError");
			companyError.getUserAccount().setUsername("companyError");

			final Md5PasswordEncoder encoder1 = new Md5PasswordEncoder();
			final String hashPassword1 = encoder1.encodePassword("companyError", null);
			company.getUserAccount().setPassword(hashPassword1);

			final Company companyErrorSave = this.companyService.saveCreate(companyError);

			// Intentar mostrar un company que no sea la position que se esta tratando
			// Controlado en controlador no en servicio por ello añado un assert
			Assert.isTrue(positionSave.getCompany().equals(companyErrorSave));
			companyErrorSave.getAddress();
			companyErrorSave.getCommercialName();
			companyErrorSave.getEmail();

			this.positionService.flush();
			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 3. List the companies available and navigate to the corresponding positions.
	 * 
	 * Analysis of sentence coverage
	 * 19,8%
	 * Analysis of data coverage
	 * ~69%
	 */
	@Test
	public void Diver04() {
		final Object testingData[][] = {
			{
				// Test positivo: list company.
				// Usuario
				null, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver04((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver04(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (user != null)
				super.authenticate(user);

			final List<Company> comanys = (List<Company>) this.companyService.findAll();
			for (final Company company : comanys) {
				company.getAddress();
				company.getCommercialName();
				company.getEmail();
			}

			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 3. List the companies available and navigate to the corresponding positions.
	 * 
	 * Analysis of sentence coverage
	 * 19,8%
	 * Analysis of data coverage
	 * ~69%
	 */
	@Test
	public void Diver05() {
		final Object testingData[][] = {
			{
				// Test positivo: list company.
				// Usuario
				null, IndexOutOfBoundsException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver05((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver05(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (user != null)
				super.authenticate(user);

			final List<Company> comanys = new ArrayList<>();
			comanys.get(0).getAddress();
			comanys.get(0).getCommercialName();
			comanys.get(0).getEmail();

			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 8. An actor who is authenticated must be able to:
	 * 2. Edit his or her personal data.
	 * 
	 * * Requisitos a tener en cuenta:
	 * 
	 * 1.The actors of the system are administrators, companies, and hackers. For every actor, the system must store a name, one or more surnames, a VAT number, a valid credit card, an optional photo, an email, an optional phone number, and an optional
	 * address. The system must also store the commercial name of the companies.
	 * 
	 * 2.Phone numbers should adhere to the following patterns: “+CC (AC) PN”, "+CC PN", or "PN":“+CC” denotes a country code in range “+1” up to “+999”, “(AC)” denotes an area code in range “(1)” up to “(999)”, and “PN” denotes a number that must have at
	 * least four digits. Phone numbers with pattern “PN” must be added automatically a default country, which is a parameter that can be changed by administrators. Note that phone numbers should adhere
	 * to the previous patterns, but they are not required to. Whenever a phone number that does not match this pattern is entered, the system must ask for confirmation; if the user confirms the number, it then must be stored.
	 * 
	 * 3.Email addresses must adhere to any of the following patterns: "identifier@domain", "alias <identifier@domain>"; administrators may have email addresses of the form "identifier@", or "alias <identifier@>". The identifier is an alpha-numeric string,
	 * the domain is a sequence
	 * of alpha-numeric strings that are separated by dots, and the alias is a sequence of alpha-numeric strings that are separated by spaces.
	 * 
	 * Analysis of sentence coverage
	 * 10,8%
	 * Analysis of data coverage
	 * ~20%
	 */
	@Test
	public void Diver06() {
		final Object testingData[][] = {
			{
				// Test positivo: Editar el nombre de la compañia
				// Usuario
				"SoyUnaPatatita", null
			}, {
				// Test negativo: Editar el nombre de la compañia
				// Usuario
				"", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver06((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver06(final String comercialName, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Company company = this.companyService.create();
			company.setAddress("soyUnaCalle");
			company.setCommercialName("soyUnaPrueba");
			company.setEmail("soyUnaPrueba@soyUnaPrueba");
			company.setName("soyUnNombre");
			company.setPhone("123456");
			company.setPhoto("http://SoyUnaFoto");
			company.setSurname("SoyUnaPreuba");
			company.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			company.getUserAccount().setPassword(hashPassword);

			final Company companySave = this.companyService.saveCreate(company);

			super.authenticate(company.getUserAccount().getUsername());
			final Company companyToEdit = companySave;
			companyToEdit.setCommercialName(comercialName);
			this.companyService.saveEdit(companyToEdit);
			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 8. An actor who is authenticated must be able to:
	 * 2. Edit his or her personal data. (show)
	 * 
	 * Analysis of sentence coverage
	 * 40,2%
	 * Analysis of data coverage
	 * ~40%
	 */
	@Test
	public void Diver07() {
		final Object testingData[][] = {
			{
				// Test positivo: show company.
				// Usuario
				null, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver07((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver07(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (user != null)
				super.authenticate(user);

			this.startTransaction();

			final Company company = this.companyService.create();
			company.setAddress("soyUnaCalle");
			company.setCommercialName("soyUnaPrueba");
			company.setEmail("soyUnaPrueba@soyUnaPrueba");
			company.setName("soyUnNombre");
			company.setPhone("123456");
			company.setPhoto("http://SoyUnaFoto");
			company.setSurname("SoyUnaPreuba");
			company.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			company.getUserAccount().setPassword(hashPassword);

			final Company companySave = this.companyService.saveCreate(company);

			final Company companyFind = this.companyService.getCompanyByUserAccountId(companySave.getUserAccount().getId());

			companyFind.getAddress();
			companyFind.getCommercialName();
			companyFind.getEmail();

			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 8. An actor who is authenticated must be able to:
	 * 2. Edit his or her personal data. (show)
	 * 
	 * Analysis of sentence coverage
	 * 40,2%
	 * Analysis of data coverage
	 * ~40%
	 */
	@Test
	public void Diver08() {
		final Object testingData[][] = {
			{
				// Test negativo: show company.
				// Usuario
				null, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver08((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver08(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (user != null)
				super.authenticate(user);

			this.startTransaction();

			final Company companyFind = this.companyService.getCompanyByUserAccountId(12345);
			companyFind.getAddress();
			companyFind.getCommercialName();
			companyFind.getEmail();

			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

}
