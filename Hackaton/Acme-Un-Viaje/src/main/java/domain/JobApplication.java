package domain;

/*
 * JobApplication.java
 * 
 * author: Alvaro de la Flor Bonilla GitHub: alvar017
 * 
 * CONTROL:
 * 22/05/2019 09:24 Creation
 */

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class JobApplication extends DomainEntity {

	private String		rejectMessage, cleanerMessage;
	private Date		createMoment;
	private Curricula	curricula;
	private Boolean		status;
	private Host host;
	private Cleaner cleaner;
	private Date dropMoment;

	@SafeHtml
	public String getRejectMessage() {
		return rejectMessage;
	}
	
	public void setRejectMessage(String rejectMessage) {
		this.rejectMessage = rejectMessage;
	}
	
	@NotBlank
	@SafeHtml
	public String getCleanerMessage() {
		return cleanerMessage;
	}
	
	public void setCleanerMessage(String cleanerMessage) {
		this.cleanerMessage = cleanerMessage;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	public Date getCreateMoment() {
		return createMoment;
	}
	
	public void setCreateMoment(Date createMoment) {
		this.createMoment = createMoment;
	}
	
	@NotNull
	@ManyToOne(optional = false)
	public Curricula getCurricula() {
		return curricula;
	}
	
	public void setCurricula(Curricula curricula) {
		this.curricula = curricula;
	}
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getDropMoment() {
		return dropMoment;
	}

	public void setDropMoment(Date dropMoment) {
		this.dropMoment = dropMoment;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Cleaner getCleaner() {
		return cleaner;
	}

	public void setCleaner(Cleaner cleaner) {
		this.cleaner = cleaner;
	}
}
