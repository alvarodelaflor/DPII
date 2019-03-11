
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
import domain.Member;
import domain.Parade;
import domain.PositionAux;
import domain.Request;

/*
 * CONTROL DE CAMBIOS PositionService.java
 * 
 * ALVARO 18/02/2019 09:22 CREACIï¿½N DE LA CLASE
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
	private ParadeService		paradeService;
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
		Assert.isTrue(request.getPositionAux().getParade().getBrotherhood().getUserAccount().getId() == LoginService.getPrincipal().getId());
		final PositionAux positionAux = request.getPositionAux();
		if (positionAux != null) {
			positionAux.setStatus(false);
			this.positionAuxService.save(positionAux);
		}
		this.requestRepository.delete(request);
	}

	public Collection<Request> findAllByParadeAccepted(final Parade parade) {
		return this.requestRepository.findAllByParade(parade.getId(), true);
	}
	public Collection<Request> findAllByParadeRejected(final Parade parade) {
		return this.requestRepository.findAllByParade(parade.getId(), false);
	}

	public Collection<Request> findAllByParadePending(final Parade parade) {
		return this.requestRepository.findAllByParadePending(parade.getId());
	}

	public Request reconstruct(final Request request, final BindingResult binding) {
		Request result;

		if (request.getId() == 0)
			result = request;
		else {
			result = this.requestRepository.findOne(request.getId());
			final Boolean cacheStatus = result.getStatus();
			result.setStatus(request.getStatus());
			if (this.positionAuxService.findOne(request.getPositionAux().getId()).getStatus().equals(true) && cacheStatus == null)
				result.setStatus(null);
			else if (request.getPositionAux() != null && result.getPositionAux() != null && !request.getPositionAux().equals(result.getPositionAux())) {
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

	public Boolean validMemberToCreateRequest(final int idParade) {
		Boolean res = true;
		final int idMember = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId()).getId();
		System.out.println("Request pendientes: " + this.requestRepository.findAllByMemberParadePending(idParade, idMember));
		System.out.println("Request Aceptadas: " + this.requestRepository.findAllByMemberParadeAccepted(idParade, idMember));
		if (this.requestRepository.findAllByMemberParadePending(idParade, idMember).size() > 0 || this.requestRepository.findAllByMemberParadeAccepted(idParade, idMember).size() > 0)
			res = false;
		return res;
	}

	public Request create(final int paradeId) {

		Assert.isTrue(this.validMemberToCreateRequest(paradeId), "request.notValidMember");
		final Request r = new Request();
		final Parade parade = this.paradeService.findOne(paradeId);
		final Member owner = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		final Collection<PositionAux> positionAuxs = this.positionAuxService.findFreePositionByProcesion(paradeId);
		if (!positionAuxs.isEmpty())
			r.setPositionAux(positionAuxs.iterator().next());
		// We have to check if the parade is in final mode
		Assert.isTrue(parade.getIsFinal());
		// We have to check if we are an active member of the brotherhood
		Assert.isTrue(this.memberService.isBrotherhoodActiveMember(owner.getId(), parade.getBrotherhood().getId()));

		//		r.setParade(parade);
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
		// We are either the brotherhood who owns the parade or the owner of the request
		if (req != null) {
			final boolean paradeOwner = req.getPositionAux().getParade().getBrotherhood().getUserAccount().equals(LoginService.getPrincipal());
			final boolean requestOwner = req.getMember().getUserAccount().equals(LoginService.getPrincipal());
			Assert.isTrue(paradeOwner || requestOwner);
		}
		return req;
	}
	public Request save(final Request request) {
		Request res;

		System.out.println("Comprobación cambio del status");
		System.out.println(request.getStatus());

		if (request.getId() == 0) {
			// Creating, we want to check member authority
			Assert.isTrue(this.checkAuthority("MEMBER"));
			res = this.requestRepository.save(request);
		} else {
			final Request req = this.requestRepository.findOne(request.getId());
			// Check if request's parade is owned by the brotherhood
			Assert.isTrue(req.getPositionAux().getParade().getBrotherhood().getUserAccount().equals(LoginService.getPrincipal()));
			res = this.requestRepository.save(req);
		}
		return res;
	}
	public void delete(final int id) {
		final Request req = this.requestRepository.findOne(id);
		// To delete a request we must be the owner and it must be 'PENDING'
		Assert.isTrue(req.getMember().getUserAccount().equals(LoginService.getPrincipal()));
		Assert.isNull(req.getStatus());
		System.out.println("RequestService.java -> delete");
		final PositionAux positionAux = req.getPositionAux();
		if (positionAux != null) {
			positionAux.setStatus(false);
			this.positionAuxService.save(positionAux);
		}

		this.requestRepository.delete(id);
	}

	private boolean checkAuthority(final String authority) {
		final UserAccount acc = LoginService.getPrincipal();
		final Authority member = new Authority();
		member.setAuthority(authority);
		return acc.getAuthorities().contains(member);
	}

	public Collection<Request> findRequestByParadeId(final int paradeId) {
		return this.requestRepository.findAllByParadeByParade(paradeId);
	}

	public void deleteAllRequestByParade(final int paradeId) {
		final Collection<Request> requests = this.findRequestByParadeId(paradeId);
		if (!requests.isEmpty())
			for (final Request request : requests) {
				final PositionAux positionAux = request.getPositionAux();
				positionAux.setStatus(false);
				this.positionAuxService.save(positionAux);
				this.delete(request);
			}
	}

	public Collection<Request> findAllByMemberAndStatusPending(final Member member) {
		Assert.notNull(member, "request.member.isNull");
		return this.requestRepository.findAllByMemberAndStatusPending(member.getId());
	}

	public Collection<Request> findAllByMemberAndStatusAccepted(final Member member) {
		Assert.notNull(member, "request.member.isNull");
		return this.requestRepository.findAllByMemberAndStatusAccepted(member.getId());
	}

	public void deleteAllRequestPendingByMember(final Member member) {
		final Collection<Request> requests = this.findAllByMemberAndStatusPending(member);
		if (!requests.isEmpty())
			for (final Request request : requests)
				this.requestRepository.delete(request);
	}

	public void deleteAllRequestAcceptedByMember(final Member member) {
		final Collection<Request> requests = this.findAllByMemberAndStatusAccepted(member);
		if (!requests.isEmpty())
			for (final Request request : requests) {
				final PositionAux positionAux = request.getPositionAux();
				positionAux.setStatus(false);
				this.positionAuxService.save(positionAux);
				this.requestRepository.delete(request);
			}
	}

	public Double getRatioRequestStatusTrue() {
		return this.requestRepository.getRatioRequestStatusTrue();
	}

	public Double getRatioRequestStatusFalse() {
		return this.requestRepository.getRatioRequestStatusFalse();
	}

	public Double getRatioRequestStatusNull() {
		return this.requestRepository.getRatioRequestStatusNull();
	}

	public Double getRatioRequestParadeStatusTrue() {
		return this.requestRepository.getRatioRequestParadeStatusTrue();
	}

	public Double getRatioRequestParadeStatusFalse() {
		return this.requestRepository.getRatioRequestParadeStatusFalse();
	}

	public Double getRatioRequestParadeStatusNull() {
		return this.requestRepository.getRatioRequestParadeStatusNull();
	}
}
