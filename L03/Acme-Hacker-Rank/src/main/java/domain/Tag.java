
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Tag extends DomainEntity {

	private String	tag;


	@Override
	public String toString() {
		return this.getTag();
	}


	private int	messageId;
	private int	actorId;


	@NotBlank
	public String getTag() {
		return this.tag;
	}

	public void setTag(final String tag) {
		this.tag = tag;
	}

	public int getMessageId() {
		return this.messageId;
	}

	public void setMessageId(final int messageId) {
		this.messageId = messageId;
	}

	public int getActorId() {
		return this.actorId;
	}

	public void setActorId(final int actorId) {
		this.actorId = actorId;
	}

}
