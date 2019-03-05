
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private AdministratorService	administratorService;


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

	public Actor getActorByEmail(final String email) {
		return this.actorRepository.getActorByEmail(email);
	}

	public Actor getActorByEmailE(final String email) {
		final UserAccount user = LoginService.getPrincipal();
		final String emailA = this.actorRepository.findByUserAccountId(user.getId()).getEmail();
		return this.actorRepository.getActorByEmail(email, emailA);
	}

	public Object getActorByUser(final String userName) {
		return this.actorRepository.getActorByUser(userName);
	}

	public Actor findByUserAccountId(final int userAccountId) {

		return this.actorRepository.findByUserAccountId(userAccountId);
	}

	public Actor banByActorId(final Actor actor) {

		// "Check that an Admin is logged"
		final Administrator creatorAdmin = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "user.logged.error");

		actor.getUserAccount().setBanned(true);
		return this.actorRepository.save(actor);
	}

	public Actor unbanByActorId(final Actor actor) {

		// "Check that an Admin is logged"
		final Administrator creatorAdmin = this.administratorService.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(creatorAdmin, "user.logged.error");

		Assert.isTrue(actor.getUserAccount().getSpammerFlag() != false, "ban.error");

		actor.getUserAccount().setBanned(false);
		return this.actorRepository.save(actor);
	}
	public Actor getActorByUserId(final Integer id) {
		final Actor a = this.actorRepository.getActorByUserId(id);
		return a;
	}

	public Collection<String> getEmailofActors() {
		return this.actorRepository.getEmailofActors();
	}

	public Actor getActorMessageBox(final Integer id) {
		return this.actorRepository.getActorByMessageBox(id);
	}

}
