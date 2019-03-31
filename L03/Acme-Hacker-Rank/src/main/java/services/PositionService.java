
package services;

import java.util.Collection;
import java.util.HashSet;

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
	public Collection<Position> findALL() {
		return this.positionRepository.findAll();
	}

	// findAllPositionByCompany ---------------------------------------------------------------
	public Collection<Position> findAllPositionByCompany(final int companyId) {
		System.out.println(companyId);
		final Collection<Position> p = this.positionRepository.findAllPositionByCompany(companyId);
		return p;
	}

	// findAllPositionByCompany ---------------------------------------------------------------
	public Collection<Position> findAllPositionWithStatusTrue() {
		final Collection<Position> p = this.positionRepository.findAllPositionWithStatusTrue();
		return p;
	}

	// FINDALL ---------------------------------------------------------------
	public Position findOne(final int id) {
		return this.positionRepository.findOne(id);
	}

	// searhPosition ---------------------------------------------------------------
	public Collection<Position> search(final String palabra) {
		final HashSet<Position> p = new HashSet<>();
		p.addAll(this.positionRepository.findWithDescription(palabra));
		p.addAll(this.positionRepository.findWithCompanyName(palabra));
		p.addAll(this.positionRepository.findWitheProfile(palabra));
		p.addAll(this.positionRepository.findWithSkills(palabra));
		p.addAll(this.positionRepository.findWithTitle(palabra));
		p.addAll(this.positionRepository.findWithTechs(palabra));
		System.out.println(p);
		return p;
	}

	// DashBoard:
	public Float avgPositionPerCompany() {

		return this.positionRepository.avgPositionPerCompany();
	}

	public Float minPositionPerCompany() {

		return this.positionRepository.minPositionPerCompany();
	}

	public Float maxPositionPerCompany() {

		return this.positionRepository.maxPositionPerCompany();
	}

	public Float stddevPositionPerCompany() {

		return this.positionRepository.stddevPositionPerCompany();
	}

	public Double avgSalaryPerPosition() {

		return this.positionRepository.avgSalaryPerPosition();
	}

	public Double minSalaryPerPosition() {

		return this.positionRepository.minSalaryPerPosition();
	}

	public Double maxSalaryPerPosition() {

		return this.positionRepository.maxSalaryPerPosition();
	}

	public Double stddevSalaryPerPosition() {

		return this.positionRepository.stddevSalaryPerPosition();
	}

	public String bestPosition() {

		return this.positionRepository.bestPositon();
	}

	public String worstPosition() {

		return this.positionRepository.worstPositon();
	}

}
