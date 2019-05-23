
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.PROPERTY)
public class BookingTransport extends DomainEntity {

	private Date		date;

	private Transport	transport;


	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	@ManyToOne(optional = false)
	public Transport getTransport() {
		return this.transport;
	}

	public void setTransport(final Transport transport) {
		this.transport = transport;
	}

}
