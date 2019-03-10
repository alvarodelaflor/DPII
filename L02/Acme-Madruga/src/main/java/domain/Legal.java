
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Legal extends Record {

	String	legalName, VATNumber, applicableLaws;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLegalName() {
		return this.legalName;
	}

	public void setLegalName(final String legalName) {
		this.legalName = legalName;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getVATNumber() {
		return this.VATNumber;
	}

	public void setVATNumber(final String vATNumber) {
		this.VATNumber = vATNumber;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getApplicableLaws() {
		return this.applicableLaws;
	}

	public void setApplicableLaws(final String applicableLaws) {
		this.applicableLaws = applicableLaws;
	}
}
