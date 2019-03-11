
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

/*
 * CONTROL DE CAMBIOS Position.java
 * 
 * ALVARO 18/02/2019 09:00 CREACIÓN DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PositionAux extends DomainEntity {

	private Integer	row;
	private Integer	colum;
	private Boolean	status;
	private Parade	parade;


	@OneToOne(optional = true)
	public Parade getParade() {
		return this.parade;
	}

	public void setParade(final Parade parade) {
		this.parade = parade;
	}

	public Integer getRow() {
		return this.row;
	}

	public void setRow(final Integer row) {
		this.row = row;
	}

	public Integer getColum() {
		return this.colum;
	}

	public void setColum(final Integer colum) {
		this.colum = colum;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(final Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return this.row + " " + this.colum;
	}

}
