
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import security.LoginService;
import domain.Administrator;
import domain.Enrolled;
import domain.Position;

/*
 * CONTROL DE CAMBIOS PositionService.java
 * 
 * ALVARO 18/02/2019 09:22 CREACIÓN DE LA CLASE
 */

@Service
public class PositionService {

	//Managed Repository -------------------	
	@Autowired
	private PositionRepository		positionRepository;

	//Supporting services ------------------
	@Autowired
	Validator						validator;
	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private EnrolledService			enrolledService;


	//Simple CRUD Methods ------------------

	public Position create() {

		// "Check that an Admin is creating the new Position"
		final Administrator creatorAdmin = this.adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin);
		final Position position = new Position();
		return position;

	}

	public Collection<Position> findAll() {
		return this.positionRepository.findAll();
	}

	public Position findOne(final int id) {
		Position position = this.positionRepository.findOne(id);
		Assert.notNull(position, "position.null");
		return position;
	}
	public Position save(final Position position) {
		// "Check if the new position already exists"
		final Position posCheck = this.positionRepository.findByNames(position.getNameEs(), position.getNameEn());
		Assert.isTrue(posCheck == null, "name.error");

		// "Check that an Admin is saving the new Area"
		final Administrator creatorAdmin = this.adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "user.error");

		return this.positionRepository.save(position);
	}

	public void delete(final Position position) {

		// "Check that an Admin is deleting the Position"
		final Administrator creatorAdmin = this.adminService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "byAdmin.error");
		// "Check that Position is not used"
		final Collection<Enrolled> e = this.enrolledService.findAllByPositionId(position.getId());
		Assert.isTrue(e.size() == 0, "positionInUse.error");
		this.positionRepository.delete(position);
	}
}
