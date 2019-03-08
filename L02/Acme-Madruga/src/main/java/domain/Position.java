
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/*
 * CONTROL DE CAMBIOS Position.java
 * 
 * ALVARO 18/02/2019 09:00 CREACI�N DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Position extends DomainEntity {

	private String	nameEs;
	private String	nameEn;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNameEs() {
		return this.nameEs;
	}

	public void setNameEs(final String nameEs) {
		this.nameEs = nameEs;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNameEn() {
		return this.nameEn;
	}

	public void setNameEn(final String nameEn) {
		this.nameEn = nameEn;
	}

}
