
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Config extends DomainEntity {

	Collection<String>	spamList, scoreList, creditCardMakeList;
	Integer				transporterBanRatio;


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
