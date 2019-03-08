
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.PositionAuxRepository;
import auxiliar.PositionAux;

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
	
	public boolean isEmptyPositionAux(int processionId, int row, int column) {
		PositionAux positionAuxCheck = this.positionAuxRepository.findAllPositionAuxByProcessionIdRowAndColum(processionId, row, column);
		return !positionAuxCheck.getStatus();
	}
	
	public Collection<PositionAux> saveAll(Collection<PositionAux> positionsAux) {
		return this.positionAuxRepository.save(positionsAux);
	}	

	public void delete(final PositionAux positionAux) {
		this.positionAuxRepository.delete(positionAux);
	}

	public Collection<PositionAux> findFreePositionByProcesion(final int processionId) {
		return this.positionAuxRepository.findAllPositionAuxFreeByProcessionId(processionId);
	}

	public Collection<PositionAux> findPositionByProcesion(final int processionId) {
		return this.positionAuxRepository.findAllPositionAuxByProcessionId(processionId);
	}

	public void deleteAllPositionByProcession(final int processionId) {
		final Collection<PositionAux> possitionAux = this.findPositionByProcesion(processionId);
		if (!possitionAux.isEmpty())
			for (final PositionAux positionAux : possitionAux)
				this.positionAuxRepository.delete(positionAux);
	}
}
