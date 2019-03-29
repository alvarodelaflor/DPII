
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	String					key;
	Date					deadline;
	Double					minSalary, maxSalary;

	Collection<Position>	positions;


	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getKey() {
		return this.key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	@NotNull
	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(final Date deadline) {
		this.deadline = deadline;
	}

	@NotNull
	public Double getMinSalary() {
		return this.minSalary;
	}

	public void setMinSalary(final Double minSalary) {
		this.minSalary = minSalary;
	}

	@NotNull
	public Double getMaxSalary() {
		return this.maxSalary;
	}

	public void setMaxSalary(final Double maxSalary) {
		this.maxSalary = maxSalary;
	}
}
