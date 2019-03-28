
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Proclaim extends DomainEntity {

	private String	text;
	private Date	moment;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(max = 250)
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

}
