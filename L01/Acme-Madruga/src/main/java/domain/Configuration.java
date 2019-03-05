
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private Collection<String>	posWords;
	private Collection<String>	negWords;

	private Collection<String>	posWordsEs;
	private Collection<String>	negWordsEs;

	private Collection<String>	spamWords;
	private Collection<String>	spamWordsEs;

	// Cache
	private Integer				cacheHours;
	private Integer				cacheAmount;


	@Min(1)
	@Max(24)
	public Integer getCacheHours() {
		return this.cacheHours;
	}

	public void setCacheHours(final Integer cacheHours) {
		this.cacheHours = cacheHours;
	}

	@Min(0)
	@Max(100)
	public Integer getCacheAmount() {
		return this.cacheAmount;
	}

	public void setCacheAmount(final Integer cacheAmount) {
		this.cacheAmount = cacheAmount;
	}

	@ElementCollection
	public Collection<String> getPosWords() {
		return this.posWords;
	}

	public void setPosWords(final Collection<String> posWords) {
		this.posWords = posWords;
	}

	@ElementCollection
	public Collection<String> getNegWords() {
		return this.negWords;
	}

	public void setNegWords(final Collection<String> negWords) {
		this.negWords = negWords;
	}

	@ElementCollection
	public Collection<String> getPosWordsEs() {
		return this.posWordsEs;
	}

	public void setPosWordsEs(final Collection<String> posWordsEs) {
		this.posWordsEs = posWordsEs;
	}

	@ElementCollection
	public Collection<String> getNegWordsEs() {
		return this.negWordsEs;
	}

	public void setNegWordsEs(final Collection<String> negWordsEs) {
		this.negWordsEs = negWordsEs;
	}

	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@ElementCollection
	public Collection<String> getSpamWordsEs() {
		return this.spamWordsEs;
	}

	public void setSpamWordsEs(final Collection<String> spamWordsEs) {
		this.spamWordsEs = spamWordsEs;
	}

}
