
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

/*
 * CONTROL DE CAMBIOS MessageBox.java
 * 
 * Antonio Salvat 23/02/2019 19:49 CREACIÓN DE LA CLASE
 */

@Entity
@Access(AccessType.PROPERTY)
public class MessageBox extends DomainEntity {

	private String				name;
	private Boolean				isDefault;
	private Collection<Message>	messages;
	private MessageBox			parentBox;


	@ManyToOne
	public MessageBox getParentBox() {
		return this.parentBox;
	}

	public void setParentBox(final MessageBox parentBox) {
		this.parentBox = parentBox;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Boolean getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(final Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@ManyToMany(mappedBy = "messageBoxes", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Valid
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

}
