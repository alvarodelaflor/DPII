
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import repositories.PositionAuxRepository;
import auxiliar.PositionAux;

/*
 * CONTROL DE CAMBIOS PositionService.java
 * 
 * ALVARO 18/02/2019 09:22 CREACIÓN DE LA CLASE
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
		final Collection<PositionAux> possitionAux = this.findFreePositionByProcesion(processionId);
		if (!possitionAux.isEmpty())
			for (final PositionAux positionAux : possitionAux)
				this.positionAuxRepository.delete(positionAux);
	}
}
