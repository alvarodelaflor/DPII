
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.TagRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Tag;

@Service
@Transactional
public class TagService {

	@Autowired
	private TagRepository	tagRepository;

	@Autowired
	private ActorService	actorService;


	public Tag create() {
		final Tag tag = new Tag();
		return tag;
	}

	public Tag save(final Tag tag) {
		final Tag saved = this.tagRepository.save(tag);
		return saved;
	}
	public Collection<Tag> findAll() {
		return this.tagRepository.findAll();
	}

	public Tag findOne(final Integer id) {
		return this.tagRepository.findOne(id);
	}

	public void delete(final Tag tag) {
		this.tagRepository.delete(tag);
	}

	public void flush() {
		this.tagRepository.flush();
	}

	public Collection<Tag> getTagByMessage(final int messageId) {
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(user.getId());
		return this.tagRepository.getTagByMessage(a.getId(), messageId);
	}
	
	public Tag getTagByMessageDeleted(final int messageId) {
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserId(user.getId());
		return this.tagRepository.getTagByMessageDelete(a.getId(), messageId);
	}
}