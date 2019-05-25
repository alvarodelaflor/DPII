
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import domain.Transport;

public class BookingTransportForm {

	private Date		date;
	private int			travelPackId;
	private Transport	transport;


	public Transport getTransport() {
		return this.transport;
	}

	public void setTransport(final Transport transport) {
		this.transport = transport;
	}

	public int getTravelPackId() {
		return this.travelPackId;
	}

	public void setTravelPackId(final int travelPackId) {
		this.travelPackId = travelPackId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

}
