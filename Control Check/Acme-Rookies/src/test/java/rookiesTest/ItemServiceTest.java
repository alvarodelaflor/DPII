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

import domain.Item;
import domain.Provider;
import services.ItemService;
import services.ProviderService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ItemServiceTest extends AbstractTest {

	@Autowired
	private ProviderService	providerService;

	@Autowired
	private ItemService	itemService;


	
	/*
	 * 9. An actor who is not authenticated must be able to:
	 * 
	 * Analysis of sentence coverage
	 * 19,8%
	 * Analysis of data coverage
	 * ~69%
	 */
	@Test
	public void Diver01() {
		final Object testingData[][] = {
			{
				// Test positivo: list items.
				// Usuario
				null, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver01(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			 List<Item> items =  (List<Item>) this.itemService.findAll();
			for (int i = 0; i < items.size(); i++) {
				final Item p = this.itemService.findOne(i);
				items.get(i).getDescription();
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
	public void Diver02() {
		final Object testingData[][] = {
			{
				// Test negativo: list items.
				// Usuario
				null, IndexOutOfBoundsException.class
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

			final List<Item> items = new ArrayList<>();
			if (items.isEmpty())
				items.get(0).getDescription();

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
	public void Diver03() {
		final Object testingData[][] = {
			{
				// Test positivo: show item.
				// Usuario
				null, null
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

			System.out.println(itemService.getProviderItems(providerSave.getId()));
			

			final List<Item> itemFind =(List<Item>) this.itemService.getProviderItems(providerSave.getId());
			
			for (int i = 0; i < itemFind.size(); i++) {
				itemFind.get(i).getDescription();				
			}

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
	public void Diver04() {
		final Object testingData[][] = {
			{
				// Test positivo: show item.
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
			

			final List<Item> itemFind =(List<Item>) this.itemService.getProviderItems(12345);
			
			for (int i = 0; i < itemFind.size(); i++) {
				itemFind.get(i).getDescription();				
			}

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
