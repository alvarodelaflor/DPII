
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Parade extends DomainEntity {

	private String	name;


	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
