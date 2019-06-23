
package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private String				systemName, countryCode, banner, systemMessageEs, systemMessageEn;
	private Integer				cacheHours, cacheAmount;
	private Double				fair, VAT;

	private Collection<String>	spamWords;
	private Collection<String>	priorities;


	@Min(0)
	@NotNull
	public Double getFair() {
		return this.fair;
	}

	public void setFair(final Double fair) {
		this.fair = fair;
	}

	@Min(0)
	@NotNull
	public Double getVAT() {
		return this.VAT;
	}

	public void setVAT(final Double vAT) {
		this.VAT = vAT;
	}

	@NotBlank
	@SafeHtml
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@ElementCollection
	public Collection<String> getSpamWords() {
		final HashSet<String> spamWords = new HashSet<>(this.spamWords);
		return spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotBlank
	@SafeHtml
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@SafeHtml
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@NotNull
	public Integer getCacheHours() {
		return this.cacheHours;
	}

	public void setCacheHours(final Integer cacheHours) {
		this.cacheHours = cacheHours;
	}

	@NotNull
	public Integer getCacheAmount() {
		return this.cacheAmount;
	}

	public void setCacheAmount(final Integer cacheAmount) {
		this.cacheAmount = cacheAmount;
	}
	@ElementCollection
	public Collection<String> getPriorities() {
		final HashSet<String> priorities = new HashSet<>(this.priorities);
		return priorities;
	}

	public void setPriorities(final Collection<String> priorities) {
		this.priorities = priorities;
	}
	@NotBlank
	@SafeHtml
	public String getSystemMessageEn() {
		return this.systemMessageEn;
	}

	public void setSystemMessageEn(final String systemMessageEn) {
		this.systemMessageEn = systemMessageEn;
	}
	@NotBlank
	@SafeHtml
	public String getSystemMessageEs() {
		return this.systemMessageEs;
	}

	public void setSystemMessageEs(final String systemMessageEs) {
		this.systemMessageEs = systemMessageEs;
	}

	private Integer				firsTime;
	public Integer getFirsTime() {
		return this.firsTime;
	}
	public void setFirsTime(final Integer firsTime) {

		this.firsTime = firsTime;
	}
	
}