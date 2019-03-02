
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/*
 * CONTROL DE CAMBIOS Member.java
 * 
 * ALVARO 17/02/2019 19:55 CREACIÓN DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
public class Member extends Actor {

	private Collection<Enrolled>	enrolleds;


	//	private Position				position;
	//
	//
	//	public Position getPosition() {
	//		return this.position;
	//	}
	//
	//	public void setPosition(final Position position) {
	//		this.position = position;
	//	}

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	public Collection<Enrolled> getEnrolleds() {
		return this.enrolleds;
	}

	public void setEnrolleds(final Collection<Enrolled> enrolleds) {
		this.enrolleds = enrolleds;
	}

}
