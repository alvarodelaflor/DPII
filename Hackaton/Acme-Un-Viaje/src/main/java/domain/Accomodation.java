
package domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
public class Accomodation extends DomainEntity {

	private Double	pricePerNight, rating;
	private String	address, place, pictures, description;
	private Integer	maxPeople;

	private Host	host;


	@ManyToOne(optional = false)
	public Host getHost() {
		return this.host;
	}

	public void setHost(final Host host) {
		this.host = host;
	}

	@NotNull
	@Min(1)
	public Double getPricePerNight() {
		return this.pricePerNight;
	}

	public void setPricePerNight(final Double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	@NotNull
	@Min(0)
	public Double getRating() {
		return this.rating;
	}

	public void setRating(final Double rating) {
		this.rating = rating;
	}

	@SafeHtml
	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@SafeHtml
	@NotBlank
	public String getPlace() {
		return this.place;
	}

	public void setPlace(final String place) {
		this.place = place;
	}

	@URL
	@SafeHtml
	@NotBlank
	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	@SafeHtml
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Min(1)
	@NotNull
	public Integer getMaxPeople() {
		return this.maxPeople;
	}

	public void setMaxPeople(final Integer maxPeople) {
		this.maxPeople = maxPeople;
	}
}
