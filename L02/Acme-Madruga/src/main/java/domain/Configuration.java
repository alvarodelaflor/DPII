
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Cache
	private Integer	cacheHours;
	private Integer	cacheAmount;
	private Double	fair;
	private Double	VAT;


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

	@NotNull
	@Min(1)
	@Max(24)
	public Integer getCacheHours() {
		return this.cacheHours;
	}

	public void setCacheHours(final Integer cacheHours) {
		this.cacheHours = cacheHours;
	}

	@NotNull
	@Min(0)
	@Max(100)
	public Integer getCacheAmount() {
		return this.cacheAmount;
	}

	public void setCacheAmount(final Integer cacheAmount) {
		this.cacheAmount = cacheAmount;
	}

}
