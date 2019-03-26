
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/*
 * CONTROL DE CAMBIOS Position.java
 * 
 * ALVARO 18/02/2019 09:00 CREACI�N DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Request extends DomainEntity {

	private Boolean		status;
	private Member		member;
	private PositionAux	positionAux;
	private String		comment;


	@OneToOne(optional = true)
	public PositionAux getPositionAux() {
		return this.positionAux;
	}

	public void setPositionAux(final PositionAux positionAux) {
		this.positionAux = positionAux;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(final Boolean status) {
		this.status = status;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	public Member getMember() {
		return this.member;
	}

	public void setMember(final Member member) {
		this.member = member;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}
}
