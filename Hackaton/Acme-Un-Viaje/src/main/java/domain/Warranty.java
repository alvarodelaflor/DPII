
package domain;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
public class Warranty extends DomainEntity {

	private String	title, terms;
	private TravelAgency travelAgency;
	private Boolean draftMode;
	
	
	public Boolean getDraftMode() {
		return draftMode;
	}

	public void setDraftMode(Boolean draftMode) {
		this.draftMode = draftMode;
	}

	@ManyToOne(optional = false)
	public TravelAgency getTravelAgency() {
		return travelAgency;
	}

	public void setTravelAgency(TravelAgency travelAgency) {
		this.travelAgency = travelAgency;
	}

	@NotBlank
	@SafeHtml
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}
	
	
}