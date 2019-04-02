
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProblemRepository;

@Service
@Transactional
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepository;


	public int getProblemCount(final int positionId) {
		return this.problemRepository.getProblemCount(positionId);
	}

}
