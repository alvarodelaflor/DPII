
package domain;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/*
 * CONTROL DE CAMBIOS LegalRecord.java
 * 
 * ALVARO 09/03/2019 20:55 CREACION DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
public class LegalRecord extends Record {

	private String legalName;
	private String vatNumber;
	private String laws;

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	@Pattern(regexp = "^([a-zA-z]{2}[0-9]{2,8}[a-zA-z]{1})$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getLaws() {
		return laws;
	}

	public void setLaws(String laws) {
		this.laws = laws;
	}
	
	
}
