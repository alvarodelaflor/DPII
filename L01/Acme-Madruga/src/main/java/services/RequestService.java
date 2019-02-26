
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import auxiliar.PositionAux;
import domain.Member;
import domain.Procession;
import domain.Request;

/*
 * CONTROL DE CAMBIOS PositionService.java
 * 
 * ALVARO 18/02/2019 09:22 CREACI�N DE LA CLASE
 */

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

	@Autowired
	private ProcessionService	processionService;
	@Autowired
	private MemberService		memberService;


	//Simple CRUD Methods ------------------

	public Request create() {

		final Request request = new Request();
		return request;

	}

	public Collection<Request> findAll() {
		return this.requestRepository.findAll();
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

	public Boolean validMemberToCreateRequest(final int idProcession) {
		Boolean res = true;
		final int idMember = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId()).getId();
		if (!this.requestRepository.findAllByMemberProcessionPending(idMember, idProcession).isEmpty() || !this.requestRepository.findAllByMemberProcessionAccepted(idMember, idProcession).isEmpty())
			res = false;
		return res;
	}

	public Request create(final int processionId) {

		Assert.isTrue(this.validMemberToCreateRequest(processionId), "request.notValidMember");
		final Request r = new Request();
		final Procession procession = this.processionService.findOne(processionId);
		final Member owner = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<PositionAux> positionAuxs = this.positionAuxService.findFreePositionByProcesion(processionId);
		if (!positionAuxs.isEmpty())
			r.setPositionAux(positionAuxs.iterator().next());
		// We have to check if the procession is in final mode
		Assert.isTrue(procession.getIsFinal());
		// We have to check if we are an active member of the brotherhood
		Assert.isTrue(this.memberService.isBrotherhoodActiveMember(owner.getId(), procession.getBrotherhood().getId()));

		//		r.setProcession(procession);
		r.setMember(owner);

		return r;
	}

	public Collection<Request> getLoggedRequests() {
		Assert.isTrue(this.checkAuthority("MEMBER"));
		final Member logged = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		return this.requestRepository.getMemberRequests(logged.getId());
	}

	public Request findOne(final int id) {
		final Request req = this.requestRepository.findOne(id);
		// We are either the brotherhood who owns the procession or the owner of the request
		if (req != null) {
			final boolean processionOwner = req.getPositionAux().getProcession().getBrotherhood().getUserAccount().equals(LoginService.getPrincipal());
			final boolean requestOwner = req.getMember().getUserAccount().equals(LoginService.getPrincipal());
			Assert.isTrue(processionOwner || requestOwner);
		}
		return req;
	}
	public Request save(final Request request) {
		Request res;
		if (request.getId() == 0) {
			// Creating, we want to check member authority
			Assert.isTrue(this.checkAuthority("MEMBER"));
			res = this.requestRepository.save(request);
		} else {
			final Request req = this.requestRepository.findOne(request.getId());
			// Check if request's procession is owned by the brotherhood
			Assert.isTrue(req.getPositionAux().getProcession().getBrotherhood().getUserAccount().equals(LoginService.getPrincipal()));
			res = this.requestRepository.save(req);
		}
		return res;
	}
	public void delete(final int id) {
		final Request req = this.requestRepository.findOne(id);
		// To delete a request we must be the owner and it must be 'PENDING'
		Assert.isTrue(req.getMember().getUserAccount().equals(LoginService.getPrincipal()));
		Assert.isNull(req.getStatus());
		this.requestRepository.delete(id);
	}

	private boolean checkAuthority(final String authority) {
		final UserAccount acc = LoginService.getPrincipal();
		final Authority member = new Authority();
		member.setAuthority(authority);
		return acc.getAuthorities().contains(member);
	}

	public Collection<Request> findRequestByProcessionId(final int processionId) {
		return this.requestRepository.findAllByProcessionByProcession(processionId);
	}

	public void deleteAllRequestByProcession(final int processionId) {
		final Collection<Request> requests = this.findRequestByProcessionId(processionId);
		if (!requests.isEmpty())
			for (final Request request : requests)
				this.delete(request);
	}

	public Collection<Request> findAllByMemberAndStatusPending(final Member member) {
		Assert.notNull(member, "request.member.isNull");
		return this.requestRepository.findAllByMemberAndStatusPending(member.getId());
	}

	public void deleteAllRequestPendingByMember(final Member member) {
		final Collection<Request> requests = this.findAllByMemberAndStatusPending(member);
		if (!requests.isEmpty())
			for (final Request request : requests)
				this.requestRepository.delete(request);
	}
}
