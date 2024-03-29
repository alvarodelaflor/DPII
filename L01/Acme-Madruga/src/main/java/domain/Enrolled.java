
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/*
 * CONTROL DE CAMBIOS Member.java
 * 
 * ALVARO 17/02/2019 19:57 CREACI�N DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(indexes = {
	@Index(columnList = "dropMoment, state")
})
public class Enrolled extends DomainEntity {

	@JsonIgnore
	private Member		member;
	private Boolean		state;
	@JsonIgnore
	private Brotherhood	brotherhood;
	private Position	position;
	private Date		createMoment;
	private Date		dropMoment;


	@OneToOne
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	@ManyToOne(optional = false)
	public Member getMember() {
		return this.member;
	}

	public void setMember(final Member member) {
		this.member = member;
	}

	public Boolean getState() {
		return this.state;
	}

	public void setState(final Boolean state) {
		this.state = state;
	}

	@ManyToOne(optional = true)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	public Date getDropMoment() {
		return this.dropMoment;
	}

	public void setDropMoment(final Date dropMoment) {
		this.dropMoment = dropMoment;
	}

	public Date getCreateMoment() {
		return this.createMoment;
	}

	public void setCreateMoment(final Date createMoment) {
		this.createMoment = createMoment;
	}

}
