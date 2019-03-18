
package domain;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/*
 * CONTROL DE CAMBIOS LinkRecord.java
 * 
 * ALVARO 09/03/2019 21:36 CREACION DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
public class LinkRecord extends Record {

	private String link;

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	@URL
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
