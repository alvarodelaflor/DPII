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

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.BrotherhoodService;
import services.FloatService;
import services.ParadeService;
import utilities.AbstractTest;
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
			//	
			//			{
			//				0, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 0, 0, 0, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 0, 0, 1, 1, 1, null
			//			}, {
			//				0, 0, 0, 1, 1, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 1, 1, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 1, 1, 0, 1, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 1, 0, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 0, 1, 1, 1, null
			//			}, {
			//				0, 0, 0, 1, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 0, 0, 1, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 1, 1, 1, 1, 1, null
			//			}, {
			//				0, 0, 1, 0, 1, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 1, 0, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 1, 0, 0, 1, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 1, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 1, 1, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 1, 1, 1, 1, null
			//			}, {
			//				0, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 0, 1, 0, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 1, 0, 1, 1, 1, null
			//			}, {
			//				0, 0, 1, 1, 1, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 1, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 1, 0, 1, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 1, 0, 1, 1, 1, null
			//			}, {
			//				0, 0, 1, 1, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 0, 1, 1, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 1, 1, 1, 1, 1, null
			//			}, {
			//				0, 0, 0, 0, 1, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 0, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 0, 0, 1, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				0, 1, 1, 1, 1, 1, 0, ConstraintViolationException.class
			//			}, {
			//				0, 1, 1, 1, 1, 1, 1, null
			//			}, {
			//				1, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 0, 0, 0, 0, 1, ConstraintViolationException.class
			//			}, {
			//				1, 0, 0, 0, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 0, 0, 1, 1, 1, null
			//			}, {
			//				1, 0, 0, 1, 1, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				1, 1, 1, 1, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				1, 1, 1, 1, 0, 1, 1, ConstraintViolationException.class
			//			}, {
			//				1, 1, 1, 0, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 1, 1, 0, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 0, 1, 1, 1, null
			//			}, {
			//				1, 0, 0, 1, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 0, 1, 0, 0, 1, ConstraintViolationException.class
			//			}, {
			//				1, 0, 0, 1, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 1, 1, 1, 1, 1, null
			//			}, {
			//				1, 0, 1, 0, 1, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 1, 0, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				1, 1, 1, 0, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				1, 1, 1, 0, 0, 1, 1, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 1, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 1, 1, 1, 0, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 1, 1, 1, 1, null
			//			}, {
			//				1, 0, 0, 0, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 1, 0, 0, 0, 1, ConstraintViolationException.class
			//			}, {
			//				1, 0, 1, 0, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 1, 0, 1, 1, 1, null
			//			}, {
			//				1, 0, 1, 1, 1, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 1, 1, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 1, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 1, 0, 1, 1, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 0, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 0, 1, 1, 0, ConstraintViolationException.class
			//			}, {
			//				1, 1, 1, 0, 1, 1, 1, null
			//			}, {
			//				1, 0, 1, 1, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 1, 1, 0, 0, 1, ConstraintViolationException.class
			//			}, {
			//				1, 0, 1, 1, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 1, 1, 1, 1, 1, null
			//			}, {
			//				1, 0, 0, 0, 1, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 0, 0, 0, 1, 0, 1, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 0, 0, 1, 0, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 0, 0, 1, 1, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 0, 0, 0, 0, ConstraintViolationException.class
			//			}, {
			//				1, 1, 0, 1, 1, 0, 1, ConstraintViolationException.class
			//			}, 
			{
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
			System.out.println("llego");

			if (maxRox != 0)
				p.setMaxRow(10);

			if (floatt != 0) {
				final domain.Float f = this.floatService.create();
				f.setDescription("soyUnDescripcion");
				f.setPictures("http://soyUnaFoto");
				f.setTitle("soyUnTitulo");
				f.setBrotherhood(this.brotherhoodService.findOne(super.getEntityId("brotherhood01")));
				final domain.Float fSave = this.floatService.save(f);
				p.setFloatt(fSave);
			}

			this.paradeService.save(p);

			Assert.notNull(p.getFloatt());

			//this.paradeService.flush();
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
}
