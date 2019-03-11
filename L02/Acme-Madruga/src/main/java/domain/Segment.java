
package domain;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	// If we create a path these attributes can't be null because this is the destination of the previous path.
	// We have to use BigDecimal in order to use @Max and @Min annotations
	private BigDecimal	latitude;
	private BigDecimal	longitude;

	// We'll create linked paths so that the origin of the destination path is the destination of THIS path.
	// If a path has no destination, it means we are in the FINAL path.
	private Segment		destination;


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
	public Segment getDestination() {
		return this.destination;
	}

	public void setDestination(final Segment destination) {
		this.destination = destination;
	}

}
