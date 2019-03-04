
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Finder {

	//======== Atributos de la clase
	private String					keyword;
	private Date					minDate;
	private Date					maxDate;
	private Date					expirationDate;
	//======== Relaciones
	private Area					area;
	private Collection<Brotherhood>	brotherhoods;


	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	public Date getMinDate() {
		return this.minDate;
	}

	public void setMinDate(final Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return this.maxDate;
	}

	public void setMaxDate(final Date maxDate) {
		this.maxDate = maxDate;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(final Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@ManyToOne(optional = true)
	public Area getArea() {
		return this.area;
	}

	public void setArea(final Area area) {
		this.area = area;
	}

	@ManyToMany
	public Collection<Brotherhood> getBrotherhoods() {
		return this.brotherhoods;
	}

	public void setBrotherhoods(final Collection<Brotherhood> brotherhoods) {
		this.brotherhoods = brotherhoods;
	}

}
