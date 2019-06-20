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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import services.CompanyService;
import services.PositionService;
import services.ProblemService;
import utilities.AbstractTest;
import domain.Company;
import domain.Position;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionServiceTest extends AbstractTest {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private CompanyService	companyService;

	@Autowired
	private ProblemService	problemService;


	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 
	 * 2. List the positions available and navigate to the corresponding companies.
	 * 
	 * Analysis of sentence coverage
	 * 10,8%
	 * Analysis of data coverage
	 * ~70%
	 */
	@Test
	public void Diver01() {
		final Object testingData[][] = {
			{
				// Test positivo: Listar position
				// Usuario
				null, null
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

			if (user != null)
				super.authenticate(user);

			final Collection<Position> positions = this.positionService.findAllPositionWithStatusTrueCancelFalse();
			for (final Position position : positions) {
				position.getDeadline();
				position.getDescription();
				position.getProfile();
				position.getSalary();
				position.getSkills();
				position.getStatus();
				position.getTechs();
				position.getTitle();
				position.getCompany();
			}

			this.positionService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			if (user != null)
				super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 
	 * 2. List the positions available and navigate to the corresponding companies.
	 * 
	 * Analysis of sentence coverage
	 * 10,8%
	 * Analysis of data coverage
	 * ~70%
	 */
	@Test
	public void Diver02() {
		final Object testingData[][] = {
			{
				// Test negativo: Listar position
				// Usuario
				null, IndexOutOfBoundsException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver02((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver02(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			if (user != null)
				super.authenticate(user);

			final List<Position> positions = new ArrayList<>();
			if (positions.size() == 0) {
				positions.get(0).getDeadline();
				positions.get(0).getDescription();
				positions.get(0).getProfile();
				positions.get(0).getSalary();
				positions.get(0).getSkills();
				positions.get(0).getStatus();
				positions.get(0).getTechs();
				positions.get(0).getTitle();
				positions.get(0).getCompany();
			}

			this.positionService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			if (user != null)
				super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 3. List the companies available and navigate to the corresponding positions.
	 * 
	 * Analysis of sentence coverage
	 * 10,8%
	 * Analysis of data coverage
	 * ~70%
	 */
	@Test
	public void Diver03() {
		final Object testingData[][] = {
			{
				// Test positivo: Listar position by company
				// Usuario
				null, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver03((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver03(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Company company = this.companyService.create();
			company.setAddress("soyUnaCalle");
			company.setCommercialName("soyUnaPrueba");
			company.setEmail("soyUnaPrueba@soyUnaPrueba");
			company.setName("soyUnNombre");
			company.setPhone("123456");
			company.setPhoto("http://SoyUnaFoto");
			company.setSurname("SoyUnaPreuba");
			company.setVatNumber("hh55g");
			company.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			company.getUserAccount().setPassword(hashPassword);

			final Company companySave = this.companyService.saveCreate(company);

			super.authenticate(company.getUserAccount().getUsername());

			final Position position = this.positionService.create();
			position.setCancel(false);
			position.setCompany(companySave);
			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 1);
			position.setDeadline(res);
			position.setDescription("soyUnaDescripcion");
			position.setProfile("SoyUnPerfil");
			position.setSalary(100.4);
			position.setSkills("soyUnaHabilidad");
			position.setStatus(false);
			position.setTechs("soyUnTechs");
			position.setTicker("SOYU-1234");
			position.setTitle("soyUnTitulo");
			final Position positionSave = this.positionService.save(position);

			super.unauthenticate();

			final List<Position> positionByCompanys = (List<Position>) this.positionService.findAllPositionStatusTrueCancelFalseByCompany(companySave.getId());
			for (final Position positionByCompany : positionByCompanys) {
				positionByCompany.getDeadline();
				positionByCompany.getDescription();
				positionByCompany.getProfile();
				positionByCompany.getSalary();
				positionByCompany.getSkills();
				positionByCompany.getStatus();
				positionByCompany.getTechs();
				positionByCompany.getTitle();
				positionByCompany.getCompany();
			}

			this.positionService.flush();
			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			if (user != null)
				super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 3. List the companies available and navigate to the corresponding positions.
	 * 
	 * Analysis of sentence coverage
	 * 10,8%
	 * Analysis of data coverage
	 * ~70%
	 */
	@Test
	public void Diver04() {
		final Object testingData[][] = {
			{
				// Test negativo: Listar position by company
				// Usuario
				null, IndexOutOfBoundsException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver04((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver04(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Company company = this.companyService.create();
			company.setAddress("soyUnaCalle");
			company.setCommercialName("soyUnaPrueba");
			company.setEmail("soyUnaPrueba@soyUnaPrueba");
			company.setName("soyUnNombre");
			company.setPhone("123456");
			company.setPhoto("http://SoyUnaFoto");
			company.setSurname("SoyUnaPreuba");
			company.setVatNumber("hh55g");
			company.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			company.getUserAccount().setPassword(hashPassword);

			final Company companySave = this.companyService.saveCreate(company);

			final List<Position> positionByCompanys = (List<Position>) this.positionService.findAllPositionStatusTrueCancelFalseByCompany(companySave.getId());
			positionByCompanys.get(0).getDeadline();
			positionByCompanys.get(0).getDescription();
			positionByCompanys.get(0).getProfile();
			positionByCompanys.get(0).getSalary();
			positionByCompanys.get(0).getSkills();
			positionByCompanys.get(0).getStatus();
			positionByCompanys.get(0).getTechs();
			positionByCompanys.get(0).getTitle();
			positionByCompanys.get(0).getCompany();

			this.positionService.flush();
			this.companyService.flush();

		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			if (user != null)
				super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 
	 * 2. List the positions available and navigate to the corresponding companies.
	 * 
	 * Analysis of sentence coverage
	 * 19,8%
	 * Analysis of data coverage
	 * ~85%
	 */
	@Test
	public void Diver05() {
		final Object testingData[][] = {
			{
				// Test positivo: Show position
				// Usuario
				null, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver05((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver05(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Company company = this.companyService.create();
			company.setAddress("soyUnaCalle");
			company.setCommercialName("soyUnaPrueba");
			company.setEmail("soyUnaPrueba@soyUnaPrueba");
			company.setName("soyUnNombre");
			company.setPhone("123456");
			company.setPhoto("http://SoyUnaFoto");
			company.setSurname("SoyUnaPreuba");
			company.setVatNumber("hh55g");
			company.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			company.getUserAccount().setPassword(hashPassword);

			final Company companySave = this.companyService.saveCreate(company);

			super.authenticate(company.getUserAccount().getUsername());

			final Position position = this.positionService.create();
			position.setCancel(false);
			position.setCompany(companySave);
			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 1);
			position.setDeadline(res);
			position.setDescription("soyUnaDescripcion");
			position.setProfile("SoyUnPerfil");
			position.setSalary(100.4);
			position.setSkills("soyUnaHabilidad");
			position.setStatus(false);
			position.setTechs("soyUnTechs");
			position.setTicker("SOYU-1234");
			position.setTitle("soyUnTitulo");
			final Position positionSave = this.positionService.save(position);

			super.unauthenticate();

			final Position posifionFind = this.positionService.findOne(positionSave.getId());

			posifionFind.getCompany();
			posifionFind.getDeadline();
			posifionFind.getProfile();

			this.positionService.flush();
			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			if (user != null)
				super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 
	 * 2. List the positions available and navigate to the corresponding companies.
	 * 
	 * Analysis of sentence coverage
	 * 19,8%
	 * Analysis of data coverage
	 * ~85%
	 */
	@Test
	public void Diver06() {
		final Object testingData[][] = {
			{
				// Test positivo: Show position
				// Usuario
				null, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver06((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver06(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Company company = this.companyService.create();
			company.setAddress("soyUnaCalle");
			company.setCommercialName("soyUnaPrueba");
			company.setEmail("soyUnaPrueba@soyUnaPrueba");
			company.setName("soyUnNombre");
			company.setPhone("123456");
			company.setPhoto("http://SoyUnaFoto");
			company.setSurname("SoyUnaPreuba");
			company.setVatNumber("hh55g");
			company.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			company.getUserAccount().setPassword(hashPassword);

			final Company companySave = this.companyService.saveCreate(company);

			super.authenticate(company.getUserAccount().getUsername());

			final Position position = this.positionService.create();
			position.setCancel(false);
			position.setCompany(companySave);
			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 1);
			position.setDeadline(res);
			position.setDescription("soyUnaDescripcion");
			position.setProfile("SoyUnPerfil");
			position.setSalary(100.4);
			position.setSkills("soyUnaHabilidad");
			position.setStatus(false);
			position.setTechs("soyUnTechs");
			position.setTicker("SOYU-1234");
			position.setTitle("soyUnTitulo");
			final Position positionSave = this.positionService.save(position);

			super.unauthenticate();

			final Position posifionFind = this.positionService.findOne(00000);

			posifionFind.getCompany();
			posifionFind.getDeadline();
			posifionFind.getProfile();

			this.positionService.flush();
			this.companyService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
			if (user != null)
				super.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 4. Search for a position using a single key word that must be contained in its title, its description,
	 * its profile, its skills, its technologies, or the name of the corresponding company.
	 * 
	 * Analysis of sentence coverage
	 * ~10%
	 * Analysis of data coverage
	 * ~45%
	 */
	@Test
	public void Diver07() {
		final Object testingData[][] = {
			{
				// Test positivo: Finder
				// Usuario
				null, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver07((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver07(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Company company = this.companyService.create();
			company.setAddress("soyUnaCalle");
			company.setCommercialName("soyUnaPrueba");
			company.setEmail("soyUnaPrueba@soyUnaPrueba");
			company.setName("soyUnNombre");
			company.setPhone("123456");
			company.setPhoto("http://SoyUnaFoto");
			company.setSurname("SoyUnaPreuba");
			company.setVatNumber("hh55g");
			company.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			company.getUserAccount().setPassword(hashPassword);

			final Company companySave = this.companyService.saveCreate(company);

			super.authenticate(company.getUserAccount().getUsername());

			final Position position = this.positionService.create();
			position.setCancel(false);
			position.setCompany(companySave);
			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 1);
			position.setDeadline(res);
			position.setDescription("soyUnaDescripcion");
			position.setProfile("SoyUnPerfil");
			position.setSalary(100.4);
			position.setSkills("soyUnaHabilidad");
			position.setStatus(false);
			position.setTechs("soyUnTechs");
			position.setTicker("SOYU-1234");
			position.setTitle("soyUnTitulo");
			final Position positionSave = this.positionService.save(position);

			super.unauthenticate();

			final HashSet<Position> positions = (HashSet<Position>) this.positionService.search("soyUnaHabilidad");
			for (final Position positionS : positions) {
				positionS.getDeadline();
				positionS.getDescription();
				positionS.getProfile();
				positionS.getSalary();
				positionS.getSkills();
				positionS.getStatus();
				positionS.getTechs();
				positionS.getTitle();
				positionS.getCompany();
			}

			this.positionService.flush();
			this.companyService.flush();

		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	/*
	 * 7. An actor who is not authenticated must be able to:
	 * 4. Search for a position using a single key word that must be contained in its title, its description,
	 * its profile, its skills, its technologies, or the name of the corresponding company.
	 * 
	 * Analysis of sentence coverage
	 * ~10%
	 * Analysis of data coverage
	 * ~45%
	 */
	@Test
	public void Diver08() {
		final Object testingData[][] = {
			{
				// Test negativo: Finder
				// Usuario
				null, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Diver08((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void Diver08(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.startTransaction();

			final Company company = this.companyService.create();
			company.setAddress("soyUnaCalle");
			company.setCommercialName("soyUnaPrueba");
			company.setEmail("soyUnaPrueba@soyUnaPrueba");
			company.setName("soyUnNombre");
			company.setPhone("123456");
			company.setPhoto("http://SoyUnaFoto");
			company.setSurname("SoyUnaPreuba");
			company.setVatNumber("hh55g");
			company.getUserAccount().setUsername("soyUnaPrueba");

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String hashPassword = encoder.encodePassword("soyUnaContrasena", null);
			company.getUserAccount().setPassword(hashPassword);

			final Company companySave = this.companyService.saveCreate(company);

			super.authenticate(company.getUserAccount().getUsername());

			final Position position = this.positionService.create();
			position.setCancel(false);
			position.setCompany(companySave);
			final Date res = LocalDateTime.now().toDate();
			res.setMonth(res.getMonth() + 1);
			position.setDeadline(res);
			position.setDescription("soyUnaDescripcion");
			position.setProfile("SoyUnPerfil");
			position.setSalary(100.4);
			position.setSkills("soyUnaHabilidad");
			position.setStatus(true);
			position.setTechs("soyUnTechs");
			position.setTicker("SOYU-1234");
			position.setTitle("soyUnTitulo");
			final Position positionSave = this.positionService.save(position);

			super.unauthenticate();

			final HashSet<Position> positions = (HashSet<Position>) this.positionService.search("SoyUnaPatataFrita");
			// Si no ecuenrta nada, no puestro nada. Controlado en controlador no en servicio
			Assert.isTrue(!positions.isEmpty());

			this.positionService.flush();
			this.companyService.flush();

		} catch (final Throwable oops) {
			System.out.println(oops);
			caught = oops.getClass();
		} finally {
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	// -------------- Req 9.1
	/*
	 * 9 An actor who is authenticated as a company must be able to:
	 * 1. List their positions and show them
	 * (We are not testing show in a different driver because we consider that it is tested in this one.
	 * Also, everyone can see positions, but view is different for the company. We can't test that here.)
	 * 
	 * Analysis of sentence coverage
	 * ~16%
	 * Analysis of data coverage
	 * ~90%
	 */
	@Test
	public void Driver09() {
		final Object testingData[][] = {
			{
				"company", "list", null
			}, {
				"company", "insertAndList", null
			}, {
				"company", "updateAndList", null
			}, {
				"company", "deleteAndList", null
			}, {
				"company", "notEmpty", null
			}, {
				"rookie", "list", IllegalArgumentException.class
			}, {
				"admin", "list", IllegalArgumentException.class
			}, {
				"rookie", "insertAndList", IllegalArgumentException.class
			}, {
				"admin", "updateAndList", IllegalArgumentException.class
			}, {
				"rookie", "deleteAndList", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Driver09((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	private void Driver09(final String user, final String mode, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);

			Collection<Position> positions = null;
			Position position = null;
			boolean isInList = false;

			switch (mode) {
			case "list":
				positions = this.positionService.findAllPositionsByLoggedCompany();
				break;
			case "insertAndList":
				position = this.testPosition();
				this.positionService.save(position);
				positions = this.positionService.findAllPositionsByLoggedCompany();

				isInList = false;
				for (final Position p : positions)
					if (p.getTitle().equals(position.getTitle())) {
						isInList = true;
						break;
					}
				Assert.isTrue(isInList);
				break;
			case "updateAndList":
				positions = this.positionService.findAllPositionsByLoggedCompany();
				position = this.positionService.findOne(positions.iterator().next().getId());
				position.setTitle("Random123");
				isInList = false;
				for (final Position p : positions)
					if (p.getTitle().equals("Random123")) {
						isInList = true;
						break;
					}
				Assert.isTrue(isInList);
				break;
			case "deleteAndList":
				position = this.testPosition();
				position = this.positionService.save(position);
				positions = this.positionService.findAllPositionsByLoggedCompany();
				this.positionService.delete(position.getId());
				positions = this.positionService.findAllPositionsByLoggedCompany();
				isInList = false;
				for (final Position p : positions)
					if (p.getTitle().equals(position.getTitle())) {
						isInList = true;
						break;
					}
				Assert.isTrue(isInList == false);
				break;
			case "notEmpty":
				position = this.testPosition();
				this.positionService.save(position);
				positions = this.positionService.findAllPositionsByLoggedCompany();
				Assert.isTrue(positions.size() > 0);
				break;
			default:
				break;
			}

		} catch (final Exception e) {
			caught = e.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	private Position testPosition() {
		final Position position = new Position();
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		position.setDeadline(calendar.getTime());
		position.setCompany(this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId()));
		position.setDescription("Test");
		position.setProfile("Test");
		position.setSalary(10d);
		position.setSkills("Test");
		position.setTechs("Test");
		position.setTitle("Testing position");
		position.setStatus(false);
		position.setCancel(false);
		position.setTicker("I am an impossible ticker");
		return position;
	}

	/*
	 * 9 An actor who is authenticated as a company must be able to:
	 * 1. Create their positions
	 * Analysis of sentence coverage
	 * ~26.9%
	 * Analysis of data coverage
	 * ~50%
	 */
	@Test
	public void Driver10() {
		final Object testingData[][] = {
			{
				"company", "create", null
			}, {
				"company", "createAndFind", null
			}, {
				"company", "createFinalWithProblems", null
			}, {
				"company", "createAndUpdate", null
			}, {
				"company", "createAndDelete", null
			}, {
				"company", "createFinalWithNoProblems", IllegalArgumentException.class
			}, {
				"admin", "create", IllegalArgumentException.class
			}, {
				"rookie", "create", IllegalArgumentException.class
			}, {
				"admin", "createAndFind", IllegalArgumentException.class
			}, {
				"rookie", "createAndFind", IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.Driver10((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	private void Driver10(final String user, final String mode, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);
			Position position = null;
			Collection<Problem> problems = null;
			Position posCopy = null;
			switch (mode) {
			case "create":
				position = this.testPosition();
				this.positionService.save(position);
				break;
			case "createAndFind":
				position = this.testPosition();
				position = this.positionService.save(position);
				position = this.positionService.findOne(position.getId());
				Assert.notNull(position);
				break;
			case "createFinalWithProblems":
				position = this.testPosition();
				position = this.positionService.save(position);
				problems = this.problemService.getFinalModeFromLoggedCompanyNotInPosition(position.getId());
				this.positionService.addProblemToPosition(problems.iterator().next().getId(), position.getId());
				this.positionService.addProblemToPosition(problems.iterator().next().getId(), position.getId());
				posCopy = this.testPosition();
				posCopy.setId(position.getId());
				posCopy.setVersion(position.getVersion());
				posCopy.setStatus(true);
				position = this.positionService.save(posCopy);
				break;
			case "createAndUpdate":
				position = this.testPosition();
				position = this.positionService.save(position);
				position.setTitle("tested");
				position = this.positionService.save(position);
				break;
			case "createAndDelete":
				position = this.testPosition();
				position = this.positionService.save(position);
				this.positionService.delete(position.getId());
				break;
			case "createFinalWithNoProblems":
				position = this.testPosition();
				posCopy = this.testPosition();
				posCopy.setId(position.getId());
				posCopy.setVersion(position.getVersion());
				posCopy.setStatus(true);
				position = this.positionService.save(posCopy);
				break;
			default:
				break;
			}
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
	 * 1. Update their positions
	 * Analysis of sentence coverage
	 * ~29.2%
	 * Analysis of data coverage
	 * ~30%
	 */
	@Test
	public void Driver11() {
		final Object testingData[][] = {
			{
				"company", "update", null
			}, {
				"company", "updateAndFind", null
			}, {
				"company", "updateFinalWithProblems", null
			}, {
				"company", "updateCancel", null
			}, {
				"company", "updateAndDelete", null
			}, {
				"company", "updateAlreadyFinal", IllegalArgumentException.class
			}, {
				"company", "updateAlreadyCancelled", IllegalArgumentException.class
			}, {
				"rookie", "update", IllegalArgumentException.class
			}, {
				"admin", "update", IllegalArgumentException.class
			}, {
				"rookie", "updateAndFind", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Driver11((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void Driver11(final String user, final String mode, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);

			Position position = null;
			Collection<Problem> problems = null;
			Position posCopy = null;
			switch (mode) {
			case "update":
				position = this.testPosition();
				position = this.positionService.save(position);
				position.setTitle("tested");
				position = this.positionService.save(position);
				break;
			case "updateAndFind":
				position = this.testPosition();
				position = this.positionService.save(position);
				position.setTitle("tested");
				position = this.positionService.save(position);
				position = this.positionService.findOne(position.getId());
				Assert.notNull(position);
				break;
			case "updateFinalWithProblems":
				position = this.testPosition();
				position = this.positionService.save(position);
				problems = this.problemService.getFinalModeFromLoggedCompanyNotInPosition(position.getId());
				this.positionService.addProblemToPosition(problems.iterator().next().getId(), position.getId());
				this.positionService.addProblemToPosition(problems.iterator().next().getId(), position.getId());
				posCopy = this.testPosition();
				posCopy.setId(position.getId());
				posCopy.setVersion(position.getVersion());
				posCopy.setStatus(true);
				position = this.positionService.save(posCopy);
				break;
			case "updateCancel":
				position = this.testPosition();
				position = this.positionService.save(position);
				problems = this.problemService.getFinalModeFromLoggedCompanyNotInPosition(position.getId());
				posCopy = this.testPosition();
				posCopy.setId(position.getId());
				posCopy.setVersion(position.getVersion());
				posCopy.setCancel(true);
				position = this.positionService.save(posCopy);
				break;
			case "updateAndDelete":
				position = this.testPosition();
				position = this.positionService.save(position);
				position.setTechs("updating");
				this.positionService.delete(position.getId());
				break;
			case "updateAlreadyFinal":
				position = this.testPosition();
				posCopy = this.testPosition();
				posCopy.setId(position.getId());
				posCopy.setVersion(position.getVersion());
				posCopy.setStatus(true);
				position = this.positionService.save(posCopy);
				posCopy = this.testPosition();
				posCopy.setId(position.getId());
				posCopy.setVersion(position.getVersion());
				posCopy.setCancel(true);
				position = this.positionService.save(posCopy);
				break;
			case "updateAlreadyCancelled":
				position = this.testPosition();
				posCopy = this.testPosition();
				posCopy.setId(position.getId());
				posCopy.setVersion(position.getVersion());
				posCopy.setCancel(true);
				position = this.positionService.save(posCopy);
				posCopy = this.testPosition();
				posCopy.setId(position.getId());
				posCopy.setVersion(position.getVersion());
				posCopy.setStatus(true);
				position = this.positionService.save(posCopy);
				break;
			default:
				break;
			}
		} catch (final Exception e) {
			caught = e.getClass();
		} finally {
			this.rollbackTransaction();
			this.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}
	/*
	 * 9 An actor who is authenticated as a company must be able to:
	 * 1. Delete their positions
	 * Analysis of sentence coverage
	 * ~31.1%
	 * Analysis of data coverage
	 * ~80%
	 */
	@Test
	public void Driver12() {
		final Object testingData[][] = {
			{
				"company", "createAndDelete", null
			}, {
				"company", "updateAndDelete", null
			}, {
				"company", "deleteAndList", null
			}, {
				"company", "deleteAndTryFind", null
			}, {
				"company", "deleteAndTryFindInList", null
			}, {
				"company", "deleteAlreadyFinal", IllegalArgumentException.class
			}, {
				"company", "deleteAlreadyCancelled", IllegalArgumentException.class
			}, {
				"rookie", "createAndDelete", IllegalArgumentException.class
			}, {
				"admin", "createAndDelete", IllegalArgumentException.class
			}, {
				"rookie", "deleteAndTryFind", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.Driver12((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	private void Driver12(final String user, final String mode, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);

			Position position = null;
			Collection<Problem> problems = null;
			Collection<Position> positions = null;
			Position posCopy = null;
			switch (mode) {
			case "createAndDelete":
				position = this.testPosition();
				position = this.positionService.save(position);
				this.positionService.delete(position.getId());
				break;
			case "updateAndDelete":
				position = this.testPosition();
				position = this.positionService.save(position);
				position.setTechs("updating");
				this.positionService.delete(position.getId());
				break;
			case "deleteAndList":
				position = this.testPosition();
				position = this.positionService.save(position);
				this.positionService.delete(position.getId());
				this.positionService.findAllPositionsByLoggedCompany();
				break;
			case "deleteAndTryFind":
				position = this.testPosition();
				position = this.positionService.save(position);
				this.positionService.delete(position.getId());
				position = this.positionService.findOne(position.getId());
				Assert.isNull(position);
				break;
			case "deleteAndTryFindInList":
				position = this.testPosition();
				position = this.positionService.save(position);
				this.positionService.delete(position.getId());
				positions = this.positionService.findAllPositionsByLoggedCompany();
				for (final Position pos : positions)
					Assert.isTrue(pos.equals(position) == false);
				break;
			case "deleteAlreadyFinal":
				position = this.testPosition();
				position = this.positionService.save(position);
				posCopy = this.testPosition();
				posCopy.setId(position.getId());
				posCopy.setVersion(position.getVersion());
				problems = this.problemService.getFinalModeFromLoggedCompanyNotInPosition(position.getId());
				this.positionService.addProblemToPosition(problems.iterator().next().getId(), position.getId());
				this.positionService.addProblemToPosition(problems.iterator().next().getId(), position.getId());
				posCopy.setStatus(true);
				position = this.positionService.save(posCopy);
				this.positionService.delete(position.getId());
				break;
			case "deleteAlreadyCancelled":
				position = this.testPosition();
				position = this.positionService.save(position);
				posCopy = this.testPosition();
				posCopy.setId(position.getId());
				posCopy.setVersion(position.getVersion());
				problems = this.problemService.getFinalModeFromLoggedCompanyNotInPosition(position.getId());
				this.positionService.addProblemToPosition(problems.iterator().next().getId(), position.getId());
				this.positionService.addProblemToPosition(problems.iterator().next().getId(), position.getId());
				posCopy.setStatus(true);
				posCopy.setCancel(true);
				position = this.positionService.save(posCopy);
				this.positionService.delete(position.getId());
				break;
			default:
				break;
			}
		} catch (final Exception e) {
			caught = e.getClass();
		} finally {
			this.rollbackTransaction();
			this.unauthenticate();
		}
		this.checkExceptions(expected, caught);
	}
}
