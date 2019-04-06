
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository	actorRepository;


	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int id) {
		final Actor actor = this.actorRepository.findOne(id);
		Assert.notNull(actor);
		return actor;
	}

	public Actor save(final Actor actor) {
		return this.actorRepository.save(actor);
	}

	public Actor getActorByUser(final String userName) {
		return this.actorRepository.getActorByUser(userName);
	}

	public Actor getActorByUserId(final Integer id) {
		final Actor a = this.actorRepository.getActorByUserId(id);
		return a;
	}

	public Collection<String> getEmailOfActors() {
		return this.actorRepository.getEmailofActors();
	}

	public Collection<Actor> getActorsThatContainsAMessage(final int messageId) {
		return this.actorRepository.getActorsThatContainsAMessage(messageId);
	}

	// QUERYS - REGISTRO USUARIO
	public Actor getActorByEmailE(final String email) {
		final UserAccount user = LoginService.getPrincipal();
		final String emailA = this.actorRepository.findByUserAccountId(user.getId()).getEmail();
		return this.actorRepository.getActorByEmail(email, emailA);
	}

	public Collection<Actor> getActorByEmail(final String email) {
		return this.actorRepository.getActorByEmail(email);
	}

	public Actor getActorByEmailOnly(final String email) {
		return this.actorRepository.getActorByEmailOnly(email);
	}
	// QUERYS - REGISTRO USUARIO

	// Ban/Unban ------------------------------------------------------------------------------------
	public Actor banByActorId(final Actor actor) {

		// "Check that an Admin is logged"
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth), "user.logged.error");

		// Check for Spammer flag
		Assert.isTrue(actor.getUserAccount().getSpammerFlag() == true || actor.getUserAccount().getPolarity() < 0, "ban.error");

		actor.getUserAccount().setBanned(true);
		return this.actorRepository.save(actor);
	}
	public Actor unbanByActorId(final Actor actor) {

		// "Check that an Admin is logged"
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth), "user.logged.error");

		actor.getUserAccount().setBanned(false);
		return this.actorRepository.save(actor);
	}
}
