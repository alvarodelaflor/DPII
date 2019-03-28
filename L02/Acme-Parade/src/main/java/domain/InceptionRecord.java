
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/*
 * CONTROL DE CAMBIOS InceptionRecord.java
 * 
 * ALVARO 09/03/2019 20:48 CREACION DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
public class InceptionRecord extends Record {

	private String photos;

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}
}
