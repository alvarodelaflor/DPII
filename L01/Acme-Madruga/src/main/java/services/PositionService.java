
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import domain.Position;

/*
 * CONTROL DE CAMBIOS PositionService.java
 * 
 * ALVARO 18/02/2019 09:22 CREACIÓN DE LA CLASE
 */

@Service
@Transactional
public class PositionService {

	//Managed Repository -------------------	

	@Autowired
	private PositionRepository	positionRepository;

	//Supporting services ------------------
	@Autowired
	Validator					validator;


	//Simple CRUD Methods ------------------

	public Position create() {

		final Position position = new Position();
		return position;

	}

	public Collection<Position> findAll() {
		return this.positionRepository.findAll();
	}

	public Position findOne(final int id) {
		return this.positionRepository.findOne(id);
	}
	public Position save(final Position position) {
		return this.positionRepository.save(position);
	}

	public void delete(final Position position) {
		this.positionRepository.delete(position);
	}
}
