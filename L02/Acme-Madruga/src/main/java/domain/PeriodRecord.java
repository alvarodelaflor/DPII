
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
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

	private Integer startYear;
	private Integer endYear;
	private String photos;
	

	@NotNull
	@Min(1500)
	@Max(3000)
	public Integer getStartYear() {
		return startYear;
	}
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}
	
	@NotNull
	@Min(1500)
	@Max(3000)
	public Integer getEndYear() {
		return endYear;
	}
	public void setEndYear(Integer endYear) {
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
