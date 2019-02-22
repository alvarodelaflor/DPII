/*
 * DomainEntity.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Brotherhood extends Actor {

	private String				title;
	private Date				establishmentDate;
	private Collection<String>	pictures;


	//	private Collection<Procession>	processions;
	//	private Collection<FloatBro>	floatBro;
	//	private Collection<Enrolled>	enrolleds;

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
	@Past
	public Date getEstablishmentDate() {
		return this.establishmentDate;
	}

	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	@ElementCollection(targetClass = String.class)
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}
	//
	//	@OneToMany(mappedBy = "brotherhood", cascade = CascadeType.ALL)
	//	public Collection<Procession> getProcessions() {
	//		return this.processions;
	//	}
	//
	//	public void setProcessions(final Collection<Procession> processions) {
	//		this.processions = processions;
	//	}
	//
	//	@OneToMany(mappedBy = "brotherhood")
	//	public Collection<FloatBro> getFloatBro() {
	//		return this.floatBro;
	//	}
	//
	//	public void setFloatBro(final Collection<FloatBro> floatBro) {
	//		this.floatBro = floatBro;
	//	}
	//
	//	@OneToMany(mappedBy = "brotherhood")
	//	public Collection<Enrolled> getEnrolleds() {
	//		return this.enrolleds;
	//	}
	//
	//	public void setEnrolleds(final Collection<Enrolled> enrolleds) {
	//		this.enrolleds = enrolleds;
	//	}
}
