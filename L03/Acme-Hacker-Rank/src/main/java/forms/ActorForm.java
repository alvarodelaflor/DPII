
package forms;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

public class ActorForm {

	private String	password, confirmPassword, address, email, name, phone, photo, surname, userName;
	private Boolean	accept;

	private String	holder, make;
	private String	CVV, number;
	private Date	expiration;


	@NotBlank
	@SafeHtml
	public String getHolder() {
		return this.holder;
	}

	public void setHolder(final String holder) {
		this.holder = holder;
	}
	@NotBlank
	@SafeHtml
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public String getCVV() {
		return this.CVV;
	}

	public void setCVV(final String cVV) {
		this.CVV = cVV;
	}

	public Date getExpiration() {
		return this.expiration;
	}

	public void setExpiration(final Date expiration) {
		this.expiration = expiration;
	}

	public Boolean getAccept() {
		return this.accept;
	}

	public void setAccept(final Boolean accept) {
		this.accept = accept;
	}

	@SafeHtml
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	@Size(min = 5, max = 32)
	@SafeHtml
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@SafeHtml
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@SafeHtml
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotBlank
	@SafeHtml
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@SafeHtml
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@URL
	@SafeHtml
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@SafeHtml
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

}
