/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package hackerRankTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.PositionService;
import utilities.AbstractTest;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionServiceTest extends AbstractTest {

	@Autowired
	private PositionService	positionService;


	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 
	 * 2. List the positions available and navigate to the corresponding companies.
	 * 
	 * Analysis of sentence coverage
	 * 10,8%
	 * Analysis of data coverage
	 * ~70%
	 */
	@Test
	public void Diver01() {
		final Object testingData[][] = {
			{
				// Test positivo: Listar position
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

			if (user != null)
				super.authenticate(user);

			final Collection<Position> positions = this.positionService.findAllPositionWithStatusTrueCancelFalse();
			for (final Position position : positions) {
				position.getDeadline();
				position.getDescription();
				position.getProfile();
				position.getSalary();
				position.getSkills();
				position.getStatus();
				position.getTechs();
				position.getTitle();
				position.getCompany();
			}

			this.positionService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			if (user != null)
				super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 
	 * 2. List the positions available and navigate to the corresponding companies.
	 * 
	 * Analysis of sentence coverage
	 * 10,8%
	 * Analysis of data coverage
	 * ~70%
	 */
	@Test
	public void Diver02() {
		final Object testingData[][] = {
			{
				// Test negativo: Listar position
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

			if (user != null)
				super.authenticate(user);

			final List<Position> positions = new ArrayList<>();
			if (positions.size() == 0) {
				positions.get(0).getDeadline();
				positions.get(0).getDescription();
				positions.get(0).getProfile();
				positions.get(0).getSalary();
				positions.get(0).getSkills();
				positions.get(0).getStatus();
				positions.get(0).getTechs();
				positions.get(0).getTitle();
				positions.get(0).getCompany();
			}

			this.positionService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			if (user != null)
				super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

}
