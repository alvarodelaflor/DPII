
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

	private InceptionRecord inceptionRecord;
	private Collection<PeriodRecord> periodRecord;
	private Collection<LegalRecord> legalRecord;

	@OneToOne(cascade = CascadeType.ALL)
	public InceptionRecord getInceptionRecord() {
		return inceptionRecord;
	}

	public void setInceptionRecord(InceptionRecord inceptionRecord) {
		this.inceptionRecord = inceptionRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PeriodRecord> getPeriodRecord() {
		return periodRecord;
	}

	public void setPeriodRecord(Collection<PeriodRecord> periodRecord) {
		this.periodRecord = periodRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<LegalRecord> getLegalRecord() {
		return legalRecord;
	}

	public void setLegalRecord(Collection<LegalRecord> legalRecord) {
		this.legalRecord = legalRecord;
	}
}
