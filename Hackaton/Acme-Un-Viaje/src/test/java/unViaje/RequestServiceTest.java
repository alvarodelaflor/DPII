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
import domain.Cleaner;
import domain.CreditCard;
import security.LoginService;
import services.CustomerService;
import services.RequestService;
import services.ConfigService;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequestServiceTest extends AbstractTest {
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ConfigService configService;
	
	/*
	 * 9. Un actor autenticado como cliente podrá:
	 * 
	 * 1. Crear una solicitud de paquete de viaje. En esta solicitud el cliente especificará varios parámetros opcionales
	 * 	  como se indica en los requisitos de información.
	 * 
	 * Analysis of sentence coverage:
	 * ~30%
	 * 
	 * Analysis of data coverage:
	 * ~20%
	 */
	@Test
	public void diver01() throws ParseException {
		
		SimpleDateFormat parseador = new SimpleDateFormat("yyyy/MM/dd");
		Date endDate = parseador.parse("20202/12/11");
		Date startDate = parseador.parse("2020/11/11");

		final Object testingData[][] = {

				{ 
					"destination", endDate, 500.5, 4, "origin", startDate, null
			}, {
					"destination", null , 3.3, 3, "origin", null, NullPointerException.class
			}, {
				"destination", startDate, 3d, 2 , "hola", endDate, IllegalArgumentException.class
				} };

		for (int i = 0; i < testingData.length; i++)
			this.diver01((String) testingData[i][0], (Date) testingData[i][1], (Double) testingData[i][2],
					(Integer) testingData[i][3], (String) testingData[i][4], (Date) testingData[i][5],
					(Class<?>) testingData[i][6]);

	}
	
	protected void diver01(final String destination, final Date endDate, final Double maxPrice, final Integer numberOfPeople,
			final String origin, final Date startDate, final Class<?> expected) {

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
			
			Request request = this.requestService.create();
			
			// CONTROLADO EN CONTROLADOR
			Assert.isTrue(startDate.before(endDate));
			request.setCustomer(this.customerService.getCustomerByUserAccountId(LoginService.getPrincipal().getId()));
			request.setDestination(destination);
			request.setEndDate(endDate);
			request.setMaxPrice(maxPrice);
			request.setNumberOfPeople(numberOfPeople);
			request.setOrigin(origin);
			request.setStartDate(startDate);
			request.setStatus(false);
			Request requestS =this.requestService.save(request);
			
				
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}


	/*
	 * 9. Un actor autenticado como cliente podrá:
	 * 
	 * 2. Borrar la solicitud realizada siempre y cuando esta no haya sido respondida aún por ninguna agencia de viaje.
	 * 
	 * Analysis of sentence coverage:
	 * ~40%
	 * 
	 * Analysis of data coverage:
	 * ~30%
	 */
	@Test
	public void diver02() throws ParseException {

		final Object testingData[][] = {

				{ 
					false, null
			}, {
					true, IllegalArgumentException.class
			}, {
					null, NullPointerException.class
				} };

		for (int i = 0; i < testingData.length; i++)
			this.diver02((Boolean) testingData[i][0], (Class<?>) testingData[i][1]);

	}
	
	protected void diver02(final Boolean status, final Class<?> expected) {

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
			
			Request request = this.requestService.create();
			
			Date endDate = parseador.parse("20202/12/11");
			Date startDate = parseador.parse("2020/11/11");
			
			request.setCustomer(this.customerService.getCustomerByUserAccountId(LoginService.getPrincipal().getId()));
			request.setDestination("destination");
			
			request.setEndDate(endDate);
			request.setMaxPrice(23.3);
			request.setNumberOfPeople(2);
			request.setOrigin("origin");
			request.setStartDate(startDate);
			request.setStatus(status);
			Request requestS =this.requestService.save(request);
			
			//CONTROLADO EN CONTROLADOR
			Assert.isTrue(!status);
			this.requestService.delete(requestS);
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}

		this.checkExceptions(expected, caught);
	}

}
