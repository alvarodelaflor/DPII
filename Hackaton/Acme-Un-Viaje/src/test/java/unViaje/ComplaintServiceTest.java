
package unViaje;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import services.ComplaintService;
import utilities.AbstractTest;
import domain.Complaint;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	@Autowired
	private ComplaintService	complaintService;


	/*
	 * 19. Un actor autentificado como cliente podrá:
	 * 
	 * Crear una queja
	 * 
	 * Analysis of sentence coverage: ~18%
	 * 
	 * Analysis of data coverage: ~20%
	 */
	@Test
	public void createComplaint() {

		final Object testingData[][] = {

			{
				"customer", null
			}, {
				"hosthost", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createComplaint((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void createComplaint(final String user, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);
			final int tpId = this.getEntityId("travelPack01");

			Complaint c = this.complaintService.create();
			c.setDescription("Test");

			final BindingResult binding = new BeanPropertyBindingResult(c, "complaint");

			c = this.complaintService.reconstruct(c, binding);
			this.complaintService.save(c, tpId);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 19. Un actor autentificado como cliente podrá:
	 * 
	 * Editar una queja
	 * 
	 * Analysis of sentence coverage: ~18%
	 * 
	 * Analysis of data coverage: ~20%
	 */
	@Test
	public void editComplaint() {

		final Object testingData[][] = {

			{
				"customer", null
			}, {
				"hosthost", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editComplaint((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void editComplaint(final String user, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate("customer");
			final int tpId = this.getEntityId("travelPack01");

			Complaint c = this.complaintService.create();
			c.setDescription("Test");

			final BindingResult binding = new BeanPropertyBindingResult(c, "complaint");

			c = this.complaintService.reconstruct(c, binding);
			this.complaintService.save(c, tpId);

			this.unauthenticate();

			this.authenticate(user);

			c = this.complaintService.reconstruct(c, binding);
			this.complaintService.save(c, tpId);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 19. Un actor autentificado como cliente podrá:
	 * 
	 * Borrar una queja
	 * 
	 * Analysis of sentence coverage: ~32%
	 * 
	 * Analysis of data coverage: ~45%
	 */
	@Test
	public void deleteComplaint() {

		final Object testingData[][] = {

			{
				"customer", null
			}, {
				"transporter", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteComplaint((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void deleteComplaint(final String user, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate("customer");
			final int tpId = this.getEntityId("travelPack01");

			Complaint c = this.complaintService.create();
			c.setDescription("Test");

			final BindingResult binding = new BeanPropertyBindingResult(c, "complaint");

			c = this.complaintService.reconstruct(c, binding);
			c = this.complaintService.save(c, tpId);

			this.unauthenticate();

			this.authenticate(user);

			this.complaintService.delete(c.getId());

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 19. Un actor autentificado como cliente podrá:
	 * 
	 * Listar sus quejas
	 * 
	 * Analysis of sentence coverage: ~9%
	 * 
	 * Analysis of data coverage: ~45%
	 */
	@Test
	public void listComplaints() {

		final Object testingData[][] = {

			{
				"customer", null
			}, {
				"transporter", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listComplaints((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void listComplaints(final String user, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);

			this.complaintService.getLoggedCustomerAssignedComplaints();
			this.complaintService.getLoggedCustomerUnassignedComplaints();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
}
