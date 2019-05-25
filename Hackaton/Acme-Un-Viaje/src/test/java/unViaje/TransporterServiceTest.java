/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

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

import domain.Transporter;
import domain.CreditCard;
import security.LoginService;
import services.TransporterService;
import services.ConfigService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TransporterServiceTest extends AbstractTest {

	@Autowired
	private TransporterService transporterService;
	
	@Autowired
	private ConfigService configService;

	/*
	 * 1.Un actor no autentificado podrá:
	 * 
	 * Registrarse en el sistema como cliente, agencia de viajes, regulador,  transportista, operario de limpieza o  anfitrión.
	 *
	 * Analysis of sentence coverage:
	 * ~20%
	 * 
	 * Analysis of data coverage:
	 * ~15%
	 */
	@Test
	public void diver01() throws ParseException {
		
		SimpleDateFormat parseador = new SimpleDateFormat("yyyy/MM/dd");
		Date date = parseador.parse("1998/11/11");

		final Object testingData[][] = {

				{ "password", "userAccount", "name", "surname", "email@email", "123456", "http://photo", date, "holder",
						"1234567890987654", "123", "03/20", null
			}, {
				"", "", "", "", "", "", "", null, "", "", "", "", ConstraintViolationException.class
			}, {
				"p", "u", "n", "s", "e", "", "", date, "holder", "1234567890987654", "123", "03/20", ConstraintViolationException.class
				} };

		for (int i = 0; i < testingData.length; i++)
			this.diver01((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5],
					(String) testingData[i][6], (Date) testingData[i][7], (String) testingData[i][8],
					(String) testingData[i][9], (String) testingData[i][10], (String) testingData[i][11],
					(Class<?>) testingData[i][12]);

	}
	
	protected void diver01(final String password, final String userAccount, final String name, final String surname,
			final String email, final String phone, final String photo, final Date birthDate, final String holder,
			final String number, final String cvv, final String expiration, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Transporter transporter = this.transporterService.create();
			List<String> make = (List<String>) this.configService.getConfiguration().getCreditCardMakeList();
			
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword(password, null);
			transporter.getUserAccount().setPassword(hashPassword);
			transporter.getUserAccount().setUsername(userAccount);
			transporter.setName(name);
			transporter.setSurname(surname);
			transporter.setEmail(email);
			transporter.setPhone(phone);
			transporter.setPhoto(photo);
			transporter.setBirthDate(birthDate);
			CreditCard creditCard = new CreditCard();
			creditCard.setHolder(holder);
			creditCard.setMake(make.get(0));
			creditCard.setNumber(number);
			creditCard.setCVV(cvv);
			creditCard.setExpiration(expiration);
			transporter.setCreditCard(creditCard);
			this.transporterService.saveRegisterAsTransporter(transporter);
			
			this.flushTransaction();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 2. Un actor autentificado en el sistema podrá:
	 * 
	 * Editar sus datos personales.
	 * 
	 * Analysis of sentence coverage:
	 * ~20%
	 * 
	 * Analysis of data coverage:
	 * ~15%
	 */
	@Test
	public void diver02() throws ParseException {
		
		SimpleDateFormat parseador = new SimpleDateFormat("yyyy/MM/dd");
		Date date = parseador.parse("1998/11/11");

		final Object testingData[][] = {

				{ "name", "surname", "email@email", "123456", "http://photo", date, "holder",
						"1234567890987654", "123", "03/20", null
			}, {
				"", "", "", "", "", null, "", "", "", "", ConstraintViolationException.class
			}, {
				"name", "surname", "email", "", "", null, "holder", "1234567890987654", "123", "03/20", ConstraintViolationException.class
			} };

		for (int i = 0; i < testingData.length; i++)
			this.diver02((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4], (Date) testingData[i][5],
					(String) testingData[i][6], (String) testingData[i][7], (String) testingData[i][8],
					(String) testingData[i][9], (Class<?>) testingData[i][10]);

	}
	
	protected void diver02(final String name, final String surname,final String email, final String phone,
			final String photo, final Date birthDate, final String holder,
			final String number, final String cvv, final String expiration, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();
			this.authenticate("transporter");
			List<String> make = (List<String>) this.configService.getConfiguration().getCreditCardMakeList();
			
			final Transporter actor = this.transporterService.create();
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("password", null);
			actor.getUserAccount().setPassword(hashPassword);
			actor.getUserAccount().setUsername("userAccont");
			actor.setName("name");
			actor.setSurname("surname");
			actor.setEmail("email@email.com");
			actor.setPhone("123456");
			actor.setPhoto("");
			SimpleDateFormat parseador = new SimpleDateFormat("yyyy/MM/dd");
			Date date = parseador.parse("1998/11/11");
			actor.setBirthDate(date);
			CreditCard creditCard = new CreditCard();
			creditCard.setHolder("holder");
			creditCard.setMake(make.get(0));
			creditCard.setNumber("123213454321234");
			creditCard.setCVV("345");
			creditCard.setExpiration("03/22");
			actor.setCreditCard(creditCard);
			Transporter actorSave = this.transporterService.saveRegisterAsTransporter(actor);
			super.unauthenticate();

			this.authenticate("userAccont");
			Transporter actorEdit = this.transporterService.getTransporterByUserAccountId(LoginService.getPrincipal().getId());
			actorEdit.setName(name);
			actorEdit.setSurname(surname);
			actorEdit.setEmail(email);
			actorEdit.setPhone(phone);
			actorEdit.setPhoto(photo);
			actorEdit.setBirthDate(birthDate);
			actorEdit.getCreditCard().setHolder(holder);
			actorEdit.getCreditCard().setMake(make.get(0));
			actorEdit.getCreditCard().setNumber(number);
			actorEdit.getCreditCard().setCVV(cvv);
			actorEdit.getCreditCard().setExpiration(expiration);
			actorEdit.setCreditCard(creditCard);
			this.transporterService.saveRegisterAsTransporter(actorEdit);
			
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
