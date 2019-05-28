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
import org.springframework.util.Assert;

import domain.Customer;
import domain.Host;
import domain.Request;
import domain.SocialProfile;
import domain.Cleaner;
import domain.CreditCard;
import security.LoginService;
import services.CustomerService;
import services.RequestService;
import services.SocialProfileService;
import services.AdminService;
import services.ConfigService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SocialProfileServiceTest extends AbstractTest {

	@Autowired
	private SocialProfileService socialProfileService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private CustomerService customerService;
	
	/*
	 * 9. Un actor autenticado como cliente podr�:
	 * 
	 * 1. Crear una solicitud de paquete de viaje. En esta solicitud el cliente especificar� varios par�metros opcionales
	 * 	  como se indica en los requisitos de informaci�n.
	 * 
	 * Analysis of sentence coverage:
	 * ~30%
	 * 
	 * Analysis of data coverage:
	 * ~20%
	 */
	@Test
	public void diver01() throws ParseException {

		final Object testingData[][] = {

				{ 
					"nick", "name", "http://link", null
			}, {
					null, null, null, null //NO ENTIENDO  -.-'''''
//			}, {
//				"destination", startDate, 3d, 2 , "hola", endDate, IllegalArgumentException.class
				} };

		for (int i = 0; i < testingData.length; i++)
			this.diver01((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
					(Class<?>) testingData[i][3]);

	}
	
	protected void diver01(final String link, final String name, final String nick, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.startTransaction();

			final Customer customer = this.customerService.create();
			List<String> make = (List<String>) this.configService.getConfiguration().getCreditCardMakeList();
			
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("password", null);
			customer.getUserAccount().setPassword(hashPassword);
			customer.getUserAccount().setUsername("userAccount");
			customer.setName("name");
			customer.setSurname("surname");
			customer.setEmail("email@email.com");
			customer.setPhone("12345");
			customer.setPhoto("");
			SimpleDateFormat parseador = new SimpleDateFormat("yyyy/MM/dd");
			Date date = parseador.parse("1998/11/11");
			customer.setBirthDate(date);
			customer.setCity("city");
			CreditCard creditCard = new CreditCard();
			creditCard.setHolder("holder");
			creditCard.setMake(make.get(0));
			creditCard.setNumber("1234567890987654");
			creditCard.setCVV("123");
			creditCard.setExpiration("03/20");
			customer.setCreditCard(creditCard);
			Customer actorSave = this.customerService.saveRegisterAsCustomer(customer);
			
			this.authenticate("userAccount");
			
			SocialProfile socialProfile = this.socialProfileService.create();
			socialProfile.setActor(this.customerService.getCustomerByUserAccountId(LoginService.getPrincipal().getId()));
			socialProfile.setLink(link);
			socialProfile.setName(name);
			socialProfile.setNick(nick);
			SocialProfile socialProfileSave = this.socialProfileService.save(socialProfile);
			
			Assert.isTrue(this.socialProfileService.findOne(socialProfileSave.getId()) != null);
				
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

	
	
}