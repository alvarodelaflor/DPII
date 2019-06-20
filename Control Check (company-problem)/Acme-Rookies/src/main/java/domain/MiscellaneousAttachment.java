
package domain;

/*
 * EducationalData.java
 * 
 * author: Álvaro de la Flor Bonilla GitHub: alvar017
 * 
 * CONTROL:
 * 30/03/2019 14:00 Creation
 */

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
public class MiscellaneousAttachment extends DomainEntity {

	private String		attachment;
	private Boolean isCopy;
	private Curricula curriculaM;
	
	@NotBlank
	@SafeHtml
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	@NotNull
	public Boolean getIsCopy() {
		return isCopy;
	}
	
	public void setIsCopy(Boolean isCopy) {
		this.isCopy = isCopy;
	}
	
	@ManyToOne(optional=false)
	@NotNull
	public Curricula getCurriculaM() {
		return curriculaM;
	}
	public void setCurriculaM(Curricula curriculaM) {
		this.curriculaM = curriculaM;
	}


}
