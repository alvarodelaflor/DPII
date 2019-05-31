
package unViaje;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import services.AccomodationService;
import services.ConfigService;
import services.TransportService;
import services.TravelAgencyService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Transport;
import domain.TravelAgency;
import forms.TransportForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TransportServiceTest extends AbstractTest {

	@Autowired
	private TransportService	transportService;

	@Autowired
	private TravelAgencyService	travelAgencyService;

	@Autowired
	private AccomodationService	accomodationService;

	@Autowired
	private ConfigService		configService;


	/*
	 * 10\. Un actor autenticado como agencia de viajes podrá:
	 * 
	 * - 1\. Ver un listado de todos los transportes disponibles y mostrarlos.
	 * - 2\. Ver un listado de todos los hospedajes disponibles y mostrarlos.
	 * En este caso solo podemos testear que con un usuario logeado se puedan solicitar los transportes u hospedajes (positivo), y que no un usuario no logeado no pueda (Negativo).
	 * 
	 * Analysis of sentence coverage:
	 * ~5%
	 * 
	 * Analysis of data coverage:
	 * ~15%
	 */
	@Test
	public void diver02() throws ParseException {

		final Object testingData[][] = {

			{
				true, null
			}, {
				false, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.diver02((Boolean) testingData[i][0], (Class<?>) testingData[i][1]);

	}

	protected void diver02(final Boolean logged, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate("travelAgency");
			final List<String> make = (List<String>) this.configService.getConfiguration().getCreditCardMakeList();

			final TravelAgency actor = this.travelAgencyService.create();
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("password", null);
			actor.getUserAccount().setPassword(hashPassword);
			actor.getUserAccount().setUsername("userAccont");
			actor.setName("name");
			actor.setSurname("surname");
			actor.setEmail("email@email.com");
			actor.setPhone("123456");
			actor.setPhoto("");
			final SimpleDateFormat parseador = new SimpleDateFormat("yyyy/MM/dd");
			final Date date = parseador.parse("1998/11/11");
			actor.setBirthDate(date);
			final CreditCard creditCard = new CreditCard();
			creditCard.setHolder("holder");
			creditCard.setMake(make.get(0));
			creditCard.setNumber("123213454321234");
			creditCard.setCVV("345");
			creditCard.setExpiration("03/22");
			actor.setCreditCard(creditCard);
			final TravelAgency actorSave = this.travelAgencyService.saveRegisterAsTravelAgency(actor);
			super.unauthenticate();

			if (logged)
				this.authenticate("userAccont");
			this.transportService.findAll();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 6. Un actor autenticado como transportista podrá:
	 * 
	 * 1. Crear un transporte disponible para una fecha.
	 * Analysis of sentence coverage: ~24%
	 * 
	 * Analysis of data coverage: ~15%
	 */
	@Test
	public void createTransport() throws ParseException {

		final Object testingData[][] = {
			{
				"transporter", "2019/12/11 15:30", null
			}, {
				"transporter", "2018/12/11 15:30", AssertionError.class
			}, {
				"transporter", "2020/12/11 16:30", AssertionError.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.driver01((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	private void driver01(final String user, final String date, final Class<?> expected) {
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(user);

			final Date d = df.parse(date);

			Transport t = this.testTransport(d);

			final BindingResult result = new BeanPropertyBindingResult(t, t.getClass().getName());
			t = this.transportService.reconstruct(t, result);
			assert result.hasErrors() == false;
			this.transportService.save(t);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	private Transport testTransport(final Date d) {
		final Transport t = this.transportService.create();
		t.setDate(d);
		t.setOrigin("Test");
		t.setDestination("Test");
		t.setPrice(10);
		t.setReservedPlaces(0);
		t.setVehicleType("Pepino");
		t.setNumberOfPlaces(4);
		return t;
	}

	/*
	 * 6. Un actor autenticado como transportista podrá:
	 * 
	 * 1. Crear un conjunto de transportes
	 * Analysis of sentence coverage: ~31%
	 * 
	 * Analysis of data coverage: ~15%
	 */
	@Test
	public void createMultipleTransports() throws ParseException {

		final Object testingData[][] = {
			{
				"transporter", "2019/12/11 15:30", "2019/12/13", null
			}, {
				"transporter", "2018/12/11 15:30", "2019/12/13", AssertionError.class
			}, {
				"transporter", "2020/12/11 16:30", "2019/12/13", AssertionError.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.driver02((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}
	private void driver02(final String user, final String initDate, final String endDate, final Class<?> expected) {
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		final DateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(user);

			final Date d = df.parse(initDate);
			final Date d2 = df2.parse(endDate);

			final TransportForm t = new TransportForm();
			t.setInitDate(d);
			t.setEndDate(d2);
			t.setOrigin("Test");
			t.setDestination("Test");
			t.setPrice(10);
			t.setVehicleType("Pepino");
			t.setNumberOfPlaces(4);

			final BindingResult result = new BeanPropertyBindingResult(t, t.getClass().getName());
			this.transportService.validateTransportForm(t, result);
			assert result.hasErrors() == false;
			this.transportService.saveMultiple(t);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 6. Un actor autenticado como transportista podrá:
	 * 
	 * 2. Editar un transporte
	 * Analysis of sentence coverage: ~38%
	 * 
	 * Analysis of data coverage: ~15%
	 */
	@Test
	public void editTransport() throws ParseException {

		final Object testingData[][] = {
			{
				"transporter", "2019/12/11 15:30", null
			}, {
				"transporter", "2018/12/11 15:30", AssertionError.class
			}, {
				"transporter", "2020/12/11 16:30", AssertionError.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.driver03((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}
	private void driver03(final String user, final String date, final Class<?> expected) {
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(user);

			final Date d = df.parse("2019/12/11 15:30");

			final Transport validTransport = this.testTransport(d);

			BindingResult result = new BeanPropertyBindingResult(validTransport, validTransport.getClass().getName());
			this.transportService.reconstruct(validTransport, result);
			assert result.hasErrors() == false;

			final Transport t = this.transportService.save(validTransport);

			final Transport modified = this.testTransport(df.parse(date));
			modified.setId(t.getId());
			modified.setVersion(t.getVersion());
			result = new BeanPropertyBindingResult(modified, modified.getClass().getName());
			this.transportService.reconstruct(modified, result);
			assert result.hasErrors() == false;
			this.transportService.save(modified);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 6. Un actor autenticado como transportista podrá:
	 * 
	 * 2. Borrar un transporte
	 * Analysis of sentence coverage: ~30%
	 * 
	 * Analysis of data coverage: ~15%
	 */
	@Test
	public void deleteTransport() throws ParseException {

		final Object testingData[][] = {
			{
				"transporter", null
			}, {
				"customer", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteTransport((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	private void deleteTransport(final String user, final Class<?> expected) {
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate("transporter");

			// Create as transporter
			final Date d = df.parse("2019/12/11 15:30");

			final Transport validTransport = this.testTransport(d);

			final BindingResult result = new BeanPropertyBindingResult(validTransport, validTransport.getClass().getName());
			this.transportService.reconstruct(validTransport, result);
			assert result.hasErrors() == false;

			final Transport t = this.transportService.save(validTransport);
			this.unauthenticate();

			// Delete as user
			this.authenticate(user);
			this.transportService.delete(t.getId());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 6. Un actor autenticado como transportista podrá:
	 * 
	 * 3. Ver uno de sus transportes
	 * Analysis of sentence coverage: ~29%
	 * 
	 * Analysis of data coverage: ~15%
	 */
	@Test
	public void showTransport() throws ParseException {

		final Object testingData[][] = {
			{
				"transporter", null
			}, {
				"customer", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.showTransport((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	private void showTransport(final String user, final Class<?> expected) {
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate("transporter");

			// Create as transporter
			final Date d = df.parse("2019/12/11 15:30");

			final Transport validTransport = this.testTransport(d);

			final BindingResult result = new BeanPropertyBindingResult(validTransport, validTransport.getClass().getName());
			this.transportService.reconstruct(validTransport, result);
			assert result.hasErrors() == false;

			final Transport t = this.transportService.save(validTransport);
			this.unauthenticate();

			// Delete as user
			this.authenticate(user);
			this.transportService.getLoggedTransporterTransport(t.getId());
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 6. Un actor autenticado como transportista podrá:
	 * 
	 * 4. Ver una lista de sus transportes
	 * Analysis of sentence coverage: ~29%
	 * 
	 * Analysis of data coverage: ~40%
	 */
	@Test
	public void listTransports() throws ParseException {

		final Object testingData[][] = {
			{
				"transporter", null
			}, {
				"customer", IllegalArgumentException.class
			}, {
				"admin", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.showTransport((String) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	private void listTransports(final String user, final Class<?> expected) {
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate(user);

			this.transportService.getLoggedTransporterTransports();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
