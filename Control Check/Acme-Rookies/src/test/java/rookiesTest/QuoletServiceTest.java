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

import domain.Audit;
import domain.Position;
import domain.Quolet;
import services.AuditService;
import services.CompanyService;
import services.PositionService;
import services.QuoletService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class QuoletServiceTest extends AbstractTest {

	@Autowired
	private QuoletService	quoletService;
	
	@Autowired 
	private CompanyService companyService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private AuditService auditService;

	/*
	 *  POSITIVE
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
		Class<?> caught = null;

		try {

			this.authenticate("company");
			
			List<Position> positions = (List<Position>) this.positionService.findAllPositionsByLoggedCompany();
			List<Audit> audits = (List<Audit>) this.auditService.getAuditByPositionId(positions.get(0).getId());

			Quolet quolet = this.quoletService.create(audits.get(0).getId());
			
			quolet.setBody(body);
			quolet.setDraftMode(draftMode);
			quolet.setPicture(picture);
			quolet.setAudit(audits.get(0));
			
			BindingResult binding = new BeanPropertyBindingResult(quolet, "quolet");
			
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
	 *  NEGATIVE
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
			this.authenticate("admin");
			
			List<Position> positions = (List<Position>) this.positionService.findAllPositionsByLoggedCompany();
			List<Audit> audits = (List<Audit>) this.auditService.getAuditByPositionId(positions.get(0).getId());

			Quolet quolet = this.quoletService.create(audits.get(0).getId());
			
			quolet.setBody(body);
			quolet.setDraftMode(draftMode);
			quolet.setPicture(picture);
			quolet.setAudit(audits.get(0));
			
			BindingResult binding = new BeanPropertyBindingResult(quolet, "quolet");
			
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
