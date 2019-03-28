
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
@Access(AccessType.PROPERTY)
public class Priority {

	private String	value;


	@Pattern(regexp = "HIGH|NEUTRAL|LOW")
	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

}
