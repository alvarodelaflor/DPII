
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProblemRepository;
import security.LoginService;
import security.UserAccount;
import domain.Hacker;

@Service
@Transactional
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepository;

	@Autowired
	private HackerService		hackerService;


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

}
