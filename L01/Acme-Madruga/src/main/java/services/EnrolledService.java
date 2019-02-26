
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EnrolledRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.Enrolled;

/*
 * CONTROL DE CAMBIOS EnrolledService.java
 * 
 * ALVARO 17/02/2019 11:51 CREACIÓN DE LA CLASE
 */

@Service
@Transactional
public class EnrolledService {

	//Managed Repository -------------------	
	@Autowired
	private EnrolledRepository	enrolledRepository;

	//Supporting services ------------------
	@Autowired
	MemberService				memberService;
	@Autowired
	Validator					validator;
	@Autowired
	BrotherhoodService			brotherhoodService;


	//Simple CRUD Methods ------------------

	public Enrolled create() {

		final Enrolled enrolled = new Enrolled();
		return enrolled;
	}

	public Collection<Enrolled> findAll() {
		return this.enrolledRepository.findAll();
	}

	public Enrolled findOne(final int id) {
		final Enrolled result = this.enrolledRepository.findOne(id);
		return result;
	}

	public Enrolled save(final Enrolled enrolled) {
		return this.enrolledRepository.save(enrolled);
	}

	public void delete(final Enrolled enrolled) {
		Assert.notNull(enrolled, "enrrolled.null");
		this.enrolledRepository.delete(enrolled);
	}

	public Collection<Enrolled> findAllByPositionId(final int positionId) {

		return this.enrolledRepository.findAllByPositionId(positionId);
	}

	public Collection<Enrolled> findAllByBrotherhoodLoggedAccepted() {
		System.out.println("IdLogged:" + LoginService.getPrincipal().getId());
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		return this.enrolledRepository.findAllByBrotherhoodLogged(brotherhood.getId(), true);
	}
	public Collection<Enrolled> findAllByBrotherhoodLoggedRejected() {
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		return this.enrolledRepository.findAllByBrotherhoodLogged(brotherhood.getId(), false);
	}

	public Collection<Enrolled> findAllByBrotherhoodLoggedPending() {
		final Brotherhood brotherhood = this.brotherhoodService.getBrotherhoodByUserAccountId(LoginService.getPrincipal().getId());
		return this.enrolledRepository.findAllByBrotherhoodLoggedPending(brotherhood.getId());
	}

	public Enrolled reconstruct(final Enrolled enrolled, final BindingResult binding) {
		Enrolled result;

		if (enrolled.getId() == 0)
			result = enrolled;
		else {
			result = this.enrolledRepository.findOne(enrolled.getId());
			result.setState(enrolled.getState());
			if (enrolled.getPosition() != null)
				result.setPosition(enrolled.getPosition());
			this.validator.validate(enrolled, binding);
		}
		return result;
	}
}
