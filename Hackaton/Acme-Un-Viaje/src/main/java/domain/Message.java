
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private String				subject;
	private String				body;
	private Date				moment;
	private Collection<Mailbox>	mailboxes;
	private Collection<String>	emailReceiver;
	private Collection<String>		tags;

	@ManyToMany
	@Valid
	public Collection<Mailbox> getMailboxes() {
		return this.mailboxes;
	}

	public void setMailboxes(final Collection<Mailbox> mailboxes) {
		this.mailboxes = mailboxes;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
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

	@ElementCollection(targetClass = String.class)
	public Collection<String> getEmailReceiver() {
		return this.emailReceiver;
	}

	public void setEmailReceiver(final Collection<String> emailReceiver) {
		this.emailReceiver = emailReceiver;
	}
	
	@ElementCollection(targetClass = String.class)
	public Collection<String> getTags() {
		return this.tags;
	}

	public void setTags(final Collection<String> tags) {
		this.tags = tags;
	}

}