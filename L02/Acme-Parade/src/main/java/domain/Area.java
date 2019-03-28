
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/*
 * CONTROL DE CAMBIOS Area.java
 * FRAN 20/02/2019 16:20 CREACIÓN DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
public class Area extends DomainEntity {

	String				name;
	Collection<String>	pictures;

	private Chapter		chapter;


	@OneToOne(optional = true)
	public Chapter getChapter() {
		return this.chapter;
	}

	public void setChapter(final Chapter chapter) {
		this.chapter = chapter;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

}
