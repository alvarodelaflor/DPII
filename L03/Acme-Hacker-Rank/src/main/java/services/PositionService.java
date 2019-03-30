
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PositionRepository;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;


	// FINDALL ---------------------------------------------------------------
	public Collection<Position> findALL(final int id) {
		return this.positionRepository.findAll();
	}

	// findAllPositionByCompany ---------------------------------------------------------------
	public Collection<Position> findAllPositionByCompany(final int companyId) {
		System.out.println(companyId);
		final Collection<Position> p = this.positionRepository.findAllPositionByCompany(companyId);
		return p;
	}

}
