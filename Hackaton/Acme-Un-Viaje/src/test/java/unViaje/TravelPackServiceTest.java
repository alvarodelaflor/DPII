
package unViaje;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.BookingAccomodation;
import domain.BookingTransport;
import domain.CreditCard;
import domain.TravelAgency;
import domain.TravelPack;
import security.LoginService;
import services.ConfigService;
import services.CustomerService;
import services.TravelAgencyService;
import services.TravelPackService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TravelPackServiceTest extends AbstractTest {

	@Autowired
	private TravelPackService	travelPackService;

	@Autowired
	private ConfigService		configService;

	@Autowired
	private TravelAgencyService	travelAgencyService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private Validator			validator;


	/*
	 * 15\. Un actor autenticado como agencia de viajes podrá:
	 * - 2\. Publicar paquetes de viaje para los clientes.
	 *
	 * Positivos : Probamos que con datos correctos no haya excepciones
	 * Negativos: Probamos que con datos incorrectos y cross scripting se lancen excepciones.
	 *
	 * Analysis of sentence coverage:
	 * ~80%
	 *
	 * Analysis of data coverage:
	 * ~70%
	 */
	@Test
	public void diver02() {

		final Object testingData[][] = {

			{
				true, true, "Prueba", null
			}, {
				false, true, "", ConstraintViolationException.class
			}, {
				true, true, "<script>alert('hackeo')</script>", null
			}, {
				true, false, "Prueba", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.diver02((Boolean) testingData[i][0], (Boolean) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	protected void diver02(final Boolean draft, final Boolean logged, final String penis, final Class<?> expected) {

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
			actor.setName("Roger");
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
			this.authenticate("customer");
			final Integer id = this.customerService.getLoggedCustomer().getId();
			super.unauthenticate();
			if (logged)
				this.authenticate("userAccont");
			final TravelPack pack = this.travelPackService.create();
			final List<BookingTransport> transports = new ArrayList<BookingTransport>();
			final List<BookingAccomodation> accomodations = new ArrayList<BookingAccomodation>();
			pack.setAccomodations(accomodations);
			pack.setTransports(transports);
			pack.setName(penis);
			pack.setCustomer(this.customerService.findOne(id));
			pack.setPrice(0.);
			pack.setStatus(draft);
			pack.setTravelAgency(this.travelAgencyService.getTravelAgencyByUserAccountId(LoginService.getPrincipal().getId()));

			final BindingResult binding = new BeanPropertyBindingResult(pack, "pack");
			final TravelPack newPack = this.travelPackService.reconstruct(pack, binding);
			final TravelPack saved = this.travelPackService.save(newPack);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

}
