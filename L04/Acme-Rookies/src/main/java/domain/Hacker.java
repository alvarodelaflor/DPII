
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Access(AccessType.PROPERTY)
public class Hacker extends Actor {

	private Finder	finder;


	@OneToOne(optional = false, cascade = javax.persistence.CascadeType.ALL)
	@Cascade({
		CascadeType.ALL
	})
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@Override
	public String toString() {
		return this.getName() + " " + this.getSurname();
	}

}
