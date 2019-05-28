
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
public class Curricula extends DomainEntity {

	private String	name, statement, phone, linkLinkedin, bannerLogo;
	private Boolean	isCopy;
	private Cleaner	cleaner;


	@NotBlank
	@SafeHtml
	@NotNull
	@Size(max=256)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	@Size(max=256)
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@NotBlank
	@SafeHtml
	@NotNull
	@Size(max=256)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@SafeHtml
	@URL
	@Size(max=256)
	public String getLinkLinkedin() {
		return this.linkLinkedin;
	}

	public void setLinkLinkedin(final String linkLinkedin) {
		this.linkLinkedin = linkLinkedin;
	}

	@NotNull
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

	@NotBlank
	@SafeHtml
	@NotNull
	@URL
	@Size(max=256)
	public String getBannerLogo() {
		return bannerLogo;
	}

	public void setBannerLogo(String bannerLogo) {
		this.bannerLogo = bannerLogo;
	}
}
