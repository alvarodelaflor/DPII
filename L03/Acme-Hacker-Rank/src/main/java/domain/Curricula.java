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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
public class Curricula extends DomainEntity {

	private String	name, statement, phone, linkGitHub, linkLinkedin, miscellaneous;
	private Boolean isCopy;
	private Hacker hacker;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	@NotBlank
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotBlank
	@Pattern(regexp = "https:\\/\\/?(?:www.)?github.com\\/[A-Za-z]{3}")
	public String getLinkGitHub() {
		return linkGitHub;
	}

	public void setLinkGitHub(String linkGitHub) {
		this.linkGitHub = linkGitHub;
	}

	@NotBlank
	@Pattern(regexp = "https:\\/\\/?(?:www.)?(?:es.)?linkedin.com\\/in\\/[A-Za-z]{5}")
	public String getLinkLinkedin() {
		return linkLinkedin;
	}

	public void setLinkLinkedin(String linkLinkedin) {
		this.linkLinkedin = linkLinkedin;
	}
	

	@NotBlank
	public String getMiscellaneous() {
		return miscellaneous;
	}

	public void setMiscellaneous(String miscellaneous) {
		this.miscellaneous = miscellaneous;
	}

	public Boolean getIsCopy() {
		return isCopy;
	}

	public void setIsCopy(Boolean isCopy) {
		this.isCopy = isCopy;
	}

	@ManyToOne(optional=false)
	public Hacker getHacker() {
		return hacker;
	}

	public void setHacker(Hacker hacker) {
		this.hacker = hacker;
	}
}
