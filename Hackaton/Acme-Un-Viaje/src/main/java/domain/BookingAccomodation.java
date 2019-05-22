
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class BookingAccomodation extends DomainEntity {

	private Date			start, end;

	private Accomodation	accomodation;


	@Past
	public Date getStart() {
		return this.start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

	@Future
	public Date getEnd() {
		return this.end;
	}

	public void setEnd(final Date end) {
		this.end = end;
	}

	@ManyToOne(optional = false)
	public Accomodation getAccomodation() {
		return this.accomodation;
	}

	public void setAccomodation(final Accomodation accomodation) {
		this.accomodation = accomodation;
	}

}
