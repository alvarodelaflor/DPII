
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import auxiliar.PositionAux;
import domain.Procession;
import domain.Request;

/*
 * CONTROL DE CAMBIOS PositionService.java
 * 
 * ALVARO 18/02/2019 09:22 CREACIÓN DE LA CLASE
 */

@Transactional
@Service
public class RequestService {

	//Managed Repository -------------------	

	@Autowired
	private RequestRepository	requestRepository;

	//Supporting services ------------------
	@Autowired
	Validator					validator;

	@Autowired
	PositionAuxService			positionAuxService;


	//Simple CRUD Methods ------------------

	public Request create() {

		final Request request = new Request();
		return request;

	}

	public Collection<Request> findAll() {
		return this.requestRepository.findAll();
	}

	public Request findOne(final int id) {
		return this.requestRepository.findOne(id);
	}
	public Request save(final Request request) {
		return this.requestRepository.save(request);
	}

	public void delete(final Request request) {
		this.requestRepository.delete(request);
	}

	public Collection<Request> findAllByProcessionAccepted(final Procession procession) {
		return this.requestRepository.findAllByProcession(procession.getId(), true);
	}
	public Collection<Request> findAllByProcessionRejected(final Procession procession) {
		return this.requestRepository.findAllByProcession(procession.getId(), false);
	}

	public Collection<Request> findAllByProcessionPending(final Procession procession) {
		return this.requestRepository.findAllByProcessionPending(procession.getId());
	}

	public Request reconstruct(final Request request, final BindingResult binding) {
		Request result;

		if (request.getId() == 0)
			result = request;
		else {
			result = this.requestRepository.findOne(request.getId());
			result.setStatus(request.getStatus());
			if (request.getPositionAux() != null && result.getPositionAux() != null && !request.getPositionAux().equals(result.getPositionAux())) {
				final PositionAux positionAux = result.getPositionAux();
				positionAux.setStatus(false);
				this.positionAuxService.save(positionAux);
			}
			result.setPositionAux(request.getPositionAux());
			result.setComment(request.getComment());
		}
		this.validator.validate(request, binding);
		return result;
	}
}
