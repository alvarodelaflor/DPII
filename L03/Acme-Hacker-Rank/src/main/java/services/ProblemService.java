
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProblemRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AuthUtils;
import domain.Company;
import domain.Hacker;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepository;

	@Autowired
	private HackerService		hackerService;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private Validator			validator;


	public Problem create() {
		final Problem problem = new Problem();
		return problem;
	}

	public int getProblemCount(final int positionId) {
		return this.problemRepository.getProblemCount(positionId);
	}

	// countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse ---------------------------------------------------------------
	public Integer countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse(final int id) {

		final UserAccount user = LoginService.getPrincipal();
		final Hacker hacker = this.hackerService.getHackerByUserAccountId(user.getId());
		Assert.isTrue(hacker != null);

		final Integer p = this.problemRepository.countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse(id);
		return p;
	}

	// countAllProblemFinalModeTrueWithPositionStatusTrueCancelFalse ---------------------------------------------------------------
	public Collection<Problem> allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(final int id) {

		final UserAccount user = LoginService.getPrincipal();
		final Hacker hacker = this.hackerService.getHackerByUserAccountId(user.getId());
		Assert.isTrue(hacker != null);

		final Collection<Problem> p = this.problemRepository.allProblemFinalModeTrueWithPositionStatusTrueCancelFalse(id);
		return p;
	}

	// FINDONE ---------------------------------------------------------------
	public Problem findOne(final int id) {
		return this.problemRepository.findOne(id);
	}

	public Collection<Problem> findAllProblemsByLoggedCompany() {
		// We have to be a company
		Assert.isTrue(AuthUtils.checkLoggedAuthority("COMPANY"));
		final int companyId = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId()).getId();
		return this.problemRepository.findFromLoggedCompany(companyId);
	}

	private boolean checkProblemOwner(final int problemId) {
		final int loggedId = LoginService.getPrincipal().getId();
		final int ownerId = this.problemRepository.findOne(problemId).getCompany().getUserAccount().getId();
		return loggedId == ownerId;
	}

	public Problem findOneLoggedIsOwner(final int problemId) {
		Assert.isTrue(this.checkProblemOwner(problemId), "Show failed: Logged user is not the owner of the company");
		return this.problemRepository.findOne(problemId);
	}

	public void delete(final int problemId) {
		// We must be the owner and problem can't be in final mode
		Assert.isTrue(this.checkProblemOwner(problemId), "Delete failed: Logged user is not the owner of the company");
		Assert.isTrue(this.getDatabaseProblemFinalMode(problemId) == false, "Delete failed: Problem is in final mode");

		this.problemRepository.delete(problemId);
	}

	private boolean getDatabaseProblemFinalMode(final int problemId) {
		return this.problemRepository.findOne(problemId).getFinalMode();
	}

	public Problem reconstruct(final Problem problem, final BindingResult binding) {
		Problem res = this.create();
		if (problem.getId() == 0) {
			res = problem;
			final Company owner = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId());

			res.setCompany(owner);
			res.setFinalMode(false);
		} else {
			final Problem dbProblem = this.problemRepository.findOne(problem.getId());
			// These we recover from db
			res.setId(dbProblem.getId());
			res.setVersion(dbProblem.getVersion());
			res.setCompany(dbProblem.getCompany());
			res.setPosition(dbProblem.getPosition());

			// These we want to modify
			res.setAttachments(problem.getAttachments());
			res.setFinalMode(problem.getFinalMode());
			res.setHint(problem.getHint());
			res.setTitle(problem.getTitle());
			res.setStatement(problem.getStatement());
		}
		this.validator.validate(res, binding);
		return res;
	}

	public void save(final Problem problem) {
		Assert.isTrue(AuthUtils.checkLoggedAuthority("COMPANY"), "Save failed: Logged user is not a company");
		if (problem.getId() != 0) {
			// Problem exists so we must be the owner
			Assert.isTrue(this.checkProblemOwner(problem.getId()), "Save failed: Logged user is not the problem owner");

			// Database problem has to be in draft mode
			Assert.isTrue(this.getDatabaseProblemFinalMode(problem.getId()) == false, "Save failed: Problem is in final mode");
		}
		this.problemRepository.save(problem);
	}

}
