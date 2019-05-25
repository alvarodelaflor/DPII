
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Valoration extends DomainEntity {

	Integer			score;
	String			comment;

	Cleaner			cleaner;
	Customer		customer;
	Transporter		transporter;
	TravelAgency	travelAgency;
	Host			host;


	@Min(1)
	@Max(10)
	@NotNull
	public Integer getScore() {
		return this.score;
	}

	public void setScore(final Integer score) {
		this.score = score;
	}

	@SafeHtml
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@ManyToOne(optional = true)
	public TravelAgency getTravelAgency() {
		return this.travelAgency;
	}

	public void setTravelAgency(final TravelAgency travelAgency) {
		this.travelAgency = travelAgency;
	}

	@ManyToOne(optional = true)
	public Cleaner getCleaner() {
		return this.cleaner;
	}

	public void setCleaner(final Cleaner cleaner) {
		this.cleaner = cleaner;
	}

	@ManyToOne(optional = true)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(optional = true)
	public Transporter getTransporter() {
		return this.transporter;
	}

	public void setTransporter(final Transporter transporter) {
		this.transporter = transporter;
	}

	@ManyToOne(optional = true)
	public Host getHost() {
		return this.host;
	}

	public void setHost(final Host host) {
		this.host = host;
	}

}
