
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	Date		creationMoment, applyMoment;
	String		response, link, status;

	Hacker		hacker;
	Position	position;


	@ManyToOne(optional = false)
	public Hacker getHacker() {
		return this.hacker;
	}

	public void setHacker(final Hacker hacker) {
		this.hacker = hacker;
	}

	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	public Date getCreationMoment() {
		return this.creationMoment;
	}

	public void setCreationMoment(final Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	public Date getApplyMoment() {
		return this.applyMoment;
	}

	public void setApplyMoment(final Date applyMoment) {
		this.applyMoment = applyMoment;
	}

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getResponse() {
		return this.response;
	}

	public void setResponse(final String response) {
		this.response = response;
	}

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLink() {
		return this.link;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	@Pattern(regexp = "(PENDING|SUBMITTED|ACCEPTED|REJECTED)")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
}
