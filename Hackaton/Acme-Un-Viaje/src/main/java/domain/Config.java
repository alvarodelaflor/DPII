
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Config extends DomainEntity {

	Collection<String>	spamList, scoreList, creditCardMakeList;
	String				systemName, systemNombre, welcomeMessageEs, welcomeMessageEn, bannerLogo, defaultPhoneCode;
	Integer				transporterBanRatio;


	@NotBlank
	@SafeHtml
	public String getDefaultPhoneCode() {
		return this.defaultPhoneCode;
	}

	public void setDefaultPhoneCode(final String defaultPhoneCode) {
		this.defaultPhoneCode = defaultPhoneCode;
	}

	@NotBlank
	@SafeHtml
	public String getSystemNombre() {
		return this.systemNombre;
	}

	public void setSystemNombre(final String systemNombre) {
		this.systemNombre = systemNombre;
	}

	@NotBlank
	@SafeHtml
	public String getWelcomeMessageEs() {
		return this.welcomeMessageEs;
	}

	public void setWelcomeMessageEs(final String welcomeMessageEs) {
		this.welcomeMessageEs = welcomeMessageEs;
	}

	@NotBlank
	@SafeHtml
	public String getWelcomeMessageEn() {
		return this.welcomeMessageEn;
	}

	public void setWelcomeMessageEn(final String welcomeMessageEn) {
		this.welcomeMessageEn = welcomeMessageEn;
	}

	@NotBlank
	@SafeHtml
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@SafeHtml
	public String getBannerLogo() {
		return this.bannerLogo;
	}

	public void setBannerLogo(final String bannerLogo) {
		this.bannerLogo = bannerLogo;
	}

	@ElementCollection
	public Collection<String> getSpamList() {
		return this.spamList;
	}

	public void setSpamList(final Collection<String> spamList) {
		this.spamList = spamList;
	}

	@ElementCollection
	public Collection<String> getScoreList() {
		return this.scoreList;
	}

	public void setScoreList(final Collection<String> scoreList) {
		this.scoreList = scoreList;
	}

	@ElementCollection
	public Collection<String> getCreditCardMakeList() {
		return this.creditCardMakeList;
	}

	public void setCreditCardMakeList(final Collection<String> creditCardMakeList) {
		this.creditCardMakeList = creditCardMakeList;
	}

	@Min(0)
	@Max(100)
	@NotNull
	public Integer getTransporterBanRatio() {
		return this.transporterBanRatio;
	}

	public void setTransporterBanRatio(final Integer transporterBanRatio) {
		this.transporterBanRatio = transporterBanRatio;
	}

}
