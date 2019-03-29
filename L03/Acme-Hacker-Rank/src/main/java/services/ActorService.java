
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActorRepository;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository	actorRepository;

}
