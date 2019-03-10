
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.PROPERTY)
public class History extends DomainEntity {

	// Must be exactly 1 Inception Record
	Collection<Record>	records;
	Brotherhood			brotherhood;


	@ElementCollection
	public Collection<Record> getRecords() {
		return this.records;
	}

	public void setRecords(final Collection<Record> records) {
		this.records = records;
	}

	@ManyToOne(optional = true)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}
}
