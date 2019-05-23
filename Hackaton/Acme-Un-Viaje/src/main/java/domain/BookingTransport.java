
package domain;

import java.util.Date;

/*
 * Curricula.java
 *
 * author: Alvaro de la Flor Bonilla GitHub: alvar017
 *
 * CONTROL:
 * 30/03/2019 14:28 Creation
 */

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class BookingTransport extends DomainEntity {

	private Date		startDate;
	private Date		endDate;
	private Transport	transport;


	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}
	@ManyToOne(optional = false)
	public Transport getTransport() {
		return this.transport;
	}

	public void setTransport(final Transport transport) {
		this.transport = transport;
	}

}
