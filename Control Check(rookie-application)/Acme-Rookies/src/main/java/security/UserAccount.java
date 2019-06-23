/*
 * UserAccount.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import domain.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
public class UserAccount extends DomainEntity implements UserDetails {

	// Constructors -----------------------------------------------------------

	private static final long	serialVersionUID	= 7254823034213841482L;


	public UserAccount() {
		super();

		this.authorities = new ArrayList<Authority>();
	}


	// Attributes -------------------------------------------------------------

	// UserDetails interface --------------------------------------------------

	private String					username;
	private String					password;
	private Collection<Authority>	authorities;

	private Double					msgCounter		= 0.;
	private Double					spamMsgCounter	= 0.;
	private Double					polarity		= 0.5;

	private Boolean					spammerFlag		= false;
	private Boolean					banned			= false;


	public Double getMsgCounter() {
		return this.msgCounter;
	}

	public void setMsgCounter(final Double msgCounter) {
		this.msgCounter = msgCounter;
	}

	public Double getSpamMsgCounter() {
		return this.spamMsgCounter;
	}

	public void setSpamMsgCounter(final Double spamMsgCounter) {
		this.spamMsgCounter = spamMsgCounter;
	}

	public Double getPolarity() {
		return this.polarity;
	}

	public void setPolarity(final Double polarity) {
		this.polarity = polarity;
	}

	public Boolean getSpammerFlag() {
		return this.spammerFlag;
	}

	public void setSpammerFlag(final Boolean spammerFlag) {
		this.spammerFlag = spammerFlag;
	}

	public Boolean getBanned() {
		return this.banned;
	}

	public void setBanned(final Boolean banned) {
		this.banned = banned;
	}

	@Size(min = 5, max = 32)
	@Column(unique = true)
	@Override
	@SafeHtml
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	@Override
	@SafeHtml
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@NotEmpty
	@Valid
	@ElementCollection
	@Override
	public Collection<Authority> getAuthorities() {
		// WARNING: Should return an unmodifiable copy, but it's not possible with hibernate!
		return this.authorities;
	}

	public void setAuthorities(final Collection<Authority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthority(final Authority authority) {
		Assert.notNull(authority);
		Assert.isTrue(!this.authorities.contains(authority));

		this.authorities.add(authority);
	}

	public void removeAuthority(final Authority authority) {
		Assert.notNull(authority);
		Assert.isTrue(this.authorities.contains(authority));

		this.authorities.remove(authority);
	}

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isEnabled() {
		return !this.spammerFlag;
	}

}
