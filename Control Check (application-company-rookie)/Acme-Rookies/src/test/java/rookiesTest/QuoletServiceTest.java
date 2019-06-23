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

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import services.ApplicationService;
import services.CompanyService;
import services.PositionService;
import services.QuoletService;
import services.RookieService;
import utilities.AbstractTest;
import domain.Application;
import domain.Company;
import domain.Quolet;
import domain.Rookie;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class QuoletServiceTest extends AbstractTest {

	@Autowired
	private QuoletService		quoletService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private RookieService		rookieServie;


	/*
	 * POSITIVE
	 */
	@Test
	public void Diver01() {
		final Object testingData[][] = {
			{
				// Test positive: create
				// body, picture, draftMode
				"body", "http://prueba", true, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((String) testingData[i][0], (String) testingData[i][1], (Boolean) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	// Ancillary methods ------------------------------------------------------

	protected void Diver01(final String body, final String picture, final Boolean draftMode, final Class<?> expected) {
		final Class<?> caught = null;

		try {
			this.startTransaction();

			this.authenticate("rookie");
			final Rookie rookie = this.rookieServie.getRookieLogin();
			final List<Application> applications = (List<Application>) this.applicationService.getApplicationsByRookie(rookie.getId());
			final Company company = applications.get(0).getPosition().getCompany();
			this.unauthenticate();

			this.authenticate(company.getUserAccount().getUsername());

			Quolet quolet = this.quoletService.create(applications.get(0).getId());

			quolet.setBody(body);
			quolet.setDraftMode(draftMode);
			quolet.setPicture(picture);
			quolet.setApplication(applications.get(0));

			final BindingResult binding = new BeanPropertyBindingResult(quolet, "quolet");

			quolet = this.quoletService.reconstruct(quolet, binding);

			this.quoletService.save(quolet);

			this.quoletService.flush();

		} catch (final Throwable oops) {
			System.out.println(oops);
			this.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}
	/*
	 * NEGATIVE
	 */
	@Test
	public void Diver02() {
		final Object testingData[][] = {
			{
				// Test negative: create
				// body, picture, draftMode
				"body", "http://prueba", true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver02((String) testingData[i][0], (String) testingData[i][1], (Boolean) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	// Ancillary methods ------------------------------------------------------

	protected void Diver02(final String body, final String picture, final Boolean draftMode, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();

			this.authenticate("rookie");
			final Rookie rookie = this.rookieServie.getRookieLogin();
			final List<Application> applications = (List<Application>) this.applicationService.getApplicationsByRookie(rookie.getId());
			final Company company = applications.get(0).getPosition().getCompany();
			this.unauthenticate();

			this.authenticate("admin");

			Quolet quolet = this.quoletService.create(applications.get(0).getId());

			quolet.setBody(body);
			quolet.setDraftMode(draftMode);
			quolet.setPicture(picture);
			quolet.setApplication(applications.get(0));

			final BindingResult binding = new BeanPropertyBindingResult(quolet, "quolet");

			quolet = this.quoletService.reconstruct(quolet, binding);

			this.quoletService.save(quolet);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

}
