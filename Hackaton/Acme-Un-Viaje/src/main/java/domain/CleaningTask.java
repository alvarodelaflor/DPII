package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CleaningTask extends DomainEntity{
	
	private Cleaner     	cleaner;
	private Date    		startMoment;
	private Date        	endMoment;
	private Accomodation 	accomodation;
	private String 			description;
	
	@SafeHtml
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(optional = false)
	public Cleaner getCleaner() {
		return cleaner;
	}
	
	public void setCleaner(Cleaner cleaner) {
		this.cleaner = cleaner;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getStartMoment() {
		return startMoment;
	}
	
	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getEndMoment() {
		return endMoment;
	}
	
	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}
	
	@ManyToOne(optional = false)
	public Accomodation getAccomodation() {
		return accomodation;
	}
	
	public void setAccomodation(Accomodation accomodation) {
		this.accomodation = accomodation;
	}

}
