
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import security.LoginService;
import utilities.AuthUtils;
import domain.Company;
import domain.Position;

@Service
@Transactional
public class PositionService {

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private Validator			validator;


	public Position create() {
		final Position res = new Position();
		return res;
	}
	// FINDALL ---------------------------------------------------------------
	public Collection<Position> findALL() {
		return this.positionRepository.findAll();
	}

	// findAllPositionStatusTrueCancelFalseByCompany ---------------------------------------------------------------
	public Collection<Position> findAllPositionStatusTrueCancelFalseByCompany(final int companyId) {
		System.out.println(companyId);
		final Collection<Position> p = this.positionRepository.findAllPositionStatusTrueCancelFalseByCompany(companyId);
		return p;
	}

	// findAllPositionWithStatusTrueCancelFalse ---------------------------------------------------------------
	public Collection<Position> findAllPositionWithStatusTrueCancelFalse() {
		final Collection<Position> p = this.positionRepository.findAllPositionWithStatusTrueCancelFalse();
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

	public Position reconstruct(final Position position, final BindingResult binding) {
		Position res = this.create();
		if (position.getId() == 0) {
			res = position;
			final Company owner = this.companyService.getCompanyByUserAccountId(LoginService.getPrincipal().getId());

			res.setCompany(owner);
			res.setStatus(false);
			res.setCancel(false);
			res.setTicker(this.getTickerForCompany(owner));
		} else {
			final Position dbPosition = this.positionRepository.findOne(position.getId());
			// These we recover from db
			res.setId(dbPosition.getId());
			res.setVersion(dbPosition.getVersion());
			res.setCompany(dbPosition.getCompany());
			res.setTicker(dbPosition.getTicker());

			// These we want to modify
			res.setDeadline(position.getDeadline());
			res.setDescription(position.getDescription());
			res.setProfile(position.getProfile());
			res.setSalary(position.getSalary());
			res.setSkills(position.getSkills());
			res.setStatus(position.getStatus());
			res.setTechs(position.getTechs());
			res.setTitle(position.getTitle());
		}
		this.validator.validate(res, binding);
		return res;
	}

	private String getTickerForCompany(final Company owner) {
		String ticker = "";
		final String croppedName = owner.getCommercialName().substring(0, 4).toUpperCase();

		int validTicker = 1;

		while (validTicker != 0) {
			validTicker = 0;
			ticker = croppedName + "-" + this.generateRandomNumber();
			validTicker += this.positionRepository.countByTicker(ticker);
		}
		return ticker;
	}

	private String generateRandomNumber() {
		String res = "";
		final Random random = new Random();
		for (int i = 0; i < 4; i++)
			res += random.nextInt(10);
		return res;
	}

	public void save(final Position pos) {
		Assert.isTrue(AuthUtils.checkLoggedAuthority("COMPANY"), "Logged user is not a company");
		if (pos.getId() != 0) {
			// Position exists so we must be the owner
			Assert.isTrue(this.checkPositionOwner(pos.getId()), "Logged user is not the position owner");

			// Database position has to be in draft mode
			Assert.isTrue(this.getPositionDatabaseStatus(pos.getId()) == false, "Position is in final mode");

			// In case we are setting this as final, we have to have at least 2 problems
			if (pos.getStatus()) {
				final int problemCount = this.problemService.getProblemCount(pos.getId());
				Assert.isTrue(problemCount >= 2, "Position can't be setted to final mode because it has less than 2 problems");
			}
		}
		this.positionRepository.save(pos);
	}

	private boolean getPositionDatabaseStatus(final int positionId) {
		final Position dbPosition = this.positionRepository.findOne(positionId);
		// Database position has to be in draft mode
		return dbPosition.getStatus();
	}

	public String findCompanyWithMorePositions() {

		final List<String> ls = this.positionRepository.findCompanyWithMorePositions();
		String res = "";
		if (!ls.isEmpty())
			res = ls.get(0);
		return res;
	}

	public void cancel(final int positionId) {
		// We must be the owner
		Assert.isTrue(this.checkPositionOwner(positionId), "Logged user is not the position owner");
		final Position dbPosition = this.positionRepository.findOne(positionId);
		// We can cancel a position if it is in final mode
		Assert.isTrue(dbPosition.getStatus(), "Only positions in final mode can be cancelled");
		dbPosition.setCancel(true);
	}

	public void delete(final int positionId) {
		// We must be the owner
		Assert.isTrue(this.checkPositionOwner(positionId), "Logged user is not the position owner");
		// We can delete a position if it is not in final mode
		Assert.isTrue(this.getPositionDatabaseStatus(positionId) == false, "Position is not in draft mode");
		this.positionRepository.delete(positionId);
	}
}
