
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import repositories.PositionAuxRepository;
import domain.PositionAux;

/*
 * CONTROL DE CAMBIOS PositionService.java
 * 
 * ALVARO 18/02/2019 09:22 CREACI�N DE LA CLASE
 */

@Service
public class PositionAuxService {

	//Managed Repository -------------------	

	@Autowired
	private PositionAuxRepository	positionAuxRepository;

	//Supporting services ------------------
	@Autowired
	Validator						validator;


	//Simple CRUD Methods ------------------

	public PositionAux create() {

		final PositionAux positionAux = new PositionAux();
		return positionAux;

	}

	public Collection<PositionAux> findAll() {
		return this.positionAuxRepository.findAll();
	}

	public PositionAux findOne(final int id) {
		return this.positionAuxRepository.findOne(id);
	}
	public PositionAux save(final PositionAux positionAux) {
		return this.positionAuxRepository.save(positionAux);
	}

	public boolean isEmptyPositionAux(final int paradeId, final int row, final int column) {
		final PositionAux positionAuxCheck = this.positionAuxRepository.findAllPositionAuxByParadeIdRowAndColum(paradeId, row, column);
		return !positionAuxCheck.getStatus();
	}

	public Collection<PositionAux> saveAll(final Collection<PositionAux> positionsAux) {
		return this.positionAuxRepository.save(positionsAux);
	}

	public void delete(final PositionAux positionAux) {
		this.positionAuxRepository.delete(positionAux);
	}

	public Collection<PositionAux> findFreePositionByParade(final int paradeId) {
		return this.positionAuxRepository.findAllPositionAuxFreeByParadeId(paradeId);
	}

	public Collection<PositionAux> findPositionByParade(final int paradeId) {
		return this.positionAuxRepository.findAllPositionAuxByParadeId(paradeId);
	}

	public void deleteAllPositionByParade(final int paradeId) {
		final Collection<PositionAux> possitionAux = this.findPositionByParade(paradeId);
		if (!possitionAux.isEmpty())
			for (final PositionAux positionAux : possitionAux)
				this.positionAuxRepository.delete(positionAux);
	}
}
