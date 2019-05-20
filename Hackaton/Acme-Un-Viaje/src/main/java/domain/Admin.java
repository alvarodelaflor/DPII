
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Admin extends Actor {

	Config	config;


	@OneToOne(optional = true)
	public Config getConfig() {
		return this.config;
	}

	public void setConfig(final Config config) {
		this.config = config;
	}
}
