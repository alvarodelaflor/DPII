/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package acmeParadeTest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ActorService;
import services.AreaService;
import services.BrotherhoodService;
import services.ChapterService;
import services.ConfigurationService;
import services.FinderService;
import services.HistoryService;
import services.MemberService;
import services.ParadeService;
import services.RequestService;
import services.SponsorService;
import services.SponsorshipService;
import services.WelcomeService;
import utilities.AbstractTest;
import domain.Member;
import domain.Parade;

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
	BrotherhoodService		brotherhoodService;

	@Autowired
	RequestService			requestService;

	@Autowired
	MemberService			memberService;

	@Autowired
	ActorService			actorService;

	@Autowired
	SponsorService			sponsorService;

	@Autowired
	SponsorshipService		sponsorshipService;

	@Autowired
	FinderService			finderService;

	@Autowired
	WelcomeService			welcomeService;

	@Autowired
	ConfigurationService	configurationService;

	@Autowired
	HistoryService			historyService;


	// ******NOTE: TO PROPERLY EXECUTE THIS TEST IT'S NECESSARY TO EXECUTE DashboardTestPopulateDatabase.java because we need to have a non variable set of data
	@Test
	/*
	 * 
	 * 
	 * In this test we will test the dashboard (ACME-PARADE).
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	public void DriverDashboard1() {

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

	@Test
	public void QueriesRequirement12() {

		final Object testingData[][] = {
			{
				"admin", 0.5, 0.0, 1.0, 0.5, "Hermandad de la VERA CRUZ", "Hermandad del Rosario", 0.0, 0.333, 0.333, null
			}, {
				"admin", 0.0, 0.0, 1.0, 0.5, "a", "b", 0.0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				"admin", 0.5, 1.0, 1.0, 0.5, "a", "b", 0.0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				"admin", 0.5, 0.0, 0.0, 0.5, "a", "b", 0.0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				"admin", 0.5, 0.0, 1.0, 1.0, "a", "b", 0.0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				"admin", 0.5, 0.0, 1.0, 0.5, "a", "Hermandad del Rosario", 0.0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				"admin", 0.5, 0.0, 1.0, 0.5, "Hermandad de la VERA CRUZ", "b", 0.0, 0.0, 0.0, IllegalArgumentException.class
			}, {
				"admin", 0.5, 0.0, 1.0, 0.5, "Hermandad de la VERA CRUZ", "Hermandad del Rosario", 1.0, 0.333, 0.333, IllegalArgumentException.class
			}, {
				"admin", 0.5, 0.0, 1.0, 0.5, "Hermandad de la VERA CRUZ", "Hermandad del Rosario", 0.0, 0.0, 0.333, IllegalArgumentException.class
			}, {
				"admin", 0.5, 0.0, 1.0, 0.5, "Hermandad de la VERA CRUZ", "Hermandad del Rosario", 0.0, 0.333, 0.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.QueriesRequirement12((String) testingData[i][0], (double) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (double) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(double) testingData[i][7], (double) testingData[i][8], (double) testingData[i][9], (Class<?>) testingData[i][10]);
	}
	// Ancillary methods ------------------------------------------------------

	protected void QueriesRequirement12(final String username, final Double avg, final Double min, final Double max, final Double stddev, final String largest, final String smallest, final Double rrpf, final Double rrpn, final Double rrpt,
		final Class<?> expected) {

		Class<?> caught = null;
		final DecimalFormat df = new DecimalFormat("#.###");

		try {

			this.startTransaction();
			super.authenticate(username);
			final Double a = Double.valueOf(df.format(this.memberService.avgNumberOfMemberPerBrotherhood()));
			final Double b = Double.valueOf(df.format(this.memberService.minNumberOfMemberPerBrotherhood()));
			final Double c = Double.valueOf(df.format(this.memberService.maxNumberOfMemberPerBrotherhood()));
			final Double d = Double.valueOf(df.format(this.memberService.desviationOfNumberOfMemberPerBrotherhood()));
			final Double g = Double.valueOf(df.format(this.requestService.getRatioRequestParadeStatusFalse()));
			final Double h = Double.valueOf(df.format(this.requestService.getRatioRequestParadeStatusNull()));
			final Double i = Double.valueOf(df.format(this.requestService.getRatioRequestParadeStatusTrue()));
			Assert.isTrue(avg.equals(a));
			Assert.isTrue(min.equals(b));
			Assert.isTrue(max.equals(c));
			Assert.isTrue(stddev.equals(d));
			Assert.isTrue(g.equals(rrpf));
			Assert.isTrue(h.equals(rrpn));
			Assert.isTrue(i.equals(rrpt));
			super.unauthenticate();
		} catch (final Exception oops) {

			caught = oops.getClass();
		} finally {

			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
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

	/////////////////////////////////////////////////////////////////////////
	// ******NOTE: TO PROPERLY EXECUTE THIS TEST IT'S NECESSARY TO EXECUTE DashboardTestPopulateDatabase.java because we need to have a non variable set of data
	@Test
	/*
	 * 
	 * 
	 * In this test we will test the dashboard (ACME-MADRUGA).
	 * 
	 * Analysis of sentence coverage
	 * TODO
	 * Analysis of data coverage
	 * TODO
	 */
	public void DriverDashboard2() {

		final Collection<Member> res = new ArrayList<>();
		final Collection<Parade> res1 = new ArrayList<>();

		final Collection<String> res2 = new ArrayList<>();
		res2.add("Hermandad de la VERA CRUZ");
		final Collection<String> res3 = new ArrayList<>();
		res3.add("Hermandad del Rosario");

		final Map<String, Long> map = new HashMap<>();
		map.put("Carmen", 0L);
		map.put("Pregonar", 2L);
		map.put("Hola", 0L);

		final Map<String, Long> map1 = new HashMap<>();

		final Object testingData[][] = {
			{
				// Test positivos de resultados 
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, null
			}, {
				// Test negativos de actor
				"chapter", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"sponsor", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"brotherhood", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"member", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				null, 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				// Test negativos de datos erroneos
				"admin", 1.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 2.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 2.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.20, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.20, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 2.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res2, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 2.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.2, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.52, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.52, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res, "", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 1, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 8, 0.66, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.3, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 22.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.94, 0.3, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.4, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 1.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 2.0, 0.0, 100.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 10.0, 0.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 2.0, res2, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res, res3, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res, map, IllegalArgumentException.class
			}, {
				"admin", 0.0, 0.0, 1.0, 0.0, 0.0, 0.333, res, 1.0, 0.0, 0.5, 0.5, res1, "Virgen del Rosario", "Virgen del Rosario", 10, 9, 0.667, 0.0, 2.0, 0.943, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 0.0, res2, res3, map1, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Template((String) testingData[i][0], (double) testingData[i][1], (double) testingData[i][2],

			(double) testingData[i][3], (double) testingData[i][4], (double) testingData[i][5], (double) testingData[i][6],

			(Collection<Member>) testingData[i][7], (double) testingData[i][8], (double) testingData[i][9],

			(double) testingData[i][10], (double) testingData[i][11], (Collection<Parade>) testingData[i][12], (String) testingData[i][13],

			(String) testingData[i][14], (int) testingData[i][15], (int) testingData[i][16],

			(double) testingData[i][17], (double) testingData[i][18], (double) testingData[i][19], (double) testingData[i][20],

			(double) testingData[i][21], (double) testingData[i][22], (double) testingData[i][23], (double) testingData[i][24], (double) testingData[i][25],

			(double) testingData[i][26], (double) testingData[i][27],

			(Collection<String>) testingData[i][28], (Collection<String>) testingData[i][29],

			(Map<String, Long>) testingData[i][30],

			(Class<?>) testingData[i][31]);
	}
	// Ancillary methods ------------------------------------------------------

	protected void Template(final String userName, final Double getRatioRequestStatusTrue, final Double getRatioRequestStatusFalse, final Double getRatioRequestStatusNull, final Double getRatioRequestParadeStatusTrue,
		final Double getRatioRequestParadeStatusFalse, final Double getRatioRequestParadeStatusNull, final Collection<Member> lisMemberAccept, final Double maxNumberOfMemberPerBrotherhood, final Double minNumberOfMemberPerBrotherhood,
		final Double avgNumberOfMemberPerBrotherhood, final Double desviationOfNumberOfMemberPerBrotherhood, final Collection<Parade> paradeOrganised, final String minParade, final String maxParade, final Integer minParadeN, final Integer maxParadeN,

		final Double avgBrotherhoodPerArea, final Double minBrotherhoodPerArea, final Double maxBrotherhoodPerArea, final Double stddevBrotherhoodPerArea,

		final Double minNumberOfResult, final Double maxNumberOfResult, final Double avgNumberOfResult, final Double stddevNumberOfResult, final Double ratioFinder,

		final Double noSpammersRation, final Double spammersRation,

		final Collection<String> largestBrotherhood, final Collection<String> smallestBrotherhood,

		final Map<String, Long> map,

		final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);

			final DecimalFormat df = new DecimalFormat("#.###");

			final Collection<Object[]> countBrotherhoodPerArea = this.brotherhoodService.countBrotherhoodPerArea();
			final Map<String, Long> map1 = new HashMap<>();
			for (final Object[] o : countBrotherhoodPerArea)
				map1.put((String) o[0], (Long) o[1]);

			Assert.isTrue(map.equals(map1));
			Assert.isTrue(largestBrotherhood.equals((this.brotherhoodService.largestBrotherhood())));
			Assert.isTrue(smallestBrotherhood.equals((this.brotherhoodService.smallestBrotherhood())));
			Assert.isTrue(noSpammersRation.equals(Double.valueOf(df.format(this.actorService.noSpammersRation()))));
			Assert.isTrue(spammersRation.equals(Double.valueOf(df.format(this.actorService.spammersRation()))));
			Assert.isTrue(avgBrotherhoodPerArea.equals(Double.valueOf(df.format(this.brotherhoodService.avgBrotherhoodPerArea()))));
			Assert.isTrue(minBrotherhoodPerArea.equals(Double.valueOf(df.format(this.brotherhoodService.minBrotherhoodPerArea()))));
			Assert.isTrue(maxBrotherhoodPerArea.equals(Double.valueOf(df.format(this.brotherhoodService.maxBrotherhoodPerArea()))));
			Assert.isTrue(stddevBrotherhoodPerArea.equals(Double.valueOf(df.format(this.brotherhoodService.stddevBrotherhoodPerArea()))));
			Assert.isTrue(minNumberOfResult.equals(Double.valueOf(df.format(this.finderService.minNumberOfResult()))));
			Assert.isTrue(maxNumberOfResult.equals(Double.valueOf(df.format(this.finderService.maxNumberOfResult()))));
			Assert.isTrue(avgNumberOfResult.equals(Double.valueOf(df.format(this.finderService.avgNumberOfResult()))));
			Assert.isTrue(stddevNumberOfResult.equals(Double.valueOf(df.format(this.finderService.stddevNumberOfResult()))));
			Assert.isTrue(getRatioRequestStatusTrue.equals(Double.valueOf(df.format(this.requestService.getRatioRequestStatusTrue()))));
			Assert.isTrue(getRatioRequestStatusFalse.equals(Double.valueOf(df.format(this.requestService.getRatioRequestStatusFalse()))));
			Assert.isTrue(getRatioRequestStatusNull.equals(Double.valueOf(df.format(this.requestService.getRatioRequestStatusNull()))));
			Assert.isTrue(getRatioRequestParadeStatusTrue.equals(Double.valueOf(df.format(this.requestService.getRatioRequestParadeStatusTrue()))));
			Assert.isTrue(getRatioRequestParadeStatusFalse.equals(Double.valueOf(df.format(this.requestService.getRatioRequestParadeStatusFalse()))));
			Assert.isTrue(getRatioRequestParadeStatusNull.equals(Double.valueOf(df.format(this.requestService.getRatioRequestParadeStatusNull()))));
			Assert.isTrue(lisMemberAccept.equals((this.memberService.lisMemberAccept())));
			Assert.isTrue(maxNumberOfMemberPerBrotherhood.equals(Double.valueOf(df.format(this.memberService.maxNumberOfMemberPerBrotherhood()))));
			Assert.isTrue(minNumberOfMemberPerBrotherhood.equals(Double.valueOf(df.format(this.memberService.minNumberOfMemberPerBrotherhood()))));
			Assert.isTrue(avgNumberOfMemberPerBrotherhood.equals(Double.valueOf(df.format(this.memberService.avgNumberOfMemberPerBrotherhood()))));
			Assert.isTrue(desviationOfNumberOfMemberPerBrotherhood.equals(Double.valueOf(df.format(this.memberService.desviationOfNumberOfMemberPerBrotherhood()))));
			Assert.isTrue(paradeOrganised.equals(this.paradeService.paradeOrganised()));
			Assert.isTrue(minParade.equals(this.paradeService.minParade()));
			Assert.isTrue(maxParade.equals(this.paradeService.maxParade()));
			Assert.isTrue(minParadeN.equals(this.paradeService.minParadeN()));
			Assert.isTrue(maxParadeN.equals((this.paradeService.maxParadeN())));

			super.unauthenticate();

		} catch (final Exception oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
