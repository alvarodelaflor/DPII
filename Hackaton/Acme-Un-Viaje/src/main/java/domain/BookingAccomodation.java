
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class BookingAccomodation extends DomainEntity {

	private Date			startDate, endDate;

	private Accomodation	accomodation;


	@ManyToOne(optional = false)
	public Accomodation getAccomodation() {
		return this.accomodation;
	}

	public void setAccomodation(final Accomodation accomodation) {
		this.accomodation = accomodation;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

}
