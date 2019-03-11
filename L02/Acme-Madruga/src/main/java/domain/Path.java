
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Path extends DomainEntity {

	private String	origin;
	// We'll create linked paths so that the origin of the destination path is the destination of THIS path.
	// If a path has no destination, it means we are in the FINAL path.
	private Path	destination;


	// If we create a path we must have an origin
	@NotBlank
	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(final String origin) {
		this.origin = origin;
	}

	@OneToOne(optional = true)
	public Path getDestination() {
		return this.destination;
	}

	public void setDestination(final Path destination) {
		this.destination = destination;
	}

}
