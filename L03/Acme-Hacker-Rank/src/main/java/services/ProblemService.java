
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProblemRepository;
import domain.Problem;

@Service
@Transactional
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepo;


	public void deleteAllByPosition(final int positionId) {
		final Collection<Problem> problems = this.problemRepo.getProblemsByPosition(positionId);
		if (!problems.isEmpty())
			this.problemRepo.deleteInBatch(problems);
	}

	public void deleteCompanyProblems(final int id) {
		final Collection<Problem> problems = this.problemRepo.getProblemsByCompany(id);
		this.problemRepo.deleteInBatch(problems);

	}

}
