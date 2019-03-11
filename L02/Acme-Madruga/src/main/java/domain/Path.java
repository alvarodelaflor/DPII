
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Path extends DomainEntity {

	private Parade	parade;
	private Segment	origin;


	@ManyToOne(optional = false)
	public Parade getParade() {
		return this.parade;
	}

	public void setParade(final Parade parade) {
		this.parade = parade;
	}

	@OneToOne(optional = false)
	public Segment getOrigin() {
		return this.origin;
	}

	public void setOrigin(final Segment origin) {
		this.origin = origin;
	}

}
