
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/*
 * CONTROL DE CAMBIOS Procession.java
 * 
 * ALVARO 17/02/2019 11:30 CREACI�N DE LA CLASE
 * ALVARO 17/02/2019 12:06 A�ADIDO ATRIBUTO TICKER
 */

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Procession extends DomainEntity {

	private String		title;
	private String		description;
	private Date		moment;
	private Brotherhood	brotherhood;
	private String		ticker;
	private Boolean		isFinal;
	private Integer		maxRow;
	private Integer		maxColum;
	private FloatBro	floatBro;


	@NotNull
	@Min(1)
	public Integer getMaxRow() {
		return this.maxRow;
	}

	public void setMaxRow(final Integer maxRow) {
		this.maxRow = maxRow;
	}

	@Min(1)
	@NotNull
	public Integer getMaxColum() {
		return this.maxColum;
	}

	public void setMaxColum(final Integer maxColum) {
		this.maxColum = maxColum;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
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

	@ManyToOne(optional = false)
	public FloatBro getFloatBro() {
		return this.floatBro;
	}

	public void setFloatBro(final FloatBro floatBro) {
		this.floatBro = floatBro;
	}
}
