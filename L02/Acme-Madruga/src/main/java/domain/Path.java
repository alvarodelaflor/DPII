
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Path extends DomainEntity {

	// If we create a path these attributes can't be null because this is the destination of the previous path.
	// We have to use BigDecimal to use @Max and @Min
	private BigDecimal	latitude;
	private BigDecimal	longitude;

	// We'll create linked paths so that the origin of the destination path is the destination of THIS path.
	// If a path has no destination, it means we are in the FINAL path.
	private Path		destination;


	@NotNull
	@Max(90)
	@Min(-90)
	public BigDecimal getLatitude() {
		return this.latitude;
	}

	public void setLatitude(final BigDecimal latitude) {
		this.latitude = latitude;
	}

	@NotNull
	@Max(180)
	@Min(-180)
	public BigDecimal getLongitude() {
		return this.longitude;
	}

	public void setLongitude(final BigDecimal longitude) {
		this.longitude = longitude;
	}

	@OneToOne(optional = true)
	public Path getDestination() {
		return this.destination;
	}

	public void setDestination(final Path destination) {
		this.destination = destination;
	}

}
