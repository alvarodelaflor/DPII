
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Period extends Record {

	Date	start, end;
	// This string will be trimed and splited to form a collection
	String	photos;


	public Date getStart() {
		return this.start;
	}

	public void setStart(final Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return this.end;
	}

	public void setEnd(final Date end) {
		this.end = end;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhotos() {
		return this.photos;
	}

	public void setPhotos(final String photos) {
		this.photos = photos;
	}

}
