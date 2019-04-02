
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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

	// FINDONE ---------------------------------------------------------------
	public Position findOne(final int id) {
		return this.positionRepository.findOne(id);
	}

	// searchPosition ---------------------------------------------------------------
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

		final List<String> ls = this.positionRepository.bestPositon();
		String res = "";
		if (!ls.isEmpty())
			res = ls.get(0);
		return res;
	}

	public String worstPosition() {

		final List<String> ls = this.positionRepository.worstPositon();
		String res = "";
		if (!ls.isEmpty())
			res = ls.get(0);
		return res;
	}

	public Collection<Position> findAllPositionsByLoggedCompany() {
		Assert.isTrue(AuthUtils.checkLoggedAuthority("COMPANY"));
		final int companyId = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId()).getId();
		return this.positionRepository.findAllPositionsByCompany(companyId);
	}

	public Position findOneLoggedIsOwner(final int positionId) {
		Assert.isTrue(this.checkPositionOwner(positionId), "Logged company is not the owner of position " + positionId);
		return this.positionRepository.findOne(positionId);
	}
	private boolean checkPositionOwner(final int positionId) {
		final int loggedId = LoginService.getPrincipal().getId();
		final int ownerId = this.positionRepository.findOne(positionId).getCompany().getUserAccount().getId();
		return loggedId == ownerId;
	}

	public String findCompanyWithMorePositions() {

		final List<String> ls = this.positionRepository.findCompanyWithMorePositions();
		String res = "";
		if (!ls.isEmpty())
			res = ls.get(0);
		return res;
	}
}
