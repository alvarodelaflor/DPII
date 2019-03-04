
package domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private Collection<String>	posWords	= Arrays.asList();
	private Collection<String>	negWords	= Arrays.asList();

	private Collection<String>	posWordsEs	= Arrays.asList();
	private Collection<String>	negWordsEs	= Arrays.asList();

	private Date				expirationDate;

	// NºResults + Cache
	private Integer				hour;
	private Integer				cant;


	public Integer getHour() {
		return this.hour;
	}

	public void setHour(final Integer hour) {
		this.hour = hour;
	}

	public Integer getCant() {
		return this.cant;
	}

	public void setCant(final Integer cant) {
		this.cant = cant;
	}

	public Collection<String> getPosWords() {
		return this.posWords;
	}

	public Collection<String> getNegWords() {
		return this.negWords;
	}

	public Collection<String> getPosWordsEs() {
		return this.posWordsEs;
	}

	public Collection<String> getNegWordsEs() {
		return this.negWordsEs;
	}

	public void setPosWords(final Collection<String> posWords) {
		this.posWords = posWords;
	}

	public void setNegWords(final Collection<String> negWords) {
		this.negWords = negWords;
	}

	public void setPosWordsEs(final Collection<String> posWordsEs) {
		this.posWordsEs = posWordsEs;
	}

	public void setNegWordsEs(final Collection<String> negWordsEs) {
		this.negWordsEs = negWordsEs;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(final Date expirationDate) {
		this.expirationDate = expirationDate;
	}
}
