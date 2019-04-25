
package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

/*
 * CONTROL DE CAMBIOS Message.java
 * 
 * Antonio Salvat 23/02/2019 19:49 CREACI�N DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity implements Comparable<Message> {

	private String				subject;
	private String				body;
	private Date				moment;
	private Collection<Tag>		tags;
	private String				sender;
	private Collection<String>	recipient;


	@NotBlank
	public String getSender() {
		return this.sender;
	}

	@ElementCollection(targetClass = String.class)
	public Collection<String> getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Collection<String> recipient) {
		this.recipient = recipient;
	}

	public void setSender(final String sender) {
		this.sender = sender;
	}

	public void setTags(final Collection<Tag> tags) {
		this.tags = tags;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Tag.class)
	public Collection<Tag> getTags() {
		return this.tags;
	}

	@NotBlank
	@SafeHtml
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	@SafeHtml
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Override
	public int compareTo(final Message o) {
		final List<Tag> tags1 = new ArrayList<>();
		final List<Tag> tags2 = new ArrayList<>();
		tags1.addAll(o.getTags());
		tags2.addAll(this.tags);
		final Tag tag1 = tags1.get(0);
		final Tag tag2 = tags2.get(0);
		if (tag1.getTag().compareTo(tag2.getTag()) < 0)
			return 1;
		else if (tag1.getTag().compareTo(tag2.getTag()) > 0)
			return -1;
		return 0;
	}

}
