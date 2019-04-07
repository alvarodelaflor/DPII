
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import security.LoginService;
import utilities.AuthUtils;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private PositionDataService	positionDataService;

	@Autowired
	private ProblemService		problemService;


	// FINDALL ---------------------------------------------------------------
	public Collection<Position> findALL() {
		return this.positionRepository.findAll();
	}

	// findAllPositionByCompany ---------------------------------------------------------------
	public Collection<Position> findAllPositionStatusTrueByCompany(final int companyId) {
		System.out.println(companyId);
		final Collection<Position> p = this.positionRepository.findAllPositionStatusTrueByCompany(companyId);
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

	public Collection<Position> findAllPositionsByLoggedCompany() {
		Assert.isTrue(AuthUtils.checkLoggedAuthority("COMPANY"));
		final int companyId = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId()).getId();
		return this.positionRepository.findAllPositionsByCompany(companyId);
	}

	public void deleteCompanyPositions(final int companyId) {

		final Collection<Position> positions = this.positionRepository.findAllPositionsByCompany(companyId);
		if (!positions.isEmpty())
			for (final Position position : positions) {
				this.problemService.deleteAllByPosition(position.getId());
				this.positionDataService.deleteAllByPosition(position.getId());
				this.positionRepository.delete(position);
			}
	}

}
