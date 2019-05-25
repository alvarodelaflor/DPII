
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import domain.Accomodation;

public class BookingAccForm {

	private Date			startDate, endDate;
	private int				travelPackId;
	private Accomodation	accomodation;


	public Accomodation getAccomodation() {
		return this.accomodation;
	}

	public void setAccomodation(final Accomodation accomodation) {
		this.accomodation = accomodation;
	}

	public int getTravelPackId() {
		return this.travelPackId;
	}

	public void setTravelPackId(final int travelPackId) {
		this.travelPackId = travelPackId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

}
