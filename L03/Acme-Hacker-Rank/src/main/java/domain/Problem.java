
package domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Problem extends DomainEntity {

	private String		title, statement, hint, attachments;

	private Position	position;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	public String getHint() {
		return this.hint;
	}

	public void setHint(final String hint) {
		this.hint = hint;
	}

	@NotBlank
	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}
}
