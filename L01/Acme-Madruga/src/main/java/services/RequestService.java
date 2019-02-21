
package services;

import java.util.Collection;

import javax.transaction.Transactional;

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
import domain.Procession;
import domain.Request;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;
	@Autowired
	private MemberService		memberService;
	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private Validator			validator;


	public Request create(final int processionId) {
		final Request r = new Request();
		final Procession procession = this.processionService.findOne(processionId);
		final Member owner = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());

		// We have to check if the procession is in final mode
		Assert.isTrue(procession.getIsFinal());
		// We have to check if we are an active member of the brotherhood
		Assert.isTrue(this.memberService.isBrotherhoodActiveMember(owner.getId(), procession.getBrotherhood().getId()));

		r.setProcession(procession);
		r.setMember(owner);

		return r;
	}
	public Collection<Request> getLoggedRequests() {
		Assert.isTrue(this.checkAuthority("MEMBER"));
		final Member logged = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId());
		return this.requestRepository.getMemberRequests(logged.getId());
	}

	public Request findOne(final int id) {
		// To display a request we must be logged as a member or brotherhood
		final Request req = this.requestRepository.findOne(id);

		// We are either the brotherhood who owns the procession or the owner of the request
		final boolean processionOwner = req.getProcession().getBrotherhood().getUserAccount().equals(LoginService.getPrincipal());
		final boolean requestOwner = req.getMember().getUserAccount().equals(LoginService.getPrincipal());

		Assert.isTrue(processionOwner || requestOwner);
		return req;
	}

	public Request save(final Request request) {
		Request res;
		if (request.getId() == 0) {
			// Creating, we want to check member authority
			this.checkAuthority("MEMBER");
			res = this.requestRepository.save(request);
		} else {
			final Request req = this.requestRepository.findOne(request.getId());
			// Check if request's procession is owned by the brotherhood
			Assert.isTrue(req.getProcession().getBrotherhood().getUserAccount().equals(LoginService.getPrincipal()));
			// Check if request is "pending"
			Assert.isNull(req.getStatus());
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

	public Request reconstruct(final Request request, final BindingResult binding) {
		Request res;
		if (request.getId() == 0)
			res = request;
		else {
			res = this.requestRepository.findOne(request.getId());
			// What I want to modify
			res.setCol(request.getCol());
			res.setRow(request.getRow());
			res.setRejectionReason(request.getRejectionReason());
			res.setStatus(request.getStatus());

			this.validator.validate(res, binding);
		}
		return res;
	}

	private boolean checkAuthority(final String authority) {
		final UserAccount acc = LoginService.getPrincipal();
		final Authority member = new Authority();
		member.setAuthority(authority);
		return acc.getAuthorities().contains(member);
	}

}
