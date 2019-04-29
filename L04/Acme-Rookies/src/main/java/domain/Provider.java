
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Provider extends Actor {

	String	make;


	@NotBlank
	@SafeHtml
	public String getCommercialName() {
		return this.make;
	}

	public void setCommercialName(final String commercialName) {
		this.make = commercialName;
	}

}
