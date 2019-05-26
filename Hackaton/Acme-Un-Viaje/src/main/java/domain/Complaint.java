
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Complaint extends DomainEntity {

	private String			description;
	private Date			moment;

	private Customer		customer;
	private TravelAgency	travelAgency;
	private Host			host;
	private Transporter		transporter;
	private Review			review;


	@SafeHtml
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(optional = true)
	public TravelAgency getTravelAgency() {
		return this.travelAgency;
	}

	public void setTravelAgency(final TravelAgency travelAgency) {
		this.travelAgency = travelAgency;
	}

	@ManyToOne(optional = true)
	public Host getHost() {
		return this.host;
	}

	public void setHost(final Host host) {
		this.host = host;
	}

	@ManyToOne(optional = true)
	public Transporter getTransporter() {
		return this.transporter;
	}

	public void setTransporter(final Transporter transporter) {
		this.transporter = transporter;
	}

	@OneToOne(optional = true)
	@Cascade({
		CascadeType.ALL
	})
	public Review getReview() {
		return this.review;
	}

	public void setReview(final Review review) {
		this.review = review;
	}

}
