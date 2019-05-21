
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Transport
 */
@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Transport extends DomainEntity {

	// --- Attributes
	private int			numberOfPlaces;
	private double		price;
	private Date		date;
	private String		vehicleType;
	private String		origin;
	private String		destination;

	// --- Relations
	private Transporter	transporter;


	public Transport() {
	}

	public Transport(final int numberOfPlaces, final double price, final Date date, final String vehicleType) {
		this.numberOfPlaces = numberOfPlaces;
		this.price = price;
		this.date = date;
		this.vehicleType = vehicleType;
	}

	@Min(1)
	public int getNumberOfPlaces() {
		return this.numberOfPlaces;
	}

	public void setNumberOfPlaces(final int numberOfPlaces) {
		this.numberOfPlaces = numberOfPlaces;
	}

	@Min(0)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	@Future
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	@NotEmpty
	public String getVehicleType() {
		return this.vehicleType;
	}

	public void setVehicleType(final String vehicleType) {
		this.vehicleType = vehicleType;
	}

	@NotEmpty
	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(final String origin) {
		this.origin = origin;
	}

	@NotEmpty
	public String getDestination() {
		return this.destination;
	}

	public void setDestination(final String destination) {
		this.destination = destination;
	}

	@ManyToOne(optional = false)
	public Transporter getTransporter() {
		return this.transporter;
	}

	public void setTransporter(final Transporter transporter) {
		this.transporter = transporter;
	}

}
