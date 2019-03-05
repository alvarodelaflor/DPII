
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/*
 * CONTROL DE CAMBIOS Brotherhoods.java
 * 
 * ALVARO 17/02/2019 11:23 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 17:10 A�ADIDO PROCESIONES Y FLOAT
 * ALVARO 17/02/2019 20:03 A�ADIDO ENROLLED
 */

@Entity
@Access(AccessType.PROPERTY)
public class Brotherhood extends Actor {

	private String					title;
	private Date					establishmentDate;
	private String					pictures;
	private Collection<Procession>	processions;
	private Collection<FloatBro>	floatBro;
	private Collection<Enrolled>	enrolleds;
	private Area					area;


	@OneToOne(optional = true)
	public Area getArea() {
		return this.area;
	}

	public void setArea(final Area area) {
		this.area = area;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	public Date getEstablishmentDate() {
		return this.establishmentDate;
	}

	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	@OneToMany(mappedBy = "brotherhood", cascade = CascadeType.ALL)
	public Collection<Procession> getProcessions() {
		return this.processions;
	}

	public void setProcessions(final Collection<Procession> processions) {
		this.processions = processions;
	}

	@OneToMany(mappedBy = "brotherhood")
	public Collection<FloatBro> getFloatBro() {
		return this.floatBro;
	}

	public void setFloatBro(final Collection<FloatBro> floatBro) {
		this.floatBro = floatBro;
	}

	@OneToMany(mappedBy = "brotherhood")
	public Collection<Enrolled> getEnrolleds() {
		return this.enrolleds;
	}

	public void setEnrolleds(final Collection<Enrolled> enrolleds) {
		this.enrolleds = enrolleds;
	}
}
