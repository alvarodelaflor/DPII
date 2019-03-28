/*
 * DomainEntity.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

/*
 * CONTROL DE CAMBIOS Actor.java
 * 
 * ANTONIO 24/02/2019 17:23 ISBANNED/ISSUSPICIOUS/COLLECTION MESSAGEBOX
 */

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Actor extends DomainEntity {

	private String						name;
	private String						surname;
	private String						photo;
	private String						email;
	private String						phone;
	private String						address;
	private UserAccount					userAccount;
	private String						middleName;
	private Collection<MessageBox>		messageBoxes;
	//	private Boolean						isBanned;
	//	private Boolean						isSuspicious;
	private Collection<SocialProfile>	socialProfiles;


	@Valid
	@OneToMany(cascade = javax.persistence.CascadeType.ALL)
	@Cascade({
		CascadeType.ALL
	})
	public Collection<SocialProfile> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(final Collection<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@ManyToOne(cascade = javax.persistence.CascadeType.ALL)
	@Cascade({
		CascadeType.ALL
	})
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Valid
	@OneToMany(cascade = javax.persistence.CascadeType.ALL)
	@Cascade({
		CascadeType.ALL
	})
	public Collection<MessageBox> getMessageBoxes() {
		return this.messageBoxes;
	}

	public void setMessageBoxes(final Collection<MessageBox> messageBoxes) {
		this.messageBoxes = messageBoxes;
	}

	//	public Boolean getIsBanned() {
	//		return this.isBanned;
	//	}
	//
	//	public void setIsBanned(final Boolean isBanned) {
	//		this.isBanned = isBanned;
	//	}
	//
	//	public Boolean getIsSuspicious() {
	//		return this.isSuspicious;
	//	}
	//
	//	public void setIsSuspicious(final Boolean isSuspicious) {
	//		this.isSuspicious = isSuspicious;
	//	}

}
