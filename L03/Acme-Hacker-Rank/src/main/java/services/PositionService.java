
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PositionRepository;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;


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
