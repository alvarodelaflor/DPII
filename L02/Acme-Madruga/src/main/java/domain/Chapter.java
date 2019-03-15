
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Chapter extends Actor {

	private String					title;

	private Collection<Proclaim>	proclaim;


	@OneToMany
	public Collection<Proclaim> getProclaim() {
		return this.proclaim;
	}

	public void setProclaim(final Collection<Proclaim> proclaim) {
		this.proclaim = proclaim;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

}
