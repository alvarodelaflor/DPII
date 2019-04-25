
package hackerRankTest;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.LoginService;
import services.CompanyService;
import services.ProblemService;
import utilities.AbstractTest;
import domain.Company;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProblemServiceTest extends AbstractTest {

	@Autowired
	private ProblemService	problemService;

	@Autowired
	private CompanyService	companyService;


	/*
	 * 9 An actor who is authenticated as a company must be able to:
	 * 2. Create their problems
	 * Analysis of sentence coverage
	 * ~5.4%
	 * Analysis of data coverage
	 * ~5%
	 */
	@Test
	public void Driver01() {
		final Object testingData[][] = {
			{ // Positive
				"company", null
			}, { // Negative
				"hacker", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.Driver01((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void Driver01(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);

			this.problemService.save(this.testProblem());
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
	 * 2. List and show their problems
	 * Analysis of sentence coverage
	 * ~6%
	 * Analysis of data coverage
	 * ~5%
	 */
	@Test
	public void Driver02() {
		final Object testingData[][] = {
			{ // Positive
				"company", null
			}, { // Negative
				"hacker", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.Driver02((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void Driver02(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);

			this.problemService.findAllProblemsByLoggedCompany();
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
	 * 2. Update their problems
	 * Analysis of sentence coverage
	 * ~19.1%
	 * Analysis of data coverage
	 * ~10%
	 */
	@Test
	public void Driver03() {
		final Object testingData[][] = {
			{ // Positive
				"company", null
			}, { // Negative
				"hacker", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.Driver03((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void Driver03(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);

			final Problem problem = this.problemService.save(this.testProblem());
			problem.setAttachments("bro");
			this.problemService.save(problem);
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
	 * 2. Update their problems
	 * Analysis of sentence coverage
	 * ~20.4%
	 * Analysis of data coverage
	 * ~10%
	 */
	@Test
	public void Driver04() {
		final Object testingData[][] = {
			{ // Positive
				"company", null
			}, { // Negative
				"hacker", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.Driver04((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void Driver04(final String user, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.startTransaction();
			this.authenticate(user);

			final Problem problem = this.problemService.save(this.testProblem());
			this.problemService.delete(problem.getId());
		} catch (final Exception e) {
			caught = e.getClass();
		} finally {
			this.unauthenticate();
			this.rollbackTransaction();
		}
		this.checkExceptions(expected, caught);
	}

	private Problem testProblem() {
		final Problem problem = new Problem();
		final Company company = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId());
		problem.setAttachments("Attachments");
		problem.setCompany(company);
		problem.setFinalMode(false);
		problem.setHint("Hint");
		problem.setStatement("Statement");
		problem.setTitle("Title");
		return problem;
	}
}
