
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/*
 * CONTROL DE CAMBIOS History.java
 * 
 * ALVARO 09/03/2019 11:30 CREACION DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
public class History extends DomainEntity {

	private InceptionRecord					inceptionRecord;
	private Collection<PeriodRecord>		periodRecord;
	private Collection<LegalRecord>			legalRecord;
	private Collection<LinkRecord>			linkRecord;
	private Collection<MiscellaneousRecord>	miscellaneousRecord;


	@OneToOne(cascade = CascadeType.ALL)
	public InceptionRecord getInceptionRecord() {
		return this.inceptionRecord;
	}

	public void setInceptionRecord(final InceptionRecord inceptionRecord) {
		this.inceptionRecord = inceptionRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecord() {
		return this.miscellaneousRecord;
	}

	public void setMiscellaneousRecord(final Collection<MiscellaneousRecord> miscellaneousRecord) {
		this.miscellaneousRecord = miscellaneousRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PeriodRecord> getPeriodRecord() {
		return this.periodRecord;
	}

	public void setPeriodRecord(final Collection<PeriodRecord> periodRecord) {
		this.periodRecord = periodRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<LegalRecord> getLegalRecord() {
		return this.legalRecord;
	}

	public void setLegalRecord(final Collection<LegalRecord> legalRecord) {
		this.legalRecord = legalRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<LinkRecord> getLinkRecord() {
		return this.linkRecord;
	}

	public void setLinkRecord(final Collection<LinkRecord> linkRecord) {
		this.linkRecord = linkRecord;
	}
}
