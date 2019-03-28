/*
 * ProclaimTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package acmeParadeTest;


import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import domain.Proclaim;
import services.ProclaimService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProclaimTest extends AbstractTest {

	@Autowired
	private ProclaimService		proclaimService;

	// Tests ------------------------------------------------------------------

//	@Test
	public void test01() {
		/*
		 * POSITIVE TEST
		 * 
		 * In this test we will test the creation of a proclaim.
		 * 
		 * Information requirements
		 * 
		 * 12. Chapters can publish proclaims. For every proclaim, the system must store the moment when it's 
		 *     published and a piece of text that can't be longer than 250 characters.
		 *    
		 * F.R.
		 * 
		 * 17. An actor who is authenticated as a chapter must be able to:
		 * 
		 * 			1. Publish a proclaim. Note that once a proclaim is published, there's no way to update 
		 * 				or delete it, so double confirmation prior to publication is a must.
		 *
		 *	Analysis of sentence coverage 
		 *			TODO
		 *	Analysis of data coverage
		 *			TODO
		 *
		 */
		final Object testingData[][] = {
			// chapterId, textMin, texMax, date 
			{ 
				"chapter", 0, 0, null
			}, { 
				"chapter", 0, 1, null
			}, { 
				"chapter", 1, 1, null
			}, { 
				"chapter", 1, 1, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void checkTest(final String userName, final int textMin, final int textMax, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);
			
			Proclaim proclaim = this.proclaimService.create();
			
			if (textMin != 0 && textMax == 0) {
				proclaim.setText("m");
			} else if (textMin == 0 && textMax != 0) {
				proclaim.setText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium.");				
			} else {
				proclaim.setText("Normal text");
			}
			
			proclaim.setMoment(LocalDate.now().toDate());
			
			this.proclaimService.save(proclaim);
			this.proclaimService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
	
	@Test
	public void test02() {
		/*
		 * NEGATIVE TEST
		 * 
		 * In this test we will test the creation of a proclaim.
		 * 
		 * Information requirements
		 * 
		 * 12. Chapters can publish proclaims. For every proclaim, the system must store the moment when it's 
		 *     published and a piece of text that can't be longer than 250 characters.
		 *    
		 * F.R.
		 * 
		 * 17. An actor who is authenticated as a chapter must be able to:
		 * 
		 * 			1. Publish a proclaim. Note that once a proclaim is published, there's no way to update 
		 * 				or delete it, so double confirmation prior to publication is a must.
		 *
		 *	Analysis of sentence coverage 
		 *			TODO
		 *	Analysis of data coverage
		 *			TODO
		 *
		 */
		final Object testingData[][] = {
			// chapterId, textBlank, texMax, textNull, date 
			{ 
				"chapter", 0, 0, 0, ConstraintViolationException.class
			}, { 
				"chapter", 0, 0, 1, ConstraintViolationException.class
			}, { 
				"chapter", 0, 1, 0, ConstraintViolationException.class
			}, { 
				"chapter", 0, 1, 1, ConstraintViolationException.class
			}, { 
				"chapter", 1, 0, 0, ConstraintViolationException.class
			}, { 
				"chapter", 1, 0, 1, ConstraintViolationException.class
			}, { 
				"chapter", 1, 1, 1, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.checkTest((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void checkTest(final String userName, final int textBlank, final int textMax, final int textNull, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			super.authenticate(userName);
			
			Proclaim proclaim = this.proclaimService.create();
			
			if (textBlank != 0 && textMax == 0 && textNull == 0) {
				proclaim.setText("");
			} else if (textBlank == 0 && textMax == 1 && textNull == 0) {
				proclaim.setText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium q");
			} else if (textBlank == 0 && textMax == 0 && textNull == 1){
				proclaim.setText(null);
			} else {
				proclaim.setText("Normal text");
			}
			
			if (((textBlank + textMax + textNull) > 1 || (textBlank + textMax + textNull) == 0)) {
				/*
				 * Este es el caso de que el resultado es positivo, por lo que vamos a forzar un caso negativo para agilizar las pruebas
				 */
				proclaim.setText("");
			}
						
			this.proclaimService.save(proclaim);
			this.proclaimService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
