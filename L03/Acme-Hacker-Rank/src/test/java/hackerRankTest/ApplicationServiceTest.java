/*
 * SampleTest.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package hackerRankTest;

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
import services.HackerService;
import services.PositionService;
import services.ProblemService;
import utilities.AbstractTest;
import domain.Application;
import domain.Hacker;
import domain.Position;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private PositionService		positionService;


	/*
	 * 10. An actor who is authenticated as a hacker must be able to:
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
			authority.setAuthority(Authority.HACKER);
			Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(authority));

			final Collection<Application> applications = this.applicationService.getApplicationsByHacker(1234);
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
	 * 10. An actor who is authenticated as a hacker must be able to:
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

			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("soyUnaCalle");
			hacker.setEmail("soyUnaPrueba@soyUnaPrueba");
			hacker.setName("soyUnNombre");
			hacker.setPhone("123456");
			hacker.setPhoto("http://SoyUnaFoto");
			hacker.setSurname("SoyUnaPreuba");
			hacker.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			hacker.getUserAccount().setPassword(hashPassword);

			final Hacker hackerSave = this.hackerService.saveCreate(hacker);

			final Collection<Application> applications = this.applicationService.getApplicationsByHacker(hackerSave.getId());
			for (final Application application : applications) {
				application.getApplyMoment();
				application.getCurricula();
				application.getLink();
			}

			this.hackerService.flush();
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
	 * 10. An actor who is authenticated as a hacker must be able to:
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

			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("soyUnaCalle");
			hacker.setEmail("soyUnaPrueba@soyUnaPrueba");
			hacker.setName("soyUnNombre");
			hacker.setPhone("123456");
			hacker.setPhoto("http://SoyUnaFoto");
			hacker.setSurname("SoyUnaPreuba");
			hacker.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			hacker.getUserAccount().setPassword(hashPassword);

			final Hacker hackerSave = this.hackerService.saveCreate(hacker);

			final Application application = this.applicationService.create();
			final List<Position> position = (List<Position>) this.positionService.findALL();
			application.setPosition(position.get(0));
			application.setHacker(hackerSave);
			application.setCreationMoment(LocalDate.now().toDate());

			final List<Problem> problems = (List<Problem>) this.problemService.allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(application.getPosition().getId());

			final Integer alatorio = (int) Math.floor(Math.random() * (problems.size()));
			final Problem seleccion = problems.get(alatorio);
			application.setProblem(seleccion);

			final Application applicationSave = this.applicationService.save(application);

			this.hackerService.flush();
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
	 * 10. An actor who is authenticated as a hacker must be able to:
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

			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("soyUnaCalle");
			hacker.setEmail("soyUnaPrueba@soyUnaPrueba");
			hacker.setName("soyUnNombre");
			hacker.setPhone("123456");
			hacker.setPhoto("http://SoyUnaFoto");
			hacker.setSurname("SoyUnaPreuba");
			hacker.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			hacker.getUserAccount().setPassword(hashPassword);

			final Hacker hackerSave = this.hackerService.saveCreate(hacker);

			super.authenticate(hackerSave.getUserAccount().getUsername());

			final Application application = this.applicationService.create();
			final List<Position> position = (List<Position>) this.positionService.findALL();
			application.setPosition(position.get(0));
			application.setHacker(hackerSave);
			application.setCreationMoment(LocalDate.now().toDate());

			final List<Problem> problems = (List<Problem>) this.problemService.allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(application.getPosition().getId());

			final Integer alatorio = (int) Math.floor(Math.random() * (problems.size()));
			final Problem seleccion = problems.get(alatorio);
			application.setProblem(seleccion);

			final Application applicationSave = this.applicationService.save(application);

			this.hackerService.flush();
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
	 * 10. An actor who is authenticated as a hacker must be able to:
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

			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("soyUnaCalle");
			hacker.setEmail("soyUnaPrueba@soyUnaPrueba");
			hacker.setName("soyUnNombre");
			hacker.setPhone("123456");
			hacker.setPhoto("http://SoyUnaFoto");
			hacker.setSurname("SoyUnaPreuba");
			hacker.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			hacker.getUserAccount().setPassword(hashPassword);

			final Hacker hackerSave = this.hackerService.saveCreate(hacker);

			super.authenticate(hackerSave.getUserAccount().getUsername());

			final Application application = this.applicationService.create();
			final List<Position> position = (List<Position>) this.positionService.findALL();
			application.setPosition(position.get(0));
			application.setHacker(hackerSave);
			application.setCreationMoment(LocalDate.now().toDate());

			final List<Problem> problems = (List<Problem>) this.problemService.allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(application.getPosition().getId());

			final Integer alatorio = (int) Math.floor(Math.random() * (problems.size()));
			final Problem seleccion = problems.get(alatorio);
			application.setProblem(seleccion);

			final Application applicationSave = this.applicationService.save(application);

			application.setLink(link);
			application.setResponse(response);

			final Application applicationSave1 = this.applicationService.save(applicationSave);

			this.hackerService.flush();
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
	 * 10. An actor who is authenticated as a hacker must be able to:
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

			final Hacker hacker = this.hackerService.create();
			hacker.setAddress("soyUnaCalle");
			hacker.setEmail("soyUnaPrueba@soyUnaPrueba");
			hacker.setName("soyUnNombre");
			hacker.setPhone("123456");
			hacker.setPhoto("http://SoyUnaFoto");
			hacker.setSurname("SoyUnaPreuba");
			hacker.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			hacker.getUserAccount().setPassword(hashPassword);

			final Hacker hackerSave = this.hackerService.saveCreate(hacker);

			final Application application = this.applicationService.create();
			final List<Position> position = (List<Position>) this.positionService.findALL();
			application.setPosition(position.get(0));
			application.setHacker(hackerSave);
			application.setCreationMoment(LocalDate.now().toDate());

			final List<Problem> problems = (List<Problem>) this.problemService.allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(application.getPosition().getId());

			final Integer alatorio = (int) Math.floor(Math.random() * (problems.size()));
			final Problem seleccion = problems.get(alatorio);
			application.setProblem(seleccion);

			final Application applicationSave = this.applicationService.save(application);

			application.setLink(link);
			application.setResponse(response);

			final Application applicationSave1 = this.applicationService.save(applicationSave);

			this.hackerService.flush();
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

}
