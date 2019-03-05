
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

/*
 * CONTROL DE CAMBIOS Message.java
 * 
 * Antonio Salvat 23/02/2019 19:49 CREACIÓN DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private String					subject;
	private String					body;
	private Date					moment;
	private Priority				priority;
	private Collection<MessageBox>	messageBoxes;
	private Collection<String>		emailReceiver;


	@ManyToMany
	@Valid
	public Collection<MessageBox> getMessageBoxes() {
		return this.messageBoxes;
	}

	public void setMessageBoxes(final Collection<MessageBox> messageBoxes) {
		this.messageBoxes = messageBoxes;
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

	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(final Priority priority) {
		this.priority = priority;
	}

	@ElementCollection(targetClass = String.class)
	public Collection<String> getEmailReceiver() {
		return this.emailReceiver;
	}

	public void setEmailReceiver(final Collection<String> emailReceiver) {
		this.emailReceiver = emailReceiver;
	}

}
