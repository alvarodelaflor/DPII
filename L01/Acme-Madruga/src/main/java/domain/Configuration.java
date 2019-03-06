
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Cache
	private Integer	cacheHours;
	private Integer	cacheAmount;


	@Min(1)
	@Max(24)
	public Integer getCacheHours() {
		return this.cacheHours;
	}

	public void setCacheHours(final Integer cacheHours) {
		this.cacheHours = cacheHours;
	}

	@Min(0)
	@Max(100)
	public Integer getCacheAmount() {
		return this.cacheAmount;
	}

	public void setCacheAmount(final Integer cacheAmount) {
		this.cacheAmount = cacheAmount;
	}
}
