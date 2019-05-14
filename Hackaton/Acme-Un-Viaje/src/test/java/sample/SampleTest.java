/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package sample;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SampleTest extends AbstractTest {

	// System under test ------------------------------------------------------

	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	@Test
	public void samplePositiveTest() {
		Assert.isTrue(true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void sampleNegativeTest() {
		Assert.isTrue(false);
	}

	@Test
	public void sampleDriver() {
		final Object testingData[][] = {
			{
				"userAccount1", 4, null
			}, {
				"userAccount2", 5, null
			}, {
				"userAccount3", 6, null
			}, {
				"non-existent", 0, AssertionError.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.sampleTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void sampleTemplate(final String beanName, final int id, final Class<?> expected) {
		Class<?> caught;
		int dbId;

		caught = null;
		try {
			dbId = super.getEntityId(beanName);
			Assert.isTrue(dbId == id);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
