
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import forms.RegisterActor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;

	// FIND ALL NON-BANNED BUT ADMINS
	// ---------------------------------------------------------------
	public Collection<Actor> findAllNonBannedButAdmins() {

		return this.actorRepository.findAllNonBannedButAdmins();
	}

	// FIND ALL BANNED BUT ADMINS
	// ---------------------------------------------------------------
	public Collection<Actor> findAllBannedButAdmins() {

		return this.actorRepository.findAllBannedButAdmins();
	}

	// SAVE ACTOR
	// ---------------------------------------------------------------
	public Actor save(final Actor actor) {

		return this.actorRepository.save(actor);
	}

	// FIND ONE ACTOR
	// ---------------------------------------------------------------
	public Actor findOne(final int id) {

		return this.actorRepository.findONE(id);
	}

	// CHECK REGISTER AS ACTOR
	// ---------------------------------------------------------------
	public void checkActor(final RegisterActor registerActor, final BindingResult binding) {

		if (!registerActor.getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}"))
			binding.rejectValue("expiration", "error.expirationFormatter");

		final Calendar calendar = Calendar.getInstance();
		if (registerActor.getExpiration().matches("([0-9]){2}" + "/" + "([0-9]){2}")) {
			final String[] parts = registerActor.getExpiration().split("/");
			final String part1 = parts[0]; // MM
			final String part2 = parts[1]; // YY

			final int monthRigthNow = calendar.getTime().getMonth();
			final int monthCreditCard = Integer.parseInt(part1);

			int yearRigthNow = calendar.getTime().getYear();
			yearRigthNow = yearRigthNow % 100;
			final int yearCredictCard = Integer.parseInt(part2);

			System.out.println(yearCredictCard >= yearRigthNow);
			System.out.println(monthCreditCard > monthRigthNow);

			if (yearCredictCard < yearRigthNow || monthCreditCard == 00 || monthCreditCard > 12)
				binding.rejectValue("expiration", "error.expirationFuture");

			if (yearCredictCard >= yearRigthNow && monthCreditCard != 00 && monthCreditCard > 12)
				if (yearCredictCard == yearRigthNow)
					if (monthCreditCard < monthRigthNow)
						binding.rejectValue("expiration", "error.expirationFuture");
		}

		if (registerActor.getUserName().length() <= 5 && registerActor.getUserName().length() <= 5)
			binding.rejectValue("userName", "error.userAcount");

		if (this.actorRepository.getActorByUser(registerActor.getUserName()) != null)
			binding.rejectValue("userName", "error.userNameExists");

		if (registerActor.getConfirmPassword().length() <= 5 && registerActor.getPassword().length() <= 5)
			binding.rejectValue("password", "error.password");

		if (!registerActor.getConfirmPassword().equals(registerActor.getPassword()))
			binding.rejectValue("password", "error.password.confirm");

		if (!registerActor.getNumber().matches("([0-9]){16}"))
			binding.rejectValue("number", "error.numberCredictCard");

		if (!registerActor.getCVV().matches("([0-9]){3}"))
			binding.rejectValue("CVV", "error.CVVCredictCard");

		if (registerActor.getHolder() == "")
			binding.rejectValue("holder", "error.holderCredictCard");

		if (registerActor.getMake() == "")
			binding.rejectValue("make", "error.makeCredictCard");

		if (registerActor.getAccept() == false) {
			final ObjectError error = new ObjectError("accept", "You have to accept terms and condictions");
			binding.addError(error);
			binding.rejectValue("accept", "error.termsAndConditions");
		}

		if (registerActor.getHolder().contains(">") || registerActor.getHolder().contains("<"))
			binding.rejectValue("holder", "error.html");

		if (registerActor.getMake().contains(">") || registerActor.getMake().contains("<"))
			binding.rejectValue("make", "error.html");

		if (registerActor.getPassword().contains(">") || registerActor.getPassword().contains("<"))
			binding.rejectValue("password", "error.html");

		if (registerActor.getUserName().contains(">") || registerActor.getUserName().contains("<"))
			binding.rejectValue("userName", "error.html");

		if (this.getActorByEmail(registerActor.getEmail()).size() >= 1)
			binding.rejectValue("email", "error.email");

		if (registerActor.getBirthDate() != null) {
			if (registerActor.getBirthDate().after(calendar.getTime())) {
				binding.rejectValue("birthDate", "error.birthDate");
			}
			calendar.add(Calendar.YEAR, -18);
			if (registerActor.getBirthDate().after(calendar.getTime())) {
				binding.rejectValue("birthDate", "error.birthDateM");
			}

		}
	}

	public Collection<Actor> getActorByEmail(final String email) {
		return this.actorRepository.getActorByEmail(email);
	}

	public Actor getActorByEmail2(final String email) {
		return this.actorRepository.getActorByEmail2(email);
	}

	public Actor getActorByEmailEdit(final String email) {
		final UserAccount user = LoginService.getPrincipal();
		final String emailA = this.actorRepository.findByUserAccountId(user.getId()).getEmail();
		return this.actorRepository.getCompareEmailActor(email, emailA);
	}

	public Collection<Actor> getActoresSameEmail(final String email) {
		return this.actorRepository.getActoresSameEmail(email);
	}

	public Actor getActorByUserId(final Integer id) {
		final Actor a = this.actorRepository.getActorByUserId(id);
		return a;
	}

	public Actor findByUserAccountId(final int id) {
		return this.actorRepository.findByUserAccountId(id);
	}

	public Collection<String> getEmailofActors() {
		return this.actorRepository.getEmailofActors();
	}

	public Actor getActorMailbox(final Integer id) {
		return this.actorRepository.getActorByMailbox(id);
	}

}
