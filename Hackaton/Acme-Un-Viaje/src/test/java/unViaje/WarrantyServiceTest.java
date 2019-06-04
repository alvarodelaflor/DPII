
package unViaje;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.WarrantyService;
import utilities.AbstractTest;
import domain.Warranty;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	@Autowired
	private WarrantyService	warrantyService;


	/*
	 * 22. Un actor autentificado como agencia de viaje podrá:
	 * 
	 * Crear garantías.
	 * 
	 * Analysis of sentence coverage: ~12%
	 * 
	 * Analysis of data coverage: ~35%
	 */
	@Test
	public void createWarranty() {

		final Object testingData[][] = {

			{
				"travelAgency", null
			}, {
				"transporter", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createWarranty((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void createWarranty(final String user, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);
			final Warranty w = this.warrantyService.create();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 22. Un actor autentificado como agencia de viaje podrá:
	 * 
	 * Editar garantías.
	 * 
	 * Analysis of sentence coverage: ~15%
	 * 
	 * Analysis of data coverage: ~35%
	 */
	@Test
	public void editWarranty() {

		final Object testingData[][] = {

			{
				"travelAgency", null
			}, {
				"hosthost", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editWarranty((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void editWarranty(final String user, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate("travelAgency");
			Warranty w = this.warrantyService.create();
			w.setDraftMode(true);
			w.setTerms("Test");
			w.setTitle("Test");
			w = this.warrantyService.save(w);
			this.unauthenticate();
			this.authenticate(user);
			final Warranty w2 = this.warrantyService.create();
			w2.setId(w.getId());
			w2.setVersion(w.getVersion());
			w2.setTerms("Test2");
			w2.setTitle("Test2");
			this.warrantyService.save(w2);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 22. Un actor autentificado como agencia de viaje podrá:
	 * 
	 * Borrar garantías.
	 * 
	 * Analysis of sentence coverage: ~27%
	 * 
	 * Analysis of data coverage: ~35%
	 */
	@Test
	public void deleteWarranty() {

		final Object testingData[][] = {

			{
				"travelAgency", null
			}, {
				"hosthost", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteWarranty((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void deleteWarranty(final String user, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate("travelAgency");
			Warranty w = this.warrantyService.create();
			w.setDraftMode(true);
			w.setTerms("Test");
			w.setTitle("Test");
			w = this.warrantyService.save(w);
			this.unauthenticate();
			this.authenticate(user);
			this.warrantyService.delete(w);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 22. Un actor autentificado como agencia de viaje podrá:
	 * 
	 * Listar garantías.
	 * 
	 * Analysis of sentence coverage: ~11%
	 * 
	 * Analysis of data coverage: ~35%
	 */
	@Test
	public void listWarranties() {

		final Object testingData[][] = {

			{
				"travelAgency", null
			}, {
				"transporter", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listWarranties((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void listWarranties(final String user, final Class<?> expected) {

		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);
			this.warrantyService.getTravelAgencyWarranty();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			super.unauthenticate();
			this.rollbackTransaction();
		}

		this.checkExceptions(expected, caught);
	}
}
