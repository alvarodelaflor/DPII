
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	String	commercialName;
	Double auditScore;

	@NotBlank
	public String getCommercialName() {
		return this.commercialName;
	}

	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}

	@NotBlank
	public Double getAuditScore() {
		return this.auditScore;
	}

	public void setAuditScore(Double auditScore) {
		this.auditScore = auditScore;
	}
	

}
