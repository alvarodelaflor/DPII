
package acmeParadeTest;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.EnrolledService;
import services.ParadeService;
import services.RequestService;
import utilities.AbstractTest;
import domain.Request;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequestTest extends AbstractTest {

	@Autowired
	private EnrolledService	enrolledService;

	@Autowired
	private RequestService	requestService;

	@Autowired
	private ParadeService	paradeService;


	/*
	 * (LEVEL C 11) Testing request
	 * 
	 * Analysis of sentence coverage: 35.8%
	 * 
	 * Analysis of data coverage: TODO
	 */
	@Test
	public void RequestDriver1() {

		// DATA: user, useCases
		final Object testingData[][] = {
			// ----- Positive test (valid user who belongs to a brotherhood and deletes a pending request)
			{
				"member", Arrays.asList("delete"), null
			},
			//----- Positive test (valid user who belongs to a brotherhood, deletes a pending request and sends another one)
			{
				"member", Arrays.asList("delete", "send"), null
			},
			//----- Negative test (valid user who belongs to a brotherhood and sends a request while having already one in pending state)
			{
				"member", Arrays.asList("send"), IllegalArgumentException.class
			},
			//----- Negative test (valid user who belongs to a brotherhood and deletes a request that is not in pending)
			{
				"member", Arrays.asList("changeStatus", "delete"), IllegalArgumentException.class
			},
			//----- Negative test (valid user who doesn't belong to a brotherhood and sends tries to send a request)
			{
				"member2", Arrays.asList("send"), IllegalArgumentException.class
			},
			//----- Negative test (valid user who doesn't belong to a brotherhood tries to delete a request of another member)
			{
				"member2", Arrays.asList("delete"), IllegalArgumentException.class
			},
			//----- Negative tests (invalid users try to delete a request)
			{
				"sponsor", Arrays.asList("delete"), IllegalArgumentException.class
			}, {
				"chapter", Arrays.asList("delete"), IllegalArgumentException.class
			}, {
				"brotherhood", Arrays.asList("delete"), IllegalArgumentException.class
			}, {
				"admin", Arrays.asList("delete"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.RequestTemplate((String) testingData[i][0], (List<String>) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void RequestTemplate(final String userName, final List<String> useCases, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			this.startTransaction();
			super.authenticate(userName);

			Request request = null;
			int brotherhoodId = 0;
			int requestId = 0;
			if (useCases.contains("delete")) {
				requestId = super.getEntityId("request01");

				if (useCases.contains("changeStatus")) {
					request = this.requestService.findOne(requestId);
					request.setStatus(true);
				}

				this.requestService.delete(requestId);
			}

			if (useCases.contains("send")) {
				brotherhoodId = this.getEntityId("brotherhood01");
				final int paradeId = this.paradeService.getParadeByBrotherhoodId(brotherhoodId).iterator().next().getId();
				request = this.requestService.create(paradeId);
				request = this.requestService.save(request);
			}

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
}
