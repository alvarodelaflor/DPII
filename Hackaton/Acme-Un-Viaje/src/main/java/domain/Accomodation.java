
package domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Accomodation extends DomainEntity {

	Double	pricePerNight, rating;
	String	address, place, pictures, description;
	Integer	maxPeople;

	Host	host;


	@ManyToOne(optional = false)

	public Host getHost() {
		return this.host;
	}

	public void setHost(final Host host) {
		this.host = host;
	}

	public Double getPricePerNight() {
		return this.pricePerNight;
	}

	public void setPricePerNight(final Double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public Double getRating() {
		return this.rating;
	}

	public void setRating(final Double rating) {
		this.rating = rating;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(final String place) {
		this.place = place;
	}

	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Integer getMaxPeople() {
		return this.maxPeople;
	}

	public void setMaxPeople(final Integer maxPeople) {
		this.maxPeople = maxPeople;
	}
}
