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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.AreaService;
import services.ChapterService;
import services.HistoryService;
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

	@Autowired
	private ChapterService	chapterService;

	@Autowired
	private HistoryService	historyService;


	// ******NOTE: TO PROPERLY EXECUTE THIS TEST IT'S NECESSARY TO EXECUTE DashboardTestPopulateDatabase.java because we need to have a non variable set of data
	@Test
	/*
	 * 
	 * 
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
	public void DriverDashboard2() {

		final Collection<String> res = new ArrayList<>();
		res.add(this.chapterService.findOne(807).getName());

		final Collection<String> res1 = new ArrayList<>();

		final Object testingData[][] = {
			{
				// Test positivos de resultados 
				"admin", 0.333, 0.333, 0.0, 0.667, 0.5, 3.0, 3.0, 3.0, 0.0, res, null
			}, {
				// Test negativos de actor
				"brotherhood", 0.333, 0.333, 0.0, 0.667, 0.5, 3.0, 3.0, 3.0, 0.0, res, IllegalArgumentException.class
			}, {
				"chapter", 0.333, 0.333, 0.0, 0.667, 0.5, 3.0, 3.0, 3.0, 0.0, res, IllegalArgumentException.class
			}, {
				"sponsor", 0.333, 0.333, 0.0, 0.667, 0.5, 3.0, 3.0, 3.0, 0.0, res, IllegalArgumentException.class
			}, {
				"member", 0.333, 0.333, 0.0, 0.667, 0.5, 3.0, 3.0, 3.0, 0.0, res, IllegalArgumentException.class
			}, {
				// Test negativos datos erroneos
				"admin", 0.0, 0.333, 0.0, 0.667, 0.5, 3.0, 3.0, 3.0, 0.0, res, IllegalArgumentException.class
			}, {
				"admin", 0.333, 0.0, 0.0, 0.667, 0.5, 3.0, 3.0, 3.0, 0.0, res, IllegalArgumentException.class
			}, {
				"admin", 0.333, 0.333, 0.1, 0.667, 0.5, 3.0, 3.0, 3.0, 0.0, res, IllegalArgumentException.class
			}, {
				"admin", 0.333, 0.333, 0.0, 0.662, 0.5, 3.0, 3.0, 3.0, 0.0, res, IllegalArgumentException.class
			}, {
				"admin", 0.333, 0.333, 0.0, 0.667, 0.2, 3.0, 3.0, 3.0, 0.0, res, IllegalArgumentException.class
			}, {
				"admin", 0.333, 0.333, 0.0, 0.667, 0.5, 3.3, 3.0, 3.0, 0.0, res, IllegalArgumentException.class
			}, {
				"admin", 0.333, 0.333, 0.0, 0.667, 0.5, 3.0, 0.0, 3.0, 0.0, res, IllegalArgumentException.class
			}, {
				"admin", 0.333, 0.333, 0.0, 0.667, 0.5, 3.0, 3.0, 3.90, 0.0, res, IllegalArgumentException.class
			}, {
				"admin", 0.333, 0.333, 0.0, 0.667, 0.5, 3.0, 3.0, 3.0, 0.2, res, IllegalArgumentException.class
			}, {
				"admin", 0.333, 0.333, 0.0, 0.667, 0.5, 3.0, 3.0, 3.0, 0.0, res1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Template2((String) testingData[i][0], (double) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (double) testingData[i][4], (double) testingData[i][5], (double) testingData[i][6], (double) testingData[i][7],
				(double) testingData[i][8], (double) testingData[i][9], (Collection<String>) testingData[i][10], (Class<?>) testingData[i][11]);
	}

	public void Requirement4Queries() {

		final Object testingData[][] = {
			{
				// Test positivos de resultados 
				"admin", 0.333, 0.333, 0.0, 0.667, null
			}, {
				// Test negativos de actor
				"admin", 0.333, 0.333, 0.0, 0.667, null
			}, {
				// Test negativos datos erroneos
				"admin", 0.333, 0.333, 0.0, 0.667, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Requirement4Queries((String) testingData[i][0], (double) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (double) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void Template2(final String userName, final Double ratioFinalSUBMITTED, final Double ratioFinalACCEPTED, final Double ratioFinalREJECTED, final Double ratioAreaNoCoordinate, final Double ratioNoFinalNULL, final Double minParadeCapter,
		final Double maxParadeCapter, final Double avgParadeCapter, final Double stddevParadeCapter, final Collection<String> ParadeChapter, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);

			final DecimalFormat df = new DecimalFormat("#.###");

			Assert.isTrue(ratioFinalSUBMITTED.equals(Double.valueOf(df.format(this.paradeService.ratioFinalSUBMITTED()))));
			Assert.isTrue(ratioFinalACCEPTED.equals(Double.valueOf(df.format(this.paradeService.ratioFinalACCEPTED()))));
			Assert.isTrue(ratioFinalREJECTED.equals(Double.valueOf(df.format(this.paradeService.ratioFinalREJECTED()))));
			Assert.isTrue(ratioAreaNoCoordinate.equals(Double.valueOf(df.format(this.areaService.ratioAreaNoCoordinate()))));
			Assert.isTrue(ratioNoFinalNULL.equals(Double.valueOf(df.format(this.paradeService.ratioNoFinalNULL()))));

			Assert.isTrue(minParadeCapter.equals(Double.valueOf(df.format(this.paradeService.minParadeCapter()))));
			Assert.isTrue(maxParadeCapter.equals(Double.valueOf(df.format(this.paradeService.maxParadeCapter()))));
			Assert.isTrue(avgParadeCapter.equals(Double.valueOf(df.format(this.paradeService.avgParadeCapter()))));
			Assert.isTrue(stddevParadeCapter.equals(Double.valueOf(df.format(this.paradeService.stddevParadeCapter()))));

			Assert.isTrue(ParadeChapter.equals(this.paradeService.ParadeChapter()));

			super.unauthenticate();

		} catch (final Exception oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	//1. The average, the minimum, the maximum, and the standard deviation of the number of records per history.
	//2. The brotherhood with the largest history.
	//3. Brotherhood whose history is larger than the average.

	protected void Requirement4Queries(final String username, final Double avg, final Double max, final Double min, final Double stddev, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(username);
			final DecimalFormat df = new DecimalFormat("#.###");

			Assert.isTrue(avg.equals(Double.valueOf(df.format(this.historyService.avgRecordPerHistory()))));
			Assert.isTrue(max.equals(Double.valueOf(df.format(this.historyService.minRecordPerHistory()))));
			Assert.isTrue(min.equals(Double.valueOf(df.format(this.historyService.maxRecordPerHistory()))));
			Assert.isTrue(stddev.equals(Double.valueOf(df.format(this.historyService.stddevRecordPerHistory()))));

			super.unauthenticate();
		} catch (final Exception oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
