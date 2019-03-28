/*
 * FloatTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package acmeParadeTest;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.UserAccount;
import services.BrotherhoodService;
import services.FloatService;
import utilities.AbstractTest;
import domain.Brotherhood;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FloatTest extends AbstractTest {

	@Autowired
	private FloatService		floatService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Tests ------------------------------------------------------------------

	@Test
	public void test01() {
		/*
		 * POSITIVE TEST
		 * 
		 * In this test we will test the creation of a float.
		 * 
		 * Information requirements
		 * 
		 * 5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures.
		 * Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 * 
		 * Functional requirements
		 * 
		 * 10. An actor who is authenticated as a brotherhood must be able to:
		 * 1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 * 
		 * Analysis of sentence coverage
		 * 42.8%
		 * Analysis of data coverage
		 * 95.2%
		 */
		final Object testingData[][] = {
			// brotherhoodId, pictures
			{
				"brotherhood", 0, null
			}, {
				"brotherhood", 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void checkTest(final String userName, final int pictures, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);

			final domain.Float floatt = this.floatService.create();
			floatt.setTitle("El título");
			floatt.setDescription("La descripción");

			if (pictures != 0)
				floatt.setPictures("https://www.google.es/");
			else
				floatt.setPictures("");

			this.floatService.save(floatt);
			super.unauthenticate();

			this.floatService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void test02() {
		/*
		 * NEGATIVE TEST -> domain restriction and actor check
		 * 
		 * In this test we will test the creation of a float.
		 * 
		 * Information requirements
		 * 
		 * 5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures.
		 * Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 * 
		 * Functional requirements
		 * 
		 * 10. An actor who is authenticated as a brotherhood must be able to:
		 * 1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 * 
		 * Analysis of sentence coverage
		 * 82.5%
		 * Analysis of data coverage
		 * 95.2%
		 */
		final Object testingData[][] = {
			// brotherhoodId, titleBlank, descriptionBlank, titleNull, descriptionNull, picturesInvalid 
			{
				"brotherhood", 0, 0, 0, 0, 0, null
			}, {
				"brotherhood", 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 0, 1, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 1, 1, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 0, 1, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 1, 1, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				"member", 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				"member", 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"member", 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				"member", 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"member", 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				"member", 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"member", 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				"member", 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"member", 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				"member", 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"member", 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				"member", 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"member", 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				"member", 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"member", 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				"member", 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				"member", 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				"member", 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"member", 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				"member", 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"member", 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				"member", 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"member", 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				"member", 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"member", 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				"member", 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"member", 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				"member", 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"member", 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				"member", 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"member", 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				"member", 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				"chapter", 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				"chapter", 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"chapter", 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				"chapter", 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"chapter", 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				"chapter", 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"chapter", 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				"chapter", 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"chapter", 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				"chapter", 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"chapter", 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				"chapter", 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"chapter", 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				"chapter", 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"chapter", 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				"chapter", 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				"chapter", 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				"chapter", 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"chapter", 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				"chapter", 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"chapter", 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				"chapter", 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"chapter", 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				"chapter", 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"chapter", 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				"chapter", 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"chapter", 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				"chapter", 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"chapter", 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				"chapter", 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"chapter", 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				"chapter", 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				"admin", 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				"admin", 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"admin", 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				"admin", 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"admin", 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				"admin", 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"admin", 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				"admin", 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"admin", 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				"admin", 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"admin", 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				"admin", 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"admin", 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				"admin", 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"admin", 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				"admin", 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				"admin", 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				"admin", 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"admin", 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				"admin", 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"admin", 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				"admin", 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"admin", 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				"admin", 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"admin", 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				"admin", 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"admin", 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				"admin", 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"admin", 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				"admin", 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"admin", 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				"admin", 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				"sponsor", 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				"sponsor", 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"sponsor", 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				"sponsor", 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"sponsor", 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				"sponsor", 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"sponsor", 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				"sponsor", 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"sponsor", 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				"sponsor", 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"sponsor", 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				"sponsor", 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"sponsor", 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				"sponsor", 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"sponsor", 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				"sponsor", 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				"sponsor", 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				"sponsor", 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"sponsor", 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				"sponsor", 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"sponsor", 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				"sponsor", 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"sponsor", 1, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				"sponsor", 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"sponsor", 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				"sponsor", 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"sponsor", 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				"sponsor", 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"sponsor", 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				"sponsor", 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"sponsor", 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				"sponsor", 1, 1, 1, 1, 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void checkTest(final String userName, final int titleBlank, final int descriptionBlank, final int titleNull, final int descriptionNull, final int picturesInvalid, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);

			final domain.Float floatt = this.floatService.create();
			floatt.setTitle("El título");
			floatt.setDescription("La descripción");

			if (titleBlank != 0 && titleNull == 0)
				floatt.setTitle("");
			else if (titleBlank == 0 && titleNull != 0)
				floatt.setTitle(null);
			else
				floatt.setTitle("Valid title");

			if (descriptionBlank != 0 && descriptionNull == 0)
				floatt.setDescription("");
			else if (descriptionBlank == 0 && descriptionNull != 0)
				floatt.setDescription(null);
			else
				floatt.setDescription("Valid description");

			if (picturesInvalid != 0)
				floatt.setPictures("URL no válida");
			else
				floatt.setPictures("https://www.myPhoto.com?id=90403/'https://www.myPhoto.com?id=90402/");

			if (((titleBlank * titleNull) + (descriptionBlank * descriptionNull)) > 0 && picturesInvalid == 0)
				// Forzado de error ya que es un caso válido
				floatt.setTitle("");

			this.floatService.save(floatt);
			super.unauthenticate();

			this.floatService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
		//		System.out.println("Esperado: " + expected + ", capturado: " + caught + " caso: " + titleBlank + descriptionBlank + titleNull + descriptionNull + picturesInvalid);
	}

	@Test
	public void test03() {
		/*
		 * POSITIVE TEST
		 * 
		 * In this test we will test delete a float.
		 * 
		 * Information requirements
		 * 
		 * 5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures.
		 * Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 * 
		 * Functional requirements
		 * 
		 * 10. An actor who is authenticated as a brotherhood must be able to:
		 * 1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 * 
		 * Analysis of sentence coverage
		 * 100%
		 * Analysis of data coverage
		 * 90%
		 */
		final Object testingData[][] = {
			{
				"brotherhood2", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkDeleteM((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void test04() {
		/*
		 * NEGATIVE TEST -> not owner
		 * 
		 * In this test we will test delete a float.
		 * 
		 * Information requirements
		 * 
		 * 5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures.
		 * Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 * 
		 * Functional requirements
		 * 
		 * 10. An actor who is authenticated as a brotherhood must be able to:
		 * 1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 * 
		 * Analysis of sentence coverage
		 * 100%
		 * Analysis of data coverage
		 * 90%
		 */
		final Object testingData[][] = {
			{
				"brotherhood2", null
			}, {
				"brotherhood", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}, {
				"chapter", IllegalArgumentException.class
			}, {
				"member", IllegalArgumentException.class
			}, {
				"sponsor", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkDeleteM((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void checkDeleteM(final String userName, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);

			domain.Float floatt;
			domain.Float floattSaved;

			if (userName.equals("brotherhood2")) {
				floatt = this.floatService.create();
				floatt.setTitle("El título");
				floatt.setDescription("La descripción");
				floattSaved = this.floatService.save(floatt);
			} else {
				super.unauthenticate();
				super.authenticate("brotherhood2");

				floatt = this.floatService.create();
				floatt.setTitle("El título");
				floatt.setDescription("La descripción");
				floattSaved = this.floatService.save(floatt);

				super.unauthenticate();
				super.authenticate(userName);
			}

			this.floatService.delete(floattSaved);
			this.floatService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void test05() {
		/*
		 * POSITIVE TEST
		 * 
		 * In this test we will test edit a float.
		 * 
		 * Information requirements
		 * 
		 * 5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures.
		 * Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 * 
		 * Functional requirements
		 * 
		 * 10. An actor who is authenticated as a brotherhood must be able to:
		 * 1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 * 
		 * Analysis of sentence coverage
		 * 90%
		 * Analysis of data coverage
		 * 95.2%
		 */
		final Object testingData[][] = {
			// title, description, picture
			{
				"brotherhood", 0, 0, 1, null
			}, {
				"brotherhood", 0, 1, 0, null
			}, {
				"brotherhood", 0, 1, 1, null
			}, {
				"brotherhood", 1, 0, 0, null
			}, {
				"brotherhood", 1, 0, 1, null
			}, {
				"brotherhood", 1, 1, 0, null
			}, {
				"brotherhood", 1, 1, 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkEditPositive((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void checkEditPositive(final String userName, final int title, final int description, final int picture, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate("brotherhood");

			final domain.Float floatt = this.floatService.create();
			floatt.setTitle("El título");
			floatt.setDescription("La descripción");
			floatt.setPictures("https://thumbs.gfycat.com/ColorlessViciousApisdorsatalaboriosa-size_restricted.gif");
			final domain.Float floattSaved = this.floatService.save(floatt);
			;

			if (!userName.equals("brotherhood")) {
				super.unauthenticate();
				super.authenticate(userName);
			}

			if (title != 0)
				floattSaved.setTitle("Edición de título");

			if (description != 0)
				floattSaved.setDescription("Edición descripción");

			if (picture != 0)
				floattSaved.setPictures("https://thumbs.gfycat.com/ColorlessViciousApisdorsatalaboriosa-size_restricted.gif'https://thumbs.gfycat.com/ColorlessViciousApisdorsatalaboriosa-size_restricted.gif");

			this.floatService.save(floattSaved);
			super.unauthenticate();
			this.floatService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void test06() {
		/*
		 * NEGATIVE TEST -> domain check and owner check
		 * 
		 * In this test we will test edit a float.
		 * 
		 * Information requirements
		 * 
		 * 5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures.
		 * Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 * 
		 * Functional requirements
		 * 
		 * 10. An actor who is authenticated as a brotherhood must be able to:
		 * 1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 * 
		 * Analysis of sentence coverage
		 * 90%
		 * Analysis of data coverage
		 * 95.2%
		 */
		final Object testingData[][] = {
			// titleBlank, descriptionBlank, titleNull, descriptionNull, pictureNotValid
			{
				"brotherhood", 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 0, 1, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 0, 1, 1, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 0, 0, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 0, 0, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 0, 1, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 0, 1, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 1, 0, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 1, 0, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 1, 1, 0, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				"brotherhood", 1, 1, 1, 1, 0, ConstraintViolationException.class
			}, {
				"brotherhood", 1, 1, 1, 1, 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkEditNegative((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	protected void checkEditNegative(final String userName, final int titleBlank, final int descriptionBlank, final int titleNull, final int descriptionNull, final int picture, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate("brotherhood");

			final domain.Float floatt = this.floatService.create();
			floatt.setTitle("El título");
			floatt.setDescription("La descripción");
			floatt.setPictures("https://thumbs.gfycat.com/ColorlessViciousApisdorsatalaboriosa-size_restricted.gif");
			final domain.Float floattSaved = this.floatService.save(floatt);

			super.unauthenticate();
			super.authenticate(userName);

			if (titleBlank != 0 && titleNull == 0)
				floattSaved.setTitle("");
			else if (titleBlank == 0 && titleNull != 0)
				floattSaved.setTitle(null);
			else
				floattSaved.setTitle("Título editado");

			if (descriptionBlank != 0 && descriptionNull == 0)
				floattSaved.setDescription("");
			else if (descriptionBlank == 0 && descriptionNull != 0)
				floattSaved.setDescription(null);
			else
				floattSaved.setDescription("Descripción editada");

			if (picture != 0)
				floattSaved.setPictures("https://thumbs.gfycat.com/ColorlessViciousApisdorsatalaboriosa-size_restricted.gif'aodafomwedo://thumbs.gfycat.com/ColorlessViciousApisdorsatalaboriosa-size_restricted.gif");

			if (((titleBlank * titleNull) + (descriptionBlank * descriptionNull)) > 0 && picture == 0)
				// Caso positivo, forzamos a que sea inválido
				floattSaved.setTitle("");

			if ((titleBlank + titleNull + descriptionBlank + descriptionNull + picture) == 0) {
				//Comprobamos un brotherhood sin area
				super.unauthenticate();
				final Brotherhood brotherhood = this.brotherhoodService.create();
				brotherhood.setAddress("El Viso del Alcor");
				brotherhood.setEmail("alvdebon@alum.us.es");
				brotherhood.setEstablishmentDate(LocalDate.now().toDate());
				brotherhood.setMiddleName("alvarito");
				brotherhood.setName("Alvaro");
				brotherhood.setPhone("+34665381121");
				brotherhood.setSurname("J. JACK");
				brotherhood.setTitle("El título");
				final UserAccount user = new UserAccount();
				final List<Authority> autoridades = new ArrayList<>();
				final Authority authority = new Authority();
				authority.setAuthority(Authority.BROTHERHOOD);
				autoridades.add(authority);
				user.setAuthorities(autoridades);
				user.setUsername("alvarito");
				user.setPassword("pass");
				brotherhood.setUserAccount(user);
				this.brotherhoodService.save(brotherhood);
				super.authenticate("alvarito");
				final domain.Float floatt2 = this.floatService.create();
				floatt.setTitle("El título");
				floatt.setDescription("La descripción");
				floatt.setPictures("https://thumbs.gfycat.com/ColorlessViciousApisdorsatalaboriosa-size_restricted.gif");
				this.floatService.save(floatt2);
			}

			this.floatService.save(floattSaved);
			super.unauthenticate();
			this.floatService.flush();

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void test07() {
		/*
		 * POSITIVE TEST
		 * 
		 * In this test we will test list and show float.
		 * 
		 * Information requirements
		 * 
		 * 5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures.
		 * Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 * 
		 * Functional requirements
		 * 
		 * 10. An actor who is authenticated as a brotherhood must be able to:
		 * 1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 * 
		 * Analysis of sentence coverage
		 * 90%
		 * Analysis of data coverage
		 * 70%
		 */

		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate("brotherhood");

			final List<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			final List<domain.Float> floats = new ArrayList<domain.Float>();
			for (final Brotherhood broteherhood : brotherhoods)
				floats.addAll(broteherhood.getFloats());

			for (final domain.Float floatt : floats) {
				floatt.getId();
				floatt.getVersion();
				floatt.getBrotherhood();
				floatt.getTitle();
				floatt.getDescription();
				floatt.getPictures();
			}
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(null, caught);
	}

	@Test
	public void test08() {
		/*
		 * NEGATIVE TEST -> show a not save float
		 * 
		 * In this test we will test list and show float.
		 * 
		 * Information requirements
		 * 
		 * 5. Brotherhoods own floats, for which the system must store their title, description, and some optional pictures.
		 * Any of the floats that a brotherhood owns can be involved in any of the processions that they organise.
		 * 
		 * Functional requirements
		 * 
		 * 10. An actor who is authenticated as a brotherhood must be able to:
		 * 1. Manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 * 
		 * Analysis of sentence coverage
		 * 70%
		 * Analysis of data coverage
		 * 75%
		 */
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate("brotherhood");

			final List<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			for (final Brotherhood broteherhood : brotherhoods) {
				final List<domain.Float> floats = (List<domain.Float>) broteherhood.getFloats();
				floats.get(0).getId();
			}

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(IndexOutOfBoundsException.class, caught);
	}
}
