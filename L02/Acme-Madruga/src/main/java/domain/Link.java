
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Link extends Record {

	String	brotherhoodLink;


	@URL
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBrotherhoodLink() {
		return this.brotherhoodLink;
	}

	public void setBrotherhoodLink(final String brotherhoodLink) {
		this.brotherhoodLink = brotherhoodLink;
	}
}
