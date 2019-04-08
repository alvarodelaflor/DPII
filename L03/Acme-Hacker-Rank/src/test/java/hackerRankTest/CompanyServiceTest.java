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

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.CompanyService;
import utilities.AbstractTest;
import domain.Company;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CompanyServiceTest extends AbstractTest {

	@Autowired
	private CompanyService	companyService;


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

}
