/*
 * HistoryTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package acmeParadeTest;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import services.BrotherhoodService;
import services.FloatService;
import services.ParadeService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.Parade;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ParadeTest extends AbstractTest {

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private FloatService		floatService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Tests ------------------------------------------------------------------
	/*
	 * 
	 * In this test we will test the create of Parade.
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	@Test
	public void driver1() {
		final Object testingData[][] = {

			{
				0, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class

			}, {
				0, 1, 1, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class

			}, {
				0, 0, 1, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class

			}, {
				0, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class

			}, {
				0, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class

			}, {
				1, 1, 1, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 1, 1, 1, null
			}, {
				1, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 1, 1, 1, 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.test((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);

	}
	public void test(final int title, final int description, final int moment, final int ifFinal, final int maxRox, final int maxColum, final int floatt, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			super.authenticate("brotherhood");

			final Parade p = this.paradeService.create();

			p.setBrotherhood(this.brotherhoodService.findOne(super.getEntityId("brotherhood01")));
			System.out.println("llego");

			if (title != 0)
				p.setTitle("soyUnTitulo");
			else
				p.setTitle("");

			if (description != 0)
				p.setDescription("soyUnaDescripcion");
			else
				p.setDescription("");
			System.out.println("llego");

			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 3);

			if (moment != 0)
				p.setMoment(res);
			else
				p.setMoment(null);
			System.out.println("llego");

			if (ifFinal != 0)
				p.setIsFinal(true);
			else
				p.setIsFinal(false);

			if (maxColum != 0)
				p.setMaxColum(4);
			else
				p.setMaxColum(-1);

			if (maxRox != 0)
				p.setMaxRow(10);
			else
				p.setMaxRow(-1);

			if (floatt != 0) {
				final domain.Float f = this.floatService.create();
				f.setDescription("soyUnDescripcion");
				f.setPictures("http://soyUnaFoto");
				f.setTitle("soyUnTitulo");
				f.setBrotherhood(this.brotherhoodService.findOne(super.getEntityId("brotherhood01")));
				final domain.Float fSave = this.floatService.save(f);
				p.setFloatt(fSave);
			}

			final Parade p1 = this.paradeService.save(p);

			Assert.notNull(p1.getFloatt());

			this.paradeService.flush();

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

	@Test
	public void driver2() {
		/*
		 * POSITIVE TEST
		 * 
		 * In this test we will test the create of Parade.
		 * 
		 * Analysis of sentence coverage
		 * TODO
		 * Analysis of data coverage
		 * TODO
		 */
		final Object testingData[][] = {
			// username, error
			{
				null, IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}, {
				"chapter", IllegalArgumentException.class
			}, {
				"brotherhood", null
			}, {
				"member", IllegalArgumentException.class
			}, {
				"sponsor", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.test1((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void test1(final String actor, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			super.authenticate(actor);

			final Authority authority = new Authority();
			authority.setAuthority(Authority.BROTHERHOOD);
			Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));

			final Parade p = this.paradeService.create();

			p.setBrotherhood(this.brotherhoodService.findOne(super.getEntityId("brotherhood01")));
			System.out.println("llego");

			p.setTitle("soyUnTitulo");
			p.setDescription("soyUnaDescripcion");
			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 3);

			p.setMoment(res);

			p.setIsFinal(false);

			p.setMaxColum(4);

			p.setMaxRow(10);

			final domain.Float f = this.floatService.create();
			f.setDescription("soyUnDescripcion");
			f.setPictures("http://soyUnaFoto");
			f.setTitle("soyUnTitulo");
			f.setBrotherhood(this.brotherhoodService.findOne(super.getEntityId("brotherhood01")));
			final domain.Float fSave = this.floatService.save(f);
			p.setFloatt(fSave);

			final Parade p1 = this.paradeService.save(p);

			Assert.notNull(p1.getFloatt());

			this.paradeService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	////////////////////////////////////////////////////////////////////////////////////

	// Tests ------------------------------------------------------------------
	/*
	 * 
	 * In this test we will test the edit of Parade with status is false
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	@Test
	public void driver3() {
		final Object testingData[][] = {

			{
				0, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class

			}, {
				0, 1, 1, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class

			}, {
				0, 0, 1, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class

			}, {
				0, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class

			}, {
				0, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				0, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				0, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				0, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 1, 1, 1, 1, ConstraintViolationException.class

			}, {
				1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class

			}, {
				1, 1, 1, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 1, 1, 1, null
			}, {
				1, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			}, {
				1, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 1, 1, ConstraintViolationException.class
			}, {
				1, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 0, 1, 1, ConstraintViolationException.class
			}, {
				1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			}, {
				1, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 1, 1, 1, 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.test3((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);

	}
	public void test3(final int title, final int description, final int moment, final int ifFinal, final int maxRox, final int maxColum, final int floatt, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate("brotherhood");

			final Brotherhood brotherhood = this.brotherhoodService.findOne(super.getEntityId("brotherhood01"));

			final Parade p = this.paradeService.create();

			p.setBrotherhood(this.brotherhoodService.findOne(super.getEntityId("brotherhood01")));
			System.out.println("llego");

			p.setTitle("soyUnTitulo");
			p.setDescription("soyUnaDescripcion");
			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 3);

			p.setMoment(res);

			p.setIsFinal(false);

			p.setMaxColum(4);

			p.setMaxRow(10);

			final domain.Float f = this.floatService.create();
			f.setDescription("soyUnDescripcion");
			f.setPictures("http://soyUnaFoto");
			f.setTitle("soyUnTitulo");
			f.setBrotherhood(this.brotherhoodService.findOne(super.getEntityId("brotherhood01")));
			final domain.Float fSave = this.floatService.save(f);
			p.setFloatt(fSave);

			final Parade parade = this.paradeService.save(p);

			Assert.isTrue(!p.getIsFinal());

			//// EDITAR

			final Parade parade1 = this.paradeService.update(parade);

			if (title != 0)
				parade.setTitle("soyUnTitulo");
			else
				parade.setTitle("");

			if (description != 0)
				parade.setDescription("soyUnaDescripcion");
			else
				parade.setDescription("");
			System.out.println("llego");

			final Date res1 = LocalDateTime.now().toDate();
			res1.setMonth(res1.getMonth() + 4);

			if (moment != 0)
				parade.setMoment(res1);
			else
				parade.setMoment(null);
			System.out.println("llego");

			if (ifFinal != 0)
				parade.setIsFinal(true);
			else
				parade.setIsFinal(false);

			if (maxColum != 0)
				parade.setMaxColum(4);
			else
				parade.setMaxColum(-1);

			if (maxRox != 0)
				parade.setMaxRow(10);
			else
				parade.setMaxRow(-1);

			if (floatt != 1)
				parade.setFloatt(null);

			final Parade paradeS = this.paradeService.save(parade);

			Assert.notNull(paradeS.getFloatt());

			this.paradeService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	////////////////////////////////////////////////////////////////////////////////////

	// Tests ------------------------------------------------------------------
	/*
	 * 
	 * In this test we will test the edit of Parade with status is true
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	@Test
	public void driver4() {
		final Object testingData[][] = {

			{
				0, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				0, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				0, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class

			}, {
				0, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				0, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				0, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class

			}, {
				0, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class

			}, {
				0, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class

			}, {
				0, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				0, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				0, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				0, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				0, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				0, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class

			}, {
				1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				1, 0, 0, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				1, 1, 1, 1, 0, 1, 0, IllegalArgumentException.class

			}, {
				1, 1, 1, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				1, 0, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				1, 0, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 0, 0, 1, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 0, 1, 1, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 0, 1, 1, 1, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 0, 0, 1, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 0, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 1, 0, 0, IllegalArgumentException.class
			}, {
				1, 0, 0, 0, 1, 0, 1, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 0, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 0, 1, 1, IllegalArgumentException.class
			}, {
				1, 1, 0, 0, 0, 0, 0, IllegalArgumentException.class
			}, {
				1, 1, 0, 1, 1, 0, 1, IllegalArgumentException.class
			}, {
				1, 1, 1, 1, 1, 1, 0, IllegalArgumentException.class
			}, {
				1, 1, 1, 1, 1, 1, 1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.test4((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);

	}
	public void test4(final int title, final int description, final int moment, final int ifFinal, final int maxRox, final int maxColum, final int floatt, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate("brotherhood");

			final Brotherhood brotherhood = this.brotherhoodService.findOne(super.getEntityId("brotherhood01"));

			final Parade p = this.paradeService.create();

			p.setBrotherhood(this.brotherhoodService.findOne(super.getEntityId("brotherhood01")));
			System.out.println("llego");

			p.setTitle("soyUnTitulo");
			p.setDescription("soyUnaDescripcion");
			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 3);

			p.setMoment(res);

			p.setIsFinal(true);

			p.setMaxColum(4);

			p.setMaxRow(10);

			final domain.Float f = this.floatService.create();
			f.setDescription("soyUnDescripcion");
			f.setPictures("http://soyUnaFoto");
			f.setTitle("soyUnTitulo");
			f.setBrotherhood(this.brotherhoodService.findOne(super.getEntityId("brotherhood01")));
			final domain.Float fSave = this.floatService.save(f);
			p.setFloatt(fSave);

			final Parade parade = this.paradeService.save(p);

			Assert.isTrue(!p.getIsFinal());

			//// EDITAR

			final Parade parade2 = this.paradeService.update(parade);

			if (title != 0)
				parade2.setTitle("soyUnTitulo");
			else
				parade2.setTitle("");

			if (description != 0)
				parade2.setDescription("soyUnaDescripcion");
			else
				parade2.setDescription("");
			System.out.println("llego");

			final Date res1 = LocalDateTime.now().toDate();
			res1.setMonth(res1.getMonth() + 4);

			if (moment != 0)
				parade2.setMoment(res1);
			else
				parade2.setMoment(null);
			System.out.println("llego");

			if (ifFinal != 0)
				parade2.setIsFinal(true);
			else
				parade2.setIsFinal(false);

			if (maxColum != 0)
				parade2.setMaxColum(4);
			else
				parade2.setMaxColum(-1);

			if (maxRox != 0)
				parade2.setMaxRow(10);
			else
				parade2.setMaxRow(-1);

			if (floatt != 1)
				parade2.setFloatt(null);

			final Parade paradeS = this.paradeService.save(parade2);

			Assert.notNull(paradeS.getFloatt());

			this.paradeService.flush();

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

	@Test
	public void driver5() {
		/*
		 * 
		 * In this test we will test the edit of Parade.
		 * 
		 * Analysis of sentence coverage
		 * TODO
		 * Analysis of data coverage
		 * TODO
		 */
		final Object testingData[][] = {
			// username, error
			{
				null, IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}, {
				"chapter", IllegalArgumentException.class
			}, {
				"brotherhood", null
			}, {
				"member", IllegalArgumentException.class
			}, {
				"sponsor", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.test5((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void test5(final String actor, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			super.authenticate(actor);

			final Authority authority = new Authority();
			authority.setAuthority(Authority.BROTHERHOOD);
			Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));

			final Parade p = this.paradeService.create();

			p.setBrotherhood(this.brotherhoodService.findOne(super.getEntityId("brotherhood01")));
			System.out.println("llego");

			p.setTitle("soyUnTitulo");
			p.setDescription("soyUnaDescripcion");
			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 3);

			p.setMoment(res);

			p.setIsFinal(false);

			p.setMaxColum(4);

			p.setMaxRow(10);

			final domain.Float f = this.floatService.create();
			f.setDescription("soyUnDescripcion");
			f.setPictures("http://soyUnaFoto");
			f.setTitle("soyUnTitulo");
			f.setBrotherhood(this.brotherhoodService.findOne(super.getEntityId("brotherhood01")));
			final domain.Float fSave = this.floatService.save(f);
			p.setFloatt(fSave);

			final Parade parade = this.paradeService.save(p);

			Assert.isTrue(!p.getIsFinal());

			//// EDITAR

			parade.setTitle("soyUnTitulo");

			final Parade paradeS = this.paradeService.save(parade);

			Assert.notNull(paradeS.getFloatt());

			this.paradeService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

}
