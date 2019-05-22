
package forms;

import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Transporter;

public class TransportForm {

	// --- Attributes
	private Date		initDate;
	private Date		endDate;
	private int			numberOfPlaces;
	private double		price;
	private String		vehicleType;
	private String		origin;
	private String		destination;

	// --- Relations
	private Transporter	transporter;


	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	@Future
	public Date getInitDate() {
		return this.initDate;
	}

	public void setInitDate(final Date initDate) {
		this.initDate = initDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	@Future
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@Min(1)
	public int getNumberOfPlaces() {
		return this.numberOfPlaces;
	}

	public void setNumberOfPlaces(final int numberOfPlaces) {
		this.numberOfPlaces = numberOfPlaces;
	}

	@DecimalMin(value = "0.0")
	public double getPrice() {
		return this.price;
	}

	public void setPrice(final double price) {
		this.price = price;
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
