
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Future;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Embeddable
@Access(AccessType.PROPERTY)
public class CreditCard {

	// "Holder" har� referencia a un string significativo del actor del sistema a la cual pertenece
	String	holder, make, number, CVV;
	// De este dato se sacar� el mes y el a�o en el que la tarjeta ya no es v�lida
	Date	expiration;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getHolder() {
		return this.holder;
	}

	public void setHolder(final String holder) {
		this.holder = holder;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCVV() {
		return this.CVV;
	}

	public void setCVV(final String cVV) {
		this.CVV = cVV;
	}

	public Date getExpiration() {
		return this.expiration;
	}

	@Future
	public void setExpiration(final Date expiration) {
		this.expiration = expiration;
	}

}
