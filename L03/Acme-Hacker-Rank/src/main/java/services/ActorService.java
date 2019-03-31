
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository	actorRepository;


	// QUERYS - REGISTRO USUARIO
	public Actor getActorByEmailE(final String email) {
		final UserAccount user = LoginService.getPrincipal();
		final String emailA = this.actorRepository.findByUserAccountId(user.getId()).getEmail();
		return this.actorRepository.getActorByEmail(email, emailA);
	}

	public Collection<Actor> getActorByEmail(final String email) {
		return this.actorRepository.getActorByEmail(email);
	}
	// QUERYS - REGISTRO USUARIO

	public Actor getActorByUser(final String userName) {
		return this.actorRepository.getActorByUser(userName);
	}
}
