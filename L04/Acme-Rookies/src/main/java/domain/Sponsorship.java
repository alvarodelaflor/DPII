
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	String		banner, target;
	CreditCard	creditCard;
	Integer		bannerCount	= 0;

	Provider	provider;
	Position	position;


	public Integer getBannerCount() {
		return this.bannerCount;
	}

	public void setBannerCount(final Integer bannerCount) {
		this.bannerCount = bannerCount;
	}

	@NotBlank
	@SafeHtml
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@SafeHtml
	@URL
	public String getTarget() {
		return this.target;
	}

	public void setTarget(final String target) {
		this.target = target;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@ManyToOne(optional = false)
	public Provider getProvider() {
		return this.provider;
	}

	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

}
