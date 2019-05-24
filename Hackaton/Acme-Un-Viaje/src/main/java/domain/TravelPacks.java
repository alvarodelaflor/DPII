
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.PROPERTY)
public class TravelPacks extends DomainEntity {

	// Double price es derivada;

	private TravelAgency		travelAgency;
	private Customer			customierda;
	private Complaint			complaint;
	private BookingTransport	bookingTransport;
	private BookingAccomodation	bookingAcc;


	@ManyToOne(optional = false)
	public TravelAgency getTravelAgency() {
		return this.travelAgency;
	}

	public void setTravelAgency(final TravelAgency travelAgency) {
		this.travelAgency = travelAgency;
	}

	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customierda;
	}

	public void setCustomer(final Customer customierda) {
		this.customierda = customierda;
	}

	@ManyToOne(optional = true)
	public Complaint getComplaint() {
		return this.complaint;
	}

	public void setComplaint(final Complaint complaint) {
		this.complaint = complaint;
	}

	@ManyToOne(optional = false)
	public BookingTransport getBookingTransport() {
		return this.bookingTransport;
	}

	public void setBookingTransport(final BookingTransport bookingTransport) {
		this.bookingTransport = bookingTransport;
	}

	@ManyToOne(optional = false)
	public BookingAccomodation getBookingAcc() {
		return this.bookingAcc;
	}

	public void setBookingAcc(final BookingAccomodation bookingAcc) {
		this.bookingAcc = bookingAcc;
	}

}
