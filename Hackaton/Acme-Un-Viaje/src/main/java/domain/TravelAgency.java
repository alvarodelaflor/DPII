
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
public class TravelAgency extends Actor {

	private FinderAccomodation finder;


	@OneToOne(optional = false)
	public FinderAccomodation getFinder() {
		return this.finder;
	}

	public void setFinder(final FinderAccomodation finder) {
		this.finder = finder;
	}

}
