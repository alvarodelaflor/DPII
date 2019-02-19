
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Request;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;
	@Autowired
	private MemberService		memberService;


	public Collection<Request> getMemberRequests(final int id) {
		// To list member requests we must be logged as a member
		Assert.isTrue(this.checkAuthority("MEMBER"));
		return this.requestRepository.getMemberRequests(id);
	}

	public Request findOne(final int id) {
		// To display a request we must be logged as a member or brotherhood
		Assert.isTrue(this.checkAuthority("MEMBER") || this.checkAuthority("BROTHERHOOD"));
		return this.requestRepository.findOne(id);
	}

	public Request save(final Request request) {
		Request res;
		if (request.getId() == 0) {
			// Creating, we want to check member authority
			this.checkAuthority("MEMBER");
			res = this.requestRepository.save(request);
		} else {
			// Updating, we want to check brotherhood authority AND ONLY update status and rejection reason
			Assert.isTrue(this.checkAuthority("BROTHERHOOD"));
			final Request req = this.requestRepository.findOne(request.getId());
			req.setStatus(request.getStatus());
			req.setRejectionReason(request.getRejectionReason());
			res = this.requestRepository.save(req);
		}
		return res;
	}

	public void delete(final int id) {
		final Request req = this.requestRepository.findOne(id);
		final int loggedMemberId = this.memberService.getMemberByUserAccountId(LoginService.getPrincipal().getId()).getId();
		// To delete a request we must be the owner and it must be 'PENDING'
		Assert.isTrue(req.getMember().getId() == loggedMemberId);
		Assert.isNull(req.getStatus());
		this.requestRepository.delete(id);
	}
	private boolean checkAuthority(final String authority) {
		final UserAccount acc = LoginService.getPrincipal();
		final Authority member = new Authority();
		member.setAuthority(authority);
		return acc.getAuthorities().contains(member);
	}
}
