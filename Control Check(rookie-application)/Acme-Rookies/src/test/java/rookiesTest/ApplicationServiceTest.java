/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package rookiesTest;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import services.ApplicationService;
import services.PositionService;
import services.ProblemService;
import services.RookieService;
import utilities.AbstractTest;
import domain.Application;
import domain.Position;
import domain.Problem;
import domain.Rookie;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private RookieService		rookieService;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private PositionService		positionService;


	/*
	 * 10. An actor who is authenticated as a rookie must be able to:
	 * 
	 * 1. Manage his or her applications, which includes listing them grouped by status, showing them,
	 * creating them, and updating them. When an application is created, the system assigns an arbitrary
	 * problem to it (from the set of problems that have been registered for the corresponding position).
	 * Updating an application consists in submitting a solution to the corresponding problem (a piece of text
	 * with explanations and a link to the code), registering the submission moment, and changing the status
	 * to SUBMITTED.
	 * 
	 * Analysis of sentence coverage
	 * ~4%
	 * Analysis of data coverage
	 * ~10%
	 */
	@Test
	public void Diver01() {
		final Object testingData[][] = {
			{
				// Test negativo: Listar application
				// Usuario
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver01((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver01(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			super.authenticate(user);

			final Authority authority = new Authority();
			authority.setAuthority(Authority.ROOKIE);
			Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));

			final Collection<Application> applications = this.applicationService.getApplicationsByRookie(1234);
			for (final Application application : applications) {
				application.getApplyMoment();
				application.getCurricula();
				application.getLink();
			}

			this.applicationService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 10. An actor who is authenticated as a rookie must be able to:
	 * 
	 * 1. Manage his or her applications, which includes listing them grouped by status, showing them,
	 * creating them, and updating them. When an application is created, the system assigns an arbitrary
	 * problem to it (from the set of problems that have been registered for the corresponding position).
	 * Updating an application consists in submitting a solution to the corresponding problem (a piece of text
	 * with explanations and a link to the code), registering the submission moment, and changing the status
	 * to SUBMITTED.
	 * 
	 * Analysis of sentence coverage
	 * ~4%
	 * Analysis of data coverage
	 * ~10%
	 */
	@Test
	public void Diver02() {
		final Object testingData[][] = {
			{
				// Test positivo: Listar application
				// 
				null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver02((Class<?>) testingData[i][0]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver02(final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("soyUnaCalle");
			rookie.setEmail("soyUnaPrueba@soyUnaPrueba");
			rookie.setName("soyUnNombre");
			rookie.setPhone("123456");
			rookie.setPhoto("http://SoyUnaFoto");
			rookie.setSurname("SoyUnaPreuba");
			rookie.setVatNumber("dd33f");
			rookie.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			rookie.getUserAccount().setPassword(hashPassword);

			final Rookie rookieSave = this.rookieService.saveCreate(rookie);

			final Collection<Application> applications = this.applicationService.getApplicationsByRookie(rookieSave.getId());
			for (final Application application : applications) {
				application.getApplyMoment();
				application.getCurricula();
				application.getLink();
			}

			this.rookieService.flush();
			this.applicationService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 10. An actor who is authenticated as a rookie must be able to:
	 * 
	 * 1. Manage his or her applications, which includes listing them grouped by status, showing them,
	 * creating them, and updating them. When an application is created, the system assigns an arbitrary
	 * problem to it (from the set of problems that have been registered for the corresponding position).
	 * Updating an application consists in submitting a solution to the corresponding problem (a piece of text
	 * with explanations and a link to the code), registering the submission moment, and changing the status
	 * to SUBMITTED.
	 * 
	 * Analysis of sentence coverage
	 * ~15%
	 * Analysis of data coverage
	 * ~40%
	 */
	@Test
	public void Diver03() {
		final Object testingData[][] = {
			{
				// Test negativo: crear application
				// 
				IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver03((Class<?>) testingData[i][0]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver03(final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("soyUnaCalle");
			rookie.setEmail("soyUnaPrueba@soyUnaPrueba");
			rookie.setName("soyUnNombre");
			rookie.setPhone("123456");
			rookie.setPhoto("http://SoyUnaFoto");
			rookie.setSurname("SoyUnaPreuba");
			rookie.setVatNumber("dd33f");
			rookie.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			rookie.getUserAccount().setPassword(hashPassword);

			final Rookie rookieSave = this.rookieService.saveCreate(rookie);

			final Application application = this.applicationService.create();
			final List<Position> position = (List<Position>) this.positionService.findALL();
			application.setPosition(position.get(0));
			application.setRookie(rookieSave);
			application.setCreationMoment(LocalDate.now().toDate());

			final List<Problem> problems = (List<Problem>) this.problemService.allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(application.getPosition().getId());

			final Integer alatorio = (int) Math.floor(Math.random() * (problems.size()));
			final Problem seleccion = problems.get(alatorio);
			application.setProblem(seleccion);

			final Application applicationSave = this.applicationService.save(application);

			this.rookieService.flush();
			this.applicationService.flush();

		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 10. An actor who is authenticated as a rookie must be able to:
	 * 
	 * 1. Manage his or her applications, which includes listing them grouped by status, showing them,
	 * creating them, and updating them. When an application is created, the system assigns an arbitrary
	 * problem to it (from the set of problems that have been registered for the corresponding position).
	 * Updating an application consists in submitting a solution to the corresponding problem (a piece of text
	 * with explanations and a link to the code), registering the submission moment, and changing the status
	 * to SUBMITTED.
	 * 
	 * Analysis of sentence coverage
	 * ~15%
	 * Analysis of data coverage
	 * ~40%
	 */
	@Test
	public void Diver04() {
		final Object testingData[][] = {
			{
				// Test positivo: crear application
				// 
				null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver04((Class<?>) testingData[i][0]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver04(final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("soyUnaCalle");
			rookie.setEmail("soyUnaPrueba@soyUnaPrueba");
			rookie.setName("soyUnNombre");
			rookie.setPhone("123456");
			rookie.setPhoto("http://SoyUnaFoto");
			rookie.setSurname("SoyUnaPreuba");
			rookie.setVatNumber("dd33f");
			rookie.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			rookie.getUserAccount().setPassword(hashPassword);

			final Rookie rookieSave = this.rookieService.saveCreate(rookie);

			super.authenticate(rookieSave.getUserAccount().getUsername());

			final Application application = this.applicationService.create();
			final List<Position> position = (List<Position>) this.positionService.findALL();
			application.setPosition(position.get(0));
			application.setRookie(rookieSave);
			application.setCreationMoment(LocalDate.now().toDate());

			final List<Problem> problems = (List<Problem>) this.problemService.allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(application.getPosition().getId());

			final Integer alatorio = (int) Math.floor(Math.random() * (problems.size()));
			final Problem seleccion = problems.get(alatorio);
			application.setProblem(seleccion);

			final Application applicationSave = this.applicationService.save(application);

			this.rookieService.flush();
			this.applicationService.flush();

		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 10. An actor who is authenticated as a rookie must be able to:
	 * 
	 * 1. Manage his or her applications, which includes listing them grouped by status, showing them,
	 * creating them, and updating them. When an application is created, the system assigns an arbitrary
	 * problem to it (from the set of problems that have been registered for the corresponding position).
	 * Updating an application consists in submitting a solution to the corresponding problem (a piece of text
	 * with explanations and a link to the code), registering the submission moment, and changing the status
	 * to SUBMITTED.
	 * 
	 * Analysis of sentence coverage
	 * ~15%
	 * Analysis of data coverage
	 * ~40%
	 */
	@Test
	public void Diver05() {
		final Object testingData[][] = {
			{
				// Test positivo: editar application
				// 
				"http://", "response", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver05((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver05(final String link, final String response, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("soyUnaCalle");
			rookie.setEmail("soyUnaPrueba@soyUnaPrueba");
			rookie.setName("soyUnNombre");
			rookie.setPhone("123456");
			rookie.setPhoto("http://SoyUnaFoto");
			rookie.setSurname("SoyUnaPreuba");
			rookie.setVatNumber("dd33f");

			rookie.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			rookie.getUserAccount().setPassword(hashPassword);

			final Rookie rookieSave = this.rookieService.saveCreate(rookie);

			super.authenticate(rookieSave.getUserAccount().getUsername());

			final Application application = this.applicationService.create();
			final List<Position> position = (List<Position>) this.positionService.findALL();
			application.setPosition(position.get(0));
			application.setRookie(rookieSave);
			application.setCreationMoment(LocalDate.now().toDate());

			final List<Problem> problems = (List<Problem>) this.problemService.allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(application.getPosition().getId());

			final Integer alatorio = (int) Math.floor(Math.random() * (problems.size()));
			final Problem seleccion = problems.get(alatorio);
			application.setProblem(seleccion);

			final Application applicationSave = this.applicationService.save(application);

			application.setLink(link);
			application.setResponse(response);

			final Application applicationSave1 = this.applicationService.save(applicationSave);

			this.rookieService.flush();
			this.applicationService.flush();

		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 10. An actor who is authenticated as a rookie must be able to:
	 * 
	 * 1. Manage his or her applications, which includes listing them grouped by status, showing them,
	 * creating them, and updating them. When an application is created, the system assigns an arbitrary
	 * problem to it (from the set of problems that have been registered for the corresponding position).
	 * Updating an application consists in submitting a solution to the corresponding problem (a piece of text
	 * with explanations and a link to the code), registering the submission moment, and changing the status
	 * to SUBMITTED.
	 * 
	 * Analysis of sentence coverage
	 * ~15%
	 * Analysis of data coverage
	 * ~40%
	 */
	@Test
	public void Diver06() {
		final Object testingData[][] = {
			{
				// Test positivo: editar application
				// 
				"http://", "response", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver06((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver06(final String link, final String response, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Rookie rookie = this.rookieService.create();
			rookie.setAddress("soyUnaCalle");
			rookie.setEmail("soyUnaPrueba@soyUnaPrueba");
			rookie.setName("soyUnNombre");
			rookie.setPhone("123456");
			rookie.setPhoto("http://SoyUnaFoto");
			rookie.setSurname("SoyUnaPreuba");
			rookie.setVatNumber("dd33f");

			rookie.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			rookie.getUserAccount().setPassword(hashPassword);

			final Rookie rookieSave = this.rookieService.saveCreate(rookie);

			final Application application = this.applicationService.create();
			final List<Position> position = (List<Position>) this.positionService.findALL();
			application.setPosition(position.get(0));
			application.setRookie(rookieSave);
			application.setCreationMoment(LocalDate.now().toDate());

			final List<Problem> problems = (List<Problem>) this.problemService.allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(application.getPosition().getId());

			final Integer alatorio = (int) Math.floor(Math.random() * (problems.size()));
			final Problem seleccion = problems.get(alatorio);
			application.setProblem(seleccion);

			final Application applicationSave = this.applicationService.save(application);

			application.setLink(link);
			application.setResponse(response);

			final Application applicationSave1 = this.applicationService.save(applicationSave);

			this.rookieService.flush();
			this.applicationService.flush();

		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 9 An actor who is authenticated as a company must be able to:
	 * 3. List and show their applications
	 * Analysis of sentence coverage
	 * ~8.9%
	 * Analysis of data coverage
	 * ~10%
	 */
	@Test
	public void Driver07() {
		final Object testingData[][] = {
			{ // Positive
				"company", null
			}, { // Negative
				"rookie", NullPointerException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.Driver07((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void Driver07(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);
			this.applicationService.getAcceptedApplicationsByLoggedCompany();
			this.applicationService.getRejectedApplicationsByLoggedCompany();
			this.applicationService.getSubmittedApplicationsByLoggedCompany();
		} catch (final Exception e) {
			caught = e.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 9 An actor who is authenticated as a company must be able to:
	 * 3. Update their applications
	 * Analysis of sentence coverage
	 * ~3.4%
	 * Analysis of data coverage
	 * ~5%
	 */
	@Test
	public void Driver08() {
		final Object testingData[][] = {
			{ // Positive
				"company", null
			}, { // Negative
				"rookie", NullPointerException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.Driver08((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void Driver08(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);
			final Collection<Application> apps = this.applicationService.getSubmittedApplicationsByLoggedCompany();
			if (apps.size() > 0)
				this.applicationService.accept(apps.iterator().next().getId());

		} catch (final Exception e) {
			caught = e.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}
}
