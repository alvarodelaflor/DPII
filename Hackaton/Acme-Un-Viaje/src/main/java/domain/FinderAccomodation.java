
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class FinderAccomodation extends DomainEntity {

	//======== Atributos de la clase
	private String						keyword, address;
	private Double						price;
	private Integer						people;

	//======== Relaciones
	private Collection<Accomodation>	accomodations;


	@SafeHtml
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}
	@SafeHtml
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}
	@Min(1)
	public Integer getPeople() {
		return this.people;
	}

	public void setPeople(final Integer people) {
		this.people = people;
	}

	@ManyToMany
	public Collection<Accomodation> getAccomodations() {
		return this.accomodations;
	}

	public void setAccomodations(final Collection<Accomodation> accomodations) {
		this.accomodations = accomodations;
	}

}
