
package unViaje;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.CreditCard;
import domain.FinderRequest;
import domain.TravelAgency;
import services.ConfigService;
import services.FinderRequestService;
import services.TravelAgencyService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderRequestServiceTest extends AbstractTest {

	@Autowired
	private FinderRequestService	finderService;

	@Autowired
	private ConfigService			configService;

	@Autowired
	private TravelAgencyService		travelAgencyService;


	/*
	 * 15\. Un actor autenticado como agencia de viajes podrá:
	 * - 1\. Filtrar las solicitudes de los clientes según: fecha, precio y lugar.
	 *
	 * Positivos : Probamos que con datos correctos no haya excepciones, comprobando los límites del mínimo del precio
	 * Negativos: Probamos que con datos incorrectos y cross scripting se lancen excepciones.
	 *
	 * Analysis of sentence coverage:
	 * ~80%
	 *
	 * Analysis of data coverage:
	 * ~70%
	 */
	@Test
	public void diver02() throws ParseException {

		final SimpleDateFormat parseador = new SimpleDateFormat("yyyy/MM/dd");
		final Date date = parseador.parse("1998/11/11");

		final Object testingData[][] = {

			{
				date, 80., "", null
			}, {
				date, -5.0, "", ConstraintViolationException.class
			}, {
				date, 80., "<script>alert('hackeo')</script>", ConstraintViolationException.class
			}, {
				date, 0., "Carmona", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.diver02((Date) testingData[i][0], (Double) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	protected void diver02(final Date startDate, final Double price, final String address, final Class<?> expected) {

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

			this.authenticate("userAccont");
			final FinderRequest newFinder = actorSave.getFinderRequest();
			newFinder.setPlace(address);
			newFinder.setPrice(price);
			newFinder.setStartDate(startDate);
			this.finderService.save(newFinder);
			this.flushTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

}
