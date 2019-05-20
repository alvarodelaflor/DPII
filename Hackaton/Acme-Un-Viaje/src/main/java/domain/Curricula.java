
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

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
public class Curricula extends DomainEntity {

	private String	name, statement, phone, linkLinkedin;
	private Boolean	isCopy;
	private Cleaner	cleaner;


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@NotBlank
	@SafeHtml
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@SafeHtml
	@URL
	// Hay que hacer un patron para las url de linkedin
	public String getLinkLinkedin() {
		return this.linkLinkedin;
	}

	public void setLinkLinkedin(final String linkLinkedin) {
		this.linkLinkedin = linkLinkedin;
	}

	public Boolean getIsCopy() {
		return this.isCopy;
	}

	public void setIsCopy(final Boolean isCopy) {
		this.isCopy = isCopy;
	}

	@ManyToOne(optional = false)
	public Cleaner getCleaner() {
		return this.cleaner;
	}

	public void setCleaner(final Cleaner cleaner) {
		this.cleaner = cleaner;
	}
}
