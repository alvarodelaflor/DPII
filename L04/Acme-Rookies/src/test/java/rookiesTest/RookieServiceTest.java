/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package rookiesTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.RookieService;
import utilities.AbstractTest;
import domain.Rookie;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RookieServiceTest extends AbstractTest {

	@Autowired
	private RookieService	rookieService;


	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 1. Register to the system as a rookie.
	 * 
	 * Requisitos a tener en cuenta:
	 * 
	 * 1.The actors of the system are administrators, companies, and rookies. For every actor, the system must store a name, one or more surnames, a VAT number, a valid credit card, an optional photo, an email, an optional phone number, and an optional
	 * address. The system must also store the commercial name of the companies.
	 * 
	 * 2.Phone numbers should adhere to the following patterns: “+CC (AC) PN”, "+CC PN", or "PN":“+CC” denotes a country code in range “+1” up to “+999”, “(AC)” denotes an area code in range “(1)” up to “(999)”, and “PN”
	 * denotes a number that must have at
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
				// Test positivo: Create a Rookie
				// name, surname, photo, email, phone, address, userName, password, commercialName
				"pruebaCreateRookie", "pruebaCreateRookie", "http://pruebaCreateRookie", "pruebaCreateRookie@pruebaCreateRookie", "123456", "", "pruebaCreateRookie", "pruebaCreateRookie", "pruebaCreateRookie", "dd322d", null
			}, {
				// Test negativo: Create a Rookie
				// name, surname, photo, email, phone, address, userName, password, commercialName
				"", "", "pruebaCreateRookie", "pruebaCreateRookie", "", "", "", "", "", "ss22d", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}
	// Ancillary methods ------------------------------------------------------

	protected void Diver01(final String name, final String surname, final String photo, final String email, final String phone, final String address, final String userName, final String password, final String comercialName, final String vat,
		final Class<?> expected) {
		Class<?> caught = null;

		try {

			final Rookie rookie = this.rookieService.create();

			rookie.setAddress(address);
			rookie.setEmail(email);
			rookie.setName(name);
			rookie.setPhone(phone);
			rookie.setPhoto(photo);
			rookie.setSurname(surname);
			rookie.setVatNumber(vat);

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			rookie.getUserAccount().setPassword(hashPassword);

			rookie.getUserAccount().setUsername(userName);

			this.rookieService.saveCreate(rookie);
			System.out.println(rookie);

			this.rookieService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 8. An actor who is authenticated must be able to:
	 * 2. Edit his or her personal data.
	 * 
	 * * Requisitos a tener en cuenta:
	 * 
	 * 1.The actors of the system are administrators, companies, and rookies. For every actor, the system must store a name, one or more surnames, a VAT number, a valid credit card, an optional photo, an email, an optional phone number, and an optional
	 * address. The system must also store the commercial name of the companies.
	 * 
	 * 2.Phone numbers should adhere to the following patterns: “+CC (AC) PN”, "+CC PN", or "PN":“+CC” denotes a country code in range “+1” up to “+999”, “(AC)” denotes an area code in range “(1)” up to “(999)”, and “PN”
	 * denotes a number that must have at
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
	 * ~30%
	 */
	@Test
	public void Diver02() {
		final Object testingData[][] = {
			{
				// Test positivo: Editar el nombre de la nombre
				// Usuario
				"SoyUnaPatatita", null
			}, {
				// Test negativo: Editar el nombre de la nombre
				// Usuario
				"", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver02((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver02(final String name, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("soyUnaCalle");
			rookie.setEmail("soyUnaPrueba@soyUnaPrueba");
			rookie.setName("soyUnNombre");
			rookie.setPhone("123456");
			rookie.setPhoto("http://SoyUnaFoto");
			rookie.setSurname("SoyUnaPreuba");
			rookie.setVatNumber("dd33d");
			rookie.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			rookie.getUserAccount().setPassword(hashPassword);

			final Rookie rookieSave = this.rookieService.saveCreate(rookie);

			super.authenticate(rookie.getUserAccount().getUsername());
			final Rookie rookieToEdit = rookieSave;
			rookieToEdit.setName(name);
			this.rookieService.saveEdit(rookieToEdit);
			this.rookieService.flush();

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

			final Rookie company = this.rookieService.create();
			company.setAddress("soyUnaCalle");
			company.setEmail("soyUnaPrueba@soyUnaPrueba");
			company.setName("soyUnNombre");
			company.setPhone("123456");
			company.setPhoto("http://SoyUnaFoto");
			company.setSurname("SoyUnaPreuba");
			company.setVatNumber("dd33d");
			company.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			company.getUserAccount().setPassword(hashPassword);

			final Rookie companySave = this.rookieService.saveCreate(company);

			final Rookie rookieFind = this.rookieService.getRookieByUserAccountId(companySave.getUserAccount().getId());

			rookieFind.getAddress();
			rookieFind.getEmail();

			this.rookieService.flush();

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

			final Rookie rookieFind = this.rookieService.getRookieByUserAccountId(12345);
			rookieFind.getAddress();
			rookieFind.getEmail();

			this.rookieService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
