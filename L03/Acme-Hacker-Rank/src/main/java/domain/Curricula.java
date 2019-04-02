
package domain;

/*
 * Curricula.java
 * 
 * author: Álvaro de la Flor Bonilla GitHub: alvar017
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

	private String	name, statement, phone, linkGitHub, linkLinkedin, miscellaneous;
	private Boolean	isCopy;
	private Hacker	hacker;


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
	// Hay que hacer un patron para las url de git
	public String getLinkGitHub() {
		return this.linkGitHub;
	}

	public void setLinkGitHub(final String linkGitHub) {
		this.linkGitHub = linkGitHub;
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

	@NotBlank
	@SafeHtml
	public String getMiscellaneous() {
		return this.miscellaneous;
	}

	public void setMiscellaneous(final String miscellaneous) {
		this.miscellaneous = miscellaneous;
	}

	public Boolean getIsCopy() {
		return this.isCopy;
	}

	public void setIsCopy(final Boolean isCopy) {
		this.isCopy = isCopy;
	}

	@ManyToOne(optional = false)
	public Hacker getHacker() {
		return this.hacker;
	}

	public void setHacker(final Hacker hacker) {
		this.hacker = hacker;
	}
}
