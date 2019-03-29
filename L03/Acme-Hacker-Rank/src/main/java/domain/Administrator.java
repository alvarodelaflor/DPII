
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	Configuration	config;


	@OneToOne(optional = false)
	public Configuration getConfig() {
		return this.config;
	}

	public void setConfig(final Configuration config) {
		this.config = config;
	}
}
