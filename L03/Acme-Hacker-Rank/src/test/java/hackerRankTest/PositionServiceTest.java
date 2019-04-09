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

import java.util.ArrayList;
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

import services.CompanyService;
import services.PositionService;
import utilities.AbstractTest;
import domain.Company;
import domain.Position;

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

}
