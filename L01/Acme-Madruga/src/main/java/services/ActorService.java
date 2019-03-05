
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
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

	public Actor getActorByEmail(final String email) {
		return this.actorRepository.getActorByEmail(email);
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
