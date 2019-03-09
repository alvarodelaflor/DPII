
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/*
 * CONTROL DE CAMBIOS PeriodRecord.java
 * 
 * ALVARO 09/03/2019 20:48 CREACION DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
public class PeriodRecord extends Record {

	private Date startYear;
	private Date endYear;
	private String photos;
	
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public Date getStartYear() {
		return startYear;
	}
	public void setStartYear(Date startYear) {
		this.startYear = startYear;
	}
	
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public Date getEndYear() {
		return endYear;
	}
	public void setEndYear(Date endYear) {
		this.endYear = endYear;
	}
	
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	
	
}
