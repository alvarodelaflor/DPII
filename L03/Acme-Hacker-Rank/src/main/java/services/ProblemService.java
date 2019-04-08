
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProblemRepository;
import security.LoginService;
import security.UserAccount;
import utilities.AuthUtils;
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

	public void deleteAllByPosition(final int positionId) {
		final Collection<Problem> problems = this.problemRepository.getProblemsByPosition(positionId);
		if (!problems.isEmpty())
			this.problemRepository.deleteInBatch(problems);
	}

	public void deleteCompanyProblems(final int id) {
		final Collection<Problem> problems = this.problemRepository.getProblemsByCompany(id);
		this.problemRepository.deleteInBatch(problems);

	}

}
