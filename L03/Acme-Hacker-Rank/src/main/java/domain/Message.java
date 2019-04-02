
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

	private String				subject;
	private String				body;
	private Date				moment;
	private String				tag;
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

	@NotBlank
	//	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTag() {
		return this.tag;
	}

	public void setTag(final String tag) {
		this.tag = tag;
	}

	@NotBlank
	//	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	//	@SafeHtml(whitelistType = WhiteListType.NONE)
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

}
