
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class FinderRequest extends DomainEntity {

	//======== Atributos de la clase
	private String	place;
	private Double	price;
	private Date	startDate;


	@SafeHtml
	public String getPlace() {
		return this.place;
	}

	public void setPlace(final String place) {
		this.place = place;
	}
	@DecimalMin(message = "0.0", value = "0.0")
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@ManyToMany
	public Collection<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}


	//======== Relaciones

	private Collection<Request> requests;

}
