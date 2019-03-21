/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package sample;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AreaService;
import services.ParadeService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DashboardTest extends AbstractTest {

	@Autowired
	private AreaService		areaService;

	@Autowired
	private ParadeService	paradeService;


	@Test
	/*
	 * In this test we will test the dashboard.
	 * 
	 * I. R.
	 * 
	 * 5. There’s a new kind of actor in the system: chapters. For every chapter, the system must store its title. Every chapter co-ordinates
	 * and area and, thus, the parades organised by the brotherhoods in that area. No area can be co-ordinated by more than one chapter.
	 * 
	 * 6. Parades can now have paths, which are composed of segments. For every segment the sys-tem must store its origin and its destination
	 * (using GPS co-ordinates), the approximate time at which the parade is expected to be reaching the origin, and the approximate time at which
	 * it’s expected to be reaching the destination. The segments must be contiguous, that is: the destination of a segment must be the origin of the
	 * following segment (if any). When a parade is saved in final mode, it must be kept in status “submitted” until the corresponding chapter makes
	 * a decision on accepting or rejecting it. Only parades that have status accept-ed can be shown publicly.
	 * 
	 * F. R.
	 * 
	 * 1.The ratio of areas that are not co-ordinated by any chapters.
	 * 
	 * 2. The average, the minimum, the maximum, and the standard deviation of the number of parades co-ordinated by the chapters.
	 * 
	 * 3. The chapters that co-ordinate at least 10% more parades than the average.
	 * 
	 * 4. The ratio of parades in draft mode versus parades in final mode.
	 * 
	 * 5. The ratio of parades in final mode grouped by status.
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	public void DriverDashboard() {
		final Object testingData[][] = {
			{
				"admin", null
			}, {
				"brotherhood", IllegalArgumentException.class
			}, {
				"sponsor", IllegalArgumentException.class
			}, {
				"chapter", IllegalArgumentException.class
			}, {
				"member", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Template(final String userName, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);

			this.areaService.ratioAreaNoCoordinate();
			this.paradeService.avgParadeCapter();
			this.paradeService.ratioFinalACCEPTED();
			this.paradeService.ratioFinalREJECTED();
			this.paradeService.ratioFinalSUBMITTED();
			this.paradeService.ratioNoFinalNULL();
			this.paradeService.minParadeCapter();
			this.paradeService.maxParadeCapter();
			this.paradeService.avgParadeCapter();
			this.paradeService.stddevParadeCapter();
			this.paradeService.ParadeChapter();

			super.unauthenticate();

		} catch (final Exception oops) {
			oops.printStackTrace();
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

}
