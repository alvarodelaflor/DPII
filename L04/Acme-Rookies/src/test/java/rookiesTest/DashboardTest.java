/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package rookiesTest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Provider;
import services.ActorService;

import services.ConfigurationService;
import services.FinderService;
import services.ItemService;
import services.ProviderService;
import services.SponsorshipService;
import utilities.AbstractTest;


@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DashboardTest extends AbstractTest {

	@Autowired
	ActorService				actorService;
	
	@Autowired
	ItemService					itemService;
	
	@Autowired
	SponsorshipService			sponsorshipService;
	
	@Autowired
	ProviderService			providerService;

	@Test
	public void driverR11() {

		final float min = Float.valueOf((float) 2.0);
		final float max = Float.valueOf((float) 2.0);
		final float avg = Float.valueOf((float) 2.0);
		final float sttdev = Float.valueOf((float) 0.0);
		final float one = Float.valueOf((float)1.0);
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				min, max, avg, sttdev, null
			} ,{
				one,min,max,sttdev, IllegalArgumentException.class
			},{
				min,one,max,sttdev, IllegalArgumentException.class
			},{
				min,min,one,sttdev, IllegalArgumentException.class
			},{
				min,min,max,one, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDashR11((Float) testingData[i][0], (Float) testingData[i][1], (Float) testingData[i][2], (Float) testingData[i][3], (Class<?>) testingData[i][4]);

	}
	public void testDashR11(final Float min, final Float max, final Float avg, final Float sttdev, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate("admin");
			Assert.isTrue(min.equals(this.itemService.minItemPerProvider()));
			Assert.isTrue(max.equals(this.itemService.maxItemPerProvider()));
			Assert.isTrue(avg.equals(this.itemService.avgItemPerProvider()));
			Assert.isTrue(sttdev.equals(this.itemService.sttdevItemPerProvider()));
			
			Assert.isTrue(providerService.ProviderItem().size() == 1);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void driverR14() {

		final float minProvider = Float.valueOf((float) 0.0);
		final float maxProvider = Float.valueOf((float) 0.0);
		final float avgProvider = Float.valueOf((float) 0.0);
		final float sttdevProvider = Float.valueOf((float) 0.0);
		
		final float minPosistion = Float.valueOf((float) 0.0);
		final float maxPosition = Float.valueOf((float) 0.0);
		final float avgPosition = Float.valueOf((float) 0.0);
		final float sttdevPosition = Float.valueOf((float) 0.0);
		
		final float one = Float.valueOf((float)1.0);
		final Object testingData[][] = {
			//	middleName, address, photo, phone
			{
				minProvider, maxProvider, avgProvider, sttdevProvider,minPosistion, maxPosition, avgPosition, sttdevPosition, null
			},{
				one, maxProvider, avgProvider, sttdevProvider,minPosistion, maxPosition, avgPosition, sttdevPosition, IllegalArgumentException.class
			} ,{
				minProvider, one, avgProvider, sttdevProvider,minPosistion, maxPosition, avgPosition, sttdevPosition, IllegalArgumentException.class
			},{
				minProvider, maxProvider, one, sttdevProvider,minPosistion, maxPosition, avgPosition, sttdevPosition, IllegalArgumentException.class
			},{
				minProvider, maxProvider, avgProvider, one,minPosistion, maxPosition, avgPosition, sttdevPosition, IllegalArgumentException.class
			},{
				minProvider, maxProvider, avgProvider, sttdevProvider,one, maxPosition, avgPosition, sttdevPosition, IllegalArgumentException.class
			},{
				minProvider, maxProvider, avgProvider, sttdevProvider,minPosistion, one, avgPosition, sttdevPosition, IllegalArgumentException.class
			},{
				minProvider, maxProvider, avgProvider, sttdevProvider,minPosistion, maxPosition, one, sttdevPosition, IllegalArgumentException.class
			},{
				minProvider, maxProvider, avgProvider, sttdevProvider,minPosistion, maxPosition, avgPosition, one, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.testDashR14((Float) testingData[i][0], (Float) testingData[i][1], (Float) testingData[i][2], (Float) testingData[i][3],(Float) testingData[i][4],(Float) testingData[i][5],(Float) testingData[i][6],(Float) testingData[i][7], (Class<?>) testingData[i][8]);

	}
	public void testDashR14(final Float minProvider, final Float maxProvider, final Float avgProvider, final Float sttdevProvider,final Float minPosition, final Float maxPosition, final Float avgPosition, final Float sttdevPosition, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate("admin");
			Assert.isTrue(minProvider.equals(this.sponsorshipService.minSponsorshipPerProvider()));
			Assert.isTrue(maxProvider.equals(sponsorshipService.maxSponsorshipPerProvider()));
			Assert.isTrue(avgProvider.equals(sponsorshipService.avgSponsorshipPerProvider()));
			Assert.isTrue(sttdevProvider.equals(sponsorshipService.sttdevSponsorshipPerProvider()));
			
			Assert.isTrue(minPosition.equals(sponsorshipService.minSponsorshipPerPosition()));
			Assert.isTrue(maxPosition.equals(sponsorshipService.maxSponsorshipPerPosition()));
			Assert.isTrue(avgPosition.equals(sponsorshipService.avgSponsorshipPerPosition()));
			Assert.isTrue(sttdevPosition.equals(sponsorshipService.sttdevSponsorshipPerPosition()));
			
			Collection<Provider> providers = providerService.sponsorshipProvider();
			Assert.isTrue(providers.size() == 0);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}
}