
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
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

/*
 * CONTROL DE CAMBIOS Parade.java
 * 
 * ALVARO 17/02/2019 11:30 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 12:06 A�ADIDO ATRIBUTO TICKER
 */

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Parade extends DomainEntity {

	private String			title;
	private String			description;
	private Date			moment;
	@JsonIgnore
	private Brotherhood		brotherhood;
	private String			ticker;
	private Boolean			isFinal;
	private Integer			maxRow;
	private Integer			maxColum;
	@JsonIgnore
	private domain.Float	floatt;

	private String			status;
	private String			rejectionReason;


	@NotNull
	@Min(1)
	@Max(1200)
	public Integer getMaxRow() {
		return this.maxRow;
	}

	public void setMaxRow(final Integer maxRow) {
		this.maxRow = maxRow;
	}

	@Min(1)
	@Max(4)
	@NotNull
	public Integer getMaxColum() {
		return this.maxColum;
	}

	public void setMaxColum(final Integer maxColum) {
		this.maxColum = maxColum;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Future
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@ManyToOne(optional = true)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public Boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}

	@Pattern(regexp = "(SUBMITTED|ACCEPTED|REJECTED)")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@ManyToOne(optional = false)
	public Float getFloatt() {
		return this.floatt;
	}

	public void setFloatt(final Float floatt) {
		this.floatt = floatt;
	}

	public String getRejectionReason() {
		return this.rejectionReason;
	}

	public void setRejectionReason(final String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

}
