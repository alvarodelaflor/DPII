
package unViaje;

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

import domain.CreditCard;
import domain.TravelAgency;
import services.AccomodationService;
import services.ConfigService;
import services.TransportService;
import services.TravelAgencyService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AccomodationServiceTest extends AbstractTest {

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
	 *
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
			this.accomodationService.findAll();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

}
