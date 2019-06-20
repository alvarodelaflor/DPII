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

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.PositionService;
import services.ProviderService;
import utilities.AbstractTest;
import domain.Provider;
import domain.Provider;
import domain.Provider;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProviderServiceTest extends AbstractTest {

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private PositionService	positionService;


	/*
	 * 9. An actor who is not authenticated must be able to:
	 * 3. Register to the system as a provider.
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
				// Test positivo: Create a provider
				// name, surname, photo, email, phone, address, userName, password, commercialName
				"pruebaCreateProvider", "pruebaCreateProvider", "http://pruebaCreateProvider", "pruebaCreateProvider@pruebaCreateProvider", "", "", "pruebaCreateProvider", "pruebaCreateProvider", "pruebaCreateProvider", "dd33f", null

			}, {
				// Test negativo: Create a provider
				// name, surname, photo, email, phone, address, userName, password, commercialName
				"", "", "pruebaCreateProvider", "pruebaCreateProvider", "", "", "", "", "", "dd33ff", IllegalArgumentException.class
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

			final Provider provider = this.providerService.create();

			provider.setAddress(address);
			provider.setCommercialName(comercialName);
			provider.setEmail(email);
			provider.setName(name);
			provider.setPhone(phone);
			provider.setPhoto(photo);
			provider.setSurname(surname);
			provider.setVatNumber(vat);

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			provider.getUserAccount().setPassword(hashPassword);

			provider.getUserAccount().setUsername(userName);

			this.providerService.saveCreate(provider);
			System.out.println(provider);

			this.providerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 9. An actor who is not authenticated must be able to:
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
				// Test positivo: list provider.
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

			final List<Provider> providers = (List<Provider>) this.providerService.findAll();
			for (int i = 0; i < providers.size(); i++) {
				final Provider p = this.providerService.findOne(i);
				providers.get(i).getEmail();
			}

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 9. An actor who is not authenticated must be able to:
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
				// Test negativo: list provider.
				// Usuario
				null, IndexOutOfBoundsException.class
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

			final List<Provider> providers = new ArrayList<>();
			if (providers.isEmpty())
				providers.get(0).getAddress();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 9. An actor who is not authenticated must be able to:
	 * 
	 * Analysis of sentence coverage
	 * 40,2%
	 * Analysis of data coverage
	 * ~40%
	 */
	@Test
	public void Diver04() {
		final Object testingData[][] = {
			{
				// Test positivo: show provider.
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

			final Provider provider = this.providerService.create();
			provider.setAddress("soyUnaCalle");
			provider.setCommercialName("soyUnaPrueba");
			provider.setEmail("soyUnaPrueba@soyUnaPrueba");
			provider.setName("soyUnNombre");
			provider.setPhone("123456");
			provider.setPhoto("http://SoyUnaFoto");
			provider.setSurname("SoyUnaPreuba");
			provider.setVatNumber("ca22d");
			provider.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			provider.getUserAccount().setPassword(hashPassword);

			final Provider providerSave = this.providerService.saveCreate(provider);

			final Provider providerFind = this.providerService.getProviderByUserAccountId(providerSave.getUserAccount().getId());

			providerFind.getAddress();
			providerFind.getCommercialName();
			providerFind.getEmail();

			this.providerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 9. An actor who is not authenticated must be able to:
	 * 
	 * Analysis of sentence coverage
	 * 40,2%
	 * Analysis of data coverage
	 * ~40%
	 */
	@Test
	public void Diver05() {
		final Object testingData[][] = {
			{
				// Test negativo: show provider.
				// Usuario
				null, NullPointerException.class
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

			final Provider providerFind = this.providerService.getProviderByUserAccountId(12345);
			providerFind.getAddress();
			providerFind.getCommercialName();
			providerFind.getEmail();

			this.providerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}


}
