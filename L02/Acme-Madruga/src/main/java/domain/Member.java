
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/*
 * CONTROL DE CAMBIOS Member.java
 * 
 * ALVARO 17/02/2019 19:55 CREACI�N DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
public class Member extends Actor {

	private Collection<Enrolled>	enrolleds;

	private Finder					finder;


	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	public Collection<Enrolled> getEnrolleds() {
		return this.enrolleds;
	}

	public void setEnrolleds(final Collection<Enrolled> enrolleds) {
		this.enrolleds = enrolleds;
	}

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

}
