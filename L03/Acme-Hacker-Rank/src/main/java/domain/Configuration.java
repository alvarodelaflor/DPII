
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	String	systemName, countryCode;
	Integer	cacheHours, cacheAmount;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@NotBlank
	public Integer getCacheHours() {
		return this.cacheHours;
	}

	public void setCacheHours(final Integer cacheHours) {
		this.cacheHours = cacheHours;
	}

	@NotBlank
	public Integer getCacheAmount() {
		return this.cacheAmount;
	}

	public void setCacheAmount(final Integer cacheAmount) {
		this.cacheAmount = cacheAmount;
	}

}
